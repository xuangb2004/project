/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quoct
 */
public class AccountDAO {

    private Account model;
    private JavaConnection javaConnection;

    public AccountDAO() {
        javaConnection = new JavaConnection();
    }

    public boolean loginAccount(String username, String password) {
        String query = "Select count(*) as total from account where username = ? and password = ?";
        Connection con = javaConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int count = res.getInt("total");
                if (count > 0) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return false;
    }

    public String getNameEmployee(String username) {
        String query = "Select e.name as res from employee as e join account as a on e.EmployeeID = a.EmployeeID where a.username = ? ";
        Connection con = javaConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                String name = res.getString("res");
                return name;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return null;
    }

  
   
    public String getEmpId(String username){
        String query = "Select EmployeeID as name from account where username = ?";
           Connection con = javaConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                String  name = res.getString("name");
                return name;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return null;
    }
    
}
