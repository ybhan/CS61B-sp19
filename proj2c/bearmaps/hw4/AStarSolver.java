package bearmaps.hw4;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import edu.princeton.cs.algs4.Stopwatch;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import bearmaps.proj2ab.ArrayHeapMinPQ;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private LinkedList<Vertex> solution;
    private double solutionWeight;
    private int numStatesExplored;
    private double explorationTime;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();

        solution = new LinkedList<>();
        solutionWeight = 0;
        numStatesExplored = 0;

        Map<Vertex, Double> distTo = new HashMap<>();
        Map<Vertex, Vertex> edgeTo = new HashMap<>();

        distTo.put(start, 0.0);

        ExtrinsicMinPQ<Vertex> minPQ = new ArrayHeapMinPQ<>();
        minPQ.add(start, input.estimatedDistanceToGoal(start, end));

        while (minPQ.size() > 0) {
            if (minPQ.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;

                Vertex pos = end;
                while (!pos.equals(start)) {
                    solution.addFirst(pos);
                    pos = edgeTo.get(pos);
                }
                solution.addFirst(start);

                solutionWeight = distTo.get(end);
                explorationTime = sw.elapsedTime();
                return;
            }

            if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                explorationTime = sw.elapsedTime();
                return;
            }

            Vertex curr = minPQ.removeSmallest();
            numStatesExplored += 1;

            double distToCurr = distTo.get(curr);

            for (WeightedEdge<Vertex> edge : input.neighbors(curr)) {
                if (!distTo.containsKey(edge.to())
                        || distToCurr + edge.weight() < distTo.get(edge.to())) {

                    distTo.put(edge.to(), distToCurr + edge.weight());
                    edgeTo.put(edge.to(), curr);

                    if (minPQ.contains(edge.to())) {
                        minPQ.changePriority(edge.to(), distTo.get(edge.to())
                                + input.estimatedDistanceToGoal(edge.to(), end));
                    } else {
                        minPQ.add(edge.to(), distTo.get(edge.to())
                                + input.estimatedDistanceToGoal(edge.to(), end));
                    }
                }
            }
        }

        outcome = SolverOutcome.UNSOLVABLE;
        explorationTime = sw.elapsedTime();
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return explorationTime;
    }
}
