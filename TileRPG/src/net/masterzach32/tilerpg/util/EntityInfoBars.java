package net.masterzach32.tilerpg.util;

import java.awt.Color;
import java.awt.Graphics;

import net.masterzach32.tilerpg.entity.Entity;
import net.masterzach32.tilerpg.main.RPGGame;

public class EntityInfoBars {
	
	/**
	 * 
	 */
	public EntityInfoBars() {
		
	}
	/**
	 * Draws the health bar above the entity
	 * @param size
	 * @param health
	 * @param maxhealth
	 * @param mana
	 * @param maxmana
	 * @param level
	 * @param x
	 * @param y
	 * @param g
	 */
	public static void renderInfoBar(String size, float health, float maxhealth, float mana, float maxmana, int level, int x, int y, Graphics g) {
		if (size == "normal") {
			g.setColor(Color.GRAY);
			g.fillRect(x - 18, y - 20, 67, 18);
			g.setColor(Color.WHITE);
			g.drawString("" + level, x - 18, y - 6);
			g.setColor(Color.GREEN);
			double h0 = health;
			double h1 = maxhealth;
			double h2 = h0 / h1;
			double h3 = h2 * 45;
			g.fillRect(x - 1, y - 18, (int) h3, 10);
			
			g.setColor(Color.BLUE);
			double m0 = mana;
			double m1 = maxmana;
			double m2 = m0 / m1;
			double m3 = m2 * 45;
			g.fillRect(x - 1, y - 8, (int) m3, 5);
		}
	}

}
