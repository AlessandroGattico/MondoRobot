package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Stove extends Character {
    private boolean on;
    private PropertyChangeSupport support;

    public Stove() {
        super();
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
