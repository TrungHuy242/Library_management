package view;

import client.BanDocClientController;
import model.banDoc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ThemDocGia extends JDialog {
    private JTextField txtHoTen, txtLop, txtSdt, txtEmail, txtNgaySinh, txtGhiChu;
    private JLabel lblErrorHoTen, lblErrorSdt, lblErrorEmail, lblErrorNgaySinh;
    private QuanLyDocGia parent;

    public ThemDocGia(QuanLyDocGia parent) {
        super(parent, "THÊM ĐỘC GIẢ MỚI", true);
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
        
        JLabel lblTitle = new JLabel("THÊM ĐỘC GIẢ MỚI", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // ==================== FORM PANEL ====================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Thông tin độc giả"),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 8, 12, 8);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Họ tên
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setFont(labelFont);
        formPanel.add(lblHoTen, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtHoTen = new JTextField();
        txtHoTen.setFont(fieldFont);
        txtHoTen.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtHoTen.setToolTipText("Nhập họ tên độc giả (bắt buộc)");
        formPanel.add(txtHoTen, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorHoTen = new JLabel("");
        lblErrorHoTen.setForeground(Color.RED);
        lblErrorHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorHoTen, gbc);

        // Lớp
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblLop = new JLabel("Lớp:");
        lblLop.setFont(labelFont);
        formPanel.add(lblLop, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtLop = new JTextField();
        txtLop.setFont(fieldFont);
        txtLop.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtLop.setToolTipText("Nhập lớp học - Không bắt buộc");
        formPanel.add(txtLop, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel(), gbc);

        // SĐT
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblSdt = new JLabel("Số điện thoại:");
        lblSdt.setFont(labelFont);
        formPanel.add(lblSdt, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtSdt = new JTextField();
        txtSdt.setFont(fieldFont);
        txtSdt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtSdt.setToolTipText("Nhập số điện thoại (10 chữ số) - Không bắt buộc");
        formPanel.add(txtSdt, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorSdt = new JLabel("");
        lblErrorSdt.setForeground(Color.RED);
        lblErrorSdt.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorSdt, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        formPanel.add(lblEmail, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtEmail = new JTextField();
        txtEmail.setFont(fieldFont);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtEmail.setToolTipText("Nhập email (ví dụ: user@example.com) - Không bắt buộc");
        formPanel.add(txtEmail, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorEmail = new JLabel("");
        lblErrorEmail.setForeground(Color.RED);
        lblErrorEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorEmail, gbc);

        // Ngày sinh
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        lblNgaySinh.setFont(labelFont);
        formPanel.add(lblNgaySinh, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNgaySinh = new JTextField();
        txtNgaySinh.setFont(fieldFont);
        txtNgaySinh.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtNgaySinh.setToolTipText("Nhập ngày sinh (yyyy-mm-dd, ví dụ: 2000-01-15) - Không bắt buộc");
        formPanel.add(txtNgaySinh, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorNgaySinh = new JLabel("");
        lblErrorNgaySinh.setForeground(Color.RED);
        lblErrorNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorNgaySinh, gbc);

        // Ghi chú
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblGhiChu = new JLabel("Ghi chú:");
        lblGhiChu.setFont(labelFont);
        formPanel.add(lblGhiChu, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtGhiChu = new JTextField();
        txtGhiChu.setFont(fieldFont);
        txtGhiChu.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtGhiChu.setToolTipText("Ghi chú thêm (tùy chọn)");
        formPanel.add(txtGhiChu, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel(), gbc);

        // Validation realtime
        txtHoTen.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validateHoTen();
            }
        });
        
        txtSdt.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validateSdt();
            }
        });
        
        txtEmail.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validateEmail();
            }
        });
        
        txtNgaySinh.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validateNgaySinh();
            }
        });

        add(formPanel, BorderLayout.CENTER);

        // ==================== HƯỚNG DẪN ====================
        JLabel lblHuongDan = new JLabel(
            "<html><b>Lưu ý:</b> Trường <font color='red'>Họ tên</font> là bắt buộc. Các trường khác có thể để trống.</html>");
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
        
        JButton btnLuu = new JButton("THÊM ĐỘC GIẢ");
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setBackground(new Color(46, 204, 113));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setPreferredSize(new Dimension(160, 38));
        btnLuu.setFocusPainted(false);
        btnLuu.setToolTipText("Lưu thông tin độc giả mới");
        btnLuu.addActionListener(e -> luu());
        
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnLuu);
        pNut.add(buttonPanel, BorderLayout.CENTER);
        
        add(pNut, BorderLayout.SOUTH);
    }

    private boolean validateHoTen() {
        String hoTen = txtHoTen.getText().trim();
        if (hoTen.isEmpty()) {
            lblErrorHoTen.setText("Bắt buộc");
            txtHoTen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return false;
        } else {
            lblErrorHoTen.setText("");
            txtHoTen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true;
        }
    }

    private boolean validateSdt() {
        String sdt = txtSdt.getText().trim();
        if (sdt.isEmpty()) {
            lblErrorSdt.setText("");
            txtSdt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true; // SĐT không bắt buộc
        }
        // Kiểm tra chỉ chứa số và độ dài 10
        if (!sdt.matches("^[0-9]{10}$")) {
            lblErrorSdt.setText("10 chữ số");
            txtSdt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return false;
        } else {
            lblErrorSdt.setText("");
            txtSdt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true;
        }
    }

    private boolean validateEmail() {
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            lblErrorEmail.setText("");
            txtEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true; // Email không bắt buộc
        }
        // Kiểm tra format email
        if (!email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$")) {
            lblErrorEmail.setText("Email không hợp lệ");
            txtEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return false;
        } else {
            lblErrorEmail.setText("");
            txtEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true;
        }
    }

    private boolean validateNgaySinh() {
        String ngaySinh = txtNgaySinh.getText().trim();
        if (ngaySinh.isEmpty()) {
            lblErrorNgaySinh.setText("");
            txtNgaySinh.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true; // Ngày sinh không bắt buộc
        }
        try {
            java.sql.Date.valueOf(ngaySinh);
            lblErrorNgaySinh.setText("");
            txtNgaySinh.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true;
        } catch (IllegalArgumentException e) {
            lblErrorNgaySinh.setText("yyyy-mm-dd");
            txtNgaySinh.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return false;
        }
    }

    private void luu() {
        // Validate tất cả
        boolean valid = true;
        if (!validateHoTen()) valid = false;
        if (!validateSdt()) valid = false;
        if (!validateEmail()) valid = false;
        if (!validateNgaySinh()) valid = false;

        if (!valid) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng kiểm tra lại các trường có lỗi (màu đỏ)", 
                "Dữ liệu không hợp lệ", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            banDoc bd = new banDoc();
            bd.setHoTen(txtHoTen.getText().trim());
            bd.setLop(txtLop.getText().trim());
            bd.setSdt(txtSdt.getText().trim());
            bd.setEmail(txtEmail.getText().trim());
            
            String ngaySinh = txtNgaySinh.getText().trim();
            if (!ngaySinh.isEmpty()) {
                bd.setNgaySinh(java.sql.Date.valueOf(ngaySinh));
            }

            boolean ok = new BanDocClientController().them(bd);
            if (ok) {
                JOptionPane.showMessageDialog(this, 
                    "Thêm độc giả thành công!", 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
                parent.refresh();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Thêm độc giả thất bại!", 
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
}
