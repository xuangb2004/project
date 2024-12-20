public class LoaiPhong {
    private String maLP;
    private String tenLoaiPhong;

    // Constructor đầy đủ
    public LoaiPhong(String maLP, String tenLoaiPhong) {
        this.maLP = maLP;
        this.tenLoaiPhong = tenLoaiPhong;
    }

    // Getters và Setters
    public String getMaLP() {
        return maLP;
    }

    public void setMaLP(String maLP) {
        this.maLP = maLP;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }
}

