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

  public static Role role;

  @FXML
  private void signIn() throws IOException, Exception {
    int loginState = Auth.login(username.getText(), password.getText());
    switch (loginState) {
      case 0 -> {
        switch (getRole()) {
          case Role.ADMIN -> App.setRoot("auth");
          case Role.HOTEL -> App.setRoot("hotel");
          case Role.GUEST -> App.setRoot("auth");
        }
      }
      default -> {
        warning.setText("Tài khoản/mật khẩu không đúng");
        username.clear();
        password.clear();
      }
    }
  }
}