package net.masterzach32.spacerunner.state;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Random;

import net.masterzach32.lib.*;
import net.masterzach32.lib.assets.Background;
import net.masterzach32.lib.util.Utilities;
import net.masterzach32.spacerunner.SpaceFighters;
import net.masterzach32.spacerunner.assets.*;
import net.masterzach32.spacerunner.mapobject.*;
import net.masterzach32.spacerunner.mapobject.powerup.PowerUp;
import net.masterzach32.spacerunner.util.*;

/**
 * The main state of the game.
 * 
 * @author Zach Kozar
 */
public class LevelState extends GameState {

	public static EntityManager manager;
	public static Player player, player2;

	public Background bg;

	// wave count
	public int wave;
	// info font
	private Font font;

	// timer before they player can go to main menu
	private int deathTimer;

	private boolean paused;

	public void init() {
		manager = new EntityManager();
		player = new Player(100, 175);

		bg = new Background(Assets.getImageAsset("space_bg"), 1);
		bg.setVector(-.3, 0);

		SpaceFighters.score = 0;
		SpaceFighters.dscore = 0;
		wave = 0;
		deathTimer = 300;

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(Assets.getFile("cyberspace"))).deriveFont(Font.PLAIN, 17);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	protected void load() {
		if(manager.getEnemyList().size() == 0 && player.health > 0) {
			Random r = new Random();
			wave++;
			player.health++;
			for (int i = 0; i < 5 * wave; i++) {
				manager.addEnemy(new Enemy("Enemy", r.nextInt(690 * wave) + 900, r.nextInt(230) + 50));
			}
			for (int i = 0; i < wave / 4; i++) {
				manager.addEntity(new PowerUp(r.nextInt(4), r.nextInt(690 * wave) + 900, r.nextInt(230) + 50));
			}
			// if(wave % 5 == 0) manager.addEnemy(new Boss(wave, r.nextInt(700 * wave) + 900, 85));
		}
	}

	protected void unload() {
		if (player.health <= 0) player.health = 1;
	}

	public void tick() {
		if (!paused) {
			bg.tick();
			player.tick();
			manager.updateEntities();
			if (manager.getEnemyList().size() == 0) load();
		}
	}

	public void render(Graphics2D g) {
		bg.render(g);
		manager.renderEntities(g);
		if (player.health > 0) player.render(g);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("wave: " + wave, 10, 25);
		g.drawString("enemies left: " + manager.getEnemyList().size(), 10, 50);
		for (int i = 0; i < player.health; i++)
			g.drawImage(Assets.getImageAsset("heart"), 10 + 23 * i, 60, 20, 20, null);
		if (player.health == 0) {
			if (deathTimer > 0) deathTimer--;
			g.setFont(font.deriveFont(Font.PLAIN, 26));
			Utilities.drawCenteredString(g, "you died!", 180);
			g.setFont(font);
			if (deathTimer == 0)
				Utilities.drawCenteredString(g, "press any key to continue", 200);
		}
		if (paused) Utilities.drawCenteredString(g, "paused", 150);
		if (player.powerUp != null) g.drawImage(player.powerUp.getIcon(), 10, 295, 50, 50, null);

		g.setColor(Color.WHITE);
		g.setFont(font);
		FontMetrics fontMetrics = g.getFontMetrics();
		String tscore;
		if (SpaceFighters.tscore > 0) tscore = new String("+" + SpaceFighters.tscore);
		else if (SpaceFighters.tscore < 0) tscore = new String("" + SpaceFighters.tscore);
		else tscore = "";
		String score = new String("your score: " + SpaceFighters.dscore);
		// score coloring
		int r = 1 * SpaceFighters.tscore / 5;
		if (r > 255) r = 255;
		if (r < 0) r = 0;
		g.setColor(new Color(255, 255 - r, 255 - r));
		g.drawString(tscore, SpaceFighters.WIDTH - fontMetrics.stringWidth(tscore) - 8, 25);
		g.setColor(Color.WHITE);
		g.drawString(score, SpaceFighters.WIDTH - fontMetrics.stringWidth(score) - 8, 330);
	}

	public void keyPressed(int k) {
		if (deathTimer == 0) {
			GameState.setState(SpaceFighters.menu);
			init();
			OptionsFile.save();
			SpaceFighters.gamesPlayed++;
		}
		if (player.health > 0) {
			if (k == KeyEvent.VK_UP) player.up = true;
			if (k == KeyEvent.VK_DOWN) player.down = true;
			if (k == KeyEvent.VK_LEFT) player.left = true;
			if (k == KeyEvent.VK_RIGHT) player.right = true;
			if (k == KeyEvent.VK_SPACE) player.setFiring();
			if (k == KeyEvent.VK_ESCAPE) GameState.setState(SpaceFighters.menu);
			if (k == KeyEvent.VK_Z) if (player.powerUp != null) player.usePowerup();
			if (k == KeyEvent.VK_P) paused = !paused;
		}
	}

	public void keyReleased(int k) {
		if (k == KeyEvent.VK_UP) player.up = false;
		if (k == KeyEvent.VK_DOWN) player.down = false;
		if (k == KeyEvent.VK_LEFT) player.left = false;
		if (k == KeyEvent.VK_RIGHT) player.right = false;
	}

	public void mousePressed(int k) {}

	public void mouseReleased(int k) {}
}