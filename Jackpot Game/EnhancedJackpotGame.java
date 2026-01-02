import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

/**
 * Casino Game Suite
 * 
 * A collection of casino style games with persistent high scores and
 * multiple difficulty levels. This started as a simple jackpot game
 * and evolved into a full gaming experience.
 * 
 * Concepts covered:
 * - Random number generation
 * - Game loops and state management
 * - File I/O for saving high scores
 * - Multiple methods for organization
 * - ArrayList for leaderboard
 * - User input validation
 */
public class EnhancedJackpotGame {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    // Player state
    static String playerName = "";
    static int balance = 100;
    static int highScore = 0;
    static int gamesWon = 0;
    static int gamesPlayed = 0;

    // Leaderboard stores top scores
    static ArrayList<String> leaderboard = new ArrayList<>();
    static final String SAVE_FILE = "casino_scores.txt";

    // Difficulty settings
    static int difficulty = 2; // 1=Easy, 2=Normal, 3=Hard

    public static void main(String[] args) {
        loadLeaderboard();

        showWelcome();
        getPlayerName();

        boolean playing = true;

        while (playing) {
            showMainMenu();
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    playSlotMachine();
                    break;
                case "2":
                    playDiceGame();
                    break;
                case "3":
                    playHighLow();
                    break;
                case "4":
                    playNumberGuess();
                    break;
                case "5":
                    showLeaderboard();
                    break;
                case "6":
                    changeDifficulty();
                    break;
                case "7":
                    showStats();
                    break;
                case "8":
                    addToBalance();
                    break;
                case "9":
                    System.out.println("\nThanks for playing, " + playerName + "!");
                    System.out.println("Final balance: $" + balance);
                    saveScore();
                    playing = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            // Check for bankruptcy
            if (balance <= 0 && playing) {
                System.out.println("\nYoure out of money!");
                System.out.println("Add more funds or exit the casino.");
            }
        }

        scanner.close();
    }

