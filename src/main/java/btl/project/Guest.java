package btl.project;

import btl.database.ConnectionDB;
import btl.classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class Guest {
    @FXML
    private TableView<Phong> tablePhong;
    @FXML
    private TableView<PhieuDatPhong> tableLichSu;
    @FXML
    private DatePicker dpCheckin, dpCheckout;
    @FXML
    private Button btnDatPhong;

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
    private TableColumn<Phong, String> colMaLP;
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

    private ConnectionDB db;
    private int maKhach;

    public Guest() {
        try {
            db = new ConnectionDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        configureTableColumns();
        loadPhongTrong();
        loadLichSuDatPhong();
    }

    private void configureTableColumns() {
        colMaPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        colTenPhong.setCellValueFactory(new PropertyValueFactory<>("tenPhong"));
        colSoNguoi.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colMaLP.setCellValueFactory(new PropertyValueFactory<>("maLP"));
        colTang.setCellValueFactory(new PropertyValueFactory<>("tang"));

        colMaDP.setCellValueFactory(new PropertyValueFactory<>("maPDP"));
        colTenPhongLS.setCellValueFactory(new PropertyValueFactory<>("tenP"));
        colNgayDat.setCellValueFactory(new PropertyValueFactory<>("ngayDatPhong"));
        colNgayTra.setCellValueFactory(new PropertyValueFactory<>("ngayTraPhong"));
        colTrangThaiDP.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colGiaPhong.setCellValueFactory(new PropertyValueFactory<>("giaPhong"));
    }

    @FXML
    private void datPhong() {
        Phong phongChon = tablePhong.getSelectionModel().getSelectedItem();
        if (phongChon == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn phòng!");
            return;
        }

        LocalDate checkin = dpCheckin.getValue();
        LocalDate checkout = dpCheckout.getValue();

        if (checkin == null || checkout == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày đặt và trả phòng!");
            return;
        }

        try {
            String sql = "INSERT INTO phieudatphong (MaPhong, MaKhach, NgayDatPhong, NgayTraPhong, GiaPhong, TienTra) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = db.conn.prepareStatement(sql);
            stmt.setInt(1, phongChon.getMaPhong());
            stmt.setInt(2, maKhach);
            stmt.setDate(3, Date.valueOf(checkin));
            stmt.setDate(4, Date.valueOf(checkout));
            stmt.setString(5, String.valueOf(phongChon.getDonGia()));
            stmt.setString(6, "0"); // TienTra ban đầu = 0
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Đặt phòng thành công!");
            loadPhongTrong();
            loadLichSuDatPhong();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đặt phòng: " + e.getMessage());
        }
    }

    private void loadPhongTrong() {
        try {
            String sql = "SELECT * FROM phong WHERE TrangThai = 'Trống'";
            ResultSet rs = db.conn.createStatement().executeQuery(sql);

            ObservableList<Phong> danhSachPhong = FXCollections.observableArrayList();
            while (rs.next()) {
                Phong phong = new Phong(
                        rs.getInt("MaPhong"),
                        rs.getString("TenPhong"),
                        rs.getInt("SoNguoi"),
                        rs.getDouble("DonGia"),
                        rs.getString("TrangThai"),
                        rs.getString("MaLP"),
                        rs.getInt("Tang"));
                danhSachPhong.add(phong);
            }

            tablePhong.setItems(danhSachPhong);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách phòng: " + e.getMessage());
        }
    }

    private void loadLichSuDatPhong() {
        try {
            String sql = "SELECT pdp.*, p.TenP FROM phieudatphong pdp JOIN phong p ON pdp.MaPhong = p.MaPhong WHERE pdp.MaKhach = ?";
            PreparedStatement stmt = db.conn.prepareStatement(sql);
            stmt.setInt(1, maKhach);
            ResultSet rs = stmt.executeQuery();

            ObservableList<PhieuDatPhong> lichSu = FXCollections.observableArrayList();
            while (rs.next()) {
                PhieuDatPhong phieu = new PhieuDatPhong(
                        rs.getInt("MaPDP"),
                        rs.getInt("MaPhong"),
                        rs.getString("TenP"),
                        rs.getString("MaLP"),
                        rs.getString("TenLP"),
                        rs.getInt("MaKhach"),
                        rs.getString("TenKhach"),
                        rs.getDate("NgayDatPhong"),
                        rs.getDate("NgayTraPhong"),
                        rs.getString("GiaPhong"),
                        rs.getString("TienTra"));
                lichSu.add(phieu);
            }

            tableLichSu.setItems(lichSu);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải lịch sử đặt phòng: " + e.getMessage());
        }
    }

    @FXML
    private void datDichVu() {
        // Xử lý đặt dịch vụ ở đây
        JOptionPane.showMessageDialog(null, "Chức năng đặt dịch vụ đang được phát triển");
    }

    @FXML
    private void yeuCauHuyGiaoDich() {
        PhieuDatPhong phieuChon = tableLichSu.getSelectionModel().getSelectedItem();
        if (phieuChon == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn phiếu đặt phòng cần hủy!");
            return;
        }

        int xacNhan = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc muốn yêu cầu hủy đặt phòng này?",
                "Xác nhận hủy",
                JOptionPane.YES_NO_OPTION);

        if (xacNhan == JOptionPane.YES_OPTION) {
            try {
                String sql = "UPDATE phieudatphong SET TrangThai = 'Yêu cầu hủy' WHERE MaPDP = ?";
                PreparedStatement stmt = db.conn.prepareStatement(sql);
                stmt.setInt(1, phieuChon.getMaPDP());
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Đã gửi yêu cầu hủy đặt phòng!");
                loadLichSuDatPhong();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi hủy đặt phòng: " + e.getMessage());
            }
        }
    }
}