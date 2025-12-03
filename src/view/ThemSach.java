package view;

import controller.SachController;
import controller.TacGiaController;
import controller.TheLoaiController;
import model.sach;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ThemSach extends JDialog {

    private JTextField txtTenSach, txtNamXB, txtNhaXB, txtSoLuong;
    private JComboBox<Integer> cbTacGia, cbTheLoai;
    private JLabel lblErrorTenSach, lblErrorSoLuong, lblErrorNamXB;
    private QuanLySach parent;

    public ThemSach(QuanLySach parent) {
        super(parent, "THÊM SÁCH MỚI", true);
        this.parent = parent;
        setSize(600, 650);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 247, 250));
        
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout(15, 15));

        // ==================== HEADER ====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(new Color(46, 204, 113));
        
        JLabel lblTitle = new JLabel("THÊM SÁCH MỚI", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // ==================== FORM PANEL ====================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Thông tin sách"),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 8, 12, 8);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Tên sách
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblTenSach = new JLabel("Tên sách:");
        lblTenSach.setFont(labelFont);
        formPanel.add(lblTenSach, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtTenSach = new JTextField();
        txtTenSach.setFont(fieldFont);
        txtTenSach.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtTenSach.setToolTipText("Nhập tên sách (bắt buộc)");
        formPanel.add(txtTenSach, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorTenSach = new JLabel("");
        lblErrorTenSach.setForeground(Color.RED);
        lblErrorTenSach.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorTenSach, gbc);

        // Tác giả
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblTacGia = new JLabel("Tác giả:");
        lblTacGia.setFont(labelFont);
        formPanel.add(lblTacGia, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cbTacGia = new JComboBox<>();
        cbTacGia.setFont(fieldFont);
        cbTacGia.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        cbTacGia.setToolTipText("Chọn tác giả từ danh sách");
        loadComboTacGia();
        formPanel.add(cbTacGia, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel(), gbc);

        // Thể loại
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblTheLoai = new JLabel("Thể loại:");
        lblTheLoai.setFont(labelFont);
        formPanel.add(lblTheLoai, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cbTheLoai = new JComboBox<>();
        cbTheLoai.setFont(fieldFont);
        cbTheLoai.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        cbTheLoai.setToolTipText("Chọn thể loại từ danh sách");
        loadComboTheLoai();
        formPanel.add(cbTheLoai, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel(), gbc);

        // Năm XB
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblNamXB = new JLabel("Năm xuất bản:");
        lblNamXB.setFont(labelFont);
        formPanel.add(lblNamXB, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNamXB = new JTextField();
        txtNamXB.setFont(fieldFont);
        txtNamXB.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtNamXB.setToolTipText("Nhập năm xuất bản (ví dụ: 2024) - Không bắt buộc");
        formPanel.add(txtNamXB, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorNamXB = new JLabel("");
        lblErrorNamXB.setForeground(Color.RED);
        lblErrorNamXB.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorNamXB, gbc);

        // Nhà XB
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblNhaXB = new JLabel("Nhà xuất bản:");
        lblNhaXB.setFont(labelFont);
        formPanel.add(lblNhaXB, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNhaXB = new JTextField();
        txtNhaXB.setFont(fieldFont);
        txtNhaXB.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtNhaXB.setToolTipText("Nhập tên nhà xuất bản - Không bắt buộc");
        formPanel.add(txtNhaXB, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel(), gbc);

        // Số lượng
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setFont(labelFont);
        formPanel.add(lblSoLuong, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtSoLuong = new JTextField();
        txtSoLuong.setFont(fieldFont);
        txtSoLuong.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtSoLuong.setToolTipText("Nhập số lượng sách (phải > 0)");
        formPanel.add(txtSoLuong, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorSoLuong = new JLabel("");
        lblErrorSoLuong.setForeground(Color.RED);
        lblErrorSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorSoLuong, gbc);

        // Validation realtime
        txtTenSach.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                validateTenSach();
            }
        });
        
        txtSoLuong.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                validateSoLuong();
            }
        });
        
        txtNamXB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                validateNamXB();
            }
        });

        add(formPanel, BorderLayout.CENTER);

        // ==================== HƯỚNG DẪN ====================
        JLabel lblHuongDan = new JLabel(
            "<html><b>Lưu ý:</b> Các trường có dấu <font color='red'>*</font> là bắt buộc. Kiểm tra lại thông tin trước khi lưu.</html>");
        lblHuongDan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHuongDan.setForeground(new Color(127, 140, 141));
        lblHuongDan.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        lblHuongDan.setBackground(new Color(240, 248, 255));
        lblHuongDan.setOpaque(true);

        // ==================== PANEL NÚT ====================
        JPanel pNut = new JPanel(new BorderLayout(10, 10));
        pNut.setBackground(new Color(245, 247, 250));
        pNut.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));
        
        pNut.add(lblHuongDan, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        buttonPanel.setBackground(new Color(245, 247, 250));
        
        JButton btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnHuy.setBackground(new Color(149, 165, 166));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setPreferredSize(new Dimension(110, 38));
        btnHuy.setFocusPainted(false);
        btnHuy.setToolTipText("Đóng form mà không lưu");
        btnHuy.addActionListener(e -> dispose());
        
        JButton btnSave = new JButton("THÊM SÁCH");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(new Color(46, 204, 113));
        btnSave.setForeground(Color.WHITE);
        btnSave.setPreferredSize(new Dimension(150, 38));
        btnSave.setFocusPainted(false);
        btnSave.setToolTipText("Lưu thông tin sách mới");
        btnSave.addActionListener(e -> luu());
        
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnSave);
        pNut.add(buttonPanel, BorderLayout.CENTER);
        
        add(pNut, BorderLayout.SOUTH);
    }

    private boolean validateTenSach() {
        String ten = txtTenSach.getText().trim();
        if (ten.isEmpty()) {
            lblErrorTenSach.setText("Bắt buộc");
            txtTenSach.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return false;
        } else {
            lblErrorTenSach.setText("");
            txtTenSach.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true;
        }
    }

    private boolean validateSoLuong() {
        String sl = txtSoLuong.getText().trim();
        if (sl.isEmpty()) {
            lblErrorSoLuong.setText("Bắt buộc");
            txtSoLuong.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return false;
        }
        try {
            int soLuong = Integer.parseInt(sl);
            if (soLuong <= 0) {
                lblErrorSoLuong.setText("Phải > 0");
                txtSoLuong.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.RED, 2),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
                return false;
            } else {
                lblErrorSoLuong.setText("");
                txtSoLuong.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
                return true;
            }
        } catch (NumberFormatException e) {
            lblErrorSoLuong.setText("Số không hợp lệ");
            txtSoLuong.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return false;
        }
    }

    private boolean validateNamXB() {
        String nam = txtNamXB.getText().trim();
        if (nam.isEmpty()) {
            lblErrorNamXB.setText("");
            txtNamXB.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true; // Năm XB không bắt buộc
        }
        try {
            int namInt = Integer.parseInt(nam);
            int namHienTai = java.time.Year.now().getValue();
            if (namInt < 1000 || namInt > namHienTai + 1) {
                lblErrorNamXB.setText("Năm không hợp lệ");
                txtNamXB.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.RED, 2),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
                return false;
            } else {
                lblErrorNamXB.setText("");
                txtNamXB.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
                return true;
            }
        } catch (NumberFormatException e) {
            lblErrorNamXB.setText("Số không hợp lệ");
            txtNamXB.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return false;
        }
    }

    private void luu() {
        // Validate tất cả
        boolean valid = true;
        if (!validateTenSach()) valid = false;
        if (!validateSoLuong()) valid = false;
        if (!validateNamXB()) valid = false;

        if (!valid) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng kiểm tra lại các trường có lỗi (màu đỏ)", 
                "Dữ liệu không hợp lệ", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            sach s = new sach();
            s.setTenSach(txtTenSach.getText().trim());
            s.setMaTacGia((Integer) cbTacGia.getSelectedItem());
            s.setMaTheLoai((Integer) cbTheLoai.getSelectedItem());

            String nam = txtNamXB.getText().trim();
            s.setNamXuatBan(nam.isEmpty() ? null : Integer.parseInt(nam));

            s.setNhaXuatBan(txtNhaXB.getText().trim());

            int sl = Integer.parseInt(txtSoLuong.getText().trim());
            s.setSoLuongTong(sl);
            s.setSoLuongHienTai(sl);

            if (new SachController().them(s)) {
                JOptionPane.showMessageDialog(this, 
                    "Thêm sách thành công!", 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
                parent.refresh();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Thêm sách thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadComboTacGia() {
        HashMap<Integer, String> map = new TacGiaController().layTatCaMap();
        for (Integer id : map.keySet()) {
            cbTacGia.addItem(id);
        }
        cbTacGia.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Integer) {
                    setText(map.get(value));
                }
                return this;
            }
        });
    }

    private void loadComboTheLoai() {
        HashMap<Integer, String> map = new TheLoaiController().layTatCaMap();
        for (Integer id : map.keySet()) {
            cbTheLoai.addItem(id);
        }
        cbTheLoai.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Integer) {
                    setText(map.get(value));
                }
                return this;
            }
        });
    }
}
