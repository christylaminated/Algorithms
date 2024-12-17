import java.util.ArrayList;
import java.util.List;

class NQueensCounter {

    // Add queens from left to right
    static void nQueenUtil(int CurrCol, int n, int[] board, int[] count,
                           boolean[] row, boolean[] diag1, boolean[] diag2) {
        // Base case: If all columns are processed
        if (CurrCol > n) {
            count[0]++; // Increment the solution count
            return;
        }

        // Try placing a queen in each row for the current column
        for (int CurrRow = 0; CurrRow < n; CurrRow++) {
            // Check if placing a queen at (CurrRow, CurrCol) is valid
            if (!row[CurrRow] && !diag1[CurrRow + CurrCol] && !diag2[CurrRow - CurrCol + n]) {
                // Place the queen
                board[CurrCol - 1] = CurrRow + 1; // Store 1-based column position
                row[CurrRow] = true;
                diag1[CurrRow + CurrCol] = true;
                diag2[CurrRow - CurrCol + n] = true;

                // Recur to place the next queen
                nQueenUtil(CurrCol + 1, n, board, count, row, diag1, diag2);

                // Backtrack: Remove the queen
                board[CurrCol - 1] = 0;
                row[CurrRow] = diag1[CurrRow + CurrCol] = diag2[CurrRow - CurrCol + n] = false;
            }
        }
    }

    // Counts the number of solutions for the n-Queens problem
    static int countNQueens(int n) {
        int[] count = {0}; // Array to store the count of solutions
        int[] board = new int[n]; // Board array where each index is a column
        boolean[] row = new boolean[n];
        boolean[] diag1 = new boolean[2 * n];
        boolean[] diag2 = new boolean[2 * n];

        // Start solving from the first column
        nQueenUtil(1, n, board, count, row, diag1, diag2);
        return count[0];
    }

    public static void main(String[] args) {
        int test = countNQueens(15);
        System.out.println("Number of solutions for n = 15: " + test);
        for (int i=4; i<21; i++){
            int result = countNQueens(i);
            System.out.println("Number of solutions for n = " + i + ": " + result);
        }
    }
}

