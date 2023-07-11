import java.util.Arrays;

public class ArrayEquality {
    public static boolean areArraysEqual(int[] array1, int[] array2) {
        int length1 = array1.length;
        int length2 = array2.length;

        // If lengths of arrays are not equal, they are not equal
        if (length1 != length2) {
            return false;
        }

        // Sort both arrays
        Arrays.sort(array1);
        Arrays.sort(array2);

        // Linearly compare elements
        for (int i = 0; i < length1; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        // If all elements are the same
        return true;
    }

    public static void main(String[] args) {
        int[] array1 = {3, 5, 2, 5, 2};
        int[] array2 = {2, 3, 5, 5, 2};

        if (areArraysEqual(array1, array2)) {
            System.out.println("The arrays are equal");
        } else {
            System.out.println("The arrays are not equal");
        }
    }
}
