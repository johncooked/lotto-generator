import java.util.*;

public class Utils {

    /**
    * Displays a message to the console.
    * @param mes The message to be displayed.
     **/
    public static void printMessage(String mes) {
        System.out.println(mes);
    }

    /**
     * Displays a menu, with the options numbered from 1 to options. Length,
     * then gets a validated integer in the range 1..options.length.
     * Subtracts 1, then returns the result. If the supplied list of options
     * is empty, returns an error value (-1).
     *
     * @param title   A heading to display before the menu is listed.
     * @param options The list of objects to be displayed.
     * @return Return value is either -1 (if no options are provided) or a
     * value in 0 .. (options.Length-1).
     */
    public static int getOption(String title, Object... options) {
        if (options.length == 0) {
            return -1;
        }

        int digitsNeeded = (int) (1 + Math.floor(Math.log10(options.length)));

        System.out.println(title);

        for (int i = 0; i < options.length; i++) {
            System.out.printf("   %1$" + digitsNeeded + "s. %2$s%n", (i + 1), options[i]);
        }

        int option = getInt(String.format("Please enter a number between 1 and %d to choose", options.length), 1, options.length);

        return option - 1;
    }

    /**
     * Gets a validated integer between the designated lower and upper bounds.
     *
     * @param prompt Text used to ask the user for input.
     * @param min    The lower bound.
     * @param max    The upper bound.
     * @return A value x such that lowerBound <= x <= upperBound.
     */
    public static int getInt(String prompt, int min, int max) {
        if (min > max) {
            int t = min;
            min = max;
            max = t;
        }

        while (true) {
            int result = getInt(prompt);

            if (min <= result && result <= max) {
                return result;
            } else {
                error("Supplied value is out of range");
            }
        }
    }

    /**
     * Gets a validated integer.
     *
     * @param prompt Text used to ask the user for input.
     * @return An integer supplied by the user.
     */
    public static int getInt(String prompt) {
        while (true) {
            String response = getInput(prompt);

            try {
                return Integer.parseInt(response);
            } catch (NumberFormatException e) {
                error("Supplied value is not an integer");
            }
        }
    }