    static void showWelcome() {
        System.out.println();
        System.out.println("  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("  $                                  $");
        System.out.println("  $     WELCOME TO THE CASINO        $");
        System.out.println("  $         GAME SUITE               $");
        System.out.println("  $                                  $");
        System.out.println("  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println();
    }

    static void getPlayerName() {
        System.out.print("Enter your name: ");
        playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) {
            playerName = "Player";
        }
        System.out.println("\nWelcome, " + playerName + "! You start with $" + balance);
    }

    static void showMainMenu() {
        System.out.println("\n========== CASINO LOBBY ==========");
        System.out.println("Balance: $" + balance + " | High Score: $" + highScore);
        System.out.println("Difficulty: " + getDifficultyName());
        System.out.println("===================================");
        System.out.println();
        System.out.println("GAMES:");
        System.out.println("  1. Slot Machine (match symbols to win)");
        System.out.println("  2. Dice Game (roll higher than dealer)");
        System.out.println("  3. High Low (guess if next card is higher)");
        System.out.println("  4. Number Guess (guess the secret number)");
        System.out.println();
        System.out.println("OPTIONS:");
        System.out.println("  5. View Leaderboard");
        System.out.println("  6. Change Difficulty");
        System.out.println("  7. View Stats");
        System.out.println("  8. Add Funds");
        System.out.println("  9. Exit Casino");
        System.out.println();
    }

    /**
     * The classic slot machine game
     * Match 3 symbols for jackpot, 2 for partial win
     */
    static void playSlotMachine() {
        System.out.println("\n=== SLOT MACHINE ===");
        System.out.println("Match 3 for JACKPOT! Match 2 for small win.");

        int bet = getBet();
        if (bet == 0)
            return;

        balance -= bet;
        gamesPlayed++;

        // Spin the reels
        System.out.println("\nSpinning...");
        pause(500);

        // Generate symbols based on difficulty
        // Harder difficulty = more possible symbols = harder to match
        int symbolRange = 7 + (difficulty * 2); // Easy: 9, Normal: 11, Hard: 13
        int s1 = random.nextInt(symbolRange);
        int s2 = random.nextInt(symbolRange);
        int s3 = random.nextInt(symbolRange);

        // Convert numbers to fun symbols
        String[] symbols = { "7", "BAR", "CHERRY", "LEMON", "BELL", "STAR",
                "DIAMOND", "HEART", "CLUB", "SPADE", "CROWN",
                "COIN", "GEM" };

        System.out.println();
        System.out.println("  +-------+-------+-------+");
        System.out.printf("  | %5s | %5s | %5s |\n",
                symbols[s1], symbols[s2], symbols[s3]);
        System.out.println("  +-------+-------+-------+");

        // Check for wins
        if (s1 == s2 && s2 == s3) {
            // Jackpot! Triple match
            int multiplier = 10 - difficulty; // Easy: 9x, Normal: 8x, Hard: 7x
            int winnings = bet * multiplier;
            balance += winnings;
            gamesWon++;
            System.out.println("\n  JACKPOT! You win $" + winnings + "!");
        } else if (s1 == s2 || s2 == s3 || s1 == s3) {
            // Partial match
            int winnings = bet * 2;
            balance += winnings;
            gamesWon++;
            System.out.println("\n  Two matching! You win $" + winnings + "!");
        } else {
            System.out.println("\n  No match. You lost $" + bet);
        }

        updateHighScore();
    }

    /**
     * Dice game where you try to beat the dealer
     */
    static void playDiceGame() {
        System.out.println("\n=== DICE GAME ===");
        System.out.println("Roll higher than the dealer to win!");

        int bet = getBet();
        if (bet == 0)
            return;

        balance -= bet;
        gamesPlayed++;

        // Roll the dice
        System.out.println("\nRolling dice...");
        pause(500);

        int playerDice1 = random.nextInt(6) + 1;
        int playerDice2 = random.nextInt(6) + 1;
        int playerTotal = playerDice1 + playerDice2;

        int dealerDice1 = random.nextInt(6) + 1;
        int dealerDice2 = random.nextInt(6) + 1;
        int dealerTotal = dealerDice1 + dealerDice2;

        System.out.println();
        System.out.println("You rolled:    [" + playerDice1 + "] [" + playerDice2 + "] = " + playerTotal);
        System.out.println("Dealer rolled: [" + dealerDice1 + "] [" + dealerDice2 + "] = " + dealerTotal);

        if (playerTotal > dealerTotal) {
            int winnings = bet * 2;
            balance += winnings;
            gamesWon++;
            System.out.println("\nYou beat the dealer! Won $" + winnings);
        } else if (playerTotal == dealerTotal) {
            balance += bet; // Return the bet
            System.out.println("\nTie! Your bet is returned.");
        } else {
            System.out.println("\nDealer wins. You lost $" + bet);
        }

        updateHighScore();
    }

    /**
     * High Low card game
     * Guess if the next card is higher or lower
     */
    static void playHighLow() {
        System.out.println("\n=== HIGH LOW ===");
        System.out.println("Guess if the next card is higher or lower!");

        int bet = getBet();
        if (bet == 0)
            return;

        balance -= bet;
        gamesPlayed++;

        // Draw first card (1 to 13, like Ace to King)
        int currentCard = random.nextInt(13) + 1;

        System.out.println("\nCurrent card: " + getCardName(currentCard));
        System.out.print("Will the next card be (h)igher or (l)ower? ");
        String guess = scanner.nextLine().trim().toLowerCase();

        // Draw second card
        int nextCard = random.nextInt(13) + 1;

        pause(500);
        System.out.println("Next card: " + getCardName(nextCard));

        boolean guessedHigher = guess.startsWith("h");
        boolean isHigher = nextCard > currentCard;
        boolean isEqual = nextCard == currentCard;

        if (isEqual) {
            balance += bet; // Tie returns bet
            System.out.println("\nSame card! Bet returned.");
        } else if ((guessedHigher && isHigher) || (!guessedHigher && !isHigher)) {
            int winnings = bet * 2;
            balance += winnings;
            gamesWon++;
            System.out.println("\nCorrect! You won $" + winnings);
        } else {
            System.out.println("\nWrong! You lost $" + bet);
        }

        updateHighScore();
    }

    /**
     * Number guessing game with limited attempts
     */
    static void playNumberGuess() {
        System.out.println("\n=== NUMBER GUESS ===");

        // Range depends on difficulty
        int maxNumber = 10 * difficulty; // Easy: 10, Normal: 20, Hard: 30
        int maxAttempts = 6 - difficulty; // Easy: 5, Normal: 4, Hard: 3

        System.out.println("Guess a number between 1 and " + maxNumber);
        System.out.println("You have " + maxAttempts + " attempts.");

        int bet = getBet();
        if (bet == 0)
            return;

        balance -= bet;
        gamesPlayed++;

        int secretNumber = random.nextInt(maxNumber) + 1;
        int attempts = 0;
        boolean won = false;

        while (attempts < maxAttempts) {
            System.out.print("\nAttempt " + (attempts + 1) + "/" + maxAttempts + " - Your guess: ");
            int guess;
            try {
                guess = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
                continue;
            }

            attempts++;

            if (guess == secretNumber) {
                won = true;
                break;
            } else if (guess < secretNumber) {
                System.out.println("Higher!");
            } else {
                System.out.println("Lower!");
            }
        }

        if (won) {
            // More winnings for fewer attempts
            int multiplier = (maxAttempts - attempts + 2);
            int winnings = bet * multiplier;
            balance += winnings;
            gamesWon++;
            System.out.println("\nCorrect! The number was " + secretNumber);
            System.out.println("Won $" + winnings + " (" + multiplier + "x for " + attempts + " attempts)");
        } else {
            System.out.println("\nOut of attempts! The number was " + secretNumber);
            System.out.println("You lost $" + bet);
        }

        updateHighScore();
    }

    /**
     * Gets a valid bet from the player
     */
    static int getBet() {
        if (balance <= 0) {
            System.out.println("You dont have any money to bet!");
            return 0;
        }

        System.out.print("Enter bet amount ($1 to $" + balance + ", 0 to cancel): ");
        int bet;
        try {
            bet = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return 0;
        }

        if (bet == 0) {
            System.out.println("Cancelled.");
            return 0;
        }

        if (bet < 1 || bet > balance) {
            System.out.println("Invalid bet. Must be between $1 and $" + balance);
            return 0;
        }

        return bet;
    }

    /**
     * Updates high score if current balance is higher
     */
    static void updateHighScore() {
        if (balance > highScore) {
            highScore = balance;
            System.out.println("New personal high score: $" + highScore);
        }
        System.out.println("Balance: $" + balance);
    }

    /**
     * Converts card number to name
     */
    static String getCardName(int card) {
        switch (card) {
            case 1:
                return "Ace";
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
            default:
                return String.valueOf(card);
        }
    }

    /**
     * Gets difficulty name
     */
    static String getDifficultyName() {
        switch (difficulty) {
            case 1:
                return "Easy (better odds)";
            case 2:
                return "Normal";
            case 3:
                return "Hard (worse odds)";
            default:
                return "Normal";
        }
    }

    /**
     * Changes the difficulty setting
     */
    static void changeDifficulty() {
        System.out.println("\n=== DIFFICULTY ===");
        System.out.println("1. Easy (better odds, lower payouts)");
        System.out.println("2. Normal (balanced)");
        System.out.println("3. Hard (worse odds, but you like a challenge)");
        System.out.print("Choose: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 1 && choice <= 3) {
                difficulty = choice;
                System.out.println("Difficulty set to " + getDifficultyName());
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    /**
     * Shows player statistics
     */
    static void showStats() {
        System.out.println("\n=== YOUR STATS ===");
        System.out.println("Name: " + playerName);
        System.out.println("Current Balance: $" + balance);
        System.out.println("Highest Balance: $" + highScore);
        System.out.println("Games Played: " + gamesPlayed);
        System.out.println("Games Won: " + gamesWon);
        if (gamesPlayed > 0) {
            double winRate = (double) gamesWon / gamesPlayed * 100;
            System.out.printf("Win Rate: %.1f%%\n", winRate);
        }
    }

    /**
     * Add more funds to balance
     */
    static void addToBalance() {
        System.out.print("How much to add? $");
        try {
            int amount = Integer.parseInt(scanner.nextLine().trim());
            if (amount > 0 && amount <= 1000) {
                balance += amount;
                System.out.println("Added $" + amount + ". New balance: $" + balance);
            } else {
                System.out.println("Amount must be between $1 and $1000");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }
    }

    /**
     * Displays the leaderboard
     */
    static void showLeaderboard() {
        System.out.println("\n=== LEADERBOARD ===");

        if (leaderboard.isEmpty()) {
            System.out.println("No scores yet. Be the first!");
            return;
        }

        System.out.println("Top 10 High Scores:");
        System.out.println("-------------------");

        int rank = 1;
        for (String entry : leaderboard) {
            System.out.println(rank + ". " + entry);
            rank++;
            if (rank > 10)
                break;
        }
    }

    /**
     * Saves the current score to leaderboard
     */
    static void saveScore() {
        if (highScore > 0) {
            String entry = playerName + ": $" + highScore;

            // Add to leaderboard in sorted position
            boolean added = false;
            for (int i = 0; i < leaderboard.size(); i++) {
                int existingScore = parseScore(leaderboard.get(i));
                if (highScore > existingScore) {
                    leaderboard.add(i, entry);
                    added = true;
                    break;
                }
            }
            if (!added) {
                leaderboard.add(entry);
            }

            // Save to file
            saveLeaderboard();
            System.out.println("Score saved to leaderboard!");
        }
    }

    /**
     * Parses score from leaderboard entry
     */
    static int parseScore(String entry) {
        try {
            int dollarIndex = entry.lastIndexOf("$");
            if (dollarIndex >= 0) {
                return Integer.parseInt(entry.substring(dollarIndex + 1).trim());
            }
        } catch (NumberFormatException e) {
            // Ignore parsing errors
        }
        return 0;
    }

    /**
     * Saves leaderboard to file
     */
    static void saveLeaderboard() {
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            for (String entry : leaderboard) {
                writer.write(entry + "\n");
            }
        } catch (IOException e) {
            // Silent fail on save error
        }
    }

    /**
     * Loads leaderboard from file
     */
    static void loadLeaderboard() {
        File file = new File(SAVE_FILE);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    leaderboard.add(line);
                }
            }
        } catch (IOException e) {
            // Silent fail on load error
        }
    }

    /**
     * Simple pause for dramatic effect
     */
    static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}
