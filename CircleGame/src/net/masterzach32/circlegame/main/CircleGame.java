package net.masterzach32.circlegame.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import net.masterzach32.circlegame.entity.Dots;
import net.masterzach32.circlegame.entity.Player;
import net.masterzach32.circlegame.map.World;
import net.masterzach32.circlegame.util.LogHelper;
import net.masterzach32.circlegame.util.Utilities;


@SuppressWarnings("serial")
public class CircleGame extends Canvas implements Runnable, KeyListener, MouseListener {
	
	public static final int WIDTH = 1440, HEIGHT = 900;
	public String name;
	
	// game thread
	private Thread thread;
	private boolean running;
	/** The fps you see in game */
	public static int fps = 0;
	/** The number of times the fps counter will update per second. Higher numbers result in a less accurate fps counter. */
	public int fpsupdate = 1;
	/** The maximum amount of fps in the game, not implemented yet */
	public int maxfps = 120;
	
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
		// Ticks
		long lastTime = System.nanoTime();
		final double amountOfTicks = 30;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		// FPS
		long lastTime2 = System.nanoTime();
		int frames = 0;
		while(running){
			long time = System.nanoTime();
			delta += (time - lastTime) / ns;
			lastTime = time;
				if(delta >= 1) {
					tick();
					delta--;
				}
			frames += 1 * fpsupdate;
			if(System.nanoTime() - lastTime2 >= (1000000000L / fpsupdate) /*|| frames == maxfps*/) {
				fps = frames;
				frames = 0;
				lastTime2 = System.nanoTime();
			}
		render();
		}
	}
	
	public void tick() {
		Dots.tick();
		Player.tick();
	}
	
	public void render() {
		BufferStrategy buffer = this.getBufferStrategy();
		if(buffer == null) {
			createBufferStrategy(4);
			return;
		}
		Graphics g = buffer.getDrawGraphics();
		World.getWorld().render(g);
		Dots.render(g);
		g.dispose();
		buffer.show();
		//Player.getPlayers().render();
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
