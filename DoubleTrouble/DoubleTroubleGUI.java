import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoubleTroubleGUI extends JFrame {
    private final DoubleTrouble game;
    private final JLabel statusLabel;
    private final JPanel greenPanel, yellowPanel, orangePanel;
    private JButton greenButton, yellowButton, orangeButton;
    private int playerWins = 0;
    private int computerWins = 0;
    private int tournamentGoal;

    public DoubleTroubleGUI() {
        game = new DoubleTrouble();
        setTitle("Double Trouble Game - Player vs. Computer");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Welcome to NIM! Solved by Charles L. Bouton, Tournament", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        JPanel markerPanel = new JPanel(new GridLayout(1, 3));

        // Create interactive panels for each marker color
        greenPanel = createMarkerPanel(Color.GREEN, game.getGreenMarkers(), 1);
        yellowPanel = createMarkerPanel(Color.YELLOW, game.getYellowMarkers(), 2);
        orangePanel = createMarkerPanel(Color.ORANGE, game.getOrangeMarkers(), 3);

        markerPanel.add(greenPanel);
        markerPanel.add(yellowPanel);
        markerPanel.add(orangePanel);

        add(markerPanel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Start New Tournament");
        restartButton.addActionListener(e -> startTournament());
        add(restartButton, BorderLayout.SOUTH);

        setVisible(true);
        startTournament();  // Start by selecting tournament length and first player
        updateGameState();
    }

    // Create a marker panel with color and count, adding buttons for player moves
    private JPanel createMarkerPanel(Color color, int markerCount, int colorCode) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(color);

        JLabel countLabel = new JLabel("Count: " + markerCount);
        JButton removeButton = new JButton("Remove Markers");

        switch (colorCode) {
            case 1 -> greenButton = removeButton;
            case 2 -> yellowButton = removeButton;
            case 3 -> orangeButton = removeButton;
        }

        removeButton.addActionListener(e -> handlePlayerMove(colorCode));
        panel.add(countLabel);
        panel.add(removeButton);
        return panel;
    }

    // Prompts the user to select tournament length and who goes first
    private void startTournament() {
        String[] options = {"Best of 3", "Best of 5", "Best of 7"};
        int choice = JOptionPane.showOptionDialog(this, "Select Tournament Length:", "Tournament Setup",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        tournamentGoal = (choice + 1) * 2 - 1;  // Set goal to 3, 5, or 7 wins

        playerWins = 0;
        computerWins = 0;
        startNewGame();
    }

    // Starts a new game within the tournament
    private void startNewGame() {
        game.resetGame();
        updateGameState();
        chooseFirstTurn();
    }

    // Prompts user to choose who goes first in the current game
    private void chooseFirstTurn() {
        int choice = JOptionPane.showOptionDialog(
                this,
                "Who goes first?",
                "Choose Turn Order",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Player", "Computer"},
                "Player"
        );

        if (choice == JOptionPane.NO_OPTION) {
            game.switchTurn();  // Set to computer's turn
            handleComputerMove();  // Start with computer's move
        } else {
            statusLabel.setText("Your Turn");
            toggleButtons(true);  // Enable buttons for player's turn
        }
    }

    // Handle player move
    private void handlePlayerMove(int color) {
        int count = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of markers to remove:"));

        if (!game.removeMarkers(color, count)) {
            JOptionPane.showMessageDialog(this, "Invalid move. Try again.");
            return;
        }

        updateGameState();

        if (game.isGameOver()) {
            playerWins++;
            checkTournamentWinner();
            return;
        }

        toggleButtons(false);  // Disable buttons during computer's turn
        game.switchTurn();
        SwingUtilities.invokeLater(this::handleComputerMove);  // Trigger computer move after turn switch
    }

    // Handle computer move with XOR strategy
    private void handleComputerMove() {
        if (!game.isPlayerTurn()) {
            statusLabel.setText("Computer's Turn...");
            toggleButtons(false);  // Ensure buttons stay disabled during computer's turn

            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.computerMove();
                    updateGameState();

                    if (game.isGameOver()) {
                        computerWins++;
                        checkTournamentWinner();
                    } else {
                        game.switchTurn();
                        statusLabel.setText("Your Turn");
                        toggleButtons(true);  // Re-enable buttons for player's turn
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    // Check if either player has won the tournament
    private void checkTournamentWinner() {
        if (playerWins == (tournamentGoal + 1) / 2) {
            JOptionPane.showMessageDialog(this, "Congratulations! You won the tournament!");
            endTournament();
        } else if (computerWins == (tournamentGoal + 1) / 2) {
            JOptionPane.showMessageDialog(this, "The computer won the tournament. Better luck next time!");
            endTournament();
        } else {
            startNewGame();  // Continue with the next game in the tournament
        }
    }

    // End the tournament and reset state for a new tournament
    private void endTournament() {
        playerWins = 0;
        computerWins = 0;
        statusLabel.setText("Tournament ended. Click 'Start New Tournament' to play again.");
        toggleButtons(false);  // Ensure buttons are disabled until a new tournament starts
    }

    // Updates game state visually and refreshes marker counts
    private void updateGameState() {
        greenPanel.removeAll();
        greenPanel.add(createMarkerPanel(Color.GREEN, game.getGreenMarkers(), 1));

        yellowPanel.removeAll();
        yellowPanel.add(createMarkerPanel(Color.YELLOW, game.getYellowMarkers(), 2));

        orangePanel.removeAll();
        orangePanel.add(createMarkerPanel(Color.ORANGE, game.getOrangeMarkers(), 3));

        revalidate();
        repaint();
    }

    // Enable or disable marker buttons to prevent multiple moves
    private void toggleButtons(boolean enable) {
        greenButton.setEnabled(enable);
        yellowButton.setEnabled(enable);
        orangeButton.setEnabled(enable);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DoubleTroubleGUI::new);
    }
}
