package net.masterzach32.tilerpg.tile;

import java.awt.Graphics;

import net.masterzach32.tilerpg.gfx.ImageManager;
import net.masterzach32.tilerpg.main.RPGGame;

public class StoneTile extends Tile {

	public StoneTile(ImageManager im) {
		super(im);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(im.IMG_03, x, y, RPGGame.TILESIZE * RPGGame.SCALE, RPGGame.TILESIZE * RPGGame.SCALE, null);
	}

}
