package slick.framework;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jeppe Vinberg on 14-01-2016.
 */
public class Main extends StateBasedGame {

    public static final int menuStateID = 0;
    public static final int playStateID = 1;

    public Main() {
        super("ARPG_MarkII");
        this.addState(new MenuState(menuStateID));
        this.addState(new PlayState(playStateID));
        this.enterState(playStateID);


    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
    }

}
