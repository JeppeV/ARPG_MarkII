package view;

import model.tiles.Tile;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

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

    public Rectangle getBounds() {
        return new Rectangle(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
    }
}