    /**
     * Gets input from the user.
     *
     * @param prompt Text used to ask the user for input.
     * @return The user's input as a string.
     */
    private static String getInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    private static void error(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Simulates a loading animation with a custom message.
     * @param mes The custom message to be displayed.*/
    public static void load(String mes) {
        System.out.print(mes);
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000); // Sleep for 1 second
                System.out.print(".");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    /**
     * Appends numbers to a StringBuilder with optional leading zero for single digit numbers.
     * @param sb The StringBuilder to append numbers to.
     * @param numbers The list of numbers to append.
     */
    public static void appendNumbers(StringBuilder sb, List<Integer> numbers) {
        for (int number : numbers) {
            if (number < 10) {
                sb.append(" "); // Append leading zero
            }
            sb.append(number).append(" ");
        }
    }

    /**
     * Generates multiple random plays for a lottery game.
     * @param numPlays The number of plays to generate.
     * @param maxWinningNumber The maximum winning number allowed.
     * @param numSpecialNumbers The number of special numbers (e.g., Powerball or supplementary numbers).
     * @param isPowerball Flag indicating whether it's a Powerball game.
     * @return A list of plays, where each play is a list of integers representing winning and special numbers.
     */
    public static List<List<Integer>> generateRandomPlays(int numPlays, int maxWinningNumber, int numSpecialNumbers, boolean isPowerball) {
        List<List<Integer>> plays = new ArrayList<>();

        for (int i = 0; i < numPlays; i++) {
            List<Integer> play = generateRandomPlay(maxWinningNumber, numSpecialNumbers, isPowerball);
            plays.add(play);
        }

        return plays;
    }

    /**
     * Generates a single random play for a lottery game.
     * @param maxWinningNumber The maximum winning number allowed.
     * @param numSpecialNumbers The number of special numbers (e.g., Powerball or supplementary numbers).
     * @param isPowerball Flag indicating whether it's a Powerball game.
     * @return A list of integers representing the winning and special numbers for the play.
     */
    private static List<Integer> generateRandomPlay(int maxWinningNumber, int numSpecialNumbers, boolean isPowerball) {
        List<Integer> play = new ArrayList<>();
        Set<Integer> generatedNumbers = new HashSet<>();
        Random random = new Random();

        // Generate winning numbers
        for (int i = 0; i < 7; i++) {
            int num;
            do {
                num = random.nextInt(maxWinningNumber) + 1;
            } while (generatedNumbers.contains(num));

            play.add(num);
            generatedNumbers.add(num);
        }

        // Generate special numbers (Powerball or supplementary numbers)
        if (isPowerball) {
            int powerball;
            do {
                powerball = random.nextInt(20) + 1;
            } while (generatedNumbers.contains(powerball));

            play.add(powerball);
            generatedNumbers.add(powerball);
        } else {
            for (int i = 0; i < numSpecialNumbers; i++) {
                int special;
                do {
                    special = random.nextInt(maxWinningNumber) + 1;
                } while (generatedNumbers.contains(special));

                play.add(special);
                generatedNumbers.add(special);
            }
        }

        return play;
    }

    /**
     * Generates a list of random plays considering frequency data.
     *
     * @param numPlays      The number of random plays to generate.
     * @param winningFreq   A map containing the frequency of winning numbers.
     * @param specialFreq   A map containing the frequency of special numbers.
     * @param isPowerball   A boolean indicating whether the game type is Powerball.
     * @return A list of random plays.
     */
    public static List<List<Integer>> generatePlaysFreq(int numPlays, Map<Integer, Integer> winningFreq, Map<Integer, Integer> specialFreq, boolean isPowerball) {
        List<List<Integer>> plays = new ArrayList<>();

        // Generate the specified number of random plays
        for (int i = 0; i < numPlays; i++) {
            List<Integer> play = generatePlayFreq(winningFreq, specialFreq, isPowerball);
            plays.add(play);
        }

        return plays;
    }

    /**
     * Generates a single random play considering frequency data.
     *
     * @param winningFreq   A map containing the frequency of winning numbers.
     * @param specialFreq   A map containing the frequency of special numbers.
     * @param isPowerball   A boolean indicating whether the game type is Powerball.
     * @return A single random play.
     */
    private static List<Integer> generatePlayFreq(Map<Integer, Integer> winningFreq, Map<Integer, Integer> specialFreq, boolean isPowerball) {
        List<Integer> play = new ArrayList<>();
        Random random = new Random();

        // Generate winning numbers
        for (int i = 0; i < 7; i++) {
            int num = generateNumFreq(winningFreq, random);
            play.add(num);
        }

        // Generate special numbers (Powerball or supplementary numbers)
        if (isPowerball) {
            int powerball = generateNumFreq(specialFreq, random);
            play.add(powerball);
        } else {
            for (int i = 0; i < 3; i++) {
                int special = generateNumFreq(specialFreq, random);
                play.add(special);
            }
        }

        return play;
    }

    /**
     * Generates a random number considering frequency data.
     *
     * @param freqMap   A map containing the frequency of numbers.
     * @param random    A Random object for generating random numbers.
     * @return A randomly generated number based on frequency data.
     */
    private static int generateNumFreq(Map<Integer, Integer> freqMap, Random random) {
        // Calculate the total frequency
        int totalFrequency = freqMap.values().stream().mapToInt(Integer::intValue).sum();

        // Generate a random number between 1 and the total frequency
        int randomNumber = random.nextInt(totalFrequency) + 1;

        // Iterate through the frequency map to find the corresponding number
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            randomNumber -= entry.getValue();
            if (randomNumber <= 0) {
                return entry.getKey(); // Return the number when the random number reaches or exceeds 0
            }
        }

        // This should never be reached
        throw new IllegalStateException("Failed to generate random number with frequency.");
    }

    public static List<Integer> parseNumbers(String[] numbersStr) {
        List<Integer> numbers = new ArrayList<>();
        for (String str : numbersStr) {
            numbers.add(Integer.parseInt(str));
        }
        return numbers;
    }

    public static void printPlays(String gameType, List<List<Integer>> plays) {
        System.out.println("\nRandom " + gameType + " plays:");
        for (int i = 0; i < plays.size(); i++) {
            List<Integer> play = plays.get(i);
            printPlay(i + 1, play);
        }
    }

    public static void printPlay(int playNumber, List<Integer> play) {
        System.out.println("Play " + playNumber + ": " + play);
    }

    public static void waitInput(Scanner scanner) {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
