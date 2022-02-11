package model;

public class WashingMachine extends Character {

    public WashingMachine() {
        super();
    }

    public void breakDown(){
        for (Cell c: this.getCell().getNeighbours()) {
            c.addFeels(Feels.WET);
        }
    }

}
