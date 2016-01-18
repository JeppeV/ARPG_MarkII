package model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class Player implements Entity {

    private Vector2f position, velocity;
    private float moveSpeed, maxSpeed;
    private float moveThreshold;
    private int width, height;
    private boolean right = false, left = false, up = false, down = false;


    public Player(int x, int y, int width, int height) {
        this.position = new Vector2f(x, y);
        this.velocity = new Vector2f(0, 0);
        this.width = width;
        this.height = height;
        this.moveSpeed = 0.1f;
        this.maxSpeed = 5;
        this.moveThreshold = 0.0001f;
    }

    public void toggleRightMovement() {
        right = !right;
    }

    public void toggleLeftMovement() {
        left = !left;
    }

    public void toggleUpMovement() {
        up = !up;
    }

    public void toggleDownMovement() {
        down = !down;
    }

    @Override
    public float getX() {
        return position.getX();
    }

    @Override
    public float getY() {
        return position.getY();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void addForce(Vector2f force) {
        velocity.add(force);

    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
        move(delta);
    }

    private void move(int delta) {
        float x = 0, y = 0;
        if (right) {
            x += moveSpeed;
        }
        if (left) {
            x -= moveSpeed;
        }
        if (up) {
            y -= moveSpeed;
        }
        if (down) {
            y += moveSpeed;
        }
        Vector2f force = new Vector2f(x, y);
        force.scale(delta);
        if ((velocity.length() < maxSpeed)) {
            addForce(force);
        }
        position.add(velocity);
        if (velocity.length() > 0) {
            velocity = slowdown(velocity);
        }

    }

    private Vector2f slowdown(Vector2f vel) {
        Vector2f velocity = vel;
        velocity.scale(0.90f);
        if (velocity.length() < moveThreshold) {
            velocity.set(0, 0);
        }
        return velocity;
    }
}
