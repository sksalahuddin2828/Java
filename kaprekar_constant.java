import java.util.Scanner;
import java.util.Arrays;

public class KaprekarConstant {
    public static int kaprekarConstant(int n) {
        int count = 0;
        while (n != 6174) {
            count++;
            String digits = String.format("%04d", n);
            char[] digitsArray = digits.toCharArray();
            Arrays.sort(digitsArray);
            int ascending = Integer.parseInt(new String(digitsArray));
            for (int i = 0, j = 3; i < j; i++, j--) {
                char temp = digitsArray[i];
                digitsArray[i] = digitsArray[j];
                digitsArray[j] = temp;
            }
            int descending = Integer.parseInt(new String(digitsArray));
            n = descending - ascending;
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int num = scanner.nextInt();
        scanner.close();
        int steps = kaprekarConstant(num);
        System.out.println("Number of steps to reach Kaprekar constant: " + steps);
    }
}

//-------------------------------------------------------------------------------------------------

public class KaprekarConstant {
    public static int kaprekarConstant(int n) {
        int count = 0;
        while (n != 6174) {
            count++;
            String digits = String.format("%04d", n);
            char[] digitsArray = digits.toCharArray();
            Arrays.sort(digitsArray);
            int ascending = Integer.parseInt(new String(digitsArray));
            for (int i = 0, j = 3; i < j; i++, j--) {
                char temp = digitsArray[i];
                digitsArray[i] = digitsArray[j];
                digitsArray[j] = temp;
            }
            int descending = Integer.parseInt(new String(digitsArray));
            n = descending - ascending;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(kaprekarConstant(1234));  
    }
}

// Output: 3
