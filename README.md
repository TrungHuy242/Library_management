# 📚 Hệ Thống Quản Lý Thư Viện

> Hệ thống quản lý thư viện được xây dựng bằng **Java Swing**, kết nối với **MySQL database**. Hệ thống hỗ trợ quản lý toàn bộ hoạt động của thư viện từ quản lý sách, độc giả, mượn trả sách đến thống kê báo cáo.

## 📋 Mục Lục

- [Tổng Quan](#-tổng-quan)
- [Tính Năng](#-tính-năng)
- [Yêu Cầu Hệ Thống](#-yêu-cầu-hệ-thống)
- [Cài Đặt](#-cài-đặt)
- [Cấu Hình](#-cấu-hình)
- [Hướng Dẫn Sử Dụng](#-hướng-dẫn-sử-dụng)
- [Cấu Trúc Project](#-cấu-trúc-project)
- [Xử Lý Lỗi](#-xử-lý-lỗi)
- [Tác Giả](#-tác-giả)

---

## 🎯 Tổng Quan

Hệ thống được thiết kế với kiến trúc **MVC (Model-View-Controller)**, hỗ trợ **phân quyền người dùng** (Quản lý và Thủ thư), và cung cấp đầy đủ các chức năng cần thiết cho việc quản lý thư viện hiện đại.

### ✨ Đặc Điểm Nổi Bật

- ✅ **Giao diện thân thiện**: Thiết kế đơn giản, dễ sử dụng
- ✅ **Phân quyền rõ ràng**: 2 vai trò (Quản lý và Thủ thư) với quyền hạn khác nhau
- ✅ **Quản lý đầy đủ**: Sách, độc giả, mượn trả, thống kê
- ✅ **Cảnh báo tự động**: Thông báo sách mượn quá hạn
- ✅ **Tìm kiếm thông minh**: Tìm kiếm không phân biệt hoa thường, dấu
- ✅ **Báo cáo chi tiết**: Thống kê và xuất báo cáo XML

---

## 🚀 Tính Năng

### Vai Trò Quản Lý (Admin)
- Quản lý nhân viên (thêm, sửa, xóa thủ thư)
- Tìm kiếm phiếu mượn, độc giả, sách

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

### Bước 4: Chạy Ứng Dụng

**Cách 1: Chạy từ DangNhap (Khuyến nghị)**
```
Mở file: src/view/DangNhap.java
Run as Java Application
Đăng nhập với tài khoản trong database
```

**Cách 2: Chạy từ MainForm (Bỏ qua đăng nhập)**
```
Mở file: src/view/MainForm.java
Run as Java Application
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

## 📖 Hướng Dẫn Sử Dụng

### 1. Đăng Nhập Hệ Thống

1. Mở ứng dụng → Form đăng nhập hiển thị
2. Nhập **Tên đăng nhập** và **Mật khẩu**
3. Click **"ĐĂNG NHẬP"**
4. Hệ thống sẽ chuyển đến màn hình chính với menu theo vai trò

### 2. Quản Lý Sách

**Thêm sách:**
- Menu **"Quản Lý"** → **"Quản Lý Sách"** → **"Thêm mới"**
- Điền đầy đủ thông tin: Tên sách, Tác giả, Thể loại, Năm xuất bản, Nhà xuất bản, Số lượng, Giá tiền
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

### 10. Thống Kê Sách Được Mượn

- Menu **"Thống Kê"** → **"Thống Kê Sách Được Mượn"**
- Xem danh sách sách đang mượn với đầy đủ thông tin
- Thống kê: Tổng số đầu sách, Tổng số cuốn

### 11. Thống Kê Độc Giả

- Menu **"Thống Kê"** → **"Thống Kê Độc Giả"**
- Xem thống kê về độc giả và hoạt động mượn sách
- Thống kê: Số phiếu mượn, Số sách đã mượn, Số sách đang mượn

### 12. Thống Kê & Báo Cáo Tổng Quan

- Menu **"Báo Cáo"** → **"Thống Kê & Báo Cáo"**
- Tab **"Tổng quan"**: Xem tổng quan hệ thống
- Tab **"Phiếu quá hạn"**: Danh sách chi tiết
- Tab **"Xuất báo cáo"**: Xuất danh sách quá hạn ra file XML

### 13. Quản Lý Thể Loại

- Menu **"Quản Lý"** → **"Quản Lý Thể Loại"**
- Thêm, sửa, xóa, tìm kiếm thể loại

**Lưu ý:** Không thể xóa thể loại đã có sách sử dụng

### 14. Quản Lý Tác Giả

- Menu **"Quản Lý"** → **"Quản Lý Tác Giả"**
- Thêm, sửa, xóa, tìm kiếm tác giả

### 15. Quản Lý Nhân Viên (Chỉ Admin)

- Menu **"Quản Lý Nhân Viên"** → **"Quản Lý Thủ Thư"**
- Thêm, sửa, xóa, tìm kiếm nhân viên
- Điền đầy đủ: Họ tên, SĐT, Vai trò, Tài khoản, Mật khẩu

**Lưu ý:**
- Không thể xóa nhân viên đã có phiếu mượn liên quan
- Tài khoản phải duy nhất

---

## 📁 Cấu Trúc Project

```
LibraryManagement/
├── src/
│   ├── view/              # Giao diện người dùng (Swing)
│   │   ├── DangNhap.java
│   │   ├── MainForm.java
│   │   ├── QuanLySach.java
│   │   ├── QuanLyDocGia.java
│   │   ├── MuonSach.java
│   │   ├── TraSach.java
│   │   ├── QuanLyPhieuMuon.java
│   │   ├── QuanLyPhieuTra.java
│   │   ├── TraCuuSach.java
│   │   ├── ThongKe.java
│   │   ├── ThongKeSachDuocMuon.java
│   │   ├── ThongKeDocGia.java
│   │   ├── TheoDoiQuaHan.java
│   │   └── QuanLyNhanVien.java
│   ├── controller/        # Logic nghiệp vụ
│   │   ├── SachController.java
│   │   ├── BanDocController.java
│   │   ├── PhieuMuonController.java
│   │   └── ...
│   ├── dao/              # Truy cập dữ liệu
│   │   ├── SachDAO.java
│   │   ├── BanDocDao.java
│   │   └── ...
│   ├── model/            # Entity classes
│   │   ├── sach.java
│   │   ├── banDoc.java
│   │   └── ...
│   └── utils/            # Tiện ích
│       └── DatabaseConnection.java
├── lib/                  # Thư viện
│   ├── mysql-connector-j-8.4.0.jar
│   └── library_db.sql    # Database schema
└── README.md
```

---

## 🐛 Xử Lý Lỗi

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

### ❌ Lỗi: "Foreign key constraint fails"

**Giải pháp:**
- Không thể xóa dữ liệu đang được sử dụng (sách có trong phiếu mượn, thể loại có sách, v.v.)
- Xóa hoặc cập nhật các bản ghi liên quan trước

### ❌ Lỗi: "Data truncated for column"

**Giải pháp:**
- Kiểm tra định dạng dữ liệu (SĐT: 10-11 số, Email: đúng format)
- Kiểm tra độ dài dữ liệu không vượt quá giới hạn

---

## ⚠️ Lưu Ý Quan Trọng

1. **Database phải chạy** trước khi mở ứng dụng
2. **Kiểm tra connection string** trong `DatabaseConnection.java` nếu không kết nối được
3. **Đảm bảo MySQL Connector** đã được thêm vào classpath
4. **Port 3306** phải mở và không bị chiếm
5. **Backup database** thường xuyên để tránh mất dữ liệu
6. **Không xóa** dữ liệu đang được sử dụng

---

## 📝 Ghi Chú Kỹ Thuật

- **Kiến trúc:** MVC Pattern (Model-View-Controller)
- **Database:** MySQL với transaction support
- **UI Framework:** Java Swing
- **Validation:** Client-side validation cho tất cả input
- **Search:** Case-insensitive và accent-insensitive search
- **Security:** Phân quyền theo vai trò (Role-based access control)

---

## 👨‍💻 Tác Giả

Project được phát triển cho mục đích học tập và quản lý thư viện.

---

## 📄 License

Dự án học tập - Sử dụng tự do.

---

## 🎉 Kết Luận

Hệ thống quản lý thư viện cung cấp đầy đủ các chức năng cần thiết cho việc quản lý thư viện hiện đại. Với giao diện thân thiện, phân quyền rõ ràng và các tính năng mạnh mẽ, hệ thống sẽ giúp việc quản lý thư viện trở nên dễ dàng và hiệu quả hơn.

**Chúc bạn sử dụng thành công! 🎉**

---

*Nếu có thắc mắc hoặc gặp vấn đề, vui lòng kiểm tra lại các bước cài đặt và cấu hình ở trên.*
