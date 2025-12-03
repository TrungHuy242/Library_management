package view;

import controller.PhieuMuonController;
import controller.PhieuTraController;
import controller.AuthController;
import model.phieuMuon;
import model.CTPhieuMuon;
import model.CTPhieuTra;
import model.nhanVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TraSach extends JFrame {

    private JTextField txtMaPhieu, txtHoTen, txtNgayMuon, txtHanTra, txtQuaHan, txtTienPhat;
    private JTable table;
    private DefaultTableModel model;
    private PhieuMuonController pmCtrl = new PhieuMuonController();
    private PhieuTraController ptCtrl = new PhieuTraController();
    private AuthController authCtrl = new AuthController();
    private phieuMuon currentPhieuMuon;
    private MainForm mainForm;

    public TraSach() {
        this(null);
    }

    public TraSach(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("TRẢ SÁCH & TÍNH PHẠT");
        setSize(1300, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 247, 250));

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                quayVeMainForm();
            }
        });

        initGUI();
    }

    private void quayVeMainForm() {
        if (mainForm != null) {
            mainForm.setVisible(true);
            mainForm.capNhatCanhBao();
        }
        this.dispose();
    }

    private void initGUI() {
        setLayout(new BorderLayout(10, 10));

        // ==================== HEADER ====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(new Color(231, 76, 60));

        JLabel lblTitle = new JLabel("TRẢ SÁCH & TÍNH PHẠT", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JButton btnQuayVe = new JButton("Quay lại");
        btnQuayVe.setBackground(new Color(100, 100, 100));
        btnQuayVe.setForeground(Color.WHITE);
        btnQuayVe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnQuayVe.setFocusPainted(false);
        btnQuayVe.addActionListener(e -> quayVeMainForm());
        headerPanel.add(btnQuayVe, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ==================== BƯỚC 1: NHẬP MÃ PHIẾU ====================
        JPanel step1Panel = new JPanel(new BorderLayout());
        step1Panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Nhập mã phiếu mượn"),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        step1Panel.setBackground(Color.WHITE);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        inputPanel.setBackground(Color.WHITE);

        JLabel lblMaPhieu = new JLabel("Mã phiếu mượn (Enter để tìm):");
        lblMaPhieu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        inputPanel.add(lblMaPhieu);

        txtMaPhieu = new JTextField(15);
        txtMaPhieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMaPhieu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        txtMaPhieu.addActionListener(e -> timPhieuMuon());
        inputPanel.add(txtMaPhieu);

        JButton btnTim = createButton("Tìm kiếm", new Color(52, 152, 219));
        btnTim.addActionListener(e -> timPhieuMuon());
        inputPanel.add(btnTim);

        step1Panel.add(inputPanel, BorderLayout.CENTER);
        add(step1Panel, BorderLayout.NORTH);

        // ==================== BƯỚC 2: THÔNG TIN PHIẾU ====================
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(245, 247, 250));

        // Panel thông tin phiếu mượn
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Thông tin phiếu mượn"),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        infoPanel.setBackground(Color.WHITE);

        // Hàng 1
        JLabel lblHoTen = new JLabel("Bạn đọc:");
        lblHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoPanel.add(lblHoTen);
        txtHoTen = new JTextField();
        txtHoTen.setEditable(false);
        txtHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtHoTen.setBackground(new Color(240, 240, 240));
        txtHoTen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        infoPanel.add(txtHoTen);

        // Hàng 2
        JLabel lblNgayMuon = new JLabel("Ngày mượn:");
        lblNgayMuon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoPanel.add(lblNgayMuon);
        txtNgayMuon = new JTextField();
        txtNgayMuon.setEditable(false);
        txtNgayMuon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNgayMuon.setBackground(new Color(240, 240, 240));
        txtNgayMuon.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        infoPanel.add(txtNgayMuon);

        // Hàng 3
        JLabel lblHanTra = new JLabel("Hạn trả:");
        lblHanTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoPanel.add(lblHanTra);
        txtHanTra = new JTextField();
        txtHanTra.setEditable(false);
        txtHanTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtHanTra.setBackground(new Color(240, 240, 240));
        txtHanTra.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        infoPanel.add(txtHanTra);

        // Panel phạt
        JPanel phatPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        phatPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Thông tin phạt"),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        phatPanel.setBackground(new Color(255, 245, 245));

        JLabel lblQuaHan = new JLabel("Quá hạn:");
        lblQuaHan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        phatPanel.add(lblQuaHan);
        txtQuaHan = new JTextField("0 ngày");
        txtQuaHan.setEditable(false);
        txtQuaHan.setForeground(new Color(231, 76, 60));
        txtQuaHan.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtQuaHan.setBackground(new Color(255, 245, 245));
        txtQuaHan.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        phatPanel.add(txtQuaHan);

        JLabel lblTienPhat = new JLabel("Tiền phạt (5,000đ/ngày):");
        lblTienPhat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        phatPanel.add(lblTienPhat);
        txtTienPhat = new JTextField("0đ");
        txtTienPhat.setEditable(false);
        txtTienPhat.setForeground(new Color(231, 76, 60));
        txtTienPhat.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtTienPhat.setBackground(new Color(255, 245, 245));
        txtTienPhat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        phatPanel.add(txtTienPhat);

        // Layout thông tin
        JPanel leftInfo = new JPanel(new BorderLayout(10, 10));
        leftInfo.setBackground(new Color(245, 247, 250));
        leftInfo.add(infoPanel, BorderLayout.NORTH);
        leftInfo.add(phatPanel, BorderLayout.CENTER);

        centerPanel.add(leftInfo, BorderLayout.WEST);

        // ==================== BƯỚC 3: TÌNH TRẠNG SÁCH ====================
        JPanel step3Panel = new JPanel(new BorderLayout());
        step3Panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Kiểm tra và xác nhận tình trạng sách"),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        step3Panel.setBackground(Color.WHITE);

        model = new DefaultTableModel(new String[] { "STT", "Mã sách", "Tên sách", "Tình trạng", "Ghi chú" }, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col >= 3;
            }
        };
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setBackground(new Color(243, 156, 18));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setSelectionBackground(new Color(243, 156, 18));
        table.setSelectionForeground(Color.WHITE);

        // ComboBox cho tình trạng
        JComboBox<String> comboTrangThai = new JComboBox<>(new String[] { "Tốt", "Hỏng nhẹ", "Hỏng nặng", "Mất" });
        comboTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboTrangThai));

        JScrollPane scroll = new JScrollPane(table);
        step3Panel.add(scroll, BorderLayout.CENTER);

        // Hướng dẫn
        JLabel lblHuongDan = new JLabel(
                "<html><b>Lưu ý:</b> Chọn tình trạng sách cho từng cuốn. Nếu sách hỏng hoặc mất, vui lòng ghi chú rõ ràng.</html>");
        lblHuongDan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHuongDan.setForeground(new Color(127, 140, 141));
        lblHuongDan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        step3Panel.add(lblHuongDan, BorderLayout.SOUTH);

        centerPanel.add(step3Panel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // ==================== NÚT HÀNH ĐỘNG ====================
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bottomPanel.setBackground(new Color(245, 247, 250));

        // Hướng dẫn
        JLabel lblHuongDanChung = new JLabel(
                "<html><b>Hướng dẫn:</b> Bước 1 → Nhập mã phiếu và nhấn Enter → Bước 2 → Kiểm tra thông tin và tiền phạt → Bước 3 → Chọn tình trạng sách → Nhấn 'HOÀN TẤT TRẢ SÁCH'</html>");
        lblHuongDanChung.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHuongDanChung.setForeground(new Color(127, 140, 141));
        bottomPanel.add(lblHuongDanChung, BorderLayout.NORTH);

        // Nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setBackground(new Color(245, 247, 250));

        JButton btnTra = createLargeButton("HOÀN TẤT TRẢ SÁCH", new Color(46, 204, 113), 250, 50);
        btnTra.setToolTipText("Hoàn tất quá trình trả sách");
        btnTra.addActionListener(e -> hoanTatTraSach());

        buttonPanel.add(btnTra);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        return btn;
    }

    private JButton createLargeButton(String text, Color bgColor, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        return btn;
    }

    private void timPhieuMuon() {
        try {
            if (txtMaPhieu.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập mã phiếu mượn!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int maPhieu = Integer.parseInt(txtMaPhieu.getText().trim());
            phieuMuon pm = pmCtrl.layTheoMa(maPhieu);

            if (pm == null || "Đã trả".equals(pm.getTrangThai())) {
                JOptionPane.showMessageDialog(this,
                        "Phiếu không tồn tại hoặc đã được trả rồi!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                clearForm();
                return;
            }

            // Lưu phiếu mượn hiện tại
            currentPhieuMuon = pm;

            // Hiển thị thông tin
            txtHoTen.setText(pm.getHoTenDocGia());
            txtHoTen.setBackground(new Color(220, 255, 220));
            txtNgayMuon.setText(pm.getNgayMuon().toString());
            txtNgayMuon.setBackground(new Color(220, 255, 220));
            txtHanTra.setText(pm.getNgayHenTra().toString());
            txtHanTra.setBackground(new Color(220, 255, 220));

            // Tính phạt
            long quaHan = ChronoUnit.DAYS.between(pm.getNgayHenTra().toLocalDate(), LocalDate.now());
            if (quaHan < 0)
                quaHan = 0;
            txtQuaHan.setText(quaHan + " ngày");
            txtTienPhat.setText(String.format("%,dđ", quaHan * 5000));

            // Nếu quá hạn, làm nổi bật
            if (quaHan > 0) {
                txtQuaHan.setBackground(new Color(255, 220, 220));
                txtTienPhat.setBackground(new Color(255, 220, 220));
            } else {
                txtQuaHan.setBackground(new Color(255, 245, 245));
                txtTienPhat.setBackground(new Color(255, 245, 245));
            }

            loadChiTiet(maPhieu);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Mã phiếu không hợp lệ! Vui lòng nhập số.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void loadChiTiet(int maPhieu) {
        model.setRowCount(0);
        List<CTPhieuMuon> ds = pmCtrl.layChiTietPhieuMuon(maPhieu);
        int stt = 1;
        for (CTPhieuMuon ct : ds) {
            model.addRow(new Object[] { stt++, ct.getMaSach(), ct.getTenSach(), "Tốt", "" });
        }
    }

    private void hoanTatTraSach() {
        try {
            // Kiểm tra đã nhập mã phiếu
            if (txtMaPhieu.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập mã phiếu mượn trước!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                txtMaPhieu.requestFocus();
                return;
            }

            // Kiểm tra đã load thông tin chưa
            if (txtHoTen.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng tìm phiếu mượn trước khi hoàn tất!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra có sách nào chưa
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "Không có sách nào trong phiếu mượn!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int maPhieu = Integer.parseInt(txtMaPhieu.getText().trim());

            // Parse số ngày quá hạn
            String quaHanStr = txtQuaHan.getText().trim().replace(" ngày", "").trim();
            long quaHan = 0;
            if (!quaHanStr.isEmpty()) {
                try {
                    quaHan = Long.parseLong(quaHanStr);
                } catch (NumberFormatException e) {
                    quaHan = 0;
                }
            }

            // Xác nhận
            String message = "Xác nhận trả sách?\n\n";
            message += "Mã phiếu: " + maPhieu + "\n";
            message += "Bạn đọc: " + txtHoTen.getText() + "\n";
            message += "Số sách: " + model.getRowCount() + " cuốn\n";
            if (quaHan > 0) {
                message += "Quá hạn: " + quaHan + " ngày\n";
                message += "Tiền phạt: " + txtTienPhat.getText() + "\n";
            }

            int confirm = JOptionPane.showConfirmDialog(this, message,
                    "Xác nhận trả sách", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Lấy thông tin phiếu mượn
            if (currentPhieuMuon == null) {
                currentPhieuMuon = pmCtrl.layTheoMa(maPhieu);
            }

            // Tạo danh sách chi tiết phiếu trả
            List<CTPhieuTra> dsChiTiet = new ArrayList<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                int maSach = (Integer) model.getValueAt(i, 1);
                String tinhTrang = (String) model.getValueAt(i, 3);

                // Map giá trị tình trạng từ form sang database enum
                // Database enum: 'Bình thường','Hỏng','Mất'
                // Form có: "Tốt", "Hỏng nhẹ", "Hỏng nặng", "Mất"
                String tinhTrangDB = mapTinhTrangToDB(tinhTrang);

                // Lấy số lượng từ chi tiết phiếu mượn
                List<CTPhieuMuon> dsCTMuon = pmCtrl.layChiTietPhieuMuon(maPhieu);
                int soLuong = 1;
                for (CTPhieuMuon ct : dsCTMuon) {
                    if (ct.getMaSach() == maSach) {
                        soLuong = ct.getSoLuong();
                        break;
                    }
                }

                CTPhieuTra ctTra = new CTPhieuTra();
                ctTra.setMaSach(maSach);
                ctTra.setSoLuong(soLuong);
                ctTra.setTinhTrangSach(tinhTrangDB);
                dsChiTiet.add(ctTra);
            }

            // Tính tiền phạt
            float tienPhat = (float) (quaHan * 5000);

            // Tạo ghi chú
            String ghiChu = "";
            if (quaHan > 0) {
                ghiChu = "Quá hạn " + quaHan + " ngày";
            }

            // Lấy mã nhân viên từ phiếu mượn, nếu không hợp lệ thì dùng mã mặc định
            int maNV = currentPhieuMuon.getMaNV();

            // Kiểm tra mã nhân viên có hợp lệ không
            // Lấy danh sách nhân viên để kiểm tra
            List<nhanVien> dsNV = authCtrl.getAllNhanVien();
            boolean maNVHopLe = false;
            if (maNV > 0 && dsNV != null) {
                for (nhanVien nv : dsNV) {
                    if (nv.getMaNV() == maNV) {
                        maNVHopLe = true;
                        break;
                    }
                }
            }

            // Nếu mã nhân viên không hợp lệ, sử dụng mã nhân viên đầu tiên trong danh sách
            if (!maNVHopLe) {
                if (dsNV != null && !dsNV.isEmpty()) {
                    maNV = dsNV.get(0).getMaNV();
                } else {
                    maNV = 1; // Mã nhân viên mặc định nếu không có nhân viên nào
                }
            }

            // Tạo phiếu trả
            boolean ok = ptCtrl.traSach(maPhieu, maNV, dsChiTiet, tienPhat, ghiChu);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                        "TRẢ SÁCH THÀNH CÔNG!\n\n" +
                                "Tiền phạt: " + txtTienPhat.getText() + "\n" +
                                "Phiếu trả đã được tạo!\n" +
                                "Kho đã được cập nhật!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                currentPhieuMuon = null;
                if (mainForm != null) {
                    mainForm.capNhatCanhBao();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Trả sách thất bại!\nVui lòng thử lại.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Mã phiếu không hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void clearForm() {
        txtMaPhieu.setText("");
        txtHoTen.setText("");
        txtHoTen.setBackground(new Color(240, 240, 240));
        txtNgayMuon.setText("");
        txtNgayMuon.setBackground(new Color(240, 240, 240));
        txtHanTra.setText("");
        txtHanTra.setBackground(new Color(240, 240, 240));
        txtQuaHan.setText("0 ngày");
        txtQuaHan.setBackground(new Color(255, 245, 245));
        txtTienPhat.setText("0đ");
        txtTienPhat.setBackground(new Color(255, 245, 245));
        model.setRowCount(0);
        currentPhieuMuon = null;
        txtMaPhieu.requestFocus();
    }

    public void setMaPhieuMuon(int maPhieu) {
        txtMaPhieu.setText(String.valueOf(maPhieu));
        timPhieuMuon();
    }

    // Map giá trị tình trạng từ form sang database enum
    private String mapTinhTrangToDB(String tinhTrangForm) {
        if (tinhTrangForm == null) {
            return "Bình thường";
        }
        switch (tinhTrangForm) {
            case "Tốt":
                return "Bình thường";
            case "Hỏng nhẹ":
            case "Hỏng nặng":
                return "Hỏng";
            case "Mất":
                return "Mất";
            default:
                return "Bình thường";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TraSach(null).setVisible(true));
    }
}
