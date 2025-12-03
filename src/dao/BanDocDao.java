package dao;

import java.util.ArrayList;
import java.util.List;

import model.banDoc;
import utils.DatabaseConnection;
import java.sql.*;

public class BanDocDao {
	
	public List<banDoc> getAll() {
        List<banDoc> list = new ArrayList<>();
        String sql = "SELECT * FROM bandoc ORDER BY hoTen";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                banDoc bd = new banDoc(
                    rs.getInt("maBanDoc"), rs.getString("hoTen"), rs.getString("lop"),
                    rs.getString("sdt"), rs.getString("email"),
                    rs.getDate("ngaySinh"), rs.getDate("ngayDangKy")
                );
                list.add(bd);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
	
	public boolean them(banDoc bd) {
        String sql = "INSERT INTO bandoc(hoTen, lop, sdt, email, ngaySinh) VALUES(?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, bd.getHoTen());
            ps.setString(2, bd.getLop());
            ps.setString(3, bd.getSdt());
            ps.setString(4, bd.getEmail());
            ps.setDate(5, bd.getNgaySinh());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
	
	public boolean sua(banDoc bd) {
        String sql = "UPDATE bandoc SET hoTen=?, lop=?, sdt=?, email=?, ngaySinh=? WHERE maBanDoc=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, bd.getHoTen()); ps.setString(2, bd.getLop());
            ps.setString(3, bd.getSdt()); ps.setString(4, bd.getEmail());
            ps.setDate(5, bd.getNgaySinh()); ps.setInt(6, bd.getMaBanDoc());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
	
	public boolean xoa(int maBanDoc) {
        String sql = "DELETE FROM bandoc WHERE maBanDoc = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maBanDoc);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
