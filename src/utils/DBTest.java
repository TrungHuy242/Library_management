package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        // Dòng này để load driver MySQL 8+ (bắt buộc với connector 8.0 trở lên)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy MySQL Driver! Kiểm tra lại file JAR.");
            e.printStackTrace();
            return;
        }

        String url = "jdbc:mysql://localhost:3306/mysql?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = ""; // để trống nếu bạn chưa đặt pass

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("KẾT NỐI MYSQL THÀNH CÔNG!!!");
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại! Kiểm tra lại:");
            System.out.println("- XAMPP MySQL đang chạy chưa?");
            System.out.println("- Port 3306 có bị chiếm không?");
            e.printStackTrace();
        }
    }
}