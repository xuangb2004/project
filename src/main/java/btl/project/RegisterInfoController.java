package btl.project;

import java.io.IOException;

import btl.database.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterInfoController {
  @FXML
  private TextField name;

  @FXML
  private TextField dob;

  @FXML
  private TextField id;

  @FXML
  private TextField phone;

  @FXML
  private TextField gender;

  @FXML
  private TextField email;

  @FXML
  private TextField nation;

  @FXML
  private Text warning;

  @FXML
  private void initialize() {
    name.requestFocus();
  }

  @FXML
  private void focusDob() {
    dob.requestFocus();
  }

  @FXML
  private void focusId() {
    id.requestFocus();
  }

  @FXML
  private void focusPhone() {
    phone.requestFocus();
  }

  @FXML
  private void focusGender() {
    gender.requestFocus();
  }

  @FXML
  private void focusEmail() {
    email.requestFocus();
  }

  @FXML
  private void focusNation() {
    nation.requestFocus();
  }

  @FXML
  private void submit() throws IOException, Exception {
    if (name.getText().isEmpty() || dob.getText().isEmpty() || id.getText().isEmpty() || phone.getText().isEmpty()
        || gender.getText().isEmpty() || email.getText().isEmpty() || nation.getText().isEmpty()) {
      warning.setText("Vui lòng nhập đầy đủ thông tin.");
    } else {
      Auth.registerInfo(name.getText(), dob.getText(), id.getText(), phone.getText(), gender.getText(), email.getText(),
          nation.getText());
      App.setRoot("login");
    }
  }
}
