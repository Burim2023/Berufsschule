/*
 * Project: Bruchrechner_GUI
 * Klasse: 2 aApc
 * Author: Burim Shala
 * Last change:
 * by: Shala
 * date: 01.10.2024
 * */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fractionCalculator extends JFrame {

    private fractionCalculatorLogic logic; // Logik-Instanz
    private JTextField numerator1, denominator1, numerator2, denominator2;
    private JTextArea calcResult;
    private JButton addBtn, minusBtn, multiplyBtn, divisionBtn;
    private JButton clearBtn, resetBtn, undoBtn;
    private JPanel fractionCalculatorView;
    private JButton openDocumentationBtn;

    private String lastResult = ""; // Letztes Ergebnis speichern
    private int lastNumerator1, lastDenominator1, lastNumerator2, lastDenominator2; // Zuletzt berechnete Werte

    public fractionCalculator() {
        // Initialisiere die Logik-Klasse
        logic = new fractionCalculatorLogic();

        // ActionListener für den Add-Button
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate("+");
            }
        });

        // ActionListener für den Minus-Button
        minusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate("-");
            }
        });

        // ActionListener für den Multiply-Button
        multiplyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate("*");
            }
        });

        // ActionListener für den Division-Button
        divisionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate("/");
            }
        });

        // ActionListener für Clear-Button
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // ActionListener für Reset-Button
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetCalculator();
            }
        });

        // ActionListener für Undo-Button
        undoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoLastAction();
            }
        });
    }

    // Berechnungslogik für die Brüche
    private void calculate(String operation) {
        String z1 = numerator1.getText();
        String n1 = denominator1.getText();
        String z2 = numerator2.getText();
        String n2 = denominator2.getText();

        if (logic.validateInput(z1, n1, z2, n2)) {
            int num1 = Integer.parseInt(z1);
            int den1 = Integer.parseInt(n1);
            int num2 = Integer.parseInt(z2);
            int den2 = Integer.parseInt(n2);

            String result = "";
            lastResult = calcResult.getText(); // Vorheriges Ergebnis speichern

            switch (operation) {
                case "+":
                    result = logic.addFractions(num1, den1, num2, den2);
                    break;
                case "-":
                    result = logic.subtractFractions(num1, den1, num2, den2);
                    break;
                case "*":
                    result = logic.multiplyFractions(num1, den1, num2, den2);
                    break;
                case "/":
                    result = logic.divideFractions(num1, den1, num2, den2);
                    break;
            }
            calcResult.setText(result);

            // Speichere die letzten berechneten Werte
            lastNumerator1 = num1;
            lastDenominator1 = den1;
            lastNumerator2 = num2;
            lastDenominator2 = den2;
        } else {
            JOptionPane.showMessageDialog(null, "Ungültige Eingaben oder Division durch Null!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Felder löschen
    private void clearFields() {
        numerator1.setText("");
        denominator1.setText("");
        numerator2.setText("");
        denominator2.setText("");
        calcResult.setText("");
    }

    // Rechner zurücksetzen: Letzte Berechnung wiederherstellen
    private void resetCalculator() {
        // Setze die letzten berechneten Werte in die Felder
        numerator1.setText(String.valueOf(lastNumerator1));
        denominator1.setText(String.valueOf(lastDenominator1));
        numerator2.setText(String.valueOf(lastNumerator2));
        denominator2.setText(String.valueOf(lastDenominator2));
    }

    // Letzte Aktion rückgängig machen
    private void undoLastAction() {
        calcResult.setText(lastResult);
    }

    // Hauptprogramm
    public static void main(String[] args) {
        // Hier wird das UI im Event-Dispatch-Thread ausgeführt
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Fraction Calculator");
            frame.setContentPane(new fractionCalculator().fractionCalculatorView); // Setze das JPanel in das Frame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Schließe die App beim Beenden des Fensters
            frame.pack(); // Passe das Fenster an die Größe der enthaltenen Komponenten an
            frame.setVisible(true); // Zeige das Fenster an
        });
    }
}
