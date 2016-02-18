package model;

import model.entities.Entity;
import org.newdawn.slick.util.pathfinding.Path;

/**
 * Created by Jeppe Vinberg on 18-02-2016.
 */
public class PathContext {



    private Path path;
    private int step;
    private Entity target;

    public PathContext(Path path, int step, Entity target){
        this.path = path;
        this.step = step;
        this.target = target;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }
    



}
