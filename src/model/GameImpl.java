package model;


import generator.generators.dungeon.DungeonGenerator;
import generator.standard.Map;
import generator.standard.MapGenerator;
import generator.standard.TileType;
import model.entities.Entity;
import model.entities.EntityHandler;
import model.entities.GruntEnemy;
import model.entities.Player;
import model.tiles.Tile;
import model.tiles.TileHandler;
import model.tiles.TileImpl;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class GameImpl implements Game {

    private GameContainer gameContainer;
    private Rectangle cameraBounds;
    private Player player;
    private MapAdapter mapAdapter;
    private OffsetHandler offsetHandler;
    private TileHandler tileHandler;
    private EntityHandler entityHandler;
    private ExecutorService executorService;

    public GameImpl(GameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.cameraBounds = new Rectangle(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
        this.executorService = Executors.newCachedThreadPool();
        Map map = generateMap(new DungeonGenerator(), 100, 100);
        this.mapAdapter = new MapAdapter(map);

        this.offsetHandler = new OffsetHandler();
        this.tileHandler = new TileHandler(map, offsetHandler);
        offsetHandler.addObserver(tileHandler);
        this.entityHandler = new EntityHandler();
        offsetHandler.addObserver(entityHandler);

        //test values for player
        int playerWidth = 45, playerHeight = 45;
        Vector2f start = getRandomStartingPosition();
        start = start == null ? new Vector2f(50, 50) : start;
        this.player = new Player(start.getX(), start.getY(), playerWidth, playerHeight, this);
        entityHandler.add(player);
        //test enemies
        entityHandler.add(new GruntEnemy(start.getX(), start.getY(), this));
        //addEnemies(200);

    }

    private Map generateMap(MapGenerator mapGenerator, int width, int height) {
        return mapGenerator.generateMap(width, height);
    }

    private void addEnemies(int n) {
        Vector2f pos;
        for (int i = 0; i < n; i++) {
            pos = getRandomStartingPosition();
            if (pos != null) {
                entityHandler.add(new GruntEnemy(pos.getX(), pos.getY(), this));
            }
        }
    }

    private Vector2f getRandomStartingPosition() {
        TileImpl t;
        for (int x = 0; x < mapAdapter.getWidthInTiles(); x++) {
            for (int y = 0; y < mapAdapter.getHeightInTiles(); y++) {
                t = tileHandler.getTileByIndex(x, y);
                if (t.getID() == TileType.FLOOR && Math.random() <= 0.5f) {
                    return new Vector2f(t.getCenterX(), t.getCenterY());
                }
            }
        }
        return null;
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        entityHandler.update(gameContainer, delta);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public MapAdapter getMapAdapter() {
        return mapAdapter;
    }

    @Override
    public Rectangle getCameraBounds() {
        return cameraBounds;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Tile[][] getTiles() {
        return tileHandler.getTiles();
    }

    @Override
    public LinkedList<Entity> getEntities() {
        return entityHandler.getEntities();
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }

    public TileHandler getTileHandler() {
        return tileHandler;
    }

    public OffsetHandler getOffsetHandler() {
        return offsetHandler;
    }

    public GameContainer getGameContainer() {
        return gameContainer;
    }


}
