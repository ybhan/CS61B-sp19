public class LinkedListDeque<T> implements Deque<T> {
    private int size;
    private DequeNode sentinel;

    public LinkedListDeque() {
        size = 0;
        sentinel = new DequeNode();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public LinkedListDeque(LinkedListDeque other) {
        this();
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    @Override
    public void addFirst(T item) {
        sentinel.next.prev = new DequeNode(item, sentinel, sentinel.next);
        sentinel.next = sentinel.next.prev;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        sentinel.prev.next = new DequeNode(item, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        DequeNode first = sentinel.next;
        while (first != sentinel) {
            System.out.print(first.item + " ");
            first = first.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        DequeNode first = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.next.prev = sentinel;
        size -= 1;
        return first.item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        DequeNode last = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.prev.next = sentinel;
        size -= 1;
        return last.item;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }

        DequeNode currentNode = sentinel.next;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    public T getRecursive(int index) {
        if (index > size) {
            return null;
        }
        return getRecursive(index, sentinel.next);
    }

    private T getRecursive(int index, DequeNode node) {
        if (index == 0) {
            return node.item;
        } else {
            return getRecursive(index - 1, node.next);
        }
    }

    private class DequeNode {
        private T item;
        private DequeNode prev;
        private DequeNode next;

        private DequeNode(T i, DequeNode p, DequeNode n) {
            item = i;
            prev = p;
            next = n;
        }

        private DequeNode() {
            this(null, null, null);
        }
    }
}
