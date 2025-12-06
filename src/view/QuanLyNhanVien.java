package view;

import client.AuthClientController;
import model.nhanVien;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class QuanLyNhanVien extends JFrame {
    private AuthClientController authCtrl = new AuthClientController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtTimKiem;
    private MainForm mainForm;

    public QuanLyNhanVien(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("QUẢN LÝ NHÂN VIÊN");
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
        headerPanel.setBackground(new Color(155, 89, 182));

        JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN (THỦ THƯ)", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JButton btnQuayVe = createButton("Quay lại", new Color(100, 100, 100));
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
        JLabel lblTim = new JLabel("Tìm kiếm nhân viên:");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pTim.add(lblTim);
        txtTimKiem = new JTextField(25);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtTimKiem.setToolTipText("Nhập tên hoặc tài khoản để tìm kiếm");
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
        btnThem.setToolTipText("Thêm nhân viên mới");
        JButton btnSua = createButton("Sửa", new Color(0, 102, 204));
        btnSua.setToolTipText("Sửa thông tin nhân viên đã chọn");
        JButton btnXoa = createButton("Xóa", new Color(231, 76, 60));
        btnXoa.setToolTipText("Xóa nhân viên đã chọn");
        JButton btnLamMoi = createButton("Làm mới", new Color(52, 152, 219));
        btnLamMoi.setToolTipText("Tải lại danh sách");

        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
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

        // ==================== BẢNG NHÂN VIÊN ====================
        String[] cols = {"Mã NV", "Họ tên", "SĐT", "Vai trò", "Tài khoản"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setBackground(new Color(155, 89, 182));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(155, 89, 182), 2),
                "Danh sách nhân viên",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(155, 89, 182)
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        add(scroll, BorderLayout.CENTER);

        // ==================== STATUS BAR ====================
        JLabel status = new JLabel("Sẵn sàng", SwingConstants.LEFT);
        status.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        status.setBackground(new Color(240, 248, 255));
        status.setForeground(new Color(127, 140, 141));
        status.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        status.setOpaque(true);
        add(status, BorderLayout.SOUTH);
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
        List<nhanVien> ds = authCtrl.getAllNhanVien();

        for (nhanVien nv : ds) {
            model.addRow(new Object[] {
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getSdt() != null ? nv.getSdt() : "",
                    nv.getVaiTro() != null ? nv.getVaiTro() : "",
                    nv.getTaiKhoan() != null ? nv.getTaiKhoan() : ""
            });
        }

        // Reset filter
        txtTimKiem.setText("");
        
        // Cập nhật status
        JLabel status = (JLabel) ((BorderLayout) getContentPane().getLayout()).getLayoutComponent(BorderLayout.SOUTH);
        if (status != null) {
            status.setText("Tổng số: " + ds.size() + " nhân viên");
        }
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        model.setRowCount(0);
        List<nhanVien> ds = authCtrl.getAllNhanVien();

        for (nhanVien nv : ds) {
            // Tìm kiếm
            if (!keyword.isEmpty()) {
                boolean match = false;
                if (nv.getHoTen() != null && nv.getHoTen().toLowerCase().contains(keyword))
                    match = true;
                if (nv.getTaiKhoan() != null && nv.getTaiKhoan().toLowerCase().contains(keyword))
                    match = true;
                if (!match)
                    continue;
            }

            model.addRow(new Object[] {
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getSdt() != null ? nv.getSdt() : "",
                    nv.getVaiTro() != null ? nv.getVaiTro() : "",
                    nv.getTaiKhoan() != null ? nv.getTaiKhoan() : ""
            });
        }

        // Cập nhật status
        JLabel status = (JLabel) ((BorderLayout) getContentPane().getLayout()).getLayoutComponent(BorderLayout.SOUTH);
        if (status != null) {
            status.setText("Tìm thấy " + model.getRowCount() + " kết quả");
        }
    }

    private void themNhanVien() {
        ThemNhanVien dialog = new ThemNhanVien(this);
        dialog.setVisible(true);
        loadDuLieu();
    }

    private void suaNhanVien() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn nhân viên để sửa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int maNV = (Integer) model.getValueAt(row, 0);
        nhanVien nv = authCtrl.layTheoMa(maNV);
        if (nv == null) {
            JOptionPane.showMessageDialog(this,
                "Không tìm thấy nhân viên!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        SuaNhanVien dialog = new SuaNhanVien(this, nv);
        dialog.setVisible(true);
        loadDuLieu();
    }

    private void xoaNhanVien() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn nhân viên để xóa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int maNV = (Integer) model.getValueAt(row, 0);
        String tenNV = (String) model.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa nhân viên:\n" + tenNV + "?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean ok = authCtrl.xoa(maNV);
                if (ok) {
                    JOptionPane.showMessageDialog(this,
                        "Xóa nhân viên thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadDuLieu();
                }
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyNhanVien(null).setVisible(true));
    }
}

