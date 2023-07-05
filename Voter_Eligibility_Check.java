import java.util.Scanner;

public class VoterAgeCheck {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int age;

        while (true) {
            try {
                System.out.print("Enter your age: ");
                age = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid age entered. Please enter a valid integer age.");
            }
        }

        if (18 <= age && age <= 120) {
            System.out.println("Congratulations!");
            System.out.println("You are eligible to vote.");
        } else if (12 <= age && age < 18) {
            System.out.println("You are not yet eligible to vote.");
            System.out.println("Enjoy your teenage years!");
        } else if (0 <= age && age < 12) {
            System.out.println("You are too young to vote.");
            System.out.println("Make the most of your childhood!");
        } else if (age < 0) {
            System.out.println("Invalid age entered.");
            System.out.println("Please enter a positive value.");
        } else {
            System.out.println("You have surpassed the maximum voting age.");
            System.out.println("Thank you for your contribution to society!");
        }

        scanner.close();
    }
}


// -------------------------------END---------------------------------

// The program above is very dynamic while the program below is static

// ------------------------------START--------------------------------


import java.util.Scanner;

public class VoterAgeCheck {
    public static void main(String[] args) {
        int age;
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter their age
        System.out.print("Enter your age: ");
        age = scanner.nextInt();

        // Check if the age is within a valid range for voting
        if (age >= 18 && age <= 120) {
            System.out.println("Congratulations!");
            System.out.println("You are eligible to vote.");
        } else if (age >= 12 && age < 18) {
            System.out.println("You are not yet eligible to vote.");
            System.out.println("Enjoy your teenage years!");
        } else if (age >= 0 && age < 12) {
            System.out.println("You are too young to vote.");
            System.out.println("Make the most of your childhood!");
        } else if (age < 0) {
            System.out.println("Invalid age entered.");
            System.out.println("Please enter a positive value.");
        } else {
            System.out.println("You have surpassed the maximum voting age.");
            System.out.println("Thank you for your contribution to society!");
        }

        scanner.close();
    }
}
