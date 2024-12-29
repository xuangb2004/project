package btl.project;

import btl.classes.NhanVien;
import btl.database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class QL_NV {

    @FXML
    private TextField txtID_NV;
    @FXML
    private TextField txtName_NV;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtCMND_NV;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSalary_NV;
    @FXML
    private TextField txtSearch;

    @FXML
    private DatePicker dtpWasBorn_NV;

    @FXML
    private ComboBox<String> cbxCV;
    @FXML
    private ComboBox<String> cbxCLV;

    @FXML
    private RadioButton rbWorking;
    @FXML
    private RadioButton rbStopped;
    @FXML
    private RadioButton rbNam;
    @FXML
    private RadioButton rbNu;

    @FXML
    private ImageView imgNV;

    @FXML
    private TableView<NhanVien> tblNV;
    @FXML
    private TableColumn<NhanVien, Integer> Index_table;
    @FXML
    private TableColumn<NhanVien, Integer> IDtable;
    @FXML
    private TableColumn<NhanVien, String> NameNVtable;
    @FXML
    private TableColumn<NhanVien, String> WasBorn_table;
    @FXML
    private TableColumn<NhanVien, String> CMND_table;
    @FXML
    private TableColumn<NhanVien, String> SDT_table;
    @FXML
    private TableColumn<NhanVien, String> Email_table;
    @FXML
    private TableColumn<NhanVien, String> Role_table;
    @FXML
    private TableColumn<NhanVien, Double> Salary_table;
    @FXML
    private TableColumn<NhanVien, String> Join_table;
    @FXML
    private TableColumn<NhanVien, String> TenCLV_table;
    @FXML
    private TableColumn<NhanVien, String> Status_table;

    private ObservableList<NhanVien> nhanVienList;
    private Connection conn;

    @FXML
    public void initialize() {
        connectDB();
        configureTable();
        loadEmployees();
        loadComboBoxData();
        configureControls();
    }

    private void connectDB() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (RuntimeException e) {
            e.printStackTrace();
            showAlert("Lỗi kết nối database", e.getMessage());
        }
    }

    private void configureTable() {
        Index_table.setCellValueFactory(new PropertyValueFactory<>("stt"));
        IDtable.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        NameNVtable.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
        WasBorn_table.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        CMND_table.setCellValueFactory(new PropertyValueFactory<>("cmnd"));
        SDT_table.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        Email_table.setCellValueFactory(new PropertyValueFactory<>("email"));
        Role_table.setCellValueFactory(new PropertyValueFactory<>("tenChucVu"));
        Salary_table.setCellValueFactory(new PropertyValueFactory<>("luongCoBan"));
        Join_table.setCellValueFactory(new PropertyValueFactory<>("ngayGiaNhap"));
        TenCLV_table.setCellValueFactory(new PropertyValueFactory<>("tenCLV"));
        Status_table.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
    }

    private void loadEmployees() {
        try {
            String sql = "SELECT nv.*, cv.TenChucVu, clv.TenCLV " +
                    "FROM nhanvien nv " +
                    "LEFT JOIN chucvu cv ON nv.MaCV = cv.MaCV " +
                    "LEFT JOIN calamviec clv ON nv.MaCLV = clv.MaCLV";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            ObservableList<NhanVien> list = FXCollections.observableArrayList();
            int stt = 1;
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setStt(stt++);
                nv.setMaNV(rs.getInt("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setCmnd(rs.getString("CMND"));
                nv.setSdt(rs.getString("SDT"));
                nv.setLuongCoBan(rs.getDouble("LuongCoBan"));
                nv.setTrangThai(rs.getString("TrangThai"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setEmail(rs.getString("Email"));
                nv.setNgayGiaNhap(rs.getDate("NgayGiaNhap"));
                nv.setMaCLV(rs.getInt("MaCLV"));
                nv.setMaCV(rs.getInt("MaCV"));
                nv.setTenChucVu(rs.getString("TenChucVu"));
                nv.setTenCLV(rs.getString("TenCLV"));
                list.add(nv);
            }
            nhanVienList = list;
            tblNV.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi tải dữ liệu", e.getMessage());
        }
    }

    private int generateNextId() {
        try {
            String sql = "SELECT MAX(MaNV) as MaxId FROM nhanvien";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("MaxId") + 1;
            }
            return 1; // Nếu chưa có nhân viên nào
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tạo mã nhân viên mới");
            return -1;
        }
    }

    private void loadComboBoxData() {
        try {
            // Load chức vụ
            String sqlCV = "SELECT * FROM chucvu";
            PreparedStatement stmtCV = conn.prepareStatement(sqlCV);
            ResultSet rsCV = stmtCV.executeQuery();

            ObservableList<String> cvList = FXCollections.observableArrayList();
            while (rsCV.next()) {
                cvList.add(rsCV.getString("tenChucVu"));
            }
            cbxCV.setItems(cvList);

            // Load ca làm việc
            String sqlCLV = "SELECT * FROM calamviec";
            PreparedStatement stmtCLV = conn.prepareStatement(sqlCLV);
            ResultSet rsCLV = stmtCLV.executeQuery();

            ObservableList<String> clvList = FXCollections.observableArrayList();
            while (rsCLV.next()) {
                String thongTinCLV = rsCLV.getString("TenCLV") +
                        " (" + rsCLV.getTime("GioBatDau") + " - " +
                        rsCLV.getTime("GioKetThuc") + ")";
                clvList.add(thongTinCLV);
            }
            cbxCLV.setItems(clvList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi tải dữ liệu combobox", e.getMessage());
        }
    }

    private void configureControls() {
        // Cấu hình RadioButton
        ToggleGroup genderGroup = new ToggleGroup();
        rbNam.setToggleGroup(genderGroup);
        rbNu.setToggleGroup(genderGroup);

        ToggleGroup statusGroup = new ToggleGroup();
        rbWorking.setToggleGroup(statusGroup);
        rbStopped.setToggleGroup(statusGroup);

        // Cấu hình DatePicker
        dtpWasBorn_NV.setValue(LocalDate.now());
    }

    @FXML
    void PressAdd(ActionEvent event) {
        if (validateInput()) {
            try {
                int newId = generateNextId();
                if (newId > 0) {
                    txtID_NV.setText(String.valueOf(newId));
                    NhanVien nv = getNhanVienFromInput();
                    saveNhanVien(nv);
                    loadEmployees();
                    clearForm();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Lỗi", "Không thể thêm nhân viên: " + e.getMessage());
            }
        }
    }

    @FXML
    void PressEdit(ActionEvent event) {
        if (validateInput()) {
            NhanVien nv = getNhanVienFromInput();
            updateNhanVien(nv);
            loadEmployees();
        }
    }

    @FXML
    void PressClear(ActionEvent event) {
        clearForm();
    }

    @FXML
    void SearchNV(KeyEvent event) {
        String keyword = txtSearch.getText().toLowerCase();
        if (keyword.isEmpty()) {
            loadEmployees();
        } else {
            filterEmployees(keyword);
        }
    }

    @FXML
    void ShowInfoBacktoText(MouseEvent event) {
        NhanVien selected = tblNV.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtID_NV.setText(String.valueOf(selected.getMaNV()));
            txtName_NV.setText(selected.getTenNV());
            txtCMND_NV.setText(selected.getCmnd());
            txtPhoneNumber.setText(selected.getSdt());
            txtEmail.setText(selected.getEmail());
            txtSalary_NV.setText(String.valueOf(selected.getLuongCoBan()));

            dtpWasBorn_NV.setValue(LocalDate.parse(selected.getNgaySinh()));

            rbNam.setSelected(selected.getGioiTinh().equals("Nam"));
            rbNu.setSelected(selected.getGioiTinh().equals("Nữ"));

            rbWorking.setSelected(selected.getTrangThai().equals("Đang làm việc"));
            rbStopped.setSelected(selected.getTrangThai().equals("Đã nghỉ việc"));

            // Set combobox values
            setComboBoxValues(selected);
        }
    }

    private void setComboBoxValues(NhanVien nv) {
        try {
            // Set chức vụ
            String sqlCV = "SELECT tenChucVu FROM chucvu WHERE MaCV = ?";
            PreparedStatement stmtCV = conn.prepareStatement(sqlCV);
            stmtCV.setInt(1, nv.getMaCV());
            ResultSet rsCV = stmtCV.executeQuery();
            if (rsCV.next()) {
                cbxCV.setValue(rsCV.getString("tenChucVu"));
            }

            // Set ca làm việc
            String sqlCLV = "SELECT * FROM calamviec WHERE MaCLV = ?";
            PreparedStatement stmtCLV = conn.prepareStatement(sqlCLV);
            stmtCLV.setInt(1, nv.getMaCLV());
            ResultSet rsCLV = stmtCLV.executeQuery();
            if (rsCLV.next()) {
                String thongTinCLV = rsCLV.getString("TenCLV") +
                        " (" + rsCLV.getTime("GioBatDau") + " - " +
                        rsCLV.getTime("GioKetThuc") + ")";
                cbxCLV.setValue(thongTinCLV);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể load thông tin chi tiết");
        }
    }

    private boolean validateInput() {
        if (txtName_NV.getText().isEmpty() ||
                txtCMND_NV.getText().isEmpty() ||
                txtPhoneNumber.getText().isEmpty() ||
                txtEmail.getText().isEmpty() ||
                txtSalary_NV.getText().isEmpty() ||
                cbxCV.getValue() == null ||
                cbxCLV.getValue() == null ||
                (!rbNam.isSelected() && !rbNu.isSelected()) ||
                (!rbWorking.isSelected() && !rbStopped.isSelected())) {

            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin!");
            return false;
        }
        return true;
    }

    private NhanVien getNhanVienFromInput() {
        NhanVien nv = new NhanVien();
        if (!txtID_NV.getText().isEmpty()) {
            nv.setMaNV(Integer.parseInt(txtID_NV.getText()));
        }
        nv.setTenNV(txtName_NV.getText());
        nv.setNgaySinh(dtpWasBorn_NV.getValue().toString());
        nv.setCmnd(txtCMND_NV.getText());
        nv.setSdt(txtPhoneNumber.getText());
        nv.setLuongCoBan(Double.parseDouble(txtSalary_NV.getText()));
        nv.setTrangThai(rbWorking.isSelected() ? "Đang làm việc" : "Đã nghỉ việc");
        nv.setGioiTinh(rbNam.isSelected() ? "Nam" : "Nữ");
        nv.setEmail(txtEmail.getText());
        nv.setMaCLV(getCLVId(cbxCLV.getValue()));
        nv.setMaCV(getCVId(cbxCV.getValue()));
        return nv;
    }

    private void saveNhanVien(NhanVien nv) {
        try {
            String sql = "INSERT INTO nhanvien (MaNV, TenNV, NgaySinh, CMND, SDT, LuongCoBan, TrangThai, GioiTinh, Email, NgayGiaNhap, MaCLV, MaCV) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, nv.getMaNV());
            stmt.setString(2, nv.getTenNV());
            stmt.setString(3, nv.getNgaySinh());
            stmt.setString(4, nv.getCmnd());
            stmt.setString(5, nv.getSdt());
            stmt.setDouble(6, nv.getLuongCoBan());
            stmt.setString(7, nv.getTrangThai());
            stmt.setString(8, nv.getGioiTinh());
            stmt.setString(9, nv.getEmail());
            stmt.setInt(10, nv.getMaCLV());
            stmt.setInt(11, nv.getMaCV());

            int result = stmt.executeUpdate();
            if (result > 0) {
                showAlert("Thành công", "Thêm nhân viên thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể thêm nhân viên: " + e.getMessage());
        }
    }

    private void updateNhanVien(NhanVien nv) {
        try {
            String sql = "UPDATE nhanvien SET TenNV=?, NgaySinh=?, CMND=?, SDT=?, LuongCoBan=?, " +
                    "TrangThai=?, GioiTinh=?, Email=?, MaCLV=?, MaCV=? WHERE MaNV=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, nv.getTenNV());
            stmt.setString(2, nv.getNgaySinh());
            stmt.setString(3, nv.getCmnd());
            stmt.setString(4, nv.getSdt());
            stmt.setDouble(5, nv.getLuongCoBan());
            stmt.setString(6, nv.getTrangThai());
            stmt.setString(7, nv.getGioiTinh());
            stmt.setString(8, nv.getEmail());
            stmt.setInt(9, nv.getMaCLV());
            stmt.setInt(10, nv.getMaCV());
            stmt.setInt(11, nv.getMaNV());

            stmt.executeUpdate();
            showAlert("Thông báo", "Cập nhật nhân viên thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể cập nhật nhân viên: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtID_NV.clear();
        txtName_NV.clear();
        txtCMND_NV.clear();
        txtPhoneNumber.clear();
        txtEmail.clear();
        txtSalary_NV.clear();
        dtpWasBorn_NV.setValue(LocalDate.now());
        cbxCV.getSelectionModel().clearSelection();
        cbxCLV.getSelectionModel().clearSelection();
        rbNam.setSelected(false);
        rbNu.setSelected(false);
        rbWorking.setSelected(false);
        rbStopped.setSelected(false);
    }

    private void filterEmployees(String keyword) {
        try {
            String sql = "SELECT * FROM nhanvien WHERE TenNV LIKE ? OR CMND LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();

            ObservableList<NhanVien> filteredList = FXCollections.observableArrayList();
            int stt = 1;
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setStt(stt++);
                nv.setMaNV(rs.getInt("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setCmnd(rs.getString("CMND"));
                nv.setSdt(rs.getString("SDT"));
                nv.setLuongCoBan(rs.getDouble("LuongCoBan"));
                nv.setTrangThai(rs.getString("TrangThai"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setEmail(rs.getString("Email"));
                nv.setMaCLV(rs.getInt("MaCLV"));
                nv.setMaCV(rs.getInt("MaCV"));
                filteredList.add(nv);
            }
            tblNV.setItems(filteredList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tìm kiếm: " + e.getMessage());
        }
    }

    private int getCLVId(String tenCLV) {
        try {
            String sql = "SELECT MaCLV FROM calamviec WHERE TenCLV = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tenCLV.split(" \\(")[0]); // Lấy tên CLV trước dấu (
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("MaCLV");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getCVId(String tenChucVu) {
        try {
            String sql = "SELECT MaCV FROM chucvu WHERE tenChucVu = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tenChucVu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("MaCV");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void validateNumber(KeyEvent event) {
        String input = event.getCharacter();
        if (!input.matches("[0-9]")) {
            event.consume();
        }
    }
}