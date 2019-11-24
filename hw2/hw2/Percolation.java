package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private int[][] grid;
    private int open_num = 0;

    private WeightedQuickUnionUF uf;
    private int uf_bottom;
    private WeightedQuickUnionUF ufWithoutBottom;  // To avoid backwash!

    /** Create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        grid = new int[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
        uf_bottom = N * N + 1;
        ufWithoutBottom = new WeightedQuickUnionUF(N * N + 1);
    }

    /** Open the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = 1;
            unionAround(row, col);
            open_num += 1;
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return ufWithoutBottom.connected(0, ufIndex(row, col));
    }

    public int numberOfOpenSites() {
        return open_num;
    }

    public boolean percolates() {
        return uf.connected(0, uf_bottom);
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int ufIndex(int row, int col) {
        return row * N + col + 1;
    }

    private int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private void unionAround(int row, int col) {
        for (int[] dir : directions) {
            int x = row + dir[0];
            int y = col + dir[1];
            if (0 <= x && x < N && 0 <= y && y < N && isOpen(x, y)) {
                uf.union(ufIndex(row, col), ufIndex(x, y));
                ufWithoutBottom.union(ufIndex(row, col), ufIndex(x, y));
            }
        }
        if (row == 0) {
            uf.union(0, ufIndex(row, col));
            ufWithoutBottom.union(0, ufIndex(row, col));
        }
        if (row == N - 1) {
            uf.union(ufIndex(row, col), uf_bottom);
        }
    }

    public static void main(String[] args) { }
}
