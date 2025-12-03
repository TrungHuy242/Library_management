package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.sach;
import utils.DatabaseConnection;

public class SachDAO {
	public List<sach> getAll(){
		List<sach> list = new ArrayList<>();
		String sql = """
                SELECT s.*, tg.tenTacGia, tl.tenTheLoai 
                FROM sach s
                LEFT JOIN tacgia tg ON s.maTacGia = tg.maTacGia
                LEFT JOIN theloai tl ON s.maTheLoai = tl.maTheLoai
                ORDER BY s.tenSach
                """;
		try (Connection c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()){
			while (rs.next()) {
				sach s = new sach(
						rs.getInt("maSach"), rs.getString("tenSach"),
						rs.getInt("maTacGia"), rs.getString("tenTacGia"),
						rs.getInt("maTheLoai"), rs.getString("tenTheLoai"),
						rs.getObject("namXuatBan") != null ? rs.getInt("namXuatBan") : null,
						rs.getString("nhaXuatBan"),
						rs.getInt("soLuongTong"), rs.getInt("soLuongHienTai"),
						rs.getString("trangThai")
						);
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public boolean themSach(sach s) {
		String sql = "INSERT INTO sach(tenSach, maTacGia, maTheLoai, namXuatBan, nhaXuatBan, soLuongTong, soLuongHienTai) VALUES(?,?,?,?,?,?,?)";
		try (Connection c = DatabaseConnection.getConnection();
			PreparedStatement ps =  c.prepareStatement(sql)){
			ps.setString(1, s.getTenSach());
            ps.setInt(2, s.getMaTacGia());
            ps.setInt(3, s.getMaTheLoai());
            ps.setObject(4, s.getNamXuatBan());
            ps.setString(5, s.getNhaXuatBan());
            ps.setInt(6, s.getSoLuongTong());
            ps.setInt(7, s.getSoLuongTong());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean suaSach(sach s) {
        String sql = "UPDATE sach SET tenSach=?, maTacGia=?, maTheLoai=?, namXuatBan=?, nhaXuatBan=?, soLuongTong=?, soLuongHienTai=?, trangThai=? WHERE maSach=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getTenSach());
            ps.setInt(2, s.getMaTacGia());
            ps.setInt(3, s.getMaTheLoai());
            ps.setObject(4, s.getNamXuatBan());
            ps.setString(5, s.getNhaXuatBan());
            ps.setInt(6, s.getSoLuongTong());
            ps.setInt(7, s.getSoLuongHienTai());
            ps.setString(8, s.getTrangThai());
            ps.setInt(9, s.getMaSach());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { 
        	e.printStackTrace();
        	return false;
        	}
    }
	
	public boolean xoaSach(int maSach) {
		String sql ="DELETE FROM sach WHERE maSach = ?";
		try (Connection c = DatabaseConnection.getConnection();
	             PreparedStatement ps = c.prepareStatement(sql)) {
	            ps.setInt(1, maSach);
	            return ps.executeUpdate() > 0;
	        } catch (SQLException e) { 
	        	e.printStackTrace();
	        	return false;
	        	}
	}
	
	public List<sach> timKiem(String keyword) {
        List<sach> list = new ArrayList<>();
        String sql = "SELECT s.*, tg.tenTacGia, tl.tenTheLoai FROM sach s LEFT JOIN tacgia tg ON s.maTacGia = tg.maTacGia LEFT JOIN theloai tl ON s.maTheLoai = tl.maTheLoai WHERE s.tenSach LIKE ? OR tg.tenTacGia LIKE ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sach s = new sach(rs.getInt("maSach"), rs.getString("tenSach"), rs.getInt("maTacGia"), rs.getString("tenTacGia"),
                        rs.getInt("maTheLoai"), rs.getString("tenTheLoai"), rs.getObject("namXuatBan") != null ? rs.getInt("namXuatBan") : null,
                        rs.getString("nhaXuatBan"), rs.getInt("soLuongTong"), rs.getInt("soLuongHienTai"), rs.getString("trangThai"));
                list.add(s);
            }
        } catch (SQLException e) { 
        	e.printStackTrace(); 
        	}
        return list;
    }
	
}
