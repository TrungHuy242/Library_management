package model;

import java.io.Serializable;

public class sach implements Serializable {
	private static final long serialVersionUID = 1L;
	private int maSach;
	private String tenSach;
	private int maTacGia;
	private String tenTacGia;
	private int maTheLoai;
	private String tenTheLoai;
	private Integer namXuatBan;
	private String nhaXuatBan;
	private int soLuongTong;
	private int soLuongHienTai;
	private String trangThai;
	
	public sach() {
		super();
	}

	public sach(int maSach, String tenSach, int maTacGia, String tenTacGia, int maTheLoai, String tenTheLoai,
			Integer namXuatBan, String nhaXuatBan, int soLuongTong, int soLuongHienTai, String trangThai) {
		super();
		this.maSach = maSach;
		this.tenSach = tenSach;
		this.maTacGia = maTacGia;
		this.tenTacGia = tenTacGia;
		this.maTheLoai = maTheLoai;
		this.tenTheLoai = tenTheLoai;
		this.namXuatBan = namXuatBan;
		this.nhaXuatBan = nhaXuatBan;
		this.soLuongTong = soLuongTong;
		this.soLuongHienTai = soLuongHienTai;
		this.trangThai = trangThai;
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

	public int getMaTacGia() {
		return maTacGia;
	}

	public void setMaTacGia(int maTacGia) {
		this.maTacGia = maTacGia;
	}

	public String getTenTacGia() {
		return tenTacGia;
	}

	public void setTenTacGia(String tenTacGia) {
		this.tenTacGia = tenTacGia;
	}

	public int getMaTheLoai() {
		return maTheLoai;
	}

	public void setMaTheLoai(int maTheLoai) {
		this.maTheLoai = maTheLoai;
	}

	public String getTenTheLoai() {
		return tenTheLoai;
	}

	public void setTenTheLoai(String tenTheLoai) {
		this.tenTheLoai = tenTheLoai;
	}

	public Integer getNamXuatBan() {
		return namXuatBan;
	}

	public void setNamXuatBan(Integer namXuatBan) {
		this.namXuatBan = namXuatBan;
	}

	public String getNhaXuatBan() {
		return nhaXuatBan;
	}

	public void setNhaXuatBan(String nhaXuatBan) {
		this.nhaXuatBan = nhaXuatBan;
	}

	public int getSoLuongTong() {
		return soLuongTong;
	}

	public void setSoLuongTong(int soLuongTong) {
		this.soLuongTong = soLuongTong;
	}

	public int getSoLuongHienTai() {
		return soLuongHienTai;
	}

	public void setSoLuongHienTai(int soLuongHienTai) {
		this.soLuongHienTai = soLuongHienTai;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public String toString() {
		return tenSach + " - " + tenTacGia;
	}
	
	
}
