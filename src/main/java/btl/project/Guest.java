package btl.project;

import btl.database.DatabaseConnection;
import btl.classes.Phong;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Guest {

    private DatabaseConnection databaseConnection;

    @FXML
    private TableView<Phong> tablePhong; // Bảng phòng
    @FXML
    private TableColumn<Phong, Integer> colMaPhong; // Cột mã phòng
    @FXML
    private TableColumn<Phong, String> colTenPhong; // Cột tên phòng
    @FXML
    private TableColumn<Phong, Integer> colSoNguoi; // Cột số người
    @FXML
    private TableColumn<Phong, Double> colDonGia; // Cột đơn giá
    @FXML
    private TableColumn<Phong, String> colTrangThai; // Cột trạng thái
    @FXML
    private TableColumn<Phong, String> colMaLP; // Cột mã loại phòng
    @FXML
    private TableColumn<Phong, Integer> colTang; // Cột tầng

    @FXML
    private Button btnRoom; // Nút "Phòng"
    @FXML
    private Button btnSignout; // Nút "Đăng xuất"
    @FXML
    private DatePicker dpCheckin, dpCheckout; // Các trường chọn ngày
    @FXML
    private Label lblRoomDetails; // Các label để hiển thị chi tiết phòng
    @FXML
    private Label lblErrorMessage; // Hiển thị thông báo lỗi
    @FXML
    private Label lblSuccessMessage; // Hiển thị thông báo thành công

    private int maKhach; // Mã khách hàng (giả sử là ID của khách hàng đã đăng nhập)
    private double tongTien; // Biến lưu trữ tổng tiền

    // Constructor mặc định (không tham số)
    public Guest() {
        try {
            databaseConnection = new DatabaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        System.out.println("Controller đã được khởi tạo"); // Debug
        try {
            // Thiết lập các cell value factory cho các cột
            colMaPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
            colTenPhong.setCellValueFactory(new PropertyValueFactory<>("tenPhong"));
            colSoNguoi.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));
            colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
            colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
            colMaLP.setCellValueFactory(new PropertyValueFactory<>("maLP"));
            colTang.setCellValueFactory(new PropertyValueFactory<>("tang"));
            
            hienThiPhong();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hiển thị danh sách phòng trống
    public void hienThiPhong() throws SQLException {
        List<Phong> danhSachPhong = databaseConnection.getPhongTrong();
        System.out.println("Số phòng trống: " + danhSachPhong.size()); // Debug

        if (danhSachPhong.isEmpty()) {
            lblErrorMessage.setText("Hiện tại không có phòng trống.");
        } else {
            lblErrorMessage.setText(""); // Xóa thông báo lỗi nếu có
            for (Phong p : danhSachPhong) {
                System.out.println(p.getTenPhong()); // Debug
            }
            tablePhong.getItems().setAll(danhSachPhong);
        }
    }

    // Xử lý sự kiện khi nhấn nút "Phòng"
    @FXML
    public void xuLyDatPhong() {
        Phong phongDaChon = tablePhong.getSelectionModel().getSelectedItem();
        if (phongDaChon != null) {
            // Hiển thị chi tiết phòng đã chọn
            lblRoomDetails.setText("Tên phòng: " + phongDaChon.getTenPhong() + "\n" +
                                    "Giá phòng: " + phongDaChon.getDonGia());
            // Hiển thị các thông tin chọn ngày (check-in, check-out)
            dpCheckin.setValue(LocalDate.now());
            dpCheckout.setValue(LocalDate.now().plusDays(1)); // Mặc định ngày check-out là một ngày sau ngày check-in
            lblErrorMessage.setText(""); // Xóa thông báo lỗi nếu có
        } else {
            lblErrorMessage.setText("Vui lòng chọn một phòng.");
        }
    }

    // Đặt phòng khi người dùng điền thông tin và chọn ngày
    @FXML
    public void datPhong() {
        try {
            Phong phongDaChon = tablePhong.getSelectionModel().getSelectedItem();
            if (phongDaChon == null) {
                lblErrorMessage.setText("Vui lòng chọn một phòng trước khi đặt.");
                return;
            }

            LocalDate checkinDate = dpCheckin.getValue();
            LocalDate checkoutDate = dpCheckout.getValue();
            
            if (checkinDate == null || checkoutDate == null) {
                lblErrorMessage.setText("Vui lòng chọn ngày nhận và trả phòng.");
                return;
            }

            if (checkoutDate.isBefore(checkinDate)) {
                lblErrorMessage.setText("Ngày trả phòng không được trước ngày nhận phòng.");
                return;
            }

            int maNV = 1;
            maKhach = 1;
            Timestamp ngayDat = Timestamp.valueOf(checkinDate.atStartOfDay());
            Timestamp traPhong = Timestamp.valueOf(checkoutDate.atStartOfDay());
            long soNgay = checkoutDate.toEpochDay() - checkinDate.toEpochDay();
            double donGia = phongDaChon.getDonGia();

            Connection conn = databaseConnection.getConnection();
            conn.setAutoCommit(false);

            try {
                String sqlPhieu = "INSERT INTO phieudatphong (MaPhong, MaKhach, MaNV, NgayDatPhong, TraPhong, DonGiaThue) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sqlPhieu);
                pstmt.setInt(1, phongDaChon.getMaPhong());
                pstmt.setInt(2, maKhach);
                pstmt.setInt(3, maNV);
                pstmt.setTimestamp(4, ngayDat);
                pstmt.setTimestamp(5, traPhong);
                pstmt.setDouble(6, donGia);
                pstmt.executeUpdate();
                pstmt.close();

                String sqlUpdatePhong = "UPDATE phong SET TrangThai = 'Đang sử dụng' WHERE MaPhong = ?";
                pstmt = conn.prepareStatement(sqlUpdatePhong);
                pstmt.setInt(1, phongDaChon.getMaPhong());
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
                tongTien += donGia; // Cộng dồn đơn giá vào tổng tiền
                lblSuccessMessage.setText("Đặt phòng thành công! Tổng tiền: " + tongTien + " VND");
                lblErrorMessage.setText("");
                
                hienThiPhong();

            } catch (SQLException e) {
                if (conn != null) {
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                throw e;
            }

        } catch (SQLException e) {
            lblErrorMessage.setText("Lỗi khi đặt phòng: " + e.getMessage());
            System.out.println("Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            lblErrorMessage.setText("Có lỗi xảy ra: " + e.getMessage());
            System.out.println("Lỗi chung: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Đặt lại tổng tiền khi thoát ứng dụng
    public void resetTongTien() {
        tongTien = 0;
        System.out.println("Tổng tiền đã được đặt lại về 0."); // Debug
    }

}
