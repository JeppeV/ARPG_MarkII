package model;

import generator.generators.DungeonGenerator;
import generator.standard.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class GameImpl implements Game {


    private GameContainer gameContainer;
    private Player player;
    private DungeonGenerator dungeonGenerator;
    private Map map;
    private MapAdapter mapAdapter;
    private TileHandler tileHandler;

    public GameImpl(GameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.dungeonGenerator = new DungeonGenerator();
        this.map = dungeonGenerator.generateDungeon(10, 10);
        this.mapAdapter = new MapAdapter(map);
        this.tileHandler = new TileHandler(map);
        //test values for player
        int gameContainerCenterX = gameContainer.getWidth() / 2;
        int gameContainerCenterY = gameContainer.getHeight() / 2;
        int playerWidth = 45, playerHeight = 45;
        this.player = new Player(gameContainerCenterX - (playerWidth/2), gameContainerCenterY - (playerHeight/2), playerWidth, playerHeight, this);
        player.addObserver(tileHandler);


    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public TileHandler getTileHandler(){
        return tileHandler;
    }

    public MapAdapter getMapAdapter(){
        return mapAdapter;
    }

    public GameContainer getGameContainer(){
        return gameContainer;
    }

    @Override
    public Tile[][] getTiles(){
        return tileHandler.getTiles();
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        player.update(gameContainer, delta);
        mapAdapter.update(gameContainer, delta);
    }


}
