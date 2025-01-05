package btl.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import btl.classes.*;
import btl.database.ConnectionDB;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThongKe {
    private ObservableList<HoaDon> HoaDonsList = null;
    private ObservableList<PhieuDatPhong> phieuDatPhongsList = null;
    private ObservableList<PhieuDV> phieuDVSsList = null;

    private ConnectionDB db;

    private static Stage addDialogStage;

    private static String user;
    private static String password;

    private static Stage primaryStage;

    public void setpimaryStage(Stage pS) {
        primaryStage = pS;
    }

    private static int flag = 0;

    @FXML
    private AnchorPane anpTab;

    @FXML
    private TableView<HoaDon> tbl_HoaDon;

    @FXML
    private TableColumn<HoaDon, Integer> tb_MaHD;

    @FXML
    private TableColumn<HoaDon, String> tb_TenPhong;

    @FXML
    private TableColumn<HoaDon, Date> tb_NgayIn;

    @FXML
    private TableColumn<HoaDon, String> tb_NVlap;

    @FXML
    private TableColumn<HoaDon, String> tb_TongTien;

    @FXML
    private DatePicker dtpFromDate;

    @FXML
    private DatePicker dtpToDate;

    public void ShowHDDetails() throws IOException {
        addDialogStage = new Stage();
        addDialogStage.initOwner(primaryStage);
        addDialogStage.initModality(Modality.WINDOW_MODAL);

        FXMLLoader hdDetails = new FXMLLoader(getClass().getResource("HoaDonDetails.fxml"));
        Parent addsample2 = hdDetails.load();

        addDialogStage.setTitle("Chi tiết hóa đơn");
        Scene scene = new Scene(addsample2);

        addDialogStage.initStyle(StageStyle.UNDECORATED);
        HoaDonDetails controller = hdDetails.getController();
        controller.setPhieuDatPhongsList(phieuDatPhongsList);

        if (phieuDVSsList.size() == 0)
            System.out.println("Người này không sử dụng dịch vụ");
        else {
            System.out.println("Người này có sử dụng dịch vụ");
            // System.out.println(phieuDVSsList.get(0).getTenDV()+phieuDVSsList.get(0).getTienDV());
        }
        controller.setPhieuDVSsList(phieuDVSsList);
        controller.setpimaryStage(addDialogStage);
        controller.Init();
        addDialogStage.setScene(scene);
        addDialogStage.showAndWait();
    }

    @FXML
    void PressHD_Details(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        db = new ConnectionDB();
        if (tbl_HoaDon.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Hãy chọn hóa đơn cần xem !");
            return;
        }

        String sql = String.format("SELECT * FROM phieudatphong");

        PreparedStatement statement = db.conn.prepareStatement(sql);
        db.rs = statement.executeQuery();

        List<PhieuDatPhong> PDPList = new ArrayList<PhieuDatPhong>();

        while (db.rs.next()) {
            if (tbl_HoaDon.getSelectionModel().getSelectedItem().getMaKHACH() == db.rs.getInt("MaKhach")
                    && tbl_HoaDon.getSelectionModel().getSelectedItem().getMaP() == db.rs.getInt("MaPhong")) {
                PhieuDatPhong pdp = new PhieuDatPhong(db.rs.getInt("MaPDP"), db.rs.getInt("MaPhong"),
                        db.rs.getString("TenPhong"), db.rs.getString("TenLP"),
                        db.rs.getString("MaKhach"), db.rs.getDate("NgayDatPhong"),
                        db.rs.getDate("TraPhong"), db.rs.getBigDecimal("DonGiaPhong").toPlainString(),
                        db.rs.getBigDecimal("DonGiaThue").toPlainString());
                PDPList.add(pdp);
                System.out.println(pdp.getTenKHACH() + pdp.getTenP() + pdp.getTienTra());
            }

        }
        phieuDatPhongsList = FXCollections.observableArrayList(PDPList);

        db = new ConnectionDB();
        String sqlDV = "SELECT pdv.MaPhieuDV, pdv.MaPhong, pdv.MaDV, " +
                "dv.TenDV, dv.MaLoaiDV, ldv.TenLoaiDV, " +
                "dv.DonGiaDV as GiaDV, pdv.TienDV, pdv.SoLuong, pdv.NgaySD " +
                "FROM phieudv pdv " +
                "JOIN dichvu dv ON pdv.MaDV = dv.MaDV " +
                "JOIN loaidv ldv ON dv.MaLoaiDV = ldv.MaLoaiDV " +
                "WHERE pdv.MaPhong = ? " +
                "AND pdv.NgaySD BETWEEN ? AND ?";

        PreparedStatement stmtDV = db.conn.prepareStatement(sqlDV);
        stmtDV.setInt(1, tbl_HoaDon.getSelectionModel().getSelectedItem().getMaP());
        stmtDV.setDate(2, tbl_HoaDon.getSelectionModel().getSelectedItem().getNgayDP());
        stmtDV.setDate(3, tbl_HoaDon.getSelectionModel().getSelectedItem().getNgayInHD());

        ResultSet rsDV = stmtDV.executeQuery();
        List<PhieuDV> PDVList = new ArrayList<>();

        while (rsDV.next()) {
            PhieuDV pdv = new PhieuDV(
                    rsDV.getInt("MaPhieuDV"),
                    rsDV.getInt("MaPhong"),
                    rsDV.getString("MaDV"),
                    rsDV.getString("TenDV"),
                    rsDV.getString("MaLoaiDV"),
                    rsDV.getString("TenLoaiDV"),
                    rsDV.getBigDecimal("GiaDV").toPlainString(),
                    rsDV.getBigDecimal("TienDV").toPlainString(),
                    rsDV.getInt("SoLuong"),
                    rsDV.getDate("NgaySD"));
            PDVList.add(pdv);
            System.out.println("Đã thêm dịch vụ: " + pdv.getTenDV() + " - " + pdv.getTienDV());
        }

        phieuDVSsList = FXCollections.observableArrayList(PDVList);

        ShowHDDetails();

    }

    public void Init() {
        ShowQuest();
    }

    void ShowQuest() {
        try {
            dtpFromDate.setValue(LocalDate.now());
            dtpToDate.setValue(LocalDate.now().plusDays(30));
            db = new ConnectionDB();

            if (db == null)
                System.out.println("Unconnected");
            else
                System.out.println("Connected");

            String sql = "SELECT hd.*, p.TenPhong, nv.TenNV, pdp.NgayDatPhong " +
                    "FROM hoadon hd " +
                    "JOIN phong p ON hd.MaPhong = p.MaPhong " +
                    "JOIN nhanvien nv ON hd.MaNV = nv.MaNV " +
                    "JOIN phieudatphong pdp ON hd.MaPhong = pdp.MaPhong";
            // View_DetailsHD

            PreparedStatement statement = db.conn.prepareStatement(sql);
            db.rs = statement.executeQuery();

            List<HoaDon> HDList = new ArrayList<HoaDon>();

            while (db.rs.next()) {

                int MaHD = db.rs.getInt("MaHD");

                int MaP = db.rs.getInt("MaPhong");

                BigDecimal tongTien = db.rs.getBigDecimal("TongTien");

                Date ngayInHD = db.rs.getDate("NgayInHD");

                int MaNV = db.rs.getInt("MaNV");
                String TenNV = db.rs.getString("TenNV");
                String TenP = db.rs.getString("TenPhong");

                HoaDon hoaDon_temp = new HoaDon(MaHD, MaP, tongTien.toPlainString(), ngayInHD, MaNV, TenNV, TenP);
                HoaDon hoaDon = new HoaDon(hoaDon_temp, db.rs.getInt("MaKhach"));

                HDList.add(hoaDon);

            }

            HoaDonsList = FXCollections.observableArrayList(HDList);
            ConfigTable();
            tbl_HoaDon.setItems(HoaDonsList);

            //

        } catch (SQLException | ClassNotFoundException throwables) {

        }
    }

    void ConfigTable() {
        tb_MaHD.setCellValueFactory(new PropertyValueFactory<HoaDon, Integer>("MaHD"));
        tb_TenPhong.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("TenPhong"));
        tb_NgayIn.setCellValueFactory(new PropertyValueFactory<HoaDon, Date>("NgayInHD"));
        tb_NVlap.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("TenNV"));
        tb_TongTien.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("TongTien"));
    }

}