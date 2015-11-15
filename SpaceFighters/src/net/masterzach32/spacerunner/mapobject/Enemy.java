package net.masterzach32.spacerunner.mapobject;

import java.awt.*;
import java.util.*;

import net.masterzach32.spacerunner.SpaceFighters;
import net.masterzach32.spacerunner.assets.Assets;
import net.masterzach32.spacerunner.state.LevelState;

/**
 * Class for enemy spaceships
 * 
 * @author Zach Kozar
 */
public class Enemy extends MapObject {

	public int fireTimer;

	public ArrayList<Lazer> lazers;
	public Random random;

	public Enemy(double x, double y) {
		super("Enemy", x, y);
		width = 45;
		height = 45;
		cwidth = 40;
		cheight = 25;

		random = new Random();

		fireTimer = random.nextInt(240) + 200;

		lazers = new ArrayList<Lazer>();

		health = 2;
		image = Assets.getImageAsset("alien");
	}

	public MapObject tick() {
		if (fireTimer > 0)
			fireTimer--;
		if (fireTimer == 0) {
			fireTimer = random.nextInt(240) + 200;
			lazers.add(new Lazer(false, false, x + (cwidth / 2), y + (cheight / 2)));
		}

		for (int i = 0; i < lazers.size(); i++) {
			Lazer lazer = lazers.get(i);
			lazer.tick();
			if (!LevelState.player.flinching)
				if (lazer.intersects(LevelState.player)) {
					lazer.remove = true;
					if (!LevelState.player.shield) {
						LevelState.player.health -= 1;
						LevelState.player.flinching = true;
						LevelState.player.flinchTimer = System.nanoTime();
					}
				}
			if (lazer.shouldRemove()) {
				lazers.remove(i);
			}
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
		for (Lazer lazer : lazers)
			lazer.render(g);
		
		super.render(g);
		
		g.setColor(Color.RED);
		for (int i = 0; i < health; i++)
			g.fillRect((int) x + i * 22 + 1, (int) y + 1, 20, 4);
		g.setColor(Color.WHITE);
		
		return this;
	}
}