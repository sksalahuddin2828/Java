import java.util.Scanner;

public class IncomeSort {
    public static void main(String[] args) {
        int[] incomeList = new int[10];
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the income of 10 people:");
        for (int person = 0; person < 10; person++) {
            System.out.print("Enter income: ");
            int income = scanner.nextInt();
            incomeList[person] = income;
        }

        for (int firstIndex = 0; firstIndex < 9; firstIndex++) {
            int swapCount = 0;
            int minIncome = incomeList[firstIndex];
            int minIndex = firstIndex;

            for (int secondIndex = firstIndex + 1; secondIndex < 10; secondIndex++) {
                if (minIncome > incomeList[secondIndex]) {
                    minIncome = incomeList[secondIndex];
                    swapCount++;
                    minIndex = secondIndex;
                }
            }

            if (swapCount != 0) {
                int temp = incomeList[firstIndex];
                incomeList[firstIndex] = minIncome;
                incomeList[minIndex] = temp;
            }
        }

        System.out.println("Sorted income list:");
        for (int income : incomeList) {
            System.out.println(income);
        }
    }
}
