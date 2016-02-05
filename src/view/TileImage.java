package view;

import model.facade.Tile;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public class TileImage extends Image {

    private Tile tile;

    public TileImage(String ref, Tile tile) throws SlickException {
        super(ref);
        this.tile = tile;

    }

    @Override
    public void draw() {
        super.draw(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
    }
}
