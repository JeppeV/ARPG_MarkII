package model.entities;

import model.GameImpl;
import model.OffsetHandler;
import model.facade.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Jeppe Vinberg on 05-02-2016.
 */
public class GruntEnemy implements Enemy, Entity {

    private GameContainer gameContainer;
    private Vector2f velocity, position;
    private int width, height;
    private float moveSpeed, maxSpeed, moveThreshold, moveSlowndownFactor;

    public GruntEnemy(int x, int y, GameImpl game){
        this.gameContainer = game.getGameContainer();
        this.velocity = new Vector2f(0, 0);
        this.position = initPosition(x, y, game.getOffsetHandler());
        this.width = 30;
        this.height = 30;
        this.moveSpeed = 0.2f;
        this.maxSpeed = 5;
        this.moveThreshold = 0.0001f;
        this.moveSlowndownFactor = 0.75f;
    }

    private Vector2f initPosition(int x, int y, OffsetHandler offsetHandler){
        return new Vector2f(x - offsetHandler.getXOffset(), y - offsetHandler.getYOffset());
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
    public void setX(float x) {
        position.set(x, position.getY());
    }

    @Override
    public void setY(float y) {
        position.set(position.getX(), y);
    }

    @Override
    public void addForce(Vector2f force) {
        position.add(force);
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
    public void update(GameContainer gameContainer, int delta) {
    }

    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
