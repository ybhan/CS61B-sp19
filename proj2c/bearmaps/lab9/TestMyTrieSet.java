package bearmaps.lab9;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Jenny Huang on 3/12/19.
 * Add testLongestPrefixOf() by Yuanbo Han on 12/3/19.
 */
public class TestMyTrieSet {

    // assumes add/contains work
    @Test
    public void sanityClearTest() {
        MyTrieSet t = new MyTrieSet();
        for (int i = 0; i < 455; i++) {
            t.add("hi" + i);
            // make sure put is working via contains
            assertTrue(t.contains("hi" + i));
        }
        t.clear();
        for (int i = 0; i < 455; i++) {
            assertFalse(t.contains("hi" + i));
        }
    }

    // assumes add works
    @Test
    public void sanityContainsTest() {
        MyTrieSet t = new MyTrieSet();
        assertFalse(t.contains("waterYouDoingHere"));
        t.add("waterYouDoingHere");
        assertTrue(t.contains("waterYouDoingHere"));
    }

    // assumes add works
    @Test
    public void sanityPrefixTest() {
        String[] saStrings = new String[]{"same", "sam", "sad", "sap"};
        String[] otherStrings = new String[]{"a", "awls", "hello"};

        MyTrieSet t = new MyTrieSet();
        for (String s : saStrings) {
            t.add(s);
        }
        for (String s : otherStrings) {
            t.add(s);
        }

        List<String> keys = t.keysWithPrefix("sa");
        for (String s : saStrings) {
            assertTrue(keys.contains(s));
        }
        for (String s : otherStrings) {
            assertFalse(keys.contains(s));
        }
    }

    @Test
    public void testLongestPrefixOf() {
        String[] strings = new String[]{"smell", "sudo", "small", "peanut", "smart"};

        MyTrieSet t = new MyTrieSet();
        for (String s : strings) {
            t.add(s);
        }

        assertEquals("sma", t.longestPrefixOf("smaug"));
        assertEquals("sm", t.longestPrefixOf("sm"));
        assertEquals("", t.longestPrefixOf("other"));
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestMyTrieSet.class);
    }
}
