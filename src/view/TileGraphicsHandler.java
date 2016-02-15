package view;

import generator.standard.TileType;
import model.facade.Game;
import model.facade.Tile;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public class TileGraphicsHandler {

    private Game game;
    private TileImage[][] tileImages;

    public TileGraphicsHandler(Game game) throws SlickException {
        this.game = game;
        tileImages = initTileImages(game.getTiles());
    }

    private TileImage[][] initTileImages(Tile[][] tiles) throws SlickException {
        TileImage[][] tileImages = new TileImage[tiles.length][tiles[0].length];
        Tile tile;
        String ref;
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tile = tiles[x][y];
                ref = "res/";
                if (tile.getID() == TileType.FLOOR) {
                    ref = ref + "floor0.png";
                } else {
                    ref = ref + "wall0.png";
                }
                tileImages[x][y] = new TileImage(ref, tile);
            }

        }

        return tileImages;
    }

    public void render() {
        TileImage t;
        for (int x = 0; x < tileImages.length; x++) {
            for (int y = 0; y < tileImages[0].length; y++) {
                t = tileImages[x][y];
                if (t.getBounds().intersects(game.getCameraBounds())) t.draw();
            }
        }
    }
}
