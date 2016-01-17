package model;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public interface Entity {

    public float getX();

    public float getY();

    public int getWidth();

    public int getHeight();

    public void update(GameContainer gameContainer, int delta);
}
