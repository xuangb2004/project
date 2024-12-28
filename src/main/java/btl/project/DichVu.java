package btl.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import btl.classes.*;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DichVu {
    private ConnectionDB db;
    private static Stage addDialogStage;
    private static Stage primaryStage;

    private static String user;
    private static String password;

    public double tongtien = 0;

    public void setDb(ConnectionDB db) {
        this.db = db;
    }

    private PhieuDatPhong phongdat;

    public PhieuDatPhong getPhongdat() {
        return phongdat;
    }

    public void setPhongdat(PhieuDatPhong phongdat) {
        this.phongdat = phongdat;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public void setpimaryStage(Stage pS) {
        primaryStage = pS;
    }

    List<DichVu_Button> listDSDV = new ArrayList<DichVu_Button>();
    List<DichVu_ThucDon> listDSDV_ThucDon = new ArrayList<DichVu_ThucDon>();

    public void Init() throws SQLException, ClassNotFoundException {
        db = new ConnectionDB();
        listDSDV.add(new DichVu_Button(lblName1, btnDV1, txtGia1, cbxSL1));
        listDSDV.add(new DichVu_Button(lblName2, btnDV2, txtGia2, cbxSL2));
        listDSDV.add(new DichVu_Button(lblName3, btnDV3, txtGia3, cbxSL3));
        listDSDV.add(new DichVu_Button(lblName4, btnDV4, txtGia4, cbxSL4));
        listDSDV.add(new DichVu_Button(lblName5, btnDV5, txtGia5, cbxSL5));
        listDSDV.add(new DichVu_Button(lblName6, btnDV6, txtGia6, cbxSL6));
        listDSDV.add(new DichVu_Button(lblName7, btnDV7, txtGia7, cbxSL7));
        listDSDV.add(new DichVu_Button(lblName8, btnDV8, txtGia8, cbxSL8));
        listDSDV.add(new DichVu_Button(lblName9, btnDV9, txtGia9, cbxSL9));
        listDSDV.add(new DichVu_Button(lblName10, btnDV10, txtGia10, cbxSL10));
        listDSDV.add(new DichVu_Button(lblName11, btnDV11, txtGia11, cbxSL11));
        listDSDV.add(new DichVu_Button(lblName12, btnDV12, txtGia12, cbxSL12));
        listDSDV.add(new DichVu_Button(lblName13, btnDV13, txtGia13, cbxSL13));
        listDSDV.add(new DichVu_Button(lblName14, btnDV14, txtGia14, cbxSL14));
        listDSDV.add(new DichVu_Button(lblName15, btnDV15, txtGia15, cbxSL15));
        listDSDV.add(new DichVu_Button(lblName16, btnDV16, txtGia16, cbxSL16));
        listDSDV.add(new DichVu_Button(lblName17, btnDV17, txtGia17, cbxSL17));
        listDSDV.add(new DichVu_Button(lblName18, btnDV18, txtGia18, cbxSL18));
        listDSDV.add(new DichVu_Button(lblName19, btnDV19, txtGia19, cbxSL19));

        ShowCombobox();
        ShowGia();
        for (int i = 0; i < listDSDV_ThucDon.size(); i++) {
            listDSDV_ThucDon.get(i).setMaPhongDat(phongdat.getMaP());
        }
    }

    public void ShowCombobox() {
        List<Integer> danhsachSL = new ArrayList<>();
        for (int i = 0; i <= 30; i++)
            danhsachSL.add(i);
        ObservableList<Integer> SoLuong = FXCollections.observableArrayList(danhsachSL);

        for (int i = 0; i < listDSDV.size(); i++) {
            listDSDV.get(i).getSoLuong().setItems(SoLuong);
            listDSDV.get(i).getSoLuong().getSelectionModel().select(0);
        }

    }

    public void ShowGia() throws SQLException {

        db.rs = db.stmt.executeQuery("SELECT * FROM  dichvu");
        while (db.rs.next()) {

            listDSDV_ThucDon.add(new DichVu_ThucDon(db.rs.getString("TenDV"),
                    db.rs.getBigDecimal("DonGiaDV"), db.rs.getString("MaDV"), db.rs.getString("MaLoaiDV")));
        }

        for (int i = 0; i < listDSDV.size(); i++) {
            for (int j = 0; j < listDSDV_ThucDon.size(); j++) {
                if (listDSDV.get(i).getTenDV().getText().equals(listDSDV_ThucDon.get(j).getTenDV())) {

                    listDSDV.get(i).getGiaDV().setText(listDSDV_ThucDon.get(j).getDonGiaDV().toPlainString());
                }
            }

        }

    }

    @FXML
    void PressServices(ActionEvent event) {

        for (int i = 0; i < listDSDV.size(); i++) {
            if (listDSDV.get(i).getButtonDV() == event.getSource()) {
                int sl = listDSDV.get(i).getSoLuong().getValue();
                sl = sl + 1;
                listDSDV.get(i).getSoLuong().setValue(sl);
            }
        }
        txttongtien.setText(TongTien().toPlainString());
    }

    @FXML
    public void PressNO(ActionEvent event) {
        primaryStage.close();
    }

    public BigDecimal TongTien() {
        tongtien = 0;
        for (int i = 0; i < listDSDV.size(); i++) {
            int sl = listDSDV.get(i).getSoLuong().getValue();
            Double giaDV = new BigDecimal(listDSDV.get(i).getGiaDV().getText()).doubleValue();
            tongtien = tongtien + sl * giaDV;
        }
        return BigDecimal.valueOf(tongtien);
    }

    @FXML
    void PresschooseNumber(ActionEvent event) {
        txttongtien.setText(TongTien().toPlainString());
    }

    @FXML
    public void PressYES(ActionEvent event) throws SQLException {

        int MaPhieuDV = 0;
        db.rs = db.stmt.executeQuery("Select top 1 * from [dbo].[phieudv] ORDER BY [MaPhieuDV] DESC");
        while (db.rs.next())
            MaPhieuDV = db.rs.getInt("MaPhieuDV");

        for (int i = 0; i < listDSDV.size(); i++) {
            for (int j = 0; j < listDSDV_ThucDon.size(); j++) {
                if (listDSDV.get(i).getSoLuong().getValue() != 0 &&
                        listDSDV.get(i).getTenDV().getText().equals(listDSDV_ThucDon.get(j).getTenDV())) {
                    Long dongiaDV = listDSDV_ThucDon.get(j).getDonGiaDV().longValue();
                    int sl = listDSDV.get(i).getSoLuong().getValue();

                    BigDecimal tongtien = BigDecimal.valueOf(dongiaDV * sl);
                    db.addPDV(MaPhieuDV + 1, phongdat.getMaP(), listDSDV_ThucDon.get(j).getMaDV(),
                            tongtien, listDSDV.get(i).getSoLuong().getValue(),
                            Date.valueOf(LocalDateTime.now().toLocalDate()),
                            BigDecimal.valueOf(dongiaDV));
                }
            }
        }

        String sql = "SELECT * FROM  dbo.[hoadon] where MaPhong = ? and NgayInHD = ?";
        PreparedStatement stmt = db.conn.prepareStatement(sql);
        stmt.setInt(1, phongdat.getMaP());
        stmt.setDate(2, phongdat.getNgayTraPhong());
        stmt.execute();

        db.rs = stmt.executeQuery();
        BigDecimal TienHD = null;
        while (db.rs.next()) {
            TienHD = db.rs.getBigDecimal("TongTien");
        }

        double TT = TienHD.doubleValue() + TongTien().doubleValue();
        TienHD = BigDecimal.valueOf(TT);

        db.updateHD(phongdat.getMaP(), TienHD, phongdat.getNgayTraPhong());
        JOptionPane.showMessageDialog(null, "Đặt dịch vụ thành công !");
        primaryStage.close();
    }

    @FXML
    private AnchorPane anpTab;

    @FXML
    private TextField txttongtien;

    @FXML
    private Label lblName1, lblName2, lblName3, lblName4, lblName5, lblName6, lblName7, lblName8, lblName9, lblName10,
            lblName11, lblName12, lblName13, lblName14, lblName15, lblName16, lblName17, lblName18, lblName19;

    @FXML
    private Button btnDV1, btnDV2, btnDV3, btnDV4, btnDV5, btnDV6, btnDV7, btnDV8, btnDV9, btnDV10, btnDV11, btnDV12,
            btnDV13, btnDV14, btnDV15, btnDV16, btnDV17, btnDV18, btnDV19;

    @FXML
    private TextField txtGia1, txtGia2, txtGia3, txtGia4, txtGia5, txtGia6, txtGia7, txtGia8, txtGia9, txtGia10,
            txtGia11, txtGia12, txtGia13, txtGia14, txtGia15, txtGia16, txtGia17, txtGia18, txtGia19;

    @FXML
    private ComboBox<Integer> cbxSL1, cbxSL2, cbxSL3, cbxSL4, cbxSL5, cbxSL6, cbxSL7, cbxSL8, cbxSL9, cbxSL10, cbxSL11,
            cbxSL12, cbxSL13, cbxSL14, cbxSL15, cbxSL16, cbxSL17, cbxSL18, cbxSL19;

}
