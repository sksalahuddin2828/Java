//----------------------------------------------------------------Ruby Coding Challenges--------------------------------------------------------

// Ruby Coding Challenges on Numbers 
// Write a program in Ruby to -

// 1. Convert decimal numbers to octal numbers.
// 2. Reverse an integer.
// 3. Print the Fibonacci series using the recursive method.
// 4. Return the Nth value from the Fibonacci sequence.
// 5. Find the average of numbers (with explanations).
// 6. Convert Celsius to Fahrenheit.

//----------------------------------------------------------------Solution of Problem:----------------------------------------------------------

// 1. Converting Decimal Numbers to Octal Numbers:

public class Main {
    public static void main(String[] args) {
        int decimal_number = 25;
        int[] octal_number = new int[100];
        int i = 0;

        while (decimal_number > 0) {
            octal_number[i] = decimal_number % 8;
            decimal_number = decimal_number / 8;
            i++;
        }

        System.out.print("Octal number: ");

        for (int j = i - 1; j >= 0; j--) {
            System.out.print(octal_number[j]);
        }

        return;
    }
}

//----------------------------------------------------------------------------------------------------------------------------------------------

// 2. Reversing an Integer:


//----------------------------------------------------------------------------------------------------------------------------------------------

// 3. 
