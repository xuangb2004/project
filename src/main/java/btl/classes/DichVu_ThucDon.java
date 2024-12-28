package btl.classes;

import java.math.BigDecimal;

public class DichVu_ThucDon {
    private String tenDV;
    private BigDecimal DonGiaDV;
    private String MaDV;
    private String MaLoaiDV;
    private int MaPhongDat;

    public int getMaPhongDat() {
        return MaPhongDat;
    }

    public void setMaPhongDat(int maPhongDat) {
        MaPhongDat = maPhongDat;
    }

    public DichVu_ThucDon(String tenDV, BigDecimal donGiaDV, String maDV, String maLoaiDV) {
        this.tenDV = tenDV;
        DonGiaDV = donGiaDV;
        MaDV = maDV;
        MaLoaiDV = maLoaiDV;
    }

    public String getMaLoaiDV() {
        return MaLoaiDV;
    }

    public void setMaLoaiDV(String maLoaiDV) {
        MaLoaiDV = maLoaiDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public BigDecimal getDonGiaDV() {
        return DonGiaDV;
    }

    public void setDonGiaDV(BigDecimal donGiaDV) {
        DonGiaDV = donGiaDV;
    }

    public String getMaDV() {
        return MaDV;
    }

    public void setMaDV(String maDV) {
        MaDV = maDV;
    }
}
