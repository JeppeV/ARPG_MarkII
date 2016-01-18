package generator.generators;

import generator.standard.Constants;
import generator.standard.Coordinates;
import generator.standard.Dungeon;
import generator.standard.Room;

import java.util.Random;

public class RoomGenerator {

    private int NO_ATTEMPTS;
    private int numberOfRooms;
    private int minRoomSize, maxRoomSize;

    public RoomGenerator() {
        this.NO_ATTEMPTS = 20;
        this.numberOfRooms = 30;
        this.minRoomSize = 3;
        this.maxRoomSize = 7;

    }



    public Dungeon generateRooms(Dungeon dungeon) {
        Dungeon d = dungeon;
        boolean overlapsExisting;
        int x1, y1, roomWidth, roomHeight;
        int attempts;
        int roomsAdded = 0;
        int maxX = dungeon.getWidthInTiles() - maxRoomSize;
        int maxY = dungeon.getHeightInTiles() - maxRoomSize;
        Random random = new Random();
        Room room;


        for (int i = 0; i < numberOfRooms; i++) {
            attempts = NO_ATTEMPTS;
            while (attempts > 0) {
                x1 = random.nextInt(maxX) + Constants.BORDER;
                y1 = random.nextInt(maxY) + Constants.BORDER;
                roomWidth = minRoomSize + random.nextInt(maxRoomSize - minRoomSize) - Constants.BORDER;
                roomHeight = minRoomSize + random.nextInt(maxRoomSize - minRoomSize) - Constants.BORDER;
                room = new Room(x1, y1, roomWidth, roomHeight);
                overlapsExisting = false;
                for (Room r : dungeon.rooms()) {
                    overlapsExisting = room.overlaps(r, 1);
                    if (overlapsExisting) {
                        attempts--;
                        break;
                    }
                }
                if (!overlapsExisting) {
                    d = addRoom(d, room);
                    roomsAdded++;
                    break;
                }
            }

        }
        System.out.println("RoomGenerator succesfully created " + roomsAdded + "/" + numberOfRooms + " rooms, using " + NO_ATTEMPTS + " attempts each.");
        return d;
    }

    private Dungeon addRoom(Dungeon dungeon, Room room) {
        int x1 = room.getX1();
        int y1 = room.getY1();
        for (int x = 0; x < room.getWidth(); x++) {
            for (int y = 0; y < room.getHeight(); y++) {
                dungeon.setTile(x1 + x, y1 + y, Constants.FLOOR);
                dungeon.setVisited(x1 + x, y1 + y, true);
                room.addTile(new Coordinates(x1 + x, y1 + y));

            }
        }
        dungeon.addRoom(room);
        return dungeon;
    }

}
