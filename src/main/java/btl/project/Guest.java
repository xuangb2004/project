package btl.project;

import btl.database.DatabaseConnection;
import btl.classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;
import java.io.IOException;

public class Guest {
    @FXML
    private TableView<Phong> tablePhong;
    @FXML
    private TableView<PhieuDatPhong> tableLichSu;
    @FXML
    private DatePicker dpCheckin, dpCheckout, dpNgaySinh;
    @FXML
    private TextField txtTenKhach, txtSDT, txtEmail, txtCMND, txtQuocTich, txtSoLuongDV;
    @FXML
    private ComboBox<String> cbxGioiTinh;

    // Khai báo các cột cho bảng phòng
    @FXML
    private TableColumn<Phong, Integer> colMaPhong;
    @FXML
    private TableColumn<Phong, String> colTenPhong;
    @FXML
    private TableColumn<Phong, Integer> colSoNguoi;
    @FXML
    private TableColumn<Phong, Double> colDonGia;
    @FXML
    private TableColumn<Phong, String> colTrangThai;
    @FXML
    private TableColumn<Phong, String> colLoaiPhong;
    @FXML
    private TableColumn<Phong, Integer> colTang;

    // Khai báo các cột cho bảng lịch sử
    @FXML
    private TableColumn<PhieuDatPhong, Integer> colMaDP;
    @FXML
    private TableColumn<PhieuDatPhong, String> colTenPhongLS;
    @FXML
    private TableColumn<PhieuDatPhong, Date> colNgayDat;
    @FXML
    private TableColumn<PhieuDatPhong, Date> colNgayTra;
    @FXML
    private TableColumn<PhieuDatPhong, String> colTrangThaiDP;
    @FXML
    private TableColumn<PhieuDatPhong, String> colGiaPhong;

    private DatabaseConnection db;

    @FXML
    public void initialize() {
        try {
            db = new DatabaseConnection();
            configureTableColumns();
            loadPhongTrong();
            setupGioiTinhComboBox();
        } catch (Exception e) {
            showError("Lỗi", "Không thể kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }

    private void setupGioiTinhComboBox() {
        cbxGioiTinh.setItems(FXCollections.observableArrayList("Nam", "Nữ"));
    }

    private void configureTableColumns() {
        // Cấu hình các cột cho bảng phòng
        colMaPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        colTenPhong.setCellValueFactory(new PropertyValueFactory<>("tenPhong"));
        colSoNguoi.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colLoaiPhong.setCellValueFactory(new PropertyValueFactory<>("maLP"));
        colTang.setCellValueFactory(new PropertyValueFactory<>("tang"));

        // Cấu hình các cột cho bảng lịch sử
        colMaDP.setCellValueFactory(new PropertyValueFactory<>("maPDP"));
        colTenPhongLS.setCellValueFactory(new PropertyValueFactory<>("tenP"));
        colNgayDat.setCellValueFactory(new PropertyValueFactory<>("ngayDatPhong"));
        colNgayTra.setCellValueFactory(new PropertyValueFactory<>("ngayTraPhong"));
        colTrangThaiDP.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colGiaPhong.setCellValueFactory(new PropertyValueFactory<>("giaPhong"));
    }

    private void loadPhongTrong() {
        try {
            ObservableList<Phong> danhSachPhong = FXCollections.observableArrayList(db.getPhongTrong());
            tablePhong.setItems(danhSachPhong);
        } catch (SQLException e) {
            showError("Lỗi", "Không thể tải danh sách phòng: " + e.getMessage());
        }
    }

    @FXML
    private void handleDatPhong() {
        Phong phongDaChon = tablePhong.getSelectionModel().getSelectedItem();
        if (phongDaChon == null) {
            showError("Lỗi", "Vui lòng chọn phòng");
            return;
        }

        if (!validateInput()) {
            return;
        }

        try {
            // Thêm khách hàng mới và lấy mã khách hàng
            int maKhach = db.themKhachHang(
                    txtTenKhach.getText(),
                    Date.valueOf(dpNgaySinh.getValue()),
                    txtSDT.getText(),
                    txtCMND.getText(),
                    txtEmail.getText(),
                    cbxGioiTinh.getValue(),
                    txtQuocTich.getText());

            // Thêm phiếu đặt phòng
            db.themPhieuDatPhong(
                    phongDaChon.getMaPhong(),
                    maKhach,
                    1, // Mã nhân viên mặc định
                    Timestamp.valueOf(dpCheckin.getValue().atStartOfDay()),
                    Timestamp.valueOf(dpCheckout.getValue().atStartOfDay()),
                    String.valueOf(phongDaChon.getDonGia()),
                    "0" // Tiền trả mặc định là 0
            );

            showSuccess("Thành công", "Đặt phòng thành công!");
            loadPhongTrong();
            clearFields();
        } catch (SQLException e) {
            showError("Lỗi", "Lỗi khi đặt phòng: " + e.getMessage());
        }
    }

    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();

        if (txtTenKhach.getText().isEmpty())
            errors.append("Vui lòng nhập họ tên\n");
        if (txtSDT.getText().isEmpty())
            errors.append("Vui lòng nhập số điện thoại\n");
        if (txtEmail.getText().isEmpty())
            errors.append("Vui lòng nhập email\n");
        if (txtCMND.getText().isEmpty())
            errors.append("Vui lòng nhập CMND\n");
        if (txtQuocTich.getText().isEmpty())
            errors.append("Vui lòng nhập quốc tịch\n");
        if (dpNgaySinh.getValue() == null)
            errors.append("Vui lòng chọn ngày sinh\n");
        if (cbxGioiTinh.getValue() == null)
            errors.append("Vui lòng chọn giới tính\n");
        if (dpCheckin.getValue() == null)
            errors.append("Vui lòng chọn ngày nhận phòng\n");
        if (dpCheckout.getValue() == null)
            errors.append("Vui lòng chọn ngày trả phòng\n");

        if (errors.length() > 0) {
            showError("Lỗi", errors.toString());
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtTenKhach.clear();
        txtSDT.clear();
        txtEmail.clear();
        txtCMND.clear();
        txtQuocTich.clear();
        dpNgaySinh.setValue(null);
        cbxGioiTinh.setValue(null);
        dpCheckin.setValue(null);
        dpCheckout.setValue(null);
    }

    @FXML
    private void datDichVu() {
        showInfo("Thông báo", "Chức năng đặt dịch vụ đang được phát triển");
    }

    @FXML
    private void yeuCauHuyGiaoDich() {
        PhieuDatPhong phieuChon = tableLichSu.getSelectionModel().getSelectedItem();
        if (phieuChon == null) {
            showError("Lỗi", "Vui lòng chọn phiếu đặt phòng cần hủy");
            return;
        }

        try {
            // Cập nhật trạng thái phiếu đặt phòng
            // TODO: Implement hủy giao dịch
            showSuccess("Thành công", "Đã gửi yêu cầu hủy đặt phòng!");
        } catch (Exception e) {
            showError("Lỗi", "Không thể hủy đặt phòng: " + e.getMessage());
        }
    }

    @FXML
    private void PressSignOut() throws IOException {
        App.setRoot("login");
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}