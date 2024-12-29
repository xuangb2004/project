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
            if (tbl_HoaDon.getSelectionModel().getSelectedItem().getMaKHACH() == db.rs.getInt("Mã Khách")
                    && tbl_HoaDon.getSelectionModel().getSelectedItem().getMaP() == db.rs.getInt("Mã phòng")) {
                PhieuDatPhong pdp = new PhieuDatPhong(db.rs.getInt("Mã PDP"), db.rs.getInt("Mã Phòng"),
                        db.rs.getString("Tên Phòng"), db.rs.getString("Tên LP"),
                        db.rs.getString("Khách"), db.rs.getDate("Ngày đặt"),
                        db.rs.getDate("Ngày trả"), db.rs.getBigDecimal("Giá Phòng").toPlainString(),
                        db.rs.getBigDecimal("Tiền trả").toPlainString());
                PDPList.add(pdp);
                System.out.println(pdp.getTenKHACH() + pdp.getTenP() + pdp.getTienTra());
            }

        }
        phieuDatPhongsList = FXCollections.observableArrayList(PDPList);

        db = new ConnectionDB();
        db.rs = db.stmt.executeQuery(
                "SELECT * FROM phieudv pdv JOIN DichVu dv ON pdv.MaDV = dv.MaDV JOIN LoaiDV ldv ON dv.MaLoaiDV = ldv.MaLoaiDV ");

        List<PhieuDV> PDVList_Temp = new ArrayList<PhieuDV>();

        while (db.rs.next()) {

            PhieuDV pdv = new PhieuDV(db.rs.getInt("MaPhieuDV"),
                    db.rs.getInt("MaPhong"),
                    db.rs.getString("MaDV"), db.rs.getString("TenDV"),
                    db.rs.getString("MaLoaiDV"), db.rs.getString("TenLoaiDV"),
                    db.rs.getBigDecimal("GiaDV").toPlainString(),
                    db.rs.getBigDecimal("TienDV").toPlainString(), db.rs.getInt("Soluong"),
                    db.rs.getDate("NgaySD"));
            PDVList_Temp.add(pdv);
        }
        List<PhieuDV> PDVList = new ArrayList<PhieuDV>();

        for (int i = 0; i < PDVList_Temp.size(); i++) {
            db = new ConnectionDB();
            db.rs = db.stmt.executeQuery("SELECT * FROM hoadon");
            // View_DetailsHD
            while (db.rs.next()) {

                if (tbl_HoaDon.getSelectionModel().getSelectedItem().getMaP() == db.rs.getInt("MaPhong")
                        && tbl_HoaDon.getSelectionModel().getSelectedItem().getNgayInHD()
                                .equals(db.rs.getDate("Traphong"))

                ) {

                    if ((PDVList_Temp.get(i).getNgaySD().toLocalDate().isBefore(db.rs.getDate("Traphong").toLocalDate())
                            || PDVList_Temp.get(i).getNgaySD().toLocalDate()
                                    .equals(db.rs.getDate("Traphong").toLocalDate()))
                            && (PDVList_Temp.get(i).getNgaySD().toLocalDate()
                                    .isAfter(db.rs.getDate("NgayDatPhong").toLocalDate())
                                    || PDVList_Temp.get(i).getNgaySD().toLocalDate()
                                            .equals(db.rs.getDate("NgayDatPhong").toLocalDate()))
                            && PDVList_Temp.get(i).getMaP() == db.rs.getInt("MaPhong")) {
                        PhieuDV pdv = new PhieuDV(PDVList_Temp.get(i).getMaPhieuDV(),
                                PDVList_Temp.get(i).getMaP(),
                                PDVList_Temp.get(i).getMaDV(),
                                PDVList_Temp.get(i).getTenDV(),
                                PDVList_Temp.get(i).getMaLDV(),
                                PDVList_Temp.get(i).getTenLDV(),
                                PDVList_Temp.get(i).getGiaDV(),
                                PDVList_Temp.get(i).getTienDV(),
                                PDVList_Temp.get(i).getSoluong(),
                                PDVList_Temp.get(i).getNgaySD());
                        PDVList.add(pdv);
                        System.out.println(pdv.getTenDV() + pdv.getTienDV());
                    }

                }
            }
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

            String sql = String.format("SELECT * FROM hoadon");
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
                String TenP = db.rs.getString("Tenphong");

                HoaDon hoaDon_temp = new HoaDon(MaHD, MaP, tongTien.toPlainString(), ngayInHD, MaNV, TenNV, TenP);
                HoaDon hoaDon = new HoaDon(hoaDon_temp, db.rs.getInt("MaKHACH"));

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
        tb_TenPhong.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("TenP"));
        tb_NgayIn.setCellValueFactory(new PropertyValueFactory<HoaDon, Date>("NgayInHD"));
        tb_NVlap.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("TenNV"));
        tb_TongTien.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("Tongtien"));
    }

}
