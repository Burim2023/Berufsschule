/*
 * Project: Hangman Game
 * Klasse: 2 aApc
 * Author: Burim Shala
 * Last change:
 * by: Shala
 * date: 08.10.2024
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.util.Objects;

public class HangmanGame {
    private JLabel wordProgressLb;
    private JPanel HangmanGameView;
    private JComboBox<String> Menu_Cbx;
    private JButton enterBtn;
    private JButton RestartBtn;
    private JTextField letterInput;
    private JLabel AttemptsLb;
    private JLabel letterHistoryLb;
    private JLabel imageLabel;
    private boolean showLetterHistory = true;
    private int maxAttempts = 9;
    private HangmanGameLogic gameLogic;

    public HangmanGame() {
        // Initialisiere die Spiel-Logik mit einem zufälligen Wort
        gameLogic = new HangmanGameLogic(maxAttempts);

        // Setup der JComboBox (Dropdown-Menü) für Einstellungen
        Menu_Cbx.addItem("Buchstaben History anzeigen");
        Menu_Cbx.addItem("Buchstaben History ausblenden");
        Menu_Cbx.addItem("Maximale Fehler: 9");
        Menu_Cbx.addItem("Maximale Fehler: 7");
        Menu_Cbx.addItem("Maximale Fehler: 5");
        Menu_Cbx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) Menu_Cbx.getSelectedItem();
                if (selectedOption != null) {
                    handleMenuSelection(selectedOption);
                }
            }
        });

        // Setup des Labels für den Wortfortschritt
        wordProgressLb.setText(gameLogic.getWordProgress()); // Zeigt das zu erratende Wort an

        // Setup des Labels für die verbleibenden Versuche
        AttemptsLb.setText("Versuche übrig: " + maxAttempts);

        // Setup des Labels für die Buchstabengeschichte (bereits geratene Buchstaben)
        letterHistoryLb.setText("Geratene Buchstaben: ");
        letterHistoryLb.setVisible(showLetterHistory);

        // Setup des Buttons zum Übernehmen des geratenen Buchstabens
        enterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String letter = letterInput.getText();
                if (letter.length() == 1) {
                    boolean alreadyGuessed = gameLogic.checkGuess(letter);
                    if (alreadyGuessed) {
                        // Zeige eine MessageBox an, wenn der Buchstabe bereits eingegeben wurde
                        JOptionPane.showMessageDialog(null, "Der Buchstabe '" + letter + "' wurde bereits eingegeben!", "Achtung", JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Aktualisiere das Spiel und die UI, wenn der Buchstabe neu ist
                        updateUI();

                        // Überprüfen, ob der Benutzer gewonnen hat
                        if (gameLogic.hasWon()) {
                            JOptionPane.showMessageDialog(null, "Glückwunsch! Du hast das Wort erraten: " + gameLogic.getCurrentWord(), "Gewonnen!", JOptionPane.INFORMATION_MESSAGE);
                            resetGame();  // Spiel zurücksetzen
                        }
                        // Überprüfen, ob der Benutzer verloren hat
                        else if (gameLogic.isGameOver()) {
                            JOptionPane.showMessageDialog(null, "Game Over! Das Wort war: " + gameLogic.getCurrentWord(), "Verloren!", JOptionPane.ERROR_MESSAGE);
                            resetGame();  // Spiel zurücksetzen
                        }
                    }
                    updateImage(gameLogic.getAttempts());  // Aktualisiere das Bild nach jedem Rateversuch
                }
            }
        });

        // Setup des Neustart-Buttons
        RestartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
    }

    // Methode, um die Dropdown-Menü-Auswahl zu verarbeiten
    private void handleMenuSelection(String selectedOption) {
        switch (selectedOption) {
            case "Buchstaben History anzeigen":
                showLetterHistory = true;
                letterHistoryLb.setVisible(true);
                break;
            case "Buchstaben History ausblenden":
                showLetterHistory = false;
                letterHistoryLb.setVisible(false);
                break;
            case "Maximale Fehler: 9":
                maxAttempts = 9;
                gameLogic.setMaxAttempts(maxAttempts);
                AttemptsLb.setText("Versuche übrig: " + maxAttempts);
                break;
            case "Maximale Fehler: 7":
                maxAttempts = 7;
                gameLogic.setMaxAttempts(maxAttempts);
                AttemptsLb.setText("Versuche übrig: " + maxAttempts);
                break;
            case "Maximale Fehler: 5":
                maxAttempts = 5;
                gameLogic.setMaxAttempts(maxAttempts);
                AttemptsLb.setText("Versuche übrig: " + maxAttempts);
                break;
        }
    }

    // Methode zum Zurücksetzen des Spiels und Auswahl eines neuen zufälligen Wortes
    private void resetGame() {
        gameLogic.resetGame();  // Wählt ein neues zufälliges Wort
        wordProgressLb.setText(gameLogic.getWordProgress());
        letterHistoryLb.setText("Geratene Buchstaben: ");
        AttemptsLb.setText("Versuche übrig: " + maxAttempts);
        updateImage(0);  // Setze das Bild auf den Anfang zurück (leerer Galgen)
    }

    // Methode, um das Bild zu aktualisieren
    public void updateImage(int failedAttempts) {
        // Total number of images (0 to 9, inclusive)
        int maxImages = 9;

        // Ensure that failedAttempts is within bounds
        if (failedAttempts < 0 || failedAttempts > maxImages) {
            System.out.println("Invalid number of failed attempts: " + failedAttempts);
            return;
        }

        // Construct the image path based on failedAttempts
        String imagePath = "images/hangman_" + failedAttempts + ".png";

        // Load the image
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));

        // Check if the image was loaded correctly
        if (icon.getImageLoadStatus() == java.awt.MediaTracker.ERRORED) {
            System.out.println("Error: Image could not be loaded: " + imagePath);
            return;
        }

        // Get the dimensions of the label and handle cases where the label isn't rendered yet
        int width = imageLabel.getWidth();
        int height = imageLabel.getHeight();

        if (width == 0 || height == 0) {
            // If the label hasn't been displayed yet, set a default size for the image
            width = 300;  // Set your desired default width
            height = 300; // Set your desired default height
        }

        // Scale the image to fit within the imageLabel dimensions
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));  // Set the scaled image to the label

        // Refresh the label to ensure the image is displayed
        imageLabel.revalidate();
        imageLabel.repaint();
    }


    // Methode zum Aktualisieren der UI nach jedem Rateversuch
    private void updateUI() {
        wordProgressLb.setText(gameLogic.getWordProgress());
        AttemptsLb.setText("Versuche übrig: " + (maxAttempts - gameLogic.getAttempts()));
        letterHistoryLb.setText("Geratene Buchstaben: " + gameLogic.getHistory());
        updateImage(gameLogic.getAttempts());  // Aktualisiere das Bild basierend auf den Fehlversuchen
    }

    // Startpunkt des Programms
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hangman Game");
        frame.setContentPane(new HangmanGame().HangmanGameView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000,800);
        frame.setVisible(true);
    }
}
