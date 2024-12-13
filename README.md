# GuessingNumberGame

## Overview
The Guessing Number Game is a fun and interactive game where players try to guess a randomly generated 4-digit number. The game provides feedback on each guess to help the player deduce the correct number. Scores are recorded in a database, and the best player is highlighted based on a combination of time taken and the number of moves.

## Features
- **Player Name Input**: Players start the game by entering their name.
- **Real-Time Timer**: A timer starts at the beginning of each game to track the time taken.
- **Unique Secret Number**: The computer generates a random 4-digit number with no repeated digits.
- **Feedback System**: After each guess:
  - A `+` indicates a correct digit in the correct position.
  - A `-` indicates a correct digit in the wrong position.
- **Database Integration**: Player results (name, moves, and time) are saved in a database.
- **Best Player Display**: The player with the best score (based on minimal moves and time) is displayed.

## How to Play
1. Start a new game and enter your name.
2. The computer generates a secret 4-digit number (digits are unique).
3. Enter your guess (must be a 4-digit number with unique digits).
4. The computer provides feedback:
   - `+` for correct digits in the correct position.
   - `-` for correct digits in the wrong position.
5. Continue guessing until you find the correct number.
6. View your performance (time taken and moves made).

### Example
- Computer selects: `1234`
- Player guesses: `1672`
- Feedback: `+-`
  - `+` for digit `1` (correct position).
  - `-` for digit `2` (exists but wrong position).

## Scoring
The score is a combination of:
- **Number of Moves**: Fewer moves result in a better score.
- **Time Taken**: Less time results in a better score.

The formula for the best score combines these factors to determine the top player.

## Technology Stack
- **Programming Language**: Java
- **Database**: SQLite
- **Libraries**: Java SQL for database integration

## Requirements
- Java 8 or later
- SQLite (embedded with the application)

## Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/Pravinvignesh2/GuessingNumberGame.git
   ```
2. Open the project in your preferred IDE.
3. Ensure Java is installed on your system.
4. Run the `GuessingNumberGame` class to start the game.

## Contribution
Feel free to fork the repository and submit pull requests for improvements or new features.

Enjoy playing the Guessing Number Game!


