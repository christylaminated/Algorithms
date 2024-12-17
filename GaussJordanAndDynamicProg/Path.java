import java.util.Arrays;
public class Path {
    private static final int[][] map = {
            {84, 99, 68, 75, 98, 44, 33, 96},
            {93, 53, 24, 46, 86, 1, 41, 10},
            {7, 30, 51, 65, 27, 94, 97, 83},
            {12, 67, 88, 22, 64, 47, 71, 56},
            {15, 92, 70, 13, 48, 77, 11, 91},
            {63, 16, 4, 31, 25, 17, 59, 32},
            {74, 40, 37, 79, 23, 14, 5, 78},
            {21, 95, 20, 82, 66, 52, 89, 35}
    };
    private static final int[][] summedMap = new int[map.length][map[0].length];
    static {
        for (int i = 0; i < map[0].length; i++) {
            summedMap[i] = map[i].clone();
        }
    }

    public static void main(String[] args) {
        findPath();
    }

    private static void findPath() {
        int[] path = new int[summedMap.length + 1];

        for (int row = summedMap.length - 2; row >= 0; row--) {
            for (int col = 0; col < summedMap[0].length; col++) {
                int[] possibleMoves = new int[3];
                for (int offset = -1; offset <= 1; offset++) {
                    int adjacentIndex = col + offset;
                    if (adjacentIndex >= 0 && adjacentIndex < summedMap[0].length) {
                        possibleMoves[offset + 1] = summedMap[row + 1][adjacentIndex];
                    }
                }
                int maxMoveValue = Arrays.stream(possibleMoves).max().orElseThrow();
                summedMap[row][col] += maxMoveValue;
            }
        }

        Arrays.stream(summedMap).forEach(row -> System.out.println(Arrays.toString(row)));

        int startCol = getMaxIndex(summedMap[0]);
        path[0] = startCol;
        path[path.length - 1] = summedMap[0][startCol];

        for (int currentRow = 0; currentRow < map.length - 1; currentRow++) {
            int[] adjacentGems = new int[3];

            for (int offset = -1; offset <= 1; offset++) {
                int colToCheck = startCol + offset;

                if (colToCheck >= 0 && colToCheck < summedMap[0].length) {
                    adjacentGems[offset + 1] = summedMap[currentRow + 1][colToCheck];
                }
            }

            int offsetWithMaxGems = getMaxIndex(adjacentGems) - 1;
            startCol += offsetWithMaxGems;
            path[currentRow + 1] = startCol;
        }


        System.out.printf("Starting vault number: %d \n", (path[0] + 1));
        System.out.printf("Total number of gems collected: %d\n", path[path.length - 1]);
        System.out.printf("Final vault number: %d\n", (path[path.length - 2] + 1));
        System.out.println("Path of tiles from start to finish: ");

        for (int rowIndex = 0; rowIndex <= path.length - 2; rowIndex++) {
            System.out.printf("(Row %d, Vault %d) - Contains %d Gems\n",
                    rowIndex + 1, (path[rowIndex] + 1), map[rowIndex][path[rowIndex]]);
        }
    }

    private static int getMaxIndex(int[] arr) {
        int maxSumIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            maxSumIndex = arr[i] > arr[maxSumIndex] ? i : maxSumIndex;
        }
        return maxSumIndex;
    }
}

