# 📚 HỆ THỐNG QUẢN LÝ THƯ VIỆN

Hệ thống quản lý thư viện được xây dựng bằng **Java Swing**, kết nối với **MySQL database**. Hệ thống hỗ trợ quản lý toàn bộ hoạt động của thư viện từ quản lý sách, độc giả, mượn trả sách đến thống kê báo cáo.

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

## 📋 Yêu Cầu Hệ Thống

- **Java:** JDK 8 trở lên (khuyến nghị Java 11+)
- **Database:** MySQL 5.7+ hoặc MySQL 8.0+
- **IDE:** Eclipse, IntelliJ IDEA, hoặc NetBeans
- **MySQL Connector:** Đã có sẵn trong thư mục `lib/`
- **Hệ điều hành:** Windows, Linux, macOS

---

## 🚀 Hướng Dẫn Cài Đặt

### Bước 1: Cài Đặt MySQL

1. **Cài đặt MySQL Server:**
   - Tải và cài đặt XAMPP (khuyến nghị) hoặc MySQL Server
   - Khởi động MySQL service

2. **Tạo Database:**
   ```sql
   CREATE DATABASE library_db;
   USE library_db;
   ```

### Bước 2: Import Database Schema

1. Mở file SQL script trong thư mục `lib/library_db.sql`
2. Import vào database `library_db` bằng phpMyAdmin hoặc MySQL Workbench
3. Hoặc chạy trực tiếp file SQL trong MySQL

### Bước 3: Cấu Hình Database Connection

Mở file `src/utils/DatabaseConnection.java` và cập nhật thông tin kết nối:

```java
private static final String URL = "jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
private static final String USER = "root";          // Tên người dùng MySQL
private static final String PASSWORD = "";          // Mật khẩu MySQL (để trống nếu không có)
```

### Bước 4: Thêm MySQL Connector vào Project

1. **Eclipse:**
   - Right-click project → Properties → Java Build Path → Libraries
   - Add External JARs → Chọn file `lib/mysql-connector-j-8.4.0.jar`

2. **IntelliJ IDEA:**
   - File → Project Structure → Libraries
   - Add → Chọn file `lib/mysql-connector-j-8.4.0.jar`

### Bước 5: Chạy Ứng Dụng

**Cách 1: Chạy từ DangNhap (Khuyến nghị)**
- Mở file `src/view/DangNhap.java`
- Run as Java Application
- Đăng nhập với tài khoản trong database

**Cách 2: Chạy từ MainForm (Bỏ qua đăng nhập)**
- Mở file `src/view/MainForm.java`
- Run as Java Application

---

## 🔐 Tài Khoản Đăng Nhập

### Tạo Tài Khoản Mới

Nếu chưa có tài khoản, tạo trong database:

```sql
-- Tạo tài khoản Quản lý
INSERT INTO nhanvien (hoTen, sdt, vaiTro, taiKhoan, matKhau) 
VALUES ('Admin', '0123456789', 'Quản lý', 'admin', 'admin123');

-- Tạo tài khoản Thủ thư
INSERT INTO nhanvien (hoTen, sdt, vaiTro, taiKhoan, matKhau) 
VALUES ('Thủ thư 1', '0987654321', 'Thủ thư', 'thuthu1', 'thuthu123');
```

### Vai Trò Hệ Thống

- **Quản lý (Admin):**
  - Quản lý nhân viên (thêm, sửa, xóa thủ thư)
  - Tìm kiếm (phiếu mượn, độc giả, sách)
  
- **Thủ thư (Librarian):**
  - Quản lý sách, độc giả, thể loại, tác giả
  - Mượn/trả sách
  - Quản lý phiếu mượn/trả
  - Tra cứu sách
  - Thống kê và báo cáo

---

## 📖 Hướng Dẫn Sử Dụng Chi Tiết

### 1. 🔑 Đăng Nhập Hệ Thống

**Mô tả:** Xác thực người dùng trước khi sử dụng hệ thống.

**Cách sử dụng:**
1. Mở ứng dụng → Form đăng nhập hiển thị
2. Nhập **Tên đăng nhập** (tài khoản)
3. Nhập **Mật khẩu**
4. Click nút **"ĐĂNG NHẬP"**
5. Nếu đúng, hệ thống sẽ chuyển đến màn hình chính với menu theo vai trò

