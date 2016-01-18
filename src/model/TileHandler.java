package model;

import generator.standard.*;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public class TileHandler {

    private TileImpl[][] tiles;

    public TileHandler(Map map){
        this.tiles = initTiles(map);
    }

    private TileImpl[][] initTiles(Map map){
        TileImpl[][] tiles = new TileImpl[map.getWidthInTiles()][map.getHeightInTiles()];
        int id;
        for(int x = 0; x < map.getWidthInTiles(); x++){
            for(int y = 0; y < map.getHeightInTiles(); y++){
                tiles[x][y] = new TileImpl(x * Tile.WIDTH, y * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT, map.getTile(x, y));
            }

        }
        return tiles;
    }

    public Tile[][] getTiles(){
        return tiles;
    }
}
