public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private T[] items;
    private int capacity;
    private int nextFirst = 0;
    private int nextLast = 1;
    private int RESIZE_FACTOR = 2;
    private double USAGE_RATIO = 0.25;
    private int CAPACITY_MIN = 16;

    /** Construct an empty ArrayDeque. */
    public ArrayDeque() {
        this(8);
    }

    /** Deep copy an ArrayDeque. */
    public ArrayDeque(ArrayDeque other) {
        this(other.size() * 2);
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    /** Construct an empty ArrayDeque with given capacity. */
    private ArrayDeque(int capacity) {
        items = (T[]) new Object[capacity];
        size = 0;
        this.capacity = capacity;
    }

    @Override
    public void addFirst(T item) {
        if (isFull()) {
            expandArray(RESIZE_FACTOR);
        }
        items[nextFirst] = item;
        size++;
        nextFirst = indexAdd(nextFirst, -1);
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            expandArray(RESIZE_FACTOR);
        }
        items[nextLast] = item;
        size++;
        nextLast = indexAdd(nextLast, 1);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    @Override
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

    @Override
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

    @Override
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
        return (size > CAPACITY_MIN) && size < (capacity * USAGE_RATIO);
    }

    /** Resize ArrayDeque to have factor*capacity. */
    private void expandArray(int factor) {
        reshapeItems(capacity * factor);
        capacity *= factor;
    }

    /** Halve the capacity of ArrayDeque to save memory. */
    private void shrinkArray() {
        reshapeItems(capacity / 2);
        capacity /= 2;
    }

    private void reshapeItems(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[indexAdd(nextFirst, 1 + i)];
        }
        items = newItems;
        nextFirst = newCapacity - 1;
        nextLast = size;
    }

    /** Circulate index. */
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
