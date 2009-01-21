/*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful, but
* WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * The Timer program works just like the GNU utility &quot;sleep&quot;
 * &ndash; it takes arguments, tries to enterpret them as amounts of
 * time, then sleeps for that amount of time. The difference is that
 * this program supports milliseconds.
 */
public class Timer {

    /**
     * Determines the amount of time based on a number and a suffix.
     * <p>
     * The suffix can currently be any of "", "ms", "s", "m", "h" and
     * "d".
     *
     * @param digits a real number representing an amount of time.
     * @param suffix a valid suffix representing a time period (as in
     * seconds, minutes, hours, days, ...).
     * @return an amount of time in milliseconds.
     */
    public static int determineTime(double digits, String suffix)
            throws UnknownSuffixException {

        double time = digits;

        if (suffix == null || suffix.isEmpty()) {
            /* Don't alter the digits, this the is default
             * suffix. This is to support providing null or the empty
             * string as the suffix parameter and this sleeping for
             * the provided time interval in milliseconds. */
        } else if (suffix.equals("ms")) {
            /* Don't alter the digits, this the is default suffix. */
        } else if (suffix.equals("s")) {
            time *= 1000;
        } else if (suffix.equals("m")) {
            time *= 1000*60;
        } else if (suffix.equals("h")) {
            time *= 1000*60*60;
        } else if (suffix.equals("d")) {
            time *= 1000*60*60*24;
        } else {
            throw new UnknownSuffixException(suffix);
        }

        return (int) time;
    }

    /**
     * Extracts a suffix from a String on the format
     * <tt>&lt;digits&gt;&lt;suffix&gt;</tt>.
     *
     * @param input the string from which to extract a suffix.
     * @return the <tt>&lt;suffix&gt;</tt> part of <tt>input</tt> if
     * one can be found, otherwise the empty string.
     */
    public static String extractSuffix(String input) {
        char[] inputArray = input.toCharArray();
        if (inputArray == null || inputArray.length == 0) {
            return null;
        }

        /* Find out at what index the prefix starts. */
        int i = -1;
        while (++i < inputArray.length
               && (Character.isDigit(inputArray[i])
                   || inputArray[i] == '.')) {}

        return input.substring(i, input.length());
    }

    /**
     * Extracts the digits from a String on the format
     * <tt>&lt;digits&gt;&lt;suffix&gt;</tt>.
     *
     * @param input the String from which to extract digits.
     * @return the <tt>&lt;digits&gt;</tt> part of <tt>input</tt>, if
     * one can be found, as a Double.
     * @throws NumberFormatException if &lt;digits&gt; cannot be
     * interpreted as a Double.
     */
    public static Double extractTime(String input)
            throws NumberFormatException {
        char[] inputArray = input.toCharArray();
        if (inputArray == null || inputArray.length == 0) {
            return null;
        }

        /* Find out at what index the prefix starts. */
        int i = -1;
        while (++i < inputArray.length
               && (Character.isDigit(inputArray[i])
                   || inputArray[i] == '.')) {}

        return Double.parseDouble(input.substring(0, i));
    }

    /**
     * The UnknownSuffixException is thrown when a suffix is cannot be
     * found from a String.
     */
    private static class UnknownSuffixException extends Exception {

        /**
         * Creates a new UnknownSuffixException with the message
         * "Unknown suffix.".
         */
        public UnknownSuffixException() {
            super("Unknown suffix.");
        }

        /**
         * Creates a new UnknownSuffixException with the message
         * "Unknown suffix: " followed by the provided suffix.
         *
         * @param suffix the suffix that caused the exception.
         */
        public UnknownSuffixException(String suffix) {
            super("Unknown suffix: " + suffix);
        }
    }

    public static void main(String args[]) {

        double totalTime;

        if (args.length < 1) {
            System.err.println("Too few arguments.");
        } else {

            totalTime = 0;
            for (int i = 0; i < args.length; i++) {
                try {
                    String suffix = extractSuffix(args[i]);
                    double time = extractTime(args[i]);
                    totalTime += determineTime(time, suffix);
                } catch (UnknownSuffixException use) {
                    System.err.println(use.getMessage());
                } catch (NumberFormatException nfe) {
                    System.err.println("Could not determine time of \""
                                       + args[i] + "\"");
                }
            }

            try {
                Thread.sleep((int) totalTime);
            } catch (InterruptedException ie) {
                return;
            }
        }
    }
}
