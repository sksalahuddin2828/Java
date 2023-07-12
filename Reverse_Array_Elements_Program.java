import java.util.Scanner;

public class ReverseArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the total number you want to enter: ");
        int number = scanner.nextInt();

        int[] array = new int[number];
        for (int i = 0; i < number; i++) {
            System.out.print("Enter the element " + (i + 1) + ": ");
            array[i] = scanner.nextInt();
        }

        for (int i = 0; i < number / 2; i++) {
            int temp = array[i];
            array[i] = array[number - 1 - i];
            array[number - 1 - i] = temp;
        }

        System.out.println("\nReverse all elements of the array:");
        for (int element : array) {
            System.out.println(element);
        }
    }
}
