package slick.framework;

import controller.PlayerController;
import model.Game;
import model.GameImpl;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import view.GraphicsHandler;

/**
 * Created by Jeppe Vinberg on 14-01-2016.
 */
public class PlayState extends BasicGameState {

    private int id;
    private Game game;
    private GraphicsHandler graphicsHandler;


    public PlayState(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        game = new GameImpl();
        Input input = gameContainer.getInput();
        input.addListener(new PlayerController(game));
        graphicsHandler = new GraphicsHandler(game);

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphicsHandler.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        //System.out.println("delta: " + i);


        game.update(gameContainer, i);
    }

}
