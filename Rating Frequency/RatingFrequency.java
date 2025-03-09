public class RatingFrequency {
    public static void main (String[] args) {
        //Initialize a one-dimensional array with responses
        int[] responses = {1, 2, 5, 4, 3, 5, 2, 1, 3, 3, 1, 4, 3, 2, 5, 3, 4, 5, 3, 1};

        //Create a frequency array (index 0-4 represents ratings 1-5)
        int[] frequency = new int[5];

        //Count occurrences of each rating
        for (int response : responses) {
            frequency[response - 1]++; // Adjust index (rating 1 → index 0, etc.)
        }

        //Display the frequency of each rating
        System.out.println("Rating Frequency:");
        for (int i = 0; i < 5; i++) {
            System.out.println((i + 1) + ": " + frequency[i]); // i+1 represents rating value
        }
    }
}
