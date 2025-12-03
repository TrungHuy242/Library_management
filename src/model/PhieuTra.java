package model;

import java.sql.Date;

public class PhieuTra {
	private int maPhieuTra;
	private int maPhieuMuon;
	private int maNV;
	private String tenNhanVien;
	private int maBanDoc;
	private String tenBanDoc;
	private Date ngayTra;
	private float tienPhat;
	private String ghiChu;
	public PhieuTra() {
		super();
	}
	public PhieuTra(int maPhieuTra, int maPhieuMuon, int maNV, String tenNhanVien, Date ngayTra, float tienPhat,
			String ghiChu) {
		super();
		this.maPhieuTra = maPhieuTra;
		this.maPhieuMuon = maPhieuMuon;
		this.maNV = maNV;
		this.tenNhanVien = tenNhanVien;
		this.ngayTra = ngayTra;
		this.tienPhat = tienPhat;
		this.ghiChu = ghiChu;
	}
	public int getMaPhieuTra() {
		return maPhieuTra;
	}
	public void setMaPhieuTra(int maPhieuTra) {
		this.maPhieuTra = maPhieuTra;
	}
	public int getMaPhieuMuon() {
		return maPhieuMuon;
	}
	public void setMaPhieuMuon(int maPhieuMuon) {
		this.maPhieuMuon = maPhieuMuon;
	}
	public int getMaNV() {
		return maNV;
	}
	public void setMaNV(int maNV) {
		this.maNV = maNV;
	}
	public String getTenNhanVien() {
		return tenNhanVien;
	}
	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}
	public Date getNgayTra() {
		return ngayTra;
	}
	public void setNgayTra(Date ngayTra) {
		this.ngayTra = ngayTra;
	}
	public float getTienPhat() {
		return tienPhat;
	}
	public void setTienPhat(float tienPhat) {
		this.tienPhat = tienPhat;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public int getMaBanDoc() {
		return maBanDoc;
	}
	public void setMaBanDoc(int maBanDoc) {
		this.maBanDoc = maBanDoc;
	}
	public String getTenBanDoc() {
		return tenBanDoc;
	}
	public void setTenBanDoc(String tenBanDoc) {
		this.tenBanDoc = tenBanDoc;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "PT" + maPhieuTra + " - PM" + maPhieuMuon + " (Phạt: " + tienPhat + "đ)";
	}
	
	
}
