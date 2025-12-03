package dao;

import model.CTPhieuMuon;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CTPhieuMuonDAO {
	public List<CTPhieuMuon> getByMaPhieuMuon(int maPhieuMuon) {
        List<CTPhieuMuon> list = new ArrayList<>();
        String sql = """
                SELECT ct.*, s.tenSach, tg.tenTacGia 
                FROM ct_phieumuon ct
                JOIN sach s ON ct.maSach = s.maSach
                LEFT JOIN tacgia tg ON s.maTacGia = tg.maTacGia
                WHERE ct.maPhieuMuon = ?
                """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maPhieuMuon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CTPhieuMuon ct = new CTPhieuMuon(
                    rs.getInt("maPhieuMuon"),
                    rs.getInt("maSach"),
                    rs.getString("tenSach"),
                    rs.getString("tenTacGia"),
                    rs.getInt("soLuong"),
                    rs.getString("tinhTrangTra"),
                    rs.getString("ghiChu")
                );
                list.add(ct);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
	
	public boolean them(int maPhieuMuon, int maSach, int soLuong) {
        String sql1 = "INSERT INTO ct_phieumuon(maPhieuMuon, maSach, soLuong) VALUES(?,?,?)";
        String sql2 = "UPDATE sach SET soLuongHienTai = soLuongHienTai - ? WHERE maSach = ? AND soLuongHienTai >= ?";
        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false); 

            try (PreparedStatement ps1 = c.prepareStatement(sql1);
                 PreparedStatement ps2 = c.prepareStatement(sql2)) {
                ps1.setInt(1, maPhieuMuon);
                ps1.setInt(2, maSach);
                ps1.setInt(3, soLuong);
                ps1.executeUpdate();

                ps2.setInt(1, soLuong);
                ps2.setInt(2, maSach);
                ps2.setInt(3, soLuong);
                int updated = ps2.executeUpdate();
                if (updated == 0) throw new SQLException("Không đủ sách để mượn!");

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
	
	public boolean xoa(int maPhieuMuon, int maSach) {
        String sql = "DELETE FROM ct_phieumuon WHERE maPhieuMuon = ? AND maSach = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maPhieuMuon);
            ps.setInt(2, maSach);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
