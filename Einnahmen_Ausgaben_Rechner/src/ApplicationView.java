/*
 * Project: Buchungs-Applikation
 * Klasse: 2 aApc
 * Author: Burim Shala
 * Last change:
 * by: Shala
 * date: 23.10.2024
 * */
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

        // Button-Funktionalität für "Update"
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = TableView.getSelectedRow();
                if (selectedRow != -1) {
                    String datum = TableView.getValueAt(selectedRow, 4).toString();
                    String betrag = EingabeInput.getText();
                    String zusatzinfo = ZusatzInfoInput.getText();
                    String typ = KostenkategorieCbx.getSelectedItem().toString();

                    int kostenkategorieId = db.getKostenkategorieId(typ);
                    db.updateBuchung(datum, Double.parseDouble(betrag), zusatzinfo, kostenkategorieId);

                    JOptionPane.showMessageDialog(null, "Buchung erfolgreich aktualisiert!");

                    // Tabelle nach der Aktualisierung aktualisieren
                    Object[][] newData = db.getAllBuchungen();
                    TableView.setModel(new javax.swing.table.DefaultTableModel(newData, columns));
                } else {
                    JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine Zeile aus, die aktualisiert werden soll.");
                }
            }
        });

        // Button-Funktionalität für "Suchen"
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String von = vonInput.getText();
                String bis = bisInput.getText();
                Object[][] filteredData = db.getBuchungen(von, bis);
                TableView.setModel(new javax.swing.table.DefaultTableModel(filteredData, columns));
            }
        });

        // Button-Funktionalität für "Reset"
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Alle Eingabefelder leeren
                EingabeInput.setText("");
                ZusatzInfoInput.setText("");
                vonInput.setText("");
                bisInput.setText("");
                KostenkategorieCbx.setSelectedIndex(0);

                // Tabelle mit allen vorhandenen Buchungen aktualisieren
                Object[][] allData = db.getAllBuchungen();
                TableView.setModel(new javax.swing.table.DefaultTableModel(allData, columns));
            }
        });

        // Button-Funktionalität für "New"
        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String neueKategorie = JOptionPane.showInputDialog("Neue Kategorie hinzufügen:");
                String kurzbezeichnung = JOptionPane.showInputDialog("Kurzbezeichnung hinzufügen(3 Zeichen lang):");
                String kostentyp = JOptionPane.showInputDialog("Kostentyp (z.B. 'AU' für Ausgaben oder 'EI' für Einnahmen):");
                if (neueKategorie != null && !neueKategorie.trim().isEmpty() && kostentyp != null && !kostentyp.trim().isEmpty() && kurzbezeichnung != null && !kurzbezeichnung.trim().isEmpty()) {
                    db.addKategorie(neueKategorie, kostentyp);
                    KostenkategorieCbx.addItem(neueKategorie);
                    JOptionPane.showMessageDialog(null, "Kategorie erfolgreich hinzugefügt!");
                } else {
                    JOptionPane.showMessageDialog(null, "Ungültige Eingabe.");
                }
            }
        });

        // Button-Funktionalität für "Delete"
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = TableView.getSelectedRow();
                if (selectedRow != -1) {
                    int idBuchung = (int) TableView.getValueAt(selectedRow, 0);
                    db.deleteBuchung(idBuchung);
                    JOptionPane.showMessageDialog(null, "Buchung erfolgreich gelöscht!");

                    // Tabelle nach dem Löschen aktualisieren
                    Object[][] newData = db.getAllBuchungen();
                    TableView.setModel(new javax.swing.table.DefaultTableModel(newData, columns));
                } else {
                    JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine Zeile aus, die gelöscht werden soll.");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Einnahmen Ausgaben Rechner");
        frame.setContentPane(new ApplicationView().OverallView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1500,800);
        frame.setVisible(true);
    }
}





