package model;

public class CTPhieuMuon {
	private int maPhieuMuon;
	private int maSach;
	private String tenSach;
	private String tenTacGia;
	private int soLuong;
	private String tinhTrangTra;
	private String ghiChu;
	public CTPhieuMuon() {
		super();
	}
	public CTPhieuMuon(int maPhieuMuon, int maSach, String tenSach, String tenTacGia, int soLuong, String tinhTrangTra, String ghiChu) {
		super();
		this.maPhieuMuon = maPhieuMuon;
		this.maSach = maSach;
		this.tenSach = tenSach;
		this.tenTacGia = tenTacGia;
		this.soLuong = soLuong;
		this.tinhTrangTra = tinhTrangTra;
		this.ghiChu = ghiChu;
	}
	public int getMaPhieuMuon() {
		return maPhieuMuon;
	}
	public void setMaPhieuMuon(int maPhieuMuon) {
		this.maPhieuMuon = maPhieuMuon;
	}
	public int getMaSach() {
		return maSach;
	}
	public void setMaSach(int maSach) {
		this.maSach = maSach;
	}
	public String getTenSach() {
		return tenSach;
	}
	public void setTenSach(String tenSach) {
		this.tenSach = tenSach;
	}
	public String getTenTacGia() {
		return tenTacGia;
	}
	public void setTenTacGia(String tenTacGia) {
		this.tenTacGia = tenTacGia;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getTinhTrangTra() {
		return tinhTrangTra;
	}
	public void setTinhTrangTra(String tinhTrangTra) {
		this.tinhTrangTra = tinhTrangTra;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return tenSach + " - " + tenTacGia + " (SL: " + soLuong + ")";
	}
	
	
	
}
