package model.entities;

import model.GameImpl;
import model.tiles.Tile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Jeppe Vinberg on 05-02-2016.
 */
public class GruntEnemy extends Enemy implements Entity {

    private int width, height;
    private float mass;
    private float maxSpeed;
    private boolean closeFollow;


    public GruntEnemy(float x, float y, GameImpl game) {
        super(x, y, game);
        this.width = 30;
        this.height = 30;
        this.mass = 10;
        this.maxSpeed = 10f;
        this.closeFollow = false;
    }

    @Override
    protected Entity acquireTarget() {
        Entity target = null;
        float currentDistance, distance = 0.0f;
        int id;
        for (Entity e : game.getEntities()) {
            currentDistance = e.getCenterPosition().distance(getCenterPosition());
            id = e.getID();
            if (id == EntityID.PLAYER && (target == null || currentDistance <= distance)) {
                target = e;
                distance = currentDistance;
            }
        }
        return target;
    }

    @Override
    protected float getMass() {
        return mass;
    }

    @Override
    protected float getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        Vector2f steering = new Vector2f(0, 0);
        Entity target = acquireTarget();
        if(!inCombat && target.getCenterPosition().distance(getCenterPosition()) <= Tile.SIZE * 4){
            inCombat = existsPathTo(target);
        }
        if(inCombat){
            //if there is a target, steer towards it
            if (target != null && target.getCenterPosition().distance(getCenterPosition()) < Tile.SIZE) {
                steering = steering.add(seek(target.getCenterPosition()));
                closeFollow = true;
            } else if (target != null) {
                if (closeFollow) {
                    steering = followPathTo(target, true);
                    closeFollow = false;
                } else {
                    steering = followPathTo(target, false);
                }
            }

            steering = steering.add(tileCollisionAvoidance());
        }

        if(getPathContextCopy().getPath() == null) inCombat = false;

        //if adding steering to the current velocity does not exceed our maxSpeed, add steering to velocity
        if (velocity.copy().add(steering).length() < getMaxSpeed()) {
            velocity.add(steering);
        }

        //if character is moving, slow down
        if (velocity.length() > 0.0) {
            velocity = slowdown(velocity);
        }
        //if there is a target, and we are not touching that target, move towards target
        if (target != null && !getCollisionBox().intersects(target.getCollisionBox())) {
            position.add(velocity);
        }
    }

    @Override
    public float getRotation() {
        return (float) velocity.getTheta();
    }

    @Override
    public int getID() {
        return EntityID.ENEMY_GRUNT;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

}
