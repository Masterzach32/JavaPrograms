package net.masterzach32.spacerunner.mapobject;

import java.util.Random;

import net.masterzach32.spacerunner.SpaceFighters;
import net.masterzach32.spacerunner.assets.Assets;
import net.masterzach32.spacerunner.state.LevelState;

public class Boss extends MapObject {
	
	private Random random;

	public Boss(int level, double x, double y) {
		super("Boss", x, y);
		
		width = 100;
		height = 180;
		
		cwidth = 100;
		cheight = 180;
		
		health = level;
		
		facingRight = true;
		
		random = new Random();
		
		image = Assets.getImageAsset("boss");
	}

	public MapObject tick() {
		if (x < 525) x += 2;
		
		if(health < 0) {
			remove = true;
			SpaceFighters.game.addScore(500);
		}
		
		LevelState.manager.getEnemyList().add(new Lazer(this, false, false, x, random.nextInt(height)));
		
		return this;
	}
}