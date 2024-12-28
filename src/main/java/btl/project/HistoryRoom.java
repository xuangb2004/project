package btl.project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import btl.ClassData.*;


import java.time.LocalDate;
import java.util.Date;

public class HistoryRoom {

    public void Init(){
        ShowHistory();

    }


    @FXML
    private AnchorPane anpAddUser;

    @FXML
    private TableView<PhieuDatPhong> tblPDP;

    @FXML
    private TableColumn<PhieuDatPhong, Integer> IDPDPtable;

    @FXML
    private TableColumn<PhieuDatPhong, String> NameKHtable;

    @FXML
    private TableColumn<PhieuDatPhong, String> NamePhongtable;

    @FXML
    private TableColumn<PhieuDatPhong, String> PricePhongtable;

    @FXML
    private TableColumn<PhieuDatPhong, String> MoneyPaidtable;

    @FXML
    private TableColumn<PhieuDatPhong, Date> DayTookIn_table;

    @FXML
    private TableColumn<PhieuDatPhong, Date> DayTookOut_table;

    @FXML
    private DatePicker dtpFromDay;

    @FXML
    private DatePicker dtptoDay;

    public Stage primaryStage;

    private ObservableList<PhieuDatPhong> phieuDatPhongsList = null;
    public void setPhieuDPList(ObservableList<PhieuDatPhong> pdp){
        phieuDatPhongsList = pdp;
        if (phieuDatPhongsList == null) System.out.println("setThatBai,danh sach rong");
        else System.out.println("setThanhCong,danh sach co du lieu");
    }



    public void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }

    void ShowHistory(){
        ConfigTable();
        tblPDP.setItems(phieuDatPhongsList);

    }

    @FXML
    void PressCancel(ActionEvent event) {
        primaryStage.close();
    }

    void ConfigTable(){
        IDPDPtable.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong,Integer>("MaPDP"));
        NameKHtable.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong,String>("tenKHACH"));
        NamePhongtable.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong, String>("tenP"));
        PricePhongtable.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong,String>("GiaPhong"));
        MoneyPaidtable.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong,String>("TienTra"));
        DayTookIn_table.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong,Date>("NgayDatPhong"));
        DayTookOut_table.setCellValueFactory(new PropertyValueFactory<PhieuDatPhong,Date>("NgayTraPhong"));
    }

}
