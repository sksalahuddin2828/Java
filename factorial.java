import java.util.Scanner;

public class Factorial {
    public static void main(String[] args) {
        int number, result;

        Scanner input = new Scanner(System.in);
        System.out.print("Enter a non-negative number: ");
        number = input.nextInt();

        result = calculateFactorial(number);
        System.out.println("Factorial of " + number + " = " + result);
    }

    public static int calculateFactorial(int n) {
        if (n > 1) {
            return n * calculateFactorial(n - 1);
        } else {
            return 1;
        }
    }
}
