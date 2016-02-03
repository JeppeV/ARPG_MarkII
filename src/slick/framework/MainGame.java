package slick.framework;

import controller.DevelopmentController;
import controller.PlayerController;
import model.Game;
import model.GameImpl;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import view.GraphicsHandler;

/**
 * Created by Jeppe Vinberg on 14-01-2016.
 */
public class MainGame extends StateBasedGame {

    private Game game;
    private GraphicsHandler graphicsHandler;

    public static final int menuStateID = 0;
    public static final int playStateID = 1;

    public MainGame() {
        super("ARPG_MarkII");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MenuState(menuStateID));
        this.addState(new PlayState(playStateID));
    }


}
