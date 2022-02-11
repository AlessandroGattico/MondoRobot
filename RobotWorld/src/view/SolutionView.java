package view;

import model.House;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class SolutionView extends MapView {

    public SolutionView(int dimension) {
        super(dimension);

        this.setVisible(false);
    }

    private void showIcons(House house) {
        if (house.isGameOn()) {
            for (int i = 0; i < this.dimension; i++) {
                for (int j = 0; j < this.dimension; j++) {
                    this.mapContent[i][j].setBackground(Color.WHITE);
                    switch (house.getMap()[i][j].getItem()) {
                        case WATER:
                            this.mapContent[i][j]
                                    .setIcon(new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/arrow.png"))));
                            break;
                        case WASHINGMACHINE:
                            this.mapContent[i][j]
                                    .setIcon(new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/gold.png"))));
                            break;
                        case STOVE:
                            this.mapContent[i][j]
                                    .setIcon(new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/babyWumpus.png"))));
                            break;
                        case PET:
                            this.mapContent[i][j]
                                    .setIcon(new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/pit.png"))));
                            break;
                        case SINK:
                            this.mapContent[i][j]
                                    .setIcon(new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/survivor.png"))));
                            break;
                        case FIRE:
                            this.mapContent[i][j]
                                    .setIcon(new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/wumpus.png"))));
                            break;
                        case BRICK:
                            this.mapContent[i][j]
                                    .setIcon(new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/s.png"))));
                            break;
                        case ROBOT:
                            switch (house.getRobot().getFacing()) {
                                case NORTH:
                                    this.mapContent[i][j]
                                            .setIcon(new ImageIcon(
                                                    Objects.requireNonNull(
                                                            getClass().getResource("/icons/playerNorth.png"))));
                                    break;
                                case EAST:
                                    this.mapContent[i][j]
                                            .setIcon(new ImageIcon(
                                                    Objects.requireNonNull(
                                                            getClass().getResource("/icons/playerEast.png"))));
                                    break;
                                case SOUTH:
                                    this.mapContent[i][j]
                                            .setIcon(new ImageIcon(
                                                    Objects.requireNonNull(
                                                            getClass().getResource("/icons/playerSouth.png"))));
                                    break;
                                case WEST:
                                    this.mapContent[i][j]
                                            .setIcon(new ImageIcon(
                                                    Objects.requireNonNull(
                                                            getClass().getResource("/icons/playerWest.png"))));
                                    break;
                            }
                            break;
                        default:
                            this.mapContent[i][j].setIcon(null);
                            break;
                    }
                }
            }
        }
    }

    public void updateView(House house) {
        this.showIcons(house);
    }

    public void resetSolution() {
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.mapContent[i][j].setIcon(null);
            }
        }
    }
}
