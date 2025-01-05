package btl.classes;

import java.sql.Date;

public class GiaoDich {
    private int maGD;
    private String loaiGD;
    private Date ngayGD;
    private double tongTien;
    private String trangThai;

    public GiaoDich(int maGD, String loaiGD, Date ngayGD, double tongTien, String trangThai) {
        this.maGD = maGD;
        this.loaiGD = loaiGD;
        this.ngayGD = ngayGD;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public int getMaGD() {
        return maGD;
    }

    public void setMaGD(int maGD) {
        this.maGD = maGD;
    }

    public String getLoaiGD() {
        return loaiGD;
    }

    public void setLoaiGD(String loaiGD) {
        this.loaiGD = loaiGD;
    }

    public Date getNgayGD() {
        return ngayGD;
    }

    public void setNgayGD(Date ngayGD) {
        this.ngayGD = ngayGD;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}