package es.datastructur.synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T>, Iterable<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    private int indexPlusOne(int index) {
        return (index + 1) % capacity();
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        last = indexPlusOne(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T removed = rb[first];
        rb[first] = null;
        first = indexPlusOne(first);
        fillCount -= 1;
        return removed;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ARBIterator();
    }

    private class ARBIterator implements Iterator<T> {
        private int next = first;

        @Override
        public boolean hasNext() {
            return next != last;
        }

        @Override
        public T next() {
            T item = rb[next];
            next = indexPlusOne(next);
            return item;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        /* Check if obj is an ArrayRingBuffer. */
        if (obj.getClass() != ArrayRingBuffer.class) {
            return false;
        }
        ArrayRingBuffer obj1 = (ArrayRingBuffer) obj;

        /* Check if obj is an ArrayRingBuffer<T>. */
        Object objFirst = obj1.peek();
        if (objFirst.getClass() != this.peek().getClass()) {
            return false;
        }
        ArrayRingBuffer<T> obj2 = (ArrayRingBuffer<T>) obj1;

        /* Elementwise checking. */
        if (obj2.fillCount() != fillCount()) {
            return false;
        }
        Iterator thisIter = iterator();
        for (Object oItem : obj2) {
            if (!thisIter.next().equals(oItem)) {
                return false;
            }
        }

        return true;
    }
}
