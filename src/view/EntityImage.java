package view;


import model.facade.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


/**
 * Created by Jeppe Vinberg on 15-01-2016.
 */
public class EntityImage extends Image {

    private Entity entity;

    public EntityImage(String ref, Entity entity) throws SlickException {
        super(ref);
        this.entity = entity;
    }

    @Override
    public void draw() {
        super.setRotation(entity.getRotation());
        super.draw(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());

    }

    public Rectangle getBounds(){
        return new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
    }

}
