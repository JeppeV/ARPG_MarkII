package model;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public interface Tile {

    int WIDTH = 160;
    int HEIGHT = 160;

    float getX();

    float getY();

    float getWidth();

    float getHeight();

    char getID();
}
