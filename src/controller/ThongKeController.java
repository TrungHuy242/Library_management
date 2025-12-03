package controller;

import dao.PhieuMuonDAO;
import dao.SachDAO;


public class ThongKeController {
	private PhieuMuonDAO pmDAO = new PhieuMuonDAO();
	private SachDAO sachDAO = new SachDAO();
	
	public int soPhieuQuaHan() {
		return pmDAO.countQuaHan(); 
		}
    public int tongSoSach() { 
    	return sachDAO.getAll().size(); 
    	}
    public long soSachDangMuon() {
        return sachDAO.getAll().stream().mapToLong(s -> s.getSoLuongTong() - s.getSoLuongHienTai()).sum();
    } 
}
