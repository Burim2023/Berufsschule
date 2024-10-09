/*
 * Project: Hangman Game
 * Klasse: 2 aApc
 * Author: Burim Shala
 * Last change:
 * by: Shala
 * date: 08.10.2024
 * */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HangmanGameLogic {
    private String[] wordList = {"apple", "banana", "orange", "grape", "mango"};
    private String currentWord;
    private char[] guessedWord;
    private StringBuilder letterHistory;
    private int attempts;
    private int maxAttempts;

    public HangmanGameLogic(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        resetGame();
    }

    public void resetGame() {
        currentWord = wordList[(int)(Math.random() * wordList.length)];
        guessedWord = new char[currentWord.length()];
        letterHistory = new StringBuilder();
        attempts = 0;
        for (int i = 0; i < guessedWord.length; i++) {
            guessedWord[i] = '_';
        }
    }

    public boolean checkGuess(String letter) {
        char guessedLetter = letter.charAt(0);
        if (letterHistory.toString().contains(Character.toString(guessedLetter))) {
            return true;  // Der Buchstabe wurde bereits geraten
        }

        letterHistory.append(guessedLetter);
        boolean correctGuess = false;
        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) == guessedLetter) {
                guessedWord[i] = guessedLetter;
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            attempts++;
        }
        return false;
    }

    public String getWordProgress() {
        return new String(guessedWord);
    }

    public int getAttempts() {
        return attempts;
    }

    public String getHistory() {
        return letterHistory.toString();
    }

    public boolean hasWon() {
        return currentWord.equals(new String(guessedWord));
    }

    public boolean isGameOver() {
        return attempts >= maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getCurrentWord() {
        return currentWord;
    }
}

