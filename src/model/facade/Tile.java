package model.facade;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public interface Tile {

    int SIZE = 120;

    float getX();

    float getY();

    float getWidth();

    float getHeight();

    char getID();
}
