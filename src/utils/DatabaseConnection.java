package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final String URL = "jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				System.out.println("Kết nối CSDL thành công");
			}
		} catch (ClassNotFoundException  e) {
			System.out.println("Không tìm thấy MySQL Driver");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Lỗi kết nối CSDL! Kiểm tra:");
            System.err.println("- XAMPP MySQL có đang chạy?");
            System.err.println("- Database 'library_db' đã tồn tại?");
            e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
				System.out.println("Đã đóng kết nối CSDL");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = DatabaseConnection.getConnection();
		if(conn != null) {
			System.out.println("Test kết nối thành công");
		}

	}

}
