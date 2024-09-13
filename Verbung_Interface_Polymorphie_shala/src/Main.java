import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Benutzer wählt ein Tier aus
        System.out.println("Wähle ein Tier (Katze, Hund, Kuh, Fisch, Schwein): ");
        String wahl = scanner.nextLine().toLowerCase();

        t_animal animal = null;

        // Erstelle das entsprechende Tier-Objekt
        switch (wahl) {
            case "katze":
                animal = new Katze("Katze", 2);
                break;
            case "hund":
                animal = new Hund("Hund", 3);
                break;
            case "kuh":
                animal = new Kuh("Kuh", 4);
                break;
            case "fisch":
                animal = new Fisch("Fisch", 1);
                break;
            case "pig":
                animal = new Schwein("Schwein", 2);
                break;
            default:
                System.out.println("Ungültige Wahl.");
                break;
        }

        // Gib den Laut des gewählten Tieres aus
        if (animal != null) {
            animal.gibLaut();
        }

        scanner.close();
    }
}