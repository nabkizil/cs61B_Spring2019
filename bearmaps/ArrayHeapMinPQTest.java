package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.Stopwatch;

public class ArrayHeapMinPQTest {

    @Test
    public void arrayHeapMinPqTest() {
        Stopwatch sw = new Stopwatch();
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<Integer>();
        test.add(4, 4.0); // {4: 4.0}
        test.add(5, 5.3); // {4: 4.0, 5: 5.0}
        test.add(3, 3.61); // {3: 3.61, 4: 4.0, 5: 5.0}
        test.add(2, 2.12);
        test.add(100, 1);
        assertEquals(test.size(), 1);
        test.removeSmallest();
        assertNotEquals(test.size(), 1);
        assertEquals(test.size(), 0);
        assertFalse(test.contains(100));
        test.add(10, 0.5);
        test.add(1, 1.0);
        assertEquals(test.size(), 6);
        assertNotEquals(test.size(), 0);
        assertTrue(test.getSmallest() == 5);
        assertTrue(test.removeSmallest() == 5);
        assertFalse(test.contains(5));
        assertEquals(test.size(), 2);
        test.changePriority(3, 1.0);
        assertTrue(test.getSmallest() == 3);
        test.changePriority(4, 0.5);
        assertTrue(test.getSmallest() == 4);
        assertEquals(test.size(), 2);
        assertFalse(test.contains(20));
        assertFalse(test.contains(8));
        assertTrue(test.contains(1));
        assertTrue(test.contains(4));
        assertTrue(test.removeSmallest() == 1);
        assertTrue(test.removeSmallest() == 2);
        assertFalse(test.contains(2));
        assertTrue(test.contains(5));
        assertTrue(test.contains(5));
        assertTrue(test.contains(10));
        assertTrue(test.contains(2));
        assertTrue(test.getSmallest() == 10);
        assertTrue(test.removeSmallest() == 10);
        assertTrue(test.getSmallest() == 1);
        assertEquals(test.size(), 3);
        test.changePriority(5, 1.0);
        System.out.println("Total time elapsed: " + sw.elapsedTime() +  " seconds.");
    }
}
