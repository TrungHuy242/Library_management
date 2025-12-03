package model;

public class theLoai {
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
		// TODO Auto-generated method stub
		return tenTheLoai;
	}
	
	
}
