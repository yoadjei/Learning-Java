# Casino Game Suite

A collection of casino style games with persistent high scores, multiple difficulty levels, and a leaderboard.

## What You'll Learn

This project covers:

- **Random Number Generation** for game mechanics
- **Game Loops** and state management
- **File I/O** for persistent leaderboards
- **Methods** for code organization
- **ArrayList** for dynamic data
- **Thread.sleep()** for timing effects
- **Switch Statements** for menu handling

## How to Run

```bash
javac EnhancedJackpotGame.java
java EnhancedJackpotGame
```

## Games Included

### 1. Slot Machine
The classic 3 reel slot machine!
- Match all 3 symbols for JACKPOT (8x to 10x depending on difficulty)
- Match 2 symbols for a small win (2x)
- More symbols on harder difficulty = less chance to match

### 2. Dice Game
Roll against the dealer:
- Both roll 2 dice
- Higher total wins
- Ties return your bet

### 3. High Low
Card guessing game:
- See one card
- Guess if the next is higher or lower
- Correct guess doubles your bet

### 4. Number Guess
Test your intuition:
- Secret number picked in a range
- Limited guesses based on difficulty
- Fewer guesses = bigger multiplier

## Difficulty Levels

| Setting | Easy | Normal | Hard |
|---------|------|--------|------|
| Slot symbols | 9 | 11 | 13 |
| Number range | 1 to 10 | 1 to 20 | 1 to 30 |
| Guess attempts | 5 | 4 | 3 |
| Jackpot multiplier | 9x | 8x | 7x |

## Features

- **Persistent Leaderboard** saved to file
- **Personal Stats** tracking wins and games played
- **Balance Management** add funds anytime
- **Difficulty Settings** customize your experience

## Sample Session

```
  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
  $                                  $
  $     WELCOME TO THE CASINO        $
  $         GAME SUITE               $
  $                                  $
  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

Enter your name: John

=== SLOT MACHINE ===
Enter bet amount: 20

Spinning...

  +-------+-------+-------+
  |   BAR |   BAR | LEMON |
  +-------+-------+-------+

  Two matching! You win $40!
Balance: $120
```

## File Format

Leaderboard is saved in a simple format:
```
John: $350
Jane: $280
Bob: $150
```

## Code Structure

The game is organized into methods:
- `playSlotMachine()` for slots
- `playDiceGame()` for dice
- `playHighLow()` for cards
- `playNumberGuess()` for guessing
- `getBet()` handles betting validation
- `saveLeaderboard()` / `loadLeaderboard()` for persistence

## Try These Modifications

1. Add more games (blackjack, roulette, poker)
2. Implement achievements system
3. Add daily bonus feature
4. Create tournaments between saved players
5. Add sound effects using System.out.print("\007")
6. Implement a loan system when broke
7. Add animated ASCII art for wins
