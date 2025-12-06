package client;

import server.Request;
import server.Response;
import model.nhanVien;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Client-side controller cho Authentication
 * Gửi request đến server thay vì gọi DAO trực tiếp
 */
public class AuthClientController {
    private ClientConnection connection;
    
    public AuthClientController() {
        this.connection = ClientConnection.getInstance();
    }
    
    public nhanVien dangNhap(String taiKhoan, String matKhau) {
        try {
            Map<String, String> loginData = new HashMap<>();
            loginData.put("taiKhoan", taiKhoan);
            loginData.put("matKhau", matKhau);
            
            Request request = new Request("LOGIN", loginData);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof nhanVien) {
                return (nhanVien) response.getData();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean them(nhanVien nv) {
        try {
            Request request = new Request("THEM_NHAN_VIEN", nv);
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
    
    public boolean sua(nhanVien nv) {
        try {
            Request request = new Request("SUA_NHAN_VIEN", nv);
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
    
    public boolean xoa(int maNV) {
        try {
            Request request = new Request("XOA_NHAN_VIEN", maNV);
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
    public List<nhanVien> getAllNhanVien() {
        try {
            Request request = new Request("GET_ALL_NHAN_VIEN", null);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof List) {
                return (List<nhanVien>) response.getData();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public nhanVien layTheoMa(int maNV) {
        try {
            Request request = new Request("GET_NHAN_VIEN_BY_ID", maNV);
            Response response = connection.sendRequest(request);
            
            if (response.isSuccess() && response.getData() instanceof nhanVien) {
                return (nhanVien) response.getData();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

