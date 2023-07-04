import java.util.ArrayList;
import java.util.List;

class MaxHeap {
    private List<Integer> heap;

    public MaxHeap() {
        heap = new ArrayList<>();
    }

    private void heapify(int n, int i) {
        int largest = i;
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;

        if (leftChild < n && heap.get(i) < heap.get(leftChild)) {
            largest = leftChild;
        }

        if (rightChild < n && heap.get(largest) < heap.get(rightChild)) {
            largest = rightChild;
        }

        if (largest != i) {
            int temp = heap.get(i);
            heap.set(i, heap.get(largest));
            heap.set(largest, temp);
            heapify(n, largest);
        }
    }

    public void insert(int newNum) {
        int size = heap.size();
        if (size == 0) {
            heap.add(newNum);
        } else {
            heap.add(newNum);
            for (int i = (size / 2) - 1; i >= 0; i--) {
                heapify(size, i);
            }
        }
    }

    public void deleteNode(int num) {
        int size = heap.size();
        int i;
        for (i = 0; i < size; i++) {
            if (num == heap.get(i)) {
                break;
            }
        }

        int temp = heap.get(i);
        heap.set(i, heap.get(size - 1));
        heap.set(size - 1, temp);
        heap.remove(size - 1);

        for (i = (heap.size() / 2) - 1; i >= 0; i--) {
            heapify(heap.size(), i);
        }
    }

    public void printHeap() {
        System.out.println("Max-Heap array: " + heap);
    }
}

public class Main {
    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap();
        maxHeap.insert(3);
        maxHeap.insert(4);
        maxHeap.insert(9);
        maxHeap.insert(5);
        maxHeap.insert(2);

        maxHeap.printHeap();

        maxHeap.deleteNode(4);
        maxHeap.printHeap();
    }
}
