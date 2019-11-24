package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int N;
    private int T;
    private double[] threshold;

    /** Perform T independent experiments on an N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (T <= 0 || N <= 0) {
            throw new IllegalArgumentException();
        }

        this.T = T;
        this.N = N;
        threshold = new double[T];

        /* Monte Carlo Simulation */
        for (int t = 0; t < T; t++) {
            Percolation p = pf.make(N);
            threshold[t] = simulate(p);
        }
    }

    /** Open randomly until the system percolates. Return the threshold. */
    private double simulate(Percolation p) {
        int[] openOrder = StdRandom.permutation(N * N);
        for (int i = 0; !p.percolates(); i++) {
            int x = coordinate(openOrder[i])[0];
            int y = coordinate(openOrder[i])[1];
            p.open(x, y);
        }
        return ((double) p.numberOfOpenSites()) / (N * N);
    }

    private int[] coordinate(int index) {
        return new int[]{index / N, index % N};
    }

    /** Sample mean of percolation threshold */
    public double mean() {
        return StdStats.mean(threshold);
    }

    /** Sample standard deviation of percolation threshold */
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    /** Low endpoint of 95% confidence interval */
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    /** High endpoint of 95% confidence interval */
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    /* For test */
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(100, 1000, new PercolationFactory());
        System.out.printf("mean = %.6f\n", ps.mean());
        System.out.printf("sd = %.6f\n", ps.stddev());
        System.out.println("95% Confidence Interval:");
        System.out.printf("(%.6f, %.6f)\n", ps.confidenceLow(), ps.confidenceHigh());
    }
}
