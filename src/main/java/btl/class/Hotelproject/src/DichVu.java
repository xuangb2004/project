public class DichVu {
    private String maDV;
    private String tenDV;
    private double donGiaDV;
    private String maLoaiDV;

    // Constructor đầy đủ
    public DichVu(String maDV, String tenDV, double donGiaDV, String maLoaiDV) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.donGiaDV = donGiaDV;
        this.maLoaiDV = maLoaiDV;
    }

    // Getters và Setters
    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public double getDonGiaDV() {
        return donGiaDV;
    }

    public void setDonGiaDV(double donGiaDV) {
        this.donGiaDV = donGiaDV;
    }

    public String getMaLoaiDV() {
        return maLoaiDV;
    }

    public void setMaLoaiDV(String maLoaiDV) {
        this.maLoaiDV = maLoaiDV;
    }
}

