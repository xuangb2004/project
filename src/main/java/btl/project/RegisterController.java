package btl.project;

import java.io.IOException;

import btl.database.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterController {
  @FXML
  private TextField username;
  @FXML
  private PasswordField password;
  @FXML
  private PasswordField confirmPassword;

  @FXML
  private Text warning;

  @FXML
  private void initialize() {
    username.requestFocus();
  }

  @FXML
  private void focusPassword() {
    password.requestFocus();
  }

  @FXML
  private void focusConfirmPassword() {
    confirmPassword.requestFocus();
  }

  @FXML
  private void returnToLogin() throws IOException {
    App.setRoot("login");
  }

  @FXML
  private void register() throws IOException, Exception {
    if (username.getText().isEmpty() || password.getText().isEmpty() || confirmPassword.getText().isEmpty()) {
      warning.setText("Vui lòng nhập đầy đủ thông tin.");
    } else if (password.getText().equals(confirmPassword.getText())) {
      switch (Auth.register(username.getText(), password.getText())) {
        case 0 -> {
          App.setRoot("register-info");
        }
        case 1 -> warning.setText("Tên tài khoản đã được sử dụng.");
      }
    } else {
      warning.setText("Mật khẩu không khớp.");
    }
  }
}
