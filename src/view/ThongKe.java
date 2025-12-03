package view;

import controller.PhieuMuonController;
import controller.SachController;
import controller.BanDocController;
import controller.XmlLogController;
import model.phieuMuon;
import model.sach;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ThongKe extends JFrame {

    private JLabel lblTongSach, lblDangMuon, lblQuaHan, lblDocGia;
    private DefaultTableModel modelQuaHan;
    private JTable tableQuaHan;
    private MainForm mainForm;

    public ThongKe(MainForm mainForm) {
        this.mainForm = mainForm;
        setTitle("THỐNG KÊ & BÁO CÁO");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 247, 250));

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                quayVeMainForm();
            }
        });

        // Header panel
        JPanel pTop = new JPanel(new BorderLayout());
        pTop.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        pTop.setBackground(new Color(0, 102, 204));

        JLabel lblTitle = new JLabel("THỐNG KÊ & BÁO CÁO", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        pTop.add(lblTitle, BorderLayout.WEST);

        JButton btnQuayVe = new JButton("Quay lại");
        btnQuayVe.setBackground(new Color(100, 100, 100));
        btnQuayVe.setForeground(Color.WHITE);
        btnQuayVe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnQuayVe.setFocusPainted(false);
        btnQuayVe.addActionListener(e -> quayVeMainForm());
        pTop.add(btnQuayVe, BorderLayout.EAST);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // TAB 1: TỔNG QUAN
        JPanel pTongQuan = new JPanel(new GridLayout(2, 2, 30, 30));
        pTongQuan.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        pTongQuan.setBackground(new Color(245, 247, 250));

        // Tạo các card widget
        JPanel card1 = createStatPanel("TỔNG SÁCH TRONG THƯ VIỆN", "0 cuốn", new Color(52, 152, 219));
        lblTongSach = (JLabel) ((JPanel) card1.getComponent(1)).getComponent(0);
        pTongQuan.add(card1);

        JPanel card2 = createStatPanel("SÁCH ĐANG ĐƯỢC MƯỢN", "0 cuốn", new Color(243, 156, 18));
        lblDangMuon = (JLabel) ((JPanel) card2.getComponent(1)).getComponent(0);
        pTongQuan.add(card2);

        JPanel card3 = createStatPanel("PHIẾU QUÁ HẠN", "0 phiếu", new Color(231, 76, 60));
        lblQuaHan = (JLabel) ((JPanel) card3.getComponent(1)).getComponent(0);
        pTongQuan.add(card3);

        JPanel card4 = createStatPanel("ĐỘC GIẢ HOẠT ĐỘNG", "0 người", new Color(46, 204, 113));
        lblDocGia = (JLabel) ((JPanel) card4.getComponent(1)).getComponent(0);
        pTongQuan.add(card4);

        tabbedPane.addTab("Tổng quan", pTongQuan);

        // TAB 2: PHIẾU QUÁ HẠN CHI TIẾT
        JPanel pQuaHan = new JPanel(new BorderLayout(10, 10));
        pQuaHan.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pQuaHan.setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        JLabel title = new JLabel("DANH SÁCH PHIẾU MƯỢN QUÁ HẠN", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(231, 76, 60));
        headerPanel.add(title, BorderLayout.WEST);
        pQuaHan.add(headerPanel, BorderLayout.NORTH);

        String[] cols = { "Mã phiếu", "Mã độc giả", "Họ tên", "Ngày mượn", "Hẹn trả", "Quá hạn (ngày)" };
        modelQuaHan = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableQuaHan = new JTable(modelQuaHan);
        tableQuaHan.setRowHeight(35);
        tableQuaHan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableQuaHan.setSelectionBackground(new Color(52, 152, 219));
        tableQuaHan.setSelectionForeground(Color.WHITE);
        tableQuaHan.getTableHeader().setBackground(new Color(231, 76, 60));
        tableQuaHan.getTableHeader().setForeground(Color.WHITE);
        tableQuaHan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        pQuaHan.add(new JScrollPane(tableQuaHan), BorderLayout.CENTER);

        tabbedPane.addTab("Phiếu quá hạn", pQuaHan);

        // TAB 3: XUẤT BÁO CÁO
        JPanel pXuat = new JPanel(new GridBagLayout());
        pXuat.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));
        pXuat.setBackground(new Color(245, 247, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Button XML
        JButton btnXML = createLargeButton("XUẤT DANH SÁCH QUÁ HẠN RA XML",
                new Color(231, 76, 60), 400, 60);
        btnXML.addActionListener(e -> {
            try {
                XmlLogController.ghiLogQuaHan();
                JOptionPane.showMessageDialog(this,
                        "Đã xuất danh sách quá hạn vào file: log_qua_han.xml\n" +
                                "Vị trí: " + System.getProperty("user.dir"),
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xuất file XML: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        pXuat.add(btnXML, gbc);

        tabbedPane.addTab("Xuất báo cáo", pXuat);

        // Layout chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(pTop, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);

        // Load dữ liệu
        loadThongKe();
    }

    private JPanel createStatPanel(String title, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(color, 5, true));
        panel.setBackground(new Color(255, 255, 240));

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(color);
        panel.add(lblTitle, BorderLayout.NORTH);

        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        valuePanel.setBackground(new Color(255, 255, 240));
        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValue.setForeground(new Color(60, 60, 60));
        valuePanel.add(lblValue);
        panel.add(valuePanel, BorderLayout.CENTER);
        return panel;
    }

    private JButton createLargeButton(String text, Color bgColor, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(width, height));
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

    private void quayVeMainForm() {
        if (mainForm != null) {
            mainForm.setVisible(true);
            mainForm.capNhatCanhBao();
        }
        this.dispose();
    }

    private void loadThongKe() {
        // Tổng sách
        int tongSach = new SachController().layTatCaSach().stream()
                .mapToInt(sach::getSoLuongTong).sum();
        lblTongSach.setText(tongSach + " cuốn");

        // Sách đang mượn
        int dangMuon = new SachController().layTatCaSach().stream()
                .mapToInt(s -> s.getSoLuongTong() - s.getSoLuongHienTai()).sum();
        lblDangMuon.setText(dangMuon + " cuốn");

        // Độc giả
        int tongDocGia = new BanDocController().layTatCa().size();
        lblDocGia.setText(tongDocGia + " người");

        // Phiếu quá hạn + chi tiết
        List<phieuMuon> dsQuaHan = new PhieuMuonController().layPhieuQuaHan();
        lblQuaHan.setText(dsQuaHan.size() + " phiếu");
        lblQuaHan.setForeground(dsQuaHan.size() > 0 ? Color.RED : new Color(60, 60, 60));

        modelQuaHan.setRowCount(0);
        for (phieuMuon pm : dsQuaHan) {
            long quaHanNgay = java.time.temporal.ChronoUnit.DAYS.between(
                    pm.getNgayHenTra().toLocalDate(), java.time.LocalDate.now());

            modelQuaHan.addRow(new Object[] {
                    pm.getMaPhieuMuon(),
                    pm.getMaBanDoc(),
                    pm.getHoTenDocGia(),
                    pm.getNgayMuon(),
                    pm.getNgayHenTra(),
                    quaHanNgay + " ngày"
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ThongKe(null).setVisible(true));
    }
}
