package model;

import java.io.Serializable;
import java.sql.Date;

public class phieuMuon implements Serializable {
	private static final long serialVersionUID = 1L;
	private int maPhieuMuon;
	private int maBanDoc;
	private String tenBanDoc;
	private int maNV;
	private String tenNhanVien;
	private String hoTenDocGia;
	private Date ngayMuon;
	private Date ngayHenTra;
	private String trangThai;
	
	public phieuMuon() {
		super();
	}

	public phieuMuon(int maPhieuMuon, int maBanDoc, String tenBanDoc, int maNV, String tenNhanVien,String hoTenDocGia, Date ngayMuon,
			Date ngayHenTra, String trangThai) {
		super();
		this.maPhieuMuon = maPhieuMuon;
		this.maBanDoc = maBanDoc;
		this.tenBanDoc = tenBanDoc;
		this.maNV = maNV;
		this.tenNhanVien = tenNhanVien;
		this.hoTenDocGia = hoTenDocGia;
		this.ngayMuon = ngayMuon;
		this.ngayHenTra = ngayHenTra;
		this.trangThai = trangThai;
	}

	public int getMaPhieuMuon() {
		return maPhieuMuon;
	}

	public void setMaPhieuMuon(int maPhieuMuon) {
		this.maPhieuMuon = maPhieuMuon;
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
	
	public String getHoTenDocGia() {
		return hoTenDocGia;
	}
	
	public void setHoTenDocGia(String hoTenDocGia) {
		this.hoTenDocGia = hoTenDocGia;
	}

	public Date getNgayMuon() {
		return ngayMuon;
	}

	public void setNgayMuon(Date ngayMuon) {
		this.ngayMuon = ngayMuon;
	}

	public Date getNgayHenTra() {
		return ngayHenTra;
	}

	public void setNgayHenTra(Date ngayHenTra) {
		this.ngayHenTra = ngayHenTra;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public String toString() {
		return "PM" + maPhieuMuon + " - " + tenBanDoc + " (" + trangThai + ")";
	}
	
	
}
