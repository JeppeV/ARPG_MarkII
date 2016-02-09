package slick.framework;

import controller.MenuController;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Created by Jeppe Vinberg on 03-02-2016.
 */
public class MenuState extends BasicGameState {

    private int id;
    private Image playButton;
    private Rectangle buttonRect;
    private int playButtonX, playButtonY;
    private InputListener menuController;

    public MenuState(int id) {
        this.id = id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        playButton = new Image("res/play_button.png");
        playButtonX = gameContainer.getWidth() / 2 - playButton.getWidth() / 2;
        playButtonY = gameContainer.getHeight() / 2 - playButton.getHeight() / 2;
        buttonRect = new Rectangle(playButtonX, playButtonY, playButton.getWidth(), playButton.getHeight());
        Input input = gameContainer.getInput();
        menuController = new MenuController(stateBasedGame, buttonRect);
        input.addListener(menuController);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        playButton.draw(playButtonX, playButtonY);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Input input = gameContainer.getInput();
        input.removeListener(menuController);
    }
}
