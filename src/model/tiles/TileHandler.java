package model.tiles;

import generator.standard.Map;
import model.facade.Tile;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public class TileHandler implements Observer {

    private TileImpl[][] tiles;

    public TileHandler(Map map) {
        this.tiles = initTiles(map);
    }

    private TileImpl[][] initTiles(Map map) {
        TileImpl[][] tiles = new TileImpl[map.getWidthInTiles()][map.getHeightInTiles()];
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = new TileImpl(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE, map.getTile(x, y));
            }

        }
        return tiles;
    }

    public int getWidthInTiles() {
        return tiles.length;
    }

    public int getHeightInTiles() {
        return tiles[0].length;
    }

    public int getWidthInPixels() {
        return getWidthInTiles() * Tile.SIZE;
    }

    public int getHeightInPixels() {
        return getHeightInTiles() * Tile.SIZE;
    }

    public TileImpl getTileByPosition(float x, float y) {
        int x0 = (int) x / Tile.SIZE;
        int y0 = (int) y / Tile.SIZE;
        return tiles[x0][y0];
    }

    public ArrayList<TileImpl> getNeighboursByPosition(float x, float y) {
        int x0 = (int) x / Tile.SIZE;
        int y0 = (int) y / Tile.SIZE;
        return getNeighbours(x0, y0);

    }

    public ArrayList<TileImpl> getNeighboursByPosition(Vector2f position) {
        int x0 = (int) position.getX() / Tile.SIZE;
        int y0 = (int) position.getY() / Tile.SIZE;
        return getNeighbours(x0, y0);

    }

    public Vector2f getIndexByPosition(float x, float y) {
        int x0 = (int) x / Tile.SIZE;
        int y0 = (int) y / Tile.SIZE;
        return new Vector2f(x0, y0);
    }

    public TileImpl getTileByIndex(int x, int y) {
        return tiles[x][y];
    }

    public ArrayList<TileImpl> getNeighboursByIndex(int x, int y) {
        return getNeighbours(x, y);
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public ArrayList<TileImpl> getNeighbours(int x, int y, int size, boolean includeCenter) {
        ArrayList<TileImpl> result = new ArrayList<>();
        for (int i = -1 * size; i < size + 1; i++) {
            for (int j = -1 * size; j < size + 1; j++) {
                if ((!includeCenter && (i == 0 && j == 0)) || !isIndexWithinBounds(x + i, y + j)) continue;
                result.add(getTileByIndex(x + i, y + j));

            }
        }
        return result;
    }

    private ArrayList<TileImpl> getNeighbours(int x, int y) {
        ArrayList<TileImpl> result = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((i == 0 && j == 0) || !isIndexWithinBounds(x + i, y + j)) continue;
                result.add(getTileByIndex(x + i, y + j));

            }
        }
        return result;
    }

    private boolean isIndexWithinBounds(int x, int y) {
        return (0 <= x && x < getWidthInTiles()) && (0 <= y && y < getHeightInTiles());
    }

    private void changeTilesOffset(float xOffset, float yOffset) {
        TileImpl tile;
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
        if (v.length() != 0) {
            changeTilesOffset(v.getX(), v.getY());
        }
    }
}
