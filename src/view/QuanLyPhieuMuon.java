package view;

import controller.PhieuMuonController;
import model.phieuMuon;
import model.CTPhieuMuon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class QuanLyPhieuMuon extends JFrame {

    private PhieuMuonController pmCtrl = new PhieuMuonController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtTimKiem;
    private JComboBox<String> cbTrangThai;
    private MainForm mainForm; // Thêm field

    public QuanLyPhieuMuon(MainForm mainForm) { // Sửa constructor
        this.mainForm = mainForm;
        setTitle("QUẢN LÝ PHIẾU MƯỢN");
        setSize(1400, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Sửa để xử lý đóng
        setLayout(new BorderLayout(10, 10));

        // Thêm WindowListener
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                quayVeMainForm();
            }
        });

        initGUI();
        loadDuLieu();
    }

    // Thêm method quay về MainForm
    private void quayVeMainForm() {
        if (mainForm != null) {
            mainForm.setVisible(true);
            mainForm.capNhatCanhBao();
        }
        this.dispose();
    }

    private void initGUI() {
        // ==================== THANH TÌM KIẾM + LỌC ====================
        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        top.setBackground(new Color(240, 248, 255));

        JPanel pLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pLeft.add(new JLabel("Tìm kiếm (mã phiếu/tên bạn đọc):"));
        txtTimKiem = new JTextField(30);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                timKiem();
            }
        });
        pLeft.add(txtTimKiem);

        JPanel pRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pRight.add(new JLabel("Lọc theo trạng thái:"));
        cbTrangThai = new JComboBox<>(new String[] { "Tất cả", "Đang mượn", "Đã trả", "Quá hạn" });
        cbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbTrangThai.addActionListener(e -> locTheoTrangThai());
        pRight.add(cbTrangThai);

        JButton btnXemChiTiet = new JButton("Xem Chi Tiết");
        btnXemChiTiet.setBackground(new Color(0, 102, 204));
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setToolTipText("Xem chi tiết phiếu mượn đã chọn");
        btnXemChiTiet.addActionListener(e -> xemChiTiet());

        JButton btnTraSach = new JButton("Trả Sách");
        btnTraSach.setBackground(new Color(0, 150, 0));
        btnTraSach.setForeground(Color.WHITE);
        btnTraSach.setToolTipText("Mở form trả sách cho phiếu đã chọn");
        btnTraSach.addActionListener(e -> moTraSach());

        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setToolTipText("Tải lại danh sách phiếu mượn");
        btnLamMoi.addActionListener(e -> loadDuLieu());

        JButton btnQuayVe = new JButton("🏠 Quay lại");
        btnQuayVe.setBackground(new Color(100, 100, 100));
        btnQuayVe.setForeground(Color.WHITE);
        btnQuayVe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnQuayVe.setToolTipText("Quay về màn hình chính");
        btnQuayVe.addActionListener(e -> quayVeMainForm());

        pRight.add(btnXemChiTiet);
        pRight.add(btnTraSach);
        pRight.add(btnLamMoi);
        pRight.add(btnQuayVe); // Thêm nút vào panel

        top.add(pLeft, BorderLayout.WEST);
        top.add(pRight, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // ==================== BẢNG PHIẾU MƯỢN ====================
        String[] cols = { "Mã phiếu", "Mã bạn đọc", "Họ tên bạn đọc", "Nhân viên",
                "Ngày mượn", "Hạn trả", "Quá hạn", "Trạng thái" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Tô màu theo trạng thái
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String trangThai = (String) model.getValueAt(row, 7);
                if (!isSelected) {
                    if ("Quá hạn".equals(trangThai)) {
                        c.setBackground(new Color(255, 200, 200));
                    } else if ("Đang mượn".equals(trangThai)) {
                        c.setBackground(new Color(200, 255, 200));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu mượn"));
        add(scroll, BorderLayout.CENTER);

        // ==================== THÔNG TIN TỔNG QUAN ====================
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBorder(BorderFactory.createTitledBorder("Thống kê nhanh"));
        JLabel lblThongKe = new JLabel();
        lblThongKe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bottom.add(lblThongKe);

        // Load thống kê
        List<phieuMuon> ds = pmCtrl.layTatCaPhieuMuon();
        long dangMuon = ds.stream().filter(p -> "Đang mượn".equals(p.getTrangThai())).count();
        long daTra = ds.stream().filter(p -> "Đã trả".equals(p.getTrangThai())).count();
        long quaHan = ds.stream().filter(p -> "Quá hạn".equals(p.getTrangThai())).count();
        lblThongKe.setText(String.format(
                "Tổng: %d phiếu | Đang mượn: %d | Đã trả: %d | Quá hạn: %d",
                ds.size(), dangMuon, daTra, quaHan));

        add(bottom, BorderLayout.SOUTH);
    }

    private void loadDuLieu() {
        model.setRowCount(0);
        List<phieuMuon> ds = pmCtrl.layTatCaPhieuMuon();

        for (phieuMuon pm : ds) {
            // Tính số ngày quá hạn
            long quaHanNgay = 0;
            if (pm.getNgayHenTra() != null && "Đang mượn".equals(pm.getTrangThai())) {
                quaHanNgay = ChronoUnit.DAYS.between(
                        pm.getNgayHenTra().toLocalDate(),
                        LocalDate.now());
                if (quaHanNgay < 0)
                    quaHanNgay = 0;
            }

            String quaHanStr = quaHanNgay > 0 ? quaHanNgay + " ngày" : "-";

            model.addRow(new Object[] {
                    pm.getMaPhieuMuon(),
                    pm.getMaBanDoc(),
                    pm.getHoTenDocGia() != null ? pm.getHoTenDocGia() : pm.getTenBanDoc(),
                    pm.getTenNhanVien() != null ? pm.getTenNhanVien() : "NV#" + pm.getMaNV(),
                    pm.getNgayMuon() != null ? pm.getNgayMuon().toString() : "",
                    pm.getNgayHenTra() != null ? pm.getNgayHenTra().toString() : "",
                    quaHanStr,
                    pm.getTrangThai()
            });
        }

        // Reset filter
        txtTimKiem.setText("");
        cbTrangThai.setSelectedIndex(0);
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        String trangThaiFilter = (String) cbTrangThai.getSelectedItem();

        model.setRowCount(0);
        List<phieuMuon> ds = pmCtrl.layTatCaPhieuMuon();

        for (phieuMuon pm : ds) {
            // Lọc theo trạng thái
            if (!"Tất cả".equals(trangThaiFilter) && !trangThaiFilter.equals(pm.getTrangThai())) {
                continue;
            }

            // Tìm kiếm
            if (!keyword.isEmpty()) {
                boolean match = false;
                if (String.valueOf(pm.getMaPhieuMuon()).contains(keyword))
                    match = true;
                if (pm.getHoTenDocGia() != null && pm.getHoTenDocGia().toLowerCase().contains(keyword))
                    match = true;
                if (pm.getTenBanDoc() != null && pm.getTenBanDoc().toLowerCase().contains(keyword))
                    match = true;
                if (!match)
                    continue;
            }

            // Tính quá hạn
            long quaHanNgay = 0;
            if (pm.getNgayHenTra() != null && "Đang mượn".equals(pm.getTrangThai())) {
                quaHanNgay = ChronoUnit.DAYS.between(
                        pm.getNgayHenTra().toLocalDate(),
                        LocalDate.now());
                if (quaHanNgay < 0)
                    quaHanNgay = 0;
            }

            String quaHanStr = quaHanNgay > 0 ? quaHanNgay + " ngày" : "-";

            model.addRow(new Object[] {
                    pm.getMaPhieuMuon(),
                    pm.getMaBanDoc(),
                    pm.getHoTenDocGia() != null ? pm.getHoTenDocGia() : pm.getTenBanDoc(),
                    pm.getTenNhanVien() != null ? pm.getTenNhanVien() : "NV#" + pm.getMaNV(),
                    pm.getNgayMuon() != null ? pm.getNgayMuon().toString() : "",
                    pm.getNgayHenTra() != null ? pm.getNgayHenTra().toString() : "",
                    quaHanStr,
                    pm.getTrangThai()
            });
        }
    }

    private void locTheoTrangThai() {
        timKiem(); // Gọi lại tìm kiếm để áp dụng filter
    }

    private void xemChiTiet() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu mượn!");
            return;
        }

        int maPhieu = (Integer) model.getValueAt(row, 0);
        phieuMuon pm = pmCtrl.layTheoMa(maPhieu);

        if (pm == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu mượn!");
            return;
        }

        // Lấy chi tiết sách
        List<CTPhieuMuon> dsChiTiet = pmCtrl.layChiTietPhieuMuon(maPhieu);

        // Tạo dialog hiển thị chi tiết
        JDialog dialog = new JDialog(this, "Chi tiết phiếu mượn #" + maPhieu, true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Thông tin phiếu
        JPanel pInfo = new JPanel(new GridLayout(6, 2, 10, 10));
        pInfo.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu mượn"));
        pInfo.add(new JLabel("Mã phiếu:"));
        pInfo.add(new JLabel(String.valueOf(pm.getMaPhieuMuon())));
        pInfo.add(new JLabel("Bạn đọc:"));
        pInfo.add(new JLabel(pm.getHoTenDocGia() != null ? pm.getHoTenDocGia() : pm.getTenBanDoc()));
        pInfo.add(new JLabel("Nhân viên:"));
        pInfo.add(new JLabel(pm.getTenNhanVien() != null ? pm.getTenNhanVien() : "NV#" + pm.getMaNV()));
        pInfo.add(new JLabel("Ngày mượn:"));
        pInfo.add(new JLabel(pm.getNgayMuon() != null ? pm.getNgayMuon().toString() : ""));
        pInfo.add(new JLabel("Hạn trả:"));
        pInfo.add(new JLabel(pm.getNgayHenTra() != null ? pm.getNgayHenTra().toString() : ""));
        pInfo.add(new JLabel("Trạng thái:"));
        JLabel lblTrangThai = new JLabel(pm.getTrangThai());
        if ("Quá hạn".equals(pm.getTrangThai())) {
            lblTrangThai.setForeground(Color.RED);
            lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
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

        int stt = 1;
        for (CTPhieuMuon ct : dsChiTiet) {
            modelChiTiet.addRow(new Object[] {
                    stt++,
                    ct.getMaSach(),
                    ct.getTenSach(),
                    ct.getSoLuong()
            });
        }

        JScrollPane scrollChiTiet = new JScrollPane(tableChiTiet);
        scrollChiTiet
                .setBorder(BorderFactory.createTitledBorder("Danh sách sách mượn (" + dsChiTiet.size() + " cuốn)"));
        main.add(scrollChiTiet, BorderLayout.CENTER);

        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dialog.dispose());
        JPanel pNut = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pNut.add(btnDong);
        main.add(pNut, BorderLayout.SOUTH);

        dialog.add(main);
        dialog.setVisible(true);
    }

    private void moTraSach() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu mượn!");
            return;
        }

        int maPhieu = (Integer) model.getValueAt(row, 0);
        String trangThai = (String) model.getValueAt(row, 7);

        if ("Đã trả".equals(trangThai)) {
            JOptionPane.showMessageDialog(this, "Phiếu này đã được trả rồi!");
            return;
        }

        // Mở màn hình trả sách
        TraSach traSach = new TraSach(mainForm);
        traSach.setMaPhieuMuon(maPhieu);
        traSach.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyPhieuMuon(null).setVisible(true));
    }
}
