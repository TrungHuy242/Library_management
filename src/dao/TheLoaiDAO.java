package dao;

import model.theLoai;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TheLoaiDAO {
	public List<theLoai> getAll() {
        List<theLoai> list = new ArrayList<>();
        String sql = "SELECT * FROM theloai ORDER BY tenTheLoai";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new theLoai(rs.getInt("maTheLoai"), rs.getString("tenTheLoai")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
	
	public boolean them(theLoai tl) {
        String sql = "INSERT INTO theloai(tenTheLoai) VALUES(?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tl.getTenTheLoai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
