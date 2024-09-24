public class Kreditkonto extends Konto {
    private double kreditlimit;  // Kreditlimit des Kreditkontos

    // Konstruktor zum Initialisieren eines Kreditkontos
    public Kreditkonto(String kontoinhaber, String bankleitzahl, String kontonummer, double kontostand, double kreditlimit) {
        super(kontoinhaber, bankleitzahl, kontonummer, kontostand);  // Aufruf des Konstruktors der Basisklasse
        this.kreditlimit = kreditlimit;  // Setzen des Kreditlimits
    }

    // Überschreiben der Abhebemethode, um das Kreditlimit zu berücksichtigen
    @Override
    public void abheben(double betrag) {
        if (kontostand + kreditlimit >= betrag) {
            kontostand -= betrag;  // Betrag abziehen, Kreditlimit berücksichtigen
            System.out.println(betrag + "€ wurden abgehoben. Neuer Kontostand: " + kontostand + "€.");
        } else {
            System.out.println("Abhebung abgelehnt. Kreditlimit überschritten.");
        }
    }

    // Überschreiben der Einzahlmethode, um sicherzustellen, dass der Kontostand nicht über 0 steigt
    @Override
    public void einzahlen(double betrag) {
        if (kontostand + betrag > 0) {
            double maximalEinzahlbar = -kontostand;  // Betrag, der maximal eingezahlt werden darf
            kontostand = 0;  // Setzt den Kontostand auf 0
            System.out.println("Maximal einzahlbarer Betrag: " + maximalEinzahlbar + "€. Kontostand wurde auf 0 gesetzt.");
        } else {
            kontostand += betrag;  // Betrag zum Kontostand hinzufügen
            System.out.println(betrag + "€ wurden eingezahlt. Neuer Kontostand: " + kontostand + "€.");
        }
    }
}


