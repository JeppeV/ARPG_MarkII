package model;


import org.newdawn.slick.geom.Vector2f;

import java.util.Observable;

/**
 * Created by Jeppe Vinberg on 04-02-2016.
 */
public class OffsetHandler extends Observable {

    private float xOffset;
    private float yOffset;

    public OffsetHandler() {
        this.xOffset = 0.0f;
        this.yOffset = 0.0f;
    }

    public void changeOffset(Vector2f offset) {
        float xOffset = offset.getX();
        float yOffset = offset.getY();
        this.xOffset += xOffset;
        this.yOffset += yOffset;
        setChanged();
        notifyObservers(new Vector2f(xOffset, yOffset));
    }

    public void changeOffset(float xOffset, float yOffset) {
        this.xOffset += xOffset;
        this.yOffset += yOffset;
        setChanged();
        notifyObservers(new Vector2f(xOffset, yOffset));
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }


}
