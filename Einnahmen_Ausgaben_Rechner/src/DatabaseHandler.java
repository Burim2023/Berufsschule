/*
 * Project: Buchungs-Applikation
 * Klasse: 2 aApc
 * Author: Burim Shala
 * Last change:
 * by: Shala
 * date: 23.10.2024
 * */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void updateBuchung(String datum, double betrag, String zusatzinfo, int kostenkategorieId) {
        String sql = "UPDATE Buchung SET Betrag = ?, Zusatzinfo = ?, Kostenkategorie_idKostenkategorie = ? WHERE Datum = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn != null) {
                pstmt.setDouble(1, betrag);
                pstmt.setString(2, zusatzinfo);
                pstmt.setInt(3, kostenkategorieId);
                pstmt.setString(4, datum);
                pstmt.executeUpdate();
                System.out.println("Buchung erfolgreich aktualisiert.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aktualisieren der Buchung: " + e.getMessage());
        }
    }

    public void deleteBuchung(int idBuchung) {
        String sql = "DELETE FROM Buchung WHERE idBuchung = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn != null) {
                pstmt.setInt(1, idBuchung);
                pstmt.executeUpdate();
                System.out.println("Buchung erfolgreich gelöscht.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen der Buchung: " + e.getMessage());
        }
    }

    public void addKategorie(String bezeichnung, String kostentyp) {
        String sql = "INSERT INTO Kostenkategorie (Bezeichnung, Kostentyp) VALUES (?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn != null) {
                pstmt.setString(1, bezeichnung);
                pstmt.setString(2, kostentyp);
                pstmt.executeUpdate();
                System.out.println("Kategorie erfolgreich hinzugefügt.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Hinzufügen der Kategorie: " + e.getMessage());
        }
    }

    public int getKostenkategorieId(String kategorieName) {
        String sql = "SELECT idKostenkategorie FROM Kostenkategorie WHERE Bezeichnung = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn != null) {
                pstmt.setString(1, kategorieName);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    return rs.getInt("idKostenkategorie");
                }
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Kostenkategorie-ID: " + e.getMessage());
        }
        return -1;  // Fehlerfall
    }

    public List<String> getKostenkategorien() {
        String sql = "SELECT Bezeichnung FROM Kostenkategorie";
        List<String> kategorien = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn != null) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    kategorien.add(rs.getString("Bezeichnung"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Kostenkategorien: " + e.getMessage());
        }
        return kategorien;
    }

    public Object[][] getBuchungen(String von, String bis) {
        String sql = "SELECT b.idBuchung, k.Bezeichnung, k.Kostentyp, b.Betrag, b.Zusatzinfo, b.Datum FROM Buchung b JOIN Kostenkategorie k ON b.Kostenkategorie_idKostenkategorie = k.idKostenkategorie WHERE Datum BETWEEN ? AND ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            if (conn != null) {
                pstmt.setString(1, von);
                pstmt.setString(2, bis);
                ResultSet rs = pstmt.executeQuery();

                rs.last();
                int rowCount = rs.getRow();
                rs.beforeFirst();

                Object[][] data = new Object[rowCount + 1][6]; // Zusätzliche Zeile für die Summen
                int i = 0;
                double sumEinnahmen = 0;
                double sumAusgaben = 0;

                while (rs.next()) {
                    data[i][0] = rs.getInt("idBuchung");
                    data[i][1] = rs.getString("Bezeichnung");
                    String kostentyp = rs.getString("Kostentyp");
                    double betrag = rs.getDouble("Betrag");

                    if ("AU".equalsIgnoreCase(kostentyp)) {
                        data[i][2] = 0.0; // Einnahmen
                        data[i][3] = Math.abs(betrag); // Ausgaben
                        sumAusgaben += Math.abs(betrag);
                    } else {
                        data[i][2] = betrag; // Einnahmen
                        data[i][3] = 0.0; // Ausgaben
                        sumEinnahmen += betrag;
                    }
                    data[i][4] = rs.getString("Datum");
                    i++;
                }

                // Summenzeile hinzufügen
                data[i][1] = "Summe";
                data[i][2] = sumEinnahmen;
                data[i][3] = sumAusgaben;
                data[i][5] = "Gesamt: " + (sumEinnahmen - sumAusgaben);

                return data;
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Buchungen: " + e.getMessage());
        }
        return new Object[0][0];  // Fehlerfall
    }

    public Object[][] getAllBuchungen() {
        String sql = "SELECT b.idBuchung, k.Bezeichnung, k.Kostentyp, b.Betrag, b.Zusatzinfo, b.Datum FROM Buchung b JOIN Kostenkategorie k ON b.Kostenkategorie_idKostenkategorie = k.idKostenkategorie";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            if (conn != null) {
                ResultSet rs = pstmt.executeQuery();

                rs.last();
                int rowCount = rs.getRow();
                rs.beforeFirst();

                Object[][] data = new Object[rowCount + 1][6]; // Zusätzliche Zeile für die Summen
                int i = 0;
                double sumEinnahmen = 0;
                double sumAusgaben = 0;

                while (rs.next()) {
                    data[i][0] = rs.getInt("idBuchung");
                    data[i][1] = rs.getString("Bezeichnung");
                    String kostentyp = rs.getString("Kostentyp");
                    double betrag = rs.getDouble("Betrag");

                    if ("AU".equalsIgnoreCase(kostentyp)) {
                        data[i][2] = 0.0; // Einnahmen
                        data[i][3] = Math.abs(betrag); // Ausgaben
                        sumAusgaben += Math.abs(betrag);
                    } else {
                        data[i][2] = betrag; // Einnahmen
                        data[i][3] = 0.0; // Ausgaben
                        sumEinnahmen += betrag;
                    }
                    data[i][4] = rs.getString("Datum");
                    i++;
                }

                // Summenzeile hinzufügen
                data[i][1] = "Summe";
                data[i][2] = sumEinnahmen;
                data[i][3] = sumAusgaben;
                data[i][5] = "Gesamt: " + (sumEinnahmen - sumAusgaben);

                return data;
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Buchungen: " + e.getMessage());
        }
        return new Object[0][0];  // Fehlerfall
    }
}