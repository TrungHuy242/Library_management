package controller;

import dao.NhanVienDAO;
import model.nhanVien;
import java.util.List;

public class AuthController {
	private NhanVienDAO dao = new NhanVienDAO();
	
	public nhanVien dangNhap(String taiKhoan, String matKhau) {
        for (nhanVien nv : dao.getAll()) {
            if (nv.getTaiKhoan().equals(taiKhoan) && nv.getMatKhau().equals(matKhau)) {
                return nv;
            }
        }
        return null;
    }

    public List<nhanVien> getAllNhanVien() {
        return dao.getAll();
    }

    public boolean them(nhanVien nv) {
        // Kiểm tra tài khoản đã tồn tại chưa
        for (nhanVien n : dao.getAll()) {
            if (n.getTaiKhoan().equals(nv.getTaiKhoan())) {
                throw new RuntimeException("Tài khoản đã tồn tại!");
            }
        }
        return dao.them(nv);
    }

    public boolean sua(nhanVien nv) {
        // Kiểm tra tài khoản đã tồn tại chưa (trừ chính nó)
        if (dao.kiemTraTaiKhoanTonTai(nv.getTaiKhoan(), nv.getMaNV())) {
            throw new RuntimeException("Tài khoản đã tồn tại!");
        }
        return dao.sua(nv);
    }

    public boolean xoa(int maNV) {
        // Kiểm tra xem có thể xóa không
        if (!dao.xoa(maNV)) {
            throw new RuntimeException("Không thể xóa nhân viên này vì đã có phiếu mượn liên quan!");
        }
        return true;
    }

    public nhanVien layTheoMa(int maNV) {
        return dao.getAll().stream()
            .filter(nv -> nv.getMaNV() == maNV)
            .findFirst()
            .orElse(null);
    }
}
