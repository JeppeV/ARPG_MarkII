package model;

import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Jeppe Vinberg on 21-01-2016.
 */
public abstract class Movement {

    public static boolean isMovingUp(Vector2f velocity){
        double angle = velocity.getTheta();
        if(angle > 180 && angle < 360) return true;
        return false;
    }

    public static boolean isMovingRight(Vector2f velocity){
        double angle = velocity.getTheta();
        if(angle >= 0 && angle < 90) return true;
        if(angle <= 360 && angle > 270) return true;
        return false;
    }

    public static boolean isMovingDown(Vector2f velocity){
        double angle = velocity.getTheta();
        if(angle > 0 && angle < 180) return true;
        return false;
    }

    public static boolean isMovingLeft(Vector2f velocity){
        double angle = velocity.getTheta();
        if(angle < 270 && angle > 90) return true;
        return false;
    }

}
