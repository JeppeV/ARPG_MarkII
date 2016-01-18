package generator.generators;

import generator.standard.Constants;
import generator.standard.Coordinates;
import generator.standard.Dungeon;
import generator.standard.Maze;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {

    private Random random;
    private boolean[][] border;

    public MazeGenerator() {
        this.random = new Random();
    }

    public Dungeon generateMazes(Dungeon dungeon) {
        Maze maze;
        ArrayList<Maze> mazes = new ArrayList<>();
        init(dungeon);
        Coordinates coords = findNextEligibleInitialTile(dungeon);
        while (coords != null) {
            maze = generateMaze(dungeon, coords.getX(), coords.getY());
            dungeon.addMaze(maze);
            mazes.add(maze);
            coords = findNextEligibleInitialTile(dungeon);
        }
        System.out.println("MazeGenerator succesfully created " + mazes.size() + " mazes.");
        return dungeon;

    }


    private void init(Dungeon dungeon) {
        // initialize border array to keep track of border cells
        int width = dungeon.getWidthInTiles();
        int height = dungeon.getHeightInTiles();


        border = new boolean[width][height];

        for (int x = 0; x < width; x++) {
            border[x][0] = true;
            border[x][height - 1] = true;
        }

        for (int y = 0; y < height; y++) {
            border[0][y] = true;
            border[width - 1][y] = true;
        }
    }



    // generate the maze
    private Maze generateMaze(Dungeon dungeon, int initX, int initY) {
        Maze maze = new Maze(initX, initY);
        Stack<Coordinates> stack = new Stack<>();
        stack.push(new Coordinates(initX, initY));
        Coordinates coords;
        int x, y;
        while (!stack.isEmpty()) {
            coords = stack.pop();

            x = coords.getX();
            y = coords.getY();
            if (dungeon.getVisited(x, y) || border[x][y]) {
                continue;
            }
            if (isLegalTile(dungeon, x, y)) {
                dungeon.setTile(x, y, Constants.FLOOR);
                dungeon.setVisited(x, y, true);
                maze.addTile(new Coordinates(x, y));
                stack = addNeighboursInRandomOrder(x, y, stack);
            }
        }
        return maze;
    }

    private Stack<Coordinates> addNeighboursInRandomOrder(int x, int y, Stack<Coordinates> stack) {
        LinkedList<Coordinates> neighbours = new LinkedList<>();

        neighbours.add(new Coordinates(x - 1, y));
        neighbours.add(new Coordinates(x, y + 1));
        neighbours.add(new Coordinates(x + 1, y));
        neighbours.add(new Coordinates(x, y - 1));

        for (int i = 4; i > 0; i--) {
            stack.add(neighbours.remove(random.nextInt(i)));
        }
        return stack;
    }

    private boolean isLegalTile(Dungeon dungeon, int x, int y) {
        Coordinates[] neighbours = getNeighbours(x, y);
        boolean result = true;
        for(int i = 0; i < 8; i += 2){
            result = result && isLegalTileFromDirection(dungeon, neighbours, i);
        }
        return result;
    }

    private boolean isLegalTileFromDirection(Dungeon dungeon, Coordinates[] neighbours, int i) {
        boolean result = true;
        int x = neighbours[i].getX();
        int y = neighbours[i].getY();
        if (dungeon.getVisited(x, y)) {
            for (int j = i + 2; j < i + 7; j++) {
                int n = j % 8;
                x = neighbours[n].getX();
                y = neighbours[n].getY();
                result = result && (!dungeon.getVisited(x, y));
            }
        }
        return result;
    }

    private boolean isEligibleInitialTile(Dungeon dungeon, int x, int y) {
        boolean result = true;
        int cX, cY;
        if (dungeon.getVisited(x, y) || border[x][y]) return false;
        for (Coordinates c : getNeighbours(x, y)) {
            cX = c.getX();
            cY = c.getY();
            result = result && !dungeon.getVisited(cX, cY);
        }
        return result;
    }

    private Coordinates findNextEligibleInitialTile(Dungeon dungeon) {
        for (int x = 0; x < dungeon.getWidthInTiles(); x++) {
            for (int y = 0; y < dungeon.getHeightInTiles(); y++) {
                if (isEligibleInitialTile(dungeon, x, y)) return new Coordinates(x, y);
            }
        }
        return null;
    }

    private Coordinates[] getNeighbours(int x, int y) {
        Coordinates[] neighbours = new Coordinates[8];
        neighbours[0] = new Coordinates(x, y - 1);
        neighbours[1] = new Coordinates(x + 1, y - 1);
        neighbours[2] = new Coordinates(x + 1, y);
        neighbours[3] = new Coordinates(x + 1, y + 1);
        neighbours[4] = new Coordinates(x, y + 1);
        neighbours[5] = new Coordinates(x - 1, y + 1);
        neighbours[6] = new Coordinates(x - 1, y);
        neighbours[7] = new Coordinates(x - 1, y - 1);
        return neighbours;
    }


}
