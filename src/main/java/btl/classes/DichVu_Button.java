package btl.classes;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DichVu_Button {
    private Label tenDV;
    private Button ButtonDV;
    private TextField GiaDV;
    private ComboBox<Integer> SoLuong;

    public DichVu_Button(Label tenDV, Button buttonDV, TextField giaDV, ComboBox<Integer> soLuong) {
        this.tenDV = tenDV;
        ButtonDV = buttonDV;
        GiaDV = giaDV;
        SoLuong = soLuong;
    }

    public Label getTenDV() {
        return tenDV;
    }

    public void setTenDV(Label tenDV) {
        this.tenDV = tenDV;
    }

    public Button getButtonDV() {
        return ButtonDV;
    }

    public void setButtonDV(Button buttonDV) {
        ButtonDV = buttonDV;
    }

    public TextField getGiaDV() {
        return GiaDV;
    }

    public void setGiaDV(TextField giaDV) {
        GiaDV = giaDV;
    }

    public ComboBox<Integer> getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(ComboBox<Integer> soLuong) {
        SoLuong = soLuong;
    }
}
