package generator.generators.dungeon;

import generator.standard.TileType;
import generator.standard.Coordinates;
import generator.standard.Dungeon;

/**
 * Created by Jeppe Vinberg on 27-01-2016.
 */
public class DeadEndFiller {

    public Dungeon fillDeadEnds(Dungeon dungeon){
        Coordinates tile = findNextEligibleTile(dungeon);
        while(tile != null){
            dungeon.setTile(tile.getX(), tile.getY(), TileType.WALL);
            tile = findNextEligibleTile(dungeon);
        }
        return dungeon;
    }

    private boolean isEligibleTile(Dungeon dungeon, Coordinates coords) {
        int count = 0;
        if (dungeon.getTile(coords) == TileType.WALL) return false;
        for (Coordinates c : coords.getPrimeNeighbours()) {
            if(dungeon.getTile(c) != TileType.WALL){
                count++;
            }
        }

        return count == 1;
    }

    private Coordinates findNextEligibleTile(Dungeon dungeon){
        Coordinates current;
        for(int x = 0; x < dungeon.getWidthInTiles(); x++){
            for(int y = 0; y < dungeon.getHeightInTiles(); y++){
                current = new Coordinates(x, y);
                if(isEligibleTile(dungeon, current)) return current;

            }
        }
        return null;
    }
}
