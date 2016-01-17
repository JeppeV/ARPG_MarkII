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
            app.setDisplayMode(1300, 600, false);
            app.setVSync(true);
            app.setTargetFrameRate(60);
            app.setMinimumLogicUpdateInterval(10);
            app.setMaximumLogicUpdateInterval(20);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
