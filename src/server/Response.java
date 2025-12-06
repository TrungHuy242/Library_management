package server;

import java.io.Serializable;

/**
 * Class đại diện cho Response từ Server gửi về Client
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private boolean success;  // Thành công hay thất bại
    private String message;   // Thông báo
    private Object data;       // Dữ liệu trả về
    
    public Response() {
    }
    
    public Response(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    
    public static Response success(Object data) {
        return new Response(true, "Thành công", data);
    }
    
    public static Response success(String message, Object data) {
        return new Response(true, message, data);
    }
    
    public static Response error(String message) {
        return new Response(false, message, null);
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "Response{success=" + success + ", message='" + message + "', data=" + data + "}";
    }
}

