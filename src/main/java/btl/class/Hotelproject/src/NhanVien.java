public class NhanVien {
    private int maNV;
    private String tenNV;
    private String ngaySinh;
    private String cmnd;
    private String sdt;
    private double luongCoBan;
    private String trangThai;
    private String gioiTinh;
    private String user;
    private String password;
    private String email;
    private String ngayGiaNhap;
    private String hinhAnh;
    private int maCLV;
    private int maCV;

    // Constructor đầy đủ
    public NhanVien(int maNV, String tenNV, String ngaySinh, String cmnd, String sdt, double luongCoBan,
                    String trangThai, String gioiTinh, String user, String password, String email,
                    String ngayGiaNhap, String hinhAnh, int maCLV, int maCV) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngaySinh = ngaySinh;
        this.cmnd = cmnd;
        this.sdt = sdt;
        this.luongCoBan = luongCoBan;
        this.trangThai = trangThai;
        this.gioiTinh = gioiTinh;
        this.user = user;
        this.password = password;
        this.email = email;
        this.ngayGiaNhap = ngayGiaNhap;
        this.hinhAnh = hinhAnh;
        this.maCLV = maCLV;
        this.maCV = maCV;
    }

    // Getters và Setters
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgayGiaNhap() {
        return ngayGiaNhap;
    }

    public void setNgayGiaNhap(String ngayGiaNhap) {
        this.ngayGiaNhap = ngayGiaNhap;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
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
}

