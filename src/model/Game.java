package model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public interface Game {

    public Player getPlayer();

    public void update(GameContainer gameContainer, int delta)throws SlickException;
}
