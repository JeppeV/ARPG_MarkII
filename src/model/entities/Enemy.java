package model.entities;

import model.GameImpl;
import model.OffsetHandler;
import model.facade.Entity;
import model.tiles.TileHandler;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.Mover;

/**
 * Created by Jeppe Vinberg on 07-02-2016.
 */
public abstract class Enemy implements Entity, Mover {

    protected GameImpl game;
    protected TileHandler tileHandler;
    protected Vector2f position, velocity;
    protected boolean attacking;
    private OffsetHandler offsetHandler;
    private float moveThreshold, moveSlowdownFactor;


    public Enemy(float x, float y, GameImpl game) {
        this.game = game;
        this.offsetHandler = game.getOffsetHandler();
        this.tileHandler = game.getTileHandler();
        this.position = initPosition(x, y, game.getOffsetHandler());
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

    protected Vector2f slowdown(Vector2f vel) {
        vel.scale(moveSlowdownFactor);
        //if character is moving slower than threshold, halt
        if (vel.length() < moveThreshold) {
            vel.set(0, 0);
        }
        return vel;
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

    @Override
    public Vector2f getGlobalCenterPosition() {
        Vector2f result = getCenterPosition().copy().add(offsetHandler.getOffset());
        return result;
    }

    @Override
    public void addForce(Vector2f force) {
        velocity.add(force);
    }


}
