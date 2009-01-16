public class Timer {

    public static int determineTime(double digits, String suffix)
            throws UnknownSuffixException {

        double time = digits;

        if (suffix == null || suffix.isEmpty()) {
            throw new UnknownSuffixException();
        } else if (suffix.equals("ms")) {
            /* Don't alter the digits, this the is default suffix.*/
        } else if (suffix.equals("s")) {
            /* Multiply the time with 60 */
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

    private static class UnknownSuffixException extends Exception {

        public UnknownSuffixException() {
            super("Unknown suffix.");
        }

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
