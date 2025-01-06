/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author quoct
 */
public class JavaConnection {
    private final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project_hotel_management";
    private final String USER_NAME = "root";
    private final String PASS_WORD = "";

    public JavaConnection() {
         try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.print("Can't not load driver!");
        }
    }
    
     public Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(DB_URL, USER_NAME, PASS_WORD);
            return con;
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
    }

    public void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {

        }
    }
}
