import java.util.Random;
import java.util.Scanner;

public class EnhancedJackpotGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int balance = 100; // Starting balance
        int highScore = 0; // Track the highest balance
        int maxSpins = 10; // Maximum number of spins
        int spins = 0; // Current spin count

        System.out.println("Welcome to the Enhanced Jackpot Game!");
        System.out.println("You start with a balance of $" + balance);
        System.out.println("Match 3 numbers to win big, or 2 numbers for a small prize!");
        System.out.println("You have " + maxSpins + " spins. Good luck!");

        while (spins < maxSpins && balance > 0) {
            System.out.println("\nYour current balance is: $" + balance);
            System.out.println("Enter your bet amount (or 0 to quit): ");
            int bet = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (bet == 0) {
                System.out.println("You chose to quit the game. Thanks for playing!");
                break;
            }

            if (bet > balance || bet <= 0) {
                System.out.println("Invalid bet amount. Try again.");
                continue;
            }

            // Deduct bet from balance
            balance -= bet;
            spins++;

            System.out.println("🎰 Spinning... 🎰");
            int num1 = random.nextInt(9) + 1;
            int num2 = random.nextInt(9) + 1;
            int num3 = random.nextInt(9) + 1;

            // Display the spin results
            System.out.println("You spun: " + num1 + " | " + num2 + " | " + num3);

            // Check winning conditions
            if (num1 == num2 && num2 == num3) {
                int winnings = bet * 10; // Jackpot multiplier
                balance += winnings;
                System.out.println("🎉 Jackpot! You win $" + winnings + "!");
            } else if (num1 == num2 || num2 == num3 || num1 == num3) {
                int winnings = bet * 2; // Partial match multiplier
                balance += winnings;
                System.out.println("✨ Partial match! You win $" + winnings + "!");
            } else {
                System.out.println("No match. You lost $" + bet + ".");
            }

            // Update high score
            if (balance > highScore) {
                highScore = balance;
                System.out.println("🎖 New high score: $" + highScore);
            }

            System.out.println("Spins remaining: " + (maxSpins - spins));
        }

        if (spins == maxSpins) {
            System.out.println("\nGame over! You've used all your spins.");
        }

        System.out.println("Your final balance is: $" + balance);
        System.out.println("Highest balance achieved: $" + highScore);
        System.out.println("Thanks for playing the Enhanced Jackpot Game!");
        scanner.close();
    }
}
