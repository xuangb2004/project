package btl.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import btl.classes.PhieuDatPhong;
import btl.classes.Phong;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hotelmanagementdb";
    private static final String USER = "root";
    private static final String PASSWORD = "pass";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Không thể kết nối đến database", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Lấy danh sách phòng trống
    public List<Phong> getPhongTrong() throws SQLException {
        List<Phong> phongTrong = new ArrayList<>();
        String query = "SELECT * FROM phong WHERE trangthai = 'Trống'"; // Giả sử có trường trangthai trong bảng phong
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int maPhong = rs.getInt("maPhong");
                String tenPhong = rs.getString("tenPhong");
                int soNguoi = rs.getInt("soNguoi");
                double donGia = rs.getDouble("donGia");
                String trangThai = rs.getString("trangThai");
                String maLP = rs.getString("maLP");
                int tang = rs.getInt("tang");

                Phong phong = new Phong(maPhong, tenPhong, soNguoi, donGia, trangThai, maLP, tang);
                phongTrong.add(phong);
            }
        }
        return phongTrong;
    }

    // Lấy danh sách phiếu đặt phòng của khách hàng
    public List<PhieuDatPhong> getDanhSachPhieuDatPhong(int maKHACH) throws SQLException {
        List<PhieuDatPhong> danhSach = new ArrayList<>();
        String query = "SELECT * FROM phieudatphong WHERE MaKHACH = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maKHACH);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int maPDP = rs.getInt("MaPDP");
                    int maP = rs.getInt("MaP");
                    String tenP = rs.getString("TenP");
                    String maLP = rs.getString("MaLP");
                    String tenLP = rs.getString("TenLP");
                    int maKhach = rs.getInt("MaKHACH");
                    String tenKhach = rs.getString("TenKHACH");
                    Date ngayDatPhong = rs.getDate("NgayDatPhong");
                    Date ngayTraPhong = rs.getDate("NgayTraPhong");
                    String giaPhong = rs.getString("GiaPhong");
                    String tienTra = rs.getString("TienTra");

                    PhieuDatPhong phieu = new PhieuDatPhong(maPDP, maP, tenP, maLP, tenLP, maKhach, tenKhach, ngayDatPhong, ngayTraPhong, giaPhong, tienTra);
                    danhSach.add(phieu);
                }
            }
        }
        return danhSach;
    }

    // Thêm phiếu đặt phòng mới
    public void themPhieuDatPhong(int maP, int maKhach, int maNV, Timestamp ngayDatPhong, Timestamp ngayTraPhong, String giaPhong, String tienTra) throws SQLException {
        String query = "INSERT INTO phieudatphong (MaP, MaKHACH, MaNV, NgayDatPhong, NgayTraPhong, GiaPhong, TienTra) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maP);
            stmt.setInt(2, maKhach);
            stmt.setInt(3, maNV);
            stmt.setTimestamp(4, ngayDatPhong);
            stmt.setTimestamp(5, ngayTraPhong);
            stmt.setString(6, giaPhong);
            stmt.setString(7, tienTra);

            stmt.executeUpdate();
        }
    }

    // Cập nhật trạng thái phòng
    public void capNhatTrangThaiPhong(int maPhong, String trangThai) throws SQLException {
        String query = "UPDATE phong SET trangthai = ? WHERE maPhong = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, trangThai);
            stmt.setInt(2, maPhong);

            stmt.executeUpdate();
        }
    }

    // Yêu cầu hủy phòng
    public void yeuCauHuyPhong(int maPDP) throws SQLException {
        String query = "UPDATE phieudatphong SET trangThai = 'Đã hủy' WHERE MaPDP = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maPDP);
            stmt.executeUpdate();
        }
    }
}
