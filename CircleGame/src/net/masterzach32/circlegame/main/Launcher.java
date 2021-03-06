package net.masterzach32.circlegame.main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Launcher {
	
	private String name;
	private String server;
	
	private static JFrame window;
	
	public static void main(String[] args) {
		window = new JFrame("CircleGame");
		window.add(new CircleGame("Masterzach32", "localhost"));
		window.setSize(CircleGame.WIDTH, CircleGame.HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2-(window.getSize().width)/2, dim.height/2-(window.getSize().height)/2);
		window.setVisible(true);
	}
	
	public static JFrame getFrame() {
		return window;
	}
}
