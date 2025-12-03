package view;

import controller.BanDocController;
import controller.PhieuMuonController;
import model.banDoc;
import model.phieuMuon;
import model.CTPhieuMuon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKeDocGia extends JFrame {

    private BanDocController bdCtrl = new BanDocController();
    private PhieuMuonController pmCtrl = new PhieuMuonController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtTimKiem;
    private JLabel lblTongSo, lblTongPhieuMuon, lblTongSachMuon;
    private MainForm mainForm;

    public ThongKeDocGia(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("THỐNG KÊ ĐỘC GIẢ");
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
        headerPanel.setBackground(new Color(46, 204, 113));

        JLabel lblTitle = new JLabel("THỐNG KÊ ĐỘC GIẢ", SwingConstants.LEFT);
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
        pLeft.add(new JLabel("Tìm kiếm (tên độc giả/mã độc giả/lớp):"));
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
        String[] cols = { "Mã độc giả", "Họ tên", "Lớp", "SĐT", "Email", 
                "Số phiếu mượn", "Số sách đã mượn", "Số sách đang mượn", "Trạng thái" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setBackground(new Color(46, 204, 113));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
                "Danh sách độc giả và thống kê mượn sách",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(46, 204, 113)
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        add(scroll, BorderLayout.CENTER);

        // ==================== THÔNG TIN TỔNG QUAN ====================
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bottomPanel.setBackground(new Color(240, 255, 240));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 5));
        infoPanel.setBackground(new Color(240, 255, 240));

        lblTongSo = new JLabel("Tổng số: 0 độc giả");
        lblTongSo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTongSo.setForeground(new Color(46, 204, 113));
        infoPanel.add(lblTongSo);

        lblTongPhieuMuon = new JLabel("Tổng số phiếu mượn: 0 phiếu");
        lblTongPhieuMuon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTongPhieuMuon.setForeground(new Color(46, 204, 113));
        infoPanel.add(lblTongPhieuMuon);

        lblTongSachMuon = new JLabel("Tổng số sách đã mượn: 0 cuốn");
        lblTongSachMuon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTongSachMuon.setForeground(new Color(46, 204, 113));
        infoPanel.add(lblTongSachMuon);

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
        
        List<banDoc> dsDocGia = bdCtrl.layTatCa();
        List<phieuMuon> dsPhieuMuon = pmCtrl.layTatCaPhieuMuon();

        // Map để đếm số phiếu và số sách cho mỗi độc giả
        Map<Integer, Integer> mapSoPhieu = new HashMap<>(); // Mã độc giả -> Số phiếu
        Map<Integer, Integer> mapSoSachDaMuon = new HashMap<>(); // Mã độc giả -> Số sách đã mượn
        Map<Integer, Integer> mapSoSachDangMuon = new HashMap<>(); // Mã độc giả -> Số sách đang mượn

        // Đếm số phiếu và số sách
        for (phieuMuon pm : dsPhieuMuon) {
            int maBD = pm.getMaBanDoc();
            mapSoPhieu.put(maBD, mapSoPhieu.getOrDefault(maBD, 0) + 1);
            
            // Đếm số sách trong phiếu
            List<CTPhieuMuon> dsChiTiet = pmCtrl.layChiTietPhieuMuon(pm.getMaPhieuMuon());
            int tongSachTrongPhieu = dsChiTiet.stream().mapToInt(CTPhieuMuon::getSoLuong).sum();
            
            if ("Đã trả".equals(pm.getTrangThai())) {
                mapSoSachDaMuon.put(maBD, mapSoSachDaMuon.getOrDefault(maBD, 0) + tongSachTrongPhieu);
            } else if ("Đang mượn".equals(pm.getTrangThai()) || "Quá hạn".equals(pm.getTrangThai())) {
                mapSoSachDangMuon.put(maBD, mapSoSachDangMuon.getOrDefault(maBD, 0) + tongSachTrongPhieu);
            }
        }

        int tongPhieuMuon = 0;
        int tongSachDaMuon = 0;

        for (banDoc bd : dsDocGia) {
            int soPhieu = mapSoPhieu.getOrDefault(bd.getMaBanDoc(), 0);
            int soSachDaMuon = mapSoSachDaMuon.getOrDefault(bd.getMaBanDoc(), 0);
            int soSachDangMuon = mapSoSachDangMuon.getOrDefault(bd.getMaBanDoc(), 0);
            
            tongPhieuMuon += soPhieu;
            tongSachDaMuon += soSachDaMuon;

            String trangThai = soSachDangMuon > 0 ? "Đang mượn" : (soPhieu > 0 ? "Đã từng mượn" : "Chưa mượn");

            model.addRow(new Object[] {
                    bd.getMaBanDoc(),
                    bd.getHoTen(),
                    bd.getLop() != null ? bd.getLop() : "",
                    bd.getSdt() != null ? bd.getSdt() : "",
                    bd.getEmail() != null ? bd.getEmail() : "",
                    soPhieu,
                    soSachDaMuon,
                    soSachDangMuon,
                    trangThai
            });
        }

        // Cập nhật thông tin tổng quan
        lblTongSo.setText("Tổng số: " + dsDocGia.size() + " độc giả");
        lblTongPhieuMuon.setText("Tổng số phiếu mượn: " + tongPhieuMuon + " phiếu");
        lblTongSachMuon.setText("Tổng số sách đã mượn: " + tongSachDaMuon + " cuốn");
    }

    private void timKiem(String keyword) {
        model.setRowCount(0);
        
        List<banDoc> dsDocGia = bdCtrl.layTatCa();
        List<phieuMuon> dsPhieuMuon = pmCtrl.layTatCaPhieuMuon();

        Map<Integer, Integer> mapSoPhieu = new HashMap<>();
        Map<Integer, Integer> mapSoSachDaMuon = new HashMap<>();
        Map<Integer, Integer> mapSoSachDangMuon = new HashMap<>();

        for (phieuMuon pm : dsPhieuMuon) {
            int maBD = pm.getMaBanDoc();
            mapSoPhieu.put(maBD, mapSoPhieu.getOrDefault(maBD, 0) + 1);
            
            List<CTPhieuMuon> dsChiTiet = pmCtrl.layChiTietPhieuMuon(pm.getMaPhieuMuon());
            int tongSachTrongPhieu = dsChiTiet.stream().mapToInt(CTPhieuMuon::getSoLuong).sum();
            
            if ("Đã trả".equals(pm.getTrangThai())) {
                mapSoSachDaMuon.put(maBD, mapSoSachDaMuon.getOrDefault(maBD, 0) + tongSachTrongPhieu);
            } else if ("Đang mượn".equals(pm.getTrangThai()) || "Quá hạn".equals(pm.getTrangThai())) {
                mapSoSachDangMuon.put(maBD, mapSoSachDangMuon.getOrDefault(maBD, 0) + tongSachTrongPhieu);
            }
        }

        int tongPhieuMuon = 0;
        int tongSachDaMuon = 0;
        int count = 0;

        for (banDoc bd : dsDocGia) {
            // Tìm kiếm
            if (!keyword.isEmpty()) {
                boolean match = false;
                if (String.valueOf(bd.getMaBanDoc()).contains(keyword))
                    match = true;
                if (bd.getHoTen() != null && bd.getHoTen().toLowerCase().contains(keyword.toLowerCase()))
                    match = true;
                if (bd.getLop() != null && bd.getLop().toLowerCase().contains(keyword.toLowerCase()))
                    match = true;
                if (!match)
                    continue;
            }

            count++;
            int soPhieu = mapSoPhieu.getOrDefault(bd.getMaBanDoc(), 0);
            int soSachDaMuon = mapSoSachDaMuon.getOrDefault(bd.getMaBanDoc(), 0);
            int soSachDangMuon = mapSoSachDangMuon.getOrDefault(bd.getMaBanDoc(), 0);
            
            tongPhieuMuon += soPhieu;
            tongSachDaMuon += soSachDaMuon;

            String trangThai = soSachDangMuon > 0 ? "Đang mượn" : (soPhieu > 0 ? "Đã từng mượn" : "Chưa mượn");

            model.addRow(new Object[] {
                    bd.getMaBanDoc(),
                    bd.getHoTen(),
                    bd.getLop() != null ? bd.getLop() : "",
                    bd.getSdt() != null ? bd.getSdt() : "",
                    bd.getEmail() != null ? bd.getEmail() : "",
                    soPhieu,
                    soSachDaMuon,
                    soSachDangMuon,
                    trangThai
            });
        }

        lblTongSo.setText("Tìm thấy: " + count + " độc giả");
        lblTongPhieuMuon.setText("Tổng số phiếu mượn: " + tongPhieuMuon + " phiếu");
        lblTongSachMuon.setText("Tổng số sách đã mượn: " + tongSachDaMuon + " cuốn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ThongKeDocGia(null).setVisible(true));
    }
}

