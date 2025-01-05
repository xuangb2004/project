package btl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.text.ParseException;

import btl.classes.TaiKhoan.Role;
import static btl.classes.TaiKhoan.setUser;

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

  public static int register(String username, String password) throws Exception {
    try {
      Connection conn = DatabaseConnection.getConnection();
      PreparedStatement stmt = conn.prepareStatement("SELECT * FROM taikhoan WHERE username = ?");
      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        DatabaseConnection.closeConnection();
        return 1; // username already exists
      }
      stmt = conn.prepareStatement("INSERT INTO taikhoan (role, username, password) VALUES (?, ?, ?)");
      stmt.setInt(1, Role.GUEST.ordinal());
      stmt.setString(2, username);
      stmt.setString(3, password);
      stmt.executeUpdate();
      DatabaseConnection.closeConnection();
      return 0; // register successfully
    } catch (SQLException e) {
      throw e;
    }
  }

  public static void registerInfo(String name, String dob, String id, String phone, String gender, String email,
      String nation) throws SQLException, ParseException {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    Date dateOfBirth = (Date) df.parse(dob);
    try {
      Connection conn = DatabaseConnection.getConnection();
      String sql = "INSERT INTO khach (TenKhach, NgaySinh, CMND, SDT, GioiTinh, Email, QuocTich) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(sql);

      stmt.setString(1, name);
      stmt.setDate(2, dateOfBirth);
      stmt.setString(3, id);
      stmt.setString(4, phone);
      stmt.setString(5, gender);
      stmt.setString(6, email);
      stmt.setString(7, nation);

      stmt.execute();

    } catch (SQLException e) {
      throw e;
    }
  }
}
