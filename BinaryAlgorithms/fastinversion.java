import java.util.Scanner;

public class fastinversion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many numbers would you like to input?");
        System.out.println("Press Enter to use the default size of 8.");
        System.out.print("> ");

        int arraySize = 8; // Default array size
        String userInput = scanner.nextLine();

        if (!userInput.isBlank()) {
            try {
                arraySize = Integer.parseInt(userInput.trim());
            } catch (NumberFormatException e) {
                scanner.close();
                throw new IllegalArgumentException("Invalid input! Please enter a valid integer.");
            }
        }

        System.out.println("Enter " + arraySize + " numbers, one per line:");
        int[] numbers = new int[arraySize];

        for (int i = 0; i < arraySize; i++) {
            System.out.print("> ");
            numbers[i] = scanner.nextInt();
        }

        int totalInversions = calculateInversions(numbers);
        System.out.println("Total inversions: " + totalInversions);

        scanner.close();
    }

    public static int calculateInversions(int[] array) {
        if (array == null || array.length <= 1) {
            return 0;
        }

        int[] tempArray = new int[array.length];
        return mergeSortAndCount(array, tempArray, 0, array.length - 1);
    }

    private static int mergeSortAndCount(int[] array, int[] tempArray, int left, int right) {
        int inversionCount = 0;

        if (left < right) {
            int mid = left + (right - left) / 2;

            // Recursively count inversions in the left and right halves
            inversionCount += mergeSortAndCount(array, tempArray, left, mid);
            inversionCount += mergeSortAndCount(array, tempArray, mid + 1, right);

            // Count inversions during the merge process
            inversionCount += mergeAndCount(array, tempArray, left, mid, right);
        }

        return inversionCount;
    }

    private static int mergeAndCount(int[] array, int[] tempArray, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;
        int inversionCount = 0;

        // Merge arrays and count inversions
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                tempArray[k++] = array[i++];
            } else {
                tempArray[k++] = array[j++];
                inversionCount += (mid - i + 1); // Count inversions
            }
        }

        // Copy remaining elements from the left half
        while (i <= mid) {
            tempArray[k++] = array[i++];
        }

        // Copy remaining elements from the right half
        while (j <= right) {
            tempArray[k++] = array[j++];
        }

        // Copy the merged array back to the original array
        System.arraycopy(tempArray, left, array, left, right - left + 1);

        return inversionCount;
    }
}
