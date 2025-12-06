package client;

import server.Request;
import server.Response;
import model.banDoc;
import java.util.List;
import java.util.HashMap;

/**
 * Client-side controller cho Bạn đọc
 */
public class BanDocClientController {
    private ClientConnection connection;
    
    public BanDocClientController() {
        this.connection = ClientConnection.getInstance();
    }
    
    @SuppressWarnings("unchecked")
    public List<banDoc> layTatCa() {
        try {
            Request request = new Request("GET_ALL_BAN_DOC", null);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<banDoc>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public banDoc layTheoMa(int maBanDoc) {
        try {
            Request request = new Request("GET_BAN_DOC_BY_ID", maBanDoc);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof banDoc) {
                return (banDoc) response.getData();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<banDoc> timKiem(String keyword) {
        try {
            Request request = new Request("TIM_KIEM_BAN_DOC", keyword);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<banDoc>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public boolean them(banDoc bd) {
        try {
            Request request = new Request("THEM_BAN_DOC", bd);
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
    
    public boolean sua(banDoc bd) {
        try {
            Request request = new Request("SUA_BAN_DOC", bd);
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
    
    public boolean xoa(int maBanDoc) {
        try {
            Request request = new Request("XOA_BAN_DOC", maBanDoc);
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

