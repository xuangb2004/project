package btl.project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import btl.classes.*;
import btl.database.ConnectionDB;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class HoaDonDetails {

    private ObservableList<HoaDon> HoaDonsList = null;

    private ObservableList<PhieuDatPhong> phieuDatPhongsList = null;

    public void setPhieuDatPhongsList(ObservableList<PhieuDatPhong> phieuDatPhongsList) {
        this.phieuDatPhongsList = phieuDatPhongsList;
    }

    private ObservableList<PhieuDV> phieuDVSsList = null;

    public void setPhieuDVSsList(ObservableList<PhieuDV> phieuDVSsList) {
        this.phieuDVSsList = phieuDVSsList;
    }

    private ConnectionDB db;

    private static Stage addDialogStage;

    private static Stage primaryStage;

    public void setpimaryStage(Stage pS) {
        primaryStage = pS;
    }

    private static int flag = 0;

    @FXML
    private TableView<PhieuDatPhong> tbl_RoomDetails;

    @FXML
    private TableColumn<PhieuDatPhong, String> tb_TenPhong;

    @FXML
    private TableColumn<PhieuDatPhong, String> tb_QName;

    @FXML
    private TableColumn<PhieuDatPhong, String> tb_RoomPrice;

    @FXML
    private TableColumn<PhieuDatPhong, String> tb_MoneyPaid;

    @FXML
    private TableColumn<PhieuDatPhong, Date> tb_NgayDatPhong;

    @FXML
    private TableColumn<PhieuDatPhong, Date> tb_NgayTraPhong;

    @FXML
    private Tab tab_ServicesDetails;

    @FXML
    private Tab tab_RoomDetails;

    @FXML
    private TextField txtTienPhong;
    long tienPhong = 0;

    @FXML
    private TextField txtTienDV;
    long tienDV = 0;

    @FXML
    private TextField txtTongTien;
    long TongTien = 0;

    @FXML
    private TableView<PhieuDV> tbl_DVDetails;

    @FXML
    private TableColumn<PhieuDV, String> tb_tenDV;

    @FXML
    private TableColumn<PhieuDV, String> tb_LoaiDV;

    @FXML
    private TableColumn<PhieuDV, String> tb_DVPrice;

    @FXML
    private TableColumn<PhieuDV, Integer> tb_SL;

    @FXML
    private TableColumn<PhieuDV, String> tb_TongTienDV;

    @FXML
    private TableColumn<PhieuDV, Date> tb_DateUsed;

    @FXML
    private DatePicker dtpFromDate;

    @FXML
    private DatePicker dtpToDate;

    @FXML
    void PressCancel(ActionEvent event) {
        primaryStage.close();
    }

    public long TienDatPhong(long tienPhong) {
        for (int i = 0; i < phieuDatPhongsList.size(); i++) {
            tienPhong = new BigDecimal(phieuDatPhongsList.get(i).getTienTra()).longValue();
        }
        return tienPhong;
    }

    public long TienDV(long tongtienDV) {
        tongtienDV = 0;
        for (int i = 0; i < phieuDVSsList.size(); i++) {
            long tienDV = new BigDecimal(phieuDVSsList.get(i).getTienDV()).longValue();
            tongtienDV = tongtienDV + tienDV;
        }
        return tongtienDV;
    }

    public void Init() {
        dtpFromDate.setValue(LocalDate.now());
        dtpToDate.setValue(LocalDate.now().plusDays(30));
        ShowHDDetails();
        txtTienPhong.setText(BigDecimal.valueOf(TienDatPhong(tienPhong)).toPlainString());
        txtTienDV.setText(BigDecimal.valueOf(TienDV(tienDV)).toPlainString());
        txtTongTien.setText((BigDecimal.valueOf(TienDatPhong(tienPhong) + TienDV(tienDV)).toPlainString()));
    }

    void ShowHDDetails() {
        ConfigTable();

        tbl_DVDetails.setItems(phieuDVSsList);

        tbl_RoomDetails.setItems(phieuDatPhongsList);
        if (phieuDVSsList.size() == 0)
            tab_ServicesDetails.setDisable(true);
    }

    void ConfigTable() {
        tb_TenPhong.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong, String>("tenP"));
        tb_QName.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong, String>("tenKHACH"));
        tb_RoomPrice.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong, String>("GiaPhong"));
        tb_MoneyPaid.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong, String>("TienTra"));
        tb_NgayDatPhong.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong, Date>("NgayDatPhong"));
        tb_NgayTraPhong.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong, Date>("NgayTraPhong"));

        tb_tenDV.setCellValueFactory(new PropertyValueFactory<PhieuDV, String>("TenDV"));
        tb_LoaiDV.setCellValueFactory(new PropertyValueFactory<PhieuDV, String>("TenLDV"));
        tb_DVPrice.setCellValueFactory(new PropertyValueFactory<PhieuDV, String>("GiaDV"));
        tb_SL.setCellValueFactory(new PropertyValueFactory<PhieuDV, Integer>("Soluong"));
        tb_TongTienDV.setCellValueFactory(new PropertyValueFactory<PhieuDV, String>("TienDV"));
        tb_DateUsed.setCellValueFactory(new PropertyValueFactory<PhieuDV, Date>("NgaySD"));
    }

    @FXML
    void PressInHD(ActionEvent event) {

    }

}
