package view;

import controller.AuthController;
import model.nhanVien;
import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class SuaNhanVien extends JDialog {
    private JTextField txtHoTen, txtSdt, txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cmbVaiTro;
    private JLabel lblErrorHoTen, lblErrorSdt, lblErrorTaiKhoan, lblErrorMatKhau;
    private QuanLyNhanVien parent;
    private AuthController authCtrl = new AuthController();
    private nhanVien nv;

    public SuaNhanVien(QuanLyNhanVien parent, nhanVien nv) {
        super(parent, "SỬA THÔNG TIN NHÂN VIÊN", true);
        this.parent = parent;
        this.nv = nv;
        setSize(600, 550);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 247, 250));
        initGUI();
        loadDuLieu();
    }

    private void loadDuLieu() {
        txtHoTen.setText(nv.getHoTen() != null ? nv.getHoTen() : "");
        txtSdt.setText(nv.getSdt() != null ? nv.getSdt() : "");
        txtTaiKhoan.setText(nv.getTaiKhoan() != null ? nv.getTaiKhoan() : "");
        txtMatKhau.setText(nv.getMatKhau() != null ? nv.getMatKhau() : "");
        
        if (nv.getVaiTro() != null) {
            if ("Thủ thư".equals(nv.getVaiTro())) {
                cmbVaiTro.setSelectedIndex(0);
            } else if ("Quản lý".equals(nv.getVaiTro())) {
                cmbVaiTro.setSelectedIndex(1);
            }
        }
    }

    private void initGUI() {
        setLayout(new BorderLayout(15, 15));

        // ==================== HEADER ====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(new Color(0, 102, 204));
        
        JLabel lblTitle = new JLabel("SỬA THÔNG TIN NHÂN VIÊN", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // ==================== FORM PANEL ====================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Thông tin nhân viên"),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 8, 12, 8);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Mã NV (read-only)
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblMaNV = new JLabel("Mã NV:");
        lblMaNV.setFont(labelFont);
        formPanel.add(lblMaNV, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JLabel lblMaNVValue = new JLabel(String.valueOf(nv.getMaNV()));
        lblMaNVValue.setFont(fieldFont);
        lblMaNVValue.setForeground(new Color(127, 140, 141));
        formPanel.add(lblMaNVValue, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel(), gbc);

        // Họ tên
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
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
        txtHoTen.setToolTipText("Nhập họ tên nhân viên (bắt buộc)");
        formPanel.add(txtHoTen, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorHoTen = new JLabel("");
        lblErrorHoTen.setForeground(Color.RED);
        lblErrorHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorHoTen, gbc);

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
        txtSdt.setToolTipText("Nhập số điện thoại (bắt buộc)");
        formPanel.add(txtSdt, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorSdt = new JLabel("");
        lblErrorSdt.setForeground(Color.RED);
        lblErrorSdt.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorSdt, gbc);

        // Vai trò
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblVaiTro = new JLabel("Vai trò:");
        lblVaiTro.setFont(labelFont);
        formPanel.add(lblVaiTro, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cmbVaiTro = new JComboBox<>(new String[]{"Thủ thư", "Quản lý"});
        cmbVaiTro.setFont(fieldFont);
        formPanel.add(cmbVaiTro, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel(), gbc);

        // Tài khoản
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblTaiKhoan = new JLabel("Tài khoản:");
        lblTaiKhoan.setFont(labelFont);
        formPanel.add(lblTaiKhoan, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtTaiKhoan = new JTextField();
        txtTaiKhoan.setFont(fieldFont);
        txtTaiKhoan.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtTaiKhoan.setToolTipText("Nhập tài khoản đăng nhập (bắt buộc)");
        formPanel.add(txtTaiKhoan, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorTaiKhoan = new JLabel("");
        lblErrorTaiKhoan.setForeground(Color.RED);
        lblErrorTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorTaiKhoan, gbc);

        // Mật khẩu
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setFont(labelFont);
        formPanel.add(lblMatKhau, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtMatKhau = new JPasswordField();
        txtMatKhau.setFont(fieldFont);
        txtMatKhau.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtMatKhau.setToolTipText("Nhập mật khẩu mới (bắt buộc, tối thiểu 6 ký tự)");
        formPanel.add(txtMatKhau, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorMatKhau = new JLabel("");
        lblErrorMatKhau.setForeground(Color.RED);
        lblErrorMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorMatKhau, gbc);

        JScrollPane scroll = new JScrollPane(formPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        // ==================== BUTTON PANEL ====================
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton btnSua = new JButton("Lưu");
        btnSua.setBackground(new Color(0, 102, 204));
        btnSua.setForeground(Color.WHITE);
        btnSua.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSua.setPreferredSize(new Dimension(100, 40));
        btnSua.setFocusPainted(false);
        btnSua.addActionListener(e -> suaNhanVien());

        JButton btnHuy = new JButton("Hủy");
        btnHuy.setBackground(new Color(149, 165, 166));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnHuy.setPreferredSize(new Dimension(100, 40));
        btnHuy.setFocusPainted(false);
        btnHuy.addActionListener(e -> dispose());

        btnPanel.add(btnHuy);
        btnPanel.add(btnSua);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void suaNhanVien() {
        // Reset errors
        lblErrorHoTen.setText("");
        lblErrorSdt.setText("");
        lblErrorTaiKhoan.setText("");
        lblErrorMatKhau.setText("");

        // Validate
        boolean valid = true;
        String hoTen = txtHoTen.getText().trim();
        String sdt = txtSdt.getText().trim();
        String taiKhoan = txtTaiKhoan.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());

        if (hoTen.isEmpty()) {
            lblErrorHoTen.setText("Bắt buộc");
            valid = false;
        }

        if (sdt.isEmpty()) {
            lblErrorSdt.setText("Bắt buộc");
            valid = false;
        } else if (!Pattern.matches("^[0-9]{10,11}$", sdt)) {
            lblErrorSdt.setText("SĐT không hợp lệ");
            valid = false;
        }

        if (taiKhoan.isEmpty()) {
            lblErrorTaiKhoan.setText("Bắt buộc");
            valid = false;
        } else {
            // Kiểm tra tài khoản đã tồn tại (trừ chính nó)
            for (nhanVien n : authCtrl.getAllNhanVien()) {
                if (n.getTaiKhoan().equals(taiKhoan) && n.getMaNV() != nv.getMaNV()) {
                    lblErrorTaiKhoan.setText("Tài khoản đã tồn tại");
                    valid = false;
                    break;
                }
            }
        }

        if (matKhau.isEmpty()) {
            lblErrorMatKhau.setText("Bắt buộc");
            valid = false;
        } else if (matKhau.length() < 6) {
            lblErrorMatKhau.setText("Tối thiểu 6 ký tự");
            valid = false;
        }

        if (!valid) return;

        // Cập nhật nhân viên
        nv.setHoTen(hoTen);
        nv.setSdt(sdt);
        nv.setVaiTro((String) cmbVaiTro.getSelectedItem());
        nv.setTaiKhoan(taiKhoan);
        nv.setMatKhau(matKhau);

        try {
            boolean ok = authCtrl.sua(nv);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                    "Sửa thông tin nhân viên thành công!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Sửa thông tin nhân viên thất bại!",
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

