package controller;

import model.tacGia;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TacGiaController {
    public HashMap<Integer, String> layTatCaMap() {
        HashMap<Integer, String> map = new HashMap<>();
        String sql = "SELECT maTacGia, tenTacGia FROM tacgia ORDER BY tenTacGia";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getInt(1), rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<tacGia> layTatCa() {
        List<tacGia> ds = new ArrayList<>();
        String sql = "SELECT maTacGia, tenTacGia, namSinh, quocTich FROM tacgia ORDER BY tenTacGia";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tacGia tg = new tacGia();
                tg.setMaTacGia(rs.getInt("maTacGia"));
                tg.setTenTacGia(rs.getString("tenTacGia"));
                tg.setNamSinh(rs.getObject("namSinh") != null ? rs.getInt("namSinh") : null);
                tg.setQuocTich(rs.getString("quocTich"));
                ds.add(tg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public tacGia layTheoMa(int ma) {
        String sql = "SELECT maTacGia, tenTacGia, namSinh, quocTich FROM tacgia WHERE maTacGia = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, ma);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tacGia tg = new tacGia();
                tg.setMaTacGia(rs.getInt("maTacGia"));
                tg.setTenTacGia(rs.getString("tenTacGia"));
                tg.setNamSinh(rs.getObject("namSinh") != null ? rs.getInt("namSinh") : null);
                tg.setQuocTich(rs.getString("quocTich"));
                return tg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean them(tacGia tg) {
        String sql = "INSERT INTO tacgia(tenTacGia, namSinh, quocTich) VALUES(?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tg.getTenTacGia());
            ps.setObject(2, tg.getNamSinh());
            ps.setString(3, tg.getQuocTich());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(tacGia tg) {
        String sql = "UPDATE tacgia SET tenTacGia = ?, namSinh = ?, quocTich = ? WHERE maTacGia = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tg.getTenTacGia());
            ps.setObject(2, tg.getNamSinh());
            ps.setString(3, tg.getQuocTich());
            ps.setInt(4, tg.getMaTacGia());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(int maTacGia) {
        // Kiểm tra xem có sách nào đang sử dụng tác giả này không
        String checkSql = "SELECT COUNT(*) FROM sach WHERE maTacGia = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(checkSql)) {
            ps.setInt(1, maTacGia);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new RuntimeException("Không thể xóa tác giả này vì đang có sách sử dụng!");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Không thể xóa")) {
                throw new RuntimeException(e.getMessage());
            }
            e.printStackTrace();
            return false;
        }

        String sql = "DELETE FROM tacgia WHERE maTacGia = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maTacGia);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}