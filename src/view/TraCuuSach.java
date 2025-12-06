package view;

import client.SachClientController;
import client.TheLoaiClientController;
import model.sach;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

public class TraCuuSach extends JFrame {
    private SachClientController sachCtrl = new SachClientController();
    private TheLoaiClientController theLoaiCtrl = new TheLoaiClientController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtTenSach, txtTacGia, txtNhaXuatBan, txtNamXuatBan;
    private JComboBox<String> cmbTheLoai;
    private JLabel statusLabel;
    private MainForm mainForm;

    public TraCuuSach(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("TRA CỨU SÁCH");
        setSize(1400, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quayVeMainForm();
            }
        });

        initGUI();
        loadDuLieu();
    }

    private void initGUI() {
        getContentPane().setBackground(new Color(245, 247, 250));

        // ==================== HEADER ====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(new Color(52, 152, 219));

        JLabel lblTitle = new JLabel("TRA CỨU SÁCH", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JButton btnQuayVe = createButton("Quay lại", new Color(100, 100, 100));
        btnQuayVe.addActionListener(e -> quayVeMainForm());
        headerPanel.add(btnQuayVe, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ==================== PANEL TÌM KIẾM ====================
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Tìm kiếm theo tiêu chí",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(52, 152, 219)
            ),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        searchPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Hàng 1: Tên sách và Tác giả
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblTenSach = new JLabel("Tên sách:");
        lblTenSach.setFont(labelFont);
        searchPanel.add(lblTenSach, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtTenSach = new JTextField();
        txtTenSach.setFont(fieldFont);
        txtTenSach.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtTenSach.setToolTipText("Nhập tên sách (có thể nhập một phần, không cần chính xác)");
        txtTenSach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() != java.awt.event.KeyEvent.VK_ENTER) {
                    timKiem();
                }
            }
        });
        searchPanel.add(txtTenSach, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblTacGia = new JLabel("Tác giả:");
        lblTacGia.setFont(labelFont);
        searchPanel.add(lblTacGia, gbc);

        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtTacGia = new JTextField();
        txtTacGia.setFont(fieldFont);
        txtTacGia.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtTacGia.setToolTipText("Nhập tên tác giả (có thể nhập một phần, không cần chính xác)");
        txtTacGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() != java.awt.event.KeyEvent.VK_ENTER) {
                    timKiem();
                }
            }
        });
        searchPanel.add(txtTacGia, gbc);

        // Hàng 2: Thể loại và Nhà xuất bản
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblTheLoai = new JLabel("Thể loại:");
        lblTheLoai.setFont(labelFont);
        searchPanel.add(lblTheLoai, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cmbTheLoai = new JComboBox<>();
        cmbTheLoai.setFont(fieldFont);
        cmbTheLoai.addItem("Tất cả");
        theLoaiCtrl.layTatCaMap().values().forEach(cmbTheLoai::addItem);
        cmbTheLoai.setToolTipText("Chọn thể loại để lọc");
        cmbTheLoai.addActionListener(e -> timKiem());
        searchPanel.add(cmbTheLoai, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblNhaXuatBan = new JLabel("Nhà xuất bản:");
        lblNhaXuatBan.setFont(labelFont);
        searchPanel.add(lblNhaXuatBan, gbc);

        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNhaXuatBan = new JTextField();
        txtNhaXuatBan.setFont(fieldFont);
        txtNhaXuatBan.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtNhaXuatBan.setToolTipText("Nhập nhà xuất bản (có thể nhập một phần, không cần chính xác)");
        txtNhaXuatBan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() != java.awt.event.KeyEvent.VK_ENTER) {
                    timKiem();
                }
            }
        });
        searchPanel.add(txtNhaXuatBan, gbc);

        // Hàng 3: Năm xuất bản và Nút
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblNamXuatBan = new JLabel("Năm xuất bản:");
        lblNamXuatBan.setFont(labelFont);
        searchPanel.add(lblNamXuatBan, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNamXuatBan = new JTextField();
        txtNamXuatBan.setFont(fieldFont);
        txtNamXuatBan.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtNamXuatBan.setToolTipText("Nhập năm xuất bản (để trống để tìm tất cả)");
        txtNamXuatBan.setPreferredSize(new Dimension(100, 30));
        // Chỉ cho phép nhập số
        txtNamXuatBan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == java.awt.event.KeyEvent.VK_BACK_SPACE || 
                      c == java.awt.event.KeyEvent.VK_DELETE || c == java.awt.event.KeyEvent.VK_ENTER)) {
                    e.consume();
                }
            }
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() != java.awt.event.KeyEvent.VK_ENTER) {
                    timKiem();
                }
            }
        });
        txtNamXuatBan.addActionListener(e -> timKiem());
        searchPanel.add(txtNamXuatBan, gbc);

        // Nút tìm kiếm và xóa
        gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnXoa = createButton("Xóa bộ lọc", new Color(149, 165, 166));
        btnXoa.addActionListener(e -> xoaBoLoc());
        buttonPanel.add(btnXoa);

        JButton btnTimKiem = createButton("Tìm kiếm", new Color(52, 152, 219));
        btnTimKiem.addActionListener(e -> timKiem());
        buttonPanel.add(btnTimKiem);

        searchPanel.add(buttonPanel, gbc);

        // Thêm listener cho Enter key
        txtTenSach.addActionListener(e -> timKiem());
        txtTacGia.addActionListener(e -> timKiem());
        txtNhaXuatBan.addActionListener(e -> timKiem());

        add(searchPanel, BorderLayout.NORTH);

        // ==================== BẢNG KẾT QUẢ ====================
        String[] columns = {"Mã sách", "Tên sách", "Tác giả", "Thể loại", "Năm XB", "Nhà XB", "Số lượng", "Còn lại", "Trạng thái"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        styleTable(table, new Color(52, 152, 219));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Kết quả tìm kiếm",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(52, 152, 219)
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        scrollPane.setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // ==================== STATUS BAR ====================
        statusLabel = new JLabel("Sẵn sàng tìm kiếm. Nhập từ khóa vào các ô tìm kiếm để bắt đầu.", SwingConstants.LEFT);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setBackground(new Color(240, 248, 255));
        statusLabel.setForeground(new Color(127, 140, 141));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        statusLabel.setOpaque(true);
        add(statusLabel, BorderLayout.SOUTH);
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

    private void styleTable(JTable table, Color headerColor) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(headerColor);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
    }

    // Helper method để loại bỏ dấu tiếng Việt và chuyển thành chữ thường
    private String normalizeString(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase().trim();
    }

    private void loadDuLieu() {
        List<sach> ds = sachCtrl.layTatCaSach();
        hienThiKetQua(ds);
    }

    private Integer getNamXuatBanValue() {
        try {
            String text = txtNamXuatBan.getText().trim();
            if (text.isEmpty()) {
                return null;
            }
            return Integer.parseInt(text);
        } catch (Exception e) {
            // Ignore - trả về null nếu không parse được
            return null;
        }
    }

    private void timKiem() {
        final String tenSach = txtTenSach.getText().trim();
        final String tacGia = txtTacGia.getText().trim();
        final String theLoai = (String) cmbTheLoai.getSelectedItem();
        final String nhaXuatBan = txtNhaXuatBan.getText().trim();
        final Integer namXuatBan = getNamXuatBanValue();

        // Normalize các từ khóa tìm kiếm
        final String tenSachNormalized = normalizeString(tenSach);
        final String tacGiaNormalized = normalizeString(tacGia);
        final String nhaXuatBanNormalized = normalizeString(nhaXuatBan);

        List<sach> ds = sachCtrl.layTatCaSach();
        
        // Lọc theo các tiêu chí với tìm kiếm không phân biệt dấu
        List<sach> ketQua = ds.stream()
            .filter(s -> {
                if (tenSachNormalized.isEmpty()) return true;
                String tenSachDB = normalizeString(s.getTenSach());
                return tenSachDB.contains(tenSachNormalized);
            })
            .filter(s -> {
                if (tacGiaNormalized.isEmpty()) return true;
                String tacGiaDB = normalizeString(s.getTenTacGia());
                return tacGiaDB.contains(tacGiaNormalized);
            })
            .filter(s -> {
                if (theLoai == null || theLoai.equals("Tất cả")) return true;
                return s.getTenTheLoai() != null && s.getTenTheLoai().equals(theLoai);
            })
            .filter(s -> {
                if (nhaXuatBanNormalized.isEmpty()) return true;
                String nhaXBDb = normalizeString(s.getNhaXuatBan());
                return nhaXBDb.contains(nhaXuatBanNormalized);
            })
            .filter(s -> {
                if (namXuatBan == null) return true;
                return s.getNamXuatBan() != null && s.getNamXuatBan().equals(namXuatBan);
            })
            .toList();

        hienThiKetQua(ketQua);
    }

    private void hienThiKetQua(List<sach> ds) {
        model.setRowCount(0);
        for (sach s : ds) {
            Object[] row = {
                s.getMaSach(),
                s.getTenSach(),
                s.getTenTacGia() != null ? s.getTenTacGia() : "",
                s.getTenTheLoai() != null ? s.getTenTheLoai() : "",
                s.getNamXuatBan() != null ? s.getNamXuatBan() : "",
                s.getNhaXuatBan() != null ? s.getNhaXuatBan() : "",
                s.getSoLuongTong(),
                s.getSoLuongHienTai(),
                s.getTrangThai()
            };
            model.addRow(row);
        }
        
        // Cập nhật status bar
        if (statusLabel != null) {
            if (ds.isEmpty()) {
                statusLabel.setText("Không tìm thấy kết quả nào. Hãy thử với từ khóa khác hoặc xóa bộ lọc để xem tất cả sách.");
                statusLabel.setForeground(new Color(231, 76, 60));
            } else {
                statusLabel.setText("Tìm thấy " + ds.size() + " kết quả. Tìm kiếm hỗ trợ không phân biệt dấu và hoa thường.");
                statusLabel.setForeground(new Color(46, 204, 113));
            }
        }
    }

    private void xoaBoLoc() {
        txtTenSach.setText("");
        txtTacGia.setText("");
        cmbTheLoai.setSelectedIndex(0);
        txtNhaXuatBan.setText("");
        txtNamXuatBan.setText("");
        loadDuLieu();
    }

    private void quayVeMainForm() {
        this.dispose();
        if (mainForm != null) {
            mainForm.setVisible(true);
        }
    }
}

