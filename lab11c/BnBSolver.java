import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each
 * Bed can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds,
 * i.e. each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith
 * Bear is the same size as the ith Bed.
 */
public class BnBSolver {
    private List<Bear> sortedBears;
    private List<Bed> sortedBeds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        Pair<List<Bear>, List<Bed>> solutions = quickSort(bears, beds);
        sortedBears = solutions.first();
        sortedBeds  = solutions.second();
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return sortedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return sortedBeds;
    }

    /** Concatenate three lists. */
    private <P> List<P> concatenate(List<P> list1, List<P> list2, List<P> list3) {
        List<P> fullList = new ArrayList<>(list1);
        fullList.addAll(list2);
        fullList.addAll(list3);
        return fullList;
    }

    /** Quick sort bears and beds simultaneously. */
    private Pair<List<Bear>, List<Bed>> quickSort(List<Bear> bears, List<Bed> beds) {
        if (bears == null || beds == null || bears.size() != beds.size()) {
            throw new IllegalArgumentException();
        }

        if (bears.size() <= 1) {
            return new Pair<>(bears, beds);
        }

        Bear bear = bears.get(0);

        List<Bed> smallerBeds = new ArrayList<>();
        List<Bed> equalBeds = new ArrayList<>();
        List<Bed> largerBeds = new ArrayList<>();
        partition(beds, bear, smallerBeds, equalBeds, largerBeds);

        List<Bear> smallerBears = new ArrayList<>();
        List<Bear> equalBears = new ArrayList<>();
        List<Bear> largerBears = new ArrayList<>();
        partition(bears, equalBeds.get(0), smallerBears, equalBears, largerBears);

        Pair<List<Bear>, List<Bed>> smaller = quickSort(smallerBears, smallerBeds);
        smallerBears = smaller.first();
        smallerBeds = smaller.second();

        Pair<List<Bear>, List<Bed>> larger = quickSort(largerBears, largerBeds);
        largerBears = larger.first();
        largerBeds = larger.second();

        return new Pair<>(concatenate(smallerBears, equalBears, largerBears),
                          concatenate(smallerBeds, equalBeds, largerBeds));
    }

    private <P extends Comparable<Q>, Q extends Comparable<P>> void partition(
            List<P> items, Q pivot, List<P> smaller, List<P> equal, List<P> larger) {
        for (P item : items) {
            int cmp = item.compareTo(pivot);
            if (cmp < 0) {
                smaller.add(item);
            } else if (cmp == 0) {
                equal.add(item);
            } else {
                larger.add(item);
            }
        }
    }
}
