import java.util.Scanner;

public class Palindrome {
    public static boolean isPalindrome(String s) {
        s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        StringBuilder reversed = new StringBuilder(s).reverse();
        return s.equals(reversed.toString());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = scanner.nextLine();
        scanner.close();
        if (isPalindrome(str)) {
            System.out.println("'" + str + "' is a palindrome.");
        } else {
            System.out.println("'" + str + "' is not a palindrome.");
        }
    }
}
