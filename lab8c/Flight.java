/**
 * Represents a flight in the Flight problem.
 */
public class Flight {

    private int startTime;
    private int endTime;
    private int passengers;

    public Flight(int start, int end, int numPassengers) {
        startTime = start;
        endTime = end;
        passengers = numPassengers;
    }

    public int startTime() {
        return startTime;
    }

    public int endTime() {
        return endTime;
    }

    public int passengers() {
        return passengers;
    }
}
