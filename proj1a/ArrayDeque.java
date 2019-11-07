public class ArrayDeque<T> {
    public int size;
    private T[] items;
    public int capacity;
    private int nextFirst = 0;
    private int nextLast = 1;
    private int RESIZE_FACTOR = 2;
    private double USAGE_RATIO = 0.25;
    private int CAPACITY_BASE = 16;

    public ArrayDeque() {
        this(8);
    }

    public ArrayDeque(ArrayDeque other) {
        this(other, other.capacity);
    }

    /**
     * Construct a new ArrayDeque with given capacity.
     */
    private ArrayDeque(int capacity) {
        items = (T[]) new Object[capacity];
        size = 0;
        this.capacity = capacity;
    }

    /**
     * Deep copy an ArrayDeque other into a new ArrayDeque with given capacity.
     */
    private ArrayDeque(ArrayDeque other, int capacity) {
        this(capacity);
        size = other.size;
        nextFirst = other.nextFirst;
        nextLast = nextFirst + 1;
        for (int i = 0; i < size; i++) {
            addLast((T) other.get(i));
        }
    }

    public void addFirst(T item) {
        if (isFull()) {
            expandArray(RESIZE_FACTOR);
        }
        items[nextFirst] = item;
        size++;
        nextFirst = indexAdd(nextFirst, -1);
    }

    public void addLast(T item) {
        if (isFull()) {
            expandArray(RESIZE_FACTOR);
        }
        items[nextLast] = item;
        size++;
        nextLast = indexAdd(nextLast, 1);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = indexAdd(nextFirst, 1);
        T first = items[nextFirst];
        items[nextFirst] = null;
        size--;
        // Check to shrink ArrayDeque
        if (isSparse()) {
            shrinkArray();
        }
        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = indexAdd(nextLast, -1);
        T last = items[nextLast];
        items[nextLast] = null;
        size--;
        // Check to shrink ArrayDeque
        if (isSparse()) {
            shrinkArray();
        }
        return last;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            return items[indexAdd(nextFirst, 1 + index)];
        }
    }

    private boolean isFull() {
        return size == capacity;
    }

    private boolean isSparse() {
        return (size > CAPACITY_BASE) && size < (capacity * USAGE_RATIO);
    }

    /**
     * Resize ArrayDeque to have factor*capacity.
     */
    private void expandArray(int factor) {
        capacity *= factor;
        ArrayDeque<T> expandedDeque = new ArrayDeque<T>(this, capacity);
        items = expandedDeque.items;
        nextLast = expandedDeque.nextLast;
    }

    /**
     * Halve the capacity of ArrayDeque to save memory.
     */
    private void shrinkArray() {
        capacity /= 2;
        ArrayDeque<T> shrunkDeque = new ArrayDeque<T>(this, capacity);
        items = shrunkDeque.items;
        nextLast = shrunkDeque.nextLast;
    }

    /**
     * Circulate index.
     */
    private int indexAdd(int index, int num) {
        index += num;
        if (index < 0) {
            index += capacity;
        }
        if (index >= capacity) {
            index -= capacity;
        }
        return index;
    }
}
