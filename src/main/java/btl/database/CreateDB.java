package btl.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class CreateDB {

    public static void main(String[] args) throws Exception {
        try {
            String url = "jdbc:mysql://localhost:3306/";

            String databaseName = "HotelManagementDB";
            String userName = "root";
            String password = "123456";

            Connection connection = DriverManager.getConnection(url, userName, password);

            String sql = "CREATE DATABASE " + databaseName;

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
            JOptionPane.showMessageDialog(null, databaseName + " Database has been created successfully",
                    "System Message", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}