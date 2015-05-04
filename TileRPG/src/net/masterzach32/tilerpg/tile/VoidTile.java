package net.masterzach32.tilerpg.tile;

import java.awt.Graphics;

import net.masterzach32.tilerpg.gfx.ImageManager;
import net.masterzach32.tilerpg.main.RPGGame;

public class VoidTile extends Tile {

	public VoidTile(ImageManager im) {
		super(im);
	}
	
	@Override
	public void tick() {}

	@Override
	public void render(Graphics g, int x, int y) {}

}
