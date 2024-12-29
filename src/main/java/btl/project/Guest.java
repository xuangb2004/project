package btl.project;

import btl.classes.Phong;
import btl.classes.PhieuDatPhong;
import btl.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Guest {

    private DatabaseConnection connectionDB;
    private int maKhach; // Mã khách hàng, sẽ được lấy từ tài khoản đăng nhập

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

    public Guest(int maKhach) { // Nhận mã khách hàng từ quá trình đăng nhập
        this.maKhach = maKhach;
        connectionDB = new DatabaseConnection();
    }

    @FXML
    public void initialize() {
        try {
            hienThiPhong(); // Hiển thị danh sách phòng trống
            hienThiGiaoDich(); // Hiển thị giao dịch của khách
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hiển thị danh sách phòng trống
    public void hienThiPhong() throws SQLException {
        try {
            if (connectionDB == null) {
                throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo");
            }
            
            List<Phong> danhSachPhong = connectionDB.getPhongTrong();
            if (tablePhong != null) {
                tablePhong.getItems().clear();
                if (danhSachPhong != null) {
                    tablePhong.getItems().addAll(danhSachPhong);
                }
            } else {
                throw new SQLException("Bảng hiển thị chưa được khởi tạo");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi hiển thị danh sách phòng: " + e.getMessage());
            throw e;
        }
    }

    // Hiển thị danh sách giao dịch của khách hàng
    public void hienThiGiaoDich() throws SQLException {
        List<PhieuDatPhong> danhSachGiaoDich = connectionDB.getDanhSachPhieuDatPhong(maKhach);
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
                try {
                    int maNV = 1; // Giả sử nhân viên mặc định
                    Timestamp ngayDat = Timestamp.valueOf(checkinDate.atStartOfDay());
                    Timestamp traPhong = Timestamp.valueOf(checkoutDate.atStartOfDay());

                    // Sử dụng DatabaseConnection để thêm phiếu đặt phòng
                    connectionDB.themPhieuDatPhong(
                            phongDaChon.getMaPhong(),
                            maKhach,
                            maNV,
                            ngayDat,
                            traPhong,
                            String.valueOf(phongDaChon.getDonGia()), // Chuyển DonGia từ double thành String
                            String.valueOf(phongDaChon.getDonGia())  // Chuyển DonGia từ double thành String
                    );
                    // Cập nhật trạng thái phòng
                    connectionDB.capNhatTrangThaiPhong(phongDaChon.getMaPhong(), "Đang sử dụng");
                    hienThiPhong(); // Cập nhật lại danh sách phòng
                    hienThiGiaoDich(); // Cập nhật lại danh sách giao dịch
                    System.out.println("Đặt phòng thành công.");
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
        try {
            // Xóa phiếu đặt phòng trong cơ sở dữ liệu
            connectionDB.xoaPhieuDatPhong(phieuDaChon.getMaPDP()); // Thay getMaPDP() bằng mã phiếu

            System.out.println("Giao dịch đã được hủy.");

            // Cập nhật lại danh sách giao dịch
            hienThiGiaoDich();
            
            // Cập nhật trạng thái phòng
            Phong phong = connectionDB.getPhongByMa(phieuDaChon.getMaP());
            if (phong != null) {
                connectionDB.capNhatTrangThaiPhong(phong.getMaPhong(), "Còn trống"); // Trạng thái phòng quay lại là "Còn trống"
            }
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
