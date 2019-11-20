/* A client that uses the synthesizer package to replicate a plucked guitar string sound */

import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {
    /**
     * This keyboard arrangement imitates a piano keyboard: The “white keys” are on the qwerty and zxcv rows and the
     * “black keys” on the 12345 and asdf rows of the keyboard. There are 37 keys in total.
     */
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    /** The frequency corresponding to the i-th character of the string keyboard. */
    private static Double frequency(int i) {
        return 440 * Math.pow(2, (i - 24.0) / 12.0);
    }

    public static void main(String[] args) {
        /* create 37 guitar strings */
        GuitarString[] strings = new GuitarString[keyboard.length()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = new GuitarString(frequency(i));
        }

        //noinspection InfiniteLoopStatement
        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keyboard.indexOf(key) >= 0) {
                    strings[keyboard.indexOf(key)].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (GuitarString string : strings) {
                sample += string.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString string : strings) {
                string.tic();
            }
        }
    }
}