**Lưu ý:**
- Tài khoản và mật khẩu phân biệt hoa thường
- Sau khi đăng nhập, menu sẽ hiển thị theo quyền của người dùng

---

### 2. 📚 Quản Lý Sách

**Mô tả:** Quản lý toàn bộ thông tin sách trong thư viện (chỉ Thủ thư).

**Các chức năng:**
- ✅ Thêm sách mới
- ✅ Sửa thông tin sách
- ✅ Xóa sách
- ✅ Tìm kiếm sách
- ✅ Xem danh sách sách

**Cách sử dụng:**

**Thêm sách:**
1. Menu **"Quản Lý"** → **"Quản Lý Sách"**
2. Click nút **"Thêm mới"**
3. Điền đầy đủ thông tin:
   - Tên sách (bắt buộc)
   - Tác giả (chọn từ danh sách)
   - Thể loại (chọn từ danh sách)
   - Năm xuất bản
   - Nhà xuất bản
   - Số lượng tổng
   - Giá tiền
4. Click **"Thêm"** để lưu

**Sửa sách:**
1. Chọn sách cần sửa trong bảng
2. Click nút **"Sửa"**
3. Cập nhật thông tin
4. Click **"Lưu"**

**Xóa sách:**
1. Chọn sách cần xóa
2. Click nút **"Xóa"**
3. Xác nhận xóa

**Tìm kiếm:**
- Nhập từ khóa vào ô tìm kiếm (tên sách, tác giả, thể loại)
- Kết quả hiển thị tự động khi gõ

---

### 3. 👥 Quản Lý Độc Giả

**Mô tả:** Quản lý thông tin bạn đọc/độc giả (chỉ Thủ thư).

**Các chức năng:**
- ✅ Thêm độc giả mới
- ✅ Sửa thông tin độc giả
- ✅ Xóa độc giả
- ✅ Tìm kiếm độc giả

**Cách sử dụng:**

**Thêm độc giả:**
1. Menu **"Quản Lý"** → **"Quản Lý Độc Giả"**
2. Click **"Thêm mới"**
3. Điền thông tin:
   - Họ tên (bắt buộc)
   - Lớp
   - Số điện thoại (bắt buộc, 10-11 số)
   - Email (định dạng email hợp lệ)
   - Ngày sinh
4. Click **"Thêm"**

**Sửa/Xóa:** Tương tự như quản lý sách

---

### 4. 📖 Mượn Sách

**Mô tả:** Tạo phiếu mượn sách cho độc giả (chỉ Thủ thư).

**Cách sử dụng:**
1. Menu **"Giao Dịch"** → **"Mượn Sách"**
2. **Bước 1 - Nhập thông tin độc giả:**
   - Nhập **Mã bạn đọc** → Nhấn **Enter**
   - Hệ thống tự động load thông tin (Họ tên, Lớp)
3. **Bước 2 - Chọn sách:**
   - Tìm kiếm sách trong ô tìm kiếm (nếu cần)
   - Chọn sách bằng cách **check vào checkbox**
   - Nhập số lượng mượn (mặc định 1)
   - Click **"Thêm vào phiếu"**
   - Sách sẽ xuất hiện trong bảng "Danh sách sách mượn"
4. **Bước 3 - Chọn ngày hẹn trả:**
   - Chọn ngày hẹn trả từ spinner
5. **Bước 4 - Tạo phiếu:**
   - Click nút **"TẠO PHIẾU MƯỢN"**
   - Hệ thống sẽ:
     - Tạo phiếu mượn
     - Giảm số lượng sách trong kho
     - Hiển thị thông báo thành công

**Lưu ý:**
- Chỉ có thể mượn sách còn trong kho (số lượng hiện tại > 0)
- Có thể mượn nhiều sách trong một phiếu
- Ngày hẹn trả phải sau ngày hiện tại

---

### 5. 📥 Trả Sách

**Mô tả:** Xử lý trả sách và tính tiền phạt nếu quá hạn (chỉ Thủ thư).

