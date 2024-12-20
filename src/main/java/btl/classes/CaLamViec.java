package btl.classes;

public class CaLamViec {
    private int maCLV;
    private String tenCLV;
    private String gioBatDau;
    private String gioKetThuc;

    // Constructor đầy đủ
    public CaLamViec(int maCLV, String tenCLV, String gioBatDau, String gioKetThuc) {
        this.maCLV = maCLV;
        this.tenCLV = tenCLV;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
    }

    // Getters và Setters
    public int getMaCLV() {
        return maCLV;
    }

    public void setMaCLV(int maCLV) {
        this.maCLV = maCLV;
    }

    public String getTenCLV() {
        return tenCLV;
    }

    public void setTenCLV(String tenCLV) {
        this.tenCLV = tenCLV;
    }

    public String getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(String gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public String getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(String gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }
}

