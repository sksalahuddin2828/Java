import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4};
        int length = 1 << array.length;

        for (int var = 0; var < length; var++) {
            int number = var;
            int position = 0;
            List<Integer> storeArray = new ArrayList<>();
            
            while (number != 0) {
                if ((number & (1 << 0)) != 0) {
                    storeArray.add(array[position]);
                }
                number = number >> 1;
                position = position + 1;
            }
            
            System.out.println(storeArray);
        }
    }
}
