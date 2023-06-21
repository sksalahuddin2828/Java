import java.util.*;

public class Main3 {

static void distinct(int arr[], int num)
{
//System.out.println("");
//System.out.println("Distinct Elements: ");
	for (int i = 0; i < num; i++)
	{
		int j;
			for (j = 0; j < i; j++)
				if (arr[i] == arr[j])
					break;
			if (i == j)
				System.out.print( arr[i] + " ");
	}
}

public static void main (String[] args)
	{
	Scanner sc = new Scanner(System.in);
	int num;
	//System.out.println("Enter the number of elements in the array:");
	num = sc.nextInt();
	int arr[] = new int[num];
	//System.out.println("Enter the elements:");
	for(int i=0; i<num; i++)
	{
		arr[i] = sc.nextInt();
	}
	
	distinct(arr, num);
	}
}
