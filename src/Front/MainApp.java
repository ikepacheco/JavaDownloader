package Front;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Back.Download;

import java.awt.Color;
import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Window.Type;
import javax.swing.JPanel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.event.MouseMotionAdapter;
import javax.swing.Box;
import javax.swing.UIManager;
import java.awt.Rectangle;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.ComponentOrientation;
import javax.swing.DebugGraphics;
import java.util.Locale;
import java.awt.SystemColor;
import java.awt.Label;


public class MainApp extends JFrame {
	
	private JTextField txtLink;
	private JTextField txtFileLocation;

	int xMouse=0;
	int yMouse=0;
	
	
	JFileChooser chooseFile;
	public JProgressBar progressBar;
	public JLabel lblLogDownload;
	public boolean downloading = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp frame = new MainApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,
	                e.getMessage(),
	                e.getCause().toString(),
	                JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		

	}

	/**
	 * Create the frame.
	 */
	public MainApp() {
		setUndecorated(true);
		setType(Type.UTILITY);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Download by URL");
		getContentPane().setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		setFont(new Font("Candara Light", Font.PLAIN, 12));
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		progressBar = new JProgressBar();
		progressBar.setMaximum(1000);
		progressBar.setForeground(new Color(224, 62, 32));
		progressBar.setBorderPainted(false);
		progressBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		progressBar.setBorder(null);
		progressBar.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		progressBar.setBounds(180, 174, 385, 21);
		getContentPane().add(progressBar);
		
		JLabel lblLink = new JLabel("Link");
		lblLink.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLink.setFont(new Font("Trebuchet MS", Font.PLAIN, 27));
		lblLink.setBounds(83, 66, 68, 31);
		getContentPane().add(lblLink);
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLocation.setFont(new Font("Trebuchet MS", Font.PLAIN, 27));
		lblLocation.setBounds(22, 108, 129, 31);
		getContentPane().add(lblLocation);
		
		txtLink = new JTextField();
		txtLink.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 255, 255), Color.GRAY, new Color(255, 255, 255), Color.WHITE));
		txtLink.setToolTipText("Link");
		txtLink.setBounds(180, 66, 385, 31);
		getContentPane().add(txtLink);
		txtLink.setColumns(10);
		
		txtFileLocation = new JTextField();
		txtFileLocation.setText("C:\\Users\\demon\\Desktop");
		txtFileLocation.setBounds(new Rectangle(7, 55, 11, 55));
		txtFileLocation.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.GRAY, Color.WHITE, Color.WHITE));
		txtFileLocation.setToolTipText("Link");
		txtFileLocation.setColumns(10);
		txtFileLocation.setBounds(180, 108, 273, 31);
		getContentPane().add(txtFileLocation);
		
		JButton btnSelectFolder = new JButton("Select Folder");
		btnSelectFolder.setForeground(Color.WHITE);
		btnSelectFolder.setDebugGraphicsOptions(DebugGraphics.BUFFERED_OPTION);
		btnSelectFolder.setLocale(new Locale("es", "PE"));
		btnSelectFolder.setBackground(new Color(0, 147, 255));
		btnSelectFolder.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		btnSelectFolder.setBorder(null);
		btnSelectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				chooseFile = new JFileChooser(); 
				chooseFile.setCurrentDirectory(new java.io.File("."));
				chooseFile.setDialogTitle("Select Folder");
				chooseFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    //
			    // disable the "All files" option.
			    //
				chooseFile.setAcceptAllFileFilterUsed(false);
			    //    
				int result = chooseFile.showOpenDialog(MainApp.this);
			    if (result == JFileChooser.APPROVE_OPTION ||
			    	result == JFileChooser.CANCEL_OPTION) 
			    { 
			    	if (result == JFileChooser.APPROVE_OPTION) {
				    	System.out.println("getSelectedFile() : " +  chooseFile.getSelectedFile());
			      		txtFileLocation.setText(chooseFile.getSelectedFile().toString());
			    	}
			    }
			    else 
			    {
			    	JOptionPane.showMessageDialog(MainApp.this, "Select folder please","No folder selected",1);
			    }
				
			}
		});
		btnSelectFolder.setBounds(463, 108, 102, 31);
		getContentPane().add(btnSelectFolder);
		
		JButton btnDownload = new JButton("Download");
		btnDownload.setForeground(Color.WHITE);
		btnDownload.setBackground(new Color(0, 147, 255));
		btnDownload.setBorder(null);

		
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = "";
				String link = txtLink.getText();
				try 
				{
					fileName = Paths.get(new URI(link).getPath()).getFileName().toString();
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null,
	                e1.getMessage(),
	                e1.getCause().toString(),
	                JOptionPane.INFORMATION_MESSAGE);
				}
				File output = new File(txtFileLocation.getText() + "\\" + fileName);
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						try {
							Thread t = new Thread(new Download(link, output, progressBar, lblLogDownload, downloading, btnDownload));
							t.start();
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null,
			                e.getMessage(),
			                e.getCause().toString(),
			                JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
			}
		});
		
		btnDownload.setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
		btnDownload.setBounds(270, 206, 203, 47);
		getContentPane().add(btnDownload);
		
		
		lblLogDownload = new JLabel("--");
		lblLogDownload.setHorizontalAlignment(SwingConstants.LEFT);
		lblLogDownload.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblLogDownload.setBounds(180, 150, 385, 21);
		getContentPane().add(lblLogDownload);
		
		JPanel panelTopOptions = new JPanel();

		panelTopOptions.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xMouse = e.getX();
				yMouse = e.getY();
			}
		});
		panelTopOptions.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int xScreen = e.getXOnScreen();
				int yScreen = e.getYOnScreen();
				setLocation(xScreen - xMouse, yScreen - yMouse);
			}
		});
		panelTopOptions.setBackground(new Color(255, 213, 213));
		panelTopOptions.setBounds(0, 0, 614, 269);
		getContentPane().add(panelTopOptions);
		panelTopOptions.setLayout(null);
		
		JPanel btnExit = new JPanel();
		btnExit.addMouseListener(new MouseAdapter() {
			
		});
		btnExit.setBackground(Color.WHITE);
		btnExit.setBounds(12, 12, 41, 41);
		panelTopOptions.add(btnExit);
		btnExit.setLayout(null);
		
		JLabel xLabel = DefaultComponentFactory.getInstance().createLabel("X");
		xLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		xLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		xLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setBackground(new Color(224,62,82));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setBackground(new Color(255,255,255));				
			}
		});
		xLabel.setHorizontalAlignment(SwingConstants.CENTER);
		xLabel.setIconTextGap(1);
		xLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		xLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		xLabel.setBounds(0, 0, 41, 41);
		btnExit.add(xLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 11, 594, 247);
		panelTopOptions.add(panel);
		panel.setLayout(null);
		
		Label label = new Label("@IkePacheco");
		label.setForeground(new Color(192, 192, 192));
		label.setBounds(10, 215, 77, 22);
		panel.add(label);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 614, 269);
		
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
