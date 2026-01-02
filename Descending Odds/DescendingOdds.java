import java.util.Scanner;
import java.util.Random;

/**
 * Interactive Number Sorter and Algorithm Visualizer
 * 
 * This program helps you understand how sorting algorithms work by showing
 * each step of the process. You can compare different algorithms and see
 * which one is faster for different data sizes.
 * 
 * Concepts covered:
 * - Arrays and array manipulation
 * - Different sorting algorithms (Bubble, Selection, Insertion)
 * - Performance measurement with System.nanoTime()
 * - User input and menu systems
 * - Step by step algorithm visualization
 */
public class DescendingOdds {

    static Scanner scanner = new Scanner(System.in);
    static boolean showSteps = true; // Toggle for step by step mode

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   Number Sorter & Algorithm Visualizer");
        System.out.println("===========================================");

        boolean running = true;

        while (running) {
            showMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    runCustomSort();
                    break;
                case "2":
                    runOddNumbersDemo();
                    break;
                case "3":
                    compareAlgorithms();
                    break;
                case "4":
                    showSteps = !showSteps;
                    System.out.println("\nStep by step mode: " + (showSteps ? "ON" : "OFF"));
                    break;
                case "5":
                    learnAboutAlgorithms();
                    break;
                case "6":
                    System.out.println("\nHappy sorting!");
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
        System.out.println("1. Sort your own numbers");
        System.out.println("2. Demo: Sort odd numbers (original exercise)");
        System.out.println("3. Compare sorting algorithms");
        System.out.println("4. Toggle step by step mode (" + (showSteps ? "ON" : "OFF") + ")");
        System.out.println("5. Learn about the algorithms");
        System.out.println("6. Exit");
        System.out.println();
    }

    /**
     * Lets the user enter their own numbers or generate random ones
     * Then sorts them with their choice of algorithm
     */
    static void runCustomSort() {
        System.out.println("\n--- Custom Number Sorting ---");
        System.out.println("1. Enter numbers manually");
        System.out.println("2. Generate random numbers");
        System.out.print("Choice: ");

        String inputChoice = scanner.nextLine().trim();
        int[] numbers;

        if (inputChoice.equals("1")) {
            numbers = getManualNumbers();
        } else {
            numbers = getRandomNumbers();
        }

        if (numbers == null || numbers.length == 0) {
            System.out.println("No numbers to sort.");
            return;
        }

        System.out.println("\nYour numbers: " + arrayToString(numbers));

        // Choose sort order
        System.out.print("\nSort ascending or descending? (a/d): ");
        String order = scanner.nextLine().trim().toLowerCase();
        boolean ascending = !order.startsWith("d");

        // Choose algorithm
        System.out.println("\nChoose sorting algorithm:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Insertion Sort");
        System.out.print("Choice: ");

        String algoChoice = scanner.nextLine().trim();

        // Make a copy so we dont modify the original
        int[] toSort = numbers.clone();

        long startTime = System.nanoTime();

        switch (algoChoice) {
            case "1":
                bubbleSort(toSort, ascending);
                break;
            case "2":
                selectionSort(toSort, ascending);
                break;
            case "3":
                insertionSort(toSort, ascending);
                break;
            default:
                System.out.println("Invalid choice. Using bubble sort.");
                bubbleSort(toSort, ascending);
        }

        long endTime = System.nanoTime();
        double timeMs = (endTime - startTime) / 1000000.0;

        System.out.println("\nSorted result: " + arrayToString(toSort));
        System.out.println("Time taken: " + String.format("%.3f", timeMs) + " ms");
    }

    /**
     * The original exercise: sort odd numbers between 0 and 10 in descending order
     * Now enhanced with step by step visualization
     */
    static void runOddNumbersDemo() {
        System.out.println("\n--- Original Odd Numbers Exercise ---");
        System.out.println("Finding odd numbers between 0 and 10...\n");

        // We know there are 5 odd numbers: 1, 3, 5, 7, 9
        int[] odds = new int[5];
        int index = 0;

        // Loop through and find the odds
        for (int i = 1; i < 10; i += 2) {
            odds[index] = i;
            System.out.println("Found odd number: " + i);
            index++;
        }

        System.out.println("\nOdd numbers found: " + arrayToString(odds));
        System.out.println("\nNow sorting in descending order using Bubble Sort...\n");

        // Use bubble sort with visualization
        boolean oldShowSteps = showSteps;
        showSteps = true; // Force steps for demo
        bubbleSort(odds, false); // false = descending
        showSteps = oldShowSteps;

        System.out.println("\nFinal result: " + arrayToString(odds));
    }

