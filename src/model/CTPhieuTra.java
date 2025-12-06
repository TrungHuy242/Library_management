package model;

import java.io.Serializable;

public class CTPhieuTra implements Serializable {
	private static final long serialVersionUID = 1L;
	private int maPhieuTra;
	private int maSach;
	private String tenSach;
	private int soLuong;
	private String tinhTrangSach;
	public CTPhieuTra() {
		super();
	}
	public CTPhieuTra(int maPhieuTra, int maSach, String tenSach, int soLuong, String tinhTrangSach) {
		super();
		this.maPhieuTra = maPhieuTra;
		this.maSach = maSach;
		this.tenSach = tenSach;
		this.soLuong = soLuong;
		this.tinhTrangSach = tinhTrangSach;
	}
	public int getMaPhieuTra() {
		return maPhieuTra;
	}
	public void setMaPhieuTra(int maPhieuTra) {
		this.maPhieuTra = maPhieuTra;
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
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getTinhTrangSach() {
		return tinhTrangSach;
	}
	public void setTinhTrangSach(String tinhTrangSach) {
		this.tinhTrangSach = tinhTrangSach;
	}
	@Override
	public String toString() {
		return tenSach + " (SL: " + soLuong + ") - " + tinhTrangSach;
	}
	
	
}
