package es.datastructur.synthesizer;

public class Harp {
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a harp string of the given frequency.  */
    public Harp(double frequency) {
        int capacity = (int) Math.round(SR / frequency) * 2;
        buffer = new ArrayRingBuffer<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buffer.enqueue(0.0);
        }
    }

    /* Pluck the harp string by replacing the buffer with white noise. */
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
        double newSample = -(frontSample + buffer.peek()) / 2 * DECAY;
        buffer.enqueue(newSample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
