/*
* Project: KontoverwaltungGUI
* Klasse: 2 aApc
* Author: Burim Shala
* Last change:
* by: Shala
* date: 24.09.2024
* */
public class Konto {
    protected String kontoinhaber;  // Name des Kontoinhabers
    protected String bankleitzahl;  // Bankleitzahl des Kontos
    protected String kontonummer;   // Kontonummer
    protected double kontostand;    // Aktueller Kontostand

    // Konstruktor zum Initialisieren des Kontos
    public Konto(String kontoinhaber, String bankleitzahl, String kontonummer, double kontostand) {
        this.kontoinhaber = kontoinhaber;
        this.bankleitzahl = bankleitzahl;
        this.kontonummer = kontonummer;
        this.kontostand = kontostand;
    }

    // Methode zum Einzahlen eines Betrags auf das com.banking.exercise.Konto
    public void einzahlen(double betrag) {
        kontostand += betrag;  // Betrag zum Kontostand hinzufügen
        System.out.println(betrag + "€ wurden eingezahlt. Neuer Kontostand: " + kontostand + "€.");
    }

    // Methode zum Abheben eines Betrags vom com.banking.exercise.Konto
    public void abheben(double betrag) {
        if (kontostand >= betrag) {
            kontostand -= betrag;  // Betrag vom Kontostand abziehen
            System.out.println(betrag + "€ wurden abgehoben. Neuer Kontostand: " + kontostand + "€.");
        } else {
            System.out.println("Abhebung abgelehnt. Nicht genügend Guthaben.");
        }
    }

    // Methode zum Anzeigen des Kontoauszugs
    public void kontoauszug() {
        System.out.println("\nKontoauszug für: " + kontoinhaber);
        System.out.println("Kontonummer: " + kontonummer);
        System.out.println("Kontostand: " + kontostand + "€.\n");
    }
}


