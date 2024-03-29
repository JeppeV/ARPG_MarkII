package model.tiles;

import org.newdawn.slick.geom.Rectangle;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public class TileImpl extends Rectangle implements Tile {

    private char id;

    public TileImpl(float x, float y, float width, float height, char id) {
        super(x, y, width, height);
        this.id = id;
    }

    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }

    @Override
    public float getWidth() {
        return super.getWidth();
    }

    @Override
    public float getHeight() {
        return super.getHeight();
    }

    @Override
    public String toString() {
        return "Tile(" + (int) getX() + "," + (int) getY() + ")";
    }

    @Override
    public char getID() {
        return id;
    }
}
