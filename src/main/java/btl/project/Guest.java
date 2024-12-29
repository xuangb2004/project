package btl.project;

import btl.classes.Khach;
import btl.classes.Phong;
import btl.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Guest implements Initializable {
    @FXML
    private AnchorPane anpTab;
    @FXML
    private TableView<Phong> tablePhong;
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
    private DatePicker dpNgaySinh;
    @FXML
    private ComboBox<String> cbxGender;
    @FXML
    private DatePicker dpCheckin;
    @FXML
    private DatePicker dpCheckout;

    private DatabaseConnection db;
    private int maKhach;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            db = new DatabaseConnection();
            setupGenderComboBox();
            setupTableColumns();
            loadPhongTrong();
        } catch (SQLException e) {
            showError("Lỗi", "Không thể kết nối đến database: " + e.getMessage());
        }
    }

    private void setupGenderComboBox() {
        cbxGender.setItems(FXCollections.observableArrayList("Nam", "Nữ"));
    }

    private void setupTableColumns() {
        colMaPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        colTenPhong.setCellValueFactory(new PropertyValueFactory<>("tenPhong"));
        colSoNguoi.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colLoaiPhong.setCellValueFactory(new PropertyValueFactory<>("maLP"));
        colTang.setCellValueFactory(new PropertyValueFactory<>("tang"));
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

            // Tạo phiếu đặt phòng
            String insertPhieuDatPhongQuery = "INSERT INTO PhieuDatPhong (MaPhong, MaKhach, NgayDat, TraPhong, TongTien) "
                    +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = db.getConnection().prepareStatement(insertPhieuDatPhongQuery)) {
                pstmt.setInt(1, phongDaChon.getMaPhong());
                pstmt.setInt(2, maKhach);
                pstmt.setDate(3, Date.valueOf(dpCheckin.getValue()));
                pstmt.setDate(4, Date.valueOf(dpCheckout.getValue()));
                pstmt.setDouble(5, phongDaChon.getDonGia());

                pstmt.executeUpdate();
            }

            // Cập nhật trạng thái phòng
            String updatePhongQuery = "UPDATE Phong SET TrangThai = 'Đã đặt' WHERE MaPhong = ?";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(updatePhongQuery)) {
                pstmt.setInt(1, phongDaChon.getMaPhong());
                pstmt.executeUpdate();
            }

            showSuccess("Thành công", "Đặt phòng thành công!");
            loadPhongTrong(); // Tải lại danh sách phòng trống
            clearFields();
        } catch (SQLException e) {
            showError("Lỗi", "Không thể đặt phòng: " + e.getMessage());
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
