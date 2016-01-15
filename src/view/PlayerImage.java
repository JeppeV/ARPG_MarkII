package view;


import model.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class PlayerImage extends Image {

    private Entity player;

    public PlayerImage(Entity player) throws SlickException {
        super("res/player.png");
        this.player = player;
    }

    @Override
    public void draw() {
        super.draw(player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }
}
