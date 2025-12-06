package view;

import client.PhieuMuonClientController;
import model.CTPhieuMuon;
import model.phieuMuon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThongKeSachDuocMuon extends JFrame {

    private PhieuMuonClientController pmCtrl = new PhieuMuonClientController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtTimKiem;
    private JLabel lblTongSo, lblTongCuon;
    private MainForm mainForm;

    public ThongKeSachDuocMuon(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("THỐNG KÊ SÁCH ĐƯỢC MƯỢN");
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
        }
        this.dispose();
    }

    private void initGUI() {
        // ==================== HEADER ====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(new Color(243, 156, 18));

        JLabel lblTitle = new JLabel("THỐNG KÊ SÁCH ĐƯỢC MƯỢN", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JButton btnQuayVe = createButton("Quay lại", new Color(100, 100, 100));
        btnQuayVe.addActionListener(e -> quayVeMainForm());
        headerPanel.add(btnQuayVe, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ==================== THANH TÌM KIẾM ====================
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchPanel.setBackground(Color.WHITE);

        JPanel pLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pLeft.setBackground(Color.WHITE);
        pLeft.add(new JLabel("Tìm kiếm (tên sách/mã sách/mã phiếu):"));
        txtTimKiem = new JTextField(30);
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

        JButton btnLamMoi = createButton("Làm mới", new Color(52, 152, 219));
        btnLamMoi.setToolTipText("Tải lại danh sách");
        btnLamMoi.addActionListener(e -> loadDuLieu());

        pRight.add(btnLamMoi);

        searchPanel.add(pLeft, BorderLayout.WEST);
        searchPanel.add(pRight, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        // ==================== BẢNG THỐNG KÊ ====================
        String[] cols = { "Mã phiếu", "Mã sách", "Tên sách", "Số lượng", 
                "Mã độc giả", "Tên độc giả", "Ngày mượn", "Hạn trả", "Trạng thái" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setBackground(new Color(243, 156, 18));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Tô màu cho các dòng theo trạng thái
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    String trangThai = (String) model.getValueAt(row, 8);
                    if ("Quá hạn".equals(trangThai)) {
                        c.setBackground(new Color(255, 240, 240)); // Đỏ nhạt
                    } else if ("Đang mượn".equals(trangThai)) {
                        c.setBackground(new Color(255, 255, 240)); // Vàng nhạt
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(243, 156, 18), 2),
                "Danh sách sách đang được mượn",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(243, 156, 18)
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        add(scroll, BorderLayout.CENTER);

        // ==================== THÔNG TIN TỔNG QUAN ====================
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bottomPanel.setBackground(new Color(255, 255, 240));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 5));
        infoPanel.setBackground(new Color(255, 255, 240));

        lblTongSo = new JLabel("Tổng số: 0 đầu sách");
        lblTongSo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTongSo.setForeground(new Color(243, 156, 18));
        infoPanel.add(lblTongSo);

        lblTongCuon = new JLabel("Tổng số cuốn: 0 cuốn");
        lblTongCuon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTongCuon.setForeground(new Color(243, 156, 18));
        infoPanel.add(lblTongCuon);

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
        
        // Lấy tất cả phiếu mượn đang mượn
        List<phieuMuon> dsPhieuMuon = pmCtrl.layTatCaPhieuMuon();
        List<phieuMuon> dsDangMuon = new ArrayList<>();
        for (phieuMuon pm : dsPhieuMuon) {
            if ("Đang mượn".equals(pm.getTrangThai()) || "Quá hạn".equals(pm.getTrangThai())) {
                dsDangMuon.add(pm);
            }
        }

        // Map để đếm số đầu sách và số cuốn
        Map<Integer, Integer> mapSach = new HashMap<>(); // Mã sách -> Số cuốn
        Set<Integer> setDauSach = new HashSet<>(); // Tập hợp mã sách

        for (phieuMuon pm : dsDangMuon) {
            List<CTPhieuMuon> dsChiTiet = pmCtrl.layChiTietPhieuMuon(pm.getMaPhieuMuon());
            for (CTPhieuMuon ct : dsChiTiet) {
                int maSach = ct.getMaSach();
                int soLuong = ct.getSoLuong();
                
                setDauSach.add(maSach);
                mapSach.put(maSach, mapSach.getOrDefault(maSach, 0) + soLuong);

                // Tính số ngày quá hạn
                long quaHanNgay = 0;
                if (pm.getNgayHenTra() != null && "Quá hạn".equals(pm.getTrangThai())) {
                    quaHanNgay = ChronoUnit.DAYS.between(
                            pm.getNgayHenTra().toLocalDate(),
                            LocalDate.now());
                    if (quaHanNgay < 0)
                        quaHanNgay = 0;
                }

                model.addRow(new Object[] {
                        pm.getMaPhieuMuon(),
                        maSach,
                        ct.getTenSach() != null ? ct.getTenSach() : "",
                        soLuong,
                        pm.getMaBanDoc(),
                        pm.getHoTenDocGia() != null ? pm.getHoTenDocGia() : "",
                        pm.getNgayMuon() != null ? pm.getNgayMuon().toString() : "",
                        pm.getNgayHenTra() != null ? pm.getNgayHenTra().toString() : "",
                        pm.getTrangThai()
                });
            }
        }

        // Cập nhật thông tin tổng quan
        int tongDauSach = setDauSach.size();
        int tongCuon = mapSach.values().stream().mapToInt(Integer::intValue).sum();
        
        lblTongSo.setText("Tổng số: " + tongDauSach + " đầu sách");
        lblTongCuon.setText("Tổng số cuốn: " + tongCuon + " cuốn");
    }

    private void timKiem(String keyword) {
        model.setRowCount(0);
        
        List<phieuMuon> dsPhieuMuon = pmCtrl.layTatCaPhieuMuon();
        List<phieuMuon> dsDangMuon = new ArrayList<>();
        for (phieuMuon pm : dsPhieuMuon) {
            if ("Đang mượn".equals(pm.getTrangThai()) || "Quá hạn".equals(pm.getTrangThai())) {
                dsDangMuon.add(pm);
            }
        }

        Map<Integer, Integer> mapSach = new HashMap<>();
        Set<Integer> setDauSach = new HashSet<>();

        for (phieuMuon pm : dsDangMuon) {
            List<CTPhieuMuon> dsChiTiet = pmCtrl.layChiTietPhieuMuon(pm.getMaPhieuMuon());
            for (CTPhieuMuon ct : dsChiTiet) {
                int maSach = ct.getMaSach();
                int soLuong = ct.getSoLuong();

                // Tìm kiếm
                if (!keyword.isEmpty()) {
                    boolean match = false;
                    if (String.valueOf(pm.getMaPhieuMuon()).contains(keyword))
                        match = true;
                    if (String.valueOf(maSach).contains(keyword))
                        match = true;
                    if (ct.getTenSach() != null && ct.getTenSach().toLowerCase().contains(keyword.toLowerCase()))
                        match = true;
                    if (!match)
                        continue;
                }

                setDauSach.add(maSach);
                mapSach.put(maSach, mapSach.getOrDefault(maSach, 0) + soLuong);

                long quaHanNgay = 0;
                if (pm.getNgayHenTra() != null && "Quá hạn".equals(pm.getTrangThai())) {
                    quaHanNgay = ChronoUnit.DAYS.between(
                            pm.getNgayHenTra().toLocalDate(),
                            LocalDate.now());
                    if (quaHanNgay < 0)
                        quaHanNgay = 0;
                }

                model.addRow(new Object[] {
                        pm.getMaPhieuMuon(),
                        maSach,
                        ct.getTenSach() != null ? ct.getTenSach() : "",
                        soLuong,
                        pm.getMaBanDoc(),
                        pm.getHoTenDocGia() != null ? pm.getHoTenDocGia() : "",
                        pm.getNgayMuon() != null ? pm.getNgayMuon().toString() : "",
                        pm.getNgayHenTra() != null ? pm.getNgayHenTra().toString() : "",
                        pm.getTrangThai()
                });
            }
        }

        int tongDauSach = setDauSach.size();
        int tongCuon = mapSach.values().stream().mapToInt(Integer::intValue).sum();
        
        lblTongSo.setText("Tìm thấy: " + tongDauSach + " đầu sách");
        lblTongCuon.setText("Tổng số cuốn: " + tongCuon + " cuốn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ThongKeSachDuocMuon(null).setVisible(true));
    }
}

