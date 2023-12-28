import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionEvent;


import javax.swing.border.TitledBorder;



import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPasswordField;


public class KitapStokOtomasyon {

	public JFrame frame;
	private JTextField txtkitapismi;
	private JTextField txtstok;
	private JTextField txtfiyat;
	private JTable table;
	
	private boolean isAdminLoggedIn = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KitapStokOtomasyon window = new KitapStokOtomasyon();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		KitapStokOtomasyon kitapStokOtomasyon = new KitapStokOtomasyon();
	    kitapStokOtomasyon.Connect();
	}

	/**
	 * Create the application.
	 */
	public KitapStokOtomasyon() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField txtkitapid;
	private JPasswordField passwordField;
	
	public void Connect() {
	    try {
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kitapstoktakip", "root", "mb.72278");
	        System.out.println("Veritabanına bağlantı başarılı.");
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}

	public void table_load() {
		try {
			pst = con.prepareStatement("select * from book");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 921, 553);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 790, 0);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("KİTAP STOK TAKİP");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_1.setBounds(314, 20, 329, 42);
		frame.getContentPane().add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "KITAP KAYIT", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(27, 262, 396, 222);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("KİTAP İSMİ :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_2.setBounds(10, 42, 119, 24);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("STOK :");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_3.setBounds(10, 99, 64, 31);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("FİYAT :");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_4.setBounds(10, 157, 64, 24);
		panel.add(lblNewLabel_4);
		
		txtkitapismi = new JTextField();
		txtkitapismi.setBounds(139, 42, 204, 26);
		panel.add(txtkitapismi);
		txtkitapismi.setColumns(10);
		
		txtstok = new JTextField();
		txtstok.setBounds(139, 99, 204, 26);
		panel.add(txtstok);
		txtstok.setColumns(10);
		
		txtfiyat = new JTextField();
		txtfiyat.setBounds(139, 155, 204, 26);
		panel.add(txtfiyat);
		txtfiyat.setColumns(10);
		
		JButton btnNewButton = new JButton("KAYDET");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isAdminLoggedIn) {
					String kitapismi,stok,fiyat;
				
					kitapismi = txtkitapismi.getText();
					stok = txtstok.getText();
					fiyat = txtfiyat.getText();
				
					try {
						pst = con.prepareStatement("INSERT INTO book (kitapismi, stok, fiyat) VALUES (?, ?, ?)");
						pst.setString(1, kitapismi);
						pst.setString(2, stok);
						pst.setString(3, fiyat);
						pst.executeUpdate();

						JOptionPane.showMessageDialog(null, "Kayıt Eklendi");
						table_load();
						txtkitapismi.setText("");
						txtstok.setText("");
						txtfiyat.setText("");
						txtkitapismi.requestFocus();
					}
					catch (SQLException el) {
						el.printStackTrace();
					}
				}else{
                    JOptionPane.showMessageDialog(null, "Yönetici girişi yapmalısınız.");
                }
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
			
		btnNewButton.setBounds(437, 370, 109, 33);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnTemizle = new JButton("TEMİZLE");
		btnTemizle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtkitapismi.setText("");
				txtstok.setText("");
				txtfiyat.setText("");
				txtkitapid.setText("");
				txtkitapismi.requestFocus();
			}
		});
		btnTemizle.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnTemizle.setBounds(501, 451, 109, 33);
		frame.getContentPane().add(btnTemizle);
		
		JButton btnk = new JButton("ÇIKIŞ");
		btnk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnk.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnk.setBounds(681, 451, 109, 33);
		frame.getContentPane().add(btnk);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(437, 90, 445, 259);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "ARAMA", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(27, 174, 396, 78);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtkitapid = new JTextField();
		txtkitapid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					String kitapid = txtkitapid.getText();
					
					pst = con.prepareStatement("SELECT kitapismi, stok, fiyat FROM book WHERE kitapid = ?");
					pst.setString(1, kitapid);

						ResultSet rs = pst.executeQuery();
					if(rs.next()==true) {
						String kitapismi = rs.getString(1);
						String stok = rs.getString(2);
						String fiyat = rs.getString(3);
						
						txtkitapismi.setText(kitapismi);
						txtstok.setText(stok);
						txtfiyat.setText(fiyat);
					}
					else {
						txtkitapismi.setText("");
						txtstok.setText("");
						txtfiyat.setText("");
					}
				}
			catch (SQLException ex) {
				
			}
			}
		});
		txtkitapid.setBounds(140, 20, 218, 29);
		panel_1.add(txtkitapid);
		txtkitapid.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("KİTAP ID:");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_3_1.setBounds(10, 20, 86, 31);
		panel_1.add(lblNewLabel_3_1);
		
		JButton btnGncelle = new JButton("GÜNCELLE");
		btnGncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  if (isAdminLoggedIn) {
				String kitapismi,stok,fiyat,kitapid;
				
				kitapismi = txtkitapismi.getText();
				stok = txtstok.getText();
				fiyat = txtfiyat.getText();
				kitapid = txtkitapid.getText();
				
				 try {
					 pst = con.prepareStatement("UPDATE book SET kitapismi = ?, stok = ?, fiyat = ? WHERE kitapid = ?");
					 pst.setString(1, kitapismi);
					 pst.setString(2, stok);
					 pst.setString(3, fiyat);
					 pst.setString(4, kitapid);

					 pst.executeUpdate();
					 JOptionPane.showMessageDialog(null, "Güncellendi");
					 table_load();
					 txtkitapismi.setText("");
					 txtstok.setText("");
					 txtfiyat.setText("");
					 txtkitapismi.requestFocus();
				 }
				 catch (SQLException el) {
					 el.printStackTrace();
				 }
			  } else {
                  JOptionPane.showMessageDialog(null, "Yönetici girişi yapmalısınız.");
              }
			}
		});
		btnGncelle.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnGncelle.setBounds(601, 370, 109, 33);
		frame.getContentPane().add(btnGncelle);
		
		JButton btnSil_1 = new JButton("SİL");
		btnSil_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  if (isAdminLoggedIn) {
				String kitapid;
				
				kitapid = txtkitapid.getText();
				
				 try {
					 pst = con.prepareStatement("DELETE FROM book WHERE kitapid = ?");
					 
					 pst.setString(1, kitapid);
					 pst.executeUpdate();
					 JOptionPane.showMessageDialog(null, "Silindi");
					 table_load();
					
					 txtkitapismi.requestFocus();
				 }
				 catch (SQLException el) {
					 el.printStackTrace();
				 }
			  } else {
                  JOptionPane.showMessageDialog(null, "Yönetici girişi yapmalısınız.");
			}
			}
		});
		btnSil_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSil_1.setBounds(765, 370, 117, 33);
		frame.getContentPane().add(btnSil_1);
		
		JPanel panel_2 = new JPanel();
        panel_2.setBorder(
                new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                        "YÖNETİCİ GİRİŞİ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_2.setBounds(27, 92, 396, 64);
        frame.getContentPane().add(panel_2);
        panel_2.setLayout(null);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(10, 20, 186, 34);
        panel_2.add(passwordField);
        
        JButton btnGiris = new JButton("GİRİŞ");
        btnGiris.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAdminLogin();
            }
        });
        btnGiris.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnGiris.setBounds(206, 20, 89, 34);
        panel_2.add(btnGiris);
    }

    private void checkAdminLogin() {
        char[] password = passwordField.getPassword();
        String enteredPassword = new String(password);

        if (enteredPassword.equals("123456")) {
            isAdminLoggedIn = true;
            JOptionPane.showMessageDialog(null, "Yönetici Girişi Başarılı");
        } else {
            JOptionPane.showMessageDialog(null, "Yanlış şifre, lütfen tekrar deneyin.");
        }
    
	}
}
