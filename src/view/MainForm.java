package view;

import javax.swing.*;
import java.awt.*;
import controller.PhieuMuonController;
import controller.SachController;
import controller.BanDocController;
import model.nhanVien;

public class MainForm extends JFrame {

	private JLabel lblCanhBao;
	private JLabel lblTongSach, lblTongDocGia, lblDangMuon, lblQuaHan;
	private nhanVien currentUser;
	private JMenuBar mb;

	public MainForm() {
		this(null);
	}

	public MainForm(nhanVien user) {
		this.currentUser = user;
		setTitle("HỆ THỐNG QUẢN LÝ THƯ VIỆN");
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 247, 250));

		// Header với cảnh báo
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(new Color(0, 102, 204));
		headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ THƯ VIỆN", SwingConstants.LEFT);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitle.setForeground(Color.WHITE);
		headerPanel.add(lblTitle, BorderLayout.WEST);

		lblCanhBao = new JLabel("", SwingConstants.RIGHT);
		lblCanhBao.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblCanhBao.setForeground(Color.WHITE);
		headerPanel.add(lblCanhBao, BorderLayout.EAST);

		capNhatCanhBao();

		// Hiển thị thông tin user
		JLabel lblUserInfo = new JLabel();
		if (currentUser != null) {
			lblUserInfo.setText("Xin chào: " + currentUser.getHoTen() + " (" + currentUser.getVaiTro() + ")");
		} else {
			lblUserInfo.setText("Chưa đăng nhập");
		}
		lblUserInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblUserInfo.setForeground(Color.WHITE);
		headerPanel.add(lblUserInfo, BorderLayout.CENTER);

		add(headerPanel, BorderLayout.NORTH);

		// Menu bar
		mb = new JMenuBar();

		// Phân quyền menu theo vai trò
		boolean isAdmin = currentUser != null && "Quản lý".equals(currentUser.getVaiTro());
		boolean isThuThu = currentUser != null && "Thủ thư".equals(currentUser.getVaiTro());

		// ========== MENU CHO ADMIN ==========
		if (isAdmin) {
			// Menu Quản lý nhân viên (chỉ Admin)
			JMenu mQuanLyNV = new JMenu("Quản Lý Nhân Viên");
			mQuanLyNV.setFont(new Font("Segoe UI", Font.PLAIN, 14));

			JMenuItem mnuQuanLyNhanVien = new JMenuItem("Quản Lý Thủ Thư");
			mnuQuanLyNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuQuanLyNhanVien.setToolTipText("Quản lý thông tin thủ thư");
			mnuQuanLyNhanVien.addActionListener(e -> {
				this.setVisible(false);
				new QuanLyNhanVien(this).setVisible(true);
			});
			mQuanLyNV.add(mnuQuanLyNhanVien);
			mb.add(mQuanLyNV);

			// Menu Tìm kiếm (chỉ Admin)
			JMenu mTimKiem = new JMenu("Tìm Kiếm");
			mTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));

			JMenuItem mnuTimPhieuMuon = new JMenuItem("Tìm Phiếu Mượn");
			mnuTimPhieuMuon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuTimPhieuMuon.setToolTipText("Tìm kiếm phiếu mượn");
			mnuTimPhieuMuon.addActionListener(e -> {
				this.setVisible(false);
				new QuanLyPhieuMuon(this).setVisible(true);
			});
			mTimKiem.add(mnuTimPhieuMuon);

			JMenuItem mnuTimDocGia = new JMenuItem("Tìm Độc Giả");
			mnuTimDocGia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuTimDocGia.setToolTipText("Tìm kiếm độc giả");
			mnuTimDocGia.addActionListener(e -> {
				this.setVisible(false);
				new QuanLyDocGia(this).setVisible(true);
			});
			mTimKiem.add(mnuTimDocGia);

			JMenuItem mnuTimSach = new JMenuItem("Tìm Sách");
			mnuTimSach.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuTimSach.setToolTipText("Tìm kiếm sách");
			mnuTimSach.addActionListener(e -> {
				this.setVisible(false);
				new TraCuuSach(this).setVisible(true);
			});
			mTimKiem.add(mnuTimSach);
			mb.add(mTimKiem);
		}

		// ========== MENU CHO THỦ THƯ ==========
		if (isThuThu) {
			// Menu Quản lý (chỉ Thủ thư)
			JMenu mQuanLy = new JMenu("Quản Lý");
			mQuanLy.setFont(new Font("Segoe UI", Font.PLAIN, 14));

			JMenuItem mnuSach = new JMenuItem("Quản Lý Sách");
			mnuSach.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuSach.setToolTipText("Quản lý thông tin sách trong thư viện");
			mnuSach.addActionListener(e -> {
				this.setVisible(false);
				new QuanLySach(this).setVisible(true);
			});
			mQuanLy.add(mnuSach);

			JMenuItem mnuDocGia = new JMenuItem("Quản Lý Độc Giả");
			mnuDocGia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuDocGia.setToolTipText("Quản lý thông tin độc giả");
			mnuDocGia.addActionListener(e -> {
				this.setVisible(false);
				new QuanLyDocGia(this).setVisible(true);
			});
			mQuanLy.add(mnuDocGia);

			JMenuItem mnuTheLoai = new JMenuItem("Quản Lý Thể Loại");
			mnuTheLoai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuTheLoai.setToolTipText("Quản lý thể loại sách");
			mnuTheLoai.addActionListener(e -> {
				this.setVisible(false);
				new QuanLyTheLoai(this).setVisible(true);
			});
			mQuanLy.add(mnuTheLoai);

			JMenuItem mnuTacGia = new JMenuItem("Quản Lý Tác Giả");
			mnuTacGia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuTacGia.setToolTipText("Quản lý thông tin tác giả");
			mnuTacGia.addActionListener(e -> {
				this.setVisible(false);
				new QuanLyTacGia(this).setVisible(true);
			});
			mQuanLy.add(mnuTacGia);
			mb.add(mQuanLy);

			// Menu Giao dịch (chỉ Thủ thư)
			JMenu mGiaoDich = new JMenu("Giao Dịch");
			mGiaoDich.setFont(new Font("Segoe UI", Font.PLAIN, 14));

			JMenuItem mnuMuon = new JMenuItem("Mượn Sách");
			mnuMuon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuMuon.setToolTipText("Tạo phiếu mượn sách mới");
			mnuMuon.addActionListener(e -> {
				this.setVisible(false);
				new MuonSach(this).setVisible(true);
			});
			mGiaoDich.add(mnuMuon);

			JMenuItem mnuTra = new JMenuItem("Trả Sách");
			mnuTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuTra.setToolTipText("Xử lý trả sách và tính phạt");
			mnuTra.addActionListener(e -> {
				this.setVisible(false);
				new TraSach(this).setVisible(true);
			});
			mGiaoDich.add(mnuTra);

			JMenuItem mnuPhieuMuon = new JMenuItem("Quản Lý Phiếu Mượn");
			mnuPhieuMuon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuPhieuMuon.setToolTipText("Xem và quản lý các phiếu mượn sách");
			mnuPhieuMuon.addActionListener(e -> {
				this.setVisible(false);
				new QuanLyPhieuMuon(this).setVisible(true);
			});
			mGiaoDich.add(mnuPhieuMuon);

			JMenuItem mnuPhieuTra = new JMenuItem("Quản Lý Phiếu Trả");
			mnuPhieuTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuPhieuTra.setToolTipText("Xem và quản lý các phiếu trả sách, tiền phạt");
			mnuPhieuTra.addActionListener(e -> {
				this.setVisible(false);
				new QuanLyPhieuTra(this).setVisible(true);
			});
			mGiaoDich.add(mnuPhieuTra);

			JMenuItem mnuTraCuu = new JMenuItem("Tra Cứu Sách");
			mnuTraCuu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuTraCuu.setToolTipText("Tìm kiếm sách theo nhiều tiêu chí");
			mnuTraCuu.addActionListener(e -> {
				this.setVisible(false);
				new TraCuuSach(this).setVisible(true);
			});
			mGiaoDich.add(mnuTraCuu);
			mb.add(mGiaoDich);

			// Menu Tìm kiếm (chỉ Thủ thư)
			JMenu mTimKiem = new JMenu("Tìm Kiếm");
			mTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));

			JMenuItem mnuTimSach = new JMenuItem("Tìm Sách");
			mnuTimSach.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuTimSach.setToolTipText("Tìm kiếm sách");
			mnuTimSach.addActionListener(e -> {
				this.setVisible(false);
				new TraCuuSach(this).setVisible(true);
			});
			mTimKiem.add(mnuTimSach);

			JMenuItem mnuTimDocGia = new JMenuItem("Tìm Độc Giả");
			mnuTimDocGia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuTimDocGia.setToolTipText("Tìm kiếm độc giả");
			mnuTimDocGia.addActionListener(e -> {
				this.setVisible(false);
				new QuanLyDocGia(this).setVisible(true);
			});
			mTimKiem.add(mnuTimDocGia);

			JMenuItem mnuTimPhieuMuon = new JMenuItem("Tìm Phiếu Mượn");
			mnuTimPhieuMuon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuTimPhieuMuon.setToolTipText("Tìm kiếm phiếu mượn");
			mnuTimPhieuMuon.addActionListener(e -> {
				this.setVisible(false);
				new QuanLyPhieuMuon(this).setVisible(true);
			});
			mTimKiem.add(mnuTimPhieuMuon);
			mb.add(mTimKiem);

			// Menu Thống kê (chỉ Thủ thư)
			JMenu mThongKe = new JMenu("Thống Kê");
			mThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 14));

			JMenuItem mnuThongKeSach = new JMenuItem("Thống Kê Sách Được Mượn");
			mnuThongKeSach.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuThongKeSach.setToolTipText("Xem thống kê chi tiết các sách đang được mượn");
			mnuThongKeSach.addActionListener(e -> {
				this.setVisible(false);
				new ThongKeSachDuocMuon(this).setVisible(true);
			});
			mThongKe.add(mnuThongKeSach);

			JMenuItem mnuThongKeDocGia = new JMenuItem("Thống Kê Độc Giả");
			mnuThongKeDocGia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			mnuThongKeDocGia.setToolTipText("Xem thống kê chi tiết về độc giả và hoạt động mượn sách");
			mnuThongKeDocGia.addActionListener(e -> {
				this.setVisible(false);
				new ThongKeDocGia(this).setVisible(true);
			});
			mThongKe.add(mnuThongKeDocGia);
			mb.add(mThongKe);
		}

		// Menu Hệ thống
		JMenu mHeThong = new JMenu("Hệ Thống");
		mHeThong.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		JMenuItem mnuDangXuat = new JMenuItem("Đăng Xuất");
		mnuDangXuat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuDangXuat.setToolTipText("Đăng xuất khỏi hệ thống");
		mnuDangXuat.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(this,
					"Bạn có chắc chắn muốn đăng xuất?",
					"Xác nhận",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				this.dispose();
				new DangNhap().setVisible(true);
			}
		});
		mHeThong.add(mnuDangXuat);
		mb.add(mHeThong);

		setJMenuBar(mb);

		// Panel chính
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(new Color(245, 247, 250));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Welcome section
		JPanel welcomePanel = new JPanel(new BorderLayout());
		welcomePanel.setBackground(Color.WHITE);
		welcomePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0, 102, 204), 2, true),
				BorderFactory.createEmptyBorder(30, 30, 30, 30)));

		JLabel welcome = new JLabel(
				"<html><div style='text-align: center;'>" +
						"<h1 style='color: #0066CC; margin-bottom: 10px;'>CHÀO MỪNG ĐẾN HỆ THỐNG</h1>" +
						"<h2 style='color: #666; font-weight: normal;'>QUẢN LÝ THƯ VIỆN</h2>" +
						"<p style='color: #999; margin-top: 15px;'>Quản lý sách, độc giả và giao dịch một cách hiệu quả</p>"
						+
						"</div></html>",
				SwingConstants.CENTER);
		welcome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		welcomePanel.add(welcome, BorderLayout.CENTER);

		centerPanel.add(welcomePanel, BorderLayout.NORTH);

		// Dashboard với các widget thống kê
		JPanel dashboard = new JPanel(new GridLayout(2, 2, 25, 25));
		dashboard.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		dashboard.setBackground(new Color(245, 247, 250));

		// Widget 1: Tổng sách
		JPanel widget1 = createStatWidget("TỔNG SÁCH", "0 cuốn", new Color(52, 152, 219));
		dashboard.add(widget1);
		lblTongSach = (JLabel) ((JPanel) widget1.getComponent(1)).getComponent(0);

		// Widget 2: Tổng độc giả
		JPanel widget2 = createStatWidget("ĐỘC GIẢ", "0 người", new Color(46, 204, 113));
		dashboard.add(widget2);
		lblTongDocGia = (JLabel) ((JPanel) widget2.getComponent(1)).getComponent(0);

		// Widget 3: Sách đang mượn
		JPanel widget3 = createStatWidget("ĐANG MƯỢN", "0 cuốn", new Color(243, 156, 18));
		dashboard.add(widget3);
		lblDangMuon = (JLabel) ((JPanel) widget3.getComponent(1)).getComponent(0);

		// Widget 4: Phiếu quá hạn
		JPanel widget4 = createStatWidget("QUÁ HẠN", "0 phiếu", new Color(231, 76, 60));
		dashboard.add(widget4);
		lblQuaHan = (JLabel) ((JPanel) widget4.getComponent(1)).getComponent(0);

		centerPanel.add(dashboard, BorderLayout.CENTER);

		add(centerPanel, BorderLayout.CENTER);

		// Load dữ liệu thống kê
		capNhatThongKe();
	}

	private JPanel createStatWidget(String title, String value, Color color) {
		JPanel widget = new JPanel(new BorderLayout(10, 10));
		widget.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(color, 3, true),
				BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		widget.setBackground(Color.WHITE);

		JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTitle.setForeground(color);
		widget.add(lblTitle, BorderLayout.NORTH);

		JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		valuePanel.setBackground(Color.WHITE);
		JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
		lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblValue.setForeground(new Color(60, 60, 60));
		valuePanel.add(lblValue);
		widget.add(valuePanel, BorderLayout.CENTER);

		return widget;
	}

	private void capNhatThongKe() {
		SwingUtilities.invokeLater(() -> {
			try {
				// Tổng sách
				int tongSach = new SachController().layTatCaSach().stream()
						.mapToInt(s -> s.getSoLuongTong()).sum();
				lblTongSach.setText(tongSach + " cuốn");

				// Tổng độc giả
				int tongDocGia = new BanDocController().layTatCa().size();
				lblTongDocGia.setText(tongDocGia + " người");

				// Sách đang mượn
				int dangMuon = new SachController().layTatCaSach().stream()
						.mapToInt(s -> s.getSoLuongTong() - s.getSoLuongHienTai()).sum();
				lblDangMuon.setText(dangMuon + " cuốn");

				// Phiếu quá hạn
				int quaHan = new PhieuMuonController().demPhieuQuaHan();
				lblQuaHan.setText(quaHan + " phiếu");
				if (quaHan > 0) {
					lblQuaHan.setForeground(Color.RED);
				} else {
					lblQuaHan.setForeground(new Color(60, 60, 60));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void capNhatCanhBao() {
		int soQuaHan = new PhieuMuonController().demPhieuQuaHan();
		if (soQuaHan > 0) {
			lblCanhBao.setText("CẢNH BÁO: " + soQuaHan + " PHIẾU QUÁ HẠN - CẦN XỬ LÝ NGAY!");
			lblCanhBao.setForeground(new Color(255, 255, 0));
			lblCanhBao.setCursor(new Cursor(Cursor.HAND_CURSOR));
			lblCanhBao.setToolTipText("Click vào menu 'Báo Cáo' > 'Theo Dõi Quá Hạn' để xem chi tiết");
		} else {
			lblCanhBao.setText("HỆ THỐNG HOẠT ĐỘNG BÌNH THƯỜNG");
			lblCanhBao.setForeground(Color.WHITE);
			lblCanhBao.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			lblCanhBao.setToolTipText(null);
		}
		capNhatThongKe();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainForm().setVisible(true));
	}
}
