package controller;

import model.theLoai;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TheLoaiController {
    public HashMap<Integer, String> layTatCaMap() {
        HashMap<Integer, String> map = new HashMap<>();
        String sql = "SELECT maTheLoai, tenTheLoai FROM theloai ORDER BY tenTheLoai";
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

    public List<theLoai> layTatCa() {
        List<theLoai> ds = new ArrayList<>();
        String sql = "SELECT maTheLoai, tenTheLoai FROM theloai ORDER BY tenTheLoai";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                theLoai tl = new theLoai();
                tl.setMaTheLoai(rs.getInt("maTheLoai"));
                tl.setTenTheLoai(rs.getString("tenTheLoai"));
                ds.add(tl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public theLoai layTheoMa(int ma) {
        String sql = "SELECT maTheLoai, tenTheLoai FROM theloai WHERE maTheLoai = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, ma);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                theLoai tl = new theLoai();
                tl.setMaTheLoai(rs.getInt("maTheLoai"));
                tl.setTenTheLoai(rs.getString("tenTheLoai"));
                return tl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean them(theLoai tl) {
        String sql = "INSERT INTO theloai(tenTheLoai) VALUES(?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tl.getTenTheLoai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry
                throw new RuntimeException("Thể loại này đã tồn tại!");
            }
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(theLoai tl) {
        String sql = "UPDATE theloai SET tenTheLoai = ? WHERE maTheLoai = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tl.getTenTheLoai());
            ps.setInt(2, tl.getMaTheLoai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry
                throw new RuntimeException("Thể loại này đã tồn tại!");
            }
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(int maTheLoai) {
        // Kiểm tra xem có sách nào đang sử dụng thể loại này không
        String checkSql = "SELECT COUNT(*) FROM sach WHERE maTheLoai = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(checkSql)) {
            ps.setInt(1, maTheLoai);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new RuntimeException("Không thể xóa thể loại này vì đang có sách sử dụng!");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Không thể xóa")) {
                throw new RuntimeException(e.getMessage());
            }
            e.printStackTrace();
            return false;
        }

        String sql = "DELETE FROM theloai WHERE maTheLoai = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maTheLoai);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}