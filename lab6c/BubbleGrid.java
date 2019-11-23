public class BubbleGrid {
    private int[][] grid;
    private int rows;  // number of grid rows
    private int cols;  // number of grid columns
    private UnionFind bubbles;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        /* Each position a vertex, and an abstract vertex 0 for the ceiling. */
        bubbles = new UnionFind(rows * cols + 1);

        /* Mark popped bubbles as 2. */
        for (int[] dart : darts) {
            if (grid[dart[0]][dart[1]] == 1) {
                grid[dart[0]][dart[1]] = 2;
            }
        }

        /* -----------------State after throwing all darts----------------- */
        /* Union all bubbles in the topmost row (grid[0]). */
        for (int j = 0; j < cols; j++) {
            if (grid[0][j] == 1) {
                bubbles.union(0, stretch(0, j));
            }
        }
        /* Union all adjacent bubbles. */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    unionAround(i, j);
                }
            }
        }

        /* ----------Reverse the procedure till the original state---------- */
        int[] fallNum = new int[darts.length];

        for (int i = darts.length - 1; i >= 0; i--) {
            int[] dart = darts[i];
            int stuckNum = bubbles.sizeOf(0);

            /* Restore the position of dart. */
            if (grid[dart[0]][dart[1]] == 2) {  // dart hits a bubble
                grid[dart[0]][dart[1]] = 1;
                unionAround(dart[0], dart[1]);
                int newStuckNum = bubbles.sizeOf(0);
                if (newStuckNum - stuckNum > 1) {  // popped bubble doesn't count as fall
                    fallNum[i] = newStuckNum - stuckNum - 1;
                }
            } else {  // dart hits an empty position
                fallNum[i] = 0;
            }
        }

        return fallNum;
    }

    private int stretch(int x, int y) {
        return x * cols + y + 1;
    }

    private int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /* Union all adjacent bubbles to (x, y) */
    private void unionAround(int x, int y) {
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (0 <= nx && nx < rows && 0 <= ny && ny < cols && grid[nx][ny] == 1) {
                bubbles.union(stretch(x, y), stretch(nx, ny));
            }
            if (x == 0) {
                bubbles.union(stretch(x, y), 0);
            }
        }
    }
}
