package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] bucketNums = new int[M];
        for (Oomage o : oomages) {
            bucketNums[(o.hashCode() & 0x7FFFFFFF) % M] += 1;
        }
        for (int i = 0; i < M; i++) {
            if (bucketNums[i] < oomages.size() / 50.0 || bucketNums[i] > oomages.size() / 2.5) {
                return false;
            }
        }
        return true;
    }
}
