package dao;

import model.nhanVien;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
	public List<nhanVien> getAll() {
        List<nhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM nhanvien ORDER BY hoTen";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new nhanVien(
                    rs.getInt("maNV"), rs.getString("hoTen"), rs.getString("sdt"),
                    rs.getString("vaiTro"), rs.getString("taiKhoan"), rs.getString("matKhau")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean them(nhanVien nv) {
        String sql = "INSERT INTO nhanvien(hoTen, sdt, vaiTro, taiKhoan, matKhau) VALUES(?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getSdt());
            ps.setString(3, nv.getVaiTro());
            ps.setString(4, nv.getTaiKhoan());
            ps.setString(5, nv.getMatKhau());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(nhanVien nv) {
        String sql = "UPDATE nhanvien SET hoTen=?, sdt=?, vaiTro=?, taiKhoan=?, matKhau=? WHERE maNV=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getSdt());
            ps.setString(3, nv.getVaiTro());
            ps.setString(4, nv.getTaiKhoan());
            ps.setString(5, nv.getMatKhau());
            ps.setInt(6, nv.getMaNV());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(int maNV) {
        // Kiểm tra xem nhân viên có đang được sử dụng trong phiếu mượn không
        String sqlCheck = "SELECT COUNT(*) FROM phieumuon WHERE maNV = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement psCheck = c.prepareStatement(sqlCheck)) {
            psCheck.setInt(1, maNV);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Không thể xóa vì đã có phiếu mượn
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        String sql = "DELETE FROM nhanvien WHERE maNV = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maNV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean kiemTraTaiKhoanTonTai(String taiKhoan, int maNVHienTai) {
        String sql = "SELECT COUNT(*) FROM nhanvien WHERE taiKhoan = ? AND maNV != ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, taiKhoan);
            ps.setInt(2, maNVHienTai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
