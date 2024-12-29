package btl.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import btl.classes.*;
import btl.database.DatabaseConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Hotel implements Initializable {
    private Connection conn;
    FXMLLoader menu, room, thongke, dv, QLKH;
    private static Stage addDialogStage;
    Parent menuDesign, room_Design, thongke_Design, dv_Design, QLKH_Design;
    private static Stage primaryStage;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            conn = DatabaseConnection.getConnection();
            loadFXMLs();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi khởi tạo", "Không thể khởi tạo giao diện: " + e.getMessage());
        }
    }

    private void loadFXMLs() throws IOException {
        menu = new FXMLLoader(getClass().getResource("MenuTheme.fxml"));
        room = new FXMLLoader(getClass().getResource("DatPhong.fxml"));
        thongke = new FXMLLoader(getClass().getResource("thongke.fxml"));
        QLKH = new FXMLLoader(getClass().getResource("QL_KH.fxml"));

        menuDesign = menu.load();
        room_Design = room.load();
        thongke_Design = thongke.load();
        QLKH_Design = QLKH.load();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    public void PressSignOut(javafx.event.ActionEvent event) throws Exception {
        App.setRoot("login");
    }

    @FXML
    void PressMenu(ActionEvent event) {
        try {
            addDialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if (menuDesign != null) {
                anpTab.getChildren().setAll(menuDesign);
                AnchorPane.setBottomAnchor(menu.getRoot(), 0.0);
                AnchorPane.setLeftAnchor(menu.getRoot(), 0.0);
                AnchorPane.setRightAnchor(menu.getRoot(), 0.0);
                AnchorPane.setTopAnchor(menu.getRoot(), 0.0);
            } else {
                showError("Lỗi", "Không thể tải menu");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi", "Không thể hiển thị menu: " + e.getMessage());
        }
    }

    private ObservableList<PhieuDatPhong> phieuDatPhongsList = null;

    void setDSPDP_list() throws SQLException {
        String sql = "SELECT * FROM phieudatphong";
        try (PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {

            List<PhieuDatPhong> PDPList = new ArrayList<>();
            while (rs.next()) {
                PhieuDatPhong pdp = new PhieuDatPhong(
                        rs.getInt("Mã PDP"),
                        rs.getInt("Mã Phòng"),
                        rs.getString("Tên Phòng"),
                        rs.getString("Tên LP"),
                        rs.getString("Khách"),
                        rs.getDate("Ngày đặt"),
                        rs.getDate("Ngày trả"),
                        rs.getBigDecimal("Giá Phòng").toPlainString(),
                        rs.getBigDecimal("Tiền trả").toPlainString());
                PDPList.add(pdp);
            }
            phieuDatPhongsList = FXCollections.observableArrayList(PDPList);
        }
    }

    @FXML
    void PressRoom(ActionEvent event) {
        try {
            addDialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            setDSPDP_list();

            if (room_Design != null) {
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
            } else {
                showError("Lỗi", "Không thể tải giao diện đặt phòng");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi", "Không thể hiển thị giao diện đặt phòng: " + e.getMessage());
        }
    }

    @FXML
    void PressThongKe(ActionEvent event) {
        try {
            addDialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if (thongke_Design != null) {
                anpTab.getChildren().setAll(thongke_Design);
                AnchorPane.setBottomAnchor(thongke.getRoot(), 0.0);
                AnchorPane.setLeftAnchor(thongke.getRoot(), 0.0);
                AnchorPane.setRightAnchor(thongke.getRoot(), 0.0);
                AnchorPane.setTopAnchor(thongke.getRoot(), 0.0);

                ThongKe controller = thongke.getController();
                controller.setpimaryStage(addDialogStage);
                controller.Init();
            } else {
                showError("Lỗi", "Không thể tải giao diện thống kê");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi", "Không thể hiển thị giao diện thống kê: " + e.getMessage());
        }
    }

    @FXML
    void PressKhachHang(ActionEvent event) {
        try {
            addDialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if (QLKH_Design != null) {
                anpTab.getChildren().setAll(QLKH_Design);
                AnchorPane.setBottomAnchor(QLKH.getRoot(), 0.0);
                AnchorPane.setLeftAnchor(QLKH.getRoot(), 0.0);
                AnchorPane.setRightAnchor(QLKH.getRoot(), 0.0);
                AnchorPane.setTopAnchor(QLKH.getRoot(), 0.0);

                QL_KH controller = QLKH.getController();
                controller.ShowQuest();
                controller.setpimaryStage(addDialogStage);
            } else {
                showError("Lỗi", "Không thể tải giao diện quản lý khách hàng");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi", "Không thể hiển thị giao diện quản lý khách hàng: " + e.getMessage());
        }
    }
}