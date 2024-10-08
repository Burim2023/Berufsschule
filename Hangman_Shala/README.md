<a name="readme-top"></a>
# Hangman-Spiel-Projekt

**Author:** Burim Shala  
**LBS Eibiswald | 2aAPC**

Dies ist eine Java-basierte Hangman-Anwendung, die als Beispiel für GUI-Entwicklung und Spiel-Logik dient. Das Projekt nutzt die Java Swing-Bibliothek zur Erstellung einer grafischen Benutzeroberfläche, kombiniert mit grundlegender Java-Logik zur Implementierung des Spiels. Hier werden sowohl die UI-Elemente als auch die Spiellogik näher erklärt.

## Installation

Um das Projekt zu klonen und auszuführen, folge diesen Schritten:

1. Klone das Repository:
   ```cmd
   git clone https://github.com/Burim2023/Berufsschule/tree/main/Hangman_Shala
   ```

2. Wechsle zum Master-Branch:
   ```cmd
   git checkout origin/master
   ```

3. Öffne das Projekt in deiner bevorzugten Java-Entwicklungsumgebung (z.B. IntelliJ IDEA oder Eclipse).

4. Führe die Datei `HangmanGame.java` aus, um das Spiel zu starten.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Verwendung

### Technologien im Einsatz:

<a href="https://www.java.com" target="_blank" rel="noreferrer"> 
<img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a>

### Wichtige Funktionen:

Die Anwendung verwendet mehrere Swing-Komponenten, um das Hangman-Spiel zu realisieren:

1. **JComboBox**: Ermöglicht die Auswahl von Optionen wie der Anzahl der maximalen Fehler und dem Ein-/Ausblenden der Buchstabengeschichte. Diese Methode steuert, wie das Menü genutzt wird:
   ```java
   Menu_Cbx.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
           String selectedOption = (String) Menu_Cbx.getSelectedItem();
           if (selectedOption != null) {
               handleMenuSelection(selectedOption);
           }
       }
   });
   ```

2. **JLabel**: 
   - `wordProgressLb`: Zeigt den Fortschritt des erratenen Wortes an (z.B. `_ _ p _ e`).
   - `AttemptsLb`: Zeigt die verbleibenden Versuche an, damit der Spieler weiß, wie viele Chancen er noch hat.
   - `letterHistoryLb`: Zeigt die bisher geratenen Buchstaben an. Die Sichtbarkeit dieser Label kann über das Menü gesteuert werden.

3. **JTextField**: 
   - `letterInput`: Erlaubt dem Benutzer, einzelne Buchstaben einzugeben. Dieses Eingabefeld wird für die Benutzereingaben verwendet.

4. **JButton (Enter/Restart)**: 
   - `enterBtn`: Übernimmt die Eingabe des Buchstabens und überprüft, ob dieser im gesuchten Wort vorhanden ist. Diese Logik wird hier implementiert:
   ```java
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
                   if (gameLogic.isGameOver()) {
                       JOptionPane.showMessageDialog(null, "Spiel vorbei!");
                   }
               }
           }
       }
   });
   ```

   - `RestartBtn`: Setzt das Spiel zurück und startet es mit einem neuen zufällig ausgewählten Wort neu. Dies wird über die `resetGame()`-Methode gehandhabt.

5. **JPanel**: 
   - `imagePanel`: Dieses Panel wird verwendet, um das Bild des aktuellen Hangman-Zustands anzuzeigen. Dies ist optional und kann erweitert werden, um grafische Elemente anzuzeigen.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Funktionen im Detail

Die Anwendung ist in zwei Hauptkomponenten unterteilt:

### 1. **HangmanGameLogic.java**:

Diese Klasse enthält die gesamte Logik des Spiels. Sie speichert das zu erratende Wort, verfolgt die Eingaben des Benutzers und überwacht den Spielfortschritt. Die wichtigsten Methoden sind:

