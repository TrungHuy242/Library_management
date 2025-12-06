package server;

import model.CTPhieuTra;
import java.io.Serializable;
import java.util.List;

/**
 * Wrapper class để gửi dữ liệu trả sách
 */
public class PhieuTraData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int maPhieuMuon;
    private int maNV;
    private List<CTPhieuTra> dsChiTiet;
    private float tienPhat;
    private String ghiChu;
    
    public PhieuTraData() {
    }
    
    public PhieuTraData(int maPhieuMuon, int maNV, List<CTPhieuTra> dsChiTiet, float tienPhat, String ghiChu) {
        this.maPhieuMuon = maPhieuMuon;
        this.maNV = maNV;
        this.dsChiTiet = dsChiTiet;
        this.tienPhat = tienPhat;
        this.ghiChu = ghiChu;
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
    
    public List<CTPhieuTra> getDsChiTiet() {
        return dsChiTiet;
    }
    
    public void setDsChiTiet(List<CTPhieuTra> dsChiTiet) {
        this.dsChiTiet = dsChiTiet;
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
}

