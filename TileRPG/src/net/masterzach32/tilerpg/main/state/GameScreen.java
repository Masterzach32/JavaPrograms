package net.masterzach32.tilerpg.main.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import net.masterzach32.tilerpg.entity.EntityMob;
import net.masterzach32.tilerpg.main.RPGGame;
import net.masterzach32.tilerpg.main.ui.gui.Gui;
import net.masterzach32.tilerpg.util.LogHelper;

public class GameScreen extends State {
	
	private static int space = 15;
	private static int fontSize = 25;
	
	private static RPGGame game = RPGGame.getGame();
	// 3 + (space * 1)
	
	public GameScreen() {
		
	}

	@Override
	public void tick() {
		RPGGame.getGame().getPlayer().tick();
		for (int m = 0; m < RPGGame.mob.length; m++) {
			RPGGame.mob[m].tick();
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.fillRect(0, 0, RPGGame.WIDTH * RPGGame.SCALE, RPGGame.HEIGHT * RPGGame.SCALE);
		switch(RPGGame.current_level) {
			case 1:
				RPGGame.level_1.render(g);
				break;
			case 2:
				RPGGame.level_2.render(g);
				break;
			case 3:
				RPGGame.level_3.render(g);
				break;
			case 4:
				RPGGame.level_4.render(g);
				break;
			case 5:
				RPGGame.level_5.render(g);
				break;
			case 6:
				RPGGame.level_6.render(g);
				break;
		}
		for (int m = 0; m < RPGGame.mob.length; m++) {
			RPGGame.mob[m].render(g);
		}
		RPGGame.getGame().getPlayer().render(g);
		g.setColor(Color.WHITE);
		Font f = new Font("Dialog", Font.PLAIN, 13);
		g.setFont(f);
		//if(showInfo) {
			g.drawString("Version: " + RPGGame.VERSION, 10, 3 + (space * 1));
			g.drawString("FPS: " + RPGGame.fps, 10, 3 + (space * 2));
			g.drawString("Session ID: 48jk5-39sfg-09j43-a4dj3-aj532 (Alpha Tester)", 10, 3 + (space * 3));
			g.drawString("X: " + (RPGGame.getGame().getPlayer().getXo() + RPGGame.getGame().getPlayer().x) / (16 * RPGGame.SCALE), 10, 3 + (space * 4));
			g.drawString("Y: " + (RPGGame.getGame().getPlayer().getYo() + RPGGame.getGame().getPlayer().y) / (16 * RPGGame.SCALE), 10, 3 + (space * 5));
			//g.drawString("Level: " + RPGGame.getGame().getPlayer().getLevel() + " (" + RPGGame.getGame().getPlayer().getXp() + "/" + (int) RPGGame.getGame().getPlayer().getMaxXp() + ")", 10, 3 + (space * 6));
			//g.drawString("Health: " + (int) RPGGame.getGame().getPlayer().getHealth() + "/" + (int) RPGGame.getGame().getPlayer().getMaxHealth(), 10, 3 + (space * 7));
		//}
		if(RPGGame.getGame().getPlayer().isDead) {
			g.setColor(Color.WHITE);
			Font f2 = new Font("Dialog", Font.PLAIN, fontSize);
			g.setFont(f2);
			g.drawString("Time until respawn: " + (int) RPGGame.getGame().getPlayer().deathTimer, 505, 240 + (space * 8));
		}
		g.setFont(f);
		g.setColor(Color.WHITE);
		renderHud(g);
		if(Gui.inv.getVisibility()) {
			Gui.inv.render("Inventory", g, RPGGame.getGame().getPlayer());
		}
		if (RPGGame.getGame().isPaused()) { 
			Gui.pause_menu.render("Menu", g, RPGGame.getGame().getPlayer());
		}
		g.dispose();
	}
	
	public static void renderHud(Graphics g) {
		g.drawImage(RPGGame.hud, 0, 0, 1280, 704, null);
		g.setColor(new Color(0, 170, 0));
		
		// I know their is probably a better way to run this but this is what i came up with
		
		double h0 = RPGGame.getGame().getPlayer().getHealth();
		double h1 = RPGGame.getGame().getPlayer().getMaxHealth();
		double h2 = h0 / h1;
		double h3 = h2 * 169;
		g.fillRect(1015, 10, (int) h3, 23);
		
		g.setColor(Color.BLUE);
		double m0 = RPGGame.getGame().getPlayer().getMana();
		double m1 = RPGGame.getGame().getPlayer().getMaxMana();
		double m2 = m0 / m1;
		double m3 = m2 * 169;	
		g.fillRect(1015, 44, (int) m3, 23);
		
		g.setColor(Color.YELLOW);
		double x0 = RPGGame.getGame().getPlayer().getXp();
		double x1 = RPGGame.getGame().getPlayer().getMaxXp();
		double x2 = x0 / x1;
		double x3 = x2 * 320;	
		g.fillRect(435, 685, (int) x3, 12);
		
		g.setColor(Color.WHITE);
		g.drawString("" + (int) RPGGame.getGame().getPlayer().getHealth() + "/" + (int) RPGGame.getGame().getPlayer().getMaxHealth(), 1073, 27);
		g.drawString("" + (int) RPGGame.getGame().getPlayer().getMana() + "/" + (int) RPGGame.getGame().getPlayer().getMaxMana(), 1073, 60);
		g.drawString("Level " + RPGGame.getGame().getPlayer().getLevel() + " (" + RPGGame.getGame().getPlayer().getXp() + "/" + (int) RPGGame.getGame().getPlayer().getMaxXp() + ")", 1040, 55 + (space * 2));
		
		g.drawString(RPGGame.getGame().getPlayer().toString(), 430, 585);
		g.drawString(RPGGame.getGame().toString(), 440, 600);
	}
}
