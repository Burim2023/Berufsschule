

# Einnahmen Ausgaben Rechner

Author: Burim Shala <br>
LBS Eibiswald | 2aAPC

Das ist eine einfache Java-Anwendung zur Verwaltung von Einnahmen und Ausgaben. Sie dient als Beispiel für grundlegende Softwareentwicklungsprinzipien. Die Anwendung ermöglicht das Hinzufügen, Aktualisieren, Löschen und Suchen von Buchungen in einer Datenbank, die mithilfe einer grafischen Benutzeroberfläche (GUI) gesteuert wird.

## Installation
Um die neueste Version vom Master-Branch zu klonen und die Anwendung zu installieren, folge diesen Schritten:

```cmd
git clone https://github.com/Burim2023/Berufsschule/tree/main/Einnahmen_Ausgaben_Rechner
```

Die Anwendung kann in einer Java IDE wie IntelliJ ausgeführt werden. Stelle sicher, dass alle benötigten Abhängigkeiten (z. B. JDBC-Connector für MariaDB/MySQL) korrekt konfiguriert sind.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Verwendung

Technologien im Einsatz:
<a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/></a>

- Java SE
- Swing für die GUI
- JDBC zur Verbindung mit MariaDB/MySQL

### Beispielhafte Konfiguration der Datenbank

Die Anwendung verwendet eine MariaDB/MySQL-Datenbank, um Buchungen und Kategorien zu speichern. Eine beispielhafte SQL-Struktur ist in den Projektdateien enthalten.

```sql
CREATE DATABASE Einnahmen_Ausgaben_Rechner;
USE Einnahmen_Ausgaben_Rechner;

CREATE TABLE Kostenkategorie (
    idKostenkategorie INT PRIMARY KEY AUTO_INCREMENT,
    Bezeichnung VARCHAR(100) NOT NULL,
    Kostentyp CHAR(2) NOT NULL
);

CREATE TABLE Buchung (
    idBuchung INT PRIMARY KEY AUTO_INCREMENT,
    Datum DATETIME NOT NULL,
    Zusatzinfo TEXT,
    Betrag DOUBLE NOT NULL,
    Kostenkategorie_idKostenkategorie INT,
    FOREIGN KEY (Kostenkategorie_idKostenkategorie) REFERENCES Kostenkategorie(idKostenkategorie)
);
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Quellcode

Das Projekt besteht aus folgenden Hauptklassen:

### DatabaseHandler.java

Diese Klasse ist für die Verbindung zur Datenbank und die Datenbankoperationen verantwortlich. Sie bietet Methoden zum Hinzufügen, Aktualisieren, Löschen und Abrufen von Buchungen und Kategorien.

#### Wichtige Funktionen:

- **connect()**: Stellt eine Verbindung zur MariaDB/MySQL-Datenbank her. Diese Methode wird in allen Datenbankoperationen verwendet, um eine Verbindung zur Datenbank herzustellen.
- **addBuchung()**: Fügt eine neue Buchung in die Datenbank ein, basierend auf Betrag, Zusatzinfo, Datum und der entsprechenden Kostenkategorie.
- **updateBuchung()**: Aktualisiert eine bestehende Buchung in der Datenbank.
- **deleteBuchung()**: Löscht eine Buchung aus der Datenbank anhand ihrer ID.
- **getKostenkategorien()**: Ruft alle verfügbaren Kostenkategorien aus der Datenbank ab und gibt sie als Liste zurück.
- **getAllBuchungen()**: Ruft alle Buchungen aus der Datenbank ab und gibt sie als zweidimensionales Array zurück, um sie in der GUI anzuzeigen.

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/Einnahmen_Ausgaben_Rechner";
        String user = "root";
        String password = "";  // Dein MySQL/MariaDB-Passwort

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC-Treiber nicht gefunden: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Fehler bei der Verbindung zur Datenbank: " + e.getMessage());
            return null;
        }
    }

    public void addBuchung(double betrag, String zusatzinfo, String datum, int kostenkategorieId) {
        String sql = "INSERT INTO Buchung (Betrag, Zusatzinfo, Datum, Kostenkategorie_idKostenkategorie) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn != null) {
                pstmt.setDouble(1, betrag);
                pstmt.setString(2, zusatzinfo);
                pstmt.setString(3, datum);
                pstmt.setInt(4, kostenkategorieId);
                pstmt.executeUpdate();
                System.out.println("Buchung erfolgreich hinzugefügt.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Hinzufügen der Buchung: " + e.getMessage());
        }
    }

    // Weitere Methoden zum Aktualisieren, Löschen und Abrufen von Daten...
}
```

