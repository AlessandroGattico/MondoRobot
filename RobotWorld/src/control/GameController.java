package control;

import model.Cell;
import model.Directions;
import model.House;
import view.MondoRobotView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

public class GameController implements ActionListener, KeyListener {

    private static final int DEFAULTDIMENSION = 10;
    private final MondoRobotView view;
    private final House house;
    private boolean visibleSolution;
    private PropertyChangeListener playerListener;
    private PropertyChangeListener countdownListener;
    private PropertyChangeListener gameListener;
    private Random random;
    private boolean updating;

    public GameController() {
        this.house = new House(DEFAULTDIMENSION);
        this.view = new MondoRobotView(this.house);
        this.visibleSolution = false;
        this.updating = false;
        this.random = new Random();
        this.playerListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("move")) {
                    updating = true;
                    house.updateWorld();
                    view.updateViews((Cell) evt.getOldValue());
                    if (house.getRobot().isPlaying()) {
                        view.getControlView().timerReset();
                        view.getControlView().timerStart();
                        updating = false;
                    } else {
                        view.getControlView().timerStop();
                    }
                } else if (evt.getPropertyName().equals("shoot")) {
                    updating = true;
                    house.updateWorld();
                    view.updateViews();
                    if (house.getRobot().isPlaying()) {
                        view.getControlView().timerReset();
                        view.getControlView().timerStart();
                        updating = false;
                    } else {
                        view.getControlView().timerStop();
                    }
                } else if (evt.getPropertyName().equals("turn")) {
                    updating = true;
                    view.updateViews();
                    updating = false;
                }
            }
        };

        this.gameListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("gameOverWumpus")) {
                    updating = true;
                    view.getControlView().timerStop();
                    JOptionPane.showMessageDialog(null, "Oh no! The wumpus caught you!! GAME OVER", "Hunt the Wumpus",
                            JOptionPane.INFORMATION_MESSAGE);
                    view.getControlView().timerStop();
                } else if (evt.getPropertyName().equals("gameOverPit")) {
                    updating = true;
                    view.getControlView().timerStop();
                    JOptionPane.showMessageDialog(null, "Oh no! You fell into a pit!!", "Hunt the Wumpus",
                            JOptionPane.INFORMATION_MESSAGE);
                    view.getControlView().timerStop();
                } else if (evt.getPropertyName().equals("win")) {
                    updating = true;
                    view.getControlView().timerStop();
                    JOptionPane.showMessageDialog(null, "Amazing!! You killed the wumpus!!!", "Hunt the Wumpus",
                            JOptionPane.INFORMATION_MESSAGE);
                    view.getControlView().timerStop();
                }
            }
        };

        this.addListeners();
    }

    private void addListeners() {
        this.view.getControlView().getPlayButton().addActionListener(this);
        this.view.getControlView().getSolutionButton().addActionListener(this);
        this.view.getControlView().getRandomMoveButton().addActionListener(this);
        this.view.getControlView().addPropertyChangeListener(this.countdownListener);
        this.house.getRobot().addPropertyChangeListener(this.playerListener);
        this.house.addPropertyChangeListener(this.gameListener);
        this.view.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int choice;

        switch (e.getActionCommand()) {
            case "Play":
                if (this.house.getRobot().isPlaying()) {
                    this.view.getControlView().timerStop();
                    choice = JOptionPane.showConfirmDialog(null, "Want to restart the game?", "Robot World",
                            JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        this.house.resetGame();
                        this.view.resetViews();
                        this.startGame();
                    } else {
                        this.view.getControlView().timerStart();
                    }
                } else if (this.updating && !this.house.getRobot().isPlaying()) {
                    this.house.resetGame();
                    this.view.resetViews();
                    this.startGame();
                } else {
                    this.view.resetViews();
                    this.startGame();
                }
                break;
            case "Show solution":
                this.visibleSolution = !this.visibleSolution;
                this.view.getSolutionView().setVisible(this.visibleSolution);
                break;
            default:
                break;
        }
    }

    private void startGame() {
        this.updating = true;
        this.house.play();
        this.view.updateViews();
        this.view.getControlView().timerReset();
        this.view.getControlView().timerStart();
        this.updating = false;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!this.updating && this.house.getRobot().isPlaying()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    this.house.getRobot().turn(Directions.TURNLEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    this.house.getRobot().turn(Directions.TURNRIGHT);
                    break;
                case KeyEvent.VK_W:
                    this.house.getRobot().move();
                    break;
                case KeyEvent.VK_T:
                    this.house.getRobot().turOn();
                    break;
                case KeyEvent.VK_SPACE:
                    this.house.getRobot().repair();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
