package view;

import model.Cell;
import model.Robot;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MapView extends JPanel {
    protected JLabel[][] mapContent;
    protected int dimension;

    public MapView(int dimension) {
        super(new GridLayout(dimension, dimension));
        this.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.WHITE));
        this.dimension = dimension;
        this.mapContent = new JLabel[this.dimension][this.dimension];

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.mapContent[i][j] = new JLabel(null, null, JLabel.CENTER);
                this.mapContent[i][j].setBackground(Color.BLACK);
                this.mapContent[i][j].setOpaque(true);
                this.mapContent[i][j].setBorder(BorderFactory.createLineBorder(Color.red, 1));
                this.add(mapContent[i][j]);
                if (i == 0 || j == 0 || i == this.dimension - 1 || j == this.dimension - 1) {
                    this.mapContent[i][j].setBackground(Color.WHITE);
                    this.mapContent[i][j].setIcon(new ImageIcon(
                            Objects.requireNonNull(getClass().getResource("/icons/steps.png"))));
                }
            }
        }

        this.setVisible(true);
    }

    public void updateView(Robot robot, Cell oldCell) {

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (i == 0 || j == 0 || i == this.dimension - 1 || j == this.dimension - 1) {
                    this.mapContent[i][j].setBackground(Color.WHITE);
                    this.mapContent[i][j].setIcon(new ImageIcon(
                            Objects.requireNonNull(getClass().getResource("/icons/steps.png"))));
                }
            }
        }
        if (robot.isPlaying()) {
            this.mapContent[oldCell.getY()][oldCell.getX()].setBackground(Color.WHITE);
            this.mapContent[robot.getCell().getY()][robot.getCell().getX()].setBackground(Color.WHITE);

            switch (robot.getFacing()) {
                case NORTH:
                    this.mapContent[robot.getCell().getY()][robot.getCell().getX()]
                            .setIcon(new ImageIcon(
                                    Objects.requireNonNull(getClass().getResource("/icons/playerNorth.png"))));
                    break;
                case EAST:
                    this.mapContent[robot.getCell().getY()][robot.getCell().getX()]
                            .setIcon(
                                    new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/playerEast.png"))));
                    break;
                case SOUTH:
                    this.mapContent[robot.getCell().getY()][robot.getCell().getX()]
                            .setIcon(new ImageIcon(
                                    Objects.requireNonNull(getClass().getResource("/icons/playerSouth.png"))));
                    break;
                case WEST:
                    this.mapContent[robot.getCell().getY()][robot.getCell().getX()]
                            .setIcon(
                                    new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/playerWest.png"))));
                    break;
                default:
                    break;
            }
        }
    }

    public void updateView(Robot robot) {
        if (robot.isPlaying()) {
            this.mapContent[robot.getCell().getY()][robot.getCell().getX()].setBackground(Color.WHITE);

            switch (robot.getFacing()) {
                case NORTH:
                    this.mapContent[robot.getCell().getY()][robot.getCell().getX()]
                            .setIcon(new ImageIcon(
                                    Objects.requireNonNull(getClass().getResource("/icons/robotNorth.png"))));
                    break;
                case EAST:
                    this.mapContent[robot.getCell().getY()][robot.getCell().getX()]
                            .setIcon(
                                    new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/robotEast.png"))));
                    break;
                case SOUTH:
                    this.mapContent[robot.getCell().getY()][robot.getCell().getX()]
                            .setIcon(new ImageIcon(
                                    Objects.requireNonNull(getClass().getResource("/icons/robotSouth.png"))));
                    break;
                case WEST:
                    this.mapContent[robot.getCell().getY()][robot.getCell().getX()]
                            .setIcon(
                                    new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/robotWest.png"))));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Resets the view.
     */
    protected void resetView() {
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.mapContent[i][j].setBackground(Color.BLACK);
                this.mapContent[i][j].setIcon(null);
            }
        }
    }
}
