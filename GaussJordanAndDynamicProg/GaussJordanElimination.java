public class GaussJordanElimination {
    private static final double[][] matrix = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2047},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},
            {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 12},
            {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 48},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 384},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1536},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5},
            {0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 50},
            {0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1952},
            {11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 4083},
            {11, -10, 9, -8, 7, -6, 5, -4, 3, -2, 1, 459} // Row 10
    };

    public static void main(String[] args) {
        Elimination();

        System.out.println("Answer:");
        for (double[] row : matrix) {
            double elem = row[row.length - 1];
            System.out.print((int) Math.round(elem) + " ");
        }
    }

    private static void Elimination() {
        int len = matrix.length;

        for (int cRow = 0; cRow < len; cRow++) {

            int pivotRow = findPivotRow(cRow);

            if (pivotRow != cRow) {
                swapRows(cRow, pivotRow);
            }
            normalizeRow(cRow);
            eliminateOtherRows(cRow);
        }
    }

    private static int findPivotRow(int col) {
        int pivotRow = col;
        for (int i = col + 1; i < matrix.length; i++) {
            if (Math.abs(matrix[pivotRow][col]) < Math.abs(matrix[i][col])) {
                pivotRow = i;
            }
        }
        return pivotRow;
    }

    private static void swapRows(int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    private static void normalizeRow(int row) {
        double pivot = matrix[row][row];
        if (Math.abs(pivot) < 1e-9) {
            System.out.println("Matrix is singular or nearly singular!");
            throw new ArithmeticException("Singular matrix encountered.");
        }
        for (int i = 0; i < matrix[row].length; i++) {
            matrix[row][i] /= pivot;
        }
    }

    private static void eliminateOtherRows(int pivotRow) {
        for (int row = 0; row < matrix.length; row++) {
            if (row == pivotRow || Math.abs(matrix[row][pivotRow]) < 1e-9) {
                continue;
            }

            double scale = matrix[row][pivotRow];
            for (int col = 0; col < matrix[row].length; col++) {
                matrix[row][col] -= scale * matrix[pivotRow][col];
            }
        }
    }
}
