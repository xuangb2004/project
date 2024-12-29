package btl.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import btl.classes.*;
import btl.database.ConnectionDB;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDetails {

    @FXML
    private AnchorPane anpAddUser;

    @FXML
    private TextField txtTenP;

    @FXML
    private TextField txtTenK;

    @FXML
    private TextField txtLP;

    @FXML
    private TextField txtGia;

    @FXML
    private TextField txtNgayDat;

    @FXML
    private TextField txtNgayTra;

    @FXML
    private Button btnThueDV;

    private static Stage addDialogStage;

    private static String user;
    private static String password;

    private NhanVien nvphutrach;

    public void setNvphutrach(NhanVien nvphutrach) {
        this.nvphutrach = nvphutrach;
    }

    private PhieuDatPhong room;
    private String tenphong = "";

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }

    private static Stage primaryStage;

    public void setpimaryStage(Stage pS) {
        primaryStage = pS;
    }

    public PhieuDatPhong getRoom() {
        return room;
    }

    public void setRoom(PhieuDatPhong room) {
        this.room = room;
    }

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDatPhong;

    private int flag = 0;

    public int getFlag() {
        return flag;
    }

    private ConnectionDB db;

    private AnchorPane anpTab;

    public void setAnpTab(AnchorPane anpTab) {
        this.anpTab = anpTab;
    }

    void Init() {
        btnThueDV.setVisible(false);

        if (room == null) {
            System.out.println("Khong nhan duoc thong tin phong");
            return;

        } else {
            txtTenP.setText(room.getTenP());
            txtTenK.setText(room.getTenKHACH());
            txtGia.setText(room.getGiaPhong());
            txtLP.setText(room.getTenLP());
            txtNgayDat.setText(room.getNgayDatPhong().toString());
            txtNgayTra.setText(room.getNgayTraPhong().toString());
            btnCancel.setLayoutX(153);
            btnCancel.setLayoutY(237);
            btnDatPhong.setVisible(false);
            btnThueDV.setVisible(true);

        }
    }

    @FXML
    void PressCancel(ActionEvent event) {
        if (room == null)
            flag = 0;
        else
            flag = 1;
        primaryStage.close();
    }

    @FXML
    void PressDatPhong(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        addDialogStage = new Stage();
        addDialogStage.initOwner(primaryStage);
        addDialogStage.initModality(Modality.WINDOW_MODAL);

        FXMLLoader CreateRoom = new FXMLLoader(getClass().getResource("QLDP.fxml"));
        Parent CreateRoom_Design = CreateRoom.load();

        addDialogStage.setTitle("Room Details");
        Scene scene = new Scene(CreateRoom_Design);

        addDialogStage.initStyle(StageStyle.UNDECORATED);

        QLDP controller = CreateRoom.getController();
        controller.setNvphutrach(nvphutrach);
        controller.setPhong(tenphong);
        controller.setpimaryStage(addDialogStage);
        controller.Init();

        addDialogStage.setScene(scene);
        addDialogStage.showAndWait();

        if (controller.getFlag() == 1) {
            room = controller.getRoom();
            txtTenK.setText(room.getTenKHACH());
            txtNgayDat.setText(room.getNgayDatPhong().toString());
            txtNgayTra.setText(room.getNgayTraPhong().toString());
            txtLP.setText(room.getTenLP());
            txtGia.setText(room.getGiaPhong());
            txtTenP.setText(room.getTenP());
            btnCancel.setLayoutX(153);
            btnCancel.setLayoutY(237);
            btnDatPhong.setVisible(false);
            flag = 1;
            btnThueDV.setVisible(true);
        }

        return;

    }

    @FXML
    void PressThueDV(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        addDialogStage = new Stage();
        addDialogStage.initOwner(primaryStage);
        addDialogStage.initModality(Modality.WINDOW_MODAL);

        FXMLLoader dichvu = new FXMLLoader(getClass().getResource("DichVu.fxml"));

        Parent DichVu_Design = dichvu.load();

        addDialogStage.setTitle("Services");
        Scene scene = new Scene(DichVu_Design);

        addDialogStage.initStyle(StageStyle.UNDECORATED);

        DichVu controller = dichvu.getController();

        controller.setPhongdat(room);
        controller.setpimaryStage(addDialogStage);
        controller.Init();

        addDialogStage.setX(261);
        addDialogStage.setY(59);

        // t chinh vai cai ty

        addDialogStage.setScene(scene);
        addDialogStage.showAndWait();
        return;
    }
}
