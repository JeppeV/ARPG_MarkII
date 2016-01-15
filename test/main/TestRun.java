package main;

import org.junit.Test;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import slick.framework.Main;


/**
 * Created by Jeppe Vinberg on 14-01-2016.
 */
public class TestRun {

    @Test
    public void test() {
        try {
            AppGameContainer app = new AppGameContainer(new Main());
            app.setDisplayMode(1600, 900, false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
