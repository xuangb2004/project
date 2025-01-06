/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class BookingDao {

    private JavaConnection javaConnection;

    public BookingDao() {
        javaConnection = new JavaConnection();
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        Connection connection = javaConnection.getConnection();
        String sql = "SELECT * FROM booking";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getString("bookingId"));
                booking.setCustomerId(resultSet.getString("customerId"));
                booking.setRoomId(resultSet.getInt("roomId"));
                Timestamp checkInTimestamp = resultSet.getTimestamp("checkInDate");
                Timestamp checkOutTimestamp = resultSet.getTimestamp("checkOutDate");
                LocalDateTime checkInDateTime = checkInTimestamp.toLocalDateTime();
                LocalDateTime checkOutDateTime = checkOutTimestamp.toLocalDateTime();
                booking.setCheckInDate(checkInDateTime);
                booking.setCheckOutDate(checkOutDateTime);
                booking.setNumberOfPeople(resultSet.getString("numberOfPeople"));
                booking.setRequiredSpecial(resultSet.getString("requiredSpecial"));
                booking.setInvest(resultSet.getInt("invest"));
                booking.setRoomRental(resultSet.getString("roomRental"));
                booking.setPrice(resultSet.getDouble("price"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            javaConnection.closeConnection(connection);
        }
        return bookings;
    }

    public void insertBooking(Booking booking, int customer) {
        Connection connection = javaConnection.getConnection();
        String sql = "INSERT INTO booking (customerId, roomId, checkInDate, checkOutDate, numberOfPeople, requiredSpecial, invest, roomRental,price,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, customer);
            preparedStatement.setInt(2, booking.getRoomId());
            LocalDateTime checkInDateTime = booking.getCheckInDate();
            LocalDateTime checkOutDateTime = booking.getCheckOutDate();
            Timestamp sqlCheckInDateTime = Timestamp.valueOf(checkInDateTime);
            Timestamp sqlCheckOutDateTime = Timestamp.valueOf(checkOutDateTime);
            preparedStatement.setTimestamp(3, sqlCheckInDateTime);
            preparedStatement.setTimestamp(4, sqlCheckOutDateTime);
            preparedStatement.setString(5, booking.getNumberOfPeople());
            preparedStatement.setString(6, booking.getRequiredSpecial());
            preparedStatement.setInt(7, booking.getInvest());
            preparedStatement.setString(8, booking.getRoomRental());
            preparedStatement.setDouble(9, booking.getPrice());
            preparedStatement.setString(10, "Chưa thanh toán");
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    booking.setBookingId(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Failed to get auto-generated keys, no bookingId obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            javaConnection.closeConnection(connection);
        }
    }

    public void updateBooking(Booking booking) {
        Connection connection = javaConnection.getConnection();
        String sql = "UPDATE booking SET status = ?  WHERE bookingId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "Đã thanh toán");
            preparedStatement.setString(2, booking.getBookingId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            javaConnection.closeConnection(connection);
        }
    }

    public void deleteBooking(String bookingId) {
        Connection connection = javaConnection.getConnection();
        String sql = "DELETE FROM booking WHERE bookingId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bookingId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            javaConnection.closeConnection(connection);
        }
    }

    public List<Booking> getAllBookingsConditions(int roomId) {
        List<Booking> bookings = new ArrayList<>();
        Connection connection = javaConnection.getConnection();
        String sql = "SELECT * FROM booking WHERE RoomId = ? and status = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, roomId);
            preparedStatement.setString(2, "Chưa thanh toán");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getString("bookingId"));
                booking.setCustomerId(resultSet.getString("customerId"));
                booking.setRoomId(resultSet.getInt("roomId"));
                Timestamp checkInTimestamp = resultSet.getTimestamp("checkInDate");
                Timestamp checkOutTimestamp = resultSet.getTimestamp("checkOutDate");
                LocalDateTime checkInDateTime = checkInTimestamp.toLocalDateTime();
                LocalDateTime checkOutDateTime = checkOutTimestamp.toLocalDateTime();
                booking.setCheckInDate(checkInDateTime);
                booking.setCheckOutDate(checkOutDateTime);
                booking.setNumberOfPeople(resultSet.getString("numberOfPeople"));
                booking.setRequiredSpecial(resultSet.getString("requiredSpecial"));
                booking.setInvest(resultSet.getInt("invest"));
                booking.setRoomRental(resultSet.getString("roomRental"));
                booking.setPrice(resultSet.getDouble("price"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            javaConnection.closeConnection(connection);
        }
        return bookings;
    }

    public double revenueHotelInYear(int year) {
        double revenue = 0;
        String query = "SELECT SUM(price) AS total FROM booking WHERE YEAR(checkInDate) = ? AND status = ?";
        Connection connection = javaConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, year);
            preparedStatement.setString(2, "Đã thanh toán");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                revenue = resultSet.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            javaConnection.closeConnection(connection);
        }
        return revenue;
    }

    public double revenueHotelInMonth(int month, int year) {
        double revenue = 0;
        String query = "Select sum(price) as total from booking where year(checkInDate) = ? and Month(checkInDate) = ? and status = ?  ";
        Connection connection = javaConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);
            preparedStatement.setString(3, "Đã thanh toán");
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                revenue = res.getDouble("total");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            javaConnection.closeConnection(connection);
        }
        return revenue;
    }

    public double revenueRoomByRoom(int roomId) {
        double revenue = 0;
        String query = "Select sum(price) as total from booking where status = ? and RoomId = ?  ";
        Connection connection = javaConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Đã thanh toán");
            preparedStatement.setInt(2, roomId);
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                revenue = res.getDouble("total");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            javaConnection.closeConnection(connection);
        }
        return revenue;
    }
    public boolean changeRoom(int roomIdOld,int roomIdNew){
        String query = "Update booking  set RoomId = ? where status = ? and RoomId = ? ";
         Connection connection = javaConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
             preparedStatement.setInt(1, roomIdNew);
            preparedStatement.setString(2, "Chưa thanh toán");
            preparedStatement.setInt(3, roomIdOld);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            javaConnection.closeConnection(connection);
        }
        return false;
    }
       public boolean changeVipRoom(int roomIdOld,int roomIdNew,double newPrice){
       String query = "UPDATE booking SET RoomId = ?, price = ? WHERE status = ? AND RoomId = ?";
         Connection connection = javaConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
             preparedStatement.setInt(1, roomIdNew);
             preparedStatement.setDouble(2, newPrice);
            preparedStatement.setString(3, "Chưa thanh toán");
            preparedStatement.setInt(4, roomIdOld);
            
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            javaConnection.closeConnection(connection);
        }
        return false;
    }
  
}
