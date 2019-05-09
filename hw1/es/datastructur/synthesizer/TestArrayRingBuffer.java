package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        //ArrayRingBuffer soln = new ArrayRingBuffer(10);
        assertTrue(arb.isEmpty());
        arb.enqueue(0);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        assertFalse(arb.isEmpty());
        assertEquals(arb.fillCount(), 5);
        arb.dequeue();
        assertEquals(arb.fillCount(), 4);
        assertEquals(arb.dequeue(), 1);
        assertEquals(arb.peek(), 2);
    }
}

