public class PhieuDatPhong {
    private int maPDP;
    private int maPhong;
    private int maKhach;
    private int maNV;
    private String ngayDatPhong;
    private String traPhong;
    private double donGiaThue;
    private double donGiaPhong;

    // Constructor đầy đủ
    public PhieuDatPhong(int maPDP, int maPhong, int maKhach, int maNV, String ngayDatPhong, String traPhong, double donGiaThue, double donGiaPhong) {
        this.maPDP = maPDP;
        this.maPhong = maPhong;
        this.maKhach = maKhach;
        this.maNV = maNV;
        this.ngayDatPhong = ngayDatPhong;
        this.traPhong = traPhong;
        this.donGiaThue = donGiaThue;
        this.donGiaPhong = donGiaPhong;
    }

    // Getters và Setters
    public int getMaPDP() {
        return maPDP;
    }

    public void setMaPDP(int maPDP) {
        this.maPDP = maPDP;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public int getMaKhach() {
        return maKhach;
    }

    public void setMaKhach(int maKhach) {
        this.maKhach = maKhach;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getNgayDatPhong() {
        return ngayDatPhong;
    }

    public void setNgayDatPhong(String ngayDatPhong) {
        this.ngayDatPhong = ngayDatPhong;
    }

    public String getTraPhong() {
        return traPhong;
    }

    public void setTraPhong(String traPhong) {
        this.traPhong = traPhong;
    }

    public double getDonGiaThue() {
        return donGiaThue;
    }

    public void setDonGiaThue(double donGiaThue) {
        this.donGiaThue = donGiaThue;
    }

    public double getDonGiaPhong() {
        return donGiaPhong;
    }

    public void setDonGiaPhong(double donGiaPhong) {
        this.donGiaPhong = donGiaPhong;
    }
}