**Cách sử dụng:**
1. Menu **"Giao Dịch"** → **"Trả Sách"**
2. **Nhập mã phiếu mượn:**
   - Nhập **Mã phiếu mượn** → Nhấn **Enter**
   - Hệ thống tự động load:
     - Thông tin độc giả
     - Ngày mượn, hạn trả
     - Số ngày quá hạn (nếu có)
     - Tiền phạt (5,000đ/ngày quá hạn)
     - Danh sách sách đã mượn
3. **Kiểm tra tình trạng sách:**
   - Với mỗi cuốn sách, chọn tình trạng:
     - **Tốt**: Sách còn nguyên vẹn
     - **Hỏng nhẹ**: Sách bị hỏng nhẹ
     - **Hỏng nặng**: Sách bị hỏng nặng
     - **Mất**: Sách bị mất
4. **Hoàn tất trả sách:**
   - Click nút **"HOÀN TẤT TRẢ SÁCH"**
   - Hệ thống sẽ:
     - Tạo phiếu trả
     - Cập nhật số lượng sách trong kho
     - Cập nhật trạng thái phiếu mượn thành "Đã trả"
     - Hiển thị thông báo thành công

**Tính tiền phạt:**
- **5,000 VNĐ/ngày** cho mỗi ngày quá hạn
- Ví dụ: Quá hạn 3 ngày = 15,000 VNĐ

---

### 6. 📋 Quản Lý Phiếu Mượn

**Mô tả:** Xem và quản lý tất cả phiếu mượn (chỉ Thủ thư).

**Các chức năng:**
- ✅ Xem danh sách tất cả phiếu mượn
- ✅ Tìm kiếm phiếu mượn
- ✅ Lọc theo trạng thái (Tất cả, Đang mượn, Đã trả, Quá hạn)
- ✅ Xem chi tiết phiếu mượn

**Cách sử dụng:**
1. Menu **"Giao Dịch"** → **"Quản Lý Phiếu Mượn"**
2. **Xem danh sách:**
   - Bảng hiển thị: Mã phiếu, Mã độc giả, Họ tên, Nhân viên, Ngày mượn, Hạn trả, Quá hạn, Trạng thái
3. **Tìm kiếm:**
   - Nhập từ khóa (mã phiếu, tên độc giả) vào ô tìm kiếm
4. **Lọc theo trạng thái:**
   - Chọn trạng thái từ dropdown
5. **Xem chi tiết:**
   - Chọn phiếu → Click **"Xem Chi Tiết"**
   - Hiển thị thông tin đầy đủ và danh sách sách mượn

---

### 7. 📄 Quản Lý Phiếu Trả

**Mô tả:** Xem và quản lý tất cả phiếu trả sách (chỉ Thủ thư).

**Các chức năng:**
- ✅ Xem danh sách phiếu trả
- ✅ Tìm kiếm phiếu trả
- ✅ Xem chi tiết phiếu trả
- ✅ Thống kê tổng tiền phạt

**Cách sử dụng:**
1. Menu **"Quản Lý"** → **"Quản Lý Phiếu Trả"**
2. **Xem danh sách:**
   - Bảng hiển thị: Mã PT, Mã PM, Mã BD, Họ tên bạn đọc, Nhân viên, Ngày trả, Tiền phạt, Ghi chú
   - Các dòng có tiền phạt được tô màu đỏ nhạt
3. **Tìm kiếm:**
   - Nhập từ khóa (mã phiếu trả, mã phiếu mượn, tên bạn đọc)
4. **Xem chi tiết:**
   - Chọn phiếu → Click **"Xem Chi Tiết"**
   - Hiển thị thông tin phiếu và danh sách sách đã trả

---

### 8. 🔍 Tra Cứu Sách

**Mô tả:** Tìm kiếm sách theo nhiều tiêu chí (chỉ Thủ thư).

**Các tiêu chí tìm kiếm:**
- ✅ Tên sách
- ✅ Tác giả
- ✅ Thể loại
- ✅ Năm xuất bản
- ✅ Nhà xuất bản

**Cách sử dụng:**
1. Menu **"Giao Dịch"** → **"Tra Cứu Sách"** hoặc **"Tìm Kiếm"** → **"Tìm Sách"**
2. **Nhập thông tin tìm kiếm:**
   - Có thể nhập một hoặc nhiều tiêu chí
   - Để trống tiêu chí nào thì bỏ qua tiêu chí đó
