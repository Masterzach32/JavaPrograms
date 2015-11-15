package net.masterzach32.spacerunner.mapobject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import net.masterzach32.spacerunner.SpaceFighters;
import net.masterzach32.spacerunner.assets.Assets;
import net.masterzach32.spacerunner.mapobject.powerup.PowerUp;

/**
 * Main player class
 * 
 * @author Zach Kozar
 */
public class Player extends MapObject {

	public boolean up = false, down = false, left = false, right = false;
	public boolean flinching;
	public long flinchTimer;
	public int firing = 0;

	public ArrayList<Lazer> lazers;
	public PowerUp powerUp;

	public boolean asBuff = false, atBuff = false, shield = false;
	private int asTimer, atTimer, shieldTimer;
	private final int MAX_HEALTH = 12;

	public double overHeat, overHeatBar;
	public boolean overHeated;
	public int heatTimer;
	public final int MAX_HEAT = 20;

	public Player(double x, double y) {
		super("Player", x, y);

		health = 5;
		asTimer = 300;
		atTimer = 300;
		shieldTimer = 360;

		overHeat = 0;
		overHeatBar = 0;
		heatTimer = 0;

		image = Assets.getImageAsset("spaceship");

		lazers = new ArrayList<Lazer>();
	}

	public MapObject tick() {
		// movement
		if (up) y -= 3;
		if (down) y += 3;
		if (left) x -= 2;
		if (right) x += 3;

		// window collision
		if (x < 0) x = 0;
		if (x > SpaceFighters.WIDTH - width) x = SpaceFighters.WIDTH - width;
		if (y < 0) y = 0;
		if (y > SpaceFighters.HEIGHT - height) y = SpaceFighters.HEIGHT - height;

		// random checks
		if (firing > 0) firing--;
		if (health < 0) health = 0;
		if (health >= MAX_HEALTH) health = MAX_HEALTH;

		// overheat code
		if (heatTimer < 60 || overHeated) overHeat -= .2;
		if (overHeat < 0) overHeat = 0;
		if (overHeat > MAX_HEAT) {
			overHeat = MAX_HEAT;
			overHeated = true;
			heatTimer = 180;
		}
		if (heatTimer > 0) heatTimer--;
		if (heatTimer == 0) {
			overHeated = false;
			overHeat -= (double) 5 / 24;
		}
		if (overHeatBar < 7 * overHeat) overHeatBar += .8;
		if (overHeatBar > 7 * overHeat) overHeatBar -= .8;

		// update lazers
		for (int i = 0; i < lazers.size(); i++) {
			Lazer lazer = lazers.get(i);
			lazer.tick();
			if (lazer.shouldRemove()) {
				lazers.remove(i);
			}
		}

		// check done flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000) {
				flinching = false;
			}
		}
		// update shield
		if (shield) {
			if (shieldTimer > 0) shieldTimer--;
			else {
				shieldTimer = 360;
				shield = false;
			}
		}
		// update attack buff
		if (atBuff) {
			if (atTimer > 0) atTimer--;
			else {
				atTimer = 300;
				atBuff = false;
			}
		}
		// update speed buff
		if (asBuff) {
			if (asTimer > 0) {
				asTimer--;
				overHeat = MAX_HEAT + 1;
				if (firing <= 0) {
					firing = 5;
					lazers.add(new Lazer(true, atBuff, x + (width / 2), y + (height / 2) - 4));
				}
			} else {
				asTimer = 300;
				asBuff = false;
			}
		}

		return this;
	}

	public MapObject render(Graphics2D g) {
		for (int i = 0; i < lazers.size(); i++) {
			Lazer lazer = lazers.get(i);
			lazer.render(g);
		}
		super.render(g);

		if (shield) g.drawImage(Assets.getImageAsset("shield"), (int) x + width / 2, (int) y, width, height, null);

		int r = 6 * (int) overHeat;
		int max = MAX_HEAT * 6;
		if (r > max) r = max;
		if (r < 0) r = 0;
		g.setColor(new Color(255, max - r, max - r));
		g.fillRect(10, 110, 20, (int) overHeatBar);
		if (overHeated) g.setColor(new Color(255, 0, 0));
		g.drawRect(10, 110, 20, 20 * 7 - 2);
		g.setColor(Color.WHITE);
		return this;
	}

	public void setFiring() {
		if (asBuff) return;
		if (firing <= 0 && !overHeated) {
			heatTimer = 90;
			firing = 10;
			overHeat++;
			lazers.add(new Lazer(true, atBuff, x + (width / 2), y + (height / 2) - 4));
		}
	}

	public void addPowerup(PowerUp p) {
		powerUp = p;
		if (powerUp.type == PowerUp.HEAL) usePowerup();
	}

	public void usePowerup() {
		powerUp.use();
		powerUp = null;
	}
}