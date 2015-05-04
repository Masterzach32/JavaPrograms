package net.masterzach32.tilerpg.main.state;

import java.awt.Color;
import java.awt.Graphics;

import net.masterzach32.tilerpg.gfx.ImageManager;
import net.masterzach32.tilerpg.main.RPGGame;
import net.masterzach32.tilerpg.util.Downloader;
import net.masterzach32.tilerpg.util.LogHelper;

public class MainMenu extends State{
	
	public static String[] updates = null;
	private static int i = 0;
	private static ImageManager im = RPGGame.getImageManager();
	private static int space = 15;
	// 3 + (space * 1)
	
	public MainMenu() {
		
	}
	
	public static void displayUpdates(Graphics g) {
		for (int i1 = 0; i1 < updates.length ; i1++) {
			g.drawString(updates[i1], 10, 3 + (space * (10 + i1)));
		}
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(RPGGame.getImageManager().IMG_08, 0, 0, RPGGame.WIDTH * RPGGame.SCALE, RPGGame.HEIGHT * RPGGame.SCALE, null);
		g.setColor(Color.WHITE);
		g.drawString("Version: " + RPGGame.VERSION, 10, 3 + (space * 1));
		g.drawString("FPS: " + RPGGame.fps, 10, 3 + (space * 2));
		g.drawString("Session ID: 48jk5-39sfg-09j43-a4dj3-aj532 (Alpha Tester)", 10, 3 + (space * 3));
		g.drawString("Press M to go to the main menu", 10, 3 + (space * 5));
		g.drawString("Press X level up to 95 (For testing purposes, this will be removed)", 10, 3 + (space * 6));
		g.drawString("Press O to exit", 10, 3 + (space * 7));
		g.drawString("This game is in alpha, expect bugs!", 10, 3 + (space * 8));
		displayUpdates(g);
		g.drawString("", 10, 3 + (space * 21));
		g.drawString("", 10, 3 + (space * 22));
		g.drawString("", 0, 0);
		g.drawString(RPGGame.NAME, 590, 360);
		g.drawString("Press any key to start the game!", 520, 374);
		g.dispose();
		
	}
}
