package net.masterzach32.circlegame.map;

import java.awt.Graphics2D;

public class World {
	
	private static World world;
	private static Background bg;
	
	public static final int WIDTH = 2000, HEIGHT = 2000;
	private int x, y;
	private static int xmin, ymin, xmax, ymax;

	public World() {
		world = this;
		
		//bg = new Background(Assets.getImageAsset("Background"), 1);
	}
	
	public void render(Graphics2D g) {
		//bg.render(g);
	}
	
	public static World getWorld() {
		return world;
	}
}
