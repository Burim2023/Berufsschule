/*
 * Project: DB-Verbindung
 * Klasse: 2 aApc
 * Author: Burim Shala
 * Last change:
 * by: Shala
 * date: 16.10.2024
 * */
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
