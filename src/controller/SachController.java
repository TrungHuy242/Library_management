package controller;

import model.sach;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SachController {

    public List<sach> layTatCaSach() {
        List<sach> ds = new ArrayList<>();
        String sql = """
            SELECT s.*, tg.tenTacGia, tl.tenTheLoai 
            FROM sach s 
            LEFT JOIN tacgia tg ON s.maTacGia = tg.maTacGia
            LEFT JOIN theloai tl ON s.maTheLoai = tl.maTheLoai
            ORDER BY s.maSach DESC""";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sach s = new sach();
                s.setMaSach(rs.getInt("maSach"));
                s.setTenSach(rs.getString("tenSach"));
                s.setMaTacGia(rs.getObject("maTacGia") != null ? rs.getInt("maTacGia") : null);
                s.setMaTheLoai(rs.getObject("maTheLoai") != null ? rs.getInt("maTheLoai") : null);
                s.setTenTacGia(rs.getString("tenTacGia"));
                s.setTenTheLoai(rs.getString("tenTheLoai"));
                s.setNamXuatBan(rs.getObject("namXuatBan") != null ? rs.getInt("namXuatBan") : null);
                s.setNhaXuatBan(rs.getString("nhaXuatBan"));
                s.setSoLuongTong(rs.getInt("soLuongTong"));
                s.setSoLuongHienTai(rs.getInt("soLuongHienTai"));
                s.setTrangThai(rs.getString("trangThai"));
                ds.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<sach> timKiemSach(String keyword) {
        List<sach> ds = new ArrayList<>();
        String sql = """
            SELECT s.*, tg.tenTacGia, tl.tenTheLoai 
            FROM sach s 
            LEFT JOIN tacgia tg ON s.maTacGia = tg.maTacGia
            LEFT JOIN theloai tl ON s.maTheLoai = tl.maTheLoai
            WHERE s.tenSach LIKE ? OR tg.tenTacGia LIKE ? OR tl.tenTheLoai LIKE ?""";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sach s = new sach();
                s.setMaSach(rs.getInt("maSach"));
                s.setTenSach(rs.getString("tenSach"));
                s.setMaTacGia(rs.getObject("maTacGia") != null ? rs.getInt("maTacGia") : null);
                s.setMaTheLoai(rs.getObject("maTheLoai") != null ? rs.getInt("maTheLoai") : null);
                s.setTenTacGia(rs.getString("tenTacGia"));
                s.setTenTheLoai(rs.getString("tenTheLoai"));
                s.setNamXuatBan(rs.getObject("namXuatBan") != null ? rs.getInt("namXuatBan") : null);
                s.setNhaXuatBan(rs.getString("nhaXuatBan"));
                s.setSoLuongTong(rs.getInt("soLuongTong"));
                s.setSoLuongHienTai(rs.getInt("soLuongHienTai"));
                s.setTrangThai(rs.getString("trangThai"));
                ds.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public sach layTheoMa(int ma) {
        return layTatCaSach().stream().filter(s -> s.getMaSach() == ma).findFirst().orElse(null);
    }

    public boolean them(sach s) {
        String sql = "INSERT INTO sach(tenSach, maTacGia, maTheLoai, namXuatBan, nhaXuatBan, soLuongTong, soLuongHienTai) VALUES(?,?,?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getTenSach());
            ps.setObject(2, s.getMaTacGia());
            ps.setObject(3, s.getMaTheLoai());
            ps.setObject(4, s.getNamXuatBan());
            ps.setString(5, s.getNhaXuatBan());
            ps.setInt(6, s.getSoLuongTong());
            ps.setInt(7, s.getSoLuongHienTai());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(sach s) {
        String sql = "UPDATE sach SET tenSach=?, maTacGia=?, maTheLoai=?, namXuatBan=?, nhaXuatBan=?, soLuongTong=? WHERE maSach=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getTenSach());
            ps.setObject(2, s.getMaTacGia());
            ps.setObject(3, s.getMaTheLoai());
            ps.setObject(4, s.getNamXuatBan());
            ps.setString(5, s.getNhaXuatBan());
            ps.setInt(6, s.getSoLuongTong());
            ps.setInt(7, s.getMaSach());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(int maSach) {
        String sql = "DELETE FROM sach WHERE maSach = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maSach);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}