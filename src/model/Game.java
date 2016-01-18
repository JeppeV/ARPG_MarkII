package model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public interface Game {

    Entity getPlayer();

    Tile[][] getTiles();

    void update(GameContainer gameContainer, int delta) throws SlickException;
}
