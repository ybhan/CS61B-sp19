public class RabinKarpAlgorithm {
    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        if (pattern.length() > input.length()) {
            return -1;
        }

        int length = pattern.length();

        RollingString patternRoll = new RollingString(pattern, length);
        RollingString inputRoll = new RollingString(input.substring(0, length), length);

        if (patternRoll.equals(inputRoll)) {
            return 0;
        }

        for (int i = length; i < input.length(); i++) {
            inputRoll.addChar(input.charAt(i));
            if (patternRoll.equals(inputRoll)) {
                return i + 1 - length;
            }
        }
        return -1;
    }
}
