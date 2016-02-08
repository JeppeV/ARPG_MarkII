package model.facade;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public interface Entity {

    float getX();

    float getY();

    void setX(float x);

    void setY(float y);

    Vector2f getCenterPosition();

    Vector2f getGlobalCenterPosition();

    float getRotation();

    void addForce(Vector2f force);

    int getID();

    int getWidth();

    int getHeight();

    void update(GameContainer gameContainer, int delta);

    Rectangle getCollisionBox();
}
