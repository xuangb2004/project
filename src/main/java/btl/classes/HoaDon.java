package btl.classes;

public class HoaDon {
    private int maHD;
    private int maPhong;
    private String ngayInHD;
    private double tongTien;
    private int maNV;

    // Constructor đầy đủ
    public HoaDon(int maHD, int maPhong, String ngayInHD, double tongTien, int maNV) {
        this.maHD = maHD;
        this.maPhong = maPhong;
        this.ngayInHD = ngayInHD;
        this.tongTien = tongTien;
        this.maNV = maNV;
    }

    // Getters và Setters
    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public String getNgayInHD() {
        return ngayInHD;
    }

    public void setNgayInHD(String ngayInHD) {
        this.ngayInHD = ngayInHD;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }
}
