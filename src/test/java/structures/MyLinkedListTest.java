package structures;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
public class MyLinkedListTest {
    @Test
    void ivanListTest() {
        MyLinkedList list = new MyLinkedList();
        list.add(10);
        list.add(20);
        list.add(30);
        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }
    @Test
    void tillListTest() {
        MyLinkedList list = new MyLinkedList();
        list.add(10);
        list.add(30);
        list.add(1, 20);
        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }
    @Test
    void miziListTest() {
        MyLinkedList list = new MyLinkedList();
        list.add(5);
        list.add(10);
        list.add(15);
        int removed = list.remove(1);
        assertEquals(10, removed);
        assertEquals(2, list.size());
        assertEquals(5, list.get(0));
        assertEquals(15, list.get(1));
    }
    @Test
    void suaListTest() {
        MyLinkedList list = new MyLinkedList();
        list.add(7);
        list.add(7);
        list.add(12);
        assertTrue(list.contains(7));
        assertTrue(list.contains(12));
        assertFalse(list.contains(100));
    }
    @Test
    void lukaListTest() {
        MyLinkedList list = new MyLinkedList();
        list.add(100);
        assertEquals(1, list.size());
        assertEquals(100, list.get(0));
        list.add(200);
        list.add(300);
        assertEquals(100, list.get(0));
        assertEquals(300, list.get(2));
    }
    @Test
    void hyunaListTest() {
        MyLinkedList list = new MyLinkedList();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 10));
    }
    @Test
    void lukaAndHyunaTest() {
        MyLinkedList list = new MyLinkedList();
        list.add(10);
        list.add(20);
        list.add(30);
        assertEquals(10, list.remove(0));
        assertEquals(20, list.get(0));
        assertEquals(30, list.remove(list.size() - 1));
        assertEquals(1, list.size());
    }
    @Test
    void acornListTest() {
        MyLinkedList list = new MyLinkedList();
        LinkedList<Integer> expected = new LinkedList<>();
        Random random = new Random(42);
        for (int i = 0; i < 100; i++) {
            int value = random.nextInt(1000);
            list.add(value);
            expected.add(value);
        }
        assertEquals(expected.size(), list.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).intValue(), list.get(i));
        }
    }
}