import java.util.Scanner;

public class SquareRoot {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        double num = scanner.nextDouble();

        // Find the square root of the number
        double realPart = Math.sqrt(Math.abs(num));
        double imaginaryPart = Math.sqrt(-num);

        // Display the result
        if (imaginaryPart == 0) {
            System.out.printf("The square root of %.3f is %.3f%n", num, realPart);
        } else {
            System.out.printf("The square root of %.3f is %.3f+%.3fi%n", num, realPart, imaginaryPart);
        }
    }
}