3. **Kết quả:**
   - Hiển thị danh sách sách khớp với điều kiện
   - Thanh trạng thái hiển thị số lượng kết quả

**Đặc điểm:**
- Tìm kiếm không phân biệt hoa thường
- Tìm kiếm không phân biệt dấu (ví dụ: "Nguyen" tìm được "Nguyễn")
- Tìm kiếm theo từ khóa một phần

---

### 9. ⚠️ Theo Dõi Quá Hạn

**Mô tả:** Theo dõi và cảnh báo sách mượn quá hạn (chỉ Thủ thư).

**Các chức năng:**
- ✅ Xem danh sách phiếu quá hạn
- ✅ Tìm kiếm phiếu quá hạn
- ✅ Xem chi tiết phiếu quá hạn
- ✅ Mở form trả sách trực tiếp
- ✅ Thống kê tổng số ngày quá hạn và tiền phạt dự kiến

**Cách sử dụng:**
1. Menu **"Báo Cáo"** → **"Theo Dõi Quá Hạn"**
2. **Xem danh sách:**
   - Bảng hiển thị tất cả phiếu quá hạn với thông tin:
     - Mã phiếu, Mã độc giả, Họ tên, Ngày mượn, Hạn trả
     - Số ngày quá hạn
     - Tiền phạt dự kiến
3. **Tìm kiếm:**
   - Nhập mã phiếu hoặc tên độc giả
4. **Xem chi tiết:**
   - Chọn phiếu → Click **"Xem Chi Tiết"**
5. **Trả sách:**
   - Chọn phiếu → Click **"Trả Sách"**
   - Mở form trả sách với mã phiếu đã chọn

**Cảnh báo tự động:**
- Trên màn hình chính (MainForm) luôn hiển thị số phiếu quá hạn
- Màu vàng khi có phiếu quá hạn

---

### 10. 📊 Thống Kê Sách Được Mượn

**Mô tả:** Thống kê chi tiết các sách đang được mượn (chỉ Thủ thư).

**Thông tin hiển thị:**
- ✅ Danh sách sách đang được mượn
- ✅ Thông tin phiếu mượn (mã phiếu, độc giả, ngày mượn, hạn trả)
- ✅ Trạng thái (Đang mượn/Quá hạn)
- ✅ Tổng số đầu sách đang mượn
- ✅ Tổng số cuốn sách đang mượn

**Cách sử dụng:**
1. Menu **"Thống Kê"** → **"Thống Kê Sách Được Mượn"**
2. **Xem danh sách:**
   - Bảng hiển thị từng cuốn sách đang mượn với đầy đủ thông tin
   - Màu sắc phân biệt: Vàng nhạt (Đang mượn), Đỏ nhạt (Quá hạn)
3. **Tìm kiếm:**
   - Nhập tên sách, mã sách, hoặc mã phiếu

---

### 11. 👥 Thống Kê Độc Giả

**Mô tả:** Thống kê chi tiết về độc giả và hoạt động mượn sách (chỉ Thủ thư).

**Thông tin hiển thị:**
- ✅ Danh sách tất cả độc giả
- ✅ Số phiếu mượn của mỗi độc giả
- ✅ Số sách đã mượn (tổng)
- ✅ Số sách đang mượn
- ✅ Trạng thái (Chưa mượn, Đã từng mượn, Đang mượn)

**Cách sử dụng:**
1. Menu **"Thống Kê"** → **"Thống Kê Độc Giả"**
2. **Xem danh sách:**
   - Bảng hiển thị thông tin đầy đủ về từng độc giả
3. **Tìm kiếm:**
   - Nhập tên độc giả, mã độc giả, hoặc lớp
4. **Thống kê tổng:**
   - Tổng số độc giả
   - Tổng số phiếu mượn
   - Tổng số sách đã mượn

---

### 12. 📈 Thống Kê & Báo Cáo Tổng Quan

**Mô tả:** Xem tổng quan và xuất báo cáo (chỉ Thủ thư).

**Các tab:**
1. **Tổng quan:**
   - Tổng sách trong thư viện
   - Sách đang được mượn
   - Phiếu quá hạn
   - Độc giả hoạt động

2. **Phiếu quá hạn:**
   - Danh sách chi tiết các phiếu quá hạn

