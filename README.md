# 📚 Hệ Thống Quản Lý Thư Viện

> Hệ thống quản lý thư viện được xây dựng bằng **Java Swing** với kiến trúc **Client-Server**, kết nối với **MySQL database**. Hệ thống hỗ trợ quản lý toàn bộ hoạt động của thư viện từ quản lý sách, độc giả, mượn trả sách đến thống kê báo cáo.

## 📋 Mục Lục

- [Tổng Quan](#-tổng-quan)
- [Kiến Trúc Hệ Thống](#-kiến-trúc-hệ-thống)
- [Tính Năng](#-tính-năng)
- [Yêu Cầu Hệ Thống](#-yêu-cầu-hệ-thống)
- [Cài Đặt](#-cài-đặt)
- [Cấu Hình](#-cấu-hình)
- [Hướng Dẫn Chạy](#-hướng-dẫn-chạy)
- [Hướng Dẫn Sử Dụng](#-hướng-dẫn-sử-dụng)
- [Cấu Trúc Project](#-cấu-trúc-project)
- [Xử Lý Lỗi](#-xử-lý-lỗi)

---

## 🎯 Tổng Quan

Hệ thống được thiết kế với:
- ✅ **Kiến trúc Client-Server**: Sử dụng Socket Programming (Java Socket/ServerSocket)
- ✅ **Mô hình MVC**: Tách biệt rõ ràng Model-View-Controller
- ✅ **Phân quyền người dùng**: 2 vai trò (Quản lý và Thủ thư) với quyền hạn khác nhau
- ✅ **Dữ liệu có cấu trúc**: MySQL Database
- ✅ **Dữ liệu không có cấu trúc**: XML Export (báo cáo quá hạn)

### ✨ Đặc Điểm Nổi Bật

- ✅ **Kiến trúc Client-Server**: Tách biệt logic nghiệp vụ và giao diện
- ✅ **Multi-threading**: Server hỗ trợ nhiều client đồng thời
- ✅ **Giao diện thân thiện**: Thiết kế đơn giản, dễ sử dụng
- ✅ **Phân quyền rõ ràng**: 2 vai trò với quyền hạn khác nhau
- ✅ **Quản lý đầy đủ**: Sách, độc giả, mượn trả, thống kê
- ✅ **Cảnh báo tự động**: Thông báo sách mượn quá hạn
- ✅ **Tìm kiếm thông minh**: Tìm kiếm không phân biệt hoa thường, dấu
- ✅ **Báo cáo chi tiết**: Thống kê và xuất báo cáo XML

---

## 🏗️ Kiến Trúc Hệ Thống

### Client-Server Architecture

```
┌─────────────────┐         Socket (Port 9999)         ┌─────────────────┐
│                 │  ────────────────────────────────> │                 │
│   CLIENT        │  <────────────────────────────────  │   SERVER        │
│   (Swing UI)    │         Request/Response            │   (Business     │
│                 │                                     │    Logic)       │
│  - View Layer   │                                     │  - Controller   │
│  - ClientCtrl   │                                     │  - DAO          │
│                 │                                     │  - Database     │
└─────────────────┘                                     └─────────────────┘
```

### Luồng Hoạt Động

1. **Client** gửi `Request` đến Server qua Socket
2. **Server** nhận request, xử lý logic nghiệp vụ
3. **Server** kết nối Database, thực hiện thao tác
4. **Server** trả về `Response` cho Client
5. **Client** nhận response và cập nhật giao diện

### Các Package Chính

- **`src/server/`**: Server component (LibraryServer, RequestHandler, Request/Response)
- **`src/client/`**: Client component (ClientConnection, ClientController classes)
- **`src/view/`**: Giao diện người dùng (Swing)
- **`src/controller/`**: Business logic (Server-side)
- **`src/dao/`**: Data Access Object
- **`src/model/`**: Entity classes (tất cả đã implement Serializable)

---

## 🚀 Tính Năng

### Vai Trò Quản Lý (Admin)
- Quản lý nhân viên (thêm, sửa, xóa thủ thư)
- Xem tất cả thống kê và báo cáo

### Vai Trò Thủ Thư (Librarian)
- **Quản lý dữ liệu:**
  - Quản lý sách (CRUD)
  - Quản lý độc giả (CRUD)
  - Quản lý thể loại (CRUD)
  - Quản lý tác giả (CRUD)
  
- **Giao dịch:**
  - Mượn sách
  - Trả sách và tính phạt
  - Quản lý phiếu mượn
  - Quản lý phiếu trả
  
- **Tìm kiếm & Thống kê:**
  - Tra cứu sách đa tiêu chí
  - Thống kê sách được mượn
  - Thống kê độc giả
  - Theo dõi sách quá hạn
  - Xuất báo cáo XML

---

## 📋 Yêu Cầu Hệ Thống

| Thành Phần | Yêu Cầu |
|------------|---------|
| **Java** | JDK 8 trở lên (khuyến nghị Java 11+) |
| **Database** | MySQL 5.7+ hoặc MySQL 8.0+ |
| **IDE** | Eclipse, IntelliJ IDEA, hoặc NetBeans |
| **MySQL Connector** | Đã có sẵn trong thư mục `lib/` |
| **Hệ điều hành** | Windows, Linux, macOS |

---

## 🛠️ Cài Đặt

### Bước 1: Cài Đặt MySQL

1. Tải và cài đặt **XAMPP** (khuyến nghị) hoặc **MySQL Server**
2. Khởi động MySQL service
3. Tạo database mới:

```sql
CREATE DATABASE library_db;
USE library_db;
```

### Bước 2: Import Database Schema

1. Mở file SQL script: `lib/library_db.sql`
2. Import vào database `library_db` bằng:
   - phpMyAdmin
   - MySQL Workbench
   - Hoặc chạy trực tiếp file SQL trong MySQL

### Bước 3: Thêm MySQL Connector vào Project

**Eclipse:**
```
Right-click project → Properties → Java Build Path → Libraries 
→ Add External JARs → Chọn file lib/mysql-connector-j-8.4.0.jar
```

**IntelliJ IDEA:**
```
File → Project Structure → Libraries → Add 
→ Chọn file lib/mysql-connector-j-8.4.0.jar
```

---

## ⚙️ Cấu Hình

### Cấu Hình Database Connection

Mở file `src/utils/DatabaseConnection.java` và cập nhật thông tin:

```java
private static final String URL = "jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
private static final String USER = "root";          // Tên người dùng MySQL
private static final String PASSWORD = "";          // Mật khẩu MySQL (để trống nếu không có)
```

### Tạo Tài Khoản Đăng Nhập

Nếu chưa có tài khoản, tạo trong database:

```sql
-- Tạo tài khoản Quản lý
INSERT INTO nhanvien (hoTen, sdt, vaiTro, taiKhoan, matKhau) 
VALUES ('Admin', '0123456789', 'Quản lý', 'admin', 'admin123');

-- Tạo tài khoản Thủ thư
INSERT INTO nhanvien (hoTen, sdt, vaiTro, taiKhoan, matKhau) 
VALUES ('Thủ thư 1', '0987654321', 'Thủ thư', 'thuthu1', 'thuthu123');
```

---

## 🚀 Hướng Dẫn Chạy

### ⚠️ QUAN TRỌNG: Phải chạy Server TRƯỚC khi chạy Client!

### Bước 1: Khởi động Server

1. Mở file: `src/server/LibraryServer.java`
2. Run as Java Application
3. Server sẽ hiển thị:

```
========================================
   SERVER QUẢN LÝ THƯ VIỆN
========================================
Server đang lắng nghe trên port: 9999
Chờ kết nối từ client...
========================================
```

**Lưu ý**: Giữ cửa sổ Server mở trong suốt quá trình sử dụng!

### Bước 2: Khởi động Client

1. Mở file: `src/view/DangNhap.java`
2. Run as Java Application
3. Client sẽ tự động kết nối đến server
4. Đăng nhập với tài khoản trong database

### Kiểm Tra Kết Nối

- Nếu kết nối thành công: Server sẽ hiển thị "Client đã kết nối: [IP]"
- Nếu lỗi kết nối: Kiểm tra Server đã chạy chưa, port 9999 có bị chiếm không

---

## 📖 Hướng Dẫn Sử Dụng

### 1. Đăng Nhập Hệ Thống

1. Mở ứng dụng → Form đăng nhập hiển thị
2. Nhập **Tên đăng nhập** và **Mật khẩu**
3. Click **"ĐĂNG NHẬP"**
4. Hệ thống sẽ chuyển đến màn hình chính với menu theo vai trò

### 2. Quản Lý Sách

**Thêm sách:**
- Menu **"Quản Lý"** → **"Quản Lý Sách"** → **"Thêm mới"**
- Điền đầy đủ thông tin: Tên sách, Tác giả, Thể loại, Năm xuất bản, Nhà xuất bản, Số lượng
- Click **"Thêm"** để lưu

**Sửa/Xóa sách:**
- Chọn sách trong bảng → Click **"Sửa"** hoặc **"Xóa"**

**Tìm kiếm:**
- Nhập từ khóa vào ô tìm kiếm (tên sách, tác giả, thể loại)

### 3. Quản Lý Độc Giả

**Thêm độc giả:**
- Menu **"Quản Lý"** → **"Quản Lý Độc Giả"** → **"Thêm mới"**
- Điền thông tin: Họ tên, Lớp, SĐT, Email, Ngày sinh
- Click **"Thêm"**

### 4. Mượn Sách

1. Menu **"Giao Dịch"** → **"Mượn Sách"**
2. Nhập **Mã bạn đọc** → Nhấn **Enter** (hệ thống tự động load thông tin)
3. Chọn sách bằng checkbox → Nhập số lượng → Click **"Thêm vào phiếu"**
4. Chọn ngày hẹn trả
5. Click **"TẠO PHIẾU MƯỢN"**

**Lưu ý:** Chỉ có thể mượn sách còn trong kho (số lượng hiện tại > 0)

### 5. Trả Sách

1. Menu **"Giao Dịch"** → **"Trả Sách"**
2. Nhập **Mã phiếu mượn** → Nhấn **Enter**
3. Hệ thống tự động tính:
   - Số ngày quá hạn
   - Tiền phạt (5,000đ/ngày quá hạn)
4. Chọn tình trạng sách cho từng cuốn (Tốt, Hỏng nhẹ, Hỏng nặng, Mất)
5. Click **"HOÀN TẤT TRẢ SÁCH"**

### 6. Quản Lý Phiếu Mượn

- Menu **"Giao Dịch"** → **"Quản Lý Phiếu Mượn"**
- Xem danh sách, tìm kiếm, lọc theo trạng thái
- Click **"Xem Chi Tiết"** để xem thông tin đầy đủ

### 7. Quản Lý Phiếu Trả

- Menu **"Quản Lý"** → **"Quản Lý Phiếu Trả"**
- Xem danh sách, tìm kiếm, xem chi tiết
- Thống kê tổng tiền phạt

### 8. Tra Cứu Sách

- Menu **"Giao Dịch"** → **"Tra Cứu Sách"**
- Tìm kiếm theo: Tên sách, Tác giả, Thể loại, Năm xuất bản, Nhà xuất bản
- Tìm kiếm không phân biệt hoa thường và dấu

### 9. Theo Dõi Quá Hạn

- Menu **"Báo Cáo"** → **"Theo Dõi Quá Hạn"**
- Xem danh sách phiếu quá hạn
- Tìm kiếm, xem chi tiết
- Click **"Trả Sách"** để mở form trả sách trực tiếp

### 10. Thống Kê & Báo Cáo

- Menu **"Báo Cáo"** → **"Thống Kê & Báo Cáo"**
- Tab **"Tổng quan"**: Xem tổng quan hệ thống
- Tab **"Phiếu quá hạn"**: Danh sách chi tiết
- Tab **"Xuất báo cáo"**: Xuất danh sách quá hạn ra file XML

### 11. Quản Lý Thể Loại & Tác Giả

- Menu **"Quản Lý"** → **"Quản Lý Thể Loại"** / **"Quản Lý Tác Giả"**
- Thêm, sửa, xóa, tìm kiếm

**Lưu ý:** Không thể xóa thể loại/tác giả đã có sách sử dụng

### 12. Quản Lý Nhân Viên (Chỉ Admin)

- Menu **"Quản Lý Nhân Viên"** → **"Quản Lý Thủ Thư"**
- Thêm, sửa, xóa, tìm kiếm nhân viên

---

## 📁 Cấu Trúc Project

```
LibraryManagement/
├── src/
│   ├── server/              # Server component
│   │   ├── LibraryServer.java      # Server chính
│   │   ├── RequestHandler.java     # Xử lý request
│   │   ├── Request.java            # Class request
│   │   ├── Response.java           # Class response
│   │   ├── PhieuMuonData.java      # Wrapper data
│   │   └── PhieuTraData.java       # Wrapper data
│   │
│   ├── client/              # Client component
│   │   ├── ClientConnection.java           # Quản lý kết nối
│   │   ├── AuthClientController.java       # Client controller
│   │   ├── SachClientController.java
│   │   ├── BanDocClientController.java
│   │   ├── PhieuMuonClientController.java
│   │   ├── PhieuTraClientController.java
│   │   ├── TheLoaiClientController.java
│   │   └── TacGiaClientController.java
│   │
│   ├── view/                # Giao diện người dùng (Swing)
│   │   ├── DangNhap.java
│   │   ├── MainForm.java
│   │   ├── QuanLySach.java
│   │   ├── QuanLyDocGia.java
│   │   ├── MuonSach.java
│   │   ├── TraSach.java
│   │   └── ... (20+ view files)
│   │
│   ├── controller/          # Business logic (Server-side)
│   │   ├── SachController.java
│   │   ├── BanDocController.java
│   │   ├── PhieuMuonController.java
│   │   └── ... (8 controller files)
│   │
│   ├── dao/                 # Data Access Object
│   │   ├── SachDAO.java
│   │   ├── BanDocDao.java
│   │   └── ... (8 DAO files)
│   │
│   ├── model/               # Entity classes (Serializable)
│   │   ├── sach.java
│   │   ├── banDoc.java
│   │   └── ... (9 model files)
│   │
│   └── utils/               # Tiện ích
│       └── DatabaseConnection.java
│
├── lib/                     # Thư viện
│   ├── mysql-connector-j-8.4.0.jar
│   └── library_db.sql       # Database schema
│
└── README.md
```

---

## 🐛 Xử Lý Lỗi

### ❌ Lỗi: "Không thể kết nối đến server"

**Giải pháp:**
1. ✅ Kiểm tra Server đã chạy chưa (`LibraryServer.java`)
2. ✅ Kiểm tra port 9999 không bị chiếm
3. ✅ Kiểm tra firewall không chặn kết nối
4. ✅ Đảm bảo Server chạy TRƯỚC khi chạy Client

### ❌ Lỗi: "Không tìm thấy MySQL Driver"

**Giải pháp:**
1. Kiểm tra file `mysql-connector-j-8.4.0.jar` trong thư mục `lib/`
2. Thêm vào Build Path trong IDE

### ❌ Lỗi: "Kết nối CSDL thất bại"

**Giải pháp:**
- ✅ Kiểm tra MySQL đang chạy
- ✅ Kiểm tra database `library_db` đã tồn tại
- ✅ Kiểm tra username/password trong `DatabaseConnection.java`
- ✅ Kiểm tra port 3306 không bị chiếm

### ❌ Lỗi: "Table doesn't exist"

**Giải pháp:**
- Import file SQL script `lib/library_db.sql` vào database

### ❌ Lỗi: "NotSerializableException"

**Giải pháp:**
- Tất cả model classes đã implement `Serializable`
- Nếu gặp lỗi, kiểm tra lại model class có implement Serializable chưa

### ❌ Lỗi: "Foreign key constraint fails"

**Giải pháp:**
- Không thể xóa dữ liệu đang được sử dụng (sách có trong phiếu mượn, thể loại có sách, v.v.)
- Xóa hoặc cập nhật các bản ghi liên quan trước

---

## ⚠️ Lưu Ý Quan Trọng

1. **Server phải chạy TRƯỚC** khi chạy Client
2. **Database phải chạy** trước khi mở ứng dụng
3. **Kiểm tra connection string** trong `DatabaseConnection.java` nếu không kết nối được
4. **Đảm bảo MySQL Connector** đã được thêm vào classpath
5. **Port 9999** (Server) và **Port 3306** (MySQL) phải mở và không bị chiếm
6. **Backup database** thường xuyên để tránh mất dữ liệu
7. **Không xóa** dữ liệu đang được sử dụng

---

## 📝 Ghi Chú Kỹ Thuật

- **Kiến trúc:** Client-Server với Socket Programming
- **Pattern:** MVC (Model-View-Controller)
- **Database:** MySQL với transaction support
- **UI Framework:** Java Swing
- **Communication:** Object Serialization (ObjectInputStream/ObjectOutputStream)
- **Multi-threading:** Server hỗ trợ nhiều client đồng thời
- **Validation:** Client-side validation cho tất cả input
- **Search:** Case-insensitive và accent-insensitive search
- **Security:** Phân quyền theo vai trò (Role-based access control)
- **Data Export:** XML (DOM API) cho báo cáo quá hạn

---

## 🎉 Kết Luận

Hệ thống quản lý thư viện với kiến trúc Client-Server cung cấp đầy đủ các chức năng cần thiết cho việc quản lý thư viện hiện đại. Với giao diện thân thiện, phân quyền rõ ràng và các tính năng mạnh mẽ, hệ thống sẽ giúp việc quản lý thư viện trở nên dễ dàng và hiệu quả hơn.

**Chúc bạn sử dụng thành công! 🎉**

---

*Nếu có thắc mắc hoặc gặp vấn đề, vui lòng kiểm tra lại các bước cài đặt và cấu hình ở trên.*