- **`resetGame()`**:
   Diese Methode wird aufgerufen, wenn das Spiel neu gestartet wird. Sie wählt ein zufälliges neues Wort aus der `wordList` und setzt alle relevanten Variablen zurück (z.B. das erratene Wort und die bisherigen Eingaben).

   ```java
   public void resetGame() {
       currentWord = wordList[new Random().nextInt(wordList.length)];
       guessedWord = new char[currentWord.length()];
       Arrays.fill(guessedWord, '_');
       guessedLetters.clear();
       attempts = 0;
   }
   ```

- **`checkGuess(String guess)`**:
   Diese Methode überprüft, ob der eingegebene Buchstabe im gesuchten Wort vorhanden ist. Wenn ja, wird der Buchstabe im `guessedWord` angezeigt, andernfalls wird die Anzahl der Fehlversuche erhöht. **Neu hinzugefügt:** Wenn der Buchstabe bereits eingegeben wurde, gibt die Methode `true` zurück, um dies anzuzeigen.

   ```java
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
   ```

- **`isGameOver()`**:
   Diese Methode überprüft, ob das Spiel vorbei ist (entweder weil der Spieler das Wort erraten hat oder weil die maximale Anzahl an Fehlversuchen erreicht wurde).

   ```java
   public boolean isGameOver() {
       return attempts >= maxAttempts || new String(guessedWord).equals(currentWord);
   }
   ```

### 2. **HangmanGame.java**:

Diese Klasse stellt die Benutzeroberfläche des Spiels bereit und verarbeitet die Benutzereingaben. Sie verbindet die grafischen Elemente mit der Logik.

- **`updateUI()`**:
   Diese Methode aktualisiert die UI nach jedem Rateversuch, indem sie das Fortschritts-Label, die verbleibenden Versuche und die Buchstabengeschichte aktualisiert.

   ```java
   private void updateUI() {
       wordProgressLb.setText(gameLogic.getWordProgress());
       AttemptsLb.setText("Versuche übrig: " + (maxAttempts - gameLogic.getAttempts()));
       letterHistoryLb.setText("Geratene Buchstaben: " + gameLogic.getHistory());
   }
   ```

- **`resetGame()`**:
   Setzt das Spiel zurück, wählt ein neues zufälliges Wort und aktualisiert die UI entsprechend.

   ```java
   private void resetGame() {
       gameLogic.resetGame();  // Wählt ein neues zufälliges Wort
       wordProgressLb.setText(gameLogic.getWordProgress());
       letterHistoryLb.setText("Geratene Buchstaben: ");
       AttemptsLb.setText("Versuche übrig: " + maxAttempts);
   }
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Screenshots


<p>Hier siehst du ein Screenshot der Benutzeroberfläche, nachdem der Spieler einige Buchstaben eingegeben hat.<br></p>
![Win-View][Hangman_Win_View.png]
<p>Hier sieht man einen Screenshot bei dem der Spieler das Wort erraten hat.<br></p>
![DoubleInput-View][Hangman_Dialog_secondTimeInput.png]
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Projektstruktur

### Warum wurde diese Struktur gewählt?

1. **Trennung von Logik und UI**: 
   Die Spiel-Logik und die Benutzeroberfläche sind in zwei separaten Klassen organisiert:
   - **`HangmanGameLogic.java`** kümmert sich um alle Logik-Operationen wie das Verfolgen der Buchstaben und des Spielfortschritts.
   - **`HangmanGame.java`** ist für die Interaktionen mit dem Benutzer und die Darstellung der UI zuständig.

2. **Flexibilität durch Menüs**: 
   Über das Menü kann der Benutzer die Anzahl der Fehlversuche einstellen und die Anzeige der Buchstabengeschichte anpassen.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Quellen

- [Java Documentation](https://docs.oracle.com/javase/8/docs/api/)
- [Swing Documentation](https://docs.oracle.com/javase/tutorial/uiswing/)

<p align="right">(<a href="#readme-top">back to top</a>)