### ApplicationView.java

Diese Klasse ist für die grafische Benutzeroberfläche verantwortlich. Sie ermöglicht dem Benutzer die Interaktion mit der Datenbank über Buttons und Eingabefelder.

#### Wichtige Funktionen:

- **ApplicationView() Konstruktor**: Initialisiert die Benutzeroberfläche und lädt alle notwendigen Daten aus der Datenbank.
- **saveBtn.addActionListener()**: Fügt eine neue Buchung hinzu, nachdem alle Felder überprüft wurden. Zeigt eine Fehlermeldung an, wenn Eingabefelder leer oder ungültig sind.
- **updateBtn.addActionListener()**: Aktualisiert eine ausgewählte Buchung in der Datenbank und zeigt die aktualisierten Daten in der Tabelle an.
- **deleteBtn.addActionListener()**: Löscht eine ausgewählte Buchung aus der Datenbank.
- **resetBtn.addActionListener()**: Setzt alle Eingabefelder zurück und aktualisiert die Tabelle mit allen Buchungen.
- **searchBtn.addActionListener()**: Sucht nach Buchungen innerhalb eines bestimmten Datumsbereichs und zeigt die Ergebnisse in der Tabelle an.

```java
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ApplicationView {
    private JPanel OverallView;
    private JTable TableView;
    private JTextField EingabeInput;
    private JTextField ZusatzInfoInput;
    private JComboBox<String> KostenkategorieCbx;
    private JButton updateBtn;
    private JButton saveBtn;
    private JButton resetBtn;
    private JButton newBtn;
    private JButton deleteBtn;
    private JLabel FilterLabel;
    private JTextField bisInput;
    private JTextField vonInput;
    private JButton searchBtn;

    public ApplicationView() {
        DatabaseHandler db = new DatabaseHandler();

        // Kostenkategorien in die ComboBox laden
        List<String> kategorien = db.getKostenkategorien();
        for (String kategorie : kategorien) {
            KostenkategorieCbx.addItem(kategorie);
        }

        // Tabelle mit allen vorhandenen Buchungen füllen
        String[] columns = {"ID", "Bezeichnung", "Einnahmen", "Ausgaben", "Datum", "Gesamt"};
        Object[][] data = db.getAllBuchungen();
        TableView.setModel(new javax.swing.table.DefaultTableModel(data, columns));

        // Button-Funktionalität für "Save"
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String betragStr = EingabeInput.getText();
                String zusatzinfo = ZusatzInfoInput.getText();
                String typ = (String) KostenkategorieCbx.getSelectedItem();

                if (betragStr.isEmpty() || zusatzinfo.isEmpty() || typ == null) {
                    JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfüllen!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double betrag = Double.parseDouble(betragStr);
                    String datum = java.time.LocalDateTime.now().toString();

                    int kostenkategorieId = db.getKostenkategorieId(typ);
                    db.addBuchung(betrag, zusatzinfo, datum, kostenkategorieId);

                    JOptionPane.showMessageDialog(null, "Buchung erfolgreich hinzugefügt!");

                    // Tabelle nach dem Speichern aktualisieren
                    Object[][] newData = db.getAllBuchungen();
                    TableView.setModel(new javax.swing.table.DefaultTableModel(newData, columns));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Bitte eine gültige Zahl für den Betrag eingeben!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Weitere Button-Funktionalitäten...
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Einnahmen Ausgaben Rechner");
        frame.setContentPane(new ApplicationView().OverallView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Screenshots


![image](https://github.com/user-attachments/assets/93bfd5d3-c999-4d1d-a11e-d2ad175a32a3)<a name="readme-top"></a>


<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Quellen
- Java API: [https://docs.oracle.com/javase/](https://docs.oracle.com/javase/)
- MariaDB Dokumentation: [https://mariadb.com/kb/en/documentation/](https://mariadb.com/kb/en/documentation/)
- Swing Tutorial: [https://docs.oracle.com/javase/tutorial/uiswing/](https://docs.oracle.com/javase/tutorial/uiswing/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[java.com]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[java-url]: https://www.java.com/de/
[product-screenshot]: Screenshot.png
