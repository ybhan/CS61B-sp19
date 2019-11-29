import java.util.Queue;
import java.util.ArrayDeque;

/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString {
    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;

    private Queue<Character> stringQueue;
    private int length;
    private int hashBeforeMod;
    private int hashFirstBase;

    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert (s.length() == length);

        stringQueue = new ArrayDeque<>(length + 1);
        for (int i = 0; i < length; i++) {
            stringQueue.add(s.charAt(i));
            hashBeforeMod = (hashBeforeMod * UNIQUECHARS) + s.charAt(i);
        }

        this.length = length;
        hashFirstBase = (int) Math.pow(UNIQUECHARS, length - 1);
    }

    /**
     * Adds a character to the back of the stored "string" and
     * removes the first character of the "string".
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        hashBeforeMod = (hashBeforeMod - stringQueue.peek() * hashFirstBase) * UNIQUECHARS + c;
        stringQueue.add(c);
        stringQueue.poll();
    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        for (char c : stringQueue) {
            strb.append(c);
        }
        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        return length;
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass() != RollingString.class) {
            return false;
        }
        RollingString o1 = (RollingString) o;
        if (o1.hashCode() != this.hashCode()) {
            return false;
        }
        return o1.toString().equals(this.toString());
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        return hashBeforeMod % PRIMEBASE;
    }
}
