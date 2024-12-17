// File: DoubleTrouble.java
import java.util.Random;

public class DoubleTrouble {
    private int greenMarkers = 3;
    private int yellowMarkers = 7;
    private int orangeMarkers = 5;
    private boolean isPlayerTurn;
    private final Random random = new Random();

    public DoubleTrouble() {
        // Decide who goes first randomly for each game start
        isPlayerTurn = random.nextBoolean();
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void switchTurn() {
        isPlayerTurn = !isPlayerTurn;
    }

    public int getGreenMarkers() {
        return greenMarkers;
    }

    public int getYellowMarkers() {
        return yellowMarkers;
    }

    public int getOrangeMarkers() {
        return orangeMarkers;
    }

    public boolean isGameOver() {
        return greenMarkers == 0 && yellowMarkers == 0 && orangeMarkers == 0;
    }

    public String getWinner() {
        return isPlayerTurn ? "Computer" : "Player";
    }

    public boolean removeMarkers(int color, int count) {
        if (count <= 0) return false;
        
        switch (color) {
            case 1 -> { if (count <= greenMarkers) greenMarkers -= count; else return false; }
            case 2 -> { if (count <= yellowMarkers) yellowMarkers -= count; else return false; }
            case 3 -> { if (count <= orangeMarkers) orangeMarkers -= count; else return false; }
            default -> { return false; }
        }
        return true;
    }

    public void computerMove() {
        int xorSum = greenMarkers ^ yellowMarkers ^ orangeMarkers;
        if (xorSum != 0) {
            makeWinningMove(xorSum);
        } else {
            makeRandomMove();
        }
    }

    private void makeWinningMove(int xorSum) {
        if (greenMarkers > (yellowMarkers ^ orangeMarkers)) {
            removeMarkers(1, greenMarkers - (yellowMarkers ^ orangeMarkers));
        } else if (yellowMarkers > (greenMarkers ^ orangeMarkers)) {
            removeMarkers(2, yellowMarkers - (greenMarkers ^ orangeMarkers));
        } else {
            removeMarkers(3, orangeMarkers - (greenMarkers ^ yellowMarkers));
        }
        System.out.println("Computer made a strategic move!");
    }

    private void makeRandomMove() {
        int color, count;
        do {
            color = random.nextInt(3) + 1;
            count = random.nextInt(3) + 1;
        } while (!removeMarkers(color, count));
        System.out.println("Computer made a random move.");
    }

    public void resetGame() {
        greenMarkers = 3;
        yellowMarkers = 7;
        orangeMarkers = 5;
        isPlayerTurn = random.nextBoolean();
    }
}
