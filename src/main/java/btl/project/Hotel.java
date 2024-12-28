package btl.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import btl.ClassData.*;

import javax.swing.*;
import javax.xml.stream.XMLReporter;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hotel {

    public void init() throws SQLException, ClassNotFoundException, IOException {

        db = new ConnectionDB();

        menu = new FXMLLoader(getClass().getResource("MenuTheme.fxml"));

        room = new FXMLLoader(getClass().getResource("DatPhong.fxml"));
        thongke = new FXMLLoader(getClass().getResource("thongke.fxml"));

        QLKH = new FXMLLoader(getClass().getResource("QL_KH.fxml"));

        menuDesign = menu.load();

        room_Design = room.load();
        thongke_Design = thongke.load();

        QLKH_Design = QLKH.load();
    }

    FXMLLoader menu, room, thongke, dv, QLKH;
    private static Stage addDialogStage;
    Parent menuDesign, room_Design, thongke_Design, dv_Design, QLKH_Design;
    private static Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    private ConnectionDB db;
    @FXML
    private AnchorPane anpHome;

    @FXML
    private AnchorPane anpTab;

    @FXML
    private Button btnQuest;

    @FXML
    private Button btnRP;

    @FXML
    private Button btnRoom;

    @FXML
    private Button btnSignout;

    /////////////

    ///////////

    @FXML
    public void PressSignOut(javafx.event.ActionEvent event) throws Exception {
        App.setRoot("login");
    }

    @FXML
    void PressMenu(ActionEvent event) {
        addDialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        anpTab.getChildren().setAll(menuDesign);

        AnchorPane.setBottomAnchor(menu.getRoot(), 0.0);
        AnchorPane.setLeftAnchor(menu.getRoot(), 0.0);
        AnchorPane.setRightAnchor(menu.getRoot(), 0.0);
        AnchorPane.setTopAnchor(menu.getRoot(), 0.0);
    }
    //

    private ObservableList<PhieuDatPhong> phieuDatPhongsList = null;

    void setDSPDP_list() throws SQLException {
        String sql = String.format("SELECT * FROM [hotelmanagementdb].[dbo].[phieudatphong]");

        PreparedStatement statement = db.conn.prepareStatement(sql);
        db.rs = statement.executeQuery();

        List<PhieuDatPhong> PDPList = new ArrayList<PhieuDatPhong>();

        while (db.rs.next()) {
            PhieuDatPhong pdp = new PhieuDatPhong(db.rs.getInt("Mã PDP"), db.rs.getInt("Mã Phòng"),
                    db.rs.getString("Tên Phòng"), db.rs.getString("Tên LP"),
                    db.rs.getString("Khách"), db.rs.getDate("Ngày đặt"),
                    db.rs.getDate("Ngày trả"), db.rs.getBigDecimal("Giá Phòng").toPlainString(),
                    db.rs.getBigDecimal("Tiền trả").toPlainString());
            PDPList.add(pdp);
        }
        phieuDatPhongsList = FXCollections.observableArrayList(PDPList);

    }

    @FXML
    void PressRoom(ActionEvent event) throws SQLException {
        addDialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setDSPDP_list();

        anpTab.getChildren().setAll(room_Design);
        AnchorPane.setBottomAnchor(room.getRoot(), 0.0);
        AnchorPane.setLeftAnchor(room.getRoot(), 0.0);
        AnchorPane.setRightAnchor(room.getRoot(), 0.0);
        AnchorPane.setTopAnchor(room.getRoot(), 0.0);

        DatPhong controller = room.getController();
        controller.setAnpTabHome(anpTab);
        controller.setpimaryStage(primaryStage);
        controller.setPhieuDatPhongsList(phieuDatPhongsList);
        controller.Init();
    }

    @FXML
    void PressThongKe(ActionEvent event) {
        addDialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        anpTab.getChildren().setAll(thongke_Design);

        AnchorPane.setBottomAnchor(thongke.getRoot(), 0.0);
        AnchorPane.setLeftAnchor(thongke.getRoot(), 0.0);
        AnchorPane.setRightAnchor(thongke.getRoot(), 0.0);
        AnchorPane.setTopAnchor(thongke.getRoot(), 0.0);

        ThongKe controller = thongke.getController();
        controller.setpimaryStage(addDialogStage);
        controller.Init();
    }

    @FXML
    void PressKhachHang(ActionEvent event) {
        addDialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        anpTab.getChildren().setAll(QLKH_Design);

        AnchorPane.setBottomAnchor(QLKH.getRoot(), 0.0);
        AnchorPane.setLeftAnchor(QLKH.getRoot(), 0.0);
        AnchorPane.setRightAnchor(QLKH.getRoot(), 0.0);
        AnchorPane.setTopAnchor(QLKH.getRoot(), 0.0);

        QL_KH controller = QLKH.getController();
        controller.ShowQuest();
        controller.setpimaryStage(addDialogStage);
    }

}