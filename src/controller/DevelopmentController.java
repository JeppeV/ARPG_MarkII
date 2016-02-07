package controller;

import model.entities.Player;
import model.facade.Game;
import org.newdawn.slick.Input;

/**
 * Created by Jeppe Vinberg on 02-02-2016.
 */
public class DevelopmentController extends AbstractController {

    private Game game;
    private Player player;

    public DevelopmentController(Game game) {
        this.game = game;
        this.player = game.getPlayer();
    }

    @Override
    public void keyPressed(int i, char c) {

    }

    @Override
    public void keyReleased(int i, char c) {
        switch (i) {
            case Input.KEY_C:
                player.toggleCollisionCheck();
                System.out.println("Collision checking toggled");
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
