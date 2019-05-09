package es.datastructur.synthesizer;
import java.util.Iterator;
import java.lang.*;


public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    private static int capacity;

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    public Iterator<T> iterator() {
        return new BufferIterator();
    }

    private class BufferIterator implements Iterator<T> {
        private int pointer;

        private BufferIterator() {
            pointer = 0;
        }

        @Override
        public boolean hasNext() {
            return pointer != fillCount;
        }

        @Override
        public T next() {
            T returnItem = rb[pointer];
            pointer += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Iterator p = ((ArrayRingBuffer) o).iterator();
        Iterator q = this.iterator();
        for (int i = 0; i < ((ArrayRingBuffer) o).fillCount; i++) {
            if (p.next() != q.next()) {
                return false;
            }
        }
        return false;
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        rb = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            rb[last] = x;
            last += 1;
            if (last >= this.capacity) {
                last = 0;
            }
        }
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        T returnValue;

        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            returnValue = rb[first];
            rb[first] = null;
            first += 1;

            if (first >= this.capacity) {
                first = 0;
            }
        }

        fillCount -= 1;
        return returnValue;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            return rb[first];
        }
    }
}

