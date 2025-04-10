# Enhanced Jackpot Game

This is a simple Java program for a text-based jackpot game. It includes features like betting, spin limits, partial matches, and high score tracking.

## How to Play

1. You start with a balance of $100.
2. Place a bet for each spin (you can quit by entering `0` as your bet).
3. Spin the jackpot machine:
   - Match **3 numbers** to win a jackpot (10x your bet).
   - Match **2 numbers** to win a smaller prize (2x your bet).
4. The game ends when you run out of spins or balance.

## Features

- **Betting System**: Players can bet an amount before each spin.
- **Spin Limit**: Players have a maximum of 10 spins per game.
- **Partial Matches**: Rewards for matching 2 numbers.
- **High Score Tracking**: Tracks the highest balance achieved during the game.
- **User-Friendly Interface**: Includes symbols like 🎰, 🎉, and ✨ for a fun experience.

## How to Run

1. **Compile the program**:
   ```bash
   javac EnhancedJackpotGame.java
   ```

2. **Run the program**:
   ```bash
   java EnhancedJackpotGame
   ```

## Requirements

- Java Development Kit (JDK) installed on your system.

## Example Gameplay

```
Welcome to the Enhanced Jackpot Game!
You start with a balance of $100
Match 3 numbers to win big, or 2 numbers for a small prize!
You have 10 spins. Good luck!

Your current balance is: $100
Enter your bet amount (or 0 to quit): 10
🎰 Spinning... 🎰
You spun: 3 | 3 | 7
✨ Partial match! You win $20!
Spins remaining: 9
```

## License

This project is for learning purposes and is free to use.
