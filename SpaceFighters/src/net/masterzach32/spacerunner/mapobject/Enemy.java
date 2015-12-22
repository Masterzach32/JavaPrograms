package net.masterzach32.spacerunner.mapobject;

import java.awt.*;
import java.util.*;

import org.json.simple.JSONObject;

import net.masterzach32.spacerunner.SpaceFighters;
import net.masterzach32.spacerunner.assets.Assets;
import net.masterzach32.spacerunner.state.LevelState;

/**
 * Class for enemy spaceships
 * 
 * @author Zach Kozar
 */
public class Enemy extends MapObject {

	protected int fireTimer;
	private Random random;

	public Enemy(String name, double x, double y) {
		super(name, x, y);
		width = 45;
		height = 45;
		cwidth = 40;
		cheight = 25;
		
		random = new Random();

		fireTimer = random.nextInt( 240) + 200;
		
		facingRight = true;

		health = 2;
		id = 2;
		image = Assets.getImageAsset("alien");
	}

	public Enemy(JSONObject jsonObject) {
		super(jsonObject);
		
		width = 45;
		height = 45;
		cwidth = 40;
		cheight = 25;
		
		random = new Random();

		fireTimer = random.nextInt(240) + 200;
		
		facingRight = true;
		image = Assets.getImageAsset("alien");
	}

	public MapObject tick() {
		if (fireTimer > 0) fireTimer--;
		if (fireTimer == 0 && x < 800) {
			fireTimer = random.nextInt(240) + 180;
			LevelState.manager.getEntityList().add(new Lazer(this, false, false, x + (cwidth / 2), y + (cheight / 2)));
		}
		if (health <= 0) {
			remove = true;
			SpaceFighters.game.addScore(100);
		}
		if (x <= -100) {
			remove = true;
			SpaceFighters.game.addScore(-50);
		}
		return this;
	}

	public MapObject render(Graphics2D g) {
		super.render(g);
		
		g.setColor(Color.RED);
		for (int i = 0; i < health; i++) g.fillRect((int) x + i * 22 + 1, (int) y + 1, 20, 4);
		g.setColor(Color.WHITE);
		
		return this;
	}
}