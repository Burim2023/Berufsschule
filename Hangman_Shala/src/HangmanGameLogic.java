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
    private List<Character> guessedLetters = new ArrayList<>();
    private int attempts;
    private int maxAttempts;

    public HangmanGameLogic(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        resetGame();
    }

    public void resetGame() {
        // Wähle ein zufälliges Wort aus der Liste
        currentWord = wordList[new Random().nextInt(wordList.length)];
        guessedWord = new char[currentWord.length()];
        Arrays.fill(guessedWord, '_');
        guessedLetters.clear();
        attempts = 0;
    }

    public boolean checkGuess(String guess) {
        if (guess.length() != 1) return false;
        char letter = guess.charAt(0);

        // Wenn der Buchstabe schon geraten wurde
        if (guessedLetters.contains(letter)) {
            return true;  // Buchstabe wurde bereits eingegeben
        }

        guessedLetters.add(letter);
        boolean correctGuess = false;

        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) == letter) {
                guessedWord[i] = letter;
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            attempts++;
        }

        return false;  // Buchstabe wurde noch nicht geraten
    }

    public String getWordProgress() {
        return new String(guessedWord);
    }

    public int getAttempts() {
        return attempts;
    }

    public String getHistory() {
        return guessedLetters.toString();
    }

    public boolean isGameOver() {
        return attempts >= maxAttempts || new String(guessedWord).equals(currentWord);
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
}