    /**
     * Compares all three sorting algorithms on the same data
     * This shows how different algorithms perform differently
     */
    static void compareAlgorithms() {
        System.out.println("\n--- Algorithm Comparison ---");
        System.out.print("How many random numbers to sort? ");

        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            count = 20;
            System.out.println("Using default: 20 numbers");
        }

        // Generate random numbers
        Random random = new Random();
        int[] original = new int[count];
        for (int i = 0; i < count; i++) {
            original[i] = random.nextInt(1000);
        }

        System.out.println("\nOriginal array: " + arrayToString(original));
        System.out.println("\nRunning comparison (step by step disabled for speed)...\n");

        // Turn off steps for fair comparison
        boolean oldShowSteps = showSteps;
        showSteps = false;

        // Test Bubble Sort
        int[] bubbleArray = original.clone();
        long bubbleStart = System.nanoTime();
        bubbleSort(bubbleArray, true);
        long bubbleTime = System.nanoTime() - bubbleStart;

        // Test Selection Sort
        int[] selectionArray = original.clone();
        long selectionStart = System.nanoTime();
        selectionSort(selectionArray, true);
        long selectionTime = System.nanoTime() - selectionStart;

        // Test Insertion Sort
        int[] insertionArray = original.clone();
        long insertionStart = System.nanoTime();
        insertionSort(insertionArray, true);
        long insertionTime = System.nanoTime() - insertionStart;

        showSteps = oldShowSteps;

        // Show results
        System.out.println("Results for sorting " + count + " numbers:");
        System.out.println("----------------------------------------");
        System.out.printf("Bubble Sort:    %10.3f ms%n", bubbleTime / 1000000.0);
        System.out.printf("Selection Sort: %10.3f ms%n", selectionTime / 1000000.0);
        System.out.printf("Insertion Sort: %10.3f ms%n", insertionTime / 1000000.0);
        System.out.println("----------------------------------------");

        // Find the winner
        long fastest = Math.min(Math.min(bubbleTime, selectionTime), insertionTime);
        if (fastest == bubbleTime) {
            System.out.println("Winner: Bubble Sort!");
        } else if (fastest == selectionTime) {
            System.out.println("Winner: Selection Sort!");
        } else {
            System.out.println("Winner: Insertion Sort!");
        }

