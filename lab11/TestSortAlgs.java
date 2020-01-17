import edu.princeton.cs.algs4.Queue;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tas = buildTest();

        Queue<String> sortedTas = QuickSort.quickSort(tas);

        assertEquals(tas.size(), sortedTas.size());
        assertTrue(isSorted(sortedTas));
    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = buildTest();

        Queue<String> sortedTas = MergeSort.mergeSort(tas);

        assertEquals(tas.size(), sortedTas.size());
        assertTrue(isSorted(sortedTas));
    }

    private Queue<String> buildTest() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        tas.enqueue("Jeff");
        tas.enqueue("Norah");
        tas.enqueue("York");
        tas.enqueue("Manu");
        return tas;
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable<? super Item>> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
