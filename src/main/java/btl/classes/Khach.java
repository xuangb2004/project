package btl.classes;

public class Khach {
    private int maKhach;
    private String tenKhach;
    private String ngaySinh;
    private String sdt;
    private String cmnd;
    private String email;
    private String quocTich;
    private String gioiTinh;

    // Constructor đầy đủ
    public Khach(int maKhach, String tenKhach, String ngaySinh, String sdt, String cmnd, String email, String quocTich, String gioiTinh) {
        this.maKhach = maKhach;
        this.tenKhach = tenKhach;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.cmnd = cmnd;
        this.email = email;
        this.quocTich = quocTich;
        this.gioiTinh = gioiTinh;
    }

    // Getters và Setters
    public int getMaKhach() {
        return maKhach;
    }

    public void setMaKhach(int maKhach) {
        this.maKhach = maKhach;
    }

    public String getTenKhach() {
        return tenKhach;
    }

    public void setTenKhach(String tenKhach) {
        this.tenKhach = tenKhach;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}
