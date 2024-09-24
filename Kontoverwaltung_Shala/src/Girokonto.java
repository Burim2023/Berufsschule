public class Girokonto extends Konto {
    private double ueberziehungsrahmen;  // Überziehungsrahmen des Girokontos

    // Konstruktor zum Initialisieren eines Girokontos
    public Girokonto(String kontoinhaber, String bankleitzahl, String kontonummer, double kontostand, double ueberziehungsrahmen) {
        super(kontoinhaber, bankleitzahl, kontonummer, kontostand);  // Aufruf des Konstruktors der Basisklasse
        this.ueberziehungsrahmen = ueberziehungsrahmen;  // Setzen des Überziehungsrahmens
    }

    // Überschreiben der Abhebemethode, um den Überziehungsrahmen zu berücksichtigen
    @Override
    public void abheben(double betrag) {
        if (kontostand + ueberziehungsrahmen >= betrag) {
            kontostand -= betrag;  // Betrag abziehen, Überziehungsrahmen berücksichtigen
            System.out.println(betrag + "€ wurden abgehoben. Neuer Kontostand: " + kontostand + "€.");
        } else {
            System.out.println("Abhebung abgelehnt. Überziehungsrahmen überschritten.");
        }
    }
}
