import java.util.Scanner;

public class BMICalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your height in centimeters: ");
        float height = scanner.nextFloat();

        System.out.print("Enter your weight in Kg: ");
        float weight = scanner.nextFloat();

        height = height / 100;
        float bmi = weight / (height * height);

        System.out.printf("Your Body-Mass index is: %.2f%n", bmi);

        if (bmi > 0) {
            if (bmi <= 16) {
                System.out.println("You are severely under-weight.");
            } else if (bmi <= 18.5) {
                System.out.println("You are under-weight.");
            } else if (bmi <= 25) {
                System.out.println("You are Healthy.");
            } else if (bmi <= 30) {
                System.out.println("You are overweight.");
            } else {
                System.out.println("You are severely overweight.");
            }
        } else {
            System.out.println("Please enter valid details.");
        }

        scanner.close();
    }
}
