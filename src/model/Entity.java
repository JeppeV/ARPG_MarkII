package model;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public interface Entity {

    float getX();

    float getY();

    int getWidth();

    int getHeight();

    void update(GameContainer gameContainer, int delta);

    Rectangle getCollisionRectangle();
}
