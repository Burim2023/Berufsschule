/*
* Project: KontoverwaltungGUI
* Klasse: 2 aApc
* Author: Burim Shala
* Last change:
* by: Shala
* date: 24.09.2024
* */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class onlineBanking {
    // GUI-Komponenten
    private JComboBox<String> bankingTypCB;
    private JTextField accNumberTxt;
    private JTextField userName;
    private JTextField costTxt;
    private JTextField balanceTxt;
    private JButton abhebenButton;
    private JButton closeButton;
    private JButton einzahlenButton;
    private JButton überweisenButton;
    private JTextArea transactionHistory;
    private JButton saveBtn;
    private JComboBox<String> accCB;  // ComboBox zur Auswahl des Zielkontos für Überweisungen
    private JPanel bankingView;

    // Liste zur Speicherung der Kontodaten
    private List<KontoData> accountListe = new ArrayList<>();

    // Variable für den aktuellen Kontostand
    private double aktuellerKontostand = 0.0;
    private KontoData aktuellesKonto = null; // Speichert das aktuell verwendete Konto

    // Konstruktor zur Initialisierung der GUI
    public onlineBanking() {
        // Kontoarten in die ComboBox hinzufügen
        bankingTypCB.addItem("Girokonto");
        bankingTypCB.addItem("Sparkonto");
        bankingTypCB.addItem("Kreditkonto");

        // Transaktionshistorie nicht editierbar machen
        transactionHistory.setEditable(false);

        // ActionListener für den Einzahlen-Button
        einzahlenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                einzahlenAction();
            }
        });

        // ActionListener für den Abheben-Button
        abhebenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abhebenAction();
            }
        });

        // ActionListener für den Close-Button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAction();
            }
        });

        // ActionListener für den Speichern-Button (saveBtn)
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAccountData();
            }
        });

        // ActionListener für den Überweisen-Button (überweisenButton)
        überweisenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                überweisenAction();
            }
        });
    }
    private void closeAction() {
        int confirm = JOptionPane.showConfirmDialog(null, "Möchten Sie das Konto wirklich schließen?", "Konto schließen", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            aktuellerKontostand = 0.0;
            balanceTxt.setText("0.0");
            transactionHistory.append("Konto wurde geschlossen. Neuer Kontostand: 0.0€\n");
        }
    }
    // Methode zum Speichern der Kontodaten
    private void saveAccountData() {
        // Werte aus den Textfeldern abrufen
        String kontoArt = bankingTypCB.getSelectedItem().toString();
        String kontonummer = accNumberTxt.getText();
        String kontoinhaber = userName.getText();
        String gebuehren = costTxt.getText();
        String kontostandStr = balanceTxt.getText();

        if (kontonummer.isEmpty() || kontoinhaber.isEmpty() || gebuehren.isEmpty() || kontostandStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Felder aus.");
            return;
        }

        try {
            // Konvertiere Gebühren/Zinsen und den ursprünglichen Kontostand in double-Werte
            double gebuehrenValue = Double.parseDouble(gebuehren);
            double initialerKontostand = Double.parseDouble(kontostandStr);

            // Neues Konto-Objekt erstellen
            KontoData account = new KontoData(kontoArt, kontonummer, kontoinhaber, gebuehrenValue, initialerKontostand);

            // Konto zur Liste hinzufügen
            accountListe.add(account);

            // ComboBox aktualisieren, um das neue Konto auszuwählen (accCB)
            accCB.addItem(account.getKontonummer() + " - " + account.getKontoinhaber());

            // Setze das aktuelle Konto auf das neue Konto
            aktuellesKonto = account;

            // Bestätigung und Kontoauszug aktualisieren
            transactionHistory.append("Konto gespeichert: " + account.toString() + "\n");
            showAccountStatement(account); // Kontoauszug anzeigen
            JOptionPane.showMessageDialog(null, "Kontodaten erfolgreich gespeichert!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ungültige Eingaben. Stellen Sie sicher, dass Gebühren und Kontostand numerische Werte sind.");
        }
    }

    // Methode für das Einzahlen auf das Konto
    private void einzahlenAction() {
        String betragStr = JOptionPane.showInputDialog(null, "Betrag zum Einzahlen:");
        if (betragStr != null) {
            try {
                double betrag = Double.parseDouble(betragStr);  // Betrag zum Einzahlen
                String kontoArt = aktuellesKonto.getKontoArt();  // Prüfe den Kontotyp

                // Hole den aktuellen Kontostand des Kontos
                double aktuellerKontostand = aktuellesKonto.getKontostand();

                if (kontoArt.equals("Kreditkonto")) {
                    // Kreditkonto: Der Kontostand darf nicht über 0 gehen
                    if (aktuellerKontostand + betrag > 0) {
                        double maximalEinzahlbar = -aktuellerKontostand;
                        aktuellerKontostand = 0;  // Setze den Kontostand auf 0
                        aktuellesKonto.setKontostand(aktuellerKontostand);
                        balanceTxt.setText(String.valueOf(aktuellerKontostand));
                        transactionHistory.append("Maximal einzahlbarer Betrag: " + maximalEinzahlbar + "€. Kontostand wurde auf 0 gesetzt.\n");
                    } else {
                        aktuellerKontostand += betrag;  // Füge den Einzahlungsbetrag zum Kontostand hinzu
                        aktuellesKonto.setKontostand(aktuellerKontostand);  // Aktualisiere den Kontostand im Konto
                        balanceTxt.setText(String.valueOf(aktuellerKontostand));  // Aktualisiere die Anzeige des Kontostands
                        transactionHistory.append("Eingezahlt: " + betrag + "€. Neuer Kontostand: " + aktuellerKontostand + "€\n");
                    }
                } else {
                    // Einzahlen auf Sparkonto oder Girokonto
                    aktuellerKontostand += betrag;  // Füge den Einzahlungsbetrag zum Kontostand hinzu
                    aktuellesKonto.setKontostand(aktuellerKontostand);  // Aktualisiere den Kontostand im Konto
                    balanceTxt.setText(String.valueOf(aktuellerKontostand));  // Aktualisiere die Anzeige des Kontostands
                    transactionHistory.append("Eingezahlt: " + betrag + "€. Neuer Kontostand: " + aktuellerKontostand + "€\n");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ungültiger Betrag!");
            }
        }
    }

    // Methode für das Abheben
    private void abhebenAction() {
        String betragStr = JOptionPane.showInputDialog(null, "Betrag zum Abheben:");
        if (betragStr != null) {
            try {
                double betrag = Double.parseDouble(betragStr);
                String kontoArt = aktuellesKonto.getKontoArt();  // Prüfe den Kontotyp

                // Hole den aktuellen Kontostand des Kontos
                double aktuellerKontostand = aktuellesKonto.getKontostand();

                if (kontoArt.equals("Sparkonto")) {
                    // Sparkonto: Der Kontostand darf nicht negativ werden
                    if (aktuellerKontostand - betrag < 0) {
                        JOptionPane.showMessageDialog(null, "Nicht genügend Guthaben! Das Sparkonto darf nicht ins Minus gehen.");
                    } else {
                        // Ziehe den abgehobenen Betrag vom aktuellen Kontostand ab
                        aktuellerKontostand -= betrag;
                        aktuellesKonto.setKontostand(aktuellerKontostand);
                        balanceTxt.setText(String.valueOf(aktuellerKontostand));
                        // Transaktionshistorie aktualisieren
                        transactionHistory.append("Abgehoben: " + betrag + "€. Neuer Kontostand: " + aktuellerKontostand + "€\n");
                    }
                } else {
                    // Girokonto und Kreditkonto: Erlaubt, solange genug Geld vorhanden ist
                    if (betrag > aktuellerKontostand) {
                        JOptionPane.showMessageDialog(null, "Nicht genügend Guthaben!");
                    } else {
                        // Ziehe den abgehobenen Betrag vom aktuellen Kontostand ab
                        aktuellerKontostand -= betrag;
                        aktuellesKonto.setKontostand(aktuellerKontostand);
                        balanceTxt.setText(String.valueOf(aktuellerKontostand));
                        // Transaktionshistorie aktualisieren
                        transactionHistory.append("Abgehoben: " + betrag + "€. Neuer Kontostand: " + aktuellerKontostand + "€\n");
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ungültiger Betrag!");
            }
        }
    }


    // Methode für das Überweisen zwischen zwei Konten
    private void überweisenAction() {
        // Wähle das Zielkonto aus der ComboBox (accCB)
        String zielKontoAuswahl = (String) accCB.getSelectedItem();
        if (zielKontoAuswahl == null) {
            JOptionPane.showMessageDialog(null, "Kein Zielkonto ausgewählt.");
            return;
        }

        // Betrag für die Überweisung eingeben
        String betragStr = JOptionPane.showInputDialog(null, "Betrag zur Überweisung:");
        if (betragStr != null) {
            try {
                double betrag = Double.parseDouble(betragStr);

                // Wähle das Zielkonto anhand der Kontonummer aus
                KontoData zielKonto = findeKonto(zielKontoAuswahl.split(" - ")[0]);
                if (zielKonto == null) {
                    JOptionPane.showMessageDialog(null, "Zielkonto nicht gefunden.");
                    return;
                }

                // Hole den aktuellen Kontostand des Kontos, das überweisen möchte
                double aktuellerKontostandDesAbsenders = aktuellesKonto.getKontostand();

                // Prüfen, ob genug Geld auf dem aktuellen Konto ist
                if (betrag > aktuellerKontostandDesAbsenders) {
                    JOptionPane.showMessageDialog(null, "Nicht genügend Guthaben für die Überweisung!");
                } else {
                    // Überweisung durchführen
                    aktuellerKontostandDesAbsenders -= betrag;  // Betrag vom aktuellen Konto abziehen
                    aktuellesKonto.setKontostand(aktuellerKontostandDesAbsenders);  // Aktualisiere den Kontostand des Absenders

                    zielKonto.setKontostand(zielKonto.getKontostand() + betrag);  // Betrag zum Zielkonto hinzufügen

                    // Aktualisiere die Anzeige des Kontostands des Absenders
                    balanceTxt.setText(String.valueOf(aktuellerKontostandDesAbsenders));

                    // Transaktionshistorie aktualisieren
                    transactionHistory.append("Überwiesen: " + betrag + "€ an " + zielKonto.getKontoinhaber() + "\n");
                    transactionHistory.append("Neuer Kontostand des Absenders: " + aktuellerKontostandDesAbsenders + "€\n");
                    transactionHistory.append("Neuer Kontostand des Empfängers: " + zielKonto.getKontostand() + "€\n");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ungültiger Betrag!");
            }
        }
    }


    // Hilfsmethode zum Finden eines Kontos anhand der Kontonummer
    private KontoData findeKonto(String kontonummer) {
        for (KontoData account : accountListe) {
            if (account.getKontonummer().equals(kontonummer)) {
                return account;
            }
        }
        return null;
    }

    // Methode zur Anzeige des Kontoauszugs
    private void showAccountStatement(KontoData account) {
        transactionHistory.append("\n--- Kontoauszug ---\n");
        transactionHistory.append("Kontonummer: " + account.getKontonummer() + "\n");
        transactionHistory.append("Kontoinhaber: " + account.getKontoinhaber() + "\n");
        transactionHistory.append("Kontoart: " + account.getKontoArt() + "\n");
        transactionHistory.append("Gebühren/Zinsen: " + account.getGebuehren() + "€\n");
        transactionHistory.append("Aktueller Kontostand: " + account.getKontostand() + "€\n");
        transactionHistory.append("-------------------------\n");
    }

    // Main-Methode zum Starten der GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Online Banking");
                frame.setContentPane(new onlineBanking().bankingView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setSize(1000,600);
                frame.setVisible(true);
            }
        });
    }

}

