package server;

import java.io.Serializable;

/**
 * Class đại diện cho Request từ Client gửi đến Server
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String action;  // Hành động: "LOGIN", "GET_ALL_SACH", "THEM_SACH", etc.
    private Object data;    // Dữ liệu kèm theo request
    
    public Request() {
    }
    
    public Request(String action, Object data) {
        this.action = action;
        this.data = data;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "Request{action='" + action + "', data=" + data + "}";
    }
}

