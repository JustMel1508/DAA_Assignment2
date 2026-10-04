package structures;
import metrics.OperationCounter;
public class MyLinkedList {
    private Node head;
    private Node tail;
    private int size;
    private OperationCounter counter;
    private static class Node {
        int value;
        Node next;
        Node(int value) {
            this.value = value;
        }
    }
    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
        counter = new OperationCounter();
    }
    public void add(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
            counter.addMove();
            counter.addMove();
        } else {
            tail.next = newNode;
            tail = newNode;
            counter.addMove();
            counter.addMove();
        }
        size++;
    }
    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        Node newNode = new Node(value);
        if (index == 0) {
            newNode.next = head;
            counter.addMove();
            head = newNode;
            counter.addMove();
            size++;
            return;
        }
        Node current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            counter.addStep();
        }
        newNode.next = current.next;
        counter.addMove();
        current.next = newNode;
        counter.addMove();
        size++;
    }
    public int remove(int index) {
        checkIndex(index);
        if (index == 0) {
            int removedValue = head.value;
            head = head.next;
            counter.addMove();
            size--;
            if (size == 0) {
                tail = null;
                counter.addMove();
            }
            return removedValue;
        }
        Node current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            counter.addStep();
        }
        int removedValue = current.next.value;
        if (index == size - 1) {
            current.next = null;
            tail = current;
            counter.addMove();
            counter.addMove();
        } else {
            current.next = current.next.next;
            counter.addMove();
        }
        size--;
        return removedValue;
    }
    public int get(int index) {
        checkIndex(index);
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
            counter.addStep();
        }
        return current.value;
    }
    public boolean contains(int value) {
        Node current = head;
        while (current != null) {
            counter.addComparison();
            if (current.value == value) {
                return true;
            }
            current = current.next;
            counter.addStep();
        }
        return false;
    }
    public int size() {
        return size;
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