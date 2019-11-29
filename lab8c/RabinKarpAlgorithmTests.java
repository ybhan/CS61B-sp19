import org.junit.Test;
import static org.junit.Assert.*;

public class RabinKarpAlgorithmTests {
    @Test
    public void basic() {
        String input = "hello";
        String pattern1 = "he";
        String pattern2 = "llo";
        String pattern3 = "hello";
        String pattern4 = "eh";
        String pattern5 = "hello!";
        assertEquals(0, RabinKarpAlgorithm.rabinKarp(input, pattern1));
        assertEquals(2, RabinKarpAlgorithm.rabinKarp(input, pattern2));
        assertEquals(0, RabinKarpAlgorithm.rabinKarp(input, pattern3));
        assertEquals(-1, RabinKarpAlgorithm.rabinKarp(input, pattern4));
        assertEquals(-1, RabinKarpAlgorithm.rabinKarp(input, pattern5));
    }
}
