package model.entities;

import model.GameImpl;
import model.facade.Entity;
import org.newdawn.slick.geom.Rectangle;

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
