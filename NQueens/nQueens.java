import java.util.Arrays;
import java.util.List;

public class nQueens{
    static boolean isLegalPosition(int[] board, int n) {
        boolean[] columns = new boolean[n+1];  
        boolean[] leftDiagonals = new boolean[2 * n + 1];  
        boolean[] rightDiagonals = new boolean[2 * n + 1]; 
        int length=n;
        for(int i=0; i<board.length; i++){
            if(board[i]==0){
                length=i;
                break;
            }
        }

        //start at the current row and loop through each column to see if it collides with booleans
        for(int currRow=0; currRow<length;currRow++){
            //get column at current row
            int CurrCol = board[currRow]; 
            //make sure all columns before this is false
            int current = currRow;
            while(current>0){
                if(columns[CurrCol]==true){
                    return false;
                }
                current=current-1;
            }
            columns[CurrCol]=true;
        }  
        // diagonal booleans 
        for(int currRow=0; currRow<length;currRow++){
            //get column at current row
            int CurrCol = board[currRow]; 
            //make sure all columns before this is false
            int current = currRow;
            while(current>0){
                if(leftDiagonals[CurrCol+currRow]==true){
                    return false;
                }
                current=current-1;
            }
            leftDiagonals[CurrCol+currRow]=true;
        }
        
        for(int currRow=0; currRow<length;currRow++){
            //get column at current row
            int CurrCol = board[currRow]; 
            //make sure all columns before this is false
            int current = currRow;
            while(current>0){
                if(rightDiagonals[CurrCol-currRow+n]==true){
                    return false;
                }
                current=current-1;
            }
            rightDiagonals[CurrCol-currRow+n]=true;
        }     
    
        return true;
    }
    


        static public int[] nextLegalPosition(int[] board, int n){
            boolean legalboard = false;
            int CurrRow = n;
            for (int i=0; i<board.length; i++){
                if(board[i]==0){
                    CurrRow = i; //first place where its empty
                    break;
                }
            }
            boolean noSol = true;
            int rownum = n;
            if(CurrRow == n){ 
                while(noSol == true&&rownum>0){
                    int oldcol = board[rownum-1]; //oldcol=3, i=
                    board[rownum-1] = 0;
                    for(int i=oldcol+1; i<n; i++){
                        //go through each col to see if its possible
                        //cant be the same as old
                        board[rownum-1] = (i);
                        if(isLegalPosition(board, n)==true){
                            noSol=false;
                            return board;
                        } else{
                            board[rownum-1] = 0;
                        }
                    }
                    rownum= rownum-1;
                

                }
            }
            //CurrRow = last row with queen
            while(!legalboard){
                legalboard = true;
                //check if board is valid then if its not, make board(i) = 0, recalculate
                if(isLegalPosition(board, n)==false){
                    board[CurrRow-1] = 0;
                    CurrRow--;
                    legalboard = false;
                }
            }
            //we have board that is legal
            //go to each column
            for (int i=0; i<n; i++){
                board[CurrRow] = i+1;
                if(isLegalPosition(board, n)==true){
                    return board;
                }
            } 

            return board;
        }
        // Maximum size for arrays
    static int[] ld = new int[30]; // Left diagonals
    static int[] rd = new int[30]; // Right diagonals
    static int[] cl = new int[30]; // Columns

    static void initialize(int n) {
        ld = new int[2 * n]; // Adjust size based on n
        rd = new int[2 * n];
        cl = new int[n + 1]; // Columns are 1-indexed
    }


    // Recursive utility function to solve N-Queens
    static boolean solveNQUtil(int[] board, int row, int n) {
        // Base case: If all queens are placed
        if (row == n) {
            return true;
        }

        // Consider each column in the current row
        for (int col = 1; col <= n; col++) {
            // Check if the queen can be placed
            if (ld[row - col + n - 1] != 1 && rd[row + col] != 1 && cl[col] != 1) {
                // Place the queen
                board[row] = col;
                ld[row - col + n - 1] = rd[row + col] = cl[col] = 1;

                // Recur to place the rest of the queens
                if (solveNQUtil(board, row + 1, n)) {
                    return true;
                }

                // Backtrack: Remove the queen
                board[row] = 0;
                ld[row - col + n - 1] = rd[row + col] = cl[col] = 0;
            }
        }

        // If no valid placement is found in this row
        return false;
    }

    // Function to solve the N-Queens problem
    static boolean solveNQ(int n) {
        if(n<4){
            System.out.println("Solution does not exist for n= " + n);
            return false;
        }
        initialize(n); // Reset arrays for the new n
        int[] board = new int[n]; // Each index is a row, value is the column
        Arrays.fill(board, 0);

        if (!solveNQUtil(board, 0, n)) {
            System.out.println("Solution does not exist for n = " + n);
            return false;
        }

        // Print the solution
        System.out.print("Solution for n = " + n + ": ");
        for(int i=0; i<board.length; i++){
            System.out.print(board[i] + " ");
        }
        return true;
    }

        public static void main(String[] args){    
                int[] board = {1,6,0,0,0,0,0,0};
                //boolean yes = isLegalPosition(board, 8);
                //System.out.println(yes);
                board = nextLegalPosition(board, 8);
                for (int i=0; i<board.length; i++){
                    System.out.print(board[i] + " ");
                }
                for (int i=0; i<15; i++){
                    solveNQ(i);
                    System.out.println();
                }



    }
}