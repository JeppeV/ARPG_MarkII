package model.entities;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public interface Entity {


    /**
     * This method is used to update this entity's logic
     *
     * @param gameContainer the game container context object
     * @param delta         time elapsed since the last game loop update
     */
    void update(GameContainer gameContainer, int delta);

    /**
     * @return the x value of this entity in the context of the game container
     */
    float getX();

    /**
     * @param x the new x value of this entity in the context of the game container
     */
    void setX(float x);

    /**
     * @return the y value of this entity in the context of the game container
     */
    float getY();

    /**
     * @param y the new y value of this entity in the context of the game container
     */
    void setY(float y);

    /**
     * @return the center position of this entity in the context of the game container
     */
    Vector2f getCenterPosition();

    /**
     * @return the current angle of this entity
     */
    float getRotation();

    /**
     * @return the ID identifying the type of this entity
     */
    int getID();

    /**
     * @return the width of this entity
     */
    int getWidth();

    /**
     * @return the height of this entity
     */
    int getHeight();

    /**
     * @return the bounding rectangle of this entity used for collision detection
     */
    Rectangle getCollisionBox();

}
