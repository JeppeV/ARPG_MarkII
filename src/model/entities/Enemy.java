package model.entities;

import generator.standard.TileType;
import model.GameImpl;
import model.OffsetHandler;
import model.tiles.Tile;
import model.tiles.TileHandler;
import model.tiles.TileImpl;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

/**
 * Created by Jeppe Vinberg on 07-02-2016.
 */
public abstract class Enemy implements Entity, Mover {

    protected GameImpl game;
    protected TileHandler tileHandler;
    protected Vector2f position, velocity;
    protected boolean attacking;

    private AStarPathFinder pathFinder;
    private Path path;
    private int step;

    private Entity currentTarget;
    private float moveThreshold, moveSlowdownFactor;

    private float MAX_SEE_AHEAD = 70.0f;
    private float MAX_AVOIDANCE = 0.9f;


    public Enemy(float x, float y, GameImpl game) {
        this.game = game;
        this.tileHandler = game.getTileHandler();
        this.pathFinder = new AStarPathFinder(game.getMapAdapter(), 15, true);
        this.path = null;
        this.step = 0;
        this.position = new Vector2f(x, y);
        //this.position = initPosition(x, y, game.getOffsetHandler());
        System.out.println("Enemy " + position.getX() + ", " + position.getY());
        this.velocity = new Vector2f(0, 0);
        this.attacking = false;
        this.moveThreshold = 0.0001f;
        this.moveSlowdownFactor = 0.75f;
    }

    private Vector2f initPosition(float x, float y, OffsetHandler offsetHandler) {
        Vector2f position = new Vector2f(x, y);
        position.sub(offsetHandler.getOffset());
        return position;
    }

    protected abstract Entity acquireTarget();

    protected abstract float getMass();

    protected abstract float getMaxSpeed();

    protected Vector2f seek(Vector2f target) {
        Vector2f steering;
        Vector2f desiredVelocity = (target.sub(getCenterPosition())).normalise().scale(getMaxSpeed());
        steering = desiredVelocity.sub(velocity);
        steering.scale(1 / getMass());
        return steering;
    }

    protected Vector2f tileCollisionAvoidance() {
        Vector2f ahead, ahead2;
        Vector2f avoidance = new Vector2f(0, 0);
        TileImpl tile;

        Vector2f positionCopy = getCenterPosition().copy();
        ahead = velocity.copy().normalise();
        ahead.scale(MAX_SEE_AHEAD);
        ahead = positionCopy.add(ahead);
        positionCopy = getCenterPosition().copy();
        ahead2 = velocity.copy().normalise();
        ahead2.scale(MAX_SEE_AHEAD);
        ahead2.scale(0.5f);
        ahead2 = positionCopy.add(ahead2);


        tile = mostThreateningTile(ahead, ahead2);
        if (tile != null) {
            float x = ahead.getX() - tile.getCenterX();
            float y = ahead.getY() - tile.getCenterY();
            avoidance.set(x, y);
            avoidance.normalise();
            avoidance.scale(MAX_AVOIDANCE);
        }
        return avoidance;
    }


    private TileImpl mostThreateningTile(Vector2f ahead, Vector2f ahead2) {
        TileImpl result = null;
        float d, bestDistance = 0.0f;
        boolean collision;
        Vector2f tCenter;
        Vector2f index = tileHandler.getIndexByPosition(getCenterPosition().getX(), getCenterPosition().getY());
        for (TileImpl t : tileHandler.getNeighbours((int) index.getX(), (int) index.getY(), 2, true)) {
            collision = vectorIntersectsTile(ahead, t) || vectorIntersectsTile(ahead2, t) || vectorIntersectsTile(getCenterPosition(), t);
            if (collision) {
                tCenter = new Vector2f(t.getCenterX(), t.getCenterY());
                d = position.distance(tCenter);
                if (result == null || (d < bestDistance)) {
                    result = t;
                    bestDistance = d;
                }
            }
        }
        return result;
    }

    private boolean vectorIntersectsTile(Vector2f v, TileImpl t) {
        //return t.getID() == TileType.WALL && v.distance(tileCenter) <= Tile.SIZE / 2;
        return t.getID() == TileType.WALL && t.contains(v.getX(), v.getY());
    }

    protected Vector2f slowdown(Vector2f vel) {
        vel.scale(moveSlowdownFactor);
        //if character is moving slower than threshold, halt
        if (vel.length() < moveThreshold) {
            vel.set(0, 0);
        }
        return vel;
    }

    protected Vector2f followPathTo(Entity e, boolean forceNewPath) {
        Vector2f target, result = new Vector2f(0, 0);
        if (forceNewPath || path == null || !e.equals(currentTarget)) {
            updatePathTo(e, forceNewPath);
        }

        if (path != null && step < path.getLength()) {

            TileImpl t = tileHandler.getTileByIndex(path.getX(step), path.getY(step));
            target = new Vector2f(t.getCenterX(), t.getCenterY());
            if (getCenterPosition().distance(target) <= Tile.SIZE / 4) {
                step++;
            }
            result = seek(target);
        } else {
            path = null;
            currentTarget = null;
        }

        return result;
    }


    private void updatePathTo(Entity e, boolean forceNewPath) {
        //try to get an existing path from nearby enemy
        if(!forceNewPath) getExistingPathTo(e);
        //if it was not successful, find new path
        if(forceNewPath || path == null){
            Vector2f source = new Vector2f(tileHandler.getIndexByPosition(getCenterPosition()));
            Vector2f target = new Vector2f(tileHandler.getIndexByPosition(e.getCenterPosition()));
            path = pathFinder.findPath(this, (int) source.getX(), (int) source.getY(), (int) target.getX(), (int) target.getY());
            step = 0;
            currentTarget = e;
        }
    }

    private void getExistingPathTo(Entity entity){
        Entity target;
        Enemy enemy;
        for(Entity e : game.getEntities()){
            if(this.getCenterPosition().distance(e.getCenterPosition()) >= Tile.SIZE) continue;
            if(e.equals(this)) continue;
            if(e instanceof Enemy){
                enemy = (Enemy) e;
                target = enemy.getCurrentTarget();
                if(target != null && target.equals(entity)){
                    path = enemy.getCurrentPath();
                    step = enemy.getCurrentStep();
                    currentTarget = target;
                    return;
                }
            }
        }
        path = null;
    }

    public Path getCurrentPath(){
        return path;
    }

    public int getCurrentStep(){
        return step;
    }

    public Entity getCurrentTarget(){
        return currentTarget;
    }


    @Override
    public float getX() {
        return position.getX();
    }

    @Override
    public void setX(float x) {
        position.set(x, position.getY());
    }

    @Override
    public float getY() {
        return position.getY();
    }

    @Override
    public void setY(float y) {
        position.set(position.getX(), y);
    }

    @Override
    public Vector2f getCenterPosition() {
        return new Vector2f(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }


}
