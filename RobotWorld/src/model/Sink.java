package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Sink extends Character{
    private PropertyChangeSupport support;

    public Sink() {
        super();
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

}
