package view;

import model.Game;
import model.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class GraphicsHandler {

    private Game game;
    private Image playerImage;

    public GraphicsHandler(Game game) throws SlickException {
        this.game = game;
        initPlayer();
    }

    private void initPlayer() throws SlickException {
        Player player = game.getPlayer();
        playerImage = new PlayerImage(player);
    }

    public void render(Graphics graphics) throws SlickException {
        playerImage.draw();
    }
}
