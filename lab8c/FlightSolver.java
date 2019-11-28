import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are <= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    PriorityQueue<Flight> startTimePQ;
    PriorityQueue<Flight> endTimePQ;

    public FlightSolver(ArrayList<Flight> flights) {
        startTimePQ = new PriorityQueue<>(flights.size(), Comparator.comparingInt(Flight::startTime));
        endTimePQ = new PriorityQueue<>(flights.size(), Comparator.comparingInt(Flight::endTime));
        startTimePQ.addAll(flights);
        endTimePQ.addAll(flights);
    }

    public int solve() {
        int currentPassengers = 0;
        int maxPassengers = 0;

        while (startTimePQ.size() > 0) {
            if (startTimePQ.peek().startTime() <= endTimePQ.peek().endTime()) {
                currentPassengers += startTimePQ.poll().passengers();
                if (currentPassengers > maxPassengers) {
                    maxPassengers = currentPassengers;
                }
            } else {
                currentPassengers -= endTimePQ.poll().passengers();
            }
        }

        return maxPassengers;
    }
}
