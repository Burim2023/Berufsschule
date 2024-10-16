/*
 * Project: DB-Verbindung
 * Klasse: 2 aApc
 * Author: Burim Shala
 * Last change:
 * by: Shala
 * date: 16.10.2024
 * */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Verbindungsinformationen
        String url = "jdbc:mysql://127.0.0.1:3306/uebungen"; // Host, Port und Datenbankname anpassen

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String user = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Verbindung herstellen
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Verbindung zur MariaDB erfolgreich hergestellt!");
            } else {
                System.out.println("Verbindung zur MariaDB fehlgeschlagen.");
            }

            // Alle Einträge aus der Tabelle t_person auswählen
            String sql = "SELECT id, vname, name FROM t_person";
            try (PreparedStatement p = connection.prepareStatement(sql);
                 ResultSet rs = p.executeQuery()) {
                // Spalten dynamisch ausgeben
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getMetaData().getColumnName(i) + "\t");
                }
                System.out.println();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String vname = rs.getString("vname");
                    String name = rs.getString("name");
                    System.out.println(id + "\t" + vname + "\t" + name);
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
