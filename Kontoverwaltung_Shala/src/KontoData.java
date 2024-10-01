/*
* Project: KontoverwaltungGUI
* Klasse: 2 aApc
* Author: Burim Shala
* Last change:
* by: Shala
* date: 24.09.2024
* */
public class KontoData {
    private String kontoArt;      // Art des Kontos (Girokonto, Sparkonto, Kreditkonto)
    private String kontonummer;   // Kontonummer
    private String kontoinhaber;  // Name des Kontoinhabers
    private double gebuehren;     // Gebühren oder Zinsen
    private double kontostand;    // Aktueller Kontostand

    // Konstruktor
    public KontoData(String kontoArt, String kontonummer, String kontoinhaber, double gebuehren, double kontostand) {
        this.kontoArt = kontoArt;
        this.kontonummer = kontonummer;
        this.kontoinhaber = kontoinhaber;
        this.gebuehren = gebuehren;
        this.kontostand = kontostand;
    }

    // Getter- und Setter-Methoden
    public String getKontoArt() {
        return kontoArt;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public String getKontoinhaber() {
        return kontoinhaber;
    }

    public double getGebuehren() {
        return gebuehren;
    }

    public double getKontostand() {
        return kontostand;
    }

    public void setKontostand(double kontostand) {
        this.kontostand = kontostand;
    }

    // Methode zur String-Darstellung des Kontos
    @Override
    public String toString() {
        return "KontoData{" +
                "Kontoart='" + kontoArt + '\'' +
                ", Kontonummer='" + kontonummer + '\'' +
                ", Kontoinhaber='" + kontoinhaber + '\'' +
                ", Gebühren/Zinsen=" + gebuehren +
                ", Kontostand=" + kontostand +
                '}';
    }
}
