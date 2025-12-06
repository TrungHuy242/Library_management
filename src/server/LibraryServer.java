package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server chính của hệ thống quản lý thư viện
 * Lắng nghe kết nối từ client và xử lý request
 */
public class LibraryServer {
    private static final int PORT = 9999;
    private ServerSocket serverSocket;
    private boolean isRunning = false;
    private RequestHandler requestHandler;
    
    public LibraryServer() {
        this.requestHandler = new RequestHandler();
    }
    
    /**
     * Khởi động server
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            isRunning = true;
            System.out.println("========================================");
            System.out.println("   SERVER QUẢN LÝ THƯ VIỆN");
            System.out.println("========================================");
            System.out.println("Server đang lắng nghe trên port: " + PORT);
            System.out.println("Chờ kết nối từ client...");
            System.out.println("========================================\n");
            
            // Lắng nghe kết nối từ client
            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client đã kết nối: " + clientSocket.getInetAddress().getHostAddress());
                
                // Tạo thread mới để xử lý client
                ClientHandler clientHandler = new ClientHandler(clientSocket, requestHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khởi động server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Dừng server
     */
    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server đã dừng.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Class xử lý từng client connection
     */
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private RequestHandler requestHandler;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        
        public ClientHandler(Socket clientSocket, RequestHandler requestHandler) {
            this.clientSocket = clientSocket;
            this.requestHandler = requestHandler;
        }
        
        @Override
        public void run() {
            try {
                // Tạo input/output streams
                oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ois = new ObjectInputStream(clientSocket.getInputStream());
                
                // Xử lý request từ client
                while (true) {
                    try {
                        // Đọc request từ client
                        Request request = (Request) ois.readObject();
                        System.out.println("Nhận request: " + request.getAction());
                        
                        // Xử lý request
                        Response response = requestHandler.handleRequest(request);
                        
                        // Gửi response về client
                        oos.writeObject(response);
                        oos.flush();
                        
                        System.out.println("Đã gửi response: " + (response.isSuccess() ? "Thành công" : "Thất bại"));
                        
                    } catch (ClassNotFoundException e) {
                        System.err.println("Lỗi đọc request: " + e.getMessage());
                        Response errorResponse = Response.error("Lỗi định dạng request");
                        oos.writeObject(errorResponse);
                        oos.flush();
                    }
                }
            } catch (IOException e) {
                // Client đã ngắt kết nối
                System.out.println("Client đã ngắt kết nối: " + clientSocket.getInetAddress().getHostAddress());
            } finally {
                // Đóng kết nối
                try {
                    if (ois != null) ois.close();
                    if (oos != null) oos.close();
                    if (clientSocket != null) clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Main method để chạy server
     */
    public static void main(String[] args) {
        LibraryServer server = new LibraryServer();
        
        // Thêm shutdown hook để đóng server đúng cách
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nĐang dừng server...");
            server.stop();
        }));
        
        // Khởi động server
        server.start();
    }
}

