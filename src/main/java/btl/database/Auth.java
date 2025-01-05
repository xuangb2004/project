package btl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;

import btl.classes.TaiKhoan.Role;
import static btl.classes.TaiKhoan.setUser;

import static btl.project.RegisterController.Account;

public class Auth {
  public static int login(String username, String password) throws Exception {
    int loginState;
    try {
      Connection conn = DatabaseConnection.getConnection();
      PreparedStatement stmt = conn.prepareStatement("SELECT * FROM taikhoan WHERE username = ?");
      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        if (rs.getString("password").equals(password)) {
          setUser(rs.getInt("id"), rs.getInt("role"));
          loginState = 0; // login successfully
        } else {
          loginState = 1; // incorrect password
        }
      } else {
        loginState = 2; // username not found
      }
      DatabaseConnection.closeConnection();
      return loginState;
    } catch (SQLException e) {
      throw e;
    }
  }

  public static int check(String username, String password) throws Exception {
    try {
      Connection conn = DatabaseConnection.getConnection();
      PreparedStatement stmt = conn.prepareStatement("SELECT * FROM taikhoan WHERE username = ?");
      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        DatabaseConnection.closeConnection();
        return 1; // username already exists
      }
      DatabaseConnection.closeConnection();
      return 0; // new account is valid
    } catch (SQLException e) {
      throw e;
    }
  }

  public static void registerInfo(String name, String dob, String id, String phone, String gender, String email,
      String nation) throws SQLException, ParseException {
    Date dateOfBirth = Date.valueOf(dob);
    try {
      Connection conn = DatabaseConnection.getConnection();

      PreparedStatement stmt = conn
          .prepareStatement("INSERT INTO taikhoan (role, username, password) VALUES (?, ?, ?)");
      stmt.setInt(1, Role.GUEST.ordinal());
      stmt.setString(2, Account.getUsername());
      stmt.setString(3, Account.getPassword());
      stmt.executeUpdate();

      stmt = conn.prepareStatement("SELECT id FROM taikhoan WHERE username = ?");
      stmt.setString(1, Account.getUsername());
      ResultSet rs = stmt.executeQuery();
      int Accountid = rs.getInt("id");

      String sql = "INSERT INTO khach (account_id, TenKhach, NgaySinh, CMND, SDT, GioiTinh, Email, QuocTich) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      stmt = conn.prepareStatement(sql);

      stmt.setInt(1, Accountid);
      stmt.setString(2, name);
      stmt.setDate(3, dateOfBirth);
      stmt.setString(4, id);
      stmt.setString(5, phone);
      stmt.setString(6, gender);
      stmt.setString(7, email);
      stmt.setString(8, nation);

      stmt.executeUpdate();

      DatabaseConnection.closeConnection();

    } catch (SQLException e) {
      throw e;
    }
  }
}
