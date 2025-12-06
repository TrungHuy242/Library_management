package client;

import server.Request;
import server.Response;
import model.sach;
import java.util.List;

/**
 * Client-side controller cho Sách
 */
public class SachClientController {
    private ClientConnection connection;
    
    public SachClientController() {
        this.connection = ClientConnection.getInstance();
    }
    
    @SuppressWarnings("unchecked")
    public List<sach> layTatCaSach() {
        try {
            Request request = new Request("GET_ALL_SACH", null);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<sach>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public sach layTheoMa(int ma) {
        try {
            Request request = new Request("GET_SACH_BY_ID", ma);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof sach) {
                return (sach) response.getData();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<sach> timKiemSach(String keyword) {
        try {
            Request request = new Request("TIM_KIEM_SACH", keyword);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<sach>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public boolean them(sach s) {
        try {
            Request request = new Request("THEM_SACH", s);
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
    
    public boolean sua(sach s) {
        try {
            Request request = new Request("SUA_SACH", s);
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
    
    public boolean xoa(int maSach) {
        try {
            Request request = new Request("XOA_SACH", maSach);
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

