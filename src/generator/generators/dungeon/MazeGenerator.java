package generator.generators.dungeon;

import generator.standard.TileType;
import generator.standard.Coordinates;
import generator.standard.Dungeon;
import generator.standard.Maze;

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
        Coordinates eligibleInitialTile = findNextEligibleInitialTile(dungeon);
        while (eligibleInitialTile != null) {
            maze = generateMaze(dungeon, eligibleInitialTile.getX(), eligibleInitialTile.getY());
            dungeon.addMaze(maze);
            mazes.add(maze);
            eligibleInitialTile = findNextEligibleInitialTile(dungeon);
        }
        System.out.println("MazeGenerator successfully created " + mazes.size() + " mazes.");
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
            if (dungeon.getVisited(coords) || border[x][y]) {
                continue;
            }
            if (isLegalTile(dungeon, coords)) {
                dungeon.setTile(coords, TileType.FLOOR);
                dungeon.setVisited(coords, true);
                maze.addTile(new Coordinates(x, y));
                stack = addNeighboursInRandomOrder(coords, stack);
            }
        }
        return maze;
    }

    private Stack<Coordinates> addNeighboursInRandomOrder(Coordinates coords, Stack<Coordinates> stack) {
        LinkedList<Coordinates> neighbours = new LinkedList<>();
        for(Coordinates c : coords.getPrimeNeighbours()){
            neighbours.add(c);
        }

        for (int i = 4; i > 0; i--) {
            stack.add(neighbours.remove(random.nextInt(i)));
        }
        return stack;
    }

    private boolean isLegalTile(Dungeon dungeon, Coordinates coords) {
        Coordinates[] neighbours = coords.getNeighbours();
        boolean result = true;
        for(int i = 0; i < 8; i += 2){
            result = result && isLegalTileFromDirection(dungeon, neighbours, i);
        }
        return result;
    }

    private boolean isLegalTileFromDirection(Dungeon dungeon, Coordinates[] neighbours, int i) {
        boolean result = true;
        if (dungeon.getVisited(neighbours[i])) {
            for (int j = i + 2; j < i + 7; j++) {
                int n = j % 8;
                result = result && (!dungeon.getVisited(neighbours[n]));
            }
        }
        return result;
    }

    private boolean isEligibleInitialTile(Dungeon dungeon, Coordinates coords) {
        boolean result = true;
        int x = coords.getX();
        int y = coords.getY();
        int cX, cY;
        if (dungeon.getVisited(x, y) || border[x][y]) return false;
        for (Coordinates c : coords.getNeighbours()) {
            cX = c.getX();
            cY = c.getY();
            result = result && !dungeon.getVisited(cX, cY);
        }
        return result;
    }

    private Coordinates findNextEligibleInitialTile(Dungeon dungeon) {
        Coordinates res;
        for (int x = 0; x < dungeon.getWidthInTiles(); x++) {
            for (int y = 0; y < dungeon.getHeightInTiles(); y++) {
                res = new Coordinates(x, y);
                if (isEligibleInitialTile(dungeon, res)) return res;
            }
        }
        return null;
    }



}
