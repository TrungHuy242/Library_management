package controller;

import dao.PhieuTraDAO;
import dao.PhieuMuonDAO;
import model.CTPhieuTra;
import model.PhieuTra;
import java.util.List;

public class PhieuTraController {
	private PhieuTraDAO dao = new PhieuTraDAO();
	private PhieuMuonDAO pmDAO = new PhieuMuonDAO();
	
	public boolean traSach(int maPhieuMuon, int maNV, List<CTPhieuTra> dsChiTiet, float tienPhat, String ghiChu) {
        pmDAO.capNhatQuaHan(); 
        return dao.taoPhieuTra(maPhieuMuon, maNV, dsChiTiet, tienPhat, ghiChu);
    }

    public List<PhieuTra> layTatCaPhieuTra() {
        return dao.getAll();
    }

    public List<CTPhieuTra> layChiTietPhieuTra(int maPhieuTra) {
        return dao.getChiTietByMaPhieuTra(maPhieuTra);
    }

    public PhieuTra layTheoMa(int maPhieuTra) {
        return dao.getAll().stream()
            .filter(pt -> pt.getMaPhieuTra() == maPhieuTra)
            .findFirst()
            .orElse(null);
    }
}
