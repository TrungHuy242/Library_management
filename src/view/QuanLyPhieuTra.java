package view;

import client.PhieuTraClientController;
import model.PhieuTra;
import model.CTPhieuTra;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class QuanLyPhieuTra extends JFrame {

    private PhieuTraClientController ptCtrl = new PhieuTraClientController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtTimKiem;
    private MainForm mainForm;

    public QuanLyPhieuTra(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("QUẢN LÝ PHIẾU TRẢ");
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

        JLabel lblTitle = new JLabel("QUẢN LÝ PHIẾU TRẢ", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JButton btnQuayVe = createButton("Quay lại", new Color(100, 100, 100));
        btnQuayVe.addActionListener(e -> quayVeMainForm());
        headerPanel.add(btnQuayVe, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ==================== THANH TÌM KIẾM ====================
        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        top.setBackground(Color.WHITE);

        JPanel pLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pLeft.setBackground(Color.WHITE);
        pLeft.add(new JLabel("Tìm kiếm (mã phiếu trả/mã phiếu mượn/tên bạn đọc):"));
        txtTimKiem = new JTextField(30);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                timKiem();
            }
        });
        pLeft.add(txtTimKiem);

        JPanel pRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pRight.setBackground(Color.WHITE);

        JButton btnXemChiTiet = createButton("Xem Chi Tiết", new Color(52, 152, 219));
        btnXemChiTiet.setToolTipText("Xem chi tiết phiếu trả đã chọn");
        btnXemChiTiet.addActionListener(e -> xemChiTiet());

        JButton btnLamMoi = createButton("Làm mới", new Color(149, 165, 166));
        btnLamMoi.setToolTipText("Tải lại danh sách phiếu trả");
        btnLamMoi.addActionListener(e -> loadDuLieu());

        pRight.add(btnXemChiTiet);
        pRight.add(btnLamMoi);

        top.add(pLeft, BorderLayout.WEST);
        top.add(pRight, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // ==================== BẢNG PHIẾU TRẢ ====================
        String[] cols = { "Mã phiếu trả", "Mã phiếu mượn", "Mã bạn đọc", "Họ tên bạn đọc", 
                "Nhân viên", "Ngày trả", "Tiền phạt", "Ghi chú" };
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

        // Tô màu theo tiền phạt
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    try {
                        float tienPhat = (Float) model.getValueAt(row, 6);
                        if (tienPhat > 0) {
                            c.setBackground(new Color(255, 240, 240)); // Màu đỏ nhạt cho có phạt
                        } else {
                            c.setBackground(new Color(240, 255, 240)); // Màu xanh nhạt cho không phạt
                        }
                    } catch (Exception e) {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
                "Danh sách phiếu trả",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(231, 76, 60)
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        add(scroll, BorderLayout.CENTER);

        // ==================== THÔNG TIN TỔNG QUAN ====================
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBorder(BorderFactory.createTitledBorder("Thống kê nhanh"));
        bottom.setBackground(new Color(240, 248, 255));
        JLabel lblThongKe = new JLabel();
        lblThongKe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bottom.add(lblThongKe);

        // Load thống kê
        List<PhieuTra> ds = ptCtrl.layTatCaPhieuTra();
        long tongPhieu = ds.size();
        float tongTienPhat = (float) ds.stream()
            .mapToDouble(PhieuTra::getTienPhat)
            .sum();
        DecimalFormat df = new DecimalFormat("#,###");
        lblThongKe.setText(String.format(
                "Tổng: %d phiếu trả | Tổng tiền phạt: %s đ",
                tongPhieu, df.format(tongTienPhat)));

        add(bottom, BorderLayout.SOUTH);
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
        List<PhieuTra> ds = ptCtrl.layTatCaPhieuTra();

        DecimalFormat df = new DecimalFormat("#,###");

        for (PhieuTra pt : ds) {
            model.addRow(new Object[] {
                    pt.getMaPhieuTra(),
                    pt.getMaPhieuMuon(),
                    pt.getMaBanDoc(),
                    pt.getTenBanDoc() != null ? pt.getTenBanDoc() : "",
                    pt.getTenNhanVien() != null ? pt.getTenNhanVien() : "NV#" + pt.getMaNV(),
                    pt.getNgayTra() != null ? pt.getNgayTra().toString() : "",
                    df.format(pt.getTienPhat()) + " đ",
                    pt.getGhiChu() != null ? pt.getGhiChu() : ""
            });
        }

        // Reset filter
        txtTimKiem.setText("");
        
        // Cập nhật thống kê
        updateThongKe();
    }

    private void updateThongKe() {
        List<PhieuTra> ds = ptCtrl.layTatCaPhieuTra();
        long tongPhieu = ds.size();
        float tongTienPhat = (float) ds.stream()
            .mapToDouble(PhieuTra::getTienPhat)
            .sum();
        DecimalFormat df = new DecimalFormat("#,###");
        
        JPanel bottom = (JPanel) ((BorderLayout) getContentPane().getLayout()).getLayoutComponent(BorderLayout.SOUTH);
        if (bottom != null) {
            JLabel lblThongKe = (JLabel) bottom.getComponent(0);
            if (lblThongKe != null) {
                lblThongKe.setText(String.format(
                        "Tổng: %d phiếu trả | Tổng tiền phạt: %s đ",
                        tongPhieu, df.format(tongTienPhat)));
            }
        }
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        model.setRowCount(0);
        List<PhieuTra> ds = ptCtrl.layTatCaPhieuTra();

        DecimalFormat df = new DecimalFormat("#,###");

        for (PhieuTra pt : ds) {
            // Tìm kiếm
            if (!keyword.isEmpty()) {
                boolean match = false;
                if (String.valueOf(pt.getMaPhieuTra()).contains(keyword))
                    match = true;
                if (String.valueOf(pt.getMaPhieuMuon()).contains(keyword))
                    match = true;
                if (pt.getTenBanDoc() != null && pt.getTenBanDoc().toLowerCase().contains(keyword))
                    match = true;
                if (!match)
                    continue;
            }

            model.addRow(new Object[] {
                    pt.getMaPhieuTra(),
                    pt.getMaPhieuMuon(),
                    pt.getMaBanDoc(),
                    pt.getTenBanDoc() != null ? pt.getTenBanDoc() : "",
                    pt.getTenNhanVien() != null ? pt.getTenNhanVien() : "NV#" + pt.getMaNV(),
                    pt.getNgayTra() != null ? pt.getNgayTra().toString() : "",
                    df.format(pt.getTienPhat()) + " đ",
                    pt.getGhiChu() != null ? pt.getGhiChu() : ""
            });
        }
    }

    private void xemChiTiet() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn một phiếu trả!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maPhieuTra = (Integer) model.getValueAt(row, 0);
        PhieuTra pt = ptCtrl.layTheoMa(maPhieuTra);

        if (pt == null) {
            JOptionPane.showMessageDialog(this, 
                "Không tìm thấy phiếu trả!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy chi tiết sách
        List<CTPhieuTra> dsChiTiet = ptCtrl.layChiTietPhieuTra(maPhieuTra);

        // Tạo dialog hiển thị chi tiết
        JDialog dialog = new JDialog(this, "Chi tiết phiếu trả #" + maPhieuTra, true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(245, 247, 250));

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        main.setBackground(Color.WHITE);

        // Thông tin phiếu
        JPanel pInfo = new JPanel(new GridLayout(7, 2, 10, 10));
        pInfo.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu trả"));
        pInfo.setBackground(Color.WHITE);
        
        DecimalFormat df = new DecimalFormat("#,###");
        
        pInfo.add(new JLabel("Mã phiếu trả:"));
        pInfo.add(new JLabel(String.valueOf(pt.getMaPhieuTra())));
        pInfo.add(new JLabel("Mã phiếu mượn:"));
        pInfo.add(new JLabel(String.valueOf(pt.getMaPhieuMuon())));
        pInfo.add(new JLabel("Bạn đọc:"));
        pInfo.add(new JLabel(pt.getTenBanDoc() != null ? pt.getTenBanDoc() : ""));
        pInfo.add(new JLabel("Nhân viên:"));
        pInfo.add(new JLabel(pt.getTenNhanVien() != null ? pt.getTenNhanVien() : "NV#" + pt.getMaNV()));
        pInfo.add(new JLabel("Ngày trả:"));
        pInfo.add(new JLabel(pt.getNgayTra() != null ? pt.getNgayTra().toString() : ""));
        pInfo.add(new JLabel("Tiền phạt:"));
        JLabel lblTienPhat = new JLabel(df.format(pt.getTienPhat()) + " đ");
        if (pt.getTienPhat() > 0) {
            lblTienPhat.setForeground(Color.RED);
            lblTienPhat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
        pInfo.add(lblTienPhat);
        pInfo.add(new JLabel("Ghi chú:"));
        pInfo.add(new JLabel(pt.getGhiChu() != null ? pt.getGhiChu() : ""));

        main.add(pInfo, BorderLayout.NORTH);

        // Bảng chi tiết sách
        String[] cols = { "STT", "Mã sách", "Tên sách", "Số lượng", "Tình trạng" };
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
        for (CTPhieuTra ct : dsChiTiet) {
            modelChiTiet.addRow(new Object[] {
                    stt++,
                    ct.getMaSach(),
                    ct.getTenSach() != null ? ct.getTenSach() : "",
                    ct.getSoLuong(),
                    ct.getTinhTrangSach() != null ? ct.getTinhTrangSach() : ""
            });
        }

        JScrollPane scrollChiTiet = new JScrollPane(tableChiTiet);
        scrollChiTiet.setBorder(BorderFactory.createTitledBorder(
            "Danh sách sách trả (" + dsChiTiet.size() + " cuốn)"));
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyPhieuTra(null).setVisible(true));
    }
}

