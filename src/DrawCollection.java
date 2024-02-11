import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DrawCollection {
    public List<Draw> drawCollection;
    private final String filePath;

    public DrawCollection(String filePath) {
        this.drawCollection = new ArrayList<>();
        this.filePath = filePath;
    }

    public List<Draw> getAllDraws() {
        return drawCollection;
    }

    public String getFilePath() {
        return filePath;
    }

    public void loadData(String fn) {
        try {
            Gson gson = new Gson();

            Draw[] draws = gson.fromJson(new FileReader(fn), Draw[].class);

            drawCollection.addAll(Arrays.asList(draws));

            Utils.printMessage("\nData loaded successfully!");
        } catch(IOException e) {
            System.out.println("\nError loading data from JSON file: " + e.getMessage());
        }

    }

    public void printLatestDraw(List<Draw> allDraws, String gameType) {
        if (allDraws.isEmpty()) {
            Utils.printMessage("\nNo draws available. Could be an error with the data files.");
        }

        Draw latestDraw = allDraws.get(allDraws.size() - 1);
        // Print the formatted draw information
        Utils.printMessage("\nLatest " + gameType + " Draw:");
        latestDraw.printDraw();
    }

    public void deleteDraw(int drawNumber) {
        for (Iterator<Draw> iterator = drawCollection.iterator(); iterator.hasNext();) {
            Draw draw = iterator.next();
            if (draw.getDrawNumber() == drawNumber) {
                // Remove the draw object from the list
                iterator.remove();
                System.out.println("\nDraw " + drawNumber + " deleted successfully.");
                return;
            }
        }
        // If the draw with the specified drawNumber is not found
        System.out.println("\nDraw " + drawNumber + " not found.");
    }

    public void addNewDraw(Draw newDraw) {
        // Check if the draw number already exists
        for (Draw draw : drawCollection) {
            if (draw.getDrawNumber() == newDraw.getDrawNumber()) {
                System.out.println("\nDraw " + newDraw.getDrawNumber() + " already exists. Unable to add.");
                return;
            }
        }

        // If the draw number does not exist, add the new draw
        drawCollection.add(newDraw);
        Utils.printMessage("\nNew draw added successfully.");
    }

    public void printNumberFreq(Map<Integer, Integer> numbersFreq, String numberType) {
        // Calculate the total number of draws
        int totalDraws = drawCollection.size();

        // Sort the numbers by frequency percentage in descending order
        List<Map.Entry<Integer, Integer>> sortedEntries = new ArrayList<>(numbersFreq.entrySet());
        sortedEntries.sort((entry1, entry2) -> {
            double percentage1 = (double) entry1.getValue() / totalDraws;
            double percentage2 = (double) entry2.getValue() / totalDraws;
            return Double.compare(percentage2, percentage1); // Sort in descending order
        });

        Utils.printMessage("\n" + numberType + " numbers frequency:");

        // Print the numbers and their frequency in percentage in descending order
        for (Map.Entry<Integer, Integer> entry : sortedEntries) {
            int number = entry.getKey();
            int frequency = entry.getValue();
            double percentage = (double) frequency / totalDraws * 100;

            // Add a leading space for single-digit numbers
            String numberStr = (number < 10) ? " " + number : String.valueOf(number);

            System.out.printf("Number %s: %d (%.2f%%)\n", numberStr, frequency, percentage);
        }
    }

    public Map<Integer, Integer> getWinningsFreq() {
        Map<Integer, Integer> winningsFreq = new HashMap<>();

        // Iterate over each draw in the draw collection
        for (Draw draw : drawCollection) {
            // Iterate over the winning numbers of each draw
            for (Integer number : draw.getWinningNumbers()) {
                // Increment the frequency count for each winning number
                winningsFreq.put(number, winningsFreq.getOrDefault(number, 0) + 1);
            }
        }

        return winningsFreq;
    }

    public Map<Integer, Integer> getSpecialFreq() {
        Map<Integer, Integer> specialFreq = new HashMap<>();

        // Iterate through each draw in the collection
        for (Draw draw : drawCollection) {
            // Get the list of special numbers for the draw
            List<Integer> specialNumbers = draw.getSpecialNumbers();

            // Update the frequency count for each special number
            for (int specialNumber : specialNumbers) {
                specialFreq.put(specialNumber, specialFreq.getOrDefault(specialNumber, 0) + 1);
            }
        }

        return specialFreq;
    }

    public void saveData(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(drawCollection, writer);
            System.out.println("\nData saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public void clearDraws() {
        drawCollection.clear();
    }
}
