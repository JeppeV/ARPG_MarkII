package model;

import org.lwjgl.util.vector.Vector2f;

import java.util.Observable;

/**
 * Created by Jeppe Vinberg on 04-02-2016.
 */
public class OffsetHandler extends Observable {

    private float xOffset;
    private float yOffset;

    public OffsetHandler(){
        this.xOffset = 0f;
        this.yOffset = 0f;
    }

    public void changeOffset(float xOffset, float yOffset){
        this.xOffset += xOffset;
        this.yOffset += yOffset;
        setChanged();
        notifyObservers();
    }

    public float getXOffset(){
        return xOffset;
    }

    public float getyOffset(){
        return yOffset;
    }


}
