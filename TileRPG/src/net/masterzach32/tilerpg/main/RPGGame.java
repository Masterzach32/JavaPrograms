package net.masterzach32.tilerpg.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;

import net.masterzach32.tilerpg.entity.EntityMob;
import net.masterzach32.tilerpg.entity.player.EntityPlayer;
import net.masterzach32.tilerpg.entity.player.EntityPlayerFighter;
import net.masterzach32.tilerpg.gfx.ImageLoader;
import net.masterzach32.tilerpg.gfx.ImageManager;
import net.masterzach32.tilerpg.gfx.SpriteSheet;
import net.masterzach32.tilerpg.main.state.GameScreen;
import net.masterzach32.tilerpg.main.state.LevelSelect;
import net.masterzach32.tilerpg.main.state.MainMenu;
import net.masterzach32.tilerpg.main.state.State;
import net.masterzach32.tilerpg.util.KeyHandler;
import net.masterzach32.tilerpg.util.LogHelper;
import net.masterzach32.tilerpg.world.Level;

public class RPGGame extends Canvas implements Runnable {
	
	private static final long serialVersionUID = -8825877206302951735L;
	/** Game Strings */
	public static final String VERSION = "Alpha 0.1.4.065", NAME = "RPG";
	/** Window sizes */
	public static final int WIDTH = 640, HEIGHT = 360, SCALE = 2, TILESIZE = 16;
	/** Sets if the window is resizable or not */
	public static boolean resizable = false;
	/** Sets if the window is visible or not */
	public static boolean visibility = true;
	/** Sets if the game is running or not; the main while(running) loop will not run if this is false */
	public static boolean running = false;
	/** Used to show fps and version in-game, this is now default to true */
	@Deprecated
	public static boolean showInfo = true;
	/** The game thread */
	public Thread gameThread;
	/** The fps you see in game */
	public static int fps = 0;
	/** The number of times the fps counter will update per second. Higher numbers result in a less accurate fps counter. */
	public int fpsupdate = 1;
	/** The maximum amount of fps in the game, not implemented yet */
	public int maxfps = 120;
	/** The stage the game is currently in. Dont use, use the new State manager */
	@Deprecated
	public static int stage = 0;
	/** Game states */
	public static State menuState, levelState, gameState;
	
	private SpriteSheet sheet;
	/** The {@link SpriteSheet} that the game loads during the init() method */
	private BufferedImage spriteSheet;
	/** The main menu and level select background */
	public static BufferedImage loading;
	public static BufferedImage hud;
	/** The grass image */
	public static BufferedImage gui_popup;
	public static BufferedImage gui_inv;
	/** The instance of the {@link ImageManager} that the game uses */
	private static ImageManager im;
	
	/** The player */
	private static EntityPlayer player;
	public static EntityMob[] mob;
	
	/** The {@link Level} that the game runs */
	public static Level tutorial, level_1, level_2, level_3, level_4, level_5, level_6;
	
	/** The current level as an integer */
	public static int current_level = 0;
	
	private static RPGGame game;
	
	private boolean paused;
	
	private long a, b;
	
	private Random x = new Random(), y = new Random();
	
	private boolean isChosen;
	
	private String username = "Tester";
	private String password = "moreROA";
	
	
	/**
	 * Called when the game starts. Loads the {@link SpriteSheet}, {@link EntityPlayer}, and the {@link Level}
	 */
	public void init() {
		int v = 0;
		// Create the progress bar and window
		JProgressBar p = new JProgressBar();
		JLabel t = new JLabel();
		t.setText("Starting...");
		p.setSize(200, 50); 
		p.setValue(v); 
		p.setStringPainted(true); 
		JFrame f = new JFrame("Loading Progress"); 
		f.setSize(350, 100);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setResizable(resizable);
		f.setLayout(new FlowLayout()); 
		f.add(p);
		f.add(t);
		Dimension dim2 = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation((dim2.width/2-f.getSize().width/2), (dim2.height/2-f.getSize().height/2));
		f.setVisible(true);
		// Begin loading assets
		t.setText("Loading Assets...");
		LogHelper.logInfo("Loading assets");
		ImageLoader loader = new ImageLoader();
		v += 1;
		p.setValue(v);
		loading = loader.load("/loading.png");
		v += 1;
		p.setValue(v);
		spriteSheet = loader.load("/asset.png");
		v += 1;
		p.setValue(v);
		gui_popup = loader.load("/gui/popup.png");
		v += 1;
		p.setValue(v);
		gui_inv = loader.load("/gui/inv.png"); 
		v += 1;
		p.setValue(v);
		hud = loader.load("/gui/HUD.png");
		v += 1;
		p.setValue(v);
		// Create the sprite sheet
		sheet = new SpriteSheet(spriteSheet);
		v += 1;
		p.setValue(v);
		// Create the image manager
		im = new ImageManager(sheet, loading, gui_popup, gui_inv);
		v += 3;
		p.setValue(v);
		LogHelper.logInfo("Assets loaded");
		// Begin loading levels
		t.setText("Loading levels...");
		initLevels(loader, p, v);
		t.setText("Spawning entities");
		LogHelper.logInfo("Loading entities...");
		player = new EntityPlayerFighter("Fighter", WIDTH * SCALE / 2 - 16, HEIGHT * SCALE / 2 - 16, 0, 0, sheet);
		v += 10;
		p.setValue(v);
		int n = 2500;
		mob = new EntityMob[n];
		for (int m = 0; m < mob.length; m++) {
			mob[m] = new EntityMob(x.nextInt(getLevel(current_level).getWidth()) * 32, y.nextInt(getLevel(current_level).getHeight()) * 32, 1, 50, sheet);
		}
		LogHelper.logInfo("Entities loaded");
		v += 10;
		p.setValue(v);
		t.setText("Finishing up...");
		
		// Load game states
		menuState = new MainMenu();
		levelState = new LevelSelect();
		gameState = new GameScreen();
		// Set the game state
		State.setState(menuState);
		// Register key listener
		this.addKeyListener(new KeyHandler());
		//Finish up
		b = System.nanoTime();
		long c = b - a;
		long d = c / 1000000000;
		LogHelper.logInfo("Game loaded in " + d + " seconds (" + c + " nano-seconds)");
		v += 10;
		// Clean up
		p.setValue(v);
		f.setVisible(false);
		f.dispose();
	}
	
