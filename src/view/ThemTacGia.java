package view;

import client.TacGiaClientController;
import model.tacGia;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ThemTacGia extends JDialog {
    private JTextField txtTenTacGia, txtNamSinh, txtQuocTich;
    private JLabel lblErrorTenTacGia, lblErrorNamSinh;
    private QuanLyTacGia parent;

    public ThemTacGia(QuanLyTacGia parent) {
        super(parent, "THÊM TÁC GIẢ MỚI", true);
        this.parent = parent;
        setSize(550, 400);
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

        JLabel lblTitle = new JLabel("THÊM TÁC GIẢ MỚI", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // ==================== FORM PANEL ====================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Thông tin tác giả"),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 8, 12, 8);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Tên tác giả
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblTenTacGia = new JLabel("Tên tác giả:");
        lblTenTacGia.setFont(labelFont);
        formPanel.add(lblTenTacGia, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtTenTacGia = new JTextField();
        txtTenTacGia.setFont(fieldFont);
        txtTenTacGia.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtTenTacGia.setToolTipText("Nhập tên tác giả (bắt buộc)");
        formPanel.add(txtTenTacGia, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorTenTacGia = new JLabel("");
        lblErrorTenTacGia.setForeground(Color.RED);
        lblErrorTenTacGia.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorTenTacGia, gbc);

        // Năm sinh
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblNamSinh = new JLabel("Năm sinh:");
        lblNamSinh.setFont(labelFont);
        formPanel.add(lblNamSinh, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNamSinh = new JTextField();
        txtNamSinh.setFont(fieldFont);
        txtNamSinh.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtNamSinh.setToolTipText("Nhập năm sinh (ví dụ: 1950) - Không bắt buộc");
        formPanel.add(txtNamSinh, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorNamSinh = new JLabel("");
        lblErrorNamSinh.setForeground(Color.RED);
        lblErrorNamSinh.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorNamSinh, gbc);

        // Quốc tịch
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblQuocTich = new JLabel("Quốc tịch:");
        lblQuocTich.setFont(labelFont);
        formPanel.add(lblQuocTich, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtQuocTich = new JTextField();
        txtQuocTich.setFont(fieldFont);
        txtQuocTich.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtQuocTich.setToolTipText("Nhập quốc tịch - Không bắt buộc");
        formPanel.add(txtQuocTich, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel(), gbc);

        // Validation
        txtTenTacGia.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validateTenTacGia();
            }
        });

        txtNamSinh.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validateNamSinh();
            }
        });

        add(formPanel, BorderLayout.CENTER);

        // ==================== HƯỚNG DẪN ====================
        JLabel lblHuongDan = new JLabel(
            "<html><b>Lưu ý:</b> Trường <font color='red'>Tên tác giả</font> là bắt buộc. Các trường khác có thể để trống.</html>");
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
        btnHuy.addActionListener(e -> dispose());

        JButton btnLuu = new JButton("THÊM TÁC GIẢ");
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setBackground(new Color(46, 204, 113));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setPreferredSize(new Dimension(150, 38));
        btnLuu.setFocusPainted(false);
        btnLuu.addActionListener(e -> luu());

        buttonPanel.add(btnHuy);
        buttonPanel.add(btnLuu);
        pNut.add(buttonPanel, BorderLayout.CENTER);

        add(pNut, BorderLayout.SOUTH);
    }

    private boolean validateTenTacGia() {
        String ten = txtTenTacGia.getText().trim();
        if (ten.isEmpty()) {
            lblErrorTenTacGia.setText("Bắt buộc");
            txtTenTacGia.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return false;
        } else {
            lblErrorTenTacGia.setText("");
            txtTenTacGia.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true;
        }
    }

    private boolean validateNamSinh() {
        String namSinh = txtNamSinh.getText().trim();
        if (namSinh.isEmpty()) {
            lblErrorNamSinh.setText("");
            txtNamSinh.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true; // Không bắt buộc
        }
        try {
            int nam = Integer.parseInt(namSinh);
            if (nam < 1000 || nam > 2100) {
                lblErrorNamSinh.setText("1000-2100");
                txtNamSinh.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.RED, 2),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
                return false;
            }
            lblErrorNamSinh.setText("");
            txtNamSinh.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return true;
        } catch (NumberFormatException e) {
            lblErrorNamSinh.setText("Số hợp lệ");
            txtNamSinh.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return false;
        }
    }

    private void luu() {
        boolean valid = true;
        if (!validateTenTacGia()) valid = false;
        if (!validateNamSinh()) valid = false;

        if (!valid) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng kiểm tra lại các trường có lỗi (màu đỏ)",
                "Dữ liệu không hợp lệ",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            tacGia tg = new tacGia();
            tg.setTenTacGia(txtTenTacGia.getText().trim());
            
            String namSinh = txtNamSinh.getText().trim();
            if (!namSinh.isEmpty()) {
                tg.setNamSinh(Integer.parseInt(namSinh));
            }
            
            tg.setQuocTich(txtQuocTich.getText().trim());

            boolean ok = new TacGiaClientController().them(tg);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                    "Thêm tác giả thành công!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
                parent.refresh();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Thêm tác giả thất bại!",
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

