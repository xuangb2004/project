package btl.project;

import java.io.IOException;

import btl.classes.TaiKhoan.Role;
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
  private void signIn() throws IOException {
    if (username.getText().equals("admin") && password.getText().equals("admin")) {
      role = Role.ADMIN;
      App.setRoot("QL_NV");
    } else if (username.getText().equals("hotel") && password.getText().equals("hotel")) {
      role = Role.HOTEL;
      App.setRoot("auth");
    } else if (username.getText().equals("guest") && password.getText().equals("guest")) {
      role = Role.GUEST;
      App.setRoot("auth");
    } else {
      warning.setText("Tài khoản/mật khẩu không đúng");
      username.clear();
      password.clear();
    }
  }
}
