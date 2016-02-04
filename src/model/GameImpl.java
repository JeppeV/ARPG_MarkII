package model;


import generator.generators.dungeon.DungeonGenerator;
import generator.standard.Map;
import generator.standard.MapGenerator;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class GameImpl implements Game {

    private GameContainer gameContainer;
    private Player player;
    private MapGenerator mapGenerator;
    private Map map;
    private MapAdapter mapAdapter;
    private TileHandler tileHandler;

    public GameImpl(GameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.mapGenerator = new DungeonGenerator();
        this.map = mapGenerator.generateMap(50, 50);
        this.mapAdapter = new MapAdapter(map);
        this.tileHandler = new TileHandler(map);

        //test values for player
        int playerWidth = 45, playerHeight = 45;
        this.player = new Player(1200, 1400, playerWidth, playerHeight, this);
        player.addObserver(tileHandler);


    }

    @Override
    public Entity getPlayer() {
        return player;
    }

    public TileHandler getTileHandler() {
        return tileHandler;
    }

    public GameContainer getGameContainer() {
        return gameContainer;
    }

    public MapAdapter getMapAdapter(){
        return mapAdapter;
    }

    @Override
    public Tile[][] getTiles() {
        return tileHandler.getTiles();
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        player.update(gameContainer, delta);
        mapAdapter.update(gameContainer, delta);
    }


}
