import java.util.Scanner;

public class easyinversion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of integers you want to input.");
        System.out.println("Press Enter without typing a number to use the default size of 8.");
        System.out.print("> ");

        int arraySize = 8;
        String userInput = scanner.nextLine();

        if (!userInput.isBlank()) {
            try {
                arraySize = Integer.parseInt(userInput.trim());
            } catch (NumberFormatException e) {
                scanner.close();
                throw new IllegalArgumentException("Invalid input! Please enter a valid number.");
            }
        }

        System.out.println("Provide " + arraySize + " integers, one per line.");
        int[] numbers = new int[arraySize];

        for (int i = 0; i < arraySize; i++) {
            System.out.print("> ");
            numbers[i] = scanner.nextInt();
        }

        int inversionCount = 0;
        for (int i = 0; i < arraySize; i++) {
            for (int j = i + 1; j < arraySize; j++) {
                if (numbers[i] > numbers[j]) {
                    inversionCount++;
                }
            }
        }

        System.out.println("The total number of inversions is: " + inversionCount);
        scanner.close();
    }
}
