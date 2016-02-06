package model.facade;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public interface Tile {

    int WIDTH = 20;
    int HEIGHT = 20;

    float getX();

    float getY();

    float getWidth();

    float getHeight();

    char getID();
}
