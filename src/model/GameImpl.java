package model;


import generator.generators.dungeon.DungeonGenerator;
import generator.standard.Map;
import generator.standard.MapGenerator;
import generator.standard.TileType;
import model.entities.EntityHandler;
import model.entities.GruntEnemy;
import model.entities.Player;
import model.facade.Entity;
import model.facade.Game;
import model.facade.Tile;
import model.tiles.TileHandler;
import model.tiles.TileImpl;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.navmesh.NavMesh;
import org.newdawn.slick.util.pathfinding.navmesh.NavMeshBuilder;

import java.util.LinkedList;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class GameImpl implements Game {

    private GameContainer gameContainer;
    private Rectangle cameraBounds;
    private Player player;
    private MapAdapter mapAdapter;
    private NavMesh navMesh;
    private OffsetHandler offsetHandler;
    private TileHandler tileHandler;
    private EntityHandler entityHandler;

    public GameImpl(GameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.cameraBounds = new Rectangle(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
        Map map = generateMap(new DungeonGenerator(), 100, 100);
        this.mapAdapter = new MapAdapter(map);
        NavMeshBuilder navMeshBuilder = new NavMeshBuilder();
        this.navMesh = navMeshBuilder.build(mapAdapter, true);
        this.tileHandler = new TileHandler(map);
        this.entityHandler = new EntityHandler();
        this.offsetHandler = new OffsetHandler();
        offsetHandler.addObserver(tileHandler);
        offsetHandler.addObserver(entityHandler);

        //test values for player
        int playerWidth = 45, playerHeight = 45;
        Vector2f start = getRandomStartingPosition();
        start = start == null ? new Vector2f(50, 50) : start;
        this.player = new Player(start.getX(), start.getY(), playerWidth, playerHeight, this);
        entityHandler.add(player);
        //test enemy
        entityHandler.add(new GruntEnemy(start.getX(), start.getY(), this));

    }

    private Map generateMap(MapGenerator mapGenerator, int width, int height) {
        return mapGenerator.generateMap(width, height);
    }

    private Vector2f getRandomStartingPosition() {
        TileImpl t;
        for (int x = 0; x < mapAdapter.getWidthInTiles(); x++) {
            for (int y = 0; y < mapAdapter.getHeightInTiles(); y++) {
                t = tileHandler.getTileByIndex(x, y);
                if (t.getID() == TileType.FLOOR && Math.random() <= 0.2f) {
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

    public MapAdapter getMapAdapter(){
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
