package view;

import client.TacGiaClientController;
import model.tacGia;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class QuanLyTacGia extends JFrame {
    private TacGiaClientController tacGiaCtrl = new TacGiaClientController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtTimKiem;
    private JLabel statusLabel;
    private MainForm mainForm;

    public QuanLyTacGia(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("QUẢN LÝ TÁC GIẢ");
        setSize(1200, 750);
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
        // ==================== HEADER ====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(new Color(46, 204, 113));

        JLabel lblTitle = new JLabel("QUẢN LÝ TÁC GIẢ", SwingConstants.LEFT);
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

        // ==================== PANEL TÌM KIẾM VÀ THAO TÁC ====================
        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        top.setBackground(Color.WHITE);

        JPanel pTim = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pTim.setBackground(Color.WHITE);
        JLabel lblTim = new JLabel("Tìm kiếm:");
        lblTim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pTim.add(lblTim);
        txtTimKiem = new JTextField(30);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtTimKiem.setToolTipText("Nhập tên tác giả để tìm kiếm");
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                timKiem();
            }
        });
        pTim.add(txtTimKiem);

        JButton btnXoaTim = new JButton("Xóa");
        btnXoaTim.setBackground(new Color(149, 165, 166));
        btnXoaTim.setForeground(Color.WHITE);
        btnXoaTim.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnXoaTim.setFocusPainted(false);
        btnXoaTim.setPreferredSize(new Dimension(70, 30));
        btnXoaTim.addActionListener(e -> {
            txtTimKiem.setText("");
            loadDuLieu();
        });
        pTim.add(btnXoaTim);

        JPanel pNut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pNut.setBackground(Color.WHITE);

        JButton btnThem = createButton("Thêm mới", new Color(46, 204, 113));
        btnThem.setToolTipText("Thêm tác giả mới");
        JButton btnSua = createButton("Sửa", new Color(0, 102, 204));
        btnSua.setToolTipText("Sửa tác giả đã chọn");
        JButton btnXoa = createButton("Xóa", new Color(231, 76, 60));
        btnXoa.setToolTipText("Xóa tác giả đã chọn");
        JButton btnLamMoi = createButton("Làm mới", new Color(52, 152, 219));
        btnLamMoi.setToolTipText("Tải lại danh sách");

        btnThem.addActionListener(e -> themTacGia());
        btnSua.addActionListener(e -> suaTacGia());
        btnXoa.addActionListener(e -> xoaTacGia());
        btnLamMoi.addActionListener(e -> loadDuLieu());

        pNut.add(btnThem);
        pNut.add(btnSua);
        pNut.add(btnXoa);
        pNut.add(btnLamMoi);

        top.add(pTim, BorderLayout.WEST);
        top.add(pNut, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // ==================== BẢNG TÁC GIẢ ====================
        String[] cols = {"Mã tác giả", "Tên tác giả", "Năm sinh", "Quốc tịch"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(46, 204, 113));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setReorderingAllowed(false);

        // Double-click to edit
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    suaTacGia();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách tác giả"));
        add(scroll, BorderLayout.CENTER);

        // ==================== STATUS BAR ====================
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(245, 247, 250));
        
        statusLabel = new JLabel("Đang tải dữ liệu...", JLabel.LEFT);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        statusLabel.setBackground(new Color(240, 248, 255));
        statusLabel.setForeground(new Color(127, 140, 141));
        statusLabel.setOpaque(true);
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        JLabel lblInstructions = new JLabel("Hướng dẫn: Double-click vào dòng để sửa | Chọn dòng và nhấn nút để thao tác");
        lblInstructions.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInstructions.setForeground(new Color(100, 100, 100));
        lblInstructions.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));
        statusPanel.add(lblInstructions, BorderLayout.SOUTH);
        
        add(statusPanel, BorderLayout.SOUTH);
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

    private void loadDuLieu() {
        model.setRowCount(0);
        List<tacGia> ds = tacGiaCtrl.layTatCa();
        for (tacGia tg : ds) {
            model.addRow(new Object[]{
                tg.getMaTacGia(),
                tg.getTenTacGia(),
                tg.getNamSinh() != null ? tg.getNamSinh() : "",
                tg.getQuocTich() != null ? tg.getQuocTich() : ""
            });
        }
        updateStatus("Tổng cộng: " + model.getRowCount() + " tác giả");
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        model.setRowCount(0);
        List<tacGia> ds = tacGiaCtrl.layTatCa();
        for (tacGia tg : ds) {
            if (tg.getTenTacGia().toLowerCase().contains(keyword) ||
                (tg.getQuocTich() != null && tg.getQuocTich().toLowerCase().contains(keyword))) {
                model.addRow(new Object[]{
                    tg.getMaTacGia(),
                    tg.getTenTacGia(),
                    tg.getNamSinh() != null ? tg.getNamSinh() : "",
                    tg.getQuocTich() != null ? tg.getQuocTich() : ""
                });
            }
        }
        if (keyword.isEmpty()) {
            updateStatus("Tổng cộng: " + model.getRowCount() + " tác giả");
        } else {
            updateStatus("Tìm thấy: " + model.getRowCount() + " tác giả");
        }
    }

    private void updateStatus(String text) {
        if (statusLabel != null) {
            statusLabel.setText(text);
        }
    }

    private void themTacGia() {
        new ThemTacGia(this).setVisible(true);
    }

    private void suaTacGia() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn tác giả để sửa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int ma = (int) model.getValueAt(row, 0);
        new SuaTacGia(ma, this).setVisible(true);
    }

    private void xoaTacGia() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn tác giả để xóa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int ma = (int) model.getValueAt(row, 0);
        String ten = (String) model.getValueAt(row, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "XÓA TÁC GIẢ\n\n" +
            "Bạn có chắc chắn muốn xóa tác giả:\n" +
            "\"" + ten + "\"?\n\n" +
            "Cảnh báo: Không thể xóa nếu đang có sách sử dụng tác giả này!",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean ok = tacGiaCtrl.xoa(ma);
                if (ok) {
                    JOptionPane.showMessageDialog(this,
                        "Xóa tác giả thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadDuLieu();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Xóa tác giả thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refresh() {
        loadDuLieu();
        txtTimKiem.setText("");
    }

    private void quayVeMainForm() {
        if (mainForm != null) {
            mainForm.setVisible(true);
        }
        this.dispose();
    }
}

