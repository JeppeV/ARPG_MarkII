package view;

import model.facade.Game;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class GraphicsHandler {

    private TileGraphicsHandler tileGraphicsHandler;
    private EntityGraphicsHandler entityGraphicsHandler;

    public GraphicsHandler(Game game) throws SlickException {
        this.tileGraphicsHandler = new TileGraphicsHandler(game.getTiles());
        this.entityGraphicsHandler = new EntityGraphicsHandler(game);
    }


    public void render(Graphics graphics) throws SlickException {
        tileGraphicsHandler.render();
        entityGraphicsHandler.render();

    }
}
