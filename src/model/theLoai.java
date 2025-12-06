package model;

import java.io.Serializable;

public class theLoai implements Serializable {
	private static final long serialVersionUID = 1L;
	private int maTheLoai;
	private String tenTheLoai;
	public theLoai() {
		super();
	}
	public theLoai(int maTheLoai, String tenTheLoai) {
		super();
		this.maTheLoai = maTheLoai;
		this.tenTheLoai = tenTheLoai;
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
	@Override
	public String toString() {
		return tenTheLoai;
	}
	
	
}
