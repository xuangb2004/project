package btl.classes;

import java.math.BigDecimal;
import java.sql.*;
import javax.swing.JOptionPane;

public class ConnectionDB {
    private String url;
    private String username;
    private String password;
    private String driverName;

    public Connection conn;
    public Statement stmt;
    public ResultSet rs;

    public ConnectionDB() throws ClassNotFoundException, SQLException {
        driverName = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/hotelmanagementdb";
        username = "root";
        password = "pass";

        Class.forName(driverName);
        conn = DriverManager.getConnection(url, username, password);
        if (conn == null)
            System.out.println("Fail to connect to database");
        stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        rs = null;
    }

    public void updateKHDB(int maKH, String hoten, Date ngaysinh, String sdt, String cmnd, String email,
            String gioitinh, String quoctich) throws SQLException {
        String sql = "UPDATE KHACH SET TenKHACH = ?, NgaySinh = ?, CMND = ?, Email = ?, GioiTinh = ?, QuocTich = ?, SDT = ? WHERE MaKHACH = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, hoten);
        stmt.setDate(2, ngaysinh);
        stmt.setString(3, cmnd);
        stmt.setString(4, email);
        stmt.setString(5, gioitinh);
        stmt.setString(6, quoctich);
        stmt.setString(7, sdt);
        stmt.setInt(8, maKH);

        stmt.execute();
        System.out.println("Sửa thành công");
    }

    public void addKhachDB(String hoten, Date NgaySinh, String SDT, String Email, String CMND, String QuocTich,
            String GioiTinh) throws SQLException {
        String sql = "INSERT INTO KHACH (TenKHACH, NgaySinh, CMND, SDT, GioiTinh, Email, QuocTich) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, hoten);
        stmt.setDate(2, NgaySinh);
        stmt.setString(3, CMND);
        stmt.setString(4, SDT);
        stmt.setString(5, GioiTinh);
        stmt.setString(6, Email);
        stmt.setString(7, QuocTich);

        stmt.execute();
        System.out.println("Đăng ký khách hàng thành công");
    }

    public void addPDPDB(int maPDP, int maP, int maKHACH, int maNV, Date NgayDat, Date NgayTra, BigDecimal TienTra,
            BigDecimal GiaPhong) throws SQLException {
        String sql = "INSERT INTO PhieuDatPhong (MaPhieuDP, MaPhong, MaKHACH, MaNV, NgayDatPhong, Traphong, DonGiaThue, DonGiaPhong) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, maPDP);
        stmt.setInt(2, maP);
        stmt.setInt(3, maKHACH);
        stmt.setInt(4, maNV);
        stmt.setDate(5, NgayDat);
        stmt.setDate(6, NgayTra);
        stmt.setBigDecimal(7, TienTra);
        stmt.setBigDecimal(8, GiaPhong);

        stmt.execute();
        System.out.println("Đặt phòng thành công");
    }

    public void addPDV(int MaPhieuDV, int MaP, String MaDV, BigDecimal TienDV, int Soluong, Date NgaySD,
            BigDecimal giaDV) throws SQLException {
        String sql = "INSERT INTO PhieuDV (MaPhieuDV, MaPhong, MaDV, TienDV, Soluong, NgaySD, GiaDV) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, MaPhieuDV);
        stmt.setInt(2, MaP);
        stmt.setString(3, MaDV);
        stmt.setBigDecimal(4, TienDV);
        stmt.setInt(5, Soluong);
        stmt.setDate(6, NgaySD);
        stmt.setBigDecimal(7, giaDV);

        stmt.execute();
        System.out.println("Đặt dịch vụ thành công");
    }

    public void addHD(int MaPhong, BigDecimal TongTien, Date NgayInHD, int MaNV) throws SQLException {
        String sql = "INSERT INTO HoaDon (MaPhong, TongTien, NgayInHD, MaNV) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, MaPhong);
        stmt.setBigDecimal(2, TongTien);
        stmt.setDate(3, NgayInHD);
        stmt.setInt(4, MaNV);

        stmt.execute();
        System.out.println("Lưu hóa đơn thành công");
    }

    public void updateHD(int MaPhong, BigDecimal TongTien, Date NgayInHD) throws SQLException {
        String sql = "UPDATE HoaDon SET TongTien = ? WHERE MaPhong = ? AND NgayInHD = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setBigDecimal(1, TongTien);
        stmt.setInt(2, MaPhong);
        stmt.setDate(3, NgayInHD);

        stmt.execute();
        System.out.println("Cập nhật hóa đơn thành công");
    }
}