package model;

public class nhanVien {
	private int maNV;
	private String hoTen;
	private String sdt;
	private String vaiTro;
	private String taiKhoan;
	private String matKhau;
	public nhanVien() {
		super();
	}
	public nhanVien(int maNV, String hoTen, String sdt, String vaiTro, String taiKhoan, String matKhau) {
		super();
		this.maNV = maNV;
		this.hoTen = hoTen;
		this.sdt = sdt;
		this.vaiTro = vaiTro;
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
	}
	public int getMaNV() {
		return maNV;
	}
	public void setMaNV(int maNV) {
		this.maNV = maNV;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getVaiTro() {
		return vaiTro;
	}
	public void setVaiTro(String vaiTro) {
		this.vaiTro = vaiTro;
	}
	public String getTaiKhoan() {
		return taiKhoan;
	}
	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return hoTen + " - " + vaiTro;
	}
	
	
	
}
