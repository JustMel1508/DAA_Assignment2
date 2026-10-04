package structures;
import metrics.OperationCounter;
public class MinHeap {
    private int[] heap;
    private int size;
    private OperationCounter counter;
    public MinHeap() {
        heap = new int[4];
        size = 0;
        counter = new OperationCounter();
    }
    public void insert(int value) {
        if (size == heap.length) {
            resize();
        }
        heap[size] = value;
        size++;
        int index = size - 1;
        while (index > 0) {
            int parent = (index - 1) / 2;
            counter.addStep();
            counter.addStep();
            counter.addComparison();
            if (heap[parent] <= heap[index]) {
                break;
            }
            int temp = heap[parent];
            counter.addStep();
            heap[parent] = heap[index];
            counter.addStep();
            counter.addMove();
            heap[index] = temp;
            counter.addMove();
            index = parent;
        }
    }
    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        counter.addStep();
        return heap[0];
    }
    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        counter.addStep();
        int min = heap[0];
        counter.addStep();
        heap[0] = heap[size - 1];
        counter.addMove();
        size--;
        int index = 0;
        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smallest = index;
            if (left < size) {
                counter.addStep();
                counter.addStep();
                counter.addComparison();
                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }
            if (right < size) {
                counter.addStep();
                counter.addStep();
                counter.addComparison();
                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }
            if (smallest == index) {
                break;
            }
            int temp = heap[index];
            counter.addStep();
            heap[index] = heap[smallest];
            counter.addStep();
            counter.addMove();
            heap[smallest] = temp;
            counter.addMove();
            index = smallest;
        }
        return min;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    private void resize() {
        int[] newHeap = new int[heap.length * 2];
        for (int i = 0; i < heap.length; i++) {
            counter.addStep();
            newHeap[i] = heap[i];
            counter.addMove();
        }
        heap = newHeap;
    }
    public boolean isValidHeap() {
        for (int i = 0; i < size; i++) {
            int left = i * 2 + 1;
            int right = i * 2 + 2;
            if (left < size && heap[i] > heap[left]) {
                return false;
            }
            if (right < size && heap[i] > heap[right]) {
                return false;
            }
        }
        return true;
    }
    public long getSteps() {
        return counter.getSteps();
    }
    public long getMoves() {
        return counter.getMoves();
    }
    public long getComparisons() {
        return counter.getComparisons();
    }
    public void resetMetrics() {
        counter.reset();
    }
}