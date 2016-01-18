package generator.standard;

import java.util.ArrayList;
import java.util.HashMap;

public class Maze implements Region {

    private int x1, y1;
    private ArrayList<Coordinates> tiles;

    public Maze(int initX, int initY) {
        this.x1 = initX;
        this.y1 = initY;
        this.tiles = new ArrayList<>();
    }

    @Override
    public void addTile(Coordinates coords) {
        tiles.add(coords);
    }

    @Override
    public ArrayList<Coordinates> getTiles() {
        return tiles;
    }


    public int getX1(){
        return x1;
    }

    public int getY1(){
        return y1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Maze maze = (Maze) o;

        if (x1 != maze.x1) return false;
        return y1 == maze.y1;

    }

    @Override
    public int hashCode() {
        int result = x1;
        result = 31 * result + y1;
        return result;
    }

    @Override
    public String toString(){
        return "Maze: " + x1 + "," + y1;
    }
}
