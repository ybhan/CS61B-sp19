package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<HeapNode<T>> heap;
    private HashMap<T, Integer> itemIndexMap;

    private static class HeapNode<T> {
        private T item;
        private double priority;

        HeapNode(T i, double p) {
            item = i;
            priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double p) {
            priority = p;
        }
    }

    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        itemIndexMap = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Item already exists in the priority queue.");
        }
        heap.add(new HeapNode<>(item, priority));
        itemIndexMap.put(item, size() - 1);
        swimUp(size() - 1);
    }

    @Override
    public boolean contains(T item) {
        return itemIndexMap.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException("The priority queue is empty.");
        }
        return heap.get(0).getItem();
    }

    @Override
    public T removeSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException("The priority queue is empty.");
        }
        T itemRemoved = heap.get(0).getItem();
        itemIndexMap.remove(itemRemoved);
        heap.set(0, heap.get(size() - 1));
        heap.remove(size() - 1);
        swimDown(0);
        return itemRemoved;
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("Item doesn't exist in the priority queue.");
        }
        int index = itemIndexMap.get(item);

        if (priority < heap.get(index).getPriority()) {
            heap.get(index).setPriority(priority);
            swimUp(index);
        } else {
            heap.get(index).setPriority(priority);
            swimDown(index);
        }
    }

    private boolean isEmpty() {
        return size() == 0;
    }

    private void swap(int index1, int index2) {
        HeapNode<T> node1 = heap.get(index1);
        HeapNode<T> node2 = heap.get(index2);
        itemIndexMap.put(node1.getItem(), index2);
        itemIndexMap.put(node2.getItem(), index1);
        heap.set(index1, node2);
        heap.set(index2, node1);
    }

    private void swimUp(int index) {
        int parent = parent(index);
        if (heap.get(index).getPriority() < heap.get(parent).getPriority()) {
            swap(index, parent);
            swimUp(parent);
        }
    }

    private void swimDown(int index) {
        int smallerChild = smallerChild(index);
        while (heap.get(index).getPriority() > heap.get(smallerChild).getPriority()) {
            swap(index, smallerChild);
            index = smallerChild;
            smallerChild = smallerChild(index);
        }
    }
    /* Recursive implementation. This may cause StackOverflowError when calling too many times. */
    //    int smallerChild = smallerChild(index);
    //    if (heap.get(index).getPriority() > heap.get(smallerChild).getPriority()) {
    //        swap(index, smallerChild);
    //        swimDown(smallerChild);
    //    }
    //}

    private int parent(int index) {
        if (index == 0) {
            return 0;
        }
        return (index - 1) / 2;
    }

    private int smallerChild(int index) {
        int leftChild = index * 2 + 1;
        leftChild = leftChild < size() ? leftChild : index;
        int rightChild = leftChild + 1 < size() ? leftChild + 1 : leftChild;
        return heap.get(leftChild).getPriority() < heap.get(rightChild).getPriority()
                ? leftChild : rightChild;
    }
}
