package view;

import model.House;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ControlView extends JPanel {
    private static final int DEFAULTTIME = 11;
    private final MyButton playButton;
    private final MyButton solutionButton;
    private final MyButton randomMoveButton;
    private final MyTextArea popups;
    private final MyTextArea timer;
    private final MyTextArea backpack;
    private final MyTextArea moves;
    public MyTextArea commands;
    private Timer countdown;
    private int elapsedTime;
    private int seconds;
    private PropertyChangeSupport support;

    public ControlView() {
        super(new GridBagLayout());
        this.playButton = new MyButton("Play");
        this.solutionButton = new MyButton("Show solution");
        this.randomMoveButton = new MyButton("Random move");
        this.popups = new MyTextArea();
        this.timer = new MyTextArea();
        this.commands = new MyTextArea();
        this.backpack = new MyTextArea();
        this.moves = new MyTextArea();
        this.timer.setForeground(Color.GREEN);
        this.timer.setFont(new Font("Monospaced", Font.BOLD, 20));
        this.seconds = DEFAULTTIME;
        this.elapsedTime = DEFAULTTIME;
        this.support = new PropertyChangeSupport(this);
        this.countdown = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                elapsedTime = elapsedTime - 1000;
                seconds = --seconds;
                timer.setText(String.valueOf(seconds));
                if (seconds == 0) {
                    countdown.stop();
                    support.firePropertyChange("Countdown", 1, 0);
                }
            }
        });
        this.initializeView();

        this.setVisible(true);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }


    private void initializeView() {
        GridBagConstraints gridBag = new GridBagConstraints();

        gridBag.weightx = 1;
        gridBag.weighty = 1;
        gridBag.gridx = 4;
        gridBag.gridy = 0;
        gridBag.fill = GridBagConstraints.BOTH;
        this.add(this.playButton, gridBag);

        gridBag.gridx = 4;
        gridBag.gridy = 1;
        gridBag.fill = GridBagConstraints.BOTH;
        this.add(this.solutionButton, gridBag);

        gridBag.gridx = 4;
        gridBag.gridy = 2;
        gridBag.fill = GridBagConstraints.BOTH;
        this.add(this.randomMoveButton, gridBag);

        gridBag.gridx = 1;
        gridBag.gridy = 0;
        this.add(this.timer, gridBag);

        gridBag.gridx = 1;
        gridBag.gridy = 1;
        this.add(this.backpack, gridBag);

        gridBag.gridx = 1;
        gridBag.gridy = 2;
        this.add(this.moves, gridBag);

        gridBag.weightx = 2;
        gridBag.gridx = 0;
        gridBag.gridy = 0;
        gridBag.gridheight = 3;
        this.add(this.popups, gridBag);

        gridBag.gridx = 2;
        gridBag.gridy = 0;
        this.add(this.commands, gridBag);
    }


    public void timerStart() {
        this.countdown.start();
    }


    public void timerStop() {
        this.countdown.stop();
    }


    public void timerReset() {
        this.countdown.stop();
        this.elapsedTime = DEFAULTTIME;
        this.seconds = DEFAULTTIME;
    }


    public JButton getPlayButton() {
        return this.playButton;
    }


    public JButton getSolutionButton() {
        return this.solutionButton;
    }


    public JButton getRandomMoveButton() {
        return this.randomMoveButton;
    }


    public void updateView(House house) {
        StringBuilder popups = new StringBuilder();

        for (String message : house.getMessages()) {
            popups.append(message);
        }

        if (house.getRobot().isPlaying()) {
            if (!popups.toString().equals("")) {
                this.popups.setText(popups.toString());
            } else {
                this.popups.setText("Everything is quiet.");
            }

            this.moves.setText("Moves " + Integer.toString(house.getRobot().getMoves()));
            this.commands.setText(
                    "Move the player with W\nTurn the player with <- and -> arrows\nRepair items with the " +
                            "spacebar\n");
        }
    }


    protected void resetView() {
        this.timer.setText("");
        this.backpack.setText("");
        this.commands.setText("");
        this.moves.setText("");
        this.popups.setText("");
    }
}

class MyTextArea extends JTextArea {

    public MyTextArea() {
        super();
        this.setBackground(Color.BLACK);
        this.setForeground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.red, 1));
        this.setFont(new Font("Monospaced", Font.BOLD, 15));
        this.setEditable(false);
        this.setMargin(new Insets(5, 100, 0, 0));
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.setAlignmentY(CENTER_ALIGNMENT);
    }
}

class MyButton extends JButton {

    public MyButton(String name) {
        super(name);
        this.setBackground(Color.darkGray);
        this.setForeground(Color.green);
        this.setOpaque(true);
        this.setFocusable(false);
        this.setFont(new Font("Monospace", Font.BOLD, 20));
    }
}
