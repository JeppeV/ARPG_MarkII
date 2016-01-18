package generator.generators;

import generator.standard.Constants;
import generator.standard.Dungeon;
import generator.standard.Map;

public class DungeonGenerator {

    private RoomGenerator roomGenerator;
    private MazeGenerator mazeGenerator;
    private ConnectionGenerator connectionGenerator;

    public DungeonGenerator() {
        this.roomGenerator = new RoomGenerator();
        this.mazeGenerator = new MazeGenerator();
        this.connectionGenerator = new ConnectionGenerator();
    }

    public Map generateDungeon(int width, int height) {
        Dungeon dungeon = new Dungeon(width, height);
        dungeon = fillDungeonWith(dungeon, Constants.WALL);
        dungeon = fillVisitedWith(dungeon, false);
        dungeon = roomGenerator.generateRooms(dungeon);
        dungeon = mazeGenerator.generateMazes(dungeon);
        dungeon = connectionGenerator.generateConnections(dungeon);
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
