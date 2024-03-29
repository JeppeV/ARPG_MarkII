package slick.framework;

import model.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import view.GraphicsHandler;

/**
 * Created by Jeppe Vinberg on 14-01-2016.
 */
public class MainGame extends StateBasedGame {

    public static final int menuStateID = 0;
    public static final int playStateID = 1;
    private Game game;
    private GraphicsHandler graphicsHandler;

    public MainGame() {
        super("ARPG_MarkII");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MenuState(menuStateID));
        this.addState(new PlayState(playStateID));
    }


}
