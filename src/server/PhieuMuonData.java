package server;

import model.CTPhieuMuon;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Wrapper class để gửi dữ liệu tạo phiếu mượn
 */
public class PhieuMuonData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int maBanDoc;
    private int maNV;
    private Date ngayHenTra;
    private List<CTPhieuMuon> dsChiTiet;
    
    public PhieuMuonData() {
    }
    
    public PhieuMuonData(int maBanDoc, int maNV, Date ngayHenTra, List<CTPhieuMuon> dsChiTiet) {
        this.maBanDoc = maBanDoc;
        this.maNV = maNV;
        this.ngayHenTra = ngayHenTra;
        this.dsChiTiet = dsChiTiet;
    }
    
    public int getMaBanDoc() {
        return maBanDoc;
    }
    
    public void setMaBanDoc(int maBanDoc) {
        this.maBanDoc = maBanDoc;
    }
    
    public int getMaNV() {
        return maNV;
    }
    
    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }
    
    public Date getNgayHenTra() {
        return ngayHenTra;
    }
    
    public void setNgayHenTra(Date ngayHenTra) {
        this.ngayHenTra = ngayHenTra;
    }
    
    public List<CTPhieuMuon> getDsChiTiet() {
        return dsChiTiet;
    }
    
    public void setDsChiTiet(List<CTPhieuMuon> dsChiTiet) {
        this.dsChiTiet = dsChiTiet;
    }
}

