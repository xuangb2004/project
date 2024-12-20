package btl.classes;

public class Phong {
    private int maPhong;
    private String tenPhong;
    private int soNguoi;
    private double donGia;
    private String trangThai;
    private String maLP;
    private int tang;

    // Constructor đầy đủ
    public Phong(int maPhong, String tenPhong, int soNguoi, double donGia, String trangThai, String maLP, int tang) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.soNguoi = soNguoi;
        this.donGia = donGia;
        this.trangThai = trangThai;
        this.maLP = maLP;
        this.tang = tang;
    }

    // Getters và Setters
    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public int getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaLP() {
        return maLP;
    }

    public void setMaLP(String maLP) {
        this.maLP = maLP;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }
}
