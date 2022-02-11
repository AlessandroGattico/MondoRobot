package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Pet extends Character{
    private PropertyChangeSupport support;

    public Pet() {
        super();
        this.support = new PropertyChangeSupport(this);
    }

    public void move(Directions direction) {
        switch (direction) {
            case MOVEFORWARD:
                if (this.cell.getNeighbours()[0] != null && this.cell.getNeighbours()[0].getItem().equals(Items.EMPTY) &&
                        this.cell.getNeighbours()[0].getFeels().isEmpty()) {
                    this.changecell(this.cell.getNeighbours()[0]);
                }
                break;
            case MOVERIGHT:
                if (this.cell.getNeighbours()[1] != null && this.cell.getNeighbours()[1].getItem().equals(Items.EMPTY) &&
                        this.cell.getNeighbours()[1].getFeels().isEmpty()) {
                    this.changecell(this.cell.getNeighbours()[1]);
                }
                break;
            case MOVEBACKWARDS:
                if (this.cell.getNeighbours()[2] != null && this.cell.getNeighbours()[2].getItem().equals(Items.EMPTY) &&
                        !this.cell.getNeighbours()[2].getFeels().isEmpty()) {
                    this.changecell(this.cell.getNeighbours()[2]);
                }
                break;
            case MOVELEFT:
                if (this.cell.getNeighbours()[3] != null && this.cell.getNeighbours()[3].getItem().equals(Items.EMPTY) &&
                        !this.cell.getNeighbours()[3].getFeels().isEmpty()) {
                    this.changecell(this.cell.getNeighbours()[3]);
                }
                break;
            default:
                break;
        }
    }

    private void changecell(Cell cell) {
        this.cell.deleteItem();
        this.setCell(cell);
        this.cell.addItem(Items.PET);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
