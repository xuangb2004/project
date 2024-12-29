package btl.project;

import btl.database.DatabaseConnection;
import btl.classes.Phong;
import btl.classes.PhieuDatPhong;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Guest {

    @FXML
    private TableView<Phong> tablePhong; // Bảng phòng
    @FXML
    private TableView<PhieuDatPhong> tableTransactions; // Bảng giao dịch
    @FXML
    private Button btnRoom, btnSignout, btnAddService, btnCancelTransaction; // Các nút chức năng
    @FXML
    private DatePicker dpCheckin, dpCheckout; // Các trường chọn ngày
    @FXML
    private Label lblRoomDetails; // Label hiển thị chi tiết phòng

    private int maKhach; // ID khách hàng được nhận từ hệ thống đăng nhập

    // Khởi tạo với mã khách hàng từ hệ thống đăng nhập
    public Guest(int maKhachDangNhap) {
        this.maKhach = maKhachDangNhap;
    }

    @FXML
    public void initialize() {
        try {
            hienThiPhong(); // Hiển thị danh sách phòng trống
            hienThiGiaoDich(); // Hiển thị danh sách giao dịch của khách
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hiển thị danh sách phòng trống
    public void hienThiPhong() throws SQLException {
        List<Phong> danhSachPhong = new ArrayList<>();
        String query = "SELECT * FROM phong WHERE TrangThai = 'Trống'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                danhSachPhong.add(new Phong(
                        rs.getInt("MaPhong"),
                        rs.getString("TenPhong"),
                        rs.getBigDecimal("GiaPhong")
                ));
            }
        }
        tablePhong.getItems().setAll(danhSachPhong);
    }

    // Hiển thị danh sách giao dịch của khách hàng
    public void hienThiGiaoDich() throws SQLException {
        List<PhieuDatPhong> danhSachGiaoDich = new ArrayList<>();
        String query = "SELECT * FROM phieudatphong WHERE MaKhach = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, maKhach);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    danhSachGiaoDich.add(new PhieuDatPhong(
                            rs.getInt("MaPhieu"),
                            rs.getInt("MaPhong"),
                            rs.getDate("NgayDat").toLocalDate(),
                            rs.getDate("NgayTra").toLocalDate(),
                            rs.getBigDecimal("GiaPhong"),
                            rs.getBoolean("DaHuy")
                    ));
                }
            }
        }
        tableTransactions.getItems().setAll(danhSachGiaoDich);
    }

    // Đặt phòng khi người dùng chọn ngày và phòng
    @FXML
    public void datPhong() {
        Phong phongDaChon = tablePhong.getSelectionModel().getSelectedItem();
        if (phongDaChon != null) {
            LocalDate checkinDate = dpCheckin.getValue();
            LocalDate checkoutDate = dpCheckout.getValue();
            if (checkinDate != null && checkoutDate != null) {
                String query = "INSERT INTO phieudatphong (MaPhong, MaKhach, NgayDat, NgayTra, GiaPhong) VALUES (?, ?, ?, ?, ?)";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setInt(1, phongDaChon.getMaPhong());
                    stmt.setInt(2, maKhach);
                    stmt.setDate(3, Date.valueOf(checkinDate));
                    stmt.setDate(4, Date.valueOf(checkoutDate));
                    stmt.setBigDecimal(5, phongDaChon.getDonGia());

                    stmt.executeUpdate();
                    System.out.println("Đặt phòng thành công!");
                    hienThiPhong(); // Cập nhật danh sách phòng
                    hienThiGiaoDich(); // Cập nhật danh sách giao dịch
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Vui lòng chọn ngày nhận và trả phòng.");
            }
        } else {
            System.out.println("Vui lòng chọn một phòng.");
        }
    }

    // Hủy giao dịch (nếu được phép)
    @FXML
    public void huyGiaoDich() {
        PhieuDatPhong phieuDaChon = tableTransactions.getSelectionModel().getSelectedItem();
        if (phieuDaChon != null) {
            String query = "UPDATE phieudatphong SET DaHuy = 1 WHERE MaPhieu = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, phieuDaChon.getMaPhieu());
                stmt.executeUpdate();
                System.out.println("Yêu cầu hủy phòng đã được gửi.");
                hienThiGiaoDich(); // Cập nhật danh sách giao dịch
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Vui lòng chọn một giao dịch để hủy.");
        }
    }

    // Đăng xuất khi người dùng nhấn nút "Đăng xuất"
    @FXML
    public void xuLyDangXuat() {
        System.out.println("Đăng xuất thành công.");
        // Quay lại màn hình đăng nhập hoặc thực hiện các bước cần thiết
    }
}
