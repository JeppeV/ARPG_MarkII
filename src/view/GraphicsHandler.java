package view;

import model.Entity;
import model.Game;
import model.GameImpl;
import model.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class GraphicsHandler {

    private Game game;
    private Image playerImage;
    private TileGraphicsHandler tileGraphicsHandler;

    public GraphicsHandler(Game game) throws SlickException {
        this.game = game;
        this.tileGraphicsHandler = new TileGraphicsHandler(game.getTiles());
        initPlayer();
    }

    private void initPlayer() throws SlickException {
        Entity player = game.getPlayer();
        playerImage = new PlayerImage(player);
    }

    public void render(Graphics graphics) throws SlickException {
        tileGraphicsHandler.render();
        playerImage.draw();

    }
}
