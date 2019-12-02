package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    @Test
    public void testAddSize() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 4);
        minHeap.add(5, 2);
        minHeap.add(6, 3);
        assertEquals(6, minHeap.size());
        minHeap.removeSmallest();
        minHeap.removeSmallest();
        assertEquals(4, minHeap.size());
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 4);
        minHeap.add(5, 2);
        minHeap.add(6, 3);
        assertTrue(minHeap.contains(2));
        assertFalse(minHeap.contains(0));
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 4);
        minHeap.add(5, 0);
        minHeap.add(6, 3);
        assertEquals(5, (int) minHeap.getSmallest());
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 4);
        minHeap.add(5, 0);
        minHeap.add(6, 3.5);
        minHeap.add(7, 4);
        minHeap.add(8, 8);
        minHeap.add(9, 8);
        minHeap.add(10, 8);
        assertEquals(5, (int) minHeap.removeSmallest());
        assertEquals(1, (int) minHeap.removeSmallest());
        assertEquals(2, (int) minHeap.removeSmallest());
        assertEquals(3, (int) minHeap.removeSmallest());
        assertEquals(6, (int) minHeap.getSmallest());
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 4);
        minHeap.add(5, 0);
        minHeap.add(6, 3);
        minHeap.changePriority(5, 1.5);
        minHeap.changePriority(6, 0);
        assertEquals(6, (int) minHeap.getSmallest());
        assertEquals(6, (int) minHeap.removeSmallest());
        assertEquals(1, (int) minHeap.removeSmallest());
        assertEquals(5, (int) minHeap.getSmallest());
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExtrinsicMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 200000; i += 1) {
            minHeap.add(i, 100000 - i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");

        long start2 = System.currentTimeMillis();
        for (int j = 0; j < 10000; j += 1) {
            minHeap.changePriority(j, j + 1);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end2 - start2) / 1000.0 +  " seconds.");
    }
}
