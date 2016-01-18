package generator.standard;

import java.util.ArrayList;

public class Room implements Region {

    private int width, height;
    private ArrayList<Coordinates> tiles;
    private int x1, y1, x2, y2;

    public Room(int x1, int y1, int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new ArrayList<>();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x1 + width;
        this.y2 = y1 + height;
    }

    @Override
    public void addTile(Coordinates coords){
        tiles.add(coords);
    }

    @Override
    public ArrayList<Coordinates> getTiles(){
        return tiles;
    }

    /**
     * @param other   the room to check if this room overlaps
     * @param padding the padding to include when checking for overlapping
     * @return whether this room overlaps the other room
     */
    public boolean overlaps(Room other, int padding) {
        return (this.x1 - padding < other.x2) && (this.x2 + padding > other.x1) &&
                (this.y1 - padding < other.y2) && (this.y2 + padding > other.y1);
    }

    /**
     * @param other the room to check if this room overlaps
     * @return whether this room overlaps the other room
     */
    public boolean overlaps(Room other) {
        return (this.x1 < other.x2) && (this.x2 > other.x1) &&
                (this.y1 < other.y2) && (this.y2 > other.y1);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    @Override
    public String toString() {
        return "Room " + x1 + "," + y1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (x1 != room.x1) return false;
        return y1 == room.y1;
    }

    @Override
    public int hashCode() {
        int result = x1;
        result = 31 * result + y1;
        return result;
    }
}
