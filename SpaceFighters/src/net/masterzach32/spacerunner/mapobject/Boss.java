package net.masterzach32.spacerunner.mapobject;

import net.masterzach32.spacerunner.assets.Assets;

public class Boss extends MapObject {

	public Boss(double x, double y) {
		super("Boss", x, y);
		
		width = 60;
		height = 100;
		
		cwidth = 60;
		cheight = 100;
		
		image = Assets.getImageAsset("boss");
	}

	public MapObject tick() {
		if (x < 575) x += 2;
		return this;
	}
}