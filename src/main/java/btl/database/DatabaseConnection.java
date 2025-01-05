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

    // Kết nối đến cơ sở dữ liệu
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

    // Đóng kết nối cơ sở dữ liệu
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
        String query = "SELECT * FROM phong WHERE TrangThai = 'Trống'";
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
        closeConnection();
        return phongTrong;
    }

    // Lấy danh sách phiếu đặt phòng của khách hàng
    public List<PhieuDatPhong> getDanhSachPhieuDatPhong(int maKHACH) throws SQLException {
        List<PhieuDatPhong> danhSach = new ArrayList<>();
        String query = "SELECT * FROM phieudatphong WHERE MaKhach = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maKHACH);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int maPDP = rs.getInt("MaPDP");
                    int maPhong = rs.getInt("MaPhong");
                    String tenP = rs.getString("TenP");
                    String maLP = rs.getString("MaLP");
                    String tenLP = rs.getString("TenLP");
                    int maKhach = rs.getInt("MaKhach");
                    String tenKhach = rs.getString("TenKhach");
                    Date ngayDatPhong = rs.getDate("NgayDatPhong");
                    Date ngayTraPhong = rs.getDate("TraPhong");
                    String giaPhong = rs.getString("DonGiaPhong");
                    String tienTra = rs.getString("DonGiaThue");

                    PhieuDatPhong phieu = new PhieuDatPhong(maPDP, maPhong, tenP, maLP, tenLP, maKhach, tenKhach,
                            ngayDatPhong, ngayTraPhong, giaPhong, tienTra);
                    danhSach.add(phieu);
                }
            }
        }
        closeConnection();
        return danhSach;
    }

    // Thêm phiếu đặt phòng mới
    public void themPhieuDatPhong(int maPhong, int maKhach, int maNV, Timestamp ngayDatPhong, Timestamp TraPhong,
            String giaPhong, String tienTra) throws SQLException {
        String query = "INSERT INTO phieudatphong (MaPhong, MaKHACH, MaNV, NgayDatPhong, TraPhong, DonGiaPhong, DonGiaThue) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maPhong);
            stmt.setInt(2, maKhach);
            stmt.setInt(3, maNV);
            stmt.setTimestamp(4, ngayDatPhong);
            stmt.setTimestamp(5, TraPhong);
            stmt.setString(6, giaPhong);
            stmt.setString(7, tienTra);

            stmt.executeUpdate();
        }
        closeConnection();
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
        closeConnection();
    }

    // Xóa phiếu đặt phòng trong cơ sở dữ liệu
    public void xoaPhieuDatPhong(int maPDP) throws SQLException {
        String query = "DELETE FROM phieudatphong WHERE MaPDP = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maPDP);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Phiếu đặt phòng đã được xóa.");
            } else {
                System.out.println("Không tìm thấy phiếu đặt phòng để xóa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Lỗi khi xóa phiếu đặt phòng", e);
        }
        closeConnection();
    }

    // Lấy phòng theo mã phòng
    public Phong getPhongByMa(int maPhong) throws SQLException {
        String query = "SELECT * FROM phong WHERE MaPhong = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maPhong);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Phong(
                            rs.getInt("MaPhong"),
                            rs.getString("TenPhong"),
                            rs.getInt("SoNguoi"),
                            rs.getDouble("DonGia"),
                            rs.getString("TrangThai"),
                            rs.getString("MaLP"),
                            rs.getInt("Tang"));
                }
            }
        }
        closeConnection();
        return null; // Trả về null nếu không tìm thấy phòng

    }

    // Thêm khách hàng mới và trả về mã khách hàng
    public int themKhachHang(String tenKhach, Date ngaySinh, String sdt, String cmnd, String email, String gioiTinh,
            String quocTich) throws SQLException {
        String query = "INSERT INTO khach (TenKhach, NgaySinh, SDT, CMND, Email, GioiTinh, QuocTich) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tenKhach);
            stmt.setDate(2, ngaySinh);
            stmt.setString(3, sdt);
            stmt.setString(4, cmnd);
            stmt.setString(5, email);
            stmt.setString(6, gioiTinh);
            stmt.setString(7, quocTich);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Thêm khách hàng thất bại, không có dòng nào được thêm vào.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    int key = generatedKeys.getInt(1);
                    closeConnection();
                    return key;

                } else {
                    closeConnection();

                    throw new SQLException("Thêm khách hàng thất bại, không lấy được ID.");
                }

            }
        }

    }
}
