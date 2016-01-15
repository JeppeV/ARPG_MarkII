package model;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class GameImpl implements Game {

    private Player player;

    public GameImpl() {
        this.player = new Player(100, 100, 45, 45);
    }

    @Override
    public Player getPlayer() {
        return player;
    }


}
