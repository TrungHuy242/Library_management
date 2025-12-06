package client;

import server.Request;
import server.Response;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class quản lý kết nối từ Client đến Server
 */
public class ClientConnection {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9999;
    
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean isConnected = false;
    
    /**
     * Kết nối đến server
     */
    public boolean connect() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            isConnected = true;
            System.out.println("Đã kết nối đến server: " + SERVER_HOST + ":" + SERVER_PORT);
            return true;
        } catch (IOException e) {
            System.err.println("Không thể kết nối đến server: " + e.getMessage());
            e.printStackTrace();
            isConnected = false;
            return false;
        }
    }
    
    /**
     * Gửi request và nhận response từ server
     */
    public Response sendRequest(Request request) {
        if (!isConnected) {
            if (!connect()) {
                return Response.error("Không thể kết nối đến server. Vui lòng kiểm tra server đã chạy chưa.");
            }
        }
        
        try {
            // Gửi request
            oos.writeObject(request);
            oos.flush();
            
            // Nhận response
            Response response = (Response) ois.readObject();
            return response;
            
        } catch (IOException e) {
            System.err.println("Lỗi giao tiếp với server: " + e.getMessage());
            e.printStackTrace();
            isConnected = false;
            return Response.error("Lỗi kết nối server: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi đọc response: " + e.getMessage());
            e.printStackTrace();
            return Response.error("Lỗi định dạng response từ server");
        }
    }
    
    /**
     * Đóng kết nối
     */
    public void disconnect() {
        try {
            if (ois != null) ois.close();
            if (oos != null) oos.close();
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            isConnected = false;
            System.out.println("Đã ngắt kết nối với server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Kiểm tra trạng thái kết nối
     */
    public boolean isConnected() {
        return isConnected && socket != null && !socket.isClosed();
    }
    
    /**
     * Singleton instance
     */
    private static ClientConnection instance;
    
    public static ClientConnection getInstance() {
        if (instance == null) {
            instance = new ClientConnection();
            instance.connect(); // Tự động kết nối khi tạo instance
        }
        return instance;
    }
}

