// Please note that the Java code uses a HashMap instead of Counter for counting character frequencies,
// and the charCount.getOrDefault(c, 0) + 1 expression is used to handle characters that are not yet present in the map.

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void findMostOccChar(String string) {
    // Create a HashMap to store characters as keys and their frequencies as values
    Map<Character, Integer> charCount = new HashMap<>();

    // Count the occurrence of each character in the string
    for (char c : string.toCharArray()) {
        charCount.put(c, charCount.getOrDefault(c, 0) + 1);
    }

    int maxCount = 0;
    // Finding the maximum occurrence of a character
    for (int count : charCount.values()) {
        if (count > maxCount) {
            maxCount = count;
        }
    }

    // Printing the most occurring character(s) and its count
    for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
        if (entry.getValue() == maxCount) {
            System.out.println("Character: " + entry.getKey() + ", Count: " + maxCount);
        }
    }
}

public static void main(String[] args) {
    String inputString = "helloworldmylovelypython";
    findMostOccChar(inputString);
}
}



// Answer: java -cp /tmp/sON5uDD4Mn Main
//         Character: l, Count: 5
