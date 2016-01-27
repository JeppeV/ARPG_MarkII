package generator.generators.dungeon;

import generator.standard.MapGenerator;
import generator.standard.TileType;
import generator.standard.Dungeon;
import generator.standard.Map;

public class DungeonGenerator implements MapGenerator {

    private RoomGenerator roomGenerator;
    private MazeGenerator mazeGenerator;
    private ConnectionGenerator connectionGenerator;
    private DeadEndFiller deadEndFiller;

    public DungeonGenerator() {
        this.roomGenerator = new RoomGenerator();
        this.mazeGenerator = new MazeGenerator();
        this.connectionGenerator = new ConnectionGenerator();
        this.deadEndFiller = new DeadEndFiller();
    }

    @Override
    public Map generateMap(int width, int height) {
        Dungeon dungeon = new Dungeon(width, height);
        dungeon = fillDungeonWith(dungeon, TileType.WALL);
        dungeon = fillVisitedWith(dungeon, false);
        dungeon = roomGenerator.generateRooms(dungeon);
        dungeon = mazeGenerator.generateMazes(dungeon);
        dungeon = connectionGenerator.generateConnections(dungeon);
        dungeon = deadEndFiller.fillDeadEnds(dungeon);

        return dungeon;
    }

    private Dungeon fillDungeonWith(Dungeon dungeon, char c) {
        for (int x = 0; x < dungeon.getWidthInTiles(); x++) {
            for (int y = 0; y < dungeon.getHeightInTiles(); y++) {
                dungeon.setTile(x, y, c);
            }
        }
        return dungeon;
    }

    private Dungeon fillVisitedWith(Dungeon dungeon, boolean b) {
        for (int x = 0; x < dungeon.getWidthInTiles(); x++) {
            for (int y = 0; y < dungeon.getHeightInTiles(); y++) {
                dungeon.setVisited(x, y, b);
            }
        }
        return dungeon;
    }





}
