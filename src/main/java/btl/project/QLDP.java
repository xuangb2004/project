package btl.project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import btl.classes.*;
import btl.database.ConnectionDB;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class QLDP {

    private ConnectionDB db;
    private PhieuDatPhong room;
    private Khach qNameDP;
    private String phongdat;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    private int flag = 0;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @FXML
    void KeyNumber(KeyEvent event) {
        txtQPhoneNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtQPhoneNumber.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        txtQCMND.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtQCMND.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }

    public void setPhong(String phongdat) {
        this.phongdat = phongdat;
    }

    private NhanVien nvphutrach;

    public void setNvphutrach(NhanVien nvphutrach) {
        this.nvphutrach = nvphutrach;
    }

    private static Stage primaryStage;

    public void setpimaryStage(Stage pS) {
        primaryStage = pS;
    }

    public PhieuDatPhong getRoom() {
        return room;
    }

    public void Init() throws SQLException, ClassNotFoundException {
        db = new ConnectionDB();

        if (db == null)
            System.out.println("Unconnected");
        else
            System.out.println("Connected");

        String sql = String.format("SELECT * FROM phong");

        PreparedStatement statement = db.conn.prepareStatement(sql);
        db.rs = statement.executeQuery();

        while (db.rs.next()) {
            if (phongdat.equals(db.rs.getString(2))) {
                dtpQWasBorn.setValue(LocalDate.now().minusYears(21));

                // LocalDate.parse(LocalDate.now().format(formatter))
                dtpNgayDat.setValue(LocalDateTime.now().toLocalDate());
                txtIDRoom.setText(String.valueOf(db.rs.getInt("MaPhong")));
                BigDecimal gia = db.rs.getBigDecimal("DonGia");
                txtRoomPrice.setText(gia.toPlainString());
                txtRoomPeople.setText(String.valueOf(db.rs.getInt("SoNguoi")));
                txtNameRoom.setText(db.rs.getString("TenPhong"));
                txtRoomFloor.setText(String.valueOf(db.rs.getInt("Tang")));
                if (db.rs.getString("MaLP").equals("DON"))
                    rbDon.setSelected(true);
                if (db.rs.getString("MaLP").equals("DOI"))
                    rbDoi.setSelected(true);
                if (db.rs.getString("MaLP").equals("MANY"))
                    rbMany.setSelected(true);
                break;

            }
        }

        rbNu.setSelected(true);

    }

    @FXML
    private RadioButton rbNam;

    @FXML
    private RadioButton rbNu;

    @FXML
    private TextField txtQCountry;

    @FXML
    private TextField txtQCMND;

    @FXML
    private TextField txtQEmail;

    @FXML
    private TextField txtQPhoneNumber;

    @FXML
    private TextField txtQName;

    @FXML
    private DatePicker dtpQWasBorn;

    @FXML
    private DatePicker dtpNgayDat;

    @FXML
    private DatePicker dtpNgayTra;

    @FXML
    private RadioButton rbDoi;

    @FXML
    private RadioButton rbDon;

    @FXML
    private TextField txtIDRoom;

    @FXML
    private RadioButton rbMany;

    @FXML
    private TextField txtNameRoom;

    @FXML
    private TextField txtRoomPeople;

    @FXML
    private TextField txtRoomPrice;

    @FXML
    private TextField txtRoomFloor;

    @FXML
    private TextField txtMoneyPaid;

    @FXML
    void PressCancel(ActionEvent event) {
        flag = 0;
        primaryStage.close();
    }

    @FXML
    void PressOK(ActionEvent event) throws SQLException {
        if (dtpNgayTra.getValue().isBefore(dtpNgayDat.getValue())
                && dtpNgayTra.getValue().equals(dtpNgayDat.getValue())) {
            JOptionPane.showMessageDialog(null, "Không thể trả trước hoặc ngay trong ngày đặt");
            dtpNgayDat.setValue(LocalDate.parse(LocalDate.now().format(formatter)));
            return;
        }

        String GioiTinh = "";
        if (rbNu.isSelected())
            GioiTinh = "Nữ";
        else
            GioiTinh = "Nam";

        Khach khachDP = new Khach(txtQName.getText(), txtQPhoneNumber.getText(), txtQEmail.getText(),
                txtQCMND.getText(), txtQCountry.getText(), GioiTinh, Date.valueOf(dtpQWasBorn.getValue()));

        db.rs = db.stmt.executeQuery("SELECT * FROM  khach");
        int dem = 0;
        while (db.rs.next()) {
            if (txtQCMND.getText().equals(db.rs.getString("CMND"))) {
                System.out.println("Cùng một người");
                dem++;
            }

        }

        if (dem == 0)
            db.addKhachDB(txtQName.getText(), Date.valueOf(dtpQWasBorn.getValue().toString()),
                    txtQPhoneNumber.getText(), txtQEmail.getText(),
                    txtQCMND.getText(), txtQCountry.getText(), GioiTinh);

        int MaQ = 0;
        if (db == null)
            System.out.println("Unconnected");
        else
            System.out.println("Connected");

        String sql = String.format("SELECT * FROM khach WHERE CMND = ?");
        PreparedStatement statement = db.conn.prepareStatement(sql);
        statement.setString(1, txtQCMND.getText());
        db.rs = statement.executeQuery();

        while (db.rs.next()) {
            MaQ = db.rs.getInt("MaKhach");
        }

        int MaPDP = 0;
        db.rs = db.stmt.executeQuery("SELECT COUNT(MaPhieuDP) AS Tong FROM phieudatphong  ");
        while (db.rs.next()) {
            MaPDP = db.rs.getInt("Tổng");
        }

        BigDecimal tientra = new BigDecimal(txtMoneyPaid.getText());
        BigDecimal giaphong = new BigDecimal(txtRoomPrice.getText());

        db.addPDPDB(MaPDP + 1, Integer.valueOf(txtIDRoom.getText()), MaQ, nvphutrach.getMaNV(),
                Date.valueOf(dtpNgayDat.getValue()), Date.valueOf(dtpNgayTra.getValue()), tientra, giaphong);

        String tenLP = "";
        if (rbDoi.isSelected())
            tenLP = rbDoi.getText();
        if (rbDon.isSelected())
            tenLP = rbDon.getText();
        if (rbMany.isSelected())
            tenLP = rbMany.getText();

        room = new PhieuDatPhong(MaPDP + 1, Integer.valueOf(txtIDRoom.getText()), txtNameRoom.getText(), tenLP,
                txtQName.getText(), Date.valueOf(dtpNgayDat.getValue()), Date.valueOf(dtpNgayTra.getValue()),
                txtRoomPrice.getText(), txtMoneyPaid.getText());

        JOptionPane.showMessageDialog(null, "Đặt phòng thành công !");
        db.addHD(Integer.valueOf(txtIDRoom.getText()), new BigDecimal(txtMoneyPaid.getText()),
                Date.valueOf(dtpNgayTra.getValue()),
                nvphutrach.getMaNV());
        flag = 1;
        primaryStage.close();
    }

    @FXML
    void PressReset(ActionEvent event) {
        txtQCMND.setText("");
        txtQCountry.setText("");
        txtQEmail.setText("");
        txtQName.setText("");
        txtQPhoneNumber.setText("");
    }

    @FXML
    void PressNam(ActionEvent event) {
        if (rbNam.isSelected())
            rbNam.setSelected(true);
        rbNu.setSelected(false);
    }

    @FXML
    void PressNu(ActionEvent event) {
        if (rbNu.isSelected())
            rbNu.setSelected(true);
        rbNam.setSelected(false);
    }

    public void TinhTienThue() throws ParseException {

        // HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        String date1 = dtpNgayDat.getValue().toString().replace("-", "/");
        String date2 = dtpNgayTra.getValue().toString().replace("-", "/");
        System.out.println(date1 + "\n" + date2);
        java.util.Date d1 = format.parse(date1);
        java.util.Date d2 = format.parse(date2);

        // in milliseconds
        long diff = d2.getTime() - d1.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long songayo = diff / (24 * 60 * 60 * 1000);

        System.out.println("So ngay o:" + songayo);

        float giaphong = Float.valueOf(txtRoomPrice.getText());

        long SoTienTra = (long) (songayo * giaphong);

        txtMoneyPaid.setText(String.valueOf(SoTienTra));
        System.out.println("Tong so tien:" + SoTienTra);
    }

    @FXML
    void MoneyPaid(ActionEvent event) throws ParseException {
        TinhTienThue();
    }

}
