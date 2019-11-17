import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    @Test
    public void testArrayDeque() {
        StdRandom.setSeed(123);

        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();

        StringBuilder callsTrack = new StringBuilder();

        for (int i = 0; i < 100; i++) {
            int randomOperation = StdRandom.uniform(4);
            int randomElement = StdRandom.uniform(10);

            if (ads1.isEmpty()) {
                randomOperation /= 2;
            }

            switch (randomOperation) {
                case 0:
                    sad1.addFirst(randomElement);
                    ads1.addFirst(randomElement);
                    callsTrack.append(String.format("addFirst(%d)\n", randomElement));
                    break;
                case 1:
                    sad1.addLast(randomElement);
                    ads1.addLast(randomElement);
                    callsTrack.append(String.format("addLast(%d)\n", randomElement));
                    break;
                case 2:
                    callsTrack.append("removeFirst()\n");
                    assertEquals(callsTrack.toString(), ads1.removeFirst(), sad1.removeFirst());
                    break;
                case 3:
                    callsTrack.append("removeLast()\n");
                    assertEquals(callsTrack.toString(), ads1.removeLast(), sad1.removeLast());
                    break;
            }
        }
    }
}
