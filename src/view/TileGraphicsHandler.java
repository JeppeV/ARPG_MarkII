package view;

import generator.standard.Constants;
import model.Tile;
import model.TileImpl;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public class TileGraphicsHandler {

    private TileImage[][] tileImages;

    public TileGraphicsHandler(Tile[][] tiles) throws SlickException{
        tileImages = initTileImages(tiles);
    }

    private TileImage[][] initTileImages(Tile[][] tiles) throws SlickException {
        TileImage[][] tileImages = new TileImage[tiles.length][tiles[0].length];
        Tile tile;
        String ref;
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[0].length; y++){
                tile = tiles[x][y];
                ref = "res/";
                if(tile.getID() == Constants.FLOOR){
                    ref = ref + "floor0.png";
                }else{
                    ref = ref + "wall0.png";
                }
                tileImages[x][y] = new TileImage(ref, tile);
            }

        }

        return tileImages;
    }

    public void render(){
        for(int x = 0; x < tileImages.length; x++) {
            for (int y = 0; y < tileImages[0].length; y++) {
                tileImages[x][y].draw();
            }
        }
    }
}
