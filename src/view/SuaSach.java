package view;

import client.SachClientController;
import client.TacGiaClientController;
import client.TheLoaiClientController;
import model.sach;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashMap;

public class SuaSach extends JDialog {

    private JTextField txtTenSach, txtNamXB, txtNhaXB, txtSoLuong;
    private JComboBox<Integer> cbTacGia, cbTheLoai;
    private JLabel lblErrorTenSach, lblErrorSoLuong, lblErrorNamXB;
    private int maSach;
    private QuanLySach parent;
    private HashMap<Integer, String> mapTacGia;
    private HashMap<Integer, String> mapTheLoai;

    public SuaSach(int maSach, QuanLySach parent) {
        super(parent, "SỬA THÔNG TIN SÁCH", true);
        this.maSach = maSach;
        this.parent = parent;
        setSize(600, 700);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 247, 250));
        
        initGUI();
        loadDuLieuHienTai();
    }

    private void initGUI() {
        setLayout(new BorderLayout(15, 15));

        // ==================== HEADER ====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(new Color(0, 102, 204));
        
        JLabel lblTitle = new JLabel("SỬA THÔNG TIN SÁCH", SwingConstants.LEFT);
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

        // Mã sách (read-only)
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblMa = new JLabel("Mã sách:");
        lblMa.setFont(labelFont);
        formPanel.add(lblMa, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JLabel lblMaValue = new JLabel(String.valueOf(maSach));
        lblMaValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblMaValue.setForeground(new Color(0, 102, 204));
        lblMaValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        lblMaValue.setOpaque(true);
        lblMaValue.setBackground(new Color(240, 248, 255));
        formPanel.add(lblMaValue, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel(), gbc);

        // Tên sách
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
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
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
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
        loadTacGia();
        formPanel.add(cbTacGia, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel(), gbc);

        // Thể loại
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
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
        loadTheLoai();
        formPanel.add(cbTheLoai, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel(), gbc);

        // Năm XB
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
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
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
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
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblSoLuong = new JLabel("Số lượng tổng:");
        lblSoLuong.setFont(labelFont);
        formPanel.add(lblSoLuong, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtSoLuong = new JTextField();
        txtSoLuong.setFont(fieldFont);
        txtSoLuong.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtSoLuong.setToolTipText("Nhập số lượng tổng (phải >= số lượng hiện tại)");
        formPanel.add(txtSoLuong, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        lblErrorSoLuong = new JLabel("");
        lblErrorSoLuong.setForeground(Color.RED);
        lblErrorSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(lblErrorSoLuong, gbc);

        // Validation realtime
        txtTenSach.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validateTenSach();
            }
        });
        
        txtSoLuong.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validateSoLuong();
            }
        });
        
        txtNamXB.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validateNamXB();
            }
        });

        add(formPanel, BorderLayout.CENTER);

        // ==================== HƯỚNG DẪN ====================
        JLabel lblHuongDan = new JLabel(
            "<html><b>Lưu ý:</b> Các trường có dấu <font color='red'>*</font> là bắt buộc. Số lượng tổng phải >= số lượng hiện tại.</html>");
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
        
        JButton btnLuu = new JButton("LƯU THAY ĐỔI");
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setBackground(new Color(0, 102, 204));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setPreferredSize(new Dimension(160, 38));
        btnLuu.setFocusPainted(false);
        btnLuu.setToolTipText("Lưu các thay đổi");
        btnLuu.addActionListener(e -> luuSua());
        
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnLuu);
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
            sach s = new SachClientController().layTheoMa(maSach);
            if (s != null && soLuong < s.getSoLuongHienTai()) {
                lblErrorSoLuong.setText(">= " + s.getSoLuongHienTai());
                txtSoLuong.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.RED, 2),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
                return false;
            } else if (soLuong <= 0) {
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

    private void loadTacGia() {
        mapTacGia = new TacGiaClientController().layTatCaMap();
        cbTacGia.addItem(null);
        for (Integer id : mapTacGia.keySet()) {
            cbTacGia.addItem(id);
        }
        cbTacGia.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Integer && mapTacGia.containsKey(value)) {
                    setText(mapTacGia.get(value));
                } else if (value == null) {
                    setText("-- Chọn tác giả --");
                }
                return this;
            }
        });
    }

    private void loadTheLoai() {
        mapTheLoai = new TheLoaiClientController().layTatCaMap();
        cbTheLoai.addItem(null);
        for (Integer id : mapTheLoai.keySet()) {
            cbTheLoai.addItem(id);
        }
        cbTheLoai.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Integer && mapTheLoai.containsKey(value)) {
                    setText(mapTheLoai.get(value));
                } else if (value == null) {
                    setText("-- Chọn thể loại --");
                }
                return this;
            }
        });
    }

    private void loadDuLieuHienTai() {
        sach s = new SachController().layTheoMa(maSach);
        if (s != null) {
            txtTenSach.setText(s.getTenSach());
            cbTacGia.setSelectedItem(s.getMaTacGia());
            cbTheLoai.setSelectedItem(s.getMaTheLoai());
            txtNamXB.setText(s.getNamXuatBan() != null ? s.getNamXuatBan().toString() : "");
            txtNhaXB.setText(s.getNhaXuatBan() != null ? s.getNhaXuatBan() : "");
            txtSoLuong.setText(String.valueOf(s.getSoLuongTong()));
        }
    }

    private void luuSua() {
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
            s.setMaSach(maSach);
            s.setTenSach(txtTenSach.getText().trim());
            s.setMaTacGia(cbTacGia.getSelectedItem() == null ? null : (Integer) cbTacGia.getSelectedItem());
            s.setMaTheLoai(cbTheLoai.getSelectedItem() == null ? null : (Integer) cbTheLoai.getSelectedItem());

            String nam = txtNamXB.getText().trim();
            s.setNamXuatBan(nam.isEmpty() ? null : Integer.valueOf(nam));

            s.setNhaXuatBan(txtNhaXB.getText().trim());

            int slTong = Integer.parseInt(txtSoLuong.getText().trim());
            s.setSoLuongTong(slTong);

            boolean ok = new SachClientController().sua(s);
            if (ok) {
                JOptionPane.showMessageDialog(this, 
                    "Sửa sách thành công!", 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
                parent.refresh();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Sửa sách thất bại!", 
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
