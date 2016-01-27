package generator.standard;

import java.util.ArrayList;
import java.util.HashMap;

public class Dungeon implements Map {
    private int width, height;
    private char[][] dungeon;
    private boolean[][] visited;
    private ArrayList<Room> rooms;
    private ArrayList<Maze> mazes;
    private ArrayList<Region> regions;
    private HashMap<Coordinates,Region> regionsMapping;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.dungeon = new char[width][height];
        this.visited = new boolean[width][height];
        this.rooms = new ArrayList<>();
        this.mazes = new ArrayList<>();
        this.regions = new ArrayList<>();
        this.regionsMapping = new HashMap<>();
    }

    public void addMaze(Maze maze) {
        for(Coordinates c : maze.getTiles()){
            regionsMapping.put(c, maze);
        }
        mazes.add(maze);
        regions.add(maze);
    }

    public ArrayList<Maze> mazes(){
        return mazes;
    }

    public void addRoom(Room room) {
        for(Coordinates c : room.getTiles()){
            regionsMapping.put(c, room);
        }
        rooms.add(room);
        regions.add(room);
    }

    public ArrayList<Room> rooms() {
        return rooms;
    }

    public ArrayList<Region> getRegions(){
        return regions;
    }

    public Region getRegionAt(Coordinates coords) {
        return regionsMapping.get(coords);
    }

    public void setVisited(int x, int y, boolean b) {
        visited[x][y] = b;
    }

    public void setVisited(Coordinates coords, boolean b){
        int x = coords.getX();
        int y = coords.getY();
        visited[x][y] = b;
    }

    public boolean getVisited(int x, int y) {
        return visited[x][y];
    }

    public boolean getVisited(Coordinates coords){
        int x = coords.getX();
        int y = coords.getY();
        return visited[x][y];
    }

    public void setTile(int x, int y, char c) {
        dungeon[x][y] = c;
    }

    public void setTile(Coordinates coords, char c){
        int x = coords.getX();
        int y = coords.getY();
        dungeon[x][y] = c;
    }

    @Override
    public char getTile(int x, int y) {
        return dungeon[x][y];
    }

    @Override
    public char getTile(Coordinates coords){
        int x = coords.getX();
        int y = coords.getY();
        return dungeon[x][y];
    }

    @Override
    public int getWidthInTiles() {
        return width;
    }

    @Override
    public int getHeightInTiles() {
        return height;
    }

    @Override
    public String toString() {
        String string = "";
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                string = string + getTile(x, y) + " ";
            }
            string = string + "\n";
        }

        return string;
    }
}
