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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quoct
 */
public class ServiceDAO {

    private JavaConnection javaConnection;

    public ServiceDAO() {
        javaConnection = new JavaConnection();
    }

    public List<Service> getAllService() {
        List<Service> list = new ArrayList<>();
        String query = "Select * from service";
        Connection con = javaConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                String serviceID = res.getString("ServiceID");
                String nameService = res.getString("NameService");
                String description = res.getString("Description");
                int price = res.getInt("Price");
                int quantity = res.getInt("Quantity");
                list.add(new Service(serviceID, nameService, description, price, quantity));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return list;
    }

    public List<String> serviceType() {
        List<String> list = new ArrayList<>();
        String query = "Select distinct description from Service ";
        Connection con = javaConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                String description = res.getString("Description");
                list.add(description);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return list;
    }

    public List<Service> serviceSearch(String text) {
        List<Service> list = new ArrayList<>();
        String query = "SELECT * FROM Service WHERE NameService LIKE ?";
        Connection con = javaConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, "%" + text + "%");
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                String description = res.getString("Description");
                String serviceID = res.getString("ServiceID");
                String nameService = res.getString("NameService");
                int price = res.getInt("Price");
                int quantity = res.getInt("Quantity");
                list.add(new Service(serviceID, nameService, description, price, quantity));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return list;
    }
    public void updateQuantity(String text,int quantity){
        String query = "Update Service set quantity = quantity - ?  where NameService = ? ";
        Connection con = javaConnection.getConnection();
        try{
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, quantity);
            statement.setString(2, text);
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
    }
    public void updatePlusQuantity(String text,int quantity){
        String query = "Update Service set quantity = quantity + ?  where ServiceId = ? ";
        Connection con = javaConnection.getConnection();
        try{
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, quantity);
            statement.setString(2, text);
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
    }
    public int getQuantityInService(String service){
        String query = "Select quantity from service where ServiceId = ?";
        Connection con = javaConnection.getConnection();
        int quantity = 0;
        try{
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, service);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                quantity = res.getInt("quantity");
                return quantity;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return 0;
    }

}
