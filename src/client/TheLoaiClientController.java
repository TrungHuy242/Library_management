package client;

import server.Request;
import server.Response;
import model.theLoai;
import java.util.List;
import java.util.HashMap;

/**
 * Client-side controller cho Thể loại
 */
public class TheLoaiClientController {
    private ClientConnection connection;
    
    public TheLoaiClientController() {
        this.connection = ClientConnection.getInstance();
    }
    
    @SuppressWarnings("unchecked")
    public List<theLoai> layTatCa() {
        try {
            Request request = new Request("GET_ALL_THE_LOAI", null);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<theLoai>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public HashMap<Integer, String> layTatCaMap() {
        HashMap<Integer, String> map = new HashMap<>();
        List<theLoai> list = layTatCa();
        for (theLoai tl : list) {
            map.put(tl.getMaTheLoai(), tl.getTenTheLoai());
        }
        return map;
    }
    
    public theLoai layTheoMa(int ma) {
        try {
            List<theLoai> all = layTatCa();
            return all.stream()
                .filter(tl -> tl.getMaTheLoai() == ma)
                .findFirst()
                .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean them(theLoai tl) {
        try {
            Request request = new Request("THEM_THE_LOAI", tl);
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
    
    public boolean sua(theLoai tl) {
        try {
            Request request = new Request("SUA_THE_LOAI", tl);
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
    
    public boolean xoa(int maTheLoai) {
        try {
            Request request = new Request("XOA_THE_LOAI", maTheLoai);
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

