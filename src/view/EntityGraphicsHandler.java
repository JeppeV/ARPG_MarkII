package view;

import model.Entity;
import model.EntityID;
import model.Game;
import org.newdawn.slick.SlickException;

import java.util.LinkedList;

/**
 * Created by Jeppe Vinberg on 05-02-2016.
 */
public class EntityGraphicsHandler {

    private Game game;
    private LinkedList<EntityImage> entityImages;

    public EntityGraphicsHandler(Game game) throws SlickException {
        this.game = game;
        this.entityImages = initEntityImages(game.getEntities());
    }

    private LinkedList<EntityImage> initEntityImages(LinkedList<Entity> entities) throws SlickException {
        LinkedList<EntityImage> entityImages = new LinkedList<>();
        String ref;
        for (Entity e : entities) {
            ref = "res/";
            if (e.getID() == EntityID.PLAYER) {
                //player
                ref = ref + "player.png";
                entityImages.add(new EntityImage(ref, e));
            }
            //... other cases
        }
        return entityImages;
    }

    public void render() {
        entityImages.forEach(EntityImage::draw);
    }
}
