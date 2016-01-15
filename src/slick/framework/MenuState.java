package slick.framework;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jeppe Vinberg on 14-01-2016.
 */
public class MenuState extends BasicGameState {

    private int id;
    private Image playButton;
    private int gameContainerCenterX, gameContainerCenterY;

    public MenuState(int id) {
        this.id = id;
        this.playButton = null;

    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        playButton = new Image("res/play_button.png");
        gameContainerCenterX = gameContainer.getWidth() / 2;
        gameContainerCenterY = gameContainer.getHeight() / 2;


    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        playButton.drawCentered(gameContainerCenterX, gameContainerCenterY);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }
}
