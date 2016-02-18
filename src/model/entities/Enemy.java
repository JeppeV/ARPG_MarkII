package model.entities;

import generator.standard.TileType;
import model.GameImpl;
import model.OffsetHandler;
import model.PathContext;
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
    protected boolean inCombat;

    private AStarPathFinder pathFinder12;
    private AStarPathFinder pathFinder5;

    private PathContext pathContext;

    private float moveThreshold, moveSlowdownFactor;

    private float MAX_SEE_AHEAD = 70.0f;
    private float MAX_AVOIDANCE = 0.9f;


    public Enemy(float x, float y, GameImpl game) {
        this.game = game;
        this.tileHandler = game.getTileHandler();
        this.pathFinder12 = new AStarPathFinder(game.getMapAdapter(), 12, true);
        this.pathFinder5 = new AStarPathFinder(game.getMapAdapter(), 5, true);
        this.pathContext = new PathContext(null, 0, null);
        this.position = new Vector2f(x, y);
        //this.position = initPosition(x, y, game.getOffsetHandler());
        System.out.println("Enemy " + position.getX() + ", " + position.getY());
        this.velocity = new Vector2f(0, 0);
        this.inCombat = false;
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
        Path path = pathContext.getPath();
        int step = pathContext.getStep();
        Entity currentTarget = pathContext.getTarget();

        if (path != null && step < path.getLength()) {

            TileImpl t = tileHandler.getTileByIndex(path.getX(step), path.getY(step));
            target = new Vector2f(t.getCenterX(), t.getCenterY());
            if (getCenterPosition().distance(target) <= Tile.SIZE / 4) {
                pathContext.setStep(pathContext.getStep() + 1);
            }
            result = seek(target);
        }else{
            pathContext = new PathContext(null, 0, null);
        }

        path = pathContext.getPath();

        if (forceNewPath || path == null || !e.equals(currentTarget)) {
            pathContext = updatePathTo(e);
        }

        return result;
    }

    protected boolean existsPathTo(Entity e){
        Vector2f source = new Vector2f(tileHandler.getIndexByPosition(getCenterPosition()));
        Vector2f target = new Vector2f(tileHandler.getIndexByPosition(e.getCenterPosition()));
        Path path = pathFinder5.findPath(this, (int) source.getX(), (int) source.getY(), (int) target.getX(), (int) target.getY());
        return path != null;
    }


    private PathContext updatePathTo(Entity e) {
        PathContext result = new PathContext(null, 0, null);
        //if it was not successful, find new path
        if(result.getPath() == null){
            Vector2f source = new Vector2f(tileHandler.getIndexByPosition(getCenterPosition()));
            Vector2f target = new Vector2f(tileHandler.getIndexByPosition(e.getCenterPosition()));
            Path path = pathFinder12.findPath(this, (int) source.getX(), (int) source.getY(), (int) target.getX(), (int) target.getY());
            result = new PathContext(path, 0, e);
        }
        return result;
    }

    public PathContext getPathContextCopy(){
        return new PathContext(pathContext.getPath(), pathContext.getStep(), pathContext.getTarget());
    }

    public Entity getCurrentTarget(){
        return pathContext.getTarget();
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
