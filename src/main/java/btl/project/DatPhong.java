package btl.project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import btl.classes.*;
import btl.database.ConnectionDB;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatPhong {

    private NhanVien nvphutrach;

    public void setNvphutrach(NhanVien nvphutrach) {
        this.nvphutrach = nvphutrach;
    }

    @FXML
    private AnchorPane anpTab;

    @FXML
    private AnchorPane anpRoomDetails;

    @FXML
    private RadioButton btnEmpty_101, btnEmpty_102, btnEmpty_103, btnEmpty_104, btnEmpty_105, btnEmpty_106;

    @FXML
    private RadioButton btnEmpty_201, btnEmpty_202, btnEmpty_203, btnEmpty_204, btnEmpty_205, btnEmpty_206;

    @FXML
    private RadioButton btnEmpty_301, btnEmpty_302, btnEmpty_303, btnEmpty_304, btnEmpty_305, btnEmpty_306;

    @FXML
    private RadioButton btnWorking_101, btnWorking_102, btnWorking_103, btnWorking_104, btnWorking_105, btnWorking_106;

    @FXML
    private RadioButton btnWorking_201, btnWorking_202, btnWorking_203, btnWorking_204, btnWorking_205, btnWorking_206;

    @FXML
    private RadioButton btnWorking_301, btnWorking_302, btnWorking_303, btnWorking_304, btnWorking_305, btnWorking_306;

    @FXML
    private Button btnRoom101, btnRoom102, btnRoom103, btnRoom104, btnRoom105, btnRoom106;

    @FXML
    private Button btnRoom201, btnRoom202, btnRoom203, btnRoom204, btnRoom205, btnRoom206;

    @FXML
    private Button btnRoom301, btnRoom302, btnRoom303, btnRoom304, btnRoom305, btnRoom306;

    @FXML
    private Label lbl_101, lbl_102, lbl_103, lbl_104, lbl_105, lbl_106;

    @FXML
    private Label lbl_201, lbl_202, lbl_203, lbl_204, lbl_205, lbl_206;

    @FXML
    private Label lbl_301, lbl_302, lbl_303, lbl_304, lbl_305, lbl_306;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    List<PhongDat> listPhongDat = new ArrayList<>();

    private PhieuDatPhong room;

    private ConnectionDB db;

    public void setDb(ConnectionDB db) {
        this.db = db;
    }

    private static Stage addDialogStage;

    private static String user;
    private static String password;

    String style = "";

    private static Stage primaryStage;

    public void setpimaryStage(Stage pS) {
        primaryStage = pS;
    }

    private ObservableList<PhieuDatPhong> phieuDatPhongsList = null;

    private AnchorPane anpTabHome;

    public void setAnpTabHome(AnchorPane anpTabHome) {
        this.anpTabHome = anpTabHome;
    }

    public ObservableList<PhieuDatPhong> getPhieuDatPhongsList() {
        return phieuDatPhongsList;
    }

    public void setPhieuDatPhongsList(ObservableList<PhieuDatPhong> phieuDatPhongsList) {
        this.phieuDatPhongsList = phieuDatPhongsList;
    }

    public void Init() {
        listPhongDat.add(new PhongDat(btnRoom101, btnEmpty_101, btnWorking_101, lbl_101.getText()));
        listPhongDat.add(new PhongDat(btnRoom102, btnEmpty_102, btnWorking_102, lbl_102.getText()));
        listPhongDat.add(new PhongDat(btnRoom103, btnEmpty_103, btnWorking_103, lbl_103.getText()));
        listPhongDat.add(new PhongDat(btnRoom104, btnEmpty_104, btnWorking_104, lbl_104.getText()));
        listPhongDat.add(new PhongDat(btnRoom105, btnEmpty_105, btnWorking_105, lbl_105.getText()));
        listPhongDat.add(new PhongDat(btnRoom106, btnEmpty_106, btnWorking_106, lbl_106.getText()));
        listPhongDat.add(new PhongDat(btnRoom201, btnEmpty_201, btnWorking_201, lbl_201.getText()));
        listPhongDat.add(new PhongDat(btnRoom202, btnEmpty_202, btnWorking_202, lbl_202.getText()));
        listPhongDat.add(new PhongDat(btnRoom203, btnEmpty_203, btnWorking_203, lbl_203.getText()));
        listPhongDat.add(new PhongDat(btnRoom204, btnEmpty_204, btnWorking_204, lbl_204.getText()));
        listPhongDat.add(new PhongDat(btnRoom205, btnEmpty_205, btnWorking_205, lbl_205.getText()));
        listPhongDat.add(new PhongDat(btnRoom206, btnEmpty_206, btnWorking_206, lbl_206.getText()));
        listPhongDat.add(new PhongDat(btnRoom301, btnEmpty_301, btnWorking_301, lbl_301.getText()));
        listPhongDat.add(new PhongDat(btnRoom302, btnEmpty_302, btnWorking_302, lbl_302.getText()));
        listPhongDat.add(new PhongDat(btnRoom303, btnEmpty_303, btnWorking_303, lbl_303.getText()));
        listPhongDat.add(new PhongDat(btnRoom304, btnEmpty_304, btnWorking_304, lbl_304.getText()));
        listPhongDat.add(new PhongDat(btnRoom305, btnEmpty_305, btnWorking_305, lbl_305.getText()));
        listPhongDat.add(new PhongDat(btnRoom306, btnEmpty_306, btnWorking_306, lbl_306.getText()));

        ShowRoomAvailable();
    }

    private List<PhieuDatPhong> listPDP_Available = null;

    public void ShowRoomAvailable() {
        if (phieuDatPhongsList == null)
            System.out.println("Không có gì cả");
        else
            System.out.println(phieuDatPhongsList.size());

        for (int j = 0; j < listPhongDat.size(); j++) {
            listPhongDat.get(j).getBtnEmpty().setSelected(true);
            style = listPhongDat.get(j).getBtnroom().getStyle().toString();
            listPhongDat.get(j).getBtnroom().setStyle(listPhongDat.get(j).getBtnEmpty().getStyle().toString());
            String tenphong_eq = listPhongDat.get(j).getTenPhong();
            for (int i = 0; i < phieuDatPhongsList.size(); i++) {
                String tenphong = phieuDatPhongsList.get(i).getTenP();
                Date Ngaydat = java.sql.Date.valueOf(phieuDatPhongsList.get(i).getNgayDatPhong().toString());
                Date Ngaytra = java.sql.Date.valueOf(phieuDatPhongsList.get(i).getNgayTraPhong().toString());
                if (tenphong.equals(tenphong_eq)) {
                    if ((Ngaydat.toLocalDate().isBefore(java.time.LocalDate.now()) ||
                            Ngaydat.toLocalDate().isEqual(java.time.LocalDate.now()))
                            &&
                            (Ngaytra.toLocalDate().isAfter(java.time.LocalDate.now()) ||
                                    Ngaytra.toLocalDate().isEqual(java.time.LocalDate.now()))) {
                        listPhongDat.get(j).getBtnWorking().setSelected(true);
                        listPhongDat.get(j).getBtnEmpty().setSelected(false);
                        listPhongDat.get(j).getBtnroom().setStyle(style);
                        PhongDat pd = new PhongDat(listPhongDat.get(j), phieuDatPhongsList.get(i));
                        listPhongDat.set(j, pd);
                    }

                }

            }
        }

    }

    void RoomDetails(PhieuDatPhong pdp, String tenphong) throws IOException {

        addDialogStage = new Stage();
        addDialogStage.initOwner(primaryStage);
        addDialogStage.initModality(Modality.WINDOW_MODAL);

        FXMLLoader roomDetails = new FXMLLoader(getClass().getResource("RoomDetails.fxml"));
        Parent RoomDetails_Design = roomDetails.load();

        addDialogStage.setTitle("Room Details");
        Scene scene = new Scene(RoomDetails_Design);

        addDialogStage.initStyle(StageStyle.UNDECORATED);

        RoomDetails controller = roomDetails.getController();
        controller.setAnpTab(anpTabHome);
        controller.setRoom(pdp);
        controller.setNvphutrach(nvphutrach);
        controller.setTenphong(tenphong);
        controller.setpimaryStage(addDialogStage);
        controller.Init();

        addDialogStage.setScene(scene);
        addDialogStage.showAndWait();
        if (controller.getFlag() == 0)
            return;
        if (controller.getFlag() == 1)
            room = controller.getRoom();
    }

    @FXML
    void ShowDetails(ActionEvent event) throws IOException, SQLException {

        for (int i = 0; i < listPhongDat.size(); i++) {
            if (listPhongDat.get(i).getBtnroom() == event.getSource()) {

                RoomDetails(listPhongDat.get(i).getPhongdangdat(), listPhongDat.get(i).getTenPhong());
                if (room == null)
                    return;
                else {

                    listPhongDat.get(i).getBtnWorking().setSelected(true);
                    listPhongDat.get(i).getBtnEmpty().setSelected(false);
                    listPhongDat.get(i).getBtnroom().setStyle(style);
                    PhongDat pd = new PhongDat(listPhongDat.get(i), room);
                    listPhongDat.set(i, pd);
                    return;
                }
            }
        }
    }

}
