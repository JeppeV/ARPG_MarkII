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

    private GameContainer gameContainer;
    private TileHandler tileHandler;
    private MapAdapter mapAdapter;
    private Rectangle movementBox;
    private Vector2f localPosition, globalPosition, velocity;
    private float moveSpeed, maxSpeed, moveThreshold, moveSlowndownFactor;
    private int width, height;
    private boolean right, left, up, down;
    private float leftOffsetBound, topOffsetBound, rightOffsetBound, bottomOffsetBound;

    //development
    private boolean checkCollision;


    public Player(int x, int y, int width, int height, GameImpl game) {
        this.gameContainer = game.getGameContainer();
        this.tileHandler = game.getTileHandler();
        this.mapAdapter = game.getMapAdapter();
        this.leftOffsetBound = this.topOffsetBound = 0;
        this.rightOffsetBound = tileHandler.getTiles().length * Tile.WIDTH - gameContainer.getWidth();
        this.bottomOffsetBound = tileHandler.getTiles()[0].length * Tile.HEIGHT - gameContainer.getHeight();
        this.movementBox = initMovementBox(175);
        this.localPosition = initPosition(x, y);
        this.velocity = new Vector2f(0, 0);
        this.globalPosition = new Vector2f(x, y);
        this.width = width;
        this.height = height;
        this.right = this.left = this.up = this.down = false;
        this.moveSpeed = 0.2f;
        this.maxSpeed = 5;
        this.moveThreshold = 0.0001f;
        this.moveSlowndownFactor = 0.75f;

        //development
        this.checkCollision = true;

    }

    private Vector2f initPosition(int x, int y){
        Rectangle bounds = new Rectangle(0, 0, mapAdapter.getWidthInPixels(), mapAdapter.getHeightInPixels());
        Vector2f localPosition = new Vector2f(x, y);
        if(!bounds.contains(x, y)) throw new IllegalStateException("Player position is outside of world bounds");
        float dX = x - movementBox.getCenterX();
        float dY = y - movementBox.getCenterY();
        Vector2f v = new Vector2f(dX, dY);
        if(!intersectsOffsetBoundLeftOrRight(v)){
            tileHandler.changeTilesOffset(dX, 0);
        }
        if(!intersectsOffsetBoundTopOrBottom(v)){
            tileHandler.changeTilesOffset(0, dY);
        }
        localPosition.add(v.negate());
        return localPosition;

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
        return localPosition.getX();
    }

    @Override
    public float getY() {
        return localPosition.getY();
    }

    @Override
    public float getGlobalX() {
        return globalPosition.getX();
    }

    @Override
    public float getGlobalY() {
        return globalPosition.getY();
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
        if (!tileCollision(localPosition.copy().add(velocityX))) {
            if (intersectsOffsetBoundLeftOrRight(velocity)) {
                localPosition.add(velocityX);
            } else if (intersectsMovementBoxLeftOrRight()) {
                //notify tile handler to change xOffset
                setChanged();
                notifyObservers(velocityX);
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
                //notify tile handler to change yOffset
                setChanged();
                notifyObservers(velocityY);
            } else {
                localPosition.add(velocityY);
            }
            globalPosition.add(velocityY);
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


    private boolean intersectsOffsetBoundLeftOrRight(Vector2f velocity) {
        if (((tileHandler.getXOffset() <= leftOffsetBound) && Movement.isMovingLeft(velocity)) || ((tileHandler.getXOffset() >= rightOffsetBound) && Movement.isMovingRight(velocity))) {
            return true;
        }
        return false;
    }

    private boolean intersectsOffsetBoundTopOrBottom(Vector2f velocity) {
        if (((tileHandler.getYOffset() <= topOffsetBound) && Movement.isMovingUp(velocity)) || ((tileHandler.getYOffset() >= bottomOffsetBound) && Movement.isMovingDown(velocity))) {
            return true;
        }
        return false;
    }

    private boolean intersectsMovementBoxLeftOrRight() {
        float x1 = movementBox.getX();
        float x2 = movementBox.getX() + movementBox.getWidth();

        if (((localPosition.getX() <= x1) && Movement.isMovingLeft(velocity)) || ((localPosition.getX() >= x2) && Movement.isMovingRight(velocity))) {
            return true;
        }
        return false;
    }

    private boolean intersectsMovementBoxTopOrBottom() {
        float y1 = movementBox.getY();
        float y2 = movementBox.getY() + movementBox.getHeight();

        if (((localPosition.getY() <= y1) && Movement.isMovingUp(velocity)) || ((localPosition.getY() >= y2) && Movement.isMovingDown(velocity))) {
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
