package model;

public abstract class Character {
    protected Cell cell;
    protected boolean isPlaying;

    public Character() {
        this.cell = null;
        this.isPlaying = false;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void stCell(Cell cell) {
        this.cell = cell;
    }


    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setPlaying(boolean playing) {
        this.isPlaying = playing;
    }

}
