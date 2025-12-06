package model;

import java.io.Serializable;

public class tacGia implements Serializable {
	private static final long serialVersionUID = 1L;
	private int maTacGia;
	private String tenTacGia;
	private Integer namSinh;
	private String quocTich;
	public tacGia() {
		super();
	}
	public tacGia(int maTacGia, String tenTacGia, Integer namSinh, String quocTich) {
		super();
		this.maTacGia = maTacGia;
		this.tenTacGia = tenTacGia;
		this.namSinh = namSinh;
		this.quocTich = quocTich;
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
	public Integer getNamSinh() {
		return namSinh;
	}
	public void setNamSinh(Integer namSinh) {
		this.namSinh = namSinh;
	}
	public String getQuocTich() {
		return quocTich;
	}
	public void setQuocTich(String quocTich) {
		this.quocTich = quocTich;
	}
	@Override
	public String toString() {
		return tenTacGia + (quocTich != null ? " (" + quocTich + ")" : "");
	}
	
	
	
}
