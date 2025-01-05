package btl.project;

import java.io.IOException;

import btl.database.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterController {
  public static class Account {
    static private String username;
    static private String password;
    public static void setAccount(String username, String password) {
      Account.username = username;
      Account.password = password;
    }

        public static String getUsername() {
            return username;
        }

        public static String getPassword() {
            return password;
        }
  }
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
      switch (Auth.check(username.getText(), password.getText())) {
        case 0 -> {
          Account.setAccount(username.getText(), password.getText());
          App.setRoot("register-info");
        }
        case 1 -> warning.setText("Tên tài khoản đã được sử dụng.");
      }
    } else {
      warning.setText("Mật khẩu không khớp.");
    }
  }
}
