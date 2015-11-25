package net.masterzach32.spacerunner.mapobject;

import java.awt.Graphics2D;
import java.util.Random;

import net.masterzach32.spacerunner.SpaceFighters;
import net.masterzach32.spacerunner.assets.Assets;
import net.masterzach32.spacerunner.state.LevelState;

public class Boss extends Enemy {
	
	private Random random;

	public Boss(int level, double x, double y) {
		super("Boss", x, y);
		
		width = 100;
		height = 180;
		
		cwidth = 100;
		cheight = 180;
		
		fireTimer = 10;
		
		health = level;
		
		facingRight = true;
		
		random = new Random();
		
		image = Assets.getImageAsset("boss");
	}

	public MapObject tick() {
		if (x < 525) x += 2;
		
		if(health <= 0) {
			remove = true;
			SpaceFighters.game.addScore(500);
		}
		
		if(fireTimer > 0) 
			fireTimer--;
		else if(fireTimer == 0) {
			LevelState.manager.getEntityList().add(new Lazer(this, false, false, x, y + 20 + random.nextInt(140)));
			fireTimer = 45;
		}
		
		return this;
	}
	
	public MapObject render(Graphics2D g) {
		super.render(g);
		
		return this;
	}
}