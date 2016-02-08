package model.entities;

import model.GameImpl;
import model.facade.Entity;
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

    public GruntEnemy(float x, float y, GameImpl game) {
        super(x, y, game);
        this.width = 30;
        this.height = 30;
        this.mass = 10;
        this.maxSpeed = 5f;
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
    protected Entity acquireTarget() {
        Entity target = null;
        float currentDistance, distance = 0.0f;
        int id;
        for (Entity e : game.getEntities()) {
            currentDistance = e.getCenterPosition().distance(getCenterPosition());
            id = e.getID();
            if ((id == EntityID.PLAYER || id == EntityID.OTHER_PLAYER) && (target == null || currentDistance <= distance)) {
                target = e;
                distance = currentDistance;
            }
        }
        return target;
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        Entity target = acquireTarget();
        Vector2f steering = new Vector2f(0, 0);

        if (target != null) {
            steering = seek(target.getCenterPosition());
        }

        if (velocity.copy().add(steering).length() < getMaxSpeed()) {
            velocity.add(steering);
        }

        //if character is moving, slow down
        if (velocity.length() > 0) {
            velocity = slowdown(velocity);
        }
        if (target != null && !getCollisionBox().intersects(target.getCollisionBox())) {
            position.add(velocity);
        }
    }

    @Override
    public float getRotation() {
        return 90 + (float) velocity.getTheta();
    }


    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
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

}
