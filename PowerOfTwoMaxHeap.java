import java.util.ArrayList;
import java.util.NoSuchElementException;

public class PowerOfTwoMaxHeap {
    private ArrayList<Integer> heap;
    private final int childrenCount;

    public PowerOfTwoMaxHeap(int degree) {
        if (degree < 0) {
            throw new IllegalArgumentException("Degree must be non-negative.");
        }
        this.childrenCount = (int) Math.pow(2, degree);
        this.heap = new ArrayList<>();
    }

    public void insert(int value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }

    public int popMax() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }

        int maxValue = heap.get(0);
        int lastValue = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, lastValue);
            heapifyDown(0);
        }

        return maxValue;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / childrenCount;
            if (heap.get(index) > heap.get(parentIndex)) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        while (true) {
            int largest = index;
            int firstChild = index * childrenCount + 1;

            for (int i = 0; i < childrenCount; i++) {
                int childIndex = firstChild + i;
                if (childIndex < heap.size() && heap.get(childIndex) > heap.get(largest)) {
                    largest = childIndex;
                }
            }

            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int peek() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public void printHeap() {
        System.out.println(heap);
    }

    public static void main(String[] args) {
        PowerOfTwoMaxHeap heap = new PowerOfTwoMaxHeap(2); // 2^2 = 4 children per node
        heap.insert(10);
        heap.insert(20);
        heap.insert(15);
        heap.insert(30);
        heap.insert(40);

        System.out.println("Heap after inserts:");
        heap.printHeap();

        System.out.println("Max element removed: " + heap.popMax());
        System.out.println("Heap after popping max:");
        heap.printHeap();
    }
}
