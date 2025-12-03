package controller;

import dao.*;
import model.*;
import utils.DatabaseConnection;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonController {
    private PhieuMuonDAO pmDAO = new PhieuMuonDAO();
    private CTPhieuMuonDAO ctDAO = new CTPhieuMuonDAO();

    public boolean taoPhieuMuon(int maBanDoc, int maNV, Date ngayHenTra, List<CTPhieuMuon> dsChiTiet) {
        Connection c = null;
        try {
            c = utils.DatabaseConnection.getConnection();
            c.setAutoCommit(false);

            // 1. Tạo phiếu mượn - SỬA: Dùng cùng connection
            int maPhieu = taoPhieuMuonVoiConnection(c, maBanDoc, maNV, new java.sql.Date(ngayHenTra.getTime()));
            if (maPhieu == -1)
                throw new Exception("Tạo phiếu thất bại");

            // 2. Thêm từng chi tiết + giảm số lượng sách - SỬA: Dùng cùng connection
            for (CTPhieuMuon ct : dsChiTiet) {
                ct.setMaPhieuMuon(maPhieu);
                if (!themChiTietVoiConnection(c, maPhieu, ct.getMaSach(), ct.getSoLuong())) {
                    throw new Exception("Mượn sách thất bại: " + ct.getTenSach());
                }
            }

            c.commit();
            return true;
        } catch (Exception e) {
            try {
                if (c != null)
                    c.rollback();
            } catch (SQLException ex) {
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (c != null) {
                    c.setAutoCommit(true); // Reset về auto commit
                }
            } catch (SQLException ex) {
            }
        }
    }

    // Thêm method helper để tạo phiếu mượn với connection có sẵn
    private int taoPhieuMuonVoiConnection(Connection c, int maBanDoc, int maNV, java.sql.Date ngayHenTra) {
        String sql = "INSERT INTO phieumuon(maBanDoc, maNV, ngayHenTra) VALUES(?,?,?)";
        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, maBanDoc);
            ps.setInt(2, maNV);
            ps.setDate(3, ngayHenTra);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Thêm method helper để thêm chi tiết với connection có sẵn
    private boolean themChiTietVoiConnection(Connection c, int maPhieuMuon, int maSach, int soLuong) {
        String sql1 = "INSERT INTO ct_phieumuon(maPhieuMuon, maSach, soLuong) VALUES(?,?,?)";
        String sql2 = "UPDATE sach SET soLuongHienTai = soLuongHienTai - ? WHERE maSach = ? AND soLuongHienTai >= ?";

        try (PreparedStatement ps1 = c.prepareStatement(sql1);
                PreparedStatement ps2 = c.prepareStatement(sql2)) {

            // Thêm chi tiết
            ps1.setInt(1, maPhieuMuon);
            ps1.setInt(2, maSach);
            ps1.setInt(3, soLuong);
            ps1.executeUpdate();

            // Giảm số lượng sách
            ps2.setInt(1, soLuong);
            ps2.setInt(2, maSach);
            ps2.setInt(3, soLuong);
            int updated = ps2.executeUpdate();
            if (updated == 0) {
                throw new SQLException("Không đủ sách để mượn! Mã sách: " + maSach);
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<phieuMuon> layPhieuQuaHan() {
        List<phieuMuon> ds = new ArrayList<>();
        pmDAO.capNhatQuaHan();
        String sql = """
                SELECT pm.maPhieuMuon, pm.maBanDoc, pm.ngayMuon, pm.ngayHenTra, bd.hoTen AS hoTenDocGia
                FROM phieumuon pm
                JOIN bandoc bd ON pm.maBanDoc = bd.maBanDoc
                WHERE pm.trangThai = 'Quá hạn'  -- SỬA: đổi từ 'Đang mượn' thành 'Quá hạn'
                ORDER BY pm.ngayHenTra""";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                phieuMuon pm = new phieuMuon();
                pm.setMaPhieuMuon(rs.getInt("maPhieuMuon"));
                pm.setMaBanDoc(rs.getInt("maBanDoc"));
                pm.setHoTenDocGia(rs.getString("hoTenDocGia"));
                pm.setNgayMuon(rs.getDate("ngayMuon"));
                pm.setNgayHenTra(rs.getDate("ngayHenTra"));
                ds.add(pm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<phieuMuon> layTatCaPhieuMuon() {
        return pmDAO.getAll(); // tự động cập nhật quá hạn
    }

    public int demPhieuQuaHan() {
        return pmDAO.countQuaHan();
    }

    public phieuMuon layTheoMa(int maPhieu) {
        String sql = "SELECT pm.*, bd.hoTen AS hoTenDocGia FROM phieumuon pm JOIN bandoc bd ON pm.maBanDoc = bd.maBanDoc WHERE pm.maPhieuMuon = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maPhieu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                phieuMuon pm = new phieuMuon();
                pm.setMaPhieuMuon(rs.getInt("maPhieuMuon"));
                pm.setMaBanDoc(rs.getInt("maBanDoc"));
                pm.setHoTenDocGia(rs.getString("hoTenDocGia"));
                pm.setNgayMuon(rs.getDate("ngayMuon"));
                pm.setNgayHenTra(rs.getDate("ngayHenTra"));
                pm.setTrangThai(rs.getString("trangThai"));
                return pm;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CTPhieuMuon> layChiTietPhieuMuon(int maPhieu) {
        List<CTPhieuMuon> ds = new ArrayList<>();
        String sql = "SELECT ct.*, s.tenSach FROM ct_phieumuon ct JOIN sach s ON ct.maSach = s.maSach WHERE ct.maPhieuMuon = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maPhieu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CTPhieuMuon ct = new CTPhieuMuon();
                ct.setMaSach(rs.getInt("maSach"));
                ct.setTenSach(rs.getString("tenSach"));
                ct.setSoLuong(rs.getInt("soLuong"));
                ds.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public void capNhatChiTietTra(int maPhieu, CTPhieuMuon ct) {
        // SỬA: ctphieumuon → ct_phieumuon
        // Lưu ý: Bảng ct_phieumuon không có cột tinhTrangTra và ghiChu
        // Nếu cần lưu, phải lưu vào bảng ct_phieutra khi tạo phiếu trả
        // Tạm thời bỏ qua hoặc comment lại
        /*
         * String sql =
         * "UPDATE ct_phieumuon SET tinhTrangTra = ?, ghiChu = ? WHERE maPhieuMuon = ? AND maSach = ?"
         * ;
         * try (Connection c = DatabaseConnection.getConnection();
         * PreparedStatement ps = c.prepareStatement(sql)) {
         * ps.setString(1, ct.getTinhTrangTra());
         * ps.setString(2, ct.getGhiChu());
         * ps.setInt(3, maPhieu);
         * ps.setInt(4, ct.getMaSach());
         * ps.executeUpdate();
         * } catch (Exception e) {
         * e.printStackTrace();
         * }
         */
    }

    public boolean traSach(int maPhieu, long quaHanNgay) {
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();
            c.setAutoCommit(false);

            // 1. Cập nhật trạng thái phiếu (KHÔNG có ngayTra và tienPhat)
            String sql1 = "UPDATE phieumuon SET trangThai = 'Đã trả' WHERE maPhieuMuon = ?";
            try (PreparedStatement ps = c.prepareStatement(sql1)) {
                ps.setInt(1, maPhieu);
                ps.executeUpdate();
            }

            // 2. Tăng lại số lượng sách hiện tại
            // SỬA: ctphieumuon → ct_phieumuon
            String sql2 = "UPDATE sach s JOIN ct_phieumuon ct ON s.maSach = ct.maSach SET s.soLuongHienTai = s.soLuongHienTai + ct.soLuong WHERE ct.maPhieuMuon = ?";
            try (PreparedStatement ps = c.prepareStatement(sql2)) {
                ps.setInt(1, maPhieu);
                ps.executeUpdate();
            }

            // 3. Tạo phiếu trả (nếu cần lưu tiền phạt và ngày trả)
            // Sử dụng PhieuTraController nếu có, hoặc tạo trực tiếp
            // Tạm thời bỏ qua vì cần maNV

            c.commit();
            return true;
        } catch (Exception e) {
            try {
                if (c != null)
                    c.rollback();
            } catch (Exception ex) {
            }
            e.printStackTrace();
            return false;
        }
    }

}
