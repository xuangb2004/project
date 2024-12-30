package btl.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private AnchorPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Tự động load màn hình QLNV khi vào admin
        switchToQLNV();
    }

    @FXML
    public void switchToQLNV() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QL_NV.fxml"));
            Parent qlnvView = loader.load();

            // Điều chỉnh kích thước để vừa với contentArea
            qlnvView.prefWidth(contentArea.getWidth());
            qlnvView.prefHeight(contentArea.getHeight());

            // Thiết lập các anchor cho view mới
            AnchorPane.setTopAnchor(qlnvView, 0.0);
            AnchorPane.setRightAnchor(qlnvView, 0.0);
            AnchorPane.setBottomAnchor(qlnvView, 0.0);
            AnchorPane.setLeftAnchor(qlnvView, 0.0);

            // Xóa nội dung cũ và thêm view mới
            contentArea.getChildren().clear();
            contentArea.getChildren().add(qlnvView);

            // Lấy controller của QL_NV để khởi tạo dữ liệu
            QL_NV controller = loader.getController();
            if (controller != null) {
                controller.initialize();
            } else {
                showError("Lỗi", "Không thể khởi tạo controller QL_NV");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showError("Lỗi", "Không thể tải màn hình Quản lý nhân viên: " + e.getMessage());
        }
    }

    @FXML
    public void logout() {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Lỗi", "Không thể đăng xuất: " + e.getMessage());
        }
    }

    private void showError(String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}