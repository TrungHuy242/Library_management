package view;

import client.BanDocClientController;
import model.banDoc;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class QuanLyDocGia extends JFrame {

    private BanDocClientController bdCtrl = new BanDocClientController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtTimKiem;
    private MainForm mainForm;

    public QuanLyDocGia(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("QUẢN LÝ ĐỘC GIẢ");
        setSize(1300, 800);
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
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(new Color(46, 204, 113));
        
        JLabel lblTitle = new JLabel("QUẢN LÝ ĐỘC GIẢ", SwingConstants.LEFT);
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

        // Thanh tìm kiếm + nút
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
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                timKiem();
            }
        });
        pTim.add(txtTimKiem);

        JPanel pNut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pNut.setBackground(Color.WHITE);
        
        JButton btnThem = createButton("Thêm mới", new Color(46, 204, 113));
        btnThem.setToolTipText("Thêm độc giả mới vào hệ thống");
        JButton btnSua = createButton("Sửa", new Color(0, 102, 204));
        btnSua.setToolTipText("Sửa thông tin độc giả đã chọn");
        JButton btnXoa = createButton("Xóa", new Color(231, 76, 60));
        btnXoa.setToolTipText("Xóa độc giả đã chọn");
        JButton btnLamMoi = createButton("Làm mới", new Color(52, 152, 219));
        btnLamMoi.setToolTipText("Tải lại danh sách độc giả");

        btnThem.addActionListener(e -> themDocGia());
        btnSua.addActionListener(e -> suaDocGia());
        btnXoa.addActionListener(e -> xoaDocGia());
        btnLamMoi.addActionListener(e -> loadDuLieu());

        pNut.add(btnThem);
        pNut.add(btnSua);
        pNut.add(btnXoa);
        pNut.add(btnLamMoi);

        top.add(pTim, BorderLayout.WEST);
        top.add(pNut, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // Bảng độc giả
        String[] cols = { "Mã ĐG", "Họ tên", "Lớp", "SĐT", "Email", "Ngày sinh", "Ngày ĐK" };
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

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách độc giả"));
        add(scroll, BorderLayout.CENTER);

        // Status bar
        JLabel lblStatus = new JLabel("Đang tải dữ liệu...", JLabel.LEFT);
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        lblStatus.setBackground(new Color(240, 248, 255));
        lblStatus.setForeground(new Color(127, 140, 141));
        lblStatus.setOpaque(true);
        add(lblStatus, BorderLayout.SOUTH);
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
        List<banDoc> ds = bdCtrl.layTatCa();
        for (banDoc bd : ds) {
            model.addRow(new Object[] {
                    bd.getMaBanDoc(),
                    bd.getHoTen(),
                    bd.getLop(),
                    bd.getSdt(),
                    bd.getEmail(),
                    bd.getNgaySinh() != null ? bd.getNgaySinh().toString() : "",
                    bd.getNgayDangKy() != null ? bd.getNgayDangKy().toString() : ""
            });
        }
        updateStatus("Tổng cộng: " + model.getRowCount() + " độc giả");
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        model.setRowCount(0);
        List<banDoc> ds = bdCtrl.timKiem(keyword);
        for (banDoc bd : ds) {
            model.addRow(new Object[] {
                    bd.getMaBanDoc(),
                    bd.getHoTen(),
                    bd.getLop(),
                    bd.getSdt(),
                    bd.getEmail(),
                    bd.getNgaySinh() != null ? bd.getNgaySinh().toString() : "",
                    bd.getNgayDangKy() != null ? bd.getNgayDangKy().toString() : ""
            });
        }
        if (keyword.isEmpty()) {
            updateStatus("Tổng cộng: " + model.getRowCount() + " độc giả");
        } else {
            updateStatus("Tìm thấy: " + model.getRowCount() + " độc giả");
        }
    }

    private void updateStatus(String text) {
        JLabel lblStatus = (JLabel) ((BorderLayout) getContentPane().getLayout())
                .getLayoutComponent(BorderLayout.SOUTH);
        if (lblStatus != null) {
            lblStatus.setText(text);
        }
    }

    private void themDocGia() {
        new ThemDocGia(this).setVisible(true);
    }

    private void suaDocGia() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn độc giả để sửa!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int ma = (int) model.getValueAt(row, 0);
        new SuaDocGia(ma, this).setVisible(true);
    }

    private void xoaDocGia() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn độc giả để xóa!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int ma = (int) model.getValueAt(row, 0);
        String hoTen = (String) model.getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "XÓA ĐỘC GIẢ\n\n" +
            "Bạn có chắc chắn muốn xóa độc giả:\n" +
            "\"" + hoTen + "\"?\n\n" +
            "Cảnh báo: Không thể khôi phục sau khi xóa!", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = bdCtrl.xoa(ma);
            if (ok) {
                JOptionPane.showMessageDialog(this, 
                    "Xóa thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadDuLieu();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Xóa thất bại! (Có thể độc giả đang mượn sách hoặc có lỗi xảy ra)", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            mainForm.capNhatCanhBao();
        }
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyDocGia(null).setVisible(true));
    }
}
