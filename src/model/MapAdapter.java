package model;

import generator.standard.Constants;
import generator.standard.Dungeon;
import generator.standard.Map;
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

    @Override
    public int getHeightInTiles() {
        return map.getHeightInTiles();
    }

    @Override
    public void pathFinderVisited(int i, int i1) {
        pathFinderVisited[i][i1] = true;
    }

    @Override
    public boolean blocked(PathFindingContext pathFindingContext, int i, int i1) {
        char t = map.getTile(i, i1);
        return t != Constants.FLOOR;
    }

    @Override
    public float getCost(PathFindingContext pathFindingContext, int i, int i1) {
        return 1;
    }
}