import org.junit.Test;
import static org.junit.Assert.*;

public class BubbleGridTest {
    @Test
    public void test1() {
        int[][] grid = {{1, 1, 0},
                        {1, 0, 0},
                        {1, 1, 0},
                        {1, 1, 1}};
        int[][] darts = {{2, 2}, {2, 0}};
        int[] expected = {0, 4};

        validate(grid, darts, expected);
    }

    @Test
    public void test2() {
        int[][] grid = {{1}, {1}, {1}, {1}, {1}};
        int[][] darts = {{3, 0}, {4, 0}, {1, 0}, {2, 0}, {0, 0}};
        int[] expected = {1, 0, 1, 0, 0};

        validate(grid, darts, expected);
    }

    private void validate(int[][] grid, int[][] darts, int[] expected) {
        BubbleGrid sol = new BubbleGrid(grid);
        assertArrayEquals(expected, sol.popBubbles(darts));
    }
}
