package model;

import generator.standard.Coordinates;
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
    private float moveSpeed, maxSpeed;
    private float moveThreshold;
    private int width, height;
    private boolean right, left, up, down;


    public Player(int x, int y, int width, int height, GameImpl game) {
        this.game = game;
        this.tileHandler = game.getTileHandler();
        this.gameContainer = game.getGameContainer();
        this.movementBox = initMovementBox(150);
        this.position = new Vector2f(x, y);
        this.velocity = new Vector2f(0, 0);
        this.width = width;
        this.height = height;
        this.right = this.left = this.up = this.down = false;
        this.moveSpeed = 0.1f;
        this.maxSpeed = 5;
        this.moveThreshold = 0.0001f;

    }

    private Rectangle initMovementBox(float size){
        float movementBoxWidth = size * ((GameImpl) game).getGameContainer().getAspectRatio();
        float movementBoxHeight = size;
        return new Rectangle((gameContainer.getWidth()/2) - (movementBoxWidth/2), (gameContainer.getHeight()/2) - (movementBoxHeight/2), movementBoxWidth, movementBoxHeight );
    }


    public void setMoveRight(boolean right){
        this.right = right;
    }

    public void setMoveLeft(boolean left){
        this.left = left;
    }

    public void setMoveUp(boolean up){
        this.up = up;
    }

    public void setMoveDown(boolean down){
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
        move(delta);
    }

    private void move(int delta) {
        float x = 0, y = 0;
        Vector2f v1, v2;
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

        if(intersectsMovementBoxLeftOrRight() && intersectsMovementBoxTopOrBottom()){
            setChanged();
            notifyObservers(velocity);
        }else{
            //splitting velocity vector
            v1 = new Vector2f(velocity.getX(), 0);
            v2 = new Vector2f(0, velocity.getY());
            if(intersectsMovementBoxLeftOrRight()){
                setChanged();
                notifyObservers(v1);
                position.add(v2);
            }else if(intersectsMovementBoxTopOrBottom()){
                setChanged();
                notifyObservers(v2);
                position.add(v1);
            }else{
                position.add(velocity);
            }
        }




    }

    private boolean intersectsMovementBoxLeftOrRight(){
        float x1 = movementBox.getX();
        float y1 = movementBox.getY();
        float x2 = movementBox.getX() + movementBox.getWidth();
        float y2 = movementBox.getY() + movementBox.getHeight();

        if(((position.getX() <= x1) && isMovingLeft()) || ((position.getX() >= x2) && isMovingRight())){
            return true;
        }
        return false;

    }

    private boolean intersectsMovementBoxTopOrBottom(){
        float x1 = movementBox.getX();
        float y1 = movementBox.getY();
        float x2 = movementBox.getX() + movementBox.getWidth();
        float y2 = movementBox.getY() + movementBox.getHeight();


        if(((position.getY() <= y1) && isMovingUp()) || ((position.getY() >= y2) && isMovingDown())){
            return true;
        }
        return false;

    }

    private boolean isMovingUp(){
        double angle = velocity.getTheta();
        if(angle > 180 && angle < 360) return true;
        return false;
    }

    private boolean isMovingRight(){
        double angle = velocity.getTheta();
        if(angle >= 0 && angle < 90) return true;
        if(angle <= 360 && angle > 270) return true;
        return false;
    }

    private boolean isMovingDown(){
        double angle = velocity.getTheta();
        if(angle > 0 && angle < 180) return true;
        return false;
    }

    private boolean isMovingLeft(){
        double angle = velocity.getTheta();
        if(angle < 270 && angle > 90) return true;
        return false;
    }

    private Vector2f slowdown(Vector2f vel) {
        Vector2f velocity = vel;
        velocity.scale(0.90f);
        //if character is moving slower than threshold, halt
        if (velocity.length() < moveThreshold) {
            velocity.set(0, 0);
        }
        return velocity;
    }
}
