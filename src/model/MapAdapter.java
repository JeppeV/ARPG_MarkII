package model;

import generator.standard.Map;
import generator.standard.TileType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

/**
 * Created by Jeppe Vinberg on 17-01-2016.
 */
public class MapAdapter implements TileBasedMap {

    private Map map;
    private boolean[][] pathFinderVisited;

    public MapAdapter(Map map) {
        this.map = map;
        this.pathFinderVisited = new boolean[map.getWidthInTiles()][map.getHeightInTiles()];
    }

    public void update(GameContainer gameContainer, int delta) {

    }

    @Override
    public int getWidthInTiles() {
        return map.getWidthInTiles();
    }

    public int getWidthInPixels(){
        return map.getWidthInTiles() * Tile.WIDTH;
    }

    @Override
    public int getHeightInTiles() {
        return map.getHeightInTiles();
    }

    public int getHeightInPixels(){
        return map.getHeightInTiles() * Tile.HEIGHT;
    }

    @Override
    public void pathFinderVisited(int i, int i1) {
        pathFinderVisited[i][i1] = true;
    }

    @Override
    public boolean blocked(PathFindingContext pathFindingContext, int i, int i1) {
        char t = map.getTile(i, i1);
        return t != TileType.FLOOR;
    }

    @Override
    public float getCost(PathFindingContext pathFindingContext, int i, int i1) {
        return 1;
    }
}
