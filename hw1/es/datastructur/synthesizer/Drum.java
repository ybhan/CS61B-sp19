package es.datastructur.synthesizer;

public class Drum {
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = 1.0;  // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a drum of the given frequency.  */
    public Drum(double frequency) {
        int capacity = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buffer.enqueue(0.0);
        }
    }

    /* Pluck the drum by replacing the buffer with white noise. */
    public void pluck() {
        for (int i = 0; i < buffer.fillCount(); i++) {
            buffer.dequeue();
            buffer.enqueue(Math.random() - 0.5);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double frontSample = buffer.dequeue();
        double newSample = (frontSample + buffer.peek()) / 2 * DECAY;
        if (Math.random() < 0.5) {
            newSample = -newSample;
        }
        buffer.enqueue(newSample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
