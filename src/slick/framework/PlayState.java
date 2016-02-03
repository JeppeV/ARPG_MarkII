package slick.framework;

import controller.DevelopmentController;
import controller.PlayerController;
import model.Game;
import model.GameImpl;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import view.GraphicsHandler;

import java.util.ArrayList;

/**
 * Created by Jeppe Vinberg on 03-02-2016.
 */
public class PlayState extends BasicGameState {

    private int id;
    private Game game;
    private GraphicsHandler graphicsHandler;
    private ArrayList<InputListener> listeners;

    public PlayState(int id) {
        this.id = id;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        game = new GameImpl(gameContainer);
        listeners.add(new PlayerController(game));
        listeners.add(new DevelopmentController(game));
        Input input = gameContainer.getInput();
        listeners.forEach(input::addListener);
        graphicsHandler = new GraphicsHandler(game);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphicsHandler.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        game.update(gameContainer, i);
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame){
        Input input = gameContainer.getInput();
        listeners.forEach(input::removeListener);

    }

    @Override
    public int getID() {
        return id;
    }
}
