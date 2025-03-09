public class DescendingOdds {
    public static void main(String[] args) {
        // We know there are 5 odd numbers between 0 and 10: 1, 3, 5, 7, 9
        int[] odds = new int[5];
        int index = 0;
        
        for (int i = 0; i <= 20; i++) {
            if (i == 10) continue;
            if (i > 0 && i < 10 && i % 2 != 0) {
                odds[index] = i;
                index++;
            }
        }
        
        // Simple bubble sort to sort the array in descending order
        for (int i = 0; i < odds.length - 1; i++) {
            for (int j = i + 1; j < odds.length; j++) {
                if (odds[i] < odds[j]) {
                    // swap odds[i] and odds[j]
                    int temp = odds[i];
                    odds[i] = odds[j];
                    odds[j] = temp;
                }
            }
        }
        
        // Print the sorted array
        for (int i = 0; i < odds.length; i++) {
            System.out.println(odds[i]);
        }
    }
}
