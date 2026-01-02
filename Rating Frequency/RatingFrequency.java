import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

/**
 * Survey and Poll Manager
 * 
 * A complete tool for creating surveys, collecting responses, and analyzing
 * results.
 * This started as a simple frequency counter and evolved into a full polling
 * system.
 * 
 * Concepts covered:
 * - Arrays and ArrayLists
 * - Frequency counting and statistics
 * - ASCII bar chart visualization
 * - File I/O for saving/loading data
 * - Menu driven applications
 * - Mathematical calculations (mean, median, mode)
 */
public class RatingFrequency {

    static Scanner scanner = new Scanner(System.in);

    // We store multiple questions with their responses
    static ArrayList<String> questions = new ArrayList<>();
    static ArrayList<int[]> responses = new ArrayList<>();

    // Default scale is 1 to 5, but can be changed
    static int minRating = 1;
    static int maxRating = 5;

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("       Survey and Poll Manager");
        System.out.println("===========================================");

        // Load any saved data
        loadSurvey("survey_data.txt");

        boolean running = true;

        while (running) {
            showMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addQuestion();
                    break;
                case "2":
                    collectResponses();
                    break;
                case "3":
                    viewResults();
                    break;
                case "4":
                    viewAllQuestions();
                    break;
                case "5":
                    showStatistics();
                    break;
                case "6":
                    changeScale();
                    break;
                case "7":
                    saveSurvey("survey_data.txt");
                    break;
                case "8":
                    runDemo();
                    break;
                case "9":
                    System.out.println("\nGoodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid option. Try again.");
            }
        }

        scanner.close();
    }

    static void showMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add survey question");
        System.out.println("2. Collect responses");
        System.out.println("3. View results with chart");
        System.out.println("4. View all questions");
        System.out.println("5. Show detailed statistics");
        System.out.println("6. Change rating scale (current: " + minRating + " to " + maxRating + ")");
        System.out.println("7. Save survey to file");
        System.out.println("8. Run demo with sample data");
        System.out.println("9. Exit");
        System.out.println("\nQuestions: " + questions.size() + " | Scale: " + minRating + " to " + maxRating);
    }

    /**
     * Adds a new question to the survey
     */
    static void addQuestion() {
        System.out.println("\n--- Add Question ---");
        System.out.print("Enter your question: ");
        String question = scanner.nextLine().trim();

        if (question.isEmpty()) {
            System.out.println("Question cannot be empty.");
            return;
        }

        questions.add(question);
        // Initialize empty response array for this question
        responses.add(new int[0]);

        System.out.println("Question added! (#" + questions.size() + ")");
        System.out.println("Now use option 2 to collect responses for it.");
    }

    /**
     * Collects responses for a question
     */
    static void collectResponses() {
        if (questions.isEmpty()) {
            System.out.println("\nNo questions yet. Add one first.");
            return;
        }

        System.out.println("\n--- Collect Responses ---");
        System.out.println("Select a question:\n");

        for (int i = 0; i < questions.size(); i++) {
            int responseCount = responses.get(i).length;
            System.out.println((i + 1) + ". " + questions.get(i) + " (" + responseCount + " responses)");
        }

        System.out.print("\nQuestion number: ");
        int qNum;
        try {
            qNum = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        if (qNum < 0 || qNum >= questions.size()) {
            System.out.println("Invalid question number.");
            return;
        }

        System.out.println("\nQuestion: " + questions.get(qNum));
        System.out.println("Enter ratings (" + minRating + " to " + maxRating + "), separated by spaces.");
        System.out.println("Or enter one at a time. Type 'done' when finished.\n");

        ArrayList<Integer> newResponses = new ArrayList<>();

        // Add existing responses
        for (int r : responses.get(qNum)) {
            newResponses.add(r);
        }

        while (true) {
            System.out.print("Response: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            // Handle multiple space separated values
            String[] parts = input.split("\\s+");
            for (String part : parts) {
                try {
                    int rating = Integer.parseInt(part);
                    if (rating >= minRating && rating <= maxRating) {
                        newResponses.add(rating);
                        System.out.println("  Added: " + rating);
                    } else {
                        System.out.println("  Skipped " + rating + " (out of range)");
                    }
                } catch (NumberFormatException e) {
                    if (!part.isEmpty()) {
                        System.out.println("  Skipped '" + part + "' (not a number)");
                    }
                }
            }
        }

        // Convert ArrayList to array and store
        int[] responseArray = new int[newResponses.size()];
        for (int i = 0; i < newResponses.size(); i++) {
            responseArray[i] = newResponses.get(i);
        }
        responses.set(qNum, responseArray);

        System.out.println("\nTotal responses for this question: " + responseArray.length);
    }

    /**
     * Views results for a question with bar chart
     */
    static void viewResults() {
        if (questions.isEmpty()) {
            System.out.println("\nNo questions yet.");
            return;
        }

        System.out.println("\n--- View Results ---");

        for (int i = 0; i < questions.size(); i++) {
            int responseCount = responses.get(i).length;
            System.out.println((i + 1) + ". " + questions.get(i) + " (" + responseCount + " responses)");
        }

        System.out.print("\nQuestion number (or 0 for all): ");
        int qNum;
        try {
            qNum = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        if (qNum == 0) {
            // Show all questions
            for (int i = 0; i < questions.size(); i++) {
                showBarChart(i);
            }
        } else if (qNum >= 1 && qNum <= questions.size()) {
            showBarChart(qNum - 1);
        } else {
            System.out.println("Invalid question number.");
        }
    }

    /**
     * Shows a bar chart for a specific question
     */
    static void showBarChart(int questionIndex) {
        String question = questions.get(questionIndex);
        int[] data = responses.get(questionIndex);

        System.out.println("\n========================================");
        System.out.println("Q: " + question);
        System.out.println("========================================");

        if (data.length == 0) {
            System.out.println("No responses yet.");
            return;
        }

        // Count frequency for each rating
        int scaleSize = maxRating - minRating + 1;
        int[] frequency = new int[scaleSize];

        for (int rating : data) {
            int index = rating - minRating;
            if (index >= 0 && index < scaleSize) {
                frequency[index]++;
            }
        }

        // Find max frequency for scaling the bars
        int maxFreq = 0;
        for (int f : frequency) {
            if (f > maxFreq)
                maxFreq = f;
        }

        // Max bar width
        int maxBarWidth = 40;

        System.out.println("\nRating | Frequency | Bar");
        System.out.println("-------+-----------+" + "-".repeat(maxBarWidth + 2));

        for (int i = 0; i < scaleSize; i++) {
            int rating = minRating + i;
            int count = frequency[i];

            // Calculate bar length proportionally
            int barLength = 0;
            if (maxFreq > 0) {
                barLength = (int) ((double) count / maxFreq * maxBarWidth);
            }

            String bar = "#".repeat(barLength);
            double percentage = (double) count / data.length * 100;

            System.out.printf("   %d   |    %3d    | %-40s (%.1f%%)\n",
                    rating, count, bar, percentage);
        }

        System.out.println("\nTotal responses: " + data.length);
    }

    /**
     * View all questions in a list
     */
    static void viewAllQuestions() {
        System.out.println("\n--- All Questions ---");

        if (questions.isEmpty()) {
            System.out.println("No questions yet. Add one with option 1.");
            return;
        }

        for (int i = 0; i < questions.size(); i++) {
            int count = responses.get(i).length;
            System.out.println((i + 1) + ". " + questions.get(i));
            System.out.println("   Responses: " + count);
        }
    }

    /**
     * Shows detailed statistics for a question
     */
    static void showStatistics() {
        if (questions.isEmpty()) {
            System.out.println("\nNo questions yet.");
            return;
        }

        System.out.println("\n--- Detailed Statistics ---");

        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ". " + questions.get(i));
        }

        System.out.print("\nQuestion number: ");
        int qNum;
        try {
            qNum = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        if (qNum < 0 || qNum >= questions.size()) {
            System.out.println("Invalid question number.");
            return;
        }

        int[] data = responses.get(qNum);

        if (data.length == 0) {
            System.out.println("\nNo responses for this question yet.");
            return;
        }

        System.out.println("\n=== Statistics for: " + questions.get(qNum) + " ===\n");

        // Calculate mean (average)
        double sum = 0;
        for (int r : data) {
            sum += r;
        }
        double mean = sum / data.length;

        // Calculate median (middle value when sorted)
        int[] sorted = data.clone();
        java.util.Arrays.sort(sorted);
        double median;
        if (sorted.length % 2 == 0) {
            median = (sorted[sorted.length / 2 - 1] + sorted[sorted.length / 2]) / 2.0;
        } else {
            median = sorted[sorted.length / 2];
        }

        // Calculate mode (most frequent value)
        int scaleSize = maxRating - minRating + 1;
        int[] frequency = new int[scaleSize];
        for (int r : data) {
            frequency[r - minRating]++;
        }

        int maxFreq = 0;
        int mode = minRating;
        for (int i = 0; i < scaleSize; i++) {
            if (frequency[i] > maxFreq) {
                maxFreq = frequency[i];
                mode = minRating + i;
            }
        }

        // Calculate standard deviation
        double sumSquaredDiff = 0;
        for (int r : data) {
            sumSquaredDiff += Math.pow(r - mean, 2);
        }
        double stdDev = Math.sqrt(sumSquaredDiff / data.length);

        // Find min and max
        int min = sorted[0];
        int max = sorted[sorted.length - 1];

        System.out.println("Total Responses: " + data.length);
        System.out.println();
        System.out.println("Mean (Average):   " + String.format("%.2f", mean));
        System.out.println("Median (Middle):  " + String.format("%.1f", median));
        System.out.println("Mode (Most Freq): " + mode + " (appeared " + maxFreq + " times)");
        System.out.println();
        System.out.println("Standard Dev:     " + String.format("%.2f", stdDev));
        System.out.println("Range:            " + min + " to " + max);
        System.out.println();

        // Interpretation
        System.out.println("--- Interpretation ---");
        if (mean >= maxRating - 0.5) {
            System.out.println("Excellent! Very high satisfaction.");
        } else if (mean >= (maxRating + minRating) / 2.0 + 0.5) {
            System.out.println("Good. Above average satisfaction.");
        } else if (mean >= (maxRating + minRating) / 2.0 - 0.5) {
            System.out.println("Neutral. Room for improvement.");
        } else {
            System.out.println("Needs attention. Below average.");
        }
    }

    /**
     * Changes the rating scale
     */
    static void changeScale() {
        System.out.println("\n--- Change Rating Scale ---");
        System.out.println("Current scale: " + minRating + " to " + maxRating);
        System.out.println("\nNote: Changing scale will clear existing responses.");
        System.out.print("Continue? (yes/no): ");

        if (!scanner.nextLine().trim().toLowerCase().startsWith("y")) {
            System.out.println("Cancelled.");
            return;
        }

        System.out.print("New minimum rating: ");
        try {
            int newMin = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("New maximum rating: ");
            int newMax = Integer.parseInt(scanner.nextLine().trim());

            if (newMin >= newMax) {
                System.out.println("Min must be less than max.");
                return;
            }

            if (newMax - newMin > 10) {
                System.out.println("Scale too large. Max range is 10.");
                return;
            }

            minRating = newMin;
            maxRating = newMax;

            // Clear responses since old data wont fit new scale
            for (int i = 0; i < responses.size(); i++) {
                responses.set(i, new int[0]);
            }

            System.out.println("Scale updated to " + minRating + " to " + maxRating);
            System.out.println("Responses cleared.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }

    /**
     * Runs a demo with sample data
     */
    static void runDemo() {
        System.out.println("\n--- Running Demo ---");

        // Add sample questions
        questions.clear();
        responses.clear();

        questions.add("How satisfied are you with our service?");
        questions.add("How likely are you to recommend us?");
        questions.add("How would you rate the quality?");

        // Add sample responses
        int[] sampleResponses1 = { 4, 5, 3, 5, 4, 4, 5, 3, 4, 5, 2, 4, 5, 4, 3, 5, 4, 4 };
        int[] sampleResponses2 = { 5, 5, 4, 5, 3, 5, 4, 5, 5, 4, 5, 3, 4, 5, 5 };
        int[] sampleResponses3 = { 4, 4, 3, 4, 5, 3, 4, 4, 3, 5, 4, 4, 3, 4 };

        responses.add(sampleResponses1);
        responses.add(sampleResponses2);
        responses.add(sampleResponses3);

        System.out.println("Added 3 questions with sample responses.\n");

        // Show the first result
        showBarChart(0);
    }

    /**
     * Saves survey data to a file
     */
    static void saveSurvey(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // First line is scale
            writer.write("SCALE:" + minRating + "," + maxRating + "\n");

            // Then each question with its responses
            for (int i = 0; i < questions.size(); i++) {
                writer.write("Q:" + questions.get(i) + "\n");

                StringBuilder sb = new StringBuilder("R:");
                int[] data = responses.get(i);
                for (int j = 0; j < data.length; j++) {
                    sb.append(data[j]);
                    if (j < data.length - 1) {
                        sb.append(",");
                    }
                }
                writer.write(sb.toString() + "\n");
            }

            System.out.println("\nSurvey saved to " + filename);

        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    /**
     * Loads survey data from a file
     */
    static void loadSurvey(String filename) {
        File file = new File(filename);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String currentQuestion = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("SCALE:")) {
                    String[] parts = line.substring(6).split(",");
                    minRating = Integer.parseInt(parts[0]);
                    maxRating = Integer.parseInt(parts[1]);
                } else if (line.startsWith("Q:")) {
                    currentQuestion = line.substring(2);
                    questions.add(currentQuestion);
                } else if (line.startsWith("R:")) {
                    String dataStr = line.substring(2);
                    if (dataStr.isEmpty()) {
                        responses.add(new int[0]);
                    } else {
                        String[] parts = dataStr.split(",");
                        int[] data = new int[parts.length];
                        for (int i = 0; i < parts.length; i++) {
                            data[i] = Integer.parseInt(parts[i].trim());
                        }
                        responses.add(data);
                    }
                }
            }

            if (!questions.isEmpty()) {
                System.out.println("Loaded " + questions.size() + " questions from " + filename);
            }

        } catch (IOException e) {
            // Silent fail, file might not exist yet
        } catch (NumberFormatException e) {
            System.out.println("Error reading data file. Starting fresh.");
            questions.clear();
            responses.clear();
        }
    }
}
