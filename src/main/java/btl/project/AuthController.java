package btl.project;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import static btl.classes.TaiKhoan.getRole;

public class AuthController {
  @FXML
  private Text welcome;

  public void initialize() {
    welcome.setText("Xin chào, " + getRole().toString().toLowerCase());
  }

  @FXML
  private void turnBack() throws IOException {
    App.setRoot("login");
  }
}
