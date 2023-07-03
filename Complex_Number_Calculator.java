import java.util.Scanner;

public class Complex {
    private double real;
    private double imag;

    public void set_data() {
        Scanner scn = new Scanner(System.in);
        System.out.print("Enter the real value of the complex number: ");
        real = scn.nextDouble();
        System.out.print("Enter the imaginary value of the complex number: ");
        imag = scn.nextDouble();
    }

    public void add(double a, double b, double c, double d) {
        real = a + c;
        imag = b + d;
    }

    public void subtract(double a, double b, double c, double d) {
        real = a - c;
        imag = b - d;
    }

    public void multiply(double a, double b, double c, double d) {
        real = a * c - b * d;
        imag = a * d + b * c;
    }

    public void divide(double a, double b, double c, double d) {
        real = (a * c + b * d) / (c * c + d * d);
        imag = (b * c - a * d) / (c * c + d * d);
    }

    public void get_data() {
        if (imag >= 0) {
            System.out.println(real + "+" + imag + "i");
        } else {
            System.out.println(real + "" + imag + "i");
        }
    }

    public static void main(String[] args) {
        Scanner scn1 = new Scanner(System.in);
        Complex x1 = new Complex();
        Complex x2 = new Complex();
        Complex addition = new Complex();
        Complex subtraction = new Complex();
        Complex multiplication = new Complex();
        Complex division = new Complex();

        x1.set_data();
        x2.set_data();

        System.out.println("Complex number 1 is:");
        x1.get_data();
        System.out.println("Complex number 2 is:");
        x2.get_data();

        int ans = 1;
        while (ans == 1) {
            System.out.println("Choose the operation to perform:");
            System.out.println("1. Addition\n2. Subtraction\n3. Multiplication\n4. Division");
            int a = scn1.nextInt();

            if (a == 1) {
                addition.add(x1.real, x1.imag, x2.real, x2.imag);
                System.out.println("Addition of Complex 1 and Complex 2 is:");
                addition.get_data();
            } else if (a == 2) {
                subtraction.subtract(x1.real, x1.imag, x2.real, x2.imag);
                System.out.println("Subtraction of Complex 2 from Complex 1 is:");
                subtraction.get_data();
            } else if (a == 3) {
                multiplication.multiply(x1.real, x1.imag, x2.real, x2.imag);
                System.out.println("Multiplication of Complex 1 and Complex 2 is:");
                multiplication.get_data();
            } else if (a == 4) {
                if (x2.real == 0 && x2.imag == 0) {
                    System.out.println("Can't divide by zero");
                } else {
                    division.divide(x1.real, x1.imag, x2.real, x2.imag);
                    System.out.println("On division of Complex 1 by Complex 2, we get:");
                    division.get_data();
                }
            } else {
                System.out.println("Invalid option chosen!");
            }

            System.out.print("Do you want to check more? (1 for yes / 2 for no): ");
            ans = scn1.nextInt();
            if (ans == 2) {
                break;
            }
        }

        System.out.println("\nThank you");
    }
}
