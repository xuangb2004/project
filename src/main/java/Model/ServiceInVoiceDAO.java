/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

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
public class ServiceInVoiceDAO {

    private JavaConnection javaConnection;

    public ServiceInVoiceDAO() {
        javaConnection = new JavaConnection();
    }

    public List<ServiceInvoice> getAllService() {
        List<ServiceInvoice> list = new ArrayList<>();
        String query = "Select * from ServiceInvoice where status = ?";
        Connection con = javaConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, "Chưa thanh toán");
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                int serviceInvoiceId = res.getInt("ServiceInvoiceId");
                String serviceID = res.getString("ServiceID");
                int roomID = res.getInt("RoomID");
                int quantity = res.getInt("Quantity");
                double totalprice = res.getDouble("TotalPrice");
                list.add(new ServiceInvoice(serviceInvoiceId, serviceID, roomID, quantity, totalprice));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return list;
    }

    public List<ServiceInvoice> getServiceRoomByRoom(int room) {
        List<ServiceInvoice> list = new ArrayList<>();
        String query = "Select * from serviceinvoice where RoomID = ? and status = ?";
        Connection con = javaConnection.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, room);
            preparedStatement.setString(2, "Chưa thanh toán");
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                int serviceInvoiceId = res.getInt("ServiceInvoiceId");
                String serviceID = res.getString("ServiceID");
                int roomID = res.getInt("RoomID");
                int quantity = res.getInt("Quantity");
                double totalprice = res.getDouble("TotalPrice");
                list.add(new ServiceInvoice(serviceInvoiceId, serviceID, roomID, quantity, totalprice));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return list;
    }

    public void insertServiceInvoice(ServiceInvoice serviceInvoice) {
        String query = "INSERT INTO ServiceInvoice (ServiceID, RoomID, Quantity, TotalPrice,status) VALUES (?, ?, ?, ?,?)";
        Connection con = javaConnection.getConnection();

        try {
            // Chỉ đặt giá trị cho các cột khác, không bao gồm ServiceInvoiceId
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, serviceInvoice.getServiceID());
            preparedStatement.setInt(2, serviceInvoice.getRoomID());
            preparedStatement.setInt(3, serviceInvoice.getQuantity());
            preparedStatement.setDouble(4, serviceInvoice.getTotalPrice());
            preparedStatement.setString(5, "Chưa thanh toán");
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Lấy giá trị tự tăng của ServiceInvoiceId
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    serviceInvoice.setServiceInvoiceId(generatedId);
                    System.out.println("ServiceInvoice inserted successfully with ID: " + generatedId);
                } else {
                    System.out.println("Failed to retrieve generated ServiceInvoiceId.");
                }
            } else {
                System.out.println("Failed to insert ServiceInvoice.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }

    }

    public void delteSerivceInvoice(ServiceInvoice service) {
        String query = "Delete from ServiceInvoice where ServiceInvoiceId = ?";
        Connection con = javaConnection.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, service.getServiceInvoiceId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
    }

    public int checkExitsInVoice(String serviceId, int roomId) {
        int count = 0;
        Connection con = javaConnection.getConnection();

        String query = "Select count(*) as total from serviceinvoice where ServiceID = ? and RoomID = ? and status = ?  ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, serviceId);
            preparedStatement.setInt(2, roomId);
            preparedStatement.setString(3,"Chưa thanh toán");
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                count = res.getInt("total");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return count;
    }

    public boolean updateQuantityServiceInvoice(String serviceId, int roomId, int price) {
        Connection con = javaConnection.getConnection();
        String query = "UPDATE ServiceInvoice SET Quantity = Quantity + 1, TotalPrice = TotalPrice + ? WHERE ServiceID = ? AND RoomID = ? AND status = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, price);
            preparedStatement.setString(2, serviceId);
            preparedStatement.setInt(3, roomId);
            preparedStatement.setString(4,"Chưa thanh toán");

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }

        return false;
    }
    public boolean updateStatusRoomCheckOut(int roomId) {
        Connection con = javaConnection.getConnection();
        String query = "UPDATE ServiceInvoice set status = ? where RoomID = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,"Đã thanh toán");
            preparedStatement.setInt(2, roomId);
            
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }

        return false;
    }
    public double revenueServiceInvoice(String des){
        double total = 0;
         String query = "SELECT SUM(siv.TotalPrice) AS total "
                     + "FROM ServiceInvoice AS siv "
                     + "JOIN Service AS s ON siv.ServiceID = s.ServiceID "
                     + "WHERE s.Description = ? and siv.status = ?";
        Connection con = javaConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, des);
            statement.setString(2,"Đã thanh toán");
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                total = res.getDouble("total");
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return total;
    }
    public boolean updateServiceChangeRoom(int roomIdOld,int roomIdNew){
        Connection con = javaConnection.getConnection();
        String query = "Update ServiceInvoice set RoomID = ? where RoomID = ? and status = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
              preparedStatement.setInt(1, roomIdNew);
            preparedStatement.setInt(2, roomIdOld);
            preparedStatement.setString(3,"Chưa thanh toán");
            
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }

        return false;
    }
   
}
