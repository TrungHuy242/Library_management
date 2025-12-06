package client;

import server.Request;
import server.Response;
import server.PhieuMuonData;
import model.phieuMuon;
import model.CTPhieuMuon;
import java.util.List;
import java.sql.Date;

/**
 * Client-side controller cho Phiếu mượn
 */
public class PhieuMuonClientController {
    private ClientConnection connection;
    
    public PhieuMuonClientController() {
        this.connection = ClientConnection.getInstance();
    }
    
    @SuppressWarnings("unchecked")
    public List<phieuMuon> layTatCaPhieuMuon() {
        try {
            Request request = new Request("GET_ALL_PHIEU_MUON", null);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<phieuMuon>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public phieuMuon layTheoMa(int maPhieu) {
        try {
            Request request = new Request("GET_PHIEU_MUON_BY_ID", maPhieu);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof phieuMuon) {
                return (phieuMuon) response.getData();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<phieuMuon> timKiem(String keyword) {
        try {
            Request request = new Request("TIM_KIEM_PHIEU_MUON", keyword);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<phieuMuon>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public boolean taoPhieuMuon(int maBanDoc, int maNV, Date ngayHenTra, List<CTPhieuMuon> dsChiTiet) {
        try {
            PhieuMuonData pmData = new PhieuMuonData(maBanDoc, maNV, ngayHenTra, dsChiTiet);
            Request request = new Request("TAO_PHIEU_MUON", pmData);
            Response response = connection.sendRequest(request);
            
            if (!response.isSuccess()) {
                throw new RuntimeException(response.getMessage());
            }
            return true;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<phieuMuon> layPhieuQuaHan() {
        try {
            Request request = new Request("GET_PHIEU_QUA_HAN", null);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<phieuMuon>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public int demPhieuQuaHan() {
        List<phieuMuon> list = layPhieuQuaHan();
        return list.size();
    }
    
    @SuppressWarnings("unchecked")
    public List<CTPhieuMuon> layChiTietPhieuMuon(int maPhieu) {
        try {
            Request request = new Request("GET_CHI_TIET_PHIEU_MUON", maPhieu);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<CTPhieuMuon>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}

