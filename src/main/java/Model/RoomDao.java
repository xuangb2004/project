/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
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
public class RoomDao {

    private JavaConnection javaConnection;

    public RoomDao() {
        javaConnection = new JavaConnection();
    }

    public List<Room> getListRoom() {
        List<Room> list = new ArrayList<>();
        String query = "SELECT * FROM room as r join roomtype as rt on r.RoomTypeId = rt.RoomTypeId ";
        Connection con = javaConnection.getConnection();
        try (Statement statement = con.createStatement(); ResultSet res = statement.executeQuery(query)) {

            while (res.next()) {
                int idRoom = res.getInt("RoomId");
                String nameRoom = res.getString("RoomName");
                int roomType = res.getInt("RoomTypeId");
                String status = res.getString("status");
                int quantity = res.getInt("Quantity");
                int area = res.getInt("Area");
                int price = res.getInt("PricePerNight");
                String des = res.getString("Description");
                String location = res.getString("location");
                Room room = new Room(idRoom, nameRoom, des, status, quantity, area, price,location);
                list.add(room);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return list;
    }

    public boolean addRoom(Room room) {
        int roomId = room.getIdRoom();
        String nameRoom = room.getNameRoom();
        int roomType = room.getRoomType();
        String status = room.getStatus();
        int quantity = room.getQuantity();
        int area = room.getArea();
        int price = room.getPrice();
        String location = room.getLocation();
        Connection con = javaConnection.getConnection();
        String query = "INSERT INTO room(RoomId, RoomName, RoomTypeId, PricePerNight,Area,Quantity,status,location) VALUES (?, ?, ?, ?, ?, ?,?,?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);
            preparedStatement.setString(2, nameRoom);
            preparedStatement.setInt(3, roomType);
            preparedStatement.setInt(4, price);
            preparedStatement.setInt(5, area);
            preparedStatement.setInt(6, quantity);
            preparedStatement.setString(7, status);
            preparedStatement.setString(8,location);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RoomDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            javaConnection.closeConnection(con);
        }
    }

    public List<String> getRoomTypeId() {
        List<String> list = new ArrayList<>();
        String query = "Select * from roomtype";
        Connection con = javaConnection.getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery(query);

            while (res.next()) {
                String des = res.getString("Description");
                list.add(des);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return list;
    }

    public boolean updateRoom(Room room) {
        int roomId = room.getIdRoom();
        String nameRoom = room.getNameRoom();
        int roomType = room.getRoomType();
        String status = room.getStatus();
        int quantity = room.getQuantity();
        int area = room.getArea();
        int price = room.getPrice();
        String location = room.getLocation();
        Connection con = javaConnection.getConnection();
        String query = "Update room set RoomName = ? , RoomTypeId = ? , PricePerNight = ? ,Area = ? ,Quantity =? ,status = ?,location = ?  where RoomId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, nameRoom);
            preparedStatement.setInt(2, roomType);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, area);
            preparedStatement.setInt(5, quantity);
            preparedStatement.setString(6, status);
              preparedStatement.setString(7, location);
            preparedStatement.setInt(8, roomId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }

        return false;
    }

    public boolean deleteRoom(Room room) {
        int roomId = room.getIdRoom();
        Connection con = javaConnection.getConnection();
        String query = "Delete from Room  where RoomId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, roomId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }

        return false;
    }

    public int countAllRoom() {
        int count = 0;
        try (Connection con = javaConnection.getConnection(); Statement statement = con.createStatement()) {
            String query = "SELECT COUNT(*) AS total FROM Room";
            ResultSet res = statement.executeQuery(query);

            if (res.next()) {
                count = res.getInt("total");
            }

        } catch (SQLException ex) {
            Logger.getLogger(RoomDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public int countInValidRoom(String status) {
        int count = 0;
        try (Connection con = javaConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) AS total FROM Room WHERE status = ?")) {

            preparedStatement.setString(1, status);

            try (ResultSet res = preparedStatement.executeQuery()) {
                if (res.next()) {
                    count = res.getInt("total");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RoomDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public int countValidRoom(String status) {
        int count = 0;
        try (Connection con = javaConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) AS total FROM Room WHERE status = ?")) {

            preparedStatement.setString(1, status);

            try (ResultSet res = preparedStatement.executeQuery()) {
                if (res.next()) {
                    count = res.getInt("total");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RoomDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public int countUnRoom(String status) {
        int count = 0;
        try (Connection con = javaConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) AS total FROM Room WHERE status = ?")) {

            preparedStatement.setString(1, status);

            try (ResultSet res = preparedStatement.executeQuery()) {
                if (res.next()) {
                    count = res.getInt("total");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RoomDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public boolean updateStatusRoom(int roomId, String status) {
        Connection con = javaConnection.getConnection();
        String query = "Update room set status = ?  where RoomId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, roomId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }

        return false;
    }

    public int checkExitst(int roomID) {
        Connection con = javaConnection.getConnection();
        String query = "Select count(*) as total from room where RoomId = ? ";
        int count = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, roomID);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next()){
                count = res.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            javaConnection.closeConnection(con);
        }
        return count;
    }
    public void setStatus(int roomId,String status){
        String query = "Update room set status = ? where RoomId = ?";
        Connection con = javaConnection.getConnection();
        try{
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,status);
            preparedStatement.setInt(2, roomId);
            preparedStatement.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            javaConnection.closeConnection(con);
        }
    }
    public int getQuantityRoom(int roomId){
        Connection con = javaConnection.getConnection();
         String query = "Select Quantity as total from room where RoomId = ? ";
        int count = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, roomId);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next()){
                count = res.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            javaConnection.closeConnection(con);
        }
        return count;
    }
    public List<Integer> getAllRoom(){
         Connection con = javaConnection.getConnection();
         String query = "Select RoomId as r from room";
         List<Integer> list = new ArrayList<>();
         try{
             PreparedStatement statement = con.prepareStatement(query);
             ResultSet res = statement.executeQuery();
             while(res.next()){
                 list.add(res.getInt("r"));
             }
         }catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            javaConnection.closeConnection(con);
        }
         return list;
    }
      public double getPrice(int roomId){
          double price = 0;
        String query = "Select PricePerNight as total from room where  RoomId = ? ";
        Connection connection = javaConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, roomId);
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                price = res.getDouble("total");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            javaConnection.closeConnection(connection);
        }
        return price;
    }
       public String getStatus(int roomId){
          String status = "";
        String query = "Select status as total from room where  RoomId = ? ";
        Connection connection = javaConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, roomId);
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                status = res.getString("total");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            javaConnection.closeConnection(connection);
        }
        return status;
    }
       public List<String> getListRoomToFacilities(){
        Connection con = javaConnection.getConnection();
        List<String> list = new ArrayList<>();
        String query = "Select RoomId from room  ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                list.add(String.valueOf(res.getInt("RoomId")));
      
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return list;
       }
}
