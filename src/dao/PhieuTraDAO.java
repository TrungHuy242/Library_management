package dao;

import model.CTPhieuTra;
import model.PhieuTra;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuTraDAO {
	public boolean taoPhieuTra(int maPhieuMuon, int maNV, List<CTPhieuTra> chiTietList, float tienPhat, String ghiChu) {
        String sqlPhieu = "INSERT INTO phieutra(maPhieuMuon, maNV, tienPhat, ghiChu) VALUES(?,?,?,?)";
        String sqlCT = "INSERT INTO ct_phieutra(maPhieuTra, maSach, soLuong, tinhTrangSach) VALUES(?,?,?,?)";
        String sqlUpdateSach = "UPDATE sach SET soLuongHienTai = soLuongHienTai + ? WHERE maSach = ?";
        String sqlUpdatePM = "UPDATE phieumuon SET trangThai = 'Đã trả' WHERE maPhieuMuon = ?";

        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false);

            // 1. Tạo phiếu trả
            try (PreparedStatement psPhieu = c.prepareStatement(sqlPhieu, Statement.RETURN_GENERATED_KEYS)) {
                psPhieu.setInt(1, maPhieuMuon);
                psPhieu.setInt(2, maNV);
                psPhieu.setFloat(3, tienPhat);
                psPhieu.setString(4, ghiChu);
                psPhieu.executeUpdate();

                ResultSet rs = psPhieu.getGeneratedKeys();
                if (!rs.next()) throw new SQLException("Không tạo được phiếu trả");
                int maPhieuTra = rs.getInt(1);

                // 2. Thêm chi tiết + cập nhật số lượng sách
                try (PreparedStatement psCT = c.prepareStatement(sqlCT);
                     PreparedStatement psSach = c.prepareStatement(sqlUpdateSach)) {
                    for (CTPhieuTra ct : chiTietList) {
                        psCT.setInt(1, maPhieuTra);
                        psCT.setInt(2, ct.getMaSach());
                        psCT.setInt(3, ct.getSoLuong());
                        psCT.setString(4, ct.getTinhTrangSach());
                        psCT.addBatch();

                        psSach.setInt(1, ct.getSoLuong());
                        psSach.setInt(2, ct.getMaSach());
                        psSach.addBatch();
                    }
                    psCT.executeBatch();
                    psSach.executeBatch();
                }

                // 3. Cập nhật trạng thái phiếu mượn
                try (PreparedStatement psPM = c.prepareStatement(sqlUpdatePM)) {
                    psPM.setInt(1, maPhieuMuon);
                    psPM.executeUpdate();
                }

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<PhieuTra> getAll() {
        List<PhieuTra> list = new ArrayList<>();
        String sql = """
            SELECT pt.*, nv.hoTen AS tenNhanVien, pm.maBanDoc, bd.hoTen AS tenBanDoc
            FROM phieutra pt
            JOIN nhanvien nv ON pt.maNV = nv.maNV
            JOIN phieumuon pm ON pt.maPhieuMuon = pm.maPhieuMuon
            JOIN bandoc bd ON pm.maBanDoc = bd.maBanDoc
            ORDER BY pt.ngayTra DESC, pt.maPhieuTra DESC
            """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PhieuTra pt = new PhieuTra();
                pt.setMaPhieuTra(rs.getInt("maPhieuTra"));
                pt.setMaPhieuMuon(rs.getInt("maPhieuMuon"));
                pt.setMaNV(rs.getInt("maNV"));
                pt.setTenNhanVien(rs.getString("tenNhanVien"));
                pt.setMaBanDoc(rs.getInt("maBanDoc"));
                pt.setTenBanDoc(rs.getString("tenBanDoc"));
                pt.setNgayTra(rs.getDate("ngayTra"));
                pt.setTienPhat(rs.getFloat("tienPhat"));
                pt.setGhiChu(rs.getString("ghiChu"));
                list.add(pt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<CTPhieuTra> getChiTietByMaPhieuTra(int maPhieuTra) {
        List<CTPhieuTra> list = new ArrayList<>();
        String sql = """
            SELECT ct.*, s.tenSach
            FROM ct_phieutra ct
            JOIN sach s ON ct.maSach = s.maSach
            WHERE ct.maPhieuTra = ?
            """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maPhieuTra);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CTPhieuTra ct = new CTPhieuTra();
                ct.setMaPhieuTra(rs.getInt("maPhieuTra"));
                ct.setMaSach(rs.getInt("maSach"));
                ct.setTenSach(rs.getString("tenSach"));
                ct.setSoLuong(rs.getInt("soLuong"));
                ct.setTinhTrangSach(rs.getString("tinhTrangSach"));
                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
