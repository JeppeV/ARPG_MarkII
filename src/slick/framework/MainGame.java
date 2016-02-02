package slick.framework;

import controller.DevelopmentController;
import controller.PlayerController;
import model.Game;
import model.GameImpl;
import org.newdawn.slick.*;
import view.GraphicsHandler;

/**
 * Created by Jeppe Vinberg on 14-01-2016.
 */
public class MainGame extends BasicGame {

    private int id;
    private Game game;
    private GraphicsHandler graphicsHandler;

    public MainGame() {
        super("ARPG_MarkII");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        game = new GameImpl(gameContainer);
        Input input = gameContainer.getInput();
        input.addListener(new PlayerController(game));
        input.addListener(new DevelopmentController(game));
        graphicsHandler = new GraphicsHandler(game);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        game.update(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphicsHandler.render(graphics);
    }
}
