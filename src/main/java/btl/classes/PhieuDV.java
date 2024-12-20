package btl.classes;

public class PhieuDV {
    private int maPhieuDV;
    private int maPhong;
    private String maDV;
    private String ngaySD;
    private double tienDV;
    private int soLuong;
    private double giaDV;

    // Constructor đầy đủ
    public PhieuDV(int maPhieuDV, int maPhong, String maDV, String ngaySD, double tienDV, int soLuong, double giaDV) {
        this.maPhieuDV = maPhieuDV;
        this.maPhong = maPhong;
        this.maDV = maDV;
        this.ngaySD = ngaySD;
        this.tienDV = tienDV;
        this.soLuong = soLuong;
        this.giaDV = giaDV;
    }

    // Getters và Setters
    public int getMaPhieuDV() {
        return maPhieuDV;
    }

    public void setMaPhieuDV(int maPhieuDV) {
        this.maPhieuDV = maPhieuDV;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public String getNgaySD() {
        return ngaySD;
    }

    public void setNgaySD(String ngaySD) {
        this.ngaySD = ngaySD;
    }

    public double getTienDV() {
        return tienDV;
    }

    public void setTienDV(double tienDV) {
        this.tienDV = tienDV;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaDV() {
        return giaDV;
    }

    public void setGiaDV(double giaDV) {
        this.giaDV = giaDV;
    }
}
