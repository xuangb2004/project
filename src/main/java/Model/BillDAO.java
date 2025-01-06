/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author quoct
 */
public class BillDAO {

    private JavaConnection javaConnection;

    public BillDAO() {
        javaConnection = new JavaConnection();
    }

    public void insertBill(int bookingId, double total, String employeeId) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = javaConnection.getConnection();
            String query = "INSERT INTO invoicehotel (BookingId,TotalInvoicePrice,EmployeeID) VALUES (?, ?, ?)";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, bookingId);
            stmt.setDouble(2, total);
            stmt.setString(3, employeeId);
            stmt.executeUpdate();
            System.out.println("Thannh cong");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            javaConnection.closeConnection(con);
        }
    }
}
