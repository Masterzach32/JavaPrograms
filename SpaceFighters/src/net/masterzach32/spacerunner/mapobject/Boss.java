package net.masterzach32.spacerunner.mapobject;

import net.masterzach32.spacerunner.assets.Assets;

public class Boss extends MapObject {

	public Boss(double x, double y) {
		super("Boss", x, y);
		
		width = 100;
		height = 180;
		
		cwidth = 100;
		cheight = 180;
		
		facingRight = true;
		
		image = Assets.getImageAsset("boss");
	}

	public MapObject tick() {
		if (x < 525) x += 2;
		return this;
	}
}