package net.masterzach32.spacerunner;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import net.masterzach32.spacerunner.api.IUpdatable;
import net.masterzach32.spacerunner.assets.AssetLoader;
import net.masterzach32.spacerunner.assets.Assets;
import net.masterzach32.spacerunner.state.GameState;
import net.masterzach32.spacerunner.state.LevelState;
import net.masterzach32.spacerunner.state.menu.HelpState;
import net.masterzach32.spacerunner.state.menu.InfoState;
import net.masterzach32.spacerunner.state.menu.MenuState;
import net.masterzach32.spacerunner.util.*;

/**
 * A simple space fighting game for AP Computer Science PP3
 * 
 * Some aspects of this game have been pulled from my previous project, the SideScroller Project, and have been marked.
 * <https://github.com/Masterzach32/SideScrollerProject>
 * 
 * @author Zach Kozar
 * @version 1.1 Beta
 */
public class SpaceRunner implements Runnable, KeyListener, MouseListener, IUpdatable {

	public static final boolean isUpdateEnabled = true;
	
	// bunch of stuff for score.
	public static int score = 0, dscore = 0, tscore = 0, scoreTimer, highScore = 0, gamesPlayed, enemiesKilled;
	
	// dimensions and location of the window
	public static int WIDTH = 640, HEIGHT = 360, TOP = 0, LEFT = 0, SCALE = 1;
	public static final String VERSION = "1.1.172";
	
	// Thread and Game instance
	private Thread tickAndRender;
	private JPanel panel;
	public static SpaceRunner game;
	
	// tick timer
	private boolean running;
	public static int FPS = 60;
	private long targetTime;
	
	// what you see
	private BufferedImage image;
	private Graphics2D g;
	
	// game states
	public static MenuState menu;
	public static HelpState help;
	public static InfoState info;
	public static LevelState level;
	
	// as it says
	public static AssetLoader al = new AssetLoader();
	
	// output log for space fighters
	public static LogHelper logger = new LogHelper("SpaceFighters", VERSION);
	
	// font for high score
	private Font font;
	
	/**
	 * Create our game instance and start the thread
	 */
	public SpaceRunner() {
		super();
		panel = new JPanel();
		panel.setFocusable(true);
		panel.requestFocus();
		if(tickAndRender == null) {
			tickAndRender = new Thread(this);
			panel.addKeyListener(this);
			panel.addMouseListener(this);
			tickAndRender.start();
		}
	}
	
	public synchronized void stop() {
		OptionsFile.save();
		logger.saveToLog();
		/*for(int i = 1; i <= gamesPlayed; i++) {
			System.out.println();
			for(int  j = 0; j < i; j++)
				System.out.print("*");
		}*/
		System.exit(0);
	}

	protected void init() {
		IUpdatable.checkForUpdates();
		
		logger.logInfo("Loading Java Graphics");
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		logger.logInfo("Starting pre-initialzation");
		Assets.preinit();
		
		logger.logInfo("Creating Window");
		Game.getFrame().setVisible(true);
		
		running = true;
		
		logger.logInfo("Loading Assets");
		Assets.init();
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(Assets.getFile("cyberspace"))).deriveFont(Font.PLAIN, 17);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		menu = new MenuState();
		help = new HelpState();
		info = new InfoState();
		level = new LevelState();
		
		GameState.setState(menu);
	}
	
	/**
	 * Game loop
	 * 
	 * game loop pulled from SideScroller Project.
	 */
	public void run() {
		try {
			init();

			long start;
			long elapsed;
			long wait;

			// game loop
			while(running) {
				targetTime = 1000 / FPS;
				start = System.nanoTime();

				tick();
				render();

				elapsed = System.nanoTime() - start;

				wait = targetTime - elapsed / 1000000;
				if(wait < 0) wait = 5;
				Thread.sleep(wait);
			}
		} catch(Exception e) {
			Utilities.createErrorDialog("Error", "An unexpected error occured: " + e.toString(), e);
		}
	}
	
	/**
	 * updates visual score
	 * @param amount
	 */
	public void addScore(int amount) {
		tscore += amount;
		scoreTimer = 120;
	}
	
	/**
	 * main tick method, the current state gets updated here, and
	 * the visuals for the score are also updated here
	 */
	public void tick() {
		// score stuff
		if(scoreTimer > 0) scoreTimer--;
		if(scoreTimer == 0) {
			score += tscore;
			tscore = 0;
		}
		if(score > highScore) highScore = score;
		if(score > dscore) dscore += 5;
		else if(score < dscore) dscore -= 5;
		
		if(GameState.getState() != null)
			GameState.getState().tick();
	}
	
	/**
	 * main render method, the current state gets rendered here.
	 */
	public void render() {
		if(GameState.getState() != null)
			GameState.getState().render(g);
		g.setColor(Color.WHITE);
		g.setFont(font);
		FontMetrics fontMetrics = g.getFontMetrics();
		String shighScore = new String("high score: " + highScore);
		g.drawString(shighScore, WIDTH - fontMetrics.stringWidth(shighScore) - 8, 350);
		renderToScreen();
	}
	
	public void renderToScreen() {
		Graphics g = panel.getGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.dispose();
	}
	
	public Thread getThread() {
		return tickAndRender;
	}
	
	public JPanel getPane() {
		return panel;
	}
	
	public void keyPressed(KeyEvent e) {
		if(GameState.getState() != null)
			GameState.getState().keyPressed(e.getKeyCode());
	}
	
	public void keyReleased(KeyEvent e) {
		if(GameState.getState() != null)
			GameState.getState().keyReleased(e.getKeyCode());
	}
	
	public void mousePressed(MouseEvent e) {
		if(GameState.getState() != null) 
			GameState.getState().mousePressed(e.getModifiersEx());
	}

	public void mouseReleased(MouseEvent e) {
		if(GameState.getState() != null) 
			GameState.getState().mouseReleased(e.getModifiersEx());
	}
	
	public void mouseClicked(MouseEvent e) {}


	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void keyTyped(KeyEvent e) {}

	public String getLocalVersion() {
		return VERSION;
	}

	public String getRepoURL() {
		return "http://masterzach32.net/repo/spacefighters/";
	}
}