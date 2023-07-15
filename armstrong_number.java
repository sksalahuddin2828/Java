import java.util.Scanner;

public class ArmstrongNumber {
    public static boolean isArmstrongNumber(int n) {
        int numDigits = String.valueOf(n).length();
        int sumOfPowers = 0;
        int temp = n;
        while (temp > 0) {
            int digit = temp % 10;
            sumOfPowers += Math.pow(digit, numDigits);
            temp /= 10;
        }
        return n == sumOfPowers;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int num = scanner.nextInt();
        scanner.close();
        if (isArmstrongNumber(num)) {
            System.out.println(num + " is an Armstrong number.");
        } else {
            System.out.println(num + " is not an Armstrong number.");
        }
    }
}
