package structures;
import metrics.OperationCounter;
public class DynamicArray {
    private int[] data;
    private int size;
    private OperationCounter counter;
    public DynamicArray() {
        data = new int[4];
        size = 0;
        counter = new OperationCounter();
    }
    public void add(int value) {
        if (size == data.length) {
            resize();
        }
        data[size] = value;
        size++;
    }
    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == data.length) {
            resize();
        }
        for (int i = size; i > index; i--) {
            counter.addStep();
            data[i] = data[i - 1];
            counter.addMove();
        }
        data[index] = value;
        size++;
    }
    public int remove(int index) {
        checkIndex(index);
        counter.addStep();
        int removedValue = data[index];
        for (int i = index; i < size - 1; i++) {
            counter.addStep();
            data[i] = data[i + 1];
            counter.addMove();
        }
        size--;
        return removedValue;
    }
    public int get(int index) {
        checkIndex(index);
        counter.addStep();
        return data[index];
    }
    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            counter.addStep();
            counter.addComparison();
            if (data[i] == value) {
                return true;
            }
        }
        return false;
    }
    public int size() {
        return size;
    }
    private void resize() {
        int[] newData = new int[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            counter.addStep();
            newData[i] = data[i];
            counter.addMove();
        }
        data = newData;
    }
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
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