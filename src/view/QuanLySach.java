package view;

import client.SachClientController;
import model.sach;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class QuanLySach extends JFrame {

    private SachClientController sachCtrl = new SachClientController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtTimKiem;
    private JLabel status;
    private MainForm mainForm;

    public QuanLySach(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("QUẢN LÝ SÁCH");
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
        // ==================== HEADER ====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(new Color(0, 102, 204));

        JLabel lblTitle = new JLabel("QUẢN LÝ SÁCH", SwingConstants.LEFT);
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
        JPanel actionPanel = new JPanel(new BorderLayout(10, 10));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        actionPanel.setBackground(Color.WHITE);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);

        JLabel lblTim = new JLabel("Tìm kiếm sách:");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchPanel.add(lblTim);

        txtTimKiem = new JTextField(35);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        txtTimKiem.setToolTipText("Nhập tên sách, tác giả hoặc thể loại để tìm kiếm");
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                timKiemSach();
            }
        });
        searchPanel.add(txtTimKiem);

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
        searchPanel.add(btnXoaTim);

        // Panel nút thao tác
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnThem = createButton("Thêm mới", new Color(46, 204, 113));
        btnThem.setToolTipText("Thêm sách mới vào thư viện");
        JButton btnSua = createButton("Sửa", new Color(0, 102, 204));
        btnSua.setToolTipText("Sửa thông tin sách đã chọn (hoặc double-click vào dòng)");
        JButton btnXoa = createButton("Xóa", new Color(231, 76, 60));
        btnXoa.setToolTipText("Xóa sách đã chọn");
        JButton btnLamMoi = createButton("Làm mới", new Color(52, 152, 219));
        btnLamMoi.setToolTipText("Tải lại danh sách sách");

        btnThem.addActionListener(e -> themSach());
        btnSua.addActionListener(e -> suaSach());
        btnXoa.addActionListener(e -> xoaSach());
        btnLamMoi.addActionListener(e -> loadDuLieu());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLamMoi);

        actionPanel.add(searchPanel, BorderLayout.WEST);
        actionPanel.add(buttonPanel, BorderLayout.EAST);
        add(actionPanel, BorderLayout.NORTH);

        // ==================== BẢNG SÁCH ====================
        String[] cols = { "Mã sách", "Tên sách", "Tác giả", "Thể loại", "Năm XB", "Tổng", "Còn lại", "Trạng thái" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setReorderingAllowed(false);

        // Double-click để sửa
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    suaSach();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Danh sách sách trong thư viện"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        add(scroll, BorderLayout.CENTER);

        // ==================== PANEL THÔNG TIN VÀ HƯỚNG DẪN ====================
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bottomPanel.setBackground(new Color(245, 247, 250));

        // Status bar
        status = new JLabel("Đang tải dữ liệu...", SwingConstants.LEFT);
        status.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        status.setBackground(new Color(240, 248, 255));
        status.setForeground(new Color(127, 140, 141));
        status.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        status.setOpaque(true);
        bottomPanel.add(status, BorderLayout.WEST);

        // Hướng dẫn
        JLabel lblHuongDan = new JLabel(
                "<html><b>Hướng dẫn:</b> Double-click vào dòng để sửa | Chọn dòng và nhấn nút để thao tác</html>");
        lblHuongDan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHuongDan.setForeground(new Color(127, 140, 141));
        bottomPanel.add(lblHuongDan, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(120, 38));
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
        SwingUtilities.invokeLater(() -> {
            try {
                model.setRowCount(0);
                List<sach> ds = sachCtrl.layTatCaSach();
                int tongConLai = 0;
                int tongSach = 0;
                for (sach s : ds) {
                    tongConLai += s.getSoLuongHienTai();
                    tongSach += s.getSoLuongTong();
                    model.addRow(new Object[] {
                            s.getMaSach(),
                            s.getTenSach(),
                            s.getTenTacGia() != null ? s.getTenTacGia() : "Chưa rõ",
                            s.getTenTheLoai() != null ? s.getTenTheLoai() : "Chưa rõ",
                            s.getNamXuatBan() != null ? s.getNamXuatBan() : "",
                            s.getSoLuongTong(),
                            s.getSoLuongHienTai(),
                            s.getTrangThai() != null ? s.getTrangThai() : "Bình thường"
                    });
                }
                updateStatus("Tổng: " + model.getRowCount() + " đầu sách | Tổng số lượng: " + tongSach
                        + " cuốn | Còn lại: " + tongConLai + " cuốn");
            } catch (Exception e) {
                e.printStackTrace();
                updateStatus("Lỗi khi tải dữ liệu!");
            }
        });
    }

    private void timKiemSach() {
        String keyword = txtTimKiem.getText().trim();
        model.setRowCount(0);
        List<sach> ds = sachCtrl.timKiemSach(keyword);
        int tongConLai = 0;
        int tongSach = 0;
        for (sach s : ds) {
            tongConLai += s.getSoLuongHienTai();
            tongSach += s.getSoLuongTong();
            model.addRow(new Object[] {
                    s.getMaSach(),
                    s.getTenSach(),
                    s.getTenTacGia() != null ? s.getTenTacGia() : "",
                    s.getTenTheLoai() != null ? s.getTenTheLoai() : "",
                    s.getNamXuatBan() != null ? s.getNamXuatBan() : "",
                    s.getSoLuongTong(),
                    s.getSoLuongHienTai(),
                    s.getTrangThai()
            });
        }
        if (keyword.isEmpty()) {
            updateStatus("Tổng: " + model.getRowCount() + " đầu sách | Tổng số lượng: " + tongSach + " cuốn | Còn lại: "
                    + tongConLai + " cuốn");
        } else {
            updateStatus("Tìm thấy: " + model.getRowCount() + " đầu sách | Tổng số lượng: " + tongSach
                    + " cuốn | Còn lại: " + tongConLai + " cuốn");
        }
    }

    private void updateStatus(String text) {
        if (status != null) {
            status.setText(text);
        }
    }

    private void themSach() {
        new ThemSach(this).setVisible(true);
    }

    private void suaSach() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn sách để sửa!\n\nGợi ý: Click vào dòng trong bảng hoặc double-click để sửa nhanh.",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int maSach = (Integer) model.getValueAt(row, 0);
        String tenSach = (String) model.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn muốn sửa thông tin sách:\n\"" + tenSach + "\"?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            new SuaSach(maSach, this).setVisible(true);
        }
    }

    private void xoaSach() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn sách để xóa!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int maSach = (Integer) model.getValueAt(row, 0);
        String tenSach = (String) model.getValueAt(row, 1);
        int soLuongHienTai = (Integer) model.getValueAt(row, 6);

        String message = "XÓA SÁCH\n\n";
        message += "Bạn có chắc chắn muốn xóa sách:\n";
        message += "\"" + tenSach + "\"?\n\n";
        message += "Thông tin:\n";
        message += "- Mã sách: " + maSach + "\n";
        message += "- Số lượng còn lại: " + soLuongHienTai + " cuốn\n\n";
        message += "CẢNH BÁO: Tất cả phiếu mượn liên quan sẽ bị ảnh hưởng!";

        int confirm = JOptionPane.showConfirmDialog(this, message,
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = sachCtrl.xoa(maSach);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                        "Xóa sách thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadDuLieu();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không thể xóa sách!\n\n" +
                                "Nguyên nhân có thể:\n" +
                                "- Sách đang được mượn\n" +
                                "- Có lỗi xảy ra trong hệ thống\n\n" +
                                "Vui lòng kiểm tra lại.",
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
        SwingUtilities.invokeLater(() -> new QuanLySach(null).setVisible(true));
    }
}
