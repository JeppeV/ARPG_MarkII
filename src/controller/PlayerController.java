package controller;

import model.Game;
import model.Player;
import org.newdawn.slick.Input;


/**
 * Created by Jeppe Vinberg on 15-01-2016.
 * <p>
 * This class is the controller accepting input events relevant for the Player entity.
 */
public class PlayerController extends AbstractController {

    private Game game;
    private Player player;

    public PlayerController(Game game) {
        this.game = game;
        this.player = game.getPlayer();
    }

    @Override
    public void keyPressed(int i, char c) {
        switch (i) {
            case Input.KEY_W:
                player.toggleUpMovement();
                break;
            case Input.KEY_D:
                player.toggleRightMovement();
                break;
            case Input.KEY_S:
                player.toggleDownMovement();
                break;
            case Input.KEY_A:
                player.toggleLeftMovement();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(int i, char c) {
        switch (i) {
            case Input.KEY_W:
                player.toggleUpMovement();
                break;
            case Input.KEY_D:
                player.toggleRightMovement();
                break;
            case Input.KEY_S:
                player.toggleDownMovement();
                break;
            case Input.KEY_A:
                player.toggleLeftMovement();
                break;
            default:
                break;
        }
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
    public void mouseReleased(int i, int i1, int i2) {

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