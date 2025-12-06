package view;

import client.PhieuMuonClientController;
import model.phieuMuon;
import model.CTPhieuMuon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TheoDoiQuaHan extends JFrame {

    private PhieuMuonClientController pmCtrl = new PhieuMuonClientController();
    private DefaultTableModel model;
    private JTable table;
    private JLabel lblTongSo, lblTongNgayQuaHan;
    private MainForm mainForm;

    public TheoDoiQuaHan(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("THEO DÕI & CẢNH BÁO SÁCH MƯỢN QUÁ HẠN");
        setSize(1400, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 247, 250));

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                quayVeMainForm();
            }
        });

        initGUI();
        loadDuLieu();
    }

    private void quayVeMainForm() {
        if (mainForm != null) {
            mainForm.setVisible(true);
            mainForm.capNhatCanhBao();
        }
        this.dispose();
    }

    private void initGUI() {
        // ==================== HEADER ====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(new Color(231, 76, 60));

        JLabel lblTitle = new JLabel("THEO DÕI & CẢNH BÁO SÁCH MƯỢN QUÁ HẠN", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JButton btnQuayVe = createButton("Quay lại", new Color(100, 100, 100));
        btnQuayVe.addActionListener(e -> quayVeMainForm());
        headerPanel.add(btnQuayVe, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ==================== CẢNH BÁO PANEL ====================
        JPanel warningPanel = new JPanel(new BorderLayout(10, 10));
        warningPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        warningPanel.setBackground(new Color(255, 240, 240));

        JLabel lblWarning = new JLabel(
            "<html><div style='text-align: center;'>" +
            "<h2 style='color: #E74C3C; margin: 10px;'>⚠️ CẢNH BÁO: SÁCH MƯỢN QUÁ HẠN</h2>" +
            "<p style='color: #C0392B; font-size: 14px; margin: 5px;'>" +
            "Các phiếu mượn dưới đây đã quá hạn trả. Vui lòng liên hệ độc giả để thu hồi sách!</p>" +
            "</div></html>",
            SwingConstants.CENTER);
        lblWarning.setFont(new Font("Segoe UI", Font.BOLD, 14));
        warningPanel.add(lblWarning, BorderLayout.CENTER);
        add(warningPanel, BorderLayout.NORTH);

        // ==================== THANH TÌM KIẾM ====================
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchPanel.setBackground(Color.WHITE);

        JPanel pLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pLeft.setBackground(Color.WHITE);
        pLeft.add(new JLabel("Tìm kiếm (mã phiếu/tên độc giả):"));
        JTextField txtTimKiem = new JTextField(30);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                timKiem(txtTimKiem.getText().trim());
            }
        });
        pLeft.add(txtTimKiem);

        JPanel pRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pRight.setBackground(Color.WHITE);

        JButton btnXemChiTiet = createButton("Xem Chi Tiết", new Color(52, 152, 219));
        btnXemChiTiet.setToolTipText("Xem chi tiết phiếu mượn quá hạn");
        btnXemChiTiet.addActionListener(e -> xemChiTiet());

        JButton btnTraSach = createButton("Trả Sách", new Color(46, 204, 113));
        btnTraSach.setToolTipText("Mở form trả sách cho phiếu đã chọn");
        btnTraSach.addActionListener(e -> moTraSach());

        JButton btnLamMoi = createButton("Làm mới", new Color(149, 165, 166));
        btnLamMoi.setToolTipText("Tải lại danh sách");
        btnLamMoi.addActionListener(e -> loadDuLieu());

        pRight.add(btnXemChiTiet);
        pRight.add(btnTraSach);
        pRight.add(btnLamMoi);

        searchPanel.add(pLeft, BorderLayout.WEST);
        searchPanel.add(pRight, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        // ==================== BẢNG PHIẾU QUÁ HẠN ====================
        String[] cols = { "Mã phiếu", "Mã độc giả", "Họ tên độc giả", "Ngày mượn", 
                "Hạn trả", "Quá hạn (ngày)", "Tiền phạt dự kiến" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setBackground(new Color(231, 76, 60));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Tô màu đỏ cho các dòng quá hạn
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(new Color(255, 240, 240)); // Màu đỏ nhạt
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
                "Danh sách phiếu mượn quá hạn",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(231, 76, 60)
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        add(scroll, BorderLayout.CENTER);

        // ==================== THÔNG TIN TỔNG QUAN ====================
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bottomPanel.setBackground(new Color(255, 240, 240));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 5));
        infoPanel.setBackground(new Color(255, 240, 240));

        lblTongSo = new JLabel("Tổng số: 0 phiếu");
        lblTongSo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTongSo.setForeground(new Color(231, 76, 60));
        infoPanel.add(lblTongSo);

        lblTongNgayQuaHan = new JLabel("Tổng số ngày quá hạn: 0 ngày");
        lblTongNgayQuaHan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTongNgayQuaHan.setForeground(new Color(231, 76, 60));
        infoPanel.add(lblTongNgayQuaHan);

        JLabel lblTienPhat = new JLabel("Tiền phạt dự kiến: 0 đ");
        lblTienPhat.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTienPhat.setForeground(new Color(192, 57, 43));
        infoPanel.add(lblTienPhat);

        bottomPanel.add(infoPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        return btn;
    }

    private void loadDuLieu() {
        model.setRowCount(0);
        List<phieuMuon> ds = pmCtrl.layPhieuQuaHan();

        long tongNgayQuaHan = 0;
        long tongTienPhat = 0;

        for (phieuMuon pm : ds) {
            // Tính số ngày quá hạn
            long quaHanNgay = 0;
            if (pm.getNgayHenTra() != null) {
                quaHanNgay = ChronoUnit.DAYS.between(
                        pm.getNgayHenTra().toLocalDate(),
                        LocalDate.now());
                if (quaHanNgay < 0)
                    quaHanNgay = 0;
            }

            tongNgayQuaHan += quaHanNgay;
            long tienPhat = quaHanNgay * 5000; // 5,000đ/ngày
            tongTienPhat += tienPhat;

            model.addRow(new Object[] {
                    pm.getMaPhieuMuon(),
                    pm.getMaBanDoc(),
                    pm.getHoTenDocGia() != null ? pm.getHoTenDocGia() : "",
                    pm.getNgayMuon() != null ? pm.getNgayMuon().toString() : "",
                    pm.getNgayHenTra() != null ? pm.getNgayHenTra().toString() : "",
                    quaHanNgay + " ngày",
                    String.format("%,d đ", tienPhat)
            });
        }

        // Cập nhật thông tin tổng quan
        lblTongSo.setText("Tổng số: " + ds.size() + " phiếu");
        lblTongNgayQuaHan.setText("Tổng số ngày quá hạn: " + tongNgayQuaHan + " ngày");
        
        JPanel bottomPanel = (JPanel) ((BorderLayout) getContentPane().getLayout()).getLayoutComponent(BorderLayout.SOUTH);
        if (bottomPanel != null) {
            JPanel infoPanel = (JPanel) bottomPanel.getComponent(0);
            if (infoPanel != null && infoPanel.getComponentCount() > 2) {
                JLabel lblTienPhat = (JLabel) infoPanel.getComponent(2);
                if (lblTienPhat != null) {
                    lblTienPhat.setText("Tiền phạt dự kiến: " + String.format("%,d đ", tongTienPhat));
                }
            }
        }
    }

    private void timKiem(String keyword) {
        model.setRowCount(0);
        List<phieuMuon> ds = pmCtrl.layPhieuQuaHan();

        long tongNgayQuaHan = 0;
        long tongTienPhat = 0;

        for (phieuMuon pm : ds) {
            // Tìm kiếm
            if (!keyword.isEmpty()) {
                boolean match = false;
                if (String.valueOf(pm.getMaPhieuMuon()).contains(keyword))
                    match = true;
                if (pm.getHoTenDocGia() != null && pm.getHoTenDocGia().toLowerCase().contains(keyword.toLowerCase()))
                    match = true;
                if (!match)
                    continue;
            }

            // Tính số ngày quá hạn
            long quaHanNgay = 0;
            if (pm.getNgayHenTra() != null) {
                quaHanNgay = ChronoUnit.DAYS.between(
                        pm.getNgayHenTra().toLocalDate(),
                        LocalDate.now());
                if (quaHanNgay < 0)
                    quaHanNgay = 0;
            }

            tongNgayQuaHan += quaHanNgay;
            long tienPhat = quaHanNgay * 5000;
            tongTienPhat += tienPhat;

            model.addRow(new Object[] {
                    pm.getMaPhieuMuon(),
                    pm.getMaBanDoc(),
                    pm.getHoTenDocGia() != null ? pm.getHoTenDocGia() : "",
                    pm.getNgayMuon() != null ? pm.getNgayMuon().toString() : "",
                    pm.getNgayHenTra() != null ? pm.getNgayHenTra().toString() : "",
                    quaHanNgay + " ngày",
                    String.format("%,d đ", tienPhat)
            });
        }

        // Cập nhật thông tin tổng quan
        lblTongSo.setText("Tổng số: " + model.getRowCount() + " phiếu");
        lblTongNgayQuaHan.setText("Tổng số ngày quá hạn: " + tongNgayQuaHan + " ngày");
        
        JPanel bottomPanel = (JPanel) ((BorderLayout) getContentPane().getLayout()).getLayoutComponent(BorderLayout.SOUTH);
        if (bottomPanel != null) {
            JPanel infoPanel = (JPanel) bottomPanel.getComponent(0);
            if (infoPanel != null && infoPanel.getComponentCount() > 2) {
                JLabel lblTienPhat = (JLabel) infoPanel.getComponent(2);
                if (lblTienPhat != null) {
                    lblTienPhat.setText("Tiền phạt dự kiến: " + String.format("%,d đ", tongTienPhat));
                }
            }
        }
    }

    private void xemChiTiet() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn một phiếu mượn!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maPhieu = (Integer) model.getValueAt(row, 0);
        phieuMuon pm = pmCtrl.layTheoMa(maPhieu);

        if (pm == null) {
            JOptionPane.showMessageDialog(this,
                "Không tìm thấy phiếu mượn!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy chi tiết sách
        List<CTPhieuMuon> dsChiTiet = pmCtrl.layChiTietPhieuMuon(maPhieu);

        // Tính số ngày quá hạn
        long quaHanNgay = 0;
        if (pm.getNgayHenTra() != null) {
            quaHanNgay = ChronoUnit.DAYS.between(
                    pm.getNgayHenTra().toLocalDate(),
                    LocalDate.now());
            if (quaHanNgay < 0)
                quaHanNgay = 0;
        }
        long tienPhat = quaHanNgay * 5000;

        // Tạo dialog hiển thị chi tiết
        JDialog dialog = new JDialog(this, "Chi tiết phiếu mượn quá hạn #" + maPhieu, true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(245, 247, 250));

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        main.setBackground(Color.WHITE);

        // Thông tin phiếu
        JPanel pInfo = new JPanel(new GridLayout(7, 2, 10, 10));
        pInfo.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu mượn"));
        pInfo.setBackground(Color.WHITE);

        pInfo.add(new JLabel("Mã phiếu:"));
        pInfo.add(new JLabel(String.valueOf(pm.getMaPhieuMuon())));
        pInfo.add(new JLabel("Độc giả:"));
        pInfo.add(new JLabel(pm.getHoTenDocGia() != null ? pm.getHoTenDocGia() : ""));
        pInfo.add(new JLabel("Ngày mượn:"));
        pInfo.add(new JLabel(pm.getNgayMuon() != null ? pm.getNgayMuon().toString() : ""));
        pInfo.add(new JLabel("Hạn trả:"));
        pInfo.add(new JLabel(pm.getNgayHenTra() != null ? pm.getNgayHenTra().toString() : ""));
        pInfo.add(new JLabel("Quá hạn:"));
        JLabel lblQuaHan = new JLabel(quaHanNgay + " ngày");
        lblQuaHan.setForeground(Color.RED);
        lblQuaHan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pInfo.add(lblQuaHan);
        pInfo.add(new JLabel("Tiền phạt dự kiến:"));
        JLabel lblTienPhat = new JLabel(String.format("%,d đ", tienPhat));
        lblTienPhat.setForeground(Color.RED);
        lblTienPhat.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pInfo.add(lblTienPhat);
        pInfo.add(new JLabel("Trạng thái:"));
        JLabel lblTrangThai = new JLabel(pm.getTrangThai());
        lblTrangThai.setForeground(Color.RED);
        lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pInfo.add(lblTrangThai);

        main.add(pInfo, BorderLayout.NORTH);

        // Bảng chi tiết sách
        String[] cols = { "STT", "Mã sách", "Tên sách", "Số lượng" };
        DefaultTableModel modelChiTiet = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tableChiTiet = new JTable(modelChiTiet);
        tableChiTiet.setRowHeight(30);
        tableChiTiet.getTableHeader().setBackground(new Color(231, 76, 60));
        tableChiTiet.getTableHeader().setForeground(Color.WHITE);
        tableChiTiet.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        int stt = 1;
        for (CTPhieuMuon ct : dsChiTiet) {
            modelChiTiet.addRow(new Object[] {
                    stt++,
                    ct.getMaSach(),
                    ct.getTenSach() != null ? ct.getTenSach() : "",
                    ct.getSoLuong()
            });
        }

        JScrollPane scrollChiTiet = new JScrollPane(tableChiTiet);
        scrollChiTiet.setBorder(BorderFactory.createTitledBorder(
            "Danh sách sách mượn (" + dsChiTiet.size() + " cuốn)"));
        main.add(scrollChiTiet, BorderLayout.CENTER);

        JButton btnDong = createButton("Đóng", new Color(149, 165, 166));
        btnDong.addActionListener(e -> dialog.dispose());
        JPanel pNut = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pNut.setBackground(Color.WHITE);
        pNut.add(btnDong);
        main.add(pNut, BorderLayout.SOUTH);

        dialog.add(main);
        dialog.setVisible(true);
    }

    private void moTraSach() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn một phiếu mượn!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maPhieu = (Integer) model.getValueAt(row, 0);

        // Mở màn hình trả sách
        TraSach traSach = new TraSach(mainForm);
        traSach.setMaPhieuMuon(maPhieu);
        traSach.setVisible(true);
        this.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TheoDoiQuaHan(null).setVisible(true));
    }
}

