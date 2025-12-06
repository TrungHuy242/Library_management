package server;

import controller.*;
import dao.*;
import model.*;
import server.PhieuMuonData;
import server.PhieuTraData;
import java.util.List;
import java.util.Map;

/**
 * Xử lý các request từ client
 */
public class RequestHandler {
    
    // Các controller và DAO
    private AuthController authController = new AuthController();
    private SachController sachController = new SachController();
    private BanDocController banDocController = new BanDocController();
    private PhieuMuonController phieuMuonController = new PhieuMuonController();
    private PhieuTraController phieuTraController = new PhieuTraController();
    private TheLoaiController theLoaiController = new TheLoaiController();
    private TacGiaController tacGiaController = new TacGiaController();
    
    private SachDAO sachDAO = new SachDAO();
    private BanDocDao banDocDAO = new BanDocDao();
    private PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO();
    private PhieuTraDAO phieuTraDAO = new PhieuTraDAO();
    private TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
    private TacGiaDAO tacGiaDAO = new TacGiaDAO();
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
    
    /**
     * Xử lý request và trả về response
     */
    public Response handleRequest(Request request) {
        try {
            String action = request.getAction();
            Object data = request.getData();
            
            switch (action) {
                // ========== AUTHENTICATION ==========
                case "LOGIN": {
                    if (data instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, String> loginData = (Map<String, String>) data;
                        String taiKhoan = loginData.get("taiKhoan");
                        String matKhau = loginData.get("matKhau");
                        nhanVien nv = authController.dangNhap(taiKhoan, matKhau);
                        if (nv != null) {
                            return Response.success("Đăng nhập thành công", nv);
                        } else {
                            return Response.error("Sai tài khoản hoặc mật khẩu");
                        }
                    }
                    return Response.error("Dữ liệu đăng nhập không hợp lệ");
                }
                
                // ========== SACH ==========
                case "GET_ALL_SACH": {
                    List<sach> list = sachDAO.getAll();
                    return Response.success(list);
                }
                
                case "GET_SACH_BY_ID": {
                    if (data instanceof Integer) {
                        sach s = sachController.layTheoMa((Integer) data);
                        return Response.success(s);
                    }
                    return Response.error("Mã sách không hợp lệ");
                }
                
                case "TIM_KIEM_SACH": {
                    if (data instanceof String) {
                        List<sach> list = sachDAO.timKiem((String) data);
                        return Response.success(list);
                    }
                    return Response.error("Từ khóa tìm kiếm không hợp lệ");
                }
                
                case "THEM_SACH": {
                    if (data instanceof sach) {
                        boolean result = sachDAO.themSach((sach) data);
                        if (result) {
                            return Response.success("Thêm sách thành công", null);
                        } else {
                            return Response.error("Thêm sách thất bại");
                        }
                    }
                    return Response.error("Dữ liệu sách không hợp lệ");
                }
                
                case "SUA_SACH": {
                    if (data instanceof sach) {
                        boolean result = sachDAO.suaSach((sach) data);
                        if (result) {
                            return Response.success("Sửa sách thành công", null);
                        } else {
                            return Response.error("Sửa sách thất bại");
                        }
                    }
                    return Response.error("Dữ liệu sách không hợp lệ");
                }
                
                case "XOA_SACH": {
                    if (data instanceof Integer) {
                        boolean result = sachDAO.xoaSach((Integer) data);
                        if (result) {
                            return Response.success("Xóa sách thành công", null);
                        } else {
                            return Response.error("Xóa sách thất bại");
                        }
                    }
                    return Response.error("Mã sách không hợp lệ");
                }
                
                // ========== BAN DOC ==========
                case "GET_ALL_BAN_DOC": {
                    List<banDoc> list = banDocDAO.getAll();
                    return Response.success(list);
                }
                
                case "GET_BAN_DOC_BY_ID": {
                    if (data instanceof Integer) {
                        banDoc bd = banDocController.layTheoMa((Integer) data);
                        return Response.success(bd);
                    }
                    return Response.error("Mã bạn đọc không hợp lệ");
                }
                
                case "TIM_KIEM_BAN_DOC": {
                    if (data instanceof String) {
                        List<banDoc> list = banDocController.timKiem((String) data);
                        return Response.success(list);
                    }
                    return Response.error("Từ khóa tìm kiếm không hợp lệ");
                }
                
                case "THEM_BAN_DOC": {
                    if (data instanceof banDoc) {
                        boolean result = banDocDAO.them((banDoc) data);
                        if (result) {
                            return Response.success("Thêm bạn đọc thành công", null);
                        } else {
                            return Response.error("Thêm bạn đọc thất bại");
                        }
                    }
                    return Response.error("Dữ liệu bạn đọc không hợp lệ");
                }
                
                case "SUA_BAN_DOC": {
                    if (data instanceof banDoc) {
                        boolean result = banDocDAO.sua((banDoc) data);
                        if (result) {
                            return Response.success("Sửa bạn đọc thành công", null);
                        } else {
                            return Response.error("Sửa bạn đọc thất bại");
                        }
                    }
                    return Response.error("Dữ liệu bạn đọc không hợp lệ");
                }
                
                case "XOA_BAN_DOC": {
                    if (data instanceof Integer) {
                        boolean result = banDocDAO.xoa((Integer) data);
                        if (result) {
                            return Response.success("Xóa bạn đọc thành công", null);
                        } else {
                            return Response.error("Xóa bạn đọc thất bại");
                        }
                    }
                    return Response.error("Mã bạn đọc không hợp lệ");
                }
                
                // ========== PHIEU MUON ==========
                case "GET_ALL_PHIEU_MUON": {
                    List<phieuMuon> list = phieuMuonDAO.getAll();
                    return Response.success(list);
                }
                
                case "GET_PHIEU_MUON_BY_ID": {
                    if (data instanceof Integer) {
                        phieuMuon pm = phieuMuonController.layTheoMa((Integer) data);
                        return Response.success(pm);
                    }
                    return Response.error("Mã phiếu mượn không hợp lệ");
                }
                
                case "TIM_KIEM_PHIEU_MUON": {
                    // Tìm kiếm trong danh sách tất cả phiếu mượn
                    List<phieuMuon> all = phieuMuonDAO.getAll();
                    if (data instanceof String) {
                        String keyword = ((String) data).toLowerCase();
                        List<phieuMuon> result = all.stream()
                            .filter(pm -> String.valueOf(pm.getMaPhieuMuon()).contains(keyword) ||
                                         (pm.getTenBanDoc() != null && pm.getTenBanDoc().toLowerCase().contains(keyword)))
                            .collect(java.util.stream.Collectors.toList());
                        return Response.success(result);
                    }
                    return Response.error("Từ khóa tìm kiếm không hợp lệ");
                }
                
                case "TAO_PHIEU_MUON": {
                    if (data instanceof PhieuMuonData) {
                        PhieuMuonData pmData = (PhieuMuonData) data;
                        boolean result = phieuMuonController.taoPhieuMuon(
                            pmData.getMaBanDoc(),
                            pmData.getMaNV(),
                            pmData.getNgayHenTra(),
                            pmData.getDsChiTiet()
                        );
                        if (result) {
                            return Response.success("Tạo phiếu mượn thành công", null);
                        } else {
                            return Response.error("Tạo phiếu mượn thất bại");
                        }
                    }
                    return Response.error("Dữ liệu phiếu mượn không hợp lệ");
                }
                
                case "GET_PHIEU_QUA_HAN": {
                    List<phieuMuon> list = phieuMuonController.layPhieuQuaHan();
                    return Response.success(list);
                }
                
                case "GET_CHI_TIET_PHIEU_MUON": {
                    if (data instanceof Integer) {
                        List<CTPhieuMuon> list = phieuMuonController.layChiTietPhieuMuon((Integer) data);
                        return Response.success(list);
                    }
                    return Response.error("Mã phiếu mượn không hợp lệ");
                }
                
                // ========== PHIEU TRA ==========
                case "GET_ALL_PHIEU_TRA": {
                    List<PhieuTra> list = phieuTraDAO.getAll();
                    return Response.success(list);
                }
                
                case "TRA_SACH": {
                    if (data instanceof PhieuTraData) {
                        PhieuTraData ptData = (PhieuTraData) data;
                        boolean result = phieuTraController.traSach(
                            ptData.getMaPhieuMuon(),
                            ptData.getMaNV(),
                            ptData.getDsChiTiet(),
                            ptData.getTienPhat(),
                            ptData.getGhiChu()
                        );
                        if (result) {
                            return Response.success("Trả sách thành công", null);
                        } else {
                            return Response.error("Trả sách thất bại");
                        }
                    }
                    return Response.error("Dữ liệu phiếu trả không hợp lệ");
                }
                
                case "GET_CHI_TIET_PHIEU_TRA": {
                    if (data instanceof Integer) {
                        List<CTPhieuTra> list = phieuTraController.layChiTietPhieuTra((Integer) data);
                        return Response.success(list);
                    }
                    return Response.error("Mã phiếu trả không hợp lệ");
                }
                
                // ========== THE LOAI ==========
                case "GET_ALL_THE_LOAI": {
                    List<theLoai> list = theLoaiDAO.getAll();
                    return Response.success(list);
                }
                
                case "THEM_THE_LOAI": {
                    if (data instanceof theLoai) {
                        boolean result = theLoaiDAO.them((theLoai) data);
                        if (result) {
                            return Response.success("Thêm thể loại thành công", null);
                        } else {
                            return Response.error("Thêm thể loại thất bại");
                        }
                    }
                    return Response.error("Dữ liệu thể loại không hợp lệ");
                }
                
                case "SUA_THE_LOAI": {
                    if (data instanceof theLoai) {
                        try {
                            boolean result = theLoaiController.sua((theLoai) data);
                            if (result) {
                                return Response.success("Sửa thể loại thành công", null);
                            } else {
                                return Response.error("Sửa thể loại thất bại");
                            }
                        } catch (RuntimeException e) {
                            return Response.error(e.getMessage());
                        }
                    }
                    return Response.error("Dữ liệu thể loại không hợp lệ");
                }
                
                case "XOA_THE_LOAI": {
                    if (data instanceof Integer) {
                        try {
                            boolean result = theLoaiController.xoa((Integer) data);
                            if (result) {
                                return Response.success("Xóa thể loại thành công", null);
                            } else {
                                return Response.error("Xóa thể loại thất bại");
                            }
                        } catch (RuntimeException e) {
                            return Response.error(e.getMessage());
                        }
                    }
                    return Response.error("Mã thể loại không hợp lệ");
                }
                
                // ========== TAC GIA ==========
                case "GET_ALL_TAC_GIA": {
                    List<tacGia> list = tacGiaDAO.getAll();
                    return Response.success(list);
                }
                
                case "THEM_TAC_GIA": {
                    if (data instanceof tacGia) {
                        boolean result = tacGiaDAO.them((tacGia) data);
                        if (result) {
                            return Response.success("Thêm tác giả thành công", null);
                        } else {
                            return Response.error("Thêm tác giả thất bại");
                        }
                    }
                    return Response.error("Dữ liệu tác giả không hợp lệ");
                }
                
                case "SUA_TAC_GIA": {
                    if (data instanceof tacGia) {
                        try {
                            boolean result = tacGiaController.sua((tacGia) data);
                            if (result) {
                                return Response.success("Sửa tác giả thành công", null);
                            } else {
                                return Response.error("Sửa tác giả thất bại");
                            }
                        } catch (RuntimeException e) {
                            return Response.error(e.getMessage());
                        }
                    }
                    return Response.error("Dữ liệu tác giả không hợp lệ");
                }
                
                case "XOA_TAC_GIA": {
                    if (data instanceof Integer) {
                        try {
                            boolean result = tacGiaController.xoa((Integer) data);
                            if (result) {
                                return Response.success("Xóa tác giả thành công", null);
                            } else {
                                return Response.error("Xóa tác giả thất bại");
                            }
                        } catch (RuntimeException e) {
                            return Response.error(e.getMessage());
                        }
                    }
                    return Response.error("Mã tác giả không hợp lệ");
                }
                
                // ========== NHAN VIEN ==========
                case "GET_ALL_NHAN_VIEN": {
                    List<nhanVien> list = nhanVienDAO.getAll();
                    return Response.success(list);
                }
                
                case "THEM_NHAN_VIEN": {
                    if (data instanceof nhanVien) {
                        try {
                            boolean result = authController.them((nhanVien) data);
                            if (result) {
                                return Response.success("Thêm nhân viên thành công", null);
                            } else {
                                return Response.error("Thêm nhân viên thất bại");
                            }
                        } catch (RuntimeException e) {
                            return Response.error(e.getMessage());
                        }
                    }
                    return Response.error("Dữ liệu nhân viên không hợp lệ");
                }
                
                case "SUA_NHAN_VIEN": {
                    if (data instanceof nhanVien) {
                        try {
                            boolean result = authController.sua((nhanVien) data);
                            if (result) {
                                return Response.success("Sửa nhân viên thành công", null);
                            } else {
                                return Response.error("Sửa nhân viên thất bại");
                            }
                        } catch (RuntimeException e) {
                            return Response.error(e.getMessage());
                        }
                    }
                    return Response.error("Dữ liệu nhân viên không hợp lệ");
                }
                
                case "XOA_NHAN_VIEN": {
                    if (data instanceof Integer) {
                        try {
                            boolean result = authController.xoa((Integer) data);
                            if (result) {
                                return Response.success("Xóa nhân viên thành công", null);
                            } else {
                                return Response.error("Xóa nhân viên thất bại");
                            }
                        } catch (RuntimeException e) {
                            return Response.error(e.getMessage());
                        }
                    }
                    return Response.error("Mã nhân viên không hợp lệ");
                }
                
                case "GET_NHAN_VIEN_BY_ID": {
                    if (data instanceof Integer) {
                        nhanVien nv = authController.layTheoMa((Integer) data);
                        if (nv != null) {
                            return Response.success(nv);
                        } else {
                            return Response.error("Không tìm thấy nhân viên");
                        }
                    }
                    return Response.error("Mã nhân viên không hợp lệ");
                }
                
                // ========== THONG KE ==========
                case "THONG_KE_SACH_DUOC_MUON": {
                    // Sử dụng sachDAO để lấy danh sách sách đang mượn
                    List<sach> allSach = sachDAO.getAll();
                    List<sach> dangMuon = allSach.stream()
                        .filter(s -> s.getSoLuongHienTai() < s.getSoLuongTong())
                        .collect(java.util.stream.Collectors.toList());
                    return Response.success(dangMuon);
                }
                
                case "THONG_KE_DOC_GIA": {
                    // Lấy danh sách bạn đọc và thống kê
                    List<banDoc> allBanDoc = banDocDAO.getAll();
                    return Response.success(allBanDoc);
                }
                
                default:
                    return Response.error("Không tìm thấy action: " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("Lỗi xử lý request: " + e.getMessage());
        }
    }
}

