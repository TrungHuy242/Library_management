package dao;

import model.phieuMuon;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {

    public void capNhatQuaHan() {
        String sql = "UPDATE phieumuon SET trangThai = 'Quá hạn' WHERE ngayHenTra < CURDATE() AND trangThai = 'Đang mượn'";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<phieuMuon> getAll() {
        capNhatQuaHan();
        List<phieuMuon> list = new ArrayList<>();
        String sql = """
                SELECT pm.*, bd.hoTen AS tenBanDoc, nv.hoTen AS tenNhanVien
                FROM phieumuon pm
                JOIN bandoc bd ON pm.maBanDoc = bd.maBanDoc
                JOIN nhanvien nv ON pm.maNV = nv.maNV
                ORDER BY pm.ngayMuon DESC
                """;
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new phieuMuon(
                        rs.getInt("maPhieuMuon"), rs.getInt("maBanDoc"), rs.getString("tenBanDoc"),
                        rs.getInt("maNV"), rs.getString("tenNhanVien"), rs.getString("tenBanDoc"),
                        rs.getDate("ngayMuon"), rs.getDate("ngayHenTra"), rs.getString("trangThai")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countQuaHan() {
        capNhatQuaHan();
        String sql = "SELECT COUNT(*) FROM phieumuon WHERE trangThai = 'Quá hạn'";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int taoPhieuMuon(int maBanDoc, int maNV, Date ngayHenTra) {
        String sql = "INSERT INTO phieumuon(maBanDoc, maNV, ngayHenTra) VALUES(?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, maBanDoc);
            ps.setInt(2, maNV);
            ps.setDate(3, ngayHenTra);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
