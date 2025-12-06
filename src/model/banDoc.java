package model;

import java.io.Serializable;
import java.sql.Date;

public class banDoc implements Serializable {
	private static final long serialVersionUID = 1L;
	private int maBanDoc;
	private String hoTen;
	private String lop;
	private String sdt;
	private String email;
	private Date ngaySinh;
	private Date ngayDangKy;
	public banDoc() {
		super();
	}
	public banDoc(int maBanDoc, String hoTen, String lop, String sdt, String email, Date ngaySinh, Date ngayDangKy) {
		super();
		this.maBanDoc = maBanDoc;
		this.hoTen = hoTen;
		this.lop = lop;
		this.sdt = sdt;
		this.email = email;
		this.ngaySinh = ngaySinh;
		this.ngayDangKy = ngayDangKy;
	}
	public int getMaBanDoc() {
		return maBanDoc;
	}
	public void setMaBanDoc(int maBanDoc) {
		this.maBanDoc = maBanDoc;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getLop() {
		return lop;
	}
	public void setLop(String lop) {
		this.lop = lop;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public Date getNgayDangKy() {
		return ngayDangKy;
	}
	public void setNgayDangKy(Date ngayDangKy) {
		this.ngayDangKy = ngayDangKy;
	}
	@Override
	public String toString() {
		return maBanDoc + " - " + hoTen + " (" + (lop != null ? lop : "không có lớp") + ")";
	}
	
	
	
}
