/*
 * Project: DB-Verbindung
 * Klasse: 2 aApc
 * Author: Burim Shala
 * Last change:
 * by: Shala
 * date: 16.10.2024
 * */
import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/uebungen";
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String user = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (Connection db_connection = Connector.getConnection(url, user, password)) {
            if (db_connection != null) {
                System.out.println("Verbindung zur MariaDB erfolgreich hergestellt!");
                Tables.selectAllFromPersonTable(db_connection);
            } else {
                System.out.println("Verbindung fehlgeschlagen.");
            }
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }
}
