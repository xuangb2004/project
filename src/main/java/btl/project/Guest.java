package btl.project;

import btl.ClassData.ConnectionDB;
import btl.ClassData.Phong;
import btl.ClassData.PhieuDatPhong;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Guest {
    private ConnectionDB connectionDB;

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

    private int maKhach = 1; // Mã khách hàng hiện tại (giả sử là 1, thay bằng giá trị thực tế từ đăng nhập)

    public Guest() {
        try {
            connectionDB = new ConnectionDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        List<Phong> danhSachPhong = connectionDB.getPhongTrong();
        tablePhong.getItems().setAll(danhSachPhong);
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

                    connectionDB.themPhieuDatPhong(
                            phongDaChon.getMaPhong(),
                            maKhach,
                            maNV,
                            ngayDat,
                            traPhong,
                            phongDaChon.getDonGia(),
                            phongDaChon.getDonGia()
                    );
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
                if (!phieuDaChon.isDaHuy()) {
                    connectionDB.yeuCauHuyPhong(phieuDaChon.getMaPhieu());
                    System.out.println("Yêu cầu hủy phòng đã được gửi.");
                    hienThiGiaoDich(); // Cập nhật danh sách giao dịch
                } else {
                    System.out.println("Giao dịch này đã bị hủy trước đó.");
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
