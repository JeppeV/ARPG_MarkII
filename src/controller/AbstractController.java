package controller;

import org.newdawn.slick.InputListener;

/**
 * Created by Jeppe Vinberg on 15-01-2016.
 * <p>
 * An abstract class to factor out unused methods while still implementing the InputListener.
 * The result is that any class that extends this class will have the interface of
 * an InputListener which only accepts mouse events and key events.
 */
public abstract class AbstractController implements InputListener {

    @Override
    public void controllerLeftPressed(int i) {
    }

    @Override
    public void controllerLeftReleased(int i) {
    }

    @Override
    public void controllerRightPressed(int i) {
    }

    @Override
    public void controllerRightReleased(int i) {
    }

    @Override
    public void controllerUpPressed(int i) {
    }

    @Override
    public void controllerUpReleased(int i) {
    }

    @Override
    public void controllerDownPressed(int i) {
    }

    @Override
    public void controllerDownReleased(int i) {
    }

    @Override
    public void controllerButtonPressed(int i, int i1) {
    }

    @Override
    public void controllerButtonReleased(int i, int i1) {
    }


}

