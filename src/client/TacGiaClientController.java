package client;

import server.Request;
import server.Response;
import model.tacGia;
import java.util.List;
import java.util.HashMap;

/**
 * Client-side controller cho Tác giả
 */
public class TacGiaClientController {
    private ClientConnection connection;
    
    public TacGiaClientController() {
        this.connection = ClientConnection.getInstance();
    }
    
    @SuppressWarnings("unchecked")
    public List<tacGia> layTatCa() {
        try {
            Request request = new Request("GET_ALL_TAC_GIA", null);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<tacGia>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public HashMap<Integer, String> layTatCaMap() {
        HashMap<Integer, String> map = new HashMap<>();
        List<tacGia> list = layTatCa();
        for (tacGia tg : list) {
            map.put(tg.getMaTacGia(), tg.getTenTacGia());
        }
        return map;
    }
    
    public tacGia layTheoMa(int ma) {
        try {
            List<tacGia> all = layTatCa();
            return all.stream()
                .filter(tg -> tg.getMaTacGia() == ma)
                .findFirst()
                .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean them(tacGia tg) {
        try {
            Request request = new Request("THEM_TAC_GIA", tg);
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
    
    public boolean sua(tacGia tg) {
        try {
            Request request = new Request("SUA_TAC_GIA", tg);
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
    
    public boolean xoa(int maTacGia) {
        try {
            Request request = new Request("XOA_TAC_GIA", maTacGia);
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
}

