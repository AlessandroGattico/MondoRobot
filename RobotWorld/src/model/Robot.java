package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Robot extends Character {
    private static final Directions DEFAULTFACING = Directions.EAST;
    private Directions facing;
    private int moves;
    private PropertyChangeSupport support;


    public Robot() {
        super();
        this.facing = DEFAULTFACING;
        this.moves = 0;
        this.support = new PropertyChangeSupport(this);

    }

    public Directions getFacing() {
        return this.facing;
    }

    public void resetFacing() {
        this.facing = DEFAULTFACING;
    }

    public int getMoves() {
        return this.moves;
    }

    public void resetMoves() {
        this.moves = 0;
    }

    public void addPropertyChangeListener(PropertyChangeListener playerListener) {
        this.support.addPropertyChangeListener(playerListener);
    }

    public void turn(Directions direction) {
        Directions oldDirection;
        oldDirection = this.facing;

        if (direction == Directions.TURNLEFT) {
            switch (this.facing) {
                case NORTH:
                    this.facing = Directions.WEST;
                    break;
                case SOUTH:
                    this.facing = Directions.EAST;
                    break;
                case WEST:
                    this.facing = Directions.SOUTH;
                    break;
                case EAST:
                    this.facing = Directions.NORTH;
                    break;
                default:
                    break;
            }
        } else if (direction == Directions.TURNRIGHT) {
            switch (this.facing) {
                case NORTH:
                    this.facing = Directions.EAST;
                    break;
                case SOUTH:
                    this.facing = Directions.WEST;
                    break;
                case WEST:
                    this.facing = Directions.NORTH;
                    break;
                case EAST:
                    this.facing = Directions.SOUTH;
                    break;
                default:
                    break;
            }
        }
        this.moves++;
        this.support.firePropertyChange("turn", oldDirection, this.facing);
    }

    public void move() {
        Cell oldCell = this.cell;

        switch (this.facing) {
            case NORTH:
                if (this.cell.getNeighbours()[0] != null) {
                    if (this.cell.getNeighbours()[0].getItem() != Items.EMPTY) {
                        this.changeRobotCell(this.cell.getNeighbours()[0]);
                        this.moves++;
                        this.support.firePropertyChange("move", oldCell, this.cell);
                    } else {
                        this.support.firePropertyChange("bump", 0, 0);
                    }
                }
                break;
            case EAST:
                if (this.cell.getNeighbours()[1] != null) {
                    if (this.cell.getNeighbours()[1].getItem() != Items.EMPTY) {
                        this.changeRobotCell(this.cell.getNeighbours()[1]);
                        this.moves++;
                        this.support.firePropertyChange("move", oldCell, this.cell);
                    } else {
                        this.support.firePropertyChange("bump", 0, 0);
                    }
                }
                break;
            case SOUTH:
                if (this.cell.getNeighbours()[2] != null) {
                    if (this.cell.getNeighbours()[2].getItem() != Items.EMPTY) {
                        this.changeRobotCell(this.cell.getNeighbours()[2]);
                        this.moves++;
                        this.support.firePropertyChange("move", oldCell, this.cell);
                    } else {
                        this.support.firePropertyChange("bump", 0, 0);
                    }
                }
                break;
            case WEST:
                if (this.cell.getNeighbours()[3] != null) {
                    if (this.cell.getNeighbours()[3].getItem() != Items.EMPTY) {
                        this.changeRobotCell(this.cell.getNeighbours()[3]);
                        this.moves++;
                        this.support.firePropertyChange("move", oldCell, this.cell);
                    } else {
                        this.support.firePropertyChange("bump", 0, 0);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void changeRobotCell(Cell neighbour) {
        this.cell.deleteItem();
        this.cell = neighbour;
        this.cell.addItem(Items.ROBOT);
    }

    public void turOn() {
        switch (this.facing) {
            case NORTH:
                if (this.cell.getNeighbours()[0] != null) {
                    if (this.cell.getNeighbours()[0].getItem() == Items.STOVE) {
                        this.support.firePropertyChange("on", this.cell, this.cell.getNeighbours()[0]);
                    } else {
                        this.support.firePropertyChange("nothing", this.cell, this.cell.getNeighbours()[0]);
                    }
                }
                break;
            case EAST:
                if (this.cell.getNeighbours()[1] != null) {
                    if (this.cell.getNeighbours()[1].getItem() == Items.STOVE) {
                        this.support.firePropertyChange("on", this.cell, this.cell.getNeighbours()[1]);
                    } else {
                        this.support.firePropertyChange("nothing", this.cell, this.cell.getNeighbours()[1]);
                    }
                }
                break;
            case SOUTH:
                if (this.cell.getNeighbours()[2] != null) {
                    if (this.cell.getNeighbours()[2].getItem() == Items.STOVE) {
                        this.support.firePropertyChange("on", this.cell, this.cell.getNeighbours()[2]);
                    } else {
                        this.support.firePropertyChange("nothing", this.cell, this.cell.getNeighbours()[2]);
                    }
                }
                break;
            case WEST:
                if (this.cell.getNeighbours()[3] != null) {
                    if (this.cell.getNeighbours()[3].getItem() == Items.STOVE) {
                        this.support.firePropertyChange("on", this.cell, this.cell.getNeighbours()[3]);
                    } else {
                        this.support.firePropertyChange("nothing", this.cell, this.cell.getNeighbours()[3]);
                    }
                }
                break;
            default:
                break;
        }
    }

    public void repair() {
        switch (this.facing) {
            case NORTH:
                if (this.cell.getNeighbours()[0] != null) {
                    if (this.cell.getNeighbours()[0].getItem() == Items.SINK) {
                        this.support.firePropertyChange("repair", this.cell, this.cell.getNeighbours()[0]);
                    } else {
                        this.support.firePropertyChange("nothing", this.cell, this.cell.getNeighbours()[0]);
                    }
                }
                break;
            case EAST:
                if (this.cell.getNeighbours()[1] != null) {
                    if (this.cell.getNeighbours()[1].getItem() == Items.SINK) {
                        this.support.firePropertyChange("repair", this.cell, this.cell.getNeighbours()[1]);
                    } else {
                        this.support.firePropertyChange("nothing", this.cell, this.cell.getNeighbours()[1]);
                    }
                }
                break;
            case SOUTH:
                if (this.cell.getNeighbours()[2] != null) {
                    if (this.cell.getNeighbours()[2].getItem() == Items.SINK) {
                        this.support.firePropertyChange("repair", this.cell, this.cell.getNeighbours()[2]);
                    } else {
                        this.support.firePropertyChange("nothing", this.cell, this.cell.getNeighbours()[2]);
                    }
                }
                break;
            case WEST:
                if (this.cell.getNeighbours()[3] != null) {
                    if (this.cell.getNeighbours()[3].getItem() == Items.SINK) {
                        this.support.firePropertyChange("repair", this.cell, this.cell.getNeighbours()[3]);
                    } else {
                        this.support.firePropertyChange("nothing", this.cell, this.cell.getNeighbours()[3]);
                    }
                }
                break;
            default:
                break;
        }
    }


}
