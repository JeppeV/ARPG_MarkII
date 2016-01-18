package model;

import generator.generators.DungeonGenerator;
import generator.standard.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class GameImpl implements Game {

    private Player player;
    private DungeonGenerator dungeonGenerator;
    private Map map;
    private MapAdapter mapAdapter;
    private TileHandler tileHandler;

    public GameImpl() {
        this.player = new Player(100, 100, 45, 45);
        this.dungeonGenerator = new DungeonGenerator();
        this.map = dungeonGenerator.generateDungeon(50, 50);
        this.mapAdapter = new MapAdapter(map);
        this.tileHandler = new TileHandler(map);
    }

    @Override
    public Player getPlayer() {
        return player;
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
