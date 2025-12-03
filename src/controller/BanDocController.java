package controller;

import dao.BanDocDao;
import model.banDoc;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class BanDocController {
	private BanDocDao dao = new BanDocDao();

	public List<banDoc> layTatCa() {
		return dao.getAll();
	}

	public boolean them(String hoTen, String lop, String sdt, String email, Date ngaySinh) {
		banDoc bd = new banDoc();
		bd.setHoTen(hoTen);
		bd.setLop(lop);
		bd.setSdt(sdt);
		bd.setEmail(email);
		bd.setNgaySinh(ngaySinh);
		return dao.them(bd);
	}

	public boolean sua(int ma, String hoTen, String lop, String sdt, String email, Date ngaySinh) {
		banDoc bd = new banDoc();
		bd.setMaBanDoc(ma);
		bd.setHoTen(hoTen);
		bd.setLop(lop);
		bd.setSdt(sdt);
		bd.setEmail(email);
		bd.setNgaySinh(ngaySinh);
		return dao.sua(bd);
	}

	public boolean xoa(int maBanDoc) {
		return dao.xoa(maBanDoc);
	}

	public List<banDoc> timKiem(String keyword) {
		List<banDoc> ds = new ArrayList<>();
		String sql = "SELECT * FROM bandoc WHERE " +
				"hoTen LIKE ? OR lop LIKE ? OR sdt LIKE ? OR email LIKE ?";
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			String like = "%" + keyword + "%";
			for (int i = 1; i <= 4; i++)
				ps.setString(i, like);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				banDoc bd = new banDoc();
				bd.setMaBanDoc(rs.getInt("maBanDoc"));
				bd.setHoTen(rs.getString("hoTen"));
				bd.setLop(rs.getString("lop"));
				bd.setSdt(rs.getString("sdt"));
				bd.setEmail(rs.getString("email"));
				bd.setNgaySinh(rs.getDate("ngaySinh"));
				bd.setNgayDangKy(rs.getDate("ngayDangKy"));
				ds.add(bd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	public banDoc layTheoMa(int maBanDoc) {
		String sql = "SELECT * FROM bandoc WHERE maBanDoc = ?";
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, maBanDoc);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				banDoc bd = new banDoc();
				bd.setMaBanDoc(rs.getInt("maBanDoc"));
				bd.setHoTen(rs.getString("hoTen"));
				bd.setLop(rs.getString("lop"));
				bd.setSdt(rs.getString("sdt"));
				bd.setEmail(rs.getString("email"));
				bd.setNgaySinh(rs.getDate("ngaySinh"));
				bd.setNgayDangKy(rs.getDate("ngayDangKy"));
				return bd;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean them(banDoc bd) {
		String sql = "INSERT INTO bandoc (hoTen, lop, sdt, email, ngaySinh) VALUES (?, ?, ?, ?, ?)";
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, bd.getHoTen());
			ps.setString(2, bd.getLop());
			ps.setString(3, bd.getSdt());
			ps.setString(4, bd.getEmail());
			if (bd.getNgaySinh() != null) {
				ps.setDate(5, new java.sql.Date(bd.getNgaySinh().getTime()));
			} else {
				ps.setNull(5, Types.DATE);
			}
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean sua(banDoc bd) {
		String sql = "UPDATE bandoc SET hoTen=?, lop=?, sdt=?, email=?, ngaySinh=? WHERE maBanDoc=?";
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, bd.getHoTen());
			ps.setString(2, bd.getLop());
			ps.setString(3, bd.getSdt());
			ps.setString(4, bd.getEmail());
			if (bd.getNgaySinh() != null) {
				ps.setDate(5, new java.sql.Date(bd.getNgaySinh().getTime()));
			} else {
				ps.setNull(5, Types.DATE);
			}
			ps.setInt(6, bd.getMaBanDoc());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
