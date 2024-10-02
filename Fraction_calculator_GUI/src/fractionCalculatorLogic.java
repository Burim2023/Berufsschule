/*
 * Project: Bruchrechner_GUI
 * Klasse: 2 aApc
 * Author: Burim Shala
 * Last change:
 * by: Shala
 * date: 01.10.2024
 * */
public class fractionCalculatorLogic {

    // Funktion zur Addition von Brüchen
    public String addFractions(int z1, int n1, int z2, int n2) {
        int resultNumerator = z1 * n2 + z2 * n1;
        int resultDenominator = n1 * n2;
        return simplifyFraction(resultNumerator, resultDenominator);
    }

    // Funktion zur Subtraktion von Brüchen
    public String subtractFractions(int z1, int n1, int z2, int n2) {
        int resultNumerator = z1 * n2 - z2 * n1;
        int resultDenominator = n1 * n2;
        return simplifyFraction(resultNumerator, resultDenominator);
    }

    // Funktion zur Multiplikation von Brüchen
    public String multiplyFractions(int z1, int n1, int z2, int n2) {
        int resultNumerator = z1 * z2;
        int resultDenominator = n1 * n2;
        return simplifyFraction(resultNumerator, resultDenominator);
    }

    // Funktion zur Division von Brüchen
    public String divideFractions(int z1, int n1, int z2, int n2) {
        if (z2 == 0) {
            return "Division durch Null nicht möglich!";
        }
        int resultNumerator = z1 * n2;
        int resultDenominator = n1 * z2;
        return simplifyFraction(resultNumerator, resultDenominator);
    }

    // Funktion zur Vereinfachung eines Bruchs
    private String simplifyFraction(int numerator, int denominator) {
        int ggt = ggt(numerator, denominator);
        numerator /= ggt;
        denominator /= ggt;

        if (denominator == 1) {
            return String.valueOf(numerator); // Ganzzahlige Darstellung
        }
        return numerator + "/" + denominator; // Bruchdarstellung
    }

    // Berechnung des größten gemeinsamen Teilers (GGT)
    private int ggt(int a, int b) {
        if (b == 0) {
            return Math.abs(a);
        }
        return ggt(b, a % b);
    }

    // Funktion zur Validierung der Benutzereingaben
    public boolean validateInput(String z1, String n1, String z2, String n2) {
        try {
            int numerator1 = Integer.parseInt(z1);
            int denominator1 = Integer.parseInt(n1);
            int numerator2 = Integer.parseInt(z2);
            int denominator2 = Integer.parseInt(n2);

            if (denominator1 == 0 || denominator2 == 0) {
                throw new ArithmeticException("Nenner darf nicht 0 sein");
            }
            return true;
        } catch (NumberFormatException e) {
            return false; // Ungültige Zahl
        } catch (ArithmeticException e) {
            return false; // Division durch 0
        }
    }
}


