package view;

import client.TheLoaiClientController;
import model.theLoai;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class QuanLyTheLoai extends JFrame {
    private TheLoaiClientController theLoaiCtrl = new TheLoaiClientController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtTimKiem;
    private MainForm mainForm;

    public QuanLyTheLoai(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("QUẢN LÝ THỂ LOẠI");
        setSize(1000, 700);
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

        JLabel lblTitle = new JLabel("QUẢN LÝ THỂ LOẠI", SwingConstants.LEFT);
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

        // Panel tìm kiếm - hàng trên
        JPanel pTim = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pTim.setBackground(Color.WHITE);
        JLabel lblTim = new JLabel("Tìm kiếm thể loại:");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pTim.add(lblTim);
        txtTimKiem = new JTextField(25);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtTimKiem.setToolTipText("Nhập tên thể loại để tìm kiếm");
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                timKiem();
            }
        });
        pTim.add(txtTimKiem);

        JButton btnXoaTim = new JButton("Xóa");
        btnXoaTim.setBackground(new Color(149, 165, 166));
        btnXoaTim.setForeground(Color.WHITE);
        btnXoaTim.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnXoaTim.setFocusPainted(false);
        btnXoaTim.setPreferredSize(new Dimension(80, 35));
        btnXoaTim.setToolTipText("Xóa từ khóa tìm kiếm");
        btnXoaTim.addActionListener(e -> {
            txtTimKiem.setText("");
            loadDuLieu();
        });
        pTim.add(btnXoaTim);

        // Panel nút thao tác - hàng dưới
        JPanel pNut = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pNut.setBackground(Color.WHITE);
        pNut.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton btnThem = createButton("Thêm mới", new Color(46, 204, 113));
        btnThem.setToolTipText("Thêm thể loại mới");
        JButton btnSua = createButton("Sửa", new Color(0, 102, 204));
        btnSua.setToolTipText("Sửa thể loại đã chọn");
        JButton btnXoa = createButton("Xóa", new Color(231, 76, 60));
        btnXoa.setToolTipText("Xóa thể loại đã chọn");
        JButton btnLamMoi = createButton("Làm mới", new Color(52, 152, 219));
        btnLamMoi.setToolTipText("Tải lại danh sách");

        btnThem.addActionListener(e -> themTheLoai());
        btnSua.addActionListener(e -> suaTheLoai());
        btnXoa.addActionListener(e -> xoaTheLoai());
        btnLamMoi.addActionListener(e -> loadDuLieu());

        pNut.add(btnThem);
        pNut.add(btnSua);
        pNut.add(btnXoa);
        pNut.add(btnLamMoi);

        // Kết hợp 2 panel trong một container
        JPanel container = new JPanel(new BorderLayout(0, 10));
        container.setBackground(Color.WHITE);
        container.add(pTim, BorderLayout.NORTH);
        container.add(pNut, BorderLayout.CENTER);
        
        top.add(container, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        // ==================== BẢNG THỂ LOẠI ====================
        String[] cols = {"Mã thể loại", "Tên thể loại"};
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
                    suaTheLoai();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách thể loại"));
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
        List<theLoai> ds = theLoaiCtrl.layTatCa();
        for (theLoai tl : ds) {
            model.addRow(new Object[]{
                tl.getMaTheLoai(),
                tl.getTenTheLoai()
            });
        }
        updateStatus("Tổng cộng: " + model.getRowCount() + " thể loại");
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        model.setRowCount(0);
        List<theLoai> ds = theLoaiCtrl.layTatCa();
        for (theLoai tl : ds) {
            if (tl.getTenTheLoai().toLowerCase().contains(keyword)) {
                model.addRow(new Object[]{
                    tl.getMaTheLoai(),
                    tl.getTenTheLoai()
                });
            }
        }
        if (keyword.isEmpty()) {
            updateStatus("Tổng cộng: " + model.getRowCount() + " thể loại");
        } else {
            updateStatus("Tìm thấy: " + model.getRowCount() + " thể loại");
        }
    }

    private JLabel statusLabel;
    
    private void updateStatus(String text) {
        if (statusLabel == null) {
            Component[] components = getContentPane().getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel && ((JPanel) comp).getComponentCount() > 0) {
                    Component statusComp = ((JPanel) comp).getComponent(0);
                    if (statusComp instanceof JLabel) {
                        statusLabel = (JLabel) statusComp;
                        break;
                    }
                }
            }
        }
        if (statusLabel != null) {
            statusLabel.setText(text);
        }
    }

    private void themTheLoai() {
        String tenTheLoai = JOptionPane.showInputDialog(this,
            "Nhập tên thể loại mới:",
            "Thêm thể loại",
            JOptionPane.QUESTION_MESSAGE);
        
        if (tenTheLoai != null && !tenTheLoai.trim().isEmpty()) {
            try {
                theLoai tl = new theLoai();
                tl.setTenTheLoai(tenTheLoai.trim());
                boolean ok = theLoaiCtrl.them(tl);
                if (ok) {
                    JOptionPane.showMessageDialog(this,
                        "Thêm thể loại thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadDuLieu();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Thêm thể loại thất bại!",
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

    private void suaTheLoai() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn thể loại để sửa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int ma = (int) model.getValueAt(row, 0);
        String tenCu = (String) model.getValueAt(row, 1);
        
        // Tạo JTextField với giá trị mặc định là tên cũ
        JTextField txtInput = new JTextField(tenCu);
        txtInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtInput.selectAll(); // Chọn toàn bộ text để dễ sửa
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Tên thể loại hiện tại: " + tenCu), BorderLayout.NORTH);
        panel.add(txtInput, BorderLayout.CENTER);
        
        int result = JOptionPane.showConfirmDialog(this,
            panel,
            "Sửa thể loại",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String tenMoi = txtInput.getText().trim();
            if (tenMoi.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Tên thể loại không được để trống!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tenMoi.equals(tenCu)) {
                JOptionPane.showMessageDialog(this,
                    "Tên thể loại không thay đổi!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            try {
                theLoai tl = new theLoai();
                tl.setMaTheLoai(ma);
                tl.setTenTheLoai(tenMoi);
                boolean ok = theLoaiCtrl.sua(tl);
                if (ok) {
                    JOptionPane.showMessageDialog(this,
                        "Sửa thể loại thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadDuLieu();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Sửa thể loại thất bại!",
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

    private void xoaTheLoai() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn thể loại để xóa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int ma = (int) model.getValueAt(row, 0);
        String ten = (String) model.getValueAt(row, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "XÓA THỂ LOẠI\n\n" +
            "Bạn có chắc chắn muốn xóa thể loại:\n" +
            "\"" + ten + "\"?\n\n" +
            "Cảnh báo: Không thể xóa nếu đang có sách sử dụng thể loại này!",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean ok = theLoaiCtrl.xoa(ma);
                if (ok) {
                    JOptionPane.showMessageDialog(this,
                        "Xóa thể loại thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadDuLieu();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Xóa thể loại thất bại!",
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

