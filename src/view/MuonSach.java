package view;

import client.*;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Calendar;
import java.util.Date;

public class MuonSach extends JFrame {

	private SachClientController sachCtrl = new SachClientController();
	private BanDocClientController bdCtrl = new BanDocClientController();
	private PhieuMuonClientController pmCtrl = new PhieuMuonClientController();

	private JTextField txtMaBD, txtHoTen, txtLop, txtTimKiem;
	private JTable tableSach, tableMuon;
	private DefaultTableModel modelSach, modelMuon;
	private JSpinner spinnerNgayHenTra;
	private MainForm mainForm;

	public MuonSach(MainForm mainForm) {
		this.mainForm = mainForm;
		setTitle("PHIẾU MƯỢN SÁCH");
		setSize(1300, 850);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setBackground(new Color(245, 247, 250));

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				quayVeMainForm();
			}
		});

		initGUI();
		loadSachVaoBang();
	}

	private void quayVeMainForm() {
		if (mainForm != null) {
			mainForm.setVisible(true);
			mainForm.capNhatCanhBao();
		}
		this.dispose();
	}

	private void initGUI() {
		setLayout(new BorderLayout(10, 10));

		// ==================== HEADER ====================
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		headerPanel.setBackground(new Color(0, 102, 204));

		JLabel lblTitle = new JLabel("PHIẾU MƯỢN SÁCH", SwingConstants.LEFT);
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

		// ==================== BƯỚC 1: THÔNG TIN BẠN ĐỌC ====================
		JPanel step1Panel = new JPanel(new BorderLayout());
		step1Panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Nhập thông tin bạn đọc"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		step1Panel.setBackground(Color.WHITE);

		JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		infoPanel.setBackground(Color.WHITE);

		JLabel lblMaBD = new JLabel("Mã bạn đọc (Enter để tìm):");
		lblMaBD.setFont(new Font("Segoe UI", Font.BOLD, 14));
		infoPanel.add(lblMaBD);
		txtMaBD = new JTextField();
		txtMaBD.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtMaBD.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		txtMaBD.addActionListener(e -> timBanDoc());
		infoPanel.add(txtMaBD);

		JLabel lblHoTen = new JLabel("Họ tên:");
		lblHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		infoPanel.add(lblHoTen);
		txtHoTen = new JTextField();
		txtHoTen.setEditable(false);
		txtHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtHoTen.setBackground(new Color(240, 240, 240));
		txtHoTen.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		infoPanel.add(txtHoTen);

		JLabel lblLop = new JLabel("Lớp:");
		lblLop.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		infoPanel.add(lblLop);
		txtLop = new JTextField();
		txtLop.setEditable(false);
		txtLop.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtLop.setBackground(new Color(240, 240, 240));
		txtLop.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		infoPanel.add(txtLop);

		step1Panel.add(infoPanel, BorderLayout.CENTER);

		// Panel trái với thông tin bạn đọc
		JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
		leftPanel.add(step1Panel, BorderLayout.NORTH);

		// ==================== BƯỚC 2: CHỌN SÁCH ====================
		JPanel step2Panel = new JPanel(new BorderLayout());
		step2Panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Chọn sách muốn mượn"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		step2Panel.setBackground(Color.WHITE);

		// Tìm kiếm sách
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		searchPanel.setBackground(Color.WHITE);
		JLabel lblTim = new JLabel("Tìm sách:");
		lblTim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		searchPanel.add(lblTim);
		txtTimKiem = new JTextField(25);
		txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent e) {
				timKiemSach();
			}
		});
		searchPanel.add(txtTimKiem);
		step2Panel.add(searchPanel, BorderLayout.NORTH);

		// Bảng sách
		String[] colSach = { "Chọn", "Mã sách", "Tên sách", "Tác giả", "Còn lại" };
		modelSach = new DefaultTableModel(colSach, 0) {
			@Override
			public Class<?> getColumnClass(int c) {
				return c == 0 ? Boolean.class : Object.class;
			}
		};
		tableSach = new JTable(modelSach);
		tableSach.setRowHeight(30);
		tableSach.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tableSach.getTableHeader().setBackground(new Color(0, 102, 204));
		tableSach.getTableHeader().setForeground(Color.WHITE);
		tableSach.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		tableSach.setSelectionBackground(new Color(52, 152, 219));
		tableSach.setSelectionForeground(Color.WHITE);

		JScrollPane scrollSach = new JScrollPane(tableSach);
		scrollSach.setPreferredSize(new Dimension(0, 300));
		step2Panel.add(scrollSach, BorderLayout.CENTER);

		// Nút thêm vào phiếu
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnPanel.setBackground(Color.WHITE);
		JButton btnThem = createButton("Thêm vào phiếu", new Color(46, 204, 113));
		btnThem.setToolTipText("Thêm sách đã chọn vào danh sách mượn");
		btnThem.addActionListener(e -> themVaoPhieu());
		btnPanel.add(btnThem);
		step2Panel.add(btnPanel, BorderLayout.SOUTH);

		leftPanel.add(step2Panel, BorderLayout.CENTER);

		// ==================== BƯỚC 3: XÁC NHẬN ====================
		JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
		rightPanel.setBackground(new Color(245, 247, 250));

		// Sách sẽ mượn
		JPanel step3Panel = new JPanel(new BorderLayout());
		step3Panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Xác nhận sách sẽ mượn"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		step3Panel.setBackground(Color.WHITE);

		String[] colMuon = { "STT", "Mã sách", "Tên sách", "Số lượng" };
		modelMuon = new DefaultTableModel(colMuon, 0);
		tableMuon = new JTable(modelMuon);
		tableMuon.setRowHeight(30);
		tableMuon.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tableMuon.getTableHeader().setBackground(new Color(243, 156, 18));
		tableMuon.getTableHeader().setForeground(Color.WHITE);
		tableMuon.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		tableMuon.setSelectionBackground(new Color(243, 156, 18));
		tableMuon.setSelectionForeground(Color.WHITE);

		JScrollPane scrollMuon = new JScrollPane(tableMuon);
		scrollMuon.setPreferredSize(new Dimension(0, 200));
		step3Panel.add(scrollMuon, BorderLayout.CENTER);

		// Nút xóa
		JPanel btnXoaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnXoaPanel.setBackground(Color.WHITE);
		JButton btnXoa = createButton("Xóa khỏi phiếu", new Color(231, 76, 60));
		btnXoa.setToolTipText("Xóa sách đã chọn khỏi danh sách mượn");
		btnXoa.addActionListener(e -> xoaKhoiPhieu());
		btnXoaPanel.add(btnXoa);
		step3Panel.add(btnXoaPanel, BorderLayout.SOUTH);

		rightPanel.add(step3Panel, BorderLayout.CENTER);

		// Ngày mượn và hẹn trả
		JPanel datePanel = new JPanel(new GridLayout(2, 2, 10, 10));
		datePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Thông tin ngày mượn"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		datePanel.setBackground(Color.WHITE);

		JLabel lblNgayMuon = new JLabel("Ngày mượn:");
		lblNgayMuon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		datePanel.add(lblNgayMuon);
		JTextField txtNgayMuon = new JTextField(LocalDate.now().toString());
		txtNgayMuon.setEditable(false);
		txtNgayMuon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtNgayMuon.setBackground(new Color(240, 240, 240));
		txtNgayMuon.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		datePanel.add(txtNgayMuon);

		JLabel lblNgayHenTra = new JLabel("Ngày hẹn trả:");
		lblNgayHenTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		datePanel.add(lblNgayHenTra);
		SpinnerDateModel dateModel = new SpinnerDateModel();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 14);
		dateModel.setValue(cal.getTime());
		spinnerNgayHenTra = new JSpinner(dateModel);
		spinnerNgayHenTra.setEditor(new JSpinner.DateEditor(spinnerNgayHenTra, "yyyy-MM-dd"));
		spinnerNgayHenTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		datePanel.add(spinnerNgayHenTra);

		rightPanel.add(datePanel, BorderLayout.NORTH);

		// Split pane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setDividerLocation(700);
		splitPane.setResizeWeight(0.6);
		add(splitPane, BorderLayout.CENTER);

		// ==================== NÚT HÀNH ĐỘNG ====================
		JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		bottomPanel.setBackground(new Color(245, 247, 250));

		// Hướng dẫn
		JLabel lblHuongDan = new JLabel(
				"<html><b>Hướng dẫn:</b> Bước 1 → Nhập mã bạn đọc và nhấn Enter → Bước 2 → Chọn sách và nhấn 'Thêm vào phiếu' → Bước 3 → Kiểm tra lại và nhấn 'TẠO PHIẾU MƯỢN'</html>");
		lblHuongDan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblHuongDan.setForeground(new Color(127, 140, 141));
		bottomPanel.add(lblHuongDan, BorderLayout.NORTH);

		// Nút bấm
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		buttonPanel.setBackground(new Color(245, 247, 250));

		JButton btnTao = createLargeButton("TẠO PHIẾU MƯỢN", new Color(46, 204, 113), 200, 45);
		btnTao.setToolTipText("Tạo phiếu mượn với các sách đã chọn");
		btnTao.addActionListener(e -> taoPhieuMuon());

		JButton btnHuy = createButton("Hủy", new Color(149, 165, 166));
		btnHuy.setToolTipText("Hủy và quay về màn hình chính");
		btnHuy.addActionListener(e -> quayVeMainForm());

		buttonPanel.add(btnTao);
		buttonPanel.add(btnHuy);
		bottomPanel.add(buttonPanel, BorderLayout.CENTER);

		add(bottomPanel, BorderLayout.SOUTH);
	}

	private JButton createButton(String text, Color bgColor) {
		JButton btn = new JButton(text);
		btn.setBackground(bgColor);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setPreferredSize(new Dimension(140, 35));
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

	private void loadSachVaoBang() {
		modelSach.setRowCount(0);
		List<sach> ds = sachCtrl.layTatCaSach();
		for (sach s : ds) {
			if (s.getSoLuongHienTai() > 0) {
				modelSach.addRow(new Object[] {
						false, s.getMaSach(), s.getTenSach(),
						s.getTenTacGia() != null ? s.getTenTacGia() : "Không rõ",
						s.getSoLuongHienTai()
				});
			}
		}
	}

	private void timKiemSach() {
		String keyword = txtTimKiem.getText().trim();
		modelSach.setRowCount(0);
		List<sach> ds = sachCtrl.timKiemSach(keyword);
		for (sach s : ds) {
			if (s.getSoLuongHienTai() > 0) {
				modelSach.addRow(new Object[] { false, s.getMaSach(), s.getTenSach(),
						s.getTenTacGia() != null ? s.getTenTacGia() : "", s.getSoLuongHienTai() });
			}
		}
	}

	private void timBanDoc() {
		try {
			int ma = Integer.parseInt(txtMaBD.getText().trim());
			banDoc bd = bdCtrl.layTheoMa(ma);
			if (bd != null) {
				txtHoTen.setText(bd.getHoTen());
				txtLop.setText(bd.getLop());
				txtHoTen.setBackground(new Color(220, 255, 220));
				txtLop.setBackground(new Color(220, 255, 220));
			} else {
				JOptionPane.showMessageDialog(this, "Không tìm thấy bạn đọc với mã: " + ma,
						"Thông báo", JOptionPane.WARNING_MESSAGE);
				txtHoTen.setText("");
				txtLop.setText("");
				txtHoTen.setBackground(new Color(240, 240, 240));
				txtLop.setBackground(new Color(240, 240, 240));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Mã bạn đọc không hợp lệ! Vui lòng nhập số.",
					"Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void themVaoPhieu() {
		// Kiểm tra đã nhập bạn đọc chưa
		if (txtMaBD.getText().trim().isEmpty() || txtHoTen.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Vui lòng nhập mã bạn đọc trước khi chọn sách!",
					"Thông báo", JOptionPane.WARNING_MESSAGE);
			txtMaBD.requestFocus();
			return;
		}

		boolean coSachDuocChon = false;
		for (int i = 0; i < modelSach.getRowCount(); i++) {
			if ((Boolean) modelSach.getValueAt(i, 0)) {
				coSachDuocChon = true;
				int maSach = (Integer) modelSach.getValueAt(i, 1);
				String tenSach = (String) modelSach.getValueAt(i, 2);
				boolean daCo = false;
				for (int j = 0; j < modelMuon.getRowCount(); j++) {
					if ((Integer) modelMuon.getValueAt(j, 1) == maSach) {
						int sl = (Integer) modelMuon.getValueAt(j, 3);
						modelMuon.setValueAt(sl + 1, j, 3);
						daCo = true;
					}
				}
				if (!daCo) {
					modelMuon.addRow(new Object[] { modelMuon.getRowCount() + 1, maSach, tenSach, 1 });
				}
				modelSach.setValueAt(false, i, 0);
			}
		}

		if (!coSachDuocChon) {
			JOptionPane.showMessageDialog(this,
					"Vui lòng chọn ít nhất 1 cuốn sách từ danh sách!",
					"Thông báo", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void xoaKhoiPhieu() {
		int row = tableMuon.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this,
					"Vui lòng chọn sách cần xóa từ danh sách mượn!",
					"Thông báo", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		modelMuon.removeRow(row);
		for (int i = 0; i < modelMuon.getRowCount(); i++) {
			modelMuon.setValueAt(i + 1, i, 0);
		}
	}

	private void taoPhieuMuon() {
		// Kiểm tra đã chọn bạn đọc
		if (txtMaBD.getText().trim().isEmpty() || txtHoTen.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Vui lòng nhập mã bạn đọc trước!",
					"Thông báo", JOptionPane.WARNING_MESSAGE);
			txtMaBD.requestFocus();
			return;
		}

		// Kiểm tra đã chọn sách
		if (modelMuon.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this,
					"Vui lòng chọn ít nhất 1 cuốn sách!",
					"Thông báo", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			int maBanDoc = Integer.parseInt(txtMaBD.getText().trim());
			int maNV = 1;

			Date ngayHenTraDate = (Date) spinnerNgayHenTra.getValue();
			java.sql.Date ngayHenTra = new java.sql.Date(ngayHenTraDate.getTime());

			// Tạo danh sách chi tiết
			List<CTPhieuMuon> dsChiTiet = new ArrayList<>();
			for (int i = 0; i < modelMuon.getRowCount(); i++) {
				CTPhieuMuon ct = new CTPhieuMuon();

				Object maSachObj = modelMuon.getValueAt(i, 1);
				Object soLuongObj = modelMuon.getValueAt(i, 3);

				int maSach;
				int soLuong;

				if (maSachObj instanceof Integer) {
					maSach = (Integer) maSachObj;
				} else if (maSachObj instanceof String) {
					maSach = Integer.parseInt((String) maSachObj);
				} else {
					throw new Exception("Mã sách không hợp lệ ở dòng " + (i + 1));
				}

				if (soLuongObj instanceof Integer) {
					soLuong = (Integer) soLuongObj;
				} else if (soLuongObj instanceof String) {
					soLuong = Integer.parseInt((String) soLuongObj);
				} else {
					throw new Exception("Số lượng không hợp lệ ở dòng " + (i + 1));
				}

				ct.setMaSach(maSach);
				ct.setTenSach((String) modelMuon.getValueAt(i, 2));
				ct.setSoLuong(soLuong);
				dsChiTiet.add(ct);
			}

			// Tạo phiếu mượn
			boolean ok = pmCtrl.taoPhieuMuon(maBanDoc, maNV, ngayHenTra, dsChiTiet);

			if (ok) {
				JOptionPane.showMessageDialog(this,
						"Tạo phiếu mượn thành công!",
						"Thành công", JOptionPane.INFORMATION_MESSAGE);
				// Reset form
				modelMuon.setRowCount(0);
				txtMaBD.setText("");
				txtHoTen.setText("");
				txtLop.setText("");
				txtHoTen.setBackground(new Color(240, 240, 240));
				txtLop.setBackground(new Color(240, 240, 240));
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, 14);
				spinnerNgayHenTra.setValue(cal.getTime());
				loadSachVaoBang();
				if (mainForm != null) {
					mainForm.capNhatCanhBao();
				}
			} else {
				JOptionPane.showMessageDialog(this,
						"Tạo phiếu mượn thất bại!\nKiểm tra lại số lượng sách.",
						"Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,
					"Dữ liệu không hợp lệ!\nVui lòng kiểm tra lại mã bạn đọc và số lượng sách.",
					"Lỗi", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this,
					"Lỗi: " + ex.getMessage() + "\nVui lòng kiểm tra lại dữ liệu!",
					"Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MuonSach(null).setVisible(true));
	}
}