3. **Xuất báo cáo:**
   - Xuất danh sách quá hạn ra file XML

**Cách sử dụng:**
1. Menu **"Báo Cáo"** → **"Thống Kê & Báo Cáo"**
2. Chọn tab muốn xem
3. **Xuất XML:**
   - Tab "Xuất báo cáo" → Click **"XUẤT DANH SÁCH QUÁ HẠN RA XML"**
   - File sẽ được lưu tại thư mục project với tên `log_qua_han.xml`

---

### 13. 🏷️ Quản Lý Thể Loại

**Mô tả:** Quản lý thể loại sách (chỉ Thủ thư).

**Các chức năng:**
- ✅ Thêm thể loại mới
- ✅ Sửa tên thể loại
- ✅ Xóa thể loại
- ✅ Tìm kiếm thể loại

**Cách sử dụng:**
1. Menu **"Quản Lý"** → **"Quản Lý Thể Loại"**
2. **Thêm mới:**
   - Click **"Thêm mới"** → Nhập tên thể loại → **"Thêm"**
3. **Sửa:**
   - Chọn thể loại → **"Sửa"** → Nhập tên mới → **"Lưu"**
4. **Xóa:**
   - Chọn thể loại → **"Xóa"** → Xác nhận

**Lưu ý:** Không thể xóa thể loại đã có sách sử dụng

---

### 14. ✍️ Quản Lý Tác Giả

**Mô tả:** Quản lý thông tin tác giả (chỉ Thủ thư).

**Các chức năng:**
- ✅ Thêm tác giả mới
- ✅ Sửa thông tin tác giả
- ✅ Xóa tác giả
- ✅ Tìm kiếm tác giả

**Cách sử dụng:**
1. Menu **"Quản Lý"** → **"Quản Lý Tác Giả"**
2. **Thêm mới:**
   - Click **"Thêm mới"**
   - Điền: Tên tác giả, Năm sinh, Quốc tịch
   - Click **"Thêm"**
3. **Sửa/Xóa:** Tương tự các chức năng khác

---

### 15. 👨‍💼 Quản Lý Nhân Viên (Chỉ Admin)

**Mô tả:** Quản lý thông tin nhân viên/thủ thư (chỉ Quản lý).

**Các chức năng:**
- ✅ Thêm nhân viên mới
- ✅ Sửa thông tin nhân viên
- ✅ Xóa nhân viên
- ✅ Tìm kiếm nhân viên

**Cách sử dụng:**
1. Menu **"Quản Lý Nhân Viên"** → **"Quản Lý Thủ Thư"**
2. **Thêm mới:**
   - Click **"Thêm mới"**
   - Điền đầy đủ:
     - Họ tên (bắt buộc)
     - Số điện thoại (bắt buộc, 10-11 số)
     - Vai trò (Thủ thư hoặc Quản lý)
     - Tài khoản (bắt buộc, không trùng)
     - Mật khẩu (bắt buộc, tối thiểu 6 ký tự)
   - Click **"Thêm"**
3. **Sửa:**
   - Chọn nhân viên → **"Sửa"**
   - Cập nhật thông tin → **"Lưu"**
4. **Xóa:**
   - Chọn nhân viên → **"Xóa"** → Xác nhận

**Lưu ý:**
- Không thể xóa nhân viên đã có phiếu mượn liên quan
- Tài khoản phải duy nhất

---

## 📁 Cấu Trúc Project

```
LibraryManagement/
├── src/
│   ├── view/              # Giao diện người dùng (Swing)
│   │   ├── DangNhap.java          # Form đăng nhập
│   │   ├── MainForm.java          # Màn hình chính
│   │   ├── QuanLySach.java        # Quản lý sách
│   │   ├── QuanLyDocGia.java      # Quản lý độc giả
│   │   ├── MuonSach.java          # Mượn sách
│   │   ├── TraSach.java           # Trả sách
│   │   ├── QuanLyPhieuMuon.java   # Quản lý phiếu mượn
│   │   ├── QuanLyPhieuTra.java    # Quản lý phiếu trả
│   │   ├── TraCuuSach.java        # Tra cứu sách
│   │   ├── ThongKe.java           # Thống kê tổng quan
│   │   ├── ThongKeSachDuocMuon.java  # Thống kê sách mượn
│   │   ├── ThongKeDocGia.java     # Thống kê độc giả
│   │   ├── TheoDoiQuaHan.java     # Theo dõi quá hạn
│   │   ├── QuanLyNhanVien.java    # Quản lý nhân viên
│   │   └── ...                    # Các form khác
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
└── README.md             # File này
```

