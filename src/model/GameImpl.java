package model;


import generator.generators.cave.CaveGenerator;
import generator.generators.dungeon.DungeonGenerator;
import generator.standard.Map;
import generator.standard.MapGenerator;
import model.entities.GruntEnemy;
import model.facade.Entity;
import model.entities.EntityHandler;
import model.entities.Player;
import model.facade.Game;
import model.facade.Tile;
import model.tiles.TileHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import java.util.LinkedList;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class GameImpl implements Game {

    private GameContainer gameContainer;
    private Player player;
    private LinkedList<Entity> entities;
    private MapGenerator mapGenerator;
    private Map map;
    private MapAdapter mapAdapter;
    private OffsetHandler offsetHandler;
    private TileHandler tileHandler;
    private EntityHandler entityHandler;

    public GameImpl(GameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.mapGenerator = new DungeonGenerator();
        this.map = mapGenerator.generateMap(100, 20);
        this.mapAdapter = new MapAdapter(map);
        this.offsetHandler = new OffsetHandler();
        this.tileHandler = new TileHandler(map);
        this.entityHandler = new EntityHandler();
        offsetHandler.addObserver(tileHandler);
        offsetHandler.addObserver(entityHandler);

        //test values for player
        int playerWidth = 45, playerHeight = 45;
        this.player = new Player(10, 10, playerWidth, playerHeight, this);
        entityHandler.add(new GruntEnemy(1250, 1450, this));
        entityHandler.add(player);


    }

    public EntityHandler getEntityHandler() { return entityHandler; }

    public TileHandler getTileHandler() {
        return tileHandler;
    }

    public OffsetHandler getOffsetHandler() {
        return offsetHandler;
    }

    public GameContainer getGameContainer() {
        return gameContainer;
    }

    public MapAdapter getMapAdapter() {
        return mapAdapter;
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

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        entityHandler.update(gameContainer, delta);
    }


}
