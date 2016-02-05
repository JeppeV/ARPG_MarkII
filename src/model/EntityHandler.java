package model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jeppe Vinberg on 04-02-2016.
 */
public class EntityHandler implements Observer {

    private LinkedList<Entity> entities;

    public EntityHandler() {
        this.entities = new LinkedList<>();
    }

    public void add(Entity entity) {
        entities.add(entity);
    }

    public void remove(Entity entity) {
        entities.remove(entity);
    }

    public LinkedList<Entity> getEntities() {
        return entities;
    }

    private void changeEntitiesOffset(float x, float y) {
        //update offset of all entities, except player, identified by an ID of 0
        entities.stream().filter(e -> e.getID() != EntityID.PLAYER).forEach(e -> {
            e.setX(e.getX() - x);
            e.setY(e.getY() - y);
        });
    }

    public void update(GameContainer gameContainer, int delta) throws SlickException {
        for (Entity e : entities) {
            e.update(gameContainer, delta);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Vector2f v = (Vector2f) arg;
        if (v.length() != 0) {
            changeEntitiesOffset(v.getX(), v.getY());
        }
    }


}
