import java.io.FileNotFoundException;

import org.junit.Test;
import static org.junit.Assert.*;

public class SeparableEnemySolverTests {

    @Test
    public void triangleEnemies() {
        Graph g = new Graph();
        g.connect("A", "B");
        g.connect("A", "C");
        g.connect("A", "D");
        g.connect("C", "D");
        SeparableEnemySolver solver = new SeparableEnemySolver(g);
        assertFalse(solver.isSeparable());
    }

    @Test
    public void disconnected() {
        Graph g = new Graph();
        g.connect("A", "B");
        g.connect("C", "D");
        SeparableEnemySolver solver = new SeparableEnemySolver(g);
        assertTrue(solver.isSeparable());
    }

    @Test
    public void disconnected2() {
        Graph g = new Graph();
        g.connect("A", "B");
        g.connect("C", "D");
        g.connect("E", "D");
        g.connect("E", "C");
        SeparableEnemySolver solver = new SeparableEnemySolver(g);
        assertFalse(solver.isSeparable());
    }

    @Test
    public void input1() throws FileNotFoundException {
        SeparableEnemySolver solver = new SeparableEnemySolver("input/party1");
        assertTrue(solver.isSeparable());
    }

    @Test
    public void input2() throws FileNotFoundException {
        SeparableEnemySolver solver = new SeparableEnemySolver("input/party2");
        assertTrue(solver.isSeparable());
    }

    @Test
    public void input3() throws FileNotFoundException {
        SeparableEnemySolver solver = new SeparableEnemySolver("input/party3");
        assertFalse(solver.isSeparable());
    }

    @Test
    public void input4() throws FileNotFoundException {
        SeparableEnemySolver solver = new SeparableEnemySolver("input/party4");
        assertFalse(solver.isSeparable());
    }

}