---

## ⚠️ Lưu Ý Quan Trọng

1. **Database phải chạy** trước khi mở ứng dụng
2. **Kiểm tra connection string** trong `DatabaseConnection.java` nếu không kết nối được
3. **Đảm bảo MySQL Connector** đã được thêm vào classpath
4. **Port 3306** phải mở và không bị chiếm
5. **Backup database** thường xuyên để tránh mất dữ liệu
6. **Không xóa** dữ liệu đang được sử dụng (sách có trong phiếu mượn, thể loại có sách, v.v.)

---

## 🐛 Xử Lý Lỗi Thường Gặp

### ❌ Lỗi: "Không tìm thấy MySQL Driver"

**Nguyên nhân:** MySQL Connector chưa được thêm vào classpath.

**Giải pháp:**
1. Kiểm tra file `mysql-connector-j-8.4.0.jar` trong thư mục `lib/`
2. Thêm vào Build Path trong IDE:
   - **Eclipse:** Right-click project → Properties → Java Build Path → Libraries → Add External JARs
   - **IntelliJ:** File → Project Structure → Libraries → Add → Chọn file JAR

---

### ❌ Lỗi: "Kết nối CSDL thất bại"

**Nguyên nhân:** Không kết nối được với MySQL.

**Giải pháp:**
1. ✅ Kiểm tra MySQL đang chạy (XAMPP Control Panel hoặc Services)
2. ✅ Kiểm tra database `library_db` đã tồn tại:
   ```sql
   SHOW DATABASES;
   ```
3. ✅ Kiểm tra username/password trong `DatabaseConnection.java`
4. ✅ Kiểm tra port 3306 không bị chiếm
5. ✅ Kiểm tra firewall không chặn MySQL

---

### ❌ Lỗi: "Table doesn't exist"

**Nguyên nhân:** Database chưa được import hoặc thiếu bảng.

**Giải pháp:**
1. Import file SQL script `lib/library_db.sql` vào database
2. Hoặc tạo các bảng theo cấu trúc trong code

---

### ❌ Lỗi: "Foreign key constraint fails"

**Nguyên nhân:** Đang xóa dữ liệu đang được sử dụng.

**Giải pháp:**
- Không thể xóa sách đang có trong phiếu mượn
- Không thể xóa thể loại đang có sách
- Không thể xóa độc giả đang có phiếu mượn
- Không thể xóa nhân viên đang có phiếu mượn

**Cách xử lý:**
- Xóa hoặc cập nhật các bản ghi liên quan trước
- Hoặc sử dụng chức năng xóa có kiểm tra trong hệ thống

---

### ❌ Lỗi: "Data truncated for column"

**Nguyên nhân:** Dữ liệu nhập vào không đúng định dạng hoặc quá dài.

**Giải pháp:**
- Kiểm tra định dạng dữ liệu (SĐT: 10-11 số, Email: đúng format)
- Kiểm tra độ dài dữ liệu không vượt quá giới hạn trong database

---

## 📝 Ghi Chú Kỹ Thuật

- **Kiến trúc:** MVC Pattern (Model-View-Controller)
- **Database:** MySQL với transaction support
- **UI Framework:** Java Swing
- **Validation:** Client-side validation cho tất cả input
- **Search:** Case-insensitive và accent-insensitive search
- **Security:** Phân quyền theo vai trò (Role-based access control)
## 🎉 Kết Luận

Hệ thống quản lý thư viện cung cấp đầy đủ các chức năng cần thiết cho việc quản lý thư viện hiện đại. Với giao diện thân thiện, phân quyền rõ ràng và các tính năng mạnh mẽ, hệ thống sẽ giúp việc quản lý thư viện trở nên dễ dàng và hiệu quả hơn.

**Chúc bạn sử dụng thành công! 🎉**

---

#   L i b r a r y _ m a n a g e m e n t  
 