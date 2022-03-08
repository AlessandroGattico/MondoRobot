package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Sink extends Character implements Break{
    private PropertyChangeSupport support;

    public Sink() {
        super();
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    @Override
    public void breakDown(){
        for (Cell c: this.getCell().getNeighbours()) {
            c.addFeels(Feels.WET);
        }
    }

    @Override
    public void expandBreakDown() {
        Cell[] neighbours;

        neighbours = this.getCell().getNeighbours();
        for (Cell c: neighbours) {
            for (Cell n: c.getNeighbours()) {
                if (n.getItem() == Items.EMPTY) {
                    n.addFeels(Feels.WET);
                }
            }
        }
    }
}
