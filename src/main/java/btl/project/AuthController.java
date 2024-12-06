package btl.project;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class AuthController {
  @FXML
  private Text welcome;

  public void initialize() {
    welcome.setText("Xin chào, " + LoginController.role.toString().toLowerCase());
  }

  @FXML
  private void turnBack() throws IOException {
    App.setRoot("login");
  }
}
