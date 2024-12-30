package btl.project;

import java.io.IOException;

import btl.classes.TaiKhoan.Role;
import static btl.classes.TaiKhoan.getRole;
import btl.database.Auth;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {
  @FXML
  private TextField username;
  @FXML
  private PasswordField password;
  @FXML
  private Text warning;

  @FXML
  private void initialize() {
    username.requestFocus();
  }

  @FXML
  private void nextField() {
    password.requestFocus();
  }

  @FXML
  private void goToRegister() throws IOException {
    App.setRoot("register");
  }

  @FXML
  private void signIn() throws IOException, Exception {
    if (username.getText().isEmpty() || password.getText().isEmpty()) {
      warning.setText("Vui lòng nhập tài khoản/mật khẩu");
    } else {
      int loginState = Auth.login(username.getText(), password.getText());
      switch (loginState) {
        case 0 -> {
          switch (getRole()) {
            case Role.ADMIN -> App.setRoot("admin");
            case Role.HOTEL -> App.setRoot("hotel");
            case Role.GUEST -> App.setRoot("guest");
          }
        }
        default -> {
          warning.setText("Tài khoản/mật khẩu không đúng");
        }
      }
    }
  }
}