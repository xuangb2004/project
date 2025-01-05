package btl.classes;

import java.sql.Date;

public class PhieuDV {
    private int MaPhieuDV;
    private int MaPhong;
    private String TenP;
    private String MaDV;
    private String TenDV;
    private String MaLDV;
    private String TenLDV;
    private String GiaDV;
    private String TienDV;
    private int Soluong;
    private Date NgaySD;

    public PhieuDV(int maPhieuDV, int maPhong, String tenP, String maDV, String tenDV, String giaDV, String tienDV,
            int soluong, Date ngaySD) {
        MaPhieuDV = maPhieuDV;
        MaPhong = maPhong;
        TenP = tenP;
        MaDV = maDV;
        TenDV = tenDV;
        GiaDV = giaDV;
        TienDV = tienDV;
        Soluong = soluong;
        NgaySD = ngaySD;
    }

    public PhieuDV(int maPhieuDV, int maPhong, String maDV, String tienDV, int soluong, Date ngaySD) {
        MaPhieuDV = maPhieuDV;
        MaPhong = maPhong;
        MaDV = maDV;
        TienDV = tienDV;
        Soluong = soluong;
        NgaySD = ngaySD;
    }

    public PhieuDV(int maPhieuDV, int maPhong, String maDV, String tenDV, String maLDV, String tenLDV, String giaDV,
            String tienDV, int soluong, Date ngaySD) {
        MaPhieuDV = maPhieuDV;
        MaDV = maDV;
        MaPhong = maPhong;
        TenDV = tenDV;
        MaLDV = maLDV;
        TenLDV = tenLDV;
        GiaDV = giaDV;
        TienDV = tienDV;
        Soluong = soluong;
        NgaySD = ngaySD;
    }

    public String getMaLDV() {
        return MaLDV;
    }

    public void setMaLDV(String maLDV) {
        MaLDV = maLDV;
    }

    public String getTenLDV() {
        return TenLDV;
    }

    public void setTenLDV(String tenLDV) {
        TenLDV = tenLDV;
    }

    public String getGiaDV() {
        return GiaDV;
    }

    public void setGiaDV(String giaDV) {
        GiaDV = giaDV;
    }

    public String getTenP() {
        return TenP;
    }

    public void setTenP(String tenP) {
        TenP = tenP;
    }

    public String getTenDV() {
        return TenDV;
    }

    public void setTenDV(String tenDV) {
        TenDV = tenDV;
    }

    public int getMaPhieuDV() {
        return MaPhieuDV;
    }

    public void setMaPhieuDV(int maPhieuDV) {
        MaPhieuDV = maPhieuDV;
    }

    public int getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(int maPhong) {
        MaPhong = maPhong;
    }

    public String getMaDV() {
        return MaDV;
    }

    public void setMaDV(String maDV) {
        MaDV = maDV;
    }

    public String getTienDV() {
        return TienDV;
    }

    public void setTienDV(String tienDV) {
        TienDV = tienDV;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int soluong) {
        Soluong = soluong;
    }

    public Date getNgaySD() {
        return NgaySD;
    }

    public void setNgaySD(Date ngaySD) {
        NgaySD = ngaySD;
    }
}
