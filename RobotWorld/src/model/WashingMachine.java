package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class WashingMachine extends Character {

    private PropertyChangeSupport support;

    public WashingMachine() {
        super();
    }

    public void breakDown(){
        for (Cell c: this.getCell().getNeighbours()) {
            c.addFeels(Feels.WET);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
