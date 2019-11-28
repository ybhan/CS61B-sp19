import edu.princeton.cs.algs4.StdRandom;

public class ExperimentHelper {
    /**
     * Returns the internal path length for an optimum binary search tree of
     * size N. Examples:
     * N = 1, OIPL: 0
     * N = 2, OIPL: 1
     * N = 3, OIPL: 2
     * N = 4, OIPL: 4
     * N = 5, OIPL: 6
     * N = 6, OIPL: 8
     * N = 7, OIPL: 10
     * N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        if (N < 0) {
            throw new IllegalArgumentException();
        }
        if (N == 0 || N == 1) {
            return 0;
        }
        return optimalIPL(N - 1) + (int) (Math.log(N) / Math.log(2));
    }

    /**
     * Returns the average depth for nodes in an optimal BST of
     * size N.
     * Examples:
     * N = 1, OAD: 0
     * N = 5, OAD: 1.2
     * N = 8, OAD: 1.625
     */
    public static double optimalAverageDepth(int N) {
        return (double) optimalIPL(N) / N;
    }

    public static void randomDeleteInsert(BST<Integer> bst, boolean symmetricDeletion) {
        if (symmetricDeletion) {
            bst.deleteTakingRandom(bst.getRandomKey());
        } else {
            bst.deleteTakingSuccessor(bst.getRandomKey());
        }

        int toInsert = StdRandom.uniform(100000);
        while (bst.contains(toInsert)) {
            toInsert = StdRandom.uniform(100000);
        }
        bst.add(toInsert);
    }
}