        System.out.println("\nSorted array: " + arrayToString(bubbleArray));
    }

    /**
     * Bubble Sort: Compare adjacent elements and swap if out of order
     * Repeat until no swaps needed. Simple but slow for large arrays.
     */
    static void bubbleSort(int[] arr, boolean ascending) {
        if (showSteps) {
            System.out.println("Starting Bubble Sort...");
            System.out.println("Bubble sort compares neighbors and swaps if needed\n");
        }

        int n = arr.length;
        int passCount = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            passCount++;

            if (showSteps) {
                System.out.println("Pass " + passCount + ": " + arrayToString(arr));
            }

            for (int j = 0; j < n - i - 1; j++) {
                // Check if we need to swap based on sort order
                boolean shouldSwap;
                if (ascending) {
                    shouldSwap = arr[j] > arr[j + 1];
                } else {
                    shouldSwap = arr[j] < arr[j + 1];
                }

                if (shouldSwap) {
                    // Swap the elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;

                    if (showSteps) {
                        System.out.println("  Swapped " + arr[j + 1] + " and " + arr[j] + " -> " + arrayToString(arr));
                    }
                }
            }

            // If no swaps happened, array is sorted
            if (!swapped) {
                if (showSteps) {
                    System.out.println("No swaps needed. Array is sorted!");
                }
                break;
            }
        }
    }

    /**
     * Selection Sort: Find the smallest (or largest) element and put it first
     * Then find the next smallest and put it second, etc.
     */
    static void selectionSort(int[] arr, boolean ascending) {
        if (showSteps) {
            System.out.println("Starting Selection Sort...");
            System.out.println("Selection sort finds the min/max and puts it in place\n");
        }

        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int selectedIndex = i;

            // Find the min (or max for descending)
            for (int j = i + 1; j < n; j++) {
                if (ascending) {
                    if (arr[j] < arr[selectedIndex]) {
                        selectedIndex = j;
                    }
                } else {
                    if (arr[j] > arr[selectedIndex]) {
                        selectedIndex = j;
                    }
                }
            }

            // Swap if we found a better element
            if (selectedIndex != i) {
                int temp = arr[i];
                arr[i] = arr[selectedIndex];
                arr[selectedIndex] = temp;

                if (showSteps) {
                    System.out.println("Selected " + arr[i] + " for position " + i + " -> " + arrayToString(arr));
                }
            }
        }
    }

    /**
     * Insertion Sort: Build the sorted array one element at a time
     * Take each element and insert it in the right position
     */
    static void insertionSort(int[] arr, boolean ascending) {
        if (showSteps) {
            System.out.println("Starting Insertion Sort...");
            System.out.println("Insertion sort builds a sorted section one by one\n");
        }

        int n = arr.length;

        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            // Move elements that are greater than key (or less for descending)
            while (j >= 0) {
                boolean shouldMove;
                if (ascending) {
                    shouldMove = arr[j] > key;
                } else {
                    shouldMove = arr[j] < key;
                }

                if (shouldMove) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }

            arr[j + 1] = key;

            if (showSteps) {
                System.out.println("Inserted " + key + " at position " + (j + 1) + " -> " + arrayToString(arr));
            }
        }
    }

    /**
     * Educational section explaining how each algorithm works
     */
    static void learnAboutAlgorithms() {
        System.out.println("\n===========================================");
        System.out.println("      Sorting Algorithms Explained");
        System.out.println("===========================================");

        System.out.println("\n--- BUBBLE SORT ---");
        System.out.println("How it works:");
        System.out.println("  1. Compare each pair of adjacent elements");
        System.out.println("  2. Swap them if theyre in the wrong order");
        System.out.println("  3. Repeat until no swaps are needed");
        System.out.println("Best for: Small arrays or nearly sorted data");
        System.out.println("Speed: O(n^2) which means slow for large data");

        System.out.println("\n--- SELECTION SORT ---");
        System.out.println("How it works:");
        System.out.println("  1. Find the smallest element in the array");
        System.out.println("  2. Swap it with the first unsorted position");
        System.out.println("  3. Repeat for the rest of the array");
        System.out.println("Best for: When memory writes are expensive");
        System.out.println("Speed: O(n^2) but fewer swaps than bubble sort");

        System.out.println("\n--- INSERTION SORT ---");
        System.out.println("How it works:");
        System.out.println("  1. Take the next unsorted element");
        System.out.println("  2. Find its correct position in the sorted part");
        System.out.println("  3. Shift elements and insert it");
        System.out.println("Best for: Small arrays or nearly sorted data");
        System.out.println("Speed: O(n^2) worst case, O(n) for sorted data");

        System.out.println("\n--- WHICH TO USE? ---");
        System.out.println("For real applications, use Arrays.sort() or");
        System.out.println("Collections.sort() which use optimized algorithms.");
        System.out.println("These simple sorts are for learning purposes.");

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Helper to get numbers from user input
     */
    static int[] getManualNumbers() {
        System.out.print("Enter numbers separated by spaces: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return null;
        }

        String[] parts = input.split("\\s+");
        int[] numbers = new int[parts.length];

        try {
            for (int i = 0; i < parts.length; i++) {
                numbers[i] = Integer.parseInt(parts[i]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter only numbers.");
            return null;
        }

        return numbers;
    }

    /**
     * Helper to generate random numbers
     */
    static int[] getRandomNumbers() {
        System.out.print("How many numbers? ");
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            count = 10;
            System.out.println("Using default: 10 numbers");
        }

        System.out.print("Maximum value? ");
        int max;
        try {
            max = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            max = 100;
            System.out.println("Using default: 100");
        }

        Random random = new Random();
        int[] numbers = new int[count];
        for (int i = 0; i < count; i++) {
            numbers[i] = random.nextInt(max) + 1;
        }

        return numbers;
    }

    /**
     * Converts array to a nice string format for display
     */
    static String arrayToString(int[] arr) {
        if (arr == null || arr.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
