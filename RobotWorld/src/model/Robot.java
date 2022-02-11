package model;

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

    public void turn(Directions direction) {
        Directions oldDirection;
        oldDirection = this.facing;

        switch (direction) {
            case TURNLEFT:
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
                }
                break;
            case TURNRIGHT:
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
                }
                break;
        }

        this.moves++;
        this.support.firePropertyChange("turn", oldDirection, this.facing);
    }

    public void move() {
        Cell oldCell = this.cell;

        this.moves++;

        switch (this.facing) {
            case NORTH:
                if (this.cell.getNeighbours()[0] != null) {
                    this.changeRobotCell(this.cell.getNeighbours()[0]);
                }
                break;
            case EAST:
                if (this.cell.getNeighbours()[1] != null) {
                    this.changeRobotCell(this.cell.getNeighbours()[1]);
                }
                break;
            case SOUTH:
                if (this.cell.getNeighbours()[2] != null) {
                    this.changeRobotCell(this.cell.getNeighbours()[2]);
                }
                break;
            case WEST:
                if (this.cell.getNeighbours()[3] != null) {
                    this.changeRobotCell(this.cell.getNeighbours()[3]);
                }
                break;
            default:
                break;
        }

        this.support.firePropertyChange("move", oldCell, this.cell);

    }

    private void changeRobotCell(Cell neighbour) {
        this.cell.deleteItem();
        this.cell = neighbour;
        this.cell.addItem(Items.ROBOT);
    }
}
