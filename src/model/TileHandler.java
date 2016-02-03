package model;

import generator.standard.Map;
import generator.standard.TileType;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public class TileHandler implements Observer {

    private TileImpl[][] tiles;
    private float xOffset, yOffset;

    public TileHandler(Map map) {
        //temporary initial values for offset
        this.xOffset = 0.0f;
        this.yOffset = 0.0f;
        this.tiles = initTiles(map);
    }

    private TileImpl[][] initTiles(Map map) {
        TileImpl[][] tiles = new TileImpl[map.getWidthInTiles()][map.getHeightInTiles()];
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = new TileImpl(x * Tile.WIDTH, y * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT, map.getTile(x, y));
            }

        }
        return tiles;
    }


    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    private void changeTilesOffset(float xOffset, float yOffset) {
        TileImpl tile;
        this.xOffset += xOffset;
        this.yOffset += yOffset;
        //System.out.println("xOffset: " + this.xOffset + ", yOffset: " + this.yOffset);
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tile = tiles[x][y];
                tile.setX(tile.getX() - xOffset);
                tile.setY(tile.getY() - yOffset);
            }

        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Vector2f v = (Vector2f) arg;
        float x = v.getX();
        float y = v.getY();
        if (!(x == 0f && y == 0f)) {
            changeTilesOffset(x, y);
        }
    }
}
