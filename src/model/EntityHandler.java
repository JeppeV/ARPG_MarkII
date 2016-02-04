package model;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jeppe Vinberg on 04-02-2016.
 */
public class EntityHandler implements Observer {

    private LinkedList<Entity> entities;

    public EntityHandler(){
        this.entities = new LinkedList<>();
    }




    @Override
    public void update(Observable o, Object arg) {

    }
}
