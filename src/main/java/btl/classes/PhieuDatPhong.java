package btl.classes;

import java.sql.Date; // Import the Date class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

public class PhieuDatPhong {
    private int MaPDP;
    private int MaPhong;
    private String tenP;
    private String maLP;
    private String tenLP;
    private int MaKHACH;
    private String tenKHACH;
    private Date NgayDatPhong;
    private Date NgayTraPhong;
    private String GiaPhong;
    private String TienTra;

    public PhieuDatPhong(int maPDP, int maPhong, String tenP, String maLP, String tenLP, int maKHACH, String tenKHACH,
            Date ngayDatPhong, Date ngayTraPhong, String giaPhong, String tienTra) {
        MaPDP = maPDP;
        MaPhong = maPhong;
        this.tenP = tenP;
        this.maLP = maLP;
        this.tenLP = tenLP;
        MaKHACH = maKHACH;
        this.tenKHACH = tenKHACH;
        NgayDatPhong = ngayDatPhong;
        NgayTraPhong = ngayTraPhong;
        GiaPhong = giaPhong;
        TienTra = tienTra;
    }

    public PhieuDatPhong(int maPDP, int maPhong, String tenP, String tenLP, String tenKHACH, Date ngayDatPhong,
            Date ngayTraPhong, String giaPhong, String tienTra) {
        MaPDP = maPDP;
        MaPhong = maPhong;
        this.tenP = tenP;
        this.tenLP = tenLP;
        this.tenKHACH = tenKHACH;
        NgayDatPhong = ngayDatPhong;
        NgayTraPhong = ngayTraPhong;
        GiaPhong = giaPhong;
        TienTra = tienTra;
    }

    public int getMaPDP() {
        return MaPDP;
    }

    public void setMaPDP(int maPDP) {
        MaPDP = maPDP;
    }

    public int getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(int maPhong) {
        MaPhong = maPhong;
    }

    public String getTenP() {
        return tenP;
    }

    public void setTenP(String tenP) {
        this.tenP = tenP;
    }

    public String getMaLP() {
        return maLP;
    }

    public void setMaLP(String maLP) {
        this.maLP = maLP;
    }

    public String getTenLP() {
        return tenLP;
    }

    public void setTenLP(String tenLP) {
        this.tenLP = tenLP;
    }

    public int getMaKHACH() {
        return MaKHACH;
    }

    public void setMaKHACH(int maKHACH) {
        MaKHACH = maKHACH;
    }

    public String getTenKHACH() {
        return tenKHACH;
    }

    public void setTenKHACH(String tenKHACH) {
        this.tenKHACH = tenKHACH;
    }

    public Date getNgayDatPhong() {
        return NgayDatPhong;
    }

    public void setNgayDatPhong(Date ngayDatPhong) {
        NgayDatPhong = ngayDatPhong;
    }

    public Date getNgayTraPhong() {
        return NgayTraPhong;
    }

    public void setNgayTraPhong(Date ngayTraPhong) {
        NgayTraPhong = ngayTraPhong;
    }

    public String getGiaPhong() {
        return GiaPhong;
    }

    public void setGiaPhong(String giaPhong) {
        GiaPhong = giaPhong;
    }

    public String getTienTra() {
        return TienTra;
    }

    public void setTienTra(String tienTra) {
        TienTra = tienTra;
    }

    public boolean isDaHuy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isDaHuy'");
    }
}
