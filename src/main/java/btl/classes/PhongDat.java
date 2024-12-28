package btl.classes;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class PhongDat {
    private Button btnroom;
    private RadioButton btnEmpty;
    private RadioButton btnWorking;
    private String tenPhong;
    private PhieuDatPhong phongdangdat;

    public PhongDat(Button btnroom, RadioButton btnEmpty, RadioButton btnWorking, String tenPhong) {
        this.btnroom = btnroom;
        this.btnEmpty = btnEmpty;
        this.btnWorking = btnWorking;
        this.tenPhong = tenPhong;
    }

    public PhongDat(PhongDat design_button, PhieuDatPhong Phongdangdat) {
        btnroom = design_button.getBtnroom();
        btnEmpty = design_button.getBtnEmpty();
        btnWorking = design_button.getBtnWorking();
        tenPhong = design_button.tenPhong;
        phongdangdat = Phongdangdat;
    }

    public Button getBtnroom() {
        return btnroom;
    }

    public void setBtnroom(Button btnroom) {
        this.btnroom = btnroom;
    }

    public RadioButton getBtnEmpty() {
        return btnEmpty;
    }

    public void setBtnEmpty(RadioButton btnEmpty) {
        this.btnEmpty = btnEmpty;
    }

    public RadioButton getBtnWorking() {
        return btnWorking;
    }

    public void setBtnWorking(RadioButton btnWorking) {
        this.btnWorking = btnWorking;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public PhieuDatPhong getPhongdangdat() {
        return phongdangdat;
    }

    public void setPhongdangdat(PhieuDatPhong phongdangdat) {
        this.phongdangdat = phongdangdat;
    }
}
