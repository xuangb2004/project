package btl.database;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hotelmanagementdb";
    private static final String USER = "root";
    private static final String PASSWORD = "giangvip123";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Không thể kết nối đến database", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createTables() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            // Bảng phieudatphong
            String createPhieuDatPhong = "CREATE TABLE IF NOT EXISTS phieudatphong ("
                    + "Mã PDP INT PRIMARY KEY AUTO_INCREMENT,"
                    + "Mã Phòng INT NOT NULL,"
                    + "Tên Phòng VARCHAR(50) NOT NULL,"
                    + "Tên LP VARCHAR(50) NOT NULL,"
                    + "Khách VARCHAR(100) NOT NULL,"
                    + "Ngày đặt DATE NOT NULL,"
                    + "Ngày trả DATE NOT NULL,"
                    + "Giá Phòng DECIMAL(10,2) NOT NULL,"
                    + "Tiền trả DECIMAL(10,2) NOT NULL"
                    + ")";
            stmt.executeUpdate(createPhieuDatPhong);

            System.out.println("Tất cả các bảng đã được tạo thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}