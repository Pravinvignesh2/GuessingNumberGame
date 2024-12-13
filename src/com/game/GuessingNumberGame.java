package com.game;

import java.sql.*;
import java.util.*;

public class GuessingNumberGame {

    private static final String DB_URL = "jdbc:sqlite:game_results.db";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeDatabase();
        System.out.println("Welcome to the Guessing Number Game!");

        while (true) {
            System.out.print("Enter your name: ");
            String playerName = scanner.nextLine();

            String secretNumber = generateSecretNumber();
            long startTime = System.currentTimeMillis();
            int moves = playGame(secretNumber);
            long endTime = System.currentTimeMillis();

            long timeTaken = (endTime - startTime) / 1000; // Time in seconds

            saveResult(playerName, moves, timeTaken);
            displayBestPlayer();

            System.out.print("Do you want to play again? (yes/no): ");
            if (!scanner.nextLine().equalsIgnoreCase("yes")) {
                System.out.println("Thanks for playing!!");
                break;
            }
        }
    }

    private static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL); 
             Statement stmt = conn.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS results (\n"
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "name TEXT NOT NULL,\n"
                    + "moves INTEGER NOT NULL,\n"
                    + "time INTEGER NOT NULL\n"
                    + ");";
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    private static String generateSecretNumber() {
        Random random = new Random();
        Set<Integer> digits = new LinkedHashSet<>();

        while (digits.size() < 4) {
            digits.add(random.nextInt(10));
        }

        StringBuilder secretNumber = new StringBuilder();
        for (int digit : digits) {
            secretNumber.append(digit);
        }
        System.out.println("Debug: Secret Number is " + secretNumber); // Remove this in production.

        return secretNumber.toString();
    }

    private static int playGame(String secretNumber) {
        System.out.println("The game has started! Guess the 4-digit number.");
        int moves = 0;

        while (true) {
            System.out.print("Enter your guess: ");
            String guess = scanner.nextLine();

            if (!isValidGuess(guess)) {
                System.out.println("Invalid guess. Please enter a 4-digit number with unique digits.");
                continue;
            }

            moves++;
            String feedback = provideFeedback(secretNumber, guess);
            System.out.println("Feedback: " + feedback);

            if (feedback.equals("++++")) {
                System.out.println("Congratulations! You've guessed the number in " + moves + " moves.");
                break;
            }
        }

        return moves;
    }

    private static boolean isValidGuess(String guess) {
        if (guess.length() != 4 || !guess.chars().allMatch(Character::isDigit)) {
            return false;
        }

        Set<Character> uniqueDigits = new HashSet<>();
        for (char c : guess.toCharArray()) {
            uniqueDigits.add(c);
        }

        return uniqueDigits.size() == 4;
    }

    private static String provideFeedback(String secret, String guess) {
        StringBuilder feedback = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == secret.charAt(i)) {
                feedback.append("+");
            } else if (secret.contains(String.valueOf(guess.charAt(i)))) {
                feedback.append("-");
            }
        }

        return feedback.toString();
    }

    private static void saveResult(String name, int moves, long time) {
        try (Connection conn = DriverManager.getConnection(DB_URL); 
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO results (name, moves, time) VALUES (?, ?, ?)");) {
            pstmt.setString(1, name);
            pstmt.setInt(2, moves);
            pstmt.setLong(3, time);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving result: " + e.getMessage());
        }
    }

    private static void displayBestPlayer() {
        String bestPlayerSQL = "SELECT name, moves, time FROM results ORDER BY (time + moves) ASC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DB_URL); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(bestPlayerSQL)) {

            if (rs.next()) {
                System.out.println("Best Player: " + rs.getString("name") + ", Moves: " + rs.getInt("moves") + ", Time: " + rs.getInt("time") + " seconds");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving best player: " + e.getMessage());
        }
    }
}
