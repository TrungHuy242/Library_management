package dao;

import model.tacGia;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacGiaDAO {
	public List<tacGia> getAll() {
        List<tacGia> list = new ArrayList<>();
        String sql = "SELECT * FROM tacgia ORDER BY tenTacGia";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new tacGia(rs.getInt("maTacGia"), rs.getString("tenTacGia"),
                        rs.getObject("namSinh") != null ? rs.getInt("namSinh") : null,
                        rs.getString("quocTich")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
	
	public boolean them(tacGia tg) {
        String sql = "INSERT INTO tacgia(tenTacGia, namSinh, quocTich) VALUES(?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tg.getTenTacGia());
            ps.setObject(2, tg.getNamSinh());
            ps.setString(3, tg.getQuocTich());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
