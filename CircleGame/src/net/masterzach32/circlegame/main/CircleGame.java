package net.masterzach32.circlegame.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import net.masterzach32.circlegame.entity.Dots;
import net.masterzach32.circlegame.map.World;
import net.masterzach32.circlegame.util.LogHelper;
import net.masterzach32.circlegame.util.Utilities;


@SuppressWarnings("serial")
public class CircleGame extends JPanel implements Runnable, KeyListener, MouseListener {
	
	public static final int WIDTH = 1440, HEIGHT = 900;
	public String name;
	
	// game thread
	private Thread thread;
	private boolean running;
	public static int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	private Graphics2D g;
	private BufferedImage image;
	
	private World world;
	
	public CircleGame(String playerName, String serverip) {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		this.name = playerName;
		running = true;
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			thread.start();
		}
	}
	
	public static void stop() {
		System.exit(0);
	}
	
	public void init() {
		LogHelper.logInfo("Launching SideScroller Game - Build 0.1");
		LogHelper.logInfo("Date: " + Utilities.getTime());
		LogHelper.logInfo("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
		LogHelper.logInfo("OS Archetecture: " + System.getProperty("os.arch"));
		LogHelper.logInfo("Java Version: " + System.getProperty("java.version"));
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		world = new World();
	}
	
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		// game loop
		while(running) {
			start = System.nanoTime();
			
			tick();
			render();
			renderToScreen();
		
			elapsed = System.nanoTime() - start;
		
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			try {
				Thread.sleep(wait);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tick() {
		Dots.tick();
	}
	
	public void render() {
		World.getWorld().render(g);
		Dots.render(g);
		//Player.getPlayers().render();
	}
	
	private void renderToScreen() {
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
	}

	public void mouseClicked(MouseEvent e) {} 
	
	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}
}
