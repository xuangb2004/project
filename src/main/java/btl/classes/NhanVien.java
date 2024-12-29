package btl.classes;

import java.sql.Date;

public class NhanVien {
    private int stt;
    private int maNV;
    private String tenNV;
    private String ngaySinh;
    private String cmnd;
    private String sdt;
    private double luongCoBan;
    private String trangThai;
    private String gioiTinh;
    private String email;
    private Date ngayGiaNhap;
    private int maCLV;
    private int maCV;
    private String tenChucVu;
    private String tenCLV;

    public NhanVien() {
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgayGiaNhap() {
        return ngayGiaNhap;
    }

    public void setNgayGiaNhap(Date ngayGiaNhap) {
        this.ngayGiaNhap = ngayGiaNhap;
    }

    public int getMaCLV() {
        return maCLV;
    }

    public void setMaCLV(int maCLV) {
        this.maCLV = maCLV;
    }

    public int getMaCV() {
        return maCV;
    }

    public void setMaCV(int maCV) {
        this.maCV = maCV;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public String getTenCLV() {
        return tenCLV;
    }

    public void setTenCLV(String tenCLV) {
        this.tenCLV = tenCLV;
    }
}
