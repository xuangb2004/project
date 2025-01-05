package btl.project;

import btl.classes.Khach;
import btl.classes.Phong;
import btl.classes.DichVu;
import btl.classes.PhieuDV;
import btl.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Guest implements Initializable {
    @FXML
    private AnchorPane anpTab;

    // TableViews
    @FXML
    private TableView<Phong> tablePhong;
    @FXML
    private TableView<DichVu> tableDichVu;
    @FXML
    private TableView<PhieuDV> tableGiaoDich;

    // Columns cho bảng Phòng
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

    // Columns cho bảng Dịch vụ
    @FXML
    private TableColumn<DichVu, String> colMaDV;
    @FXML
    private TableColumn<DichVu, String> colTenDV;
    @FXML
    private TableColumn<DichVu, Double> colGiaDV;
    @FXML
    private TableColumn<DichVu, String> colMoTaDV;

    // Columns cho bảng Giao dịch
    @FXML
    private TableColumn<PhieuDV, Integer> colMaGD;
    @FXML
    private TableColumn<PhieuDV, String> colLoaiGD;
    @FXML
    private TableColumn<PhieuDV, Date> colNgayGD;
    @FXML
    private TableColumn<PhieuDV, Double> colTongTien;
    @FXML
    private TableColumn<PhieuDV, String> colTrangThaiGD;

    // Form controls
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtCustomerPhone;
    @FXML
    private TextField txtCustomerEmail;
    @FXML
    private TextField txtCustomerCMND;
    @FXML
    private TextField txtCustomerCountry;
    @FXML
    private TextField txtSoLuongDV;
    @FXML
    private DatePicker dpNgaySinh;
    @FXML
    private ComboBox<String> cbxGender;
    @FXML
    private DatePicker dpCheckin;
    @FXML
    private DatePicker dpCheckout;

    private DatabaseConnection db;
    private int maKhach;
    private int maPhong;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            db = new DatabaseConnection();
            setupGenderComboBox();
            setupTableColumns();
            loadPhongTrong();
            loadDichVu();
            loadPhieuDV();
        } catch (SQLException e) {
            showError("Lỗi", "Không thể kết nối đến database: " + e.getMessage());
        }
    }

    private void setupGenderComboBox() {
        cbxGender.setItems(FXCollections.observableArrayList("Nam", "Nữ"));
    }

    private void setupTableColumns() {
        // Setup columns cho bảng Phòng
        colMaPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        colTenPhong.setCellValueFactory(new PropertyValueFactory<>("tenPhong"));
        colSoNguoi.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colLoaiPhong.setCellValueFactory(new PropertyValueFactory<>("maLP"));
        colTang.setCellValueFactory(new PropertyValueFactory<>("tang"));

        // Setup columns cho bảng Dịch vụ
        colMaDV.setCellValueFactory(new PropertyValueFactory<>("maDV"));
        colTenDV.setCellValueFactory(new PropertyValueFactory<>("tenDV"));
        colGiaDV.setCellValueFactory(new PropertyValueFactory<>("donGiaDV"));
        colMoTaDV.setCellValueFactory(new PropertyValueFactory<>("maLoaiDV"));

        // Setup columns cho bảng Phiếu dịch vụ
        colMaGD.setCellValueFactory(new PropertyValueFactory<>("maPhieuDV"));
        colLoaiGD.setCellValueFactory(new PropertyValueFactory<>("tenDV"));
        colNgayGD.setCellValueFactory(new PropertyValueFactory<>("ngaySD"));
        colTongTien.setCellValueFactory(new PropertyValueFactory<>("tienDV"));
        colTrangThaiGD.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
    }

    private void loadPhongTrong() throws SQLException {
        String query = "SELECT * FROM Phong WHERE TrangThai = N'Trống'";
        ObservableList<Phong> phongList = FXCollections.observableArrayList();

        try (Statement stmt = db.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Phong phong = new Phong(
                        rs.getInt("MaPhong"),
                        rs.getString("TenPhong"),
                        rs.getInt("SoNguoi"),
                        rs.getDouble("DonGia"),
                        rs.getString("TrangThai"),
                        rs.getString("MaLP"),
                        rs.getInt("Tang"));
                phongList.add(phong);
            }
        }
        tablePhong.setItems(phongList);
    }

    private void loadDichVu() throws SQLException {
        String query = "SELECT * FROM DichVu";
        ObservableList<DichVu> dichVuList = FXCollections.observableArrayList();

        try (Statement stmt = db.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                DichVu dichVu = new DichVu(
                        rs.getString("MaDV"),
                        rs.getString("TenDV"),
                        rs.getDouble("DonGiaDV"),
                        rs.getString("MaLoaiDV"));
                dichVuList.add(dichVu);
            }
        }
        tableDichVu.setItems(dichVuList);
    }

    private void loadPhieuDV() throws SQLException {
        String query = "SELECT pdv.*, dv.TenDV FROM PhieuDV pdv JOIN DichVu dv ON pdv.MaDV = dv.MaDV WHERE pdv.MaP = ?";
        System.out.println("Query: " + query);
        System.out.println("MaPhong: " + maPhong);
        ObservableList<PhieuDV> phieuDVList = FXCollections.observableArrayList();

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, maPhong);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PhieuDV phieuDV = new PhieuDV(
                        rs.getInt("MaPhieuDV"),
                        rs.getInt("MaP"),
                        rs.getString("MaDV"),
                        rs.getString("TienDV"),
                        rs.getInt("Soluong"),
                        rs.getDate("NgaySD"));
                phieuDVList.add(phieuDV);
            }
        }
        tableGiaoDich.setItems(phieuDVList);
    }

    @FXML
    private void datPhong() {
        Phong phongDaChon = tablePhong.getSelectionModel().getSelectedItem();
        if (phongDaChon == null) {
            showError("Lỗi", "Vui lòng chọn phòng");
            return;
        }

        if (!validateInput()) {
            return;
        }

        try {
            // Lưu thông tin khách hàng
            String insertKhachQuery = "INSERT INTO Khach (TenKhach, NgaySinh, SDT, CMND, Email, GioiTinh, QuocTich) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = db.getConnection().prepareStatement(insertKhachQuery,
                    Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, txtCustomerName.getText());
                pstmt.setDate(2, Date.valueOf(dpNgaySinh.getValue()));
                pstmt.setString(3, txtCustomerPhone.getText());
                pstmt.setString(4, txtCustomerCMND.getText());
                pstmt.setString(5, txtCustomerEmail.getText());
                pstmt.setString(6, cbxGender.getValue());
                pstmt.setString(7, txtCustomerCountry.getText());

                pstmt.executeUpdate();

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        maKhach = generatedKeys.getInt(1);
                    }
                }
            }

            // Cập nhật trạng thái phòng và lưu mã phòng
            String updatePhongQuery = "UPDATE Phong SET TrangThai = N'Đã đặt' WHERE MaPhong = ?";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(updatePhongQuery)) {
                maPhong = phongDaChon.getMaPhong();
                pstmt.setInt(1, maPhong);
                pstmt.executeUpdate();
            }

            showSuccess("Thành công", "Đặt phòng thành công!");
            loadPhongTrong();
            loadPhieuDV();
            clearFields();
        } catch (SQLException e) {
            showError("Lỗi", "Không thể đặt phòng: " + e.getMessage());
        }
    }

    @FXML
    private void datDichVu() {
        DichVu dichVuDaChon = tableDichVu.getSelectionModel().getSelectedItem();
        if (dichVuDaChon == null) {
            showError("Lỗi", "Vui lòng chọn dịch vụ");
            return;
        }

        if (txtSoLuongDV.getText().isEmpty()) {
            showError("Lỗi", "Vui lòng nhập số lượng");
            return;
        }

        try {
            int soLuong = Integer.parseInt(txtSoLuongDV.getText());
            double tongTien = soLuong * dichVuDaChon.getDonGiaDV();
            String tienDV = String.valueOf(tongTien);

            String insertPhieuDVQuery = "INSERT INTO PhieuDV (MaP, MaDV, TienDV, Soluong, NgaySD) " +
                    "VALUES (?, ?, ?, ?, CURDATE())";

            try (PreparedStatement pstmt = db.getConnection().prepareStatement(insertPhieuDVQuery)) {
                pstmt.setInt(1, maPhong);
                pstmt.setString(2, dichVuDaChon.getMaDV());
                pstmt.setString(3, tienDV);
                pstmt.setInt(4, soLuong);
                pstmt.executeUpdate();
            }

            showSuccess("Thành công", "Đặt dịch vụ thành công!");
            loadPhieuDV();
            txtSoLuongDV.clear();
        } catch (SQLException e) {
            showError("Lỗi", "Không thể đặt dịch vụ: " + e.getMessage());
        } catch (NumberFormatException e) {
            showError("Lỗi", "Số lượng không hợp lệ");
        }
    }

    @FXML
    private void yeuCauHuyGiaoDich() {
        PhieuDV phieuDaChon = tableGiaoDich.getSelectionModel().getSelectedItem();
        if (phieuDaChon == null) {
            showError("Lỗi", "Vui lòng chọn phiếu cần hủy");
            return;
        }

        try {
            String updateQuery = "DELETE FROM PhieuDV WHERE MaPhieuDV = ?";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(updateQuery)) {
                pstmt.setInt(1, phieuDaChon.getMaPhieuDV());
                pstmt.executeUpdate();
            }

            showSuccess("Thành công", "Đã hủy phiếu dịch vụ!");
            loadPhieuDV();
        } catch (SQLException e) {
            showError("Lỗi", "Không thể hủy phiếu: " + e.getMessage());
        }
    }

    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();

        if (txtCustomerName.getText().isEmpty())
            errors.append("Vui lòng nhập tên khách hàng\n");
        if (txtCustomerPhone.getText().isEmpty())
            errors.append("Vui lòng nhập số điện thoại\n");
        if (txtCustomerCMND.getText().isEmpty())
            errors.append("Vui lòng nhập CMND\n");
        if (dpNgaySinh.getValue() == null)
            errors.append("Vui lòng chọn ngày sinh\n");
        if (cbxGender.getValue() == null)
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
        txtCustomerName.clear();
        txtCustomerPhone.clear();
        txtCustomerEmail.clear();
        txtCustomerCMND.clear();
        txtCustomerCountry.clear();
        dpNgaySinh.setValue(null);
        cbxGender.setValue(null);
        dpCheckin.setValue(null);
        dpCheckout.setValue(null);
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
}