	/**
	 *  Called when the game starts. Creates the game {@link Thread}
	 */
	public synchronized void start() {
		a = System.nanoTime();
		LogHelper.logInfo("Launching Game");
		LogHelper.logInfo("OS: " + System.getProperty("os.name"));
		LogHelper.logInfo("OS Version: " + System.getProperty("os.version"));
		LogHelper.logInfo("OS Archetecture: " + System.getProperty("os.arch"));
		LogHelper.logInfo("Java Version: " + System.getProperty("java.version"));
		if(running) return;
		running = true;
		paused = false;
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	/**
	 * Called when the game stops. Stops the game {@link Thread}
	 */
	public synchronized void stop() {
		if(!running)return;
		LogHelper.logInfo("Stopping!");
		
		running = false;
		System.exit(0);
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Called when the game starts. Holds the tick() and render() methods
	 */
	public void run() {
		init();
		// Ticks
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60;
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
	
	/**
	 * Called every tick. (60 per second)
	 */
	public void tick() {
		if(State.getState() != null)
			State.getState().tick();
	}
	
	/**
	 * Called as much as possible, renders the game
	 */
	public void render() {
		BufferStrategy buffer = this.getBufferStrategy();
		if(buffer == null) {
			createBufferStrategy(4);
			return;
		}
		Graphics g = buffer.getDrawGraphics();
		if(State.getState() != null)
			State.getState().render(g);
		buffer.show();
	}
	
	public RPGGame(String[] args, String user, String pass) {
		if (username == user) {
			LogHelper.logInfo("Correct Username");
			if (password == pass) {
				LogHelper.logInfo("Correct Password");
				game = this;
				Dimension dim = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
				game.setPreferredSize(dim);
				game.setMaximumSize(dim);
				game.setMinimumSize(dim);
				
				JFrame frame = new JFrame(NAME + " " + VERSION);
				frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(resizable);
				frame.add(game);
				Dimension dim2 = Toolkit.getDefaultToolkit().getScreenSize();
				frame.setLocation(dim2.width/2-frame.getSize().width/2, dim2.height/2-frame.getSize().height/2);
				frame.setVisible(visibility);
				
				game.start();
			}
		} else {
			return;
		}
		
	}
	
	private void initLevels(ImageLoader loader, JProgressBar p, int v) {
		LogHelper.logInfo("Searching for levels to load");
		for (int x = 1; x < 7; x++) {
			LogHelper.logInfo("Searching for level " + x);
			BufferedImage ilevel;
				ilevel = loader.load("/lvl_0" + x + ".png");
				if (ilevel != null) {
					LogHelper.logInfo("Found level " + x);
					switch(x) {
						case 1:
							level_1 = new Level(ilevel, 1);
							LogHelper.logInfo("Level " + x + " loaded");
							break;
						case 2:
							level_2 = new Level(ilevel, 2);
							LogHelper.logInfo("Level " + x + " loaded");
							break;
						case 3:
							level_3 = new Level(ilevel, 3);
							LogHelper.logInfo("Level " + x + " loaded");
							break;
						case 4:
							level_4 = new Level(ilevel, 4);
							LogHelper.logInfo("Level " + x + " loaded");
							break;
						case 5:
							level_5 = new Level(ilevel, 5);
							LogHelper.logInfo("Level " + x + " loaded");
							break;
						case 6:
							level_6 = new Level(ilevel, 6);
							LogHelper.logInfo("Level " + x + " loaded");
							break;
						default:
							break;
					}
				}
				v += 15;
				p.setValue(v);
			}
		LogHelper.logInfo("Loaded all levels");
	}
	
	/**
	 * Returns the current level
	 * 
	 * @param level
	 * @return The current {@link Level}
	 */
	public static Level getLevel(int level) {
		switch(level) {
			case 1:
				return level_1;
			case 2:
				return level_2;
			case 3:
				return level_3;
			case 4:
				return level_4;
			case 5:
				return level_5;
			case 6:
				return level_6;
			default:
				//LogHelper.logInfo("Invalid level submitted when calling getLevel(): " + level);
				return level_1;
		}
	}
	
	/**
	 * Returns the player
	 * @return {@link EntityPlayer}
	 */
	public EntityPlayer getPlayer() {
		return player;
	}
	
	/**
	 * Returns the game's {@link ImageManager}
	 * @return {@link ImageManager}
	 */
	public static ImageManager getImageManager() {
		return im;
	}
	
	/**
	 * Returns the game
	 * @return {@link RPGGame}
	 */
	public static RPGGame getGame() {
		return game;
	}
	
	public void pause() {
		if (paused)return;
		this.paused = true;
		LogHelper.logInfo("Game Paused");
	}
	
	public void unpause() {
		if (!paused)return;
		this.paused = false;
		LogHelper.logInfo("Game Unpaused");
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public boolean isPlayerChosen() {
		return isChosen;
		
	}

}
