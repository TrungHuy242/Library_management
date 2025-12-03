-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 03, 2025 at 01:15 PM
-- Server version: 10.6.15-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `bandoc`
--

CREATE TABLE `bandoc` (
  `maBanDoc` int(11) NOT NULL,
  `hoTen` varchar(100) NOT NULL,
  `lop` varchar(20) DEFAULT NULL,
  `sdt` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `ngaySinh` date DEFAULT NULL,
  `ngayDangKy` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `bandoc`
--

INSERT INTO `bandoc` (`maBanDoc`, `hoTen`, `lop`, `sdt`, `email`, `ngaySinh`, `ngayDangKy`) VALUES
(1, 'Nguyễn Văn An đã sửa', 'D21CQCN01', '0905000111', 'an@student.com', '2003-05-15', '2025-11-29'),
(2, 'Trần Thị Bình', 'D21CQCN02', '0905000222', 'binh@mail.com', '2003-07-20', '2025-11-29'),
(3, 'Nguyễn Ngọc Quyền', 'D20CQAT01', '0905000333', 'quyen@gmail.com', '2002-03-10', '2025-11-29'),
(4, 'Lê Ngọc Tiến', 'D21CQCN03', '0905000444', 'tien@gmail.com', '2003-09-05', '2025-11-29'),
(5, 'Đỗ Quốc Đạt', 'D21CQCN04', '0905000555', 'dat@gmail.com', '2003-01-12', '2025-11-29'),
(6, 'Phan Khánh Đức', 'D21CQCN01', '0905000666', 'duc@gmail.com', '2003-11-25', '2025-11-29'),
(7, 'Trần Văn Hiền', 'D20CQCN02', '0905000777', 'hien@gmail.com', '2002-08-30', '2025-11-29'),
(8, 'Trương Phi Hoàng', 'D21CQCN05', '0905000888', 'hoang@gmail.com', '2003-04-18', '2025-11-29');

-- --------------------------------------------------------

--
-- Table structure for table `ct_phieumuon`
--

CREATE TABLE `ct_phieumuon` (
  `maPhieuMuon` int(11) NOT NULL,
  `maSach` int(11) NOT NULL,
  `soLuong` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ct_phieumuon`
--

INSERT INTO `ct_phieumuon` (`maPhieuMuon`, `maSach`, `soLuong`) VALUES
(1, 1, 1),
(1, 3, 1),
(2, 2, 2),
(3, 4, 1),
(3, 5, 1),
(4, 1, 2),
(5, 9, 1),
(5, 10, 1),
(6, 6, 1),
(6, 7, 1),
(7, 2, 1),
(8, 8, 1),
(10, 3, 1),
(13, 10, 1),
(13, 11, 1),
(14, 10, 1),
(15, 6, 1),
(16, 10, 1),
(16, 11, 1),
(17, 9, 1),
(18, 10, 7),
(20, 1, 1),
(20, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `ct_phieutra`
--

CREATE TABLE `ct_phieutra` (
  `maPhieuTra` int(11) NOT NULL,
  `maSach` int(11) NOT NULL,
  `soLuong` int(11) NOT NULL,
  `tinhTrangSach` enum('Bình thường','Hỏng','Mất') DEFAULT 'Bình thường'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ct_phieutra`
--

INSERT INTO `ct_phieutra` (`maPhieuTra`, `maSach`, `soLuong`, `tinhTrangSach`) VALUES
(1, 4, 1, 'Bình thường'),
(1, 5, 1, 'Hỏng'),
(2, 9, 1, 'Bình thường'),
(2, 10, 1, 'Bình thường'),
(7, 6, 1, 'Bình thường'),
(8, 1, 1, 'Bình thường'),
(8, 2, 1, 'Bình thường'),
(9, 10, 7, 'Bình thường'),
(10, 10, 1, 'Bình thường'),
(10, 11, 1, 'Bình thường');

-- --------------------------------------------------------

--
-- Table structure for table `nhanvien`
--

CREATE TABLE `nhanvien` (
  `maNV` int(11) NOT NULL,
  `hoTen` varchar(100) NOT NULL,
  `sdt` varchar(15) DEFAULT NULL,
  `vaiTro` enum('Quản lý','Thủ thư') DEFAULT 'Thủ thư',
  `taiKhoan` varchar(50) DEFAULT NULL,
  `matKhau` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `nhanvien`
--

INSERT INTO `nhanvien` (`maNV`, `hoTen`, `sdt`, `vaiTro`, `taiKhoan`, `matKhau`) VALUES
(1, 'Nguyễn Văn Admin', '0909123456', 'Quản lý', 'admin', '123'),
(2, 'Trương Thị Kim Ngân', '0909111222', 'Thủ thư', 'ngandu', 'ngudan'),
(3, 'Từ Nguyễn Huyền Trang', '0909333444', 'Thủ thư', 'huyentrang', 'huy123'),
(4, 'Nguyễn Ngọc Quyền', '05646465165', 'Thủ thư', 'ngocquyen', 'ngocquyen123');

-- --------------------------------------------------------

--
-- Table structure for table `phieumuon`
--

CREATE TABLE `phieumuon` (
  `maPhieuMuon` int(11) NOT NULL,
  `maBanDoc` int(11) NOT NULL,
  `maNV` int(11) NOT NULL,
  `ngayMuon` date NOT NULL DEFAULT curdate(),
  `ngayHenTra` date NOT NULL,
  `trangThai` enum('Đang mượn','Đã trả','Quá hạn') DEFAULT 'Đang mượn'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `phieumuon`
--

INSERT INTO `phieumuon` (`maPhieuMuon`, `maBanDoc`, `maNV`, `ngayMuon`, `ngayHenTra`, `trangThai`) VALUES
(1, 1, 1, '2025-11-20', '2025-12-04', 'Đã trả'),
(2, 2, 1, '2025-11-10', '2025-11-24', 'Đã trả'),
(3, 3, 2, '2025-10-25', '2025-11-08', 'Đã trả'),
(4, 4, 1, '2025-10-15', '2025-11-05', 'Quá hạn'),
(5, 5, 2, '2025-11-01', '2025-11-15', 'Đã trả'),
(6, 6, 1, '2025-11-28', '2025-12-12', 'Đã trả'),
(7, 1, 2, '2025-11-15', '2025-11-29', 'Đã trả'),
(8, 7, 1, '2025-11-05', '2025-11-19', 'Quá hạn'),
(9, 8, 2, '2025-11-25', '2025-12-09', 'Đã trả'),
(10, 3, 1, '2025-11-18', '2025-12-02', 'Đã trả'),
(13, 2, 1, '2025-11-30', '2025-12-14', 'Đã trả'),
(14, 8, 1, '2025-12-02', '2025-12-20', 'Đã trả'),
(15, 3, 1, '2025-12-03', '2025-12-12', 'Đã trả'),
(16, 1, 1, '2025-12-03', '2025-12-17', 'Đã trả'),
(17, 4, 1, '2025-12-03', '2025-12-17', 'Đang mượn'),
(18, 1, 1, '2025-12-03', '2025-12-17', 'Đã trả'),
(20, 4, 1, '2025-12-03', '2025-12-17', 'Đã trả');

-- --------------------------------------------------------

--
-- Table structure for table `phieutra`
--

CREATE TABLE `phieutra` (
  `maPhieuTra` int(11) NOT NULL,
  `maPhieuMuon` int(11) NOT NULL,
  `maNV` int(11) NOT NULL,
  `ngayTra` date NOT NULL DEFAULT curdate(),
  `tienPhat` float DEFAULT 0,
  `ghiChu` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `phieutra`
--

INSERT INTO `phieutra` (`maPhieuTra`, `maPhieuMuon`, `maNV`, `ngayTra`, `tienPhat`, `ghiChu`) VALUES
(1, 3, 2, '2025-11-20', 50000, 'Quá hạn 12 ngày'),
(2, 5, 1, '2025-11-25', 30000, 'Quá hạn 10 ngày'),
(7, 15, 1, '2025-12-03', 0, ''),
(8, 20, 1, '2025-12-03', 0, ''),
(9, 18, 1, '2025-12-03', 0, ''),
(10, 16, 4, '2025-12-03', 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `sach`
--

CREATE TABLE `sach` (
  `maSach` int(11) NOT NULL,
  `tenSach` varchar(300) NOT NULL,
  `maTacGia` int(11) DEFAULT NULL,
  `maTheLoai` int(11) DEFAULT NULL,
  `namXuatBan` year(4) DEFAULT NULL,
  `nhaXuatBan` varchar(150) DEFAULT NULL,
  `soLuongTong` int(11) NOT NULL DEFAULT 0,
  `soLuongHienTai` int(11) NOT NULL DEFAULT 0,
  `trangThai` enum('Bình thường','Hỏng','Mất') DEFAULT 'Bình thường'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `sach`
--

INSERT INTO `sach` (`maSach`, `tenSach`, `maTacGia`, `maTheLoai`, `namXuatBan`, `nhaXuatBan`, `soLuongTong`, `soLuongHienTai`, `trangThai`) VALUES
(1, 'Lập Trình Java Cơ Bản', 2, 1, '2023', 'NXB Giáo Dục', 20, 16, 'Bình thường'),
(2, 'Java Nâng Cao', 4, 1, '2022', 'NXB Kim Đồng', 15, 13, 'Bình thường'),
(3, 'Cấu Trúc Dữ Liệu & Giải Thuật', 3, 1, '2021', 'NXB Khoa Học', 25, 20, 'Bình thường'),
(4, 'Cơ Sở Dữ Liệu', 3, 2, '2020', 'NXB Đại Học Quốc Gia', 30, 25, 'Bình thường'),
(5, 'Toán Rời Rạc', 3, 3, '2024', 'NXB Giáo Dục', 40, 35, 'Bình thường'),
(6, 'Mắt Biếc', 5, 4, '2019', 'NXB Trẻ', 50, 46, 'Bình thường'),
(7, 'Dế Mèn Phiêu Lưu Ký', 6, 4, '1941', 'NXB Văn Học', 60, 59, 'Bình thường'),
(8, 'Chí Phèo', 7, 4, '1941', 'NXB Văn Học', 35, 35, 'Bình thường'),
(9, 'C++ Programming', 1, 1, '2018', 'NXB Giáo Dục', 18, 11, 'Bình thường'),
(10, 'Lập Trình Hướng Đối Tượng', 4, 1, '2023', 'NXB Kim Đồng', 22, 8, 'Bình thường'),
(11, 'Trung hẹ hẹ', 5, 3, '2025', 'hoanhi', 12, 12, 'Bình thường');

-- --------------------------------------------------------

--
-- Table structure for table `tacgia`
--

CREATE TABLE `tacgia` (
  `maTacGia` int(11) NOT NULL,
  `tenTacGia` varchar(100) NOT NULL,
  `namSinh` year(4) DEFAULT NULL,
  `quocTich` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `tacgia`
--

INSERT INTO `tacgia` (`maTacGia`, `tenTacGia`, `namSinh`, `quocTich`) VALUES
(1, 'Bjarne Stroustrup', '1950', 'Đan Mạch'),
(2, 'James Gosling', '1955', 'Canada'),
(3, 'Donald Knuth', '1938', 'Mỹ'),
(4, 'Herbert Schildt', '1951', 'Mỹ'),
(5, 'Nguyễn Nhật Ánh', '1955', 'Việt Nam'),
(6, 'Tô Hoài', '1920', 'Việt Nam'),
(7, 'Nam Cao', '1915', 'Việt Nam'),
(8, 'Xuân Diệu', '1916', 'Việt Nam');

-- --------------------------------------------------------

--
-- Table structure for table `theloai`
--

CREATE TABLE `theloai` (
  `maTheLoai` int(11) NOT NULL,
  `tenTheLoai` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `theloai`
--

INSERT INTO `theloai` (`maTheLoai`, `tenTheLoai`) VALUES
(2, 'Cơ sở dữ liệu'),
(1, 'Lập trình'),
(5, 'Lịch sử'),
(3, 'Toán học'),
(4, 'Văn học');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bandoc`
--
ALTER TABLE `bandoc`
  ADD PRIMARY KEY (`maBanDoc`);

--
-- Indexes for table `ct_phieumuon`
--
ALTER TABLE `ct_phieumuon`
  ADD PRIMARY KEY (`maPhieuMuon`,`maSach`),
  ADD KEY `maSach` (`maSach`);

--
-- Indexes for table `ct_phieutra`
--
ALTER TABLE `ct_phieutra`
  ADD PRIMARY KEY (`maPhieuTra`,`maSach`),
  ADD KEY `maSach` (`maSach`);

--
-- Indexes for table `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`maNV`),
  ADD UNIQUE KEY `taiKhoan` (`taiKhoan`);

--
-- Indexes for table `phieumuon`
--
ALTER TABLE `phieumuon`
  ADD PRIMARY KEY (`maPhieuMuon`),
  ADD KEY `maBanDoc` (`maBanDoc`),
  ADD KEY `maNV` (`maNV`);

--
-- Indexes for table `phieutra`
--
ALTER TABLE `phieutra`
  ADD PRIMARY KEY (`maPhieuTra`),
  ADD KEY `maPhieuMuon` (`maPhieuMuon`),
  ADD KEY `maNV` (`maNV`);

--
-- Indexes for table `sach`
--
ALTER TABLE `sach`
  ADD PRIMARY KEY (`maSach`),
  ADD KEY `maTacGia` (`maTacGia`),
  ADD KEY `maTheLoai` (`maTheLoai`);

--
-- Indexes for table `tacgia`
--
ALTER TABLE `tacgia`
  ADD PRIMARY KEY (`maTacGia`);

--
-- Indexes for table `theloai`
--
ALTER TABLE `theloai`
  ADD PRIMARY KEY (`maTheLoai`),
  ADD UNIQUE KEY `tenTheLoai` (`tenTheLoai`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bandoc`
--
ALTER TABLE `bandoc`
  MODIFY `maBanDoc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `nhanvien`
--
ALTER TABLE `nhanvien`
  MODIFY `maNV` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `phieumuon`
--
ALTER TABLE `phieumuon`
  MODIFY `maPhieuMuon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `phieutra`
--
ALTER TABLE `phieutra`
  MODIFY `maPhieuTra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `sach`
--
ALTER TABLE `sach`
  MODIFY `maSach` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `tacgia`
--
ALTER TABLE `tacgia`
  MODIFY `maTacGia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `theloai`
--
ALTER TABLE `theloai`
  MODIFY `maTheLoai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ct_phieumuon`
--
ALTER TABLE `ct_phieumuon`
  ADD CONSTRAINT `ct_phieumuon_ibfk_1` FOREIGN KEY (`maPhieuMuon`) REFERENCES `phieumuon` (`maPhieuMuon`) ON DELETE CASCADE,
  ADD CONSTRAINT `ct_phieumuon_ibfk_2` FOREIGN KEY (`maSach`) REFERENCES `sach` (`maSach`);

--
-- Constraints for table `ct_phieutra`
--
ALTER TABLE `ct_phieutra`
  ADD CONSTRAINT `ct_phieutra_ibfk_1` FOREIGN KEY (`maPhieuTra`) REFERENCES `phieutra` (`maPhieuTra`) ON DELETE CASCADE,
  ADD CONSTRAINT `ct_phieutra_ibfk_2` FOREIGN KEY (`maSach`) REFERENCES `sach` (`maSach`);

--
-- Constraints for table `phieumuon`
--
ALTER TABLE `phieumuon`
  ADD CONSTRAINT `phieumuon_ibfk_1` FOREIGN KEY (`maBanDoc`) REFERENCES `bandoc` (`maBanDoc`),
  ADD CONSTRAINT `phieumuon_ibfk_2` FOREIGN KEY (`maNV`) REFERENCES `nhanvien` (`maNV`);

--
-- Constraints for table `phieutra`
--
ALTER TABLE `phieutra`
  ADD CONSTRAINT `phieutra_ibfk_1` FOREIGN KEY (`maPhieuMuon`) REFERENCES `phieumuon` (`maPhieuMuon`),
  ADD CONSTRAINT `phieutra_ibfk_2` FOREIGN KEY (`maNV`) REFERENCES `nhanvien` (`maNV`);

--
-- Constraints for table `sach`
--
ALTER TABLE `sach`
  ADD CONSTRAINT `sach_ibfk_1` FOREIGN KEY (`maTacGia`) REFERENCES `tacgia` (`maTacGia`) ON DELETE SET NULL,
  ADD CONSTRAINT `sach_ibfk_2` FOREIGN KEY (`maTheLoai`) REFERENCES `theloai` (`maTheLoai`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
