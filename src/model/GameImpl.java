package model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

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

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        player.update(gameContainer, delta);
    }


}
