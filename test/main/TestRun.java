package main;

import generator.generators.dungeon.DungeonGenerator;
import generator.standard.Map;
import generator.standard.MapGenerator;
import model.tiles.TileHandler;
import model.tiles.TileImpl;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import slick.framework.MainGame;

import java.util.ArrayList;


/**
 * Created by Jeppe Vinberg on 14-01-2016.
 */
public class TestRun {

    private MapGenerator mapGenerator;
    private Map map;
    private TileHandler tileHandler;

    @Before
    public void setup(){
        mapGenerator = new DungeonGenerator();
        map = mapGenerator.generateMap(100, 100);
        tileHandler = new TileHandler(map);
    }

    @Test
    public void testTileHandlerGetNeighbours(){
        ArrayList<TileImpl> neighbours = tileHandler.getNeighboursByIndex(0, 0);
        assert( neighbours.size() == 3);
        neighbours = tileHandler.getNeighboursByIndex(1, 1);
        assert( neighbours.size() == 8);
    }

    @Test
    public void test() {
        try {
            AppGameContainer app = new AppGameContainer(new MainGame());
            Input.disableControllers();
            app.setDisplayMode(1300, 600, false);
            //app.setVSync(true);
            //app.setTargetFrameRate(60);
            app.setMinimumLogicUpdateInterval(10);
            app.setMaximumLogicUpdateInterval(20);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
