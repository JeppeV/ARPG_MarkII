package model.facade;

import model.entities.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.util.LinkedList;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public interface Game {

    void update(GameContainer gameContainer, int delta) throws SlickException;

    Rectangle getCameraBounds();

    Player getPlayer();

    Tile[][] getTiles();

    LinkedList<Entity> getEntities();

}
