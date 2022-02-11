package control;

import model.Cell;
import model.Directions;
import model.House;
import model.Room;
import model.World;
import view.HuntTheWumpusView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

/**
 * Class that represents the game controller.
 *
 * @author Gattico Alessandro
 * @see ActionListener
 * @see KeyListener
 * @see PropertyChangeListener
 */
public class GameController implements ActionListener, KeyListener {

    private static final int DEFAULTDIMENSION = 10;
    private final HuntTheWumpusView view;
    private final House house;
    private boolean visibleSolution;
    private PropertyChangeListener playerListener;
    private PropertyChangeListener countdownListener;
    private PropertyChangeListener gameListener;
    private Random random;
    private boolean updating;

    /**
     * Creates the house, the view and the listeners.
     */
    public GameController() {
        this.house = new House(DEFAULTDIMENSION);
        this.view = new HuntTheWumpusView(this.house);
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
                    house.checkShoot((Room) evt.getNewValue());
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

        this.countdownListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("Countdown")) {
                    if (house.isGameOn() && house.getRobot().isPlaying()) {
                        house.getRobot().move(Directions.values()[random.nextInt(4)], true);
                    }
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

    /**
     * Adds the listeners to the buttons of the view and registers the PropertyChangeListeners.
     */
    private void addListeners() {
        this.view.getControlView().getPlayButton().addActionListener(this);
        this.view.getControlView().getSolutionButton().addActionListener(this);
        this.view.getControlView().getRandomMoveButton().addActionListener(this);
        this.view.getControlView().addPropertyChangeListener(this.countdownListener);
        this.house.getPlayer().addPropertyChangeListener(this.playerListener);
        this.house.addPropertyChangeListener(this.gameListener);
        this.view.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int choice;

        switch (e.getActionCommand()) {
            case "Play":
                if (this.house.getPlayer().isPlaying()) {
                    this.view.getControlView().timerStop();
                    choice = JOptionPane.showConfirmDialog(null, "Want to restart the game?", "Hunt the Wumpus",
                            JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        this.house.resetGame();
                        this.view.resetViews();
                        this.startGame();
                    } else {
                        this.view.getControlView().timerStart();
                    }
                } else if (this.updating && !this.house.getPlayer().isPlaying()) {
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
            case "Random move":
                if (this.house.getPlayer().isPlaying()) {
                    house.getPlayer().move(Directions.values()[random.nextInt(4)], true);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Starts the game and the timer.
     */
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
        if (!this.updating && this.house.getPlayer().isPlaying()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    this.house.getPlayer().turn(Directions.TURNLEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    this.house.getPlayer().turn(Directions.TURNRIGHT);
                    break;
                case KeyEvent.VK_W:
                    this.house.getPlayer().move(Directions.MOVEFORWARD, false);
                    break;
                case KeyEvent.VK_S:
                    this.house.getPlayer().move(Directions.MOVEBACKWARDS, false);
                    break;
                case KeyEvent.VK_A:
                    this.house.getPlayer().move(Directions.MOVELEFT, false);
                    break;
                case KeyEvent.VK_D:
                    this.house.getPlayer().move(Directions.MOVERIGHT, false);
                    break;
                case KeyEvent.VK_SPACE:
                    this.house.getPlayer().shoot();
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
