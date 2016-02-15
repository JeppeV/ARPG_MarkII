package model.entities;

import generator.standard.TileType;
import model.GameImpl;
import model.Movement;
import model.OffsetHandler;
import model.facade.Entity;
import model.tiles.TileHandler;
import model.tiles.TileImpl;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class Player implements Entity {

    private OffsetHandler offsetHandler;
    private TileHandler tileHandler;
    private Rectangle movementBox;
    private Vector2f localPosition, globalPosition, velocity;
    private float moveSpeed, maxSpeed, moveThreshold, moveSlowdownFactor;
    private int width, height;
    private boolean right, left, up, down;
    private float leftOffsetBound, topOffsetBound, rightOffsetBound, bottomOffsetBound;

    //development
    private boolean checkCollision = true;


    public Player(float x, float y, int width, int height, GameImpl game) {
        GameContainer gameContainer = game.getGameContainer();
        this.offsetHandler = game.getOffsetHandler();
        this.leftOffsetBound = this.topOffsetBound = 0;
        this.tileHandler = game.getTileHandler();
        this.rightOffsetBound = tileHandler.getWidthInPixels() - gameContainer.getWidth();
        this.bottomOffsetBound = tileHandler.getHeightInPixels() - gameContainer.getHeight();
        this.movementBox = Movement.getMovementBox(gameContainer, 175);
        this.localPosition = initPosition(x, y, tileHandler);
        this.globalPosition = new Vector2f(x, y);
        this.velocity = new Vector2f(0, 0);
        this.width = width;
        this.height = height;
        this.right = this.left = this.up = this.down = false;
        this.moveSpeed = 0.2f;
        this.maxSpeed = 5;
        this.moveThreshold = 0.0001f;
        this.moveSlowdownFactor = 0.75f;


    }

    private Vector2f initPosition(float x, float y, TileHandler tileHandler) {
        Rectangle bounds = new Rectangle(0, 0, tileHandler.getWidthInPixels(), tileHandler.getHeightInPixels());
        Vector2f localPosition = new Vector2f(x, y);
        if (!bounds.contains(x, y)) throw new IllegalStateException("Player position is outside of world bounds");
        float dX = x - movementBox.getCenterX();
        float dY = y - movementBox.getCenterY();
        Vector2f v = new Vector2f(dX, dY);
        if (!intersectsOffsetBoundLeftOrRight(v)) {
            offsetHandler.changeOffset(dX, 0);
            localPosition.add(new Vector2f(v.negate().getX(), 0));
        }
        if (!intersectsOffsetBoundTopOrBottom(v)) {
            offsetHandler.changeOffset(0, dY);
            localPosition.add(new Vector2f(0, v.negate().getY()));
        }

        return localPosition;

    }

    public void toggleCollisionCheck() {
        checkCollision = !checkCollision;
    }

    public void setMoveRight(boolean right) {
        this.right = right;
    }

    public void setMoveLeft(boolean left) {
        this.left = left;
    }

    public void setMoveUp(boolean up) {
        this.up = up;
    }

    public void setMoveDown(boolean down) {
        this.down = down;
    }

    @Override
    public void update(GameContainer gameContainer, int delta) {
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
        //scale with delta
        force.scale(delta);
        if ((velocity.length() < maxSpeed)) {
            velocity.add(force);
        }

        //if character is moving, slow down
        if (velocity.length() > 0) {
            velocity = slowdown(velocity);
        }

        //splitting velocity vector into X and Y components
        Vector2f velocityX = new Vector2f(velocity.getX(), 0);
        Vector2f velocityY = new Vector2f(0, velocity.getY());

        //if not colliding on X axis, add X velocity
        if (!tileCollision(localPosition.copy().add(velocityX))) {
            if (intersectsOffsetBoundLeftOrRight(velocity)) {
                localPosition.add(velocityX);
            } else if (intersectsMovementBoxLeftOrRight()) {
                offsetHandler.changeOffset(velocityX);
            } else {
                localPosition.add(velocityX);
            }
            globalPosition.add(velocityX);
        }
        //if not colliding on Y axis, add Y velocity
        if (!tileCollision(localPosition.copy().add(velocityY))) {
            if (intersectsOffsetBoundTopOrBottom(velocity)) {
                localPosition.add(velocityY);
            } else if (intersectsMovementBoxTopOrBottom()) {
                offsetHandler.changeOffset(velocityY);
            } else {
                localPosition.add(velocityY);
            }
            globalPosition.add(velocityY);
        }

        //System.out.println(tileHandler.getTileByIndex(0,0));

    }

    @Override
    public float getX() {
        return localPosition.getX();
    }

    @Override
    public void setX(float x) {
        localPosition.set(x, localPosition.getY());
    }

    @Override
    public float getY() {
        return localPosition.getY();
    }

    @Override
    public void setY(float y) {
        localPosition.set(localPosition.getX(), y);
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

    public Vector2f getGlobalCenterPosition(float x, float y) {
        Vector2f result = getCenterPosition().copy().add(offsetHandler.getOffset());
        return result;
    }

    @Override
    public float getRotation() {
        return 0;
    }

    @Override
    public void addForce(Vector2f force) {
        velocity.add(force);
    }

    @Override
    public int getID() {
        return EntityID.PLAYER;
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
        int dx = getWidth() / 4;
        int dy = getHeight() / 5;
        return new Rectangle(getX() + dx, getY() + dy, getWidth() - (2 * dx), getHeight() - (2 * dy));
    }

    private Rectangle getCollisionBox(float x, float y) {
        int dx = getWidth() / 4;
        int dy = getHeight() / 5;
        return new Rectangle(x + dx, y + dy, getWidth() - (2 * dx), getHeight() - (2 * dy));
    }

    private boolean tileCollision(Vector2f prediction) {
        Rectangle r = getCollisionBox(prediction.getX(), prediction.getY());

        //development
        if (!checkCollision) return false;

        //actual collision
        for (TileImpl t : tileHandler.getNeighboursByPosition(getGlobalCenterPosition())) {
            if (t.getID() == TileType.WALL && r.intersects(t)) return true;
        }
        return false;
    }

    private boolean intersectsOffsetBoundLeftOrRight(Vector2f velocity) {
        if (((offsetHandler.getXOffset() <= leftOffsetBound) && Movement.isMovingLeft(velocity)) || ((offsetHandler.getXOffset() >= rightOffsetBound) && Movement.isMovingRight(velocity))) {
            return true;
        }
        return false;
    }

    private boolean intersectsOffsetBoundTopOrBottom(Vector2f velocity) {
        if (((offsetHandler.getYOffset() <= topOffsetBound) && Movement.isMovingUp(velocity)) || ((offsetHandler.getYOffset() >= bottomOffsetBound) && Movement.isMovingDown(velocity))) {
            return true;
        }
        return false;
    }

    private boolean intersectsMovementBoxLeftOrRight() {
        float x1 = movementBox.getX();
        float x2 = movementBox.getX() + movementBox.getWidth();

        if (((getX() <= x1) && Movement.isMovingLeft(velocity)) || ((getX() >= x2) && Movement.isMovingRight(velocity))) {
            return true;
        }
        return false;
    }

    private boolean intersectsMovementBoxTopOrBottom() {
        float y1 = movementBox.getY();
        float y2 = movementBox.getY() + movementBox.getHeight();

        if (((getY() <= y1) && Movement.isMovingUp(velocity)) || ((getY() >= y2) && Movement.isMovingDown(velocity))) {
            return true;
        }
        return false;
    }


    private Vector2f slowdown(Vector2f vel) {
        vel.scale(moveSlowdownFactor);
        //if character is moving slower than threshold, halt
        if (vel.length() < moveThreshold) {
            vel.set(0, 0);
        }
        return vel;
    }
}
