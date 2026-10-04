package structures;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
public class DynamicArrayTest {
    @Test
    void ivanTest() {
        DynamicArray array = new DynamicArray();
        array.add(10);
        array.add(20);
        array.add(30);
        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }
    @Test
    void tillTest() {
        DynamicArray array = new DynamicArray();
        array.add(10);
        array.add(30);
        array.add(1, 20);
        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }
    @Test
    void miziTest() {
        DynamicArray array = new DynamicArray();
        array.add(5);
        array.add(10);
        array.add(15);
        int removed = array.remove(1);
        assertEquals(10, removed);
        assertEquals(2, array.size());
        assertEquals(5, array.get(0));
        assertEquals(15, array.get(1));
    }
    @Test
    void suaTest() {
        DynamicArray array = new DynamicArray();
        array.add(7);
        array.add(7);
        array.add(12);
        assertTrue(array.contains(7));
        assertTrue(array.contains(12));
        assertFalse(array.contains(100));
    }
    @Test
    void lukaTest() {
        DynamicArray array = new DynamicArray();
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(5);
        array.add(6);
        assertEquals(6, array.size());
        assertEquals(1, array.get(0));
        assertEquals(6, array.get(5));
    }
    @Test
    void hyunaTest() {
        DynamicArray array = new DynamicArray();
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, 10));
    }
    @Test
    void acornTest() {
        DynamicArray array = new DynamicArray();
        ArrayList<Integer> expected = new ArrayList<>();
        Random random = new Random(42);
        for (int i = 0; i < 100; i++) {
            int value = random.nextInt(1000);
            array.add(value);
            expected.add(value);
        }
        assertEquals(expected.size(), array.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).intValue(), array.get(i));
        }
    }
}