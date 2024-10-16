# MariaDB Java Verbindung Projekt

Author: Burim Shala  
LBS Eibiswald | 2aAPC

Dieses Projekt zeigt, wie man eine Verbindung zu einer MariaDB-Datenbank mit Java und JDBC herstellt. Die Anwendung besteht aus mehreren Klassen (`Main.java`, `Connector.java`, und `Tables.java`), die zusammenarbeiten, um eine Verbindung zu einer MariaDB-Datenbank herzustellen, Tabellen zu erstellen und Daten abzufragen.

## Installation

Um die neueste Version vom Master-Branch zu klonen und die Anwendung zu installieren, führe folgende Schritte aus:

```cmd
git clone https://github.com/Burim2023/Berufsschule.git
cd Berufsschule/DB-Anbindung_Shala
```

## Wichtige Code Teile

### Main.java

Diese Datei enthält den Einstiegspunkt der Anwendung. Hier wird die Datenbankverbindung hergestellt und eine Beispielabfrage ausgeführt.

```java
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
```

### Connector.java

Diese Datei enthält eine Methode, die eine Verbindung zur Datenbank herstellt.

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    public static Connection getConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
```

### Tables.java

Diese Datei enthält Methoden, um mit den Tabellen in der Datenbank zu arbeiten, z. B. Daten abzufragen.

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tables {
    public static void selectAllFromPersonTable(Connection connection) {
        String sql = "SELECT id, vname, name FROM t_person";
        try (PreparedStatement p = connection.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            System.out.println("id	vname	name");
            while (rs.next()) {
                int id = rs.getInt("id");
                String vname = rs.getString("vname");
                String name = rs.getString("name");
                System.out.println(id + "	" + vname + "	" + name);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
```

## Verwendung

Um die Anwendung auszuführen, folge diesen Schritten:

1. **Konfiguriere die Datenbankverbindung**: Aktualisiere die Datenbankverbindungs-URL in der `Main.java` Datei, damit sie zu deiner MariaDB-Konfiguration passt.
   ```java
   String url = "jdbc:mysql://127.0.0.1:3306/uebungen"; // Host, Port und Datenbankname entsprechend anpassen
   ```
2. **Eingabe der Benutzerdaten**: Gib deinen Benutzernamen und das Passwort für MariaDB ein, um die Verbindung herzustellen. Die Anwendung wird dann eine Abfrage auf der Tabelle `t_person` ausführen und die Spalten `id`, `vname` und `name` anzeigen.

## Beispielausgabe

```
Enter username: root
Enter password:
Verbindung zur MariaDB erfolgreich hergestellt!
id     vname   name
1      Marcus  Ganea
2      Burim   Shala
3      Dino    Haskic
4      Albin   Prushi
```

## Fehlerbehebung

- **SQL Exception: Unbekannte Spalte**: Stelle sicher, dass die Tabelle `t_person` existiert und die Spalten `id`, `vname` und `name` enthält.
- **Verbindungsprobleme**: Stelle sicher, dass der MariaDB-Server läuft und dass die Verbindungsdetails (Host, Port, Datenbankname, Benutzername und Passwort) korrekt sind.
