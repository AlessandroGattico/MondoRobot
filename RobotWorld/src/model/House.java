package model;

import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Random;

public class House {
    private Cell[][] map;
    private Robot robot;
    private WashingMachine[] washingMachines;
    private Stove[] stoves;
    private Pet[] pets;
    private Sink[] sinks;
    private int dimension;
    private HashSet<String> messages;
    private boolean gameOn;


    public House(int dim) {
        this.dimension = dim;
        this.map = new Cell[this.dimension][this.dimension];
        this.robot = new Robot();
        this.stoves = new Stove[Math.round(this.dimension / 2)];
        this.pets = new Pet[Math.round(this.dimension / 2)];
        this.washingMachines = new WashingMachine[Math.round(this.dimension / 2)];
        this.sinks = new Sink[Math.round(this.dimension/2)];
        this.messages = new HashSet<>();
        this.gameOn = false;

        this.setupHouse();
    }

    private void setupHouse() {
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.map[i][j] = new Cell(i, j);
            }
        }

        for (int i = 0; i < this.pets.length; i++) {
            this.washingMachines[i] = new WashingMachine();
            this.stoves[i] = new Stove();
            this.pets[i] = new Pet();
            this.sinks[i] = new Sink();
        }
    }

    public Cell[][] getMap() {
        return this.map;
    }

    public HashSet<String> getMessages() {
        return this.messages;
    }

    public Robot getRobot() {
        return this.robot;
    }

    public int getDimension() {
        return this.dimension;
    }

    public boolean isGameOn() {
        return this.gameOn;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        for (int i = 0; i < this.stoves.length; i++) {
            this.stoves[i].addPropertyChangeListener(listener);
            this.washingMachines[i].addPropertyChangeListener(listener);
            this.pets[i].addPropertyChangeListener(listener);
            this.sinks[i].addPropertyChangeListener(listener);
        }
    }

    public void play() {
        this.setNeighbours();
        this.setupRobot();
        this.setupStoves();
        this.setupWashingMachines();
        this.setupPets();
        this.gameOn = true;
    }

    private void setupPets() {
    }

    private void setupWashingMachines() {
        int[] random;

        for (int i = 0; i < this.washingMachines.length; i++) {

            do {
                random = getRandomCoordinates(3);
            } while (!this.neighboursEmpty(this.map[random[0]][random[1]]));

            this.washingMachines[i].setCell(this.map[random[0]][random[1]]);
            this.washingMachines[i].getCell().addItem(Items.WASHINGMACHINE);
            this.addNeighbourFeels(this.washingMachines[i].getCell(), Feels.WASH);
            this.washingMachines[i].setPlaying(true);
        }
    }

    private void setupStoves() {
    }

    private void setupRobot() {
    }

    private void setNeighbours() {
        //north = 0, east = 1, south = 2, west = 3
        Cell[] neighbours;

        for (Cell[] row : this.map) {
            for (Cell cell : row) {
                neighbours = new Cell[4];
                if (cell.getY() - 1 >= 1) {
                    neighbours[0] = this.map[cell.getY() - 1][cell.getX()];    //north
                } else {
                    neighbours[0] = null;
                }
                if (cell.getX() + 1 < this.dimension - 1) {
                    neighbours[1] = this.map[cell.getY()][cell.getX() + 1];    //east
                } else {
                    neighbours[1] = null;
                }
                if (cell.getY() + 1 < this.dimension - 1) {
                    neighbours[2] = this.map[cell.getY() + 1][cell.getX()];    //south
                } else {
                    neighbours[2] = null;
                }
                if (cell.getX() - 1 >= 1) {
                    neighbours[3] = this.map[cell.getY()][cell.getX() - 1];    //west
                } else {
                    neighbours[3] = null;
                }
                cell.setNeighbours(neighbours);
            }
        }
    }

    private int[] getRandomCoordinates(int away) {
        int[] randomC = new int[2];
        Random rand = new Random();

        do {
            do {
                randomC[0] = rand.nextInt(this.dimension - 2) + 1;
                randomC[1] = rand.nextInt(this.dimension - 2) + 1;
            } while (this.map[randomC[0]][randomC[1]].getItem() != Items.EMPTY);
        } while (randomC[0] < away + 1 && randomC[1] < away + 1);

        return randomC;
    }

    private boolean neighboursEmpty(Cell cell) {
        for (Cell r : cell.getNeighbours()) {
            if (r != null) {
                if (!r.getItem().equals(Items.EMPTY)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void addNeighbourFeels(Cell cell, Feels feel) {
        for (Cell r : cell.getNeighbours()) {
            if (r != null) {
                r.addFeels(feel);
            }
        }
    }

    private void removeNeighboursFeels(Cell cell, Feels feel) {
        for (Cell r : cell.getNeighbours()) {
            if (r != null) {
                r.deleteFeels(feel);
            }
        }
    }

    public void updateWorld() {
        
    }

    public void resetGame() {
    }
}
