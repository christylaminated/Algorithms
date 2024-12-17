import java.util.Scanner;

public class palindrome {
    public static void main(String[] args) {
        // Prompt user for input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string to check if it's a palindrome:");
        System.out.print("> ");
        
        // Read and sanitize input
        String original = scanner.nextLine().replaceAll("[^a-zA-Z]", "").toLowerCase();
        scanner.close();

        // Reverse the sanitized string
        StringBuilder reversed = new StringBuilder(original).reverse();

        // Check if original string matches the reversed string
        if (original.equals(reversed.toString())) {
            System.out.println("The input is a palindrome!");
        } else {
            System.out.println("The input is not a palindrome.");
        }
    }
}
