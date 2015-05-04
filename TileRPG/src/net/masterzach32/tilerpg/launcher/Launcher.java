package net.masterzach32.tilerpg.launcher;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import net.masterzach32.tilerpg.main.RPGGame;
import net.masterzach32.tilerpg.main.state.MainMenu;
import net.masterzach32.tilerpg.util.Downloader;
import net.masterzach32.tilerpg.util.LogHelper;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;

import java.awt.Panel;
import java.io.IOException;

import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.List;

import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class Launcher extends JFrame implements ActionListener {

	private static JFrame frame;
	private JTextField txtLogin;
	private JPasswordField txtPassword;
	
	private String username, password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher window = new Launcher();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Launcher() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("RPG Launcher 0.4");
		Dimension dim2 = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(dim2.width/2-frame.getSize().width/2, dim2.height/2-frame.getSize().height/2, 1280, 720);
		frame.setLocation(dim2.width/2-frame.getSize().width/2, dim2.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setBounds(6, 672, 1268, 20);
		frame.getContentPane().add(progressBar);
		progressBar.setVisible(true);
		
		JButton btnLaunchGame = new JButton("Launch Game");
		btnLaunchGame.setToolTipText("Start the game engine");
		btnLaunchGame.setBounds(434, 580, 400, 80);
		btnLaunchGame.addActionListener(this);
		btnLaunchGame.setActionCommand("launch");
		frame.getContentPane().add(btnLaunchGame);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 1268, 562);
		frame.getContentPane().add(tabbedPane);
		
		JEditorPane jep = new JEditorPane();
		jep.setEditable(false);   
		try {
		  jep.setPage("http://www.rpggame.masterzach32.net");
		}catch (IOException e) {
		  jep.setContentType("text/html");
		  jep.setText("<html>Could not load the page. Check your internet connection.</html>");
		} 
		JScrollPane scrollPane = new JScrollPane(jep);   
		tabbedPane.addTab("News", null, scrollPane, null);
		
		JEditorPane jep_2 = new JEditorPane();
		jep_2.setEditable(false);   
		try {
		  jep_2.setPage("http://www.rpggame.masterzach32.net/updates");
		}catch (IOException e) {
		  jep_2.setContentType("text/html");
		  jep_2.setText("<html>Could not load the page. Check your internet connection.</html>");
		} 
		JScrollPane scrollPane_2 = new JScrollPane(jep_2);   
		tabbedPane.addTab("Latest Update", null, scrollPane_2, null);
		
		
		txtLogin = new JTextField();
		txtLogin.setText("Username");
		txtLogin.setBounds(16, 605, 134, 28);
		frame.getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setText("Password");
		txtPassword.setBounds(16, 632, 134, 28);
		frame.getContentPane().add(txtPassword);
		
		JLabel lblLogin = new JLabel("Log In:");
		lblLogin.setBounds(16, 580, 61, 16);
		frame.getContentPane().add(lblLogin);
		
		JButton btnLogin = new JButton("Log In");
		btnLogin.setBounds(162, 633, 117, 29);
		btnLogin.addActionListener(this);
		btnLogin.setActionCommand("login");
		frame.getContentPane().add(btnLogin);
		
		JRadioButton rdbtnRememberLogIn = new JRadioButton("Remember me");
		rdbtnRememberLogIn.setBounds(162, 607, 170, 23);
		frame.getContentPane().add(rdbtnRememberLogIn);
		
		btnLaunchGame.addActionListener(this);
		MainMenu.updates = Downloader.readTextFile("http://masterzach32.net/wp-content/uploads/updates.txt", "updates.txt");
	}
	
	public static void startGame(String[] args, String user, String pass) {
		new RPGGame(args, user, pass);
		frame.setVisible(false);
		frame.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		    if ("launch".equals(e.getActionCommand())) {
		    	startGame(null, username, password);
		    	LogHelper.logInfo("User launching the game with U: " + username + " P: " + password);
		    } if ("login".equals(e.getActionCommand())) {
		    	username = txtLogin.getText();
		    	password = txtPassword.getText();
		    	LogHelper.logInfo("User logging in with U: " + username + " P: " + password);
		    } else {
		    	return;
		    }
		} 
}
