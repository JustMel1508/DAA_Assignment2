package structures;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
public class MinHeapTest {
    @Test
    void ivanHeapTest() {
        MinHeap heap = new MinHeap();
        heap.insert(30);
        heap.insert(10);
        heap.insert(20);
        assertEquals(10, heap.peekMin());
        assertEquals(3, heap.size());
        assertTrue(heap.isValidHeap());
    }
    @Test
    void tillHeapTest() {
        MinHeap heap = new MinHeap();
        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);
        assertEquals(1, heap.extractMin());
        assertEquals(2, heap.peekMin());
        assertEquals(3, heap.size());
        assertTrue(heap.isValidHeap());
    }
    @Test
    void miziHeapTest() {
        MinHeap heap = new MinHeap();
        heap.insert(7);
        heap.insert(7);
        heap.insert(7);
        assertEquals(7, heap.extractMin());
        assertEquals(7, heap.extractMin());
        assertEquals(7, heap.extractMin());
        assertTrue(heap.isEmpty());
    }
    @Test
    void suaHeapTest() {
        MinHeap heap = new MinHeap();
        assertThrows(IllegalStateException.class, heap::peekMin);
        assertThrows(IllegalStateException.class, heap::extractMin);
    }
    @Test
    void lukaHeapTest() {
        MinHeap heap = new MinHeap();
        int[] values = {50, 40, 30, 20, 10, 60, 5};
        for (int value : values) {
            heap.insert(value);
            assertTrue(heap.isValidHeap());
        }
        while (!heap.isEmpty()) {
            heap.extractMin();
            if (!heap.isEmpty()) {
                assertTrue(heap.isValidHeap());
            }
        }
    }
    @Test
    void hyunaHeapTest() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        assertEquals(10, heap.peekMin());
        assertEquals(10, heap.extractMin());
        assertTrue(heap.isEmpty());
    }
    @Test
    void acornHeapTest() {
        MinHeap heap = new MinHeap();
        ArrayList<Integer> expected = new ArrayList<>();
        Random random = new Random(42);
        for (int i = 0; i < 100; i++) {
            int value = random.nextInt(1000);
            heap.insert(value);
            expected.add(value);
            assertTrue(heap.isValidHeap());
        }
        Collections.sort(expected);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).intValue(), heap.extractMin());
            if (!heap.isEmpty()) {
                assertTrue(heap.isValidHeap());
            }
        }
        assertTrue(heap.isEmpty());
    }
    @Test
    void miziAndSuaSortedTest() {
        MinHeap heap = new MinHeap();
        heap.insert(9);
        heap.insert(2);
        heap.insert(15);
        heap.insert(1);
        heap.insert(7);
        heap.insert(3);
        int previous = heap.extractMin();
        while (!heap.isEmpty()) {
            int current = heap.extractMin();
            assertTrue(previous <= current);
            previous = current;
        }
    }
}