package controller;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.omg.PortableServer.POAManagerPackage.State;
import slick.framework.MainGame;

/**
 * Created by Jeppe Vinberg on 03-02-2016.
 */
public class MenuController extends AbstractController {

    private StateBasedGame stateBasedGame;
    private Rectangle playButton;

    public MenuController(StateBasedGame stateBasedGame, Rectangle playButton){
        this.stateBasedGame = stateBasedGame;
        this.playButton = playButton;
    }

    @Override
    public void keyPressed(int i, char c) {

    }

    @Override
    public void keyReleased(int i, char c) {

    }

    @Override
    public void mouseWheelMoved(int i) {

    }

    @Override
    public void mouseClicked(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mousePressed(int i, int i1, int i2) {

    }

    @Override
    public void mouseReleased(int i, int x, int y) {
        if(playButton.contains(x, y)){
            stateBasedGame.enterState(MainGame.playStateID);
        }

    }

    @Override
    public void mouseMoved(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {

    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
