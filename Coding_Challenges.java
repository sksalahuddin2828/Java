//----------------------------------------------------------------Java Coding Challenges--------------------------------------------------------

// Java Coding Challenges on Numbers 
// Write a program in Java to -

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

public class Main {
    public static void main(String[] args) {
        int number = 12345;
        int reversed_number = 0;

        while (number != 0) {
            reversed_number = reversed_number * 10 + number % 10;
            number = number / 10;
        }

        System.out.print(reversed_number);
        return;
    }
}

//----------------------------------------------------------------------------------------------------------------------------------------------

// 3. Printing the Fibonacci Series using Recursion:

public class Main {
    static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public static void main(String[] args) {
        int n = 10;
        System.out.print("Fibonacci series: ");

        for (int i = 0; i < n; i++) {
            System.out.print(fibonacci(i) + " ");
        }

        return;
    }
}

//----------------------------------------------------------------------------------------------------------------------------------------------

// 4. Returning the Nth Value from the Fibonacci Sequence:

public class Main {
    static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public static void main(String[] args) {
        int n = 10;
        int fibonacci_number = fibonacci(n);
        System.out.print(fibonacci_number);
        return;
    }
}

//----------------------------------------------------------------------------------------------------------------------------------------------

// 5. Finding the Average of Numbers:

public class Main {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40, 50};
        int size = numbers.length;
        int sum = 0;

        for (int i = 0; i < size; i++) {
            sum += numbers[i];
        }

        float average = (float)sum / size;
        System.out.print("Average: " + average);
        return;
    }
}
