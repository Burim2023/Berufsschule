public class Sparkonto extends Konto {
    // Konstruktor zum Initialisieren eines Sparkontos
    public Sparkonto(String kontoinhaber, String bankleitzahl, String kontonummer, double kontostand) {
        super(kontoinhaber, bankleitzahl, kontonummer, kontostand);  // Aufruf des Konstruktors der Basisklasse
    }

    // Überschreiben der Abhebemethode, um nur Abhebungen bei ausreichendem Guthaben zuzulassen
    @Override
    public void abheben(double betrag) {
        if (kontostand >= betrag) {
            kontostand -= betrag;  // Betrag abziehen
            System.out.println(betrag + "€ wurden abgehoben. Neuer Kontostand: " + kontostand + "€.");
        } else {
            System.out.println("Abhebung abgelehnt. Nicht genügend Guthaben.");
        }
    }
}
