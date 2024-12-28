package btl.classes;
public class ChucVu {
    private int maCV;           
    private String tenChucVu;   
    private double heSoLuong;   

   
    public ChucVu(int maCV, String tenChucVu, double heSoLuong) {
        this.maCV = maCV;
        this.tenChucVu = tenChucVu;
        this.heSoLuong = heSoLuong;
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

    public double getHeSoLuong() {
        return heSoLuong;
    }

    public void setHeSoLuong(double heSoLuong) {
        this.heSoLuong = heSoLuong;
    }
}

