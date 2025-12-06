package view;

import client.AuthClientController;
import model.nhanVien;
import javax.swing.*;
import java.awt.*;

public class DangNhap extends javax.swing.JFrame {

    private AuthClientController authCtrl = new AuthClientController();

    public DangNhap() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Đăng Nhập - Hệ Thống Quản Lý Thư Viện");
        setResizable(false);
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(Color.WHITE);
        jPanel1.setLayout(null);

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 28));
        jLabel1.setForeground(new Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ THƯ VIỆN");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 40, 450, 60);

        jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jLabel2.setForeground(new Color(44, 62, 80));
        jLabel2.setText("Tên đăng nhập:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(50, 140, 200, 30);

        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUser.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        jPanel1.add(txtUser);
        txtUser.setBounds(50, 175, 350, 40);

        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jLabel3.setForeground(new Color(44, 62, 80));
        jLabel3.setText("Mật khẩu:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(50, 240, 150, 30);

        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        jPanel1.add(txtPass);
        txtPass.setBounds(50, 275, 350, 40);

        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setText("ĐĂNG NHẬP");
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(evt -> dangNhap());
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 76, 153));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 102, 204));
            }
        });
        jPanel1.add(btnLogin);
        btnLogin.setBounds(125, 360, 200, 50);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    private void dangNhap() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        nhanVien nv = authCtrl.dangNhap(user, pass);
        if (nv != null) {
            JOptionPane.showMessageDialog(this, 
                "Chào mừng " + nv.getHoTen() + " (" + nv.getVaiTro() + ")!\nĐăng nhập thành công.", 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new MainForm(nv).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Sai tài khoản hoặc mật khẩu!\nVui lòng kiểm tra lại.", 
                "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            txtPass.setText("");
            txtUser.requestFocus();
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new DangNhap().setVisible(true));
    }

    // Variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
}
