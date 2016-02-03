package model;

import generator.standard.TileType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import java.util.Observable;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class Player extends Observable implements Entity {

    private GameImpl game;
    private GameContainer gameContainer;
    private Rectangle movementBox;
    private TileHandler tileHandler;
    private Vector2f position, velocity;
    private float moveSpeed, maxSpeed, moveThreshold, moveSlowndownFactor;
    private int width, height;
    private boolean right, left, up, down;
    private float leftOffsetBound, topOffsetBound, rightOffsetBound, bottomOffsetBound;

    //development
    private boolean checkCollision;


    public Player(int x, int y, int width, int height, GameImpl game) {
        this.game = game;
        this.tileHandler = game.getTileHandler();
        this.gameContainer = game.getGameContainer();
        this.movementBox = initMovementBox(175);
        this.position = new Vector2f(x, y);
        this.velocity = new Vector2f(0, 0);
        this.width = width;
        this.height = height;
        this.right = this.left = this.up = this.down = false;
        this.leftOffsetBound = this.topOffsetBound = 0;
        this.rightOffsetBound = tileHandler.getTiles().length * Tile.WIDTH - gameContainer.getWidth();
        this.bottomOffsetBound = tileHandler.getTiles()[0].length * Tile.HEIGHT - gameContainer.getHeight();
        this.moveSpeed = 0.2f;
        this.maxSpeed = 5;
        this.moveThreshold = 0.0001f;
        this.moveSlowndownFactor = 0.75f;

        //development
        this.checkCollision = true;

    }

    public void toggleCollisionCheck() {
        checkCollision = !checkCollision;
    }

    private Rectangle initMovementBox(float size) {
        float movementBoxWidth = size * gameContainer.getAspectRatio();
        float movementBoxHeight = size;
        return new Rectangle((gameContainer.getWidth() / 2) - (movementBoxWidth / 2), (gameContainer.getHeight() / 2) - (movementBoxHeight / 2), movementBoxWidth, movementBoxHeight);
    }

    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
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




    @Override
    public void update(GameContainer gameContainer, int delta) {
        updateMovement(delta);
    }

    private void updateMovement(int delta) {
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
        if (!tileCollision(position.copy().add(velocityX))) {
            if (intersectsOffsetBoundLeftOrRight()) {
                position.add(velocityX);
            } else if (intersectsMovementBoxLeftOrRight()) {
                setChanged();
                notifyObservers(velocityX);
            } else {
                position.add(velocityX);
            }
        }
        //if not colliding on Y axis, add Y velocity
        if (!tileCollision(position.copy().add(velocityY))) {
            if (intersectsOffsetBoundTopOrBottom()) {
                position.add(velocityY);
            } else if (intersectsMovementBoxTopOrBottom()) {
                setChanged();
                notifyObservers(velocityY);
            } else {
                position.add(velocityY);
            }
        }




    }


    private boolean tileCollision(Vector2f prediction) {
        Rectangle r = new Rectangle(prediction.getX(), prediction.getY(), getWidth(), getHeight());

        //development
        if (!checkCollision) return false;

        for (int x = 0; x < tileHandler.getTiles().length; x++) {
            for (int y = 0; y < tileHandler.getTiles()[0].length; y++) {
                if (tileHandler.getTile(x, y).getID() == TileType.WALL && r.intersects((TileImpl) tileHandler.getTile(x, y)))
                    return true;
            }
        }
        return false;
    }


    private boolean intersectsOffsetBoundLeftOrRight() {
        if (((tileHandler.getXOffset() <= leftOffsetBound) && Movement.isMovingLeft(velocity)) || ((tileHandler.getXOffset() >= rightOffsetBound) && Movement.isMovingRight(velocity))) {
            return true;
        }
        return false;
    }

    private boolean intersectsOffsetBoundTopOrBottom() {
        if (((tileHandler.getYOffset() <= topOffsetBound) && Movement.isMovingUp(velocity)) || ((tileHandler.getYOffset() >= bottomOffsetBound) && Movement.isMovingDown(velocity))) {
            return true;
        }
        return false;
    }

    private boolean intersectsMovementBoxLeftOrRight() {
        float x1 = movementBox.getX();
        float x2 = movementBox.getX() + movementBox.getWidth();

        if (((position.getX() <= x1) && Movement.isMovingLeft(velocity)) || ((position.getX() >= x2) && Movement.isMovingRight(velocity))) {
            return true;
        }
        return false;
    }

    private boolean intersectsMovementBoxTopOrBottom() {
        float y1 = movementBox.getY();
        float y2 = movementBox.getY() + movementBox.getHeight();

        if (((position.getY() <= y1) && Movement.isMovingUp(velocity)) || ((position.getY() >= y2) && Movement.isMovingDown(velocity))) {
            return true;
        }
        return false;
    }


    private Vector2f slowdown(Vector2f vel) {
        vel.scale(moveSlowndownFactor);
        //if character is moving slower than threshold, halt
        if (vel.length() < moveThreshold) {
            vel.set(0, 0);
        }
        return vel;
    }
}
