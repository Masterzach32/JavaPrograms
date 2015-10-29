package net.masterzach32.spacerunner.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import net.masterzach32.spacerunner.SpaceRunner;
import net.masterzach32.spacerunner.assets.Assets;
import net.masterzach32.spacerunner.assets.Background;
import net.masterzach32.spacerunner.mapobject.Enemy;
import net.masterzach32.spacerunner.mapobject.EntityManager;
import net.masterzach32.spacerunner.mapobject.Lazer;
import net.masterzach32.spacerunner.mapobject.MapObject;
import net.masterzach32.spacerunner.mapobject.Player;
import net.masterzach32.spacerunner.mapobject.powerup.PowerUp;
import net.masterzach32.spacerunner.util.OptionsFile;
import net.masterzach32.spacerunner.util.Utilities;

/**
 * The main state of the game. 
 * 
 * @author Zach Kozar
 */
public class LevelState extends GameState {
	
	public static EntityManager manager;
	public static Player player;
	
	private Background bg;
	
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
		
		SpaceRunner.score = 0;
		SpaceRunner.dscore = 0;
		wave = 0;
		deathTimer = 300;
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(Assets.getFile("cyberspace"))).deriveFont(Font.PLAIN, 17);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	protected void load() {
		Random r = new Random();
		wave++;
		player.health++;
		manager.getEnemyList().clear();
		for(int i = 0; i < 5 * wave; i++) {
			manager.addEnemy(new Enemy(r.nextInt(700 * wave) + 1000, r.nextInt(230) + 50));
		}
		for(int i = 0; i < wave / 4; i++) {
			manager.addEntity(new PowerUp(r.nextInt(4), r.nextInt(700 * wave) + 1000, r.nextInt(230) + 50));;
		}
	}

	protected void unload() {
		wave--;
		player.health--;
	}

	public void tick() {
		if(!paused) {
			if(player.health > 0) {
				// update everything
				bg.tick();
				player.tick();
				manager.updateEntities();
				for(int i = 0; i < manager.getEnemyList().size(); i++) {
					Enemy object = manager.getEnemyList().get(i);
					for(int j = 0; j < player.lazers.size(); j++) {
						Lazer lazer = player.lazers.get(j);
						if(lazer.intersects(object)) {
							lazer.remove = true;
							if(lazer.blue) object.health -= 2;
							else object.health -= 1;
						}
					}
					object.x -= 2;
					if(object.x <= -100) {
						object.remove = true;
						SpaceRunner.game.addScore(-50);
					}
				}
				for(int i = 0; i < manager.getEntityList().size(); i++) {
					MapObject object = manager.getEntityList().get(i);
					object.x -= 2;
					if(object.x <= -100) {
						object.remove = true;
					}
				}

				if(manager.getEnemyList().size() == 0) load();
			}
		}
	}

	public void render(Graphics2D g) {
		bg.render(g);
		manager.renderEntities(g);
		if(player.health > 0) player.render(g);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("wave: " + wave, 10, 25);
		g.drawString("enemies left: " + manager.getEnemyList().size(), 10, 50);
		for(int i = 0; i < player.health; i++)
			g.drawImage(Assets.getImageAsset("heart"), 10 + 23 * i, 60, 20, 20, null);
		if(player.health == 0) {
			if(deathTimer > 0) deathTimer--;
			g.setFont(font.deriveFont(Font.PLAIN, 26));
			Utilities.drawCenteredString(g, "you died!", 150);
			g.setFont(font);
			if(deathTimer == 0) Utilities.drawCenteredString(g, "press any key to continue", 170);
		}
		if(paused) Utilities.drawCenteredString(g, "paused", 150);
		if(player.powerUp != null) g.drawImage(player.powerUp.getIcon(), 10, 295, 50, 50, null);
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		FontMetrics fontMetrics = g.getFontMetrics();
		String tscore;
		if(SpaceRunner.tscore > 0) tscore = new String("+" + SpaceRunner.tscore);
		else if (SpaceRunner.tscore < 0) tscore = new String("" + SpaceRunner.tscore);
		else tscore = "";
		String score = new String("your score: " + SpaceRunner.dscore);
		// score coloring
		int r = 1*SpaceRunner.tscore/5;
		if(r > 255) r = 255;
		if(r < 0) r = 0;
		g.setColor(new Color(255, 255 - r, 255 - r));
		g.drawString(tscore, SpaceRunner.WIDTH - fontMetrics.stringWidth(tscore) - 8, 25);
		g.setColor(Color.WHITE);
		g.drawString(score, SpaceRunner.WIDTH - fontMetrics.stringWidth(score) - 8, 330);
	}

	public void keyPressed(int k) {
		if(deathTimer == 0) {
			GameState.setState(SpaceRunner.menu);
			init();
			OptionsFile.save();
			SpaceRunner.gamesPlayed++;
		}
		if(k == KeyEvent.VK_UP) player.up = true;
		if(k == KeyEvent.VK_DOWN) player.down = true;
		if(k == KeyEvent.VK_LEFT) player.left = true;
		if(k == KeyEvent.VK_RIGHT) player.right = true;
		if(k == KeyEvent.VK_SPACE) player.setFiring();
		if(k == KeyEvent.VK_ESCAPE) GameState.setState(SpaceRunner.menu);
		if(k == KeyEvent.VK_Z) if(player.powerUp != null)player.usePowerup();
		if(k == KeyEvent.VK_P) paused = !paused;
	}

	public void keyReleased(int k) {
		if(k == KeyEvent.VK_UP) player.up = false;
		if(k == KeyEvent.VK_DOWN) player.down = false;
		if(k == KeyEvent.VK_LEFT) player.left = false;
		if(k == KeyEvent.VK_RIGHT) player.right = false;
	}

	public void mousePressed(int k) {}

	public void mouseReleased(int k) {}
}