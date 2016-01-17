package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import slick.framework.Main;

/**
 * Created by Jeppe Vinberg on 17-01-2016.
 */
public class TestMain {

    public static void main(String[] args){
        try {
            AppGameContainer app = new AppGameContainer(new Main());
            app.setDisplayMode(1300, 600, false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
