package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class KDTreeTest {

    private static Random r = new Random(123);

    @Test
    public void testCorrectness() {
        List<Point> points = randomPoints(100);

        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> goals = randomPoints(100);

        for (Point goal : goals) {
            Point expected = nps.nearest(goal.getX(), goal.getY());
            Point actual = kd.nearest(goal.getX(), goal.getY());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testEfficiency() {
        List<Point> randomPoints = randomPoints(1000);

        KDTree kd = new KDTree(randomPoints);
        NaivePointSet nps = new NaivePointSet(randomPoints);

        List<Point> queryPoints = randomPoints(1000);

        long start = System.currentTimeMillis();
        for (Point p : queryPoints) {
            nps.nearest(p.getX(), p.getY());
        }
        long end = System.currentTimeMillis();
        System.out.println("NaivePointSet: " + (end - start) / 1000.0 + " seconds");

        start = System.currentTimeMillis();
        for (Point p : queryPoints) {
            kd.nearest(p.getX(), p.getY());
        }
        end = System.currentTimeMillis();
        System.out.println("KDTree: " + (end - start) / 1000.0 + " seconds");
    }

    private Point randomPoint() {
        return new Point(r.nextDouble(), r.nextDouble());
    }

    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i += 1) {
            points.add(randomPoint());
        }
        return points;
    }
}
