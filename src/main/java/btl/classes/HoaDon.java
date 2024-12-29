package btl.classes;

import java.sql.Date;

public class HoaDon {
    private int MaHD;
    private int MaP;
    private String Tongtien;
    private Date NgayInHD;
    private int MaNV;
    private String TenNV;
    private int MaKHACH;
    private String TenKHACH;
    private String TenP;
    private String GiaPhong;
    private String tenLP;
    private String MaPDP;
    private Date NgayDP;
    private Date NgayTP;
    private String TienTra;

    public HoaDon(int maHD, int maP, String tongtien, Date ngayInHD, int maNV, String tenNV, String tenP) {
        MaHD = maHD;
        MaP = maP;
        Tongtien = tongtien;
        NgayInHD = ngayInHD;
        MaNV = maNV;
        TenNV = tenNV;
        TenP = tenP;
    }

    public HoaDon(HoaDon hd, int MaKHACH) {
        MaHD = hd.MaHD;
        MaP = hd.MaP;
        Tongtien = hd.Tongtien;
        NgayInHD = hd.NgayInHD;
        MaNV = hd.MaNV;
        TenNV = hd.TenNV;
        TenP = hd.TenP;
        this.MaKHACH = MaKHACH;
    }

    public HoaDon(int maHD, int maP, String tongtien, Date ngayInHD, int maNV, String tenNV, int maKHACH,
            String tenKHACH, String tenP, String giaPhong, String tenLP, String maPDP, Date ngayDP, Date ngayTP,
            String tienTra) {
        MaHD = maHD;
        MaP = maP;
        Tongtien = tongtien;
        NgayInHD = ngayInHD;
        MaNV = maNV;
        TenNV = tenNV;
        MaKHACH = maKHACH;
        TenKHACH = tenKHACH;
        TenP = tenP;
        GiaPhong = giaPhong;
        this.tenLP = tenLP;
        MaPDP = maPDP;
        NgayDP = ngayDP;
        NgayTP = ngayTP;
        TienTra = tienTra;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }

    public int getMaP() {
        return MaP;
    }

    public void setMaP(int maP) {
        MaP = maP;
    }

    public String getTongtien() {
        return Tongtien;
    }

    public void setTongtien(String tongtien) {
        Tongtien = tongtien;
    }

    public Date getNgayInHD() {
        return NgayInHD;
    }

    public void setNgayInHD(Date ngayInHD) {
        NgayInHD = ngayInHD;
    }

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int maNV) {
        MaNV = maNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String tenNV) {
        TenNV = tenNV;
    }

    public int getMaKHACH() {
        return MaKHACH;
    }

    public void setMaKHACH(int maKHACH) {
        MaKHACH = maKHACH;
    }

    public String getTenKHACH() {
        return TenKHACH;
    }

    public void setTenKHACH(String tenKHACH) {
        TenKHACH = tenKHACH;
    }

    public String getTenP() {
        return TenP;
    }

    public void setTenP(String tenP) {
        TenP = tenP;
    }

    public String getGiaPhong() {
        return GiaPhong;
    }

    public void setGiaPhong(String giaPhong) {
        GiaPhong = giaPhong;
    }

    public String getTenLP() {
        return tenLP;
    }

    public void setTenLP(String tenLP) {
        this.tenLP = tenLP;
    }

    public String getMaPDP() {
        return MaPDP;
    }

    public void setMaPDP(String maPDP) {
        MaPDP = maPDP;
    }

    public Date getNgayDP() {
        return NgayDP;
    }

    public void setNgayDP(Date ngayDP) {
        NgayDP = ngayDP;
    }

    public Date getNgayTP() {
        return NgayTP;
    }

    public void setNgayTP(Date ngayTP) {
        NgayTP = ngayTP;
    }

    public String getTienTra() {
        return TienTra;
    }

    public void setTienTra(String tienTra) {
        TienTra = tienTra;
    }
}
