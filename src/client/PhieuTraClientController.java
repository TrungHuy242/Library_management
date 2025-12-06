package client;

import server.Request;
import server.Response;
import server.PhieuTraData;
import model.PhieuTra;
import model.CTPhieuTra;
import java.util.List;

/**
 * Client-side controller cho Phiếu trả
 */
public class PhieuTraClientController {
    private ClientConnection connection;
    
    public PhieuTraClientController() {
        this.connection = ClientConnection.getInstance();
    }
    
    @SuppressWarnings("unchecked")
    public List<PhieuTra> layTatCaPhieuTra() {
        try {
            Request request = new Request("GET_ALL_PHIEU_TRA", null);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<PhieuTra>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public PhieuTra layTheoMa(int maPhieuTra) {
        try {
            List<PhieuTra> all = layTatCaPhieuTra();
            return all.stream()
                .filter(pt -> pt.getMaPhieuTra() == maPhieuTra)
                .findFirst()
                .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean traSach(int maPhieuMuon, int maNV, List<CTPhieuTra> dsChiTiet, float tienPhat, String ghiChu) {
        try {
            PhieuTraData ptData = new PhieuTraData(maPhieuMuon, maNV, dsChiTiet, tienPhat, ghiChu);
            Request request = new Request("TRA_SACH", ptData);
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
    public List<CTPhieuTra> layChiTietPhieuTra(int maPhieuTra) {
        try {
            Request request = new Request("GET_CHI_TIET_PHIEU_TRA", maPhieuTra);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<CTPhieuTra>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}

