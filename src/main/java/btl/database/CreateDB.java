package btl.database;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class CreateDB {

    public static void main(String[] args) throws Exception {
        try {
            String url = "jdbc:mysql://localhost:3306/";

            String databaseName = "hotelmanagementdb";
            String userName = "root";
            String password = "pass";

            Connection connection = DriverManager.getConnection(url, userName, password);

            String sql = "CREATE DATABASE " + databaseName;

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
            JOptionPane.showMessageDialog(null, databaseName + " Database has been created successfully",
                    "System Message", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            Logger.getLogger(CreateDB.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Error creating database: " + e.getMessage(),
                    "System Message", JOptionPane.ERROR_MESSAGE);
        }
    }
}