package net.masterzach32.tilerpg.tile;

import java.awt.Graphics;

import net.masterzach32.tilerpg.gfx.ImageManager;
import net.masterzach32.tilerpg.main.RPGGame;

public class Tile {

	/** True if the {@link Tile} is solid */
	private boolean isSolid;
	protected ImageManager im;
	
	public static final Tile void_tile = new VoidTile(RPGGame.getImageManager()).setSolid();
	
	public static final Tile grass = new GrassTile(RPGGame.getImageManager());
	public static final Tile stone = new StoneTile(RPGGame.getImageManager());
	public static final Tile tree = new TreeTile(RPGGame.getImageManager()).setSolid();
	public static final Tile rock = new RockTile(RPGGame.getImageManager()).setSolid();
	public static final Tile wall_horizontal = new WallTile(RPGGame.getImageManager(), 1);
	public static final Tile wall_vertical = new WallTile(RPGGame.getImageManager(), 2);
	
	/**
	 * Creates a new {@link Tile}
	 * @param im
	 */
	public Tile(ImageManager im) {
		this.im = im;
	}
	
	/**
	 * Called when a {@link Tile} ticks
	 */
	public void tick() {
		
	}
	
	/**
	 * Called to render a {@link Tile}
	 * @param g
	 * @param x
	 * @param y
	 */
	public void render(Graphics g, int x, int y) {
		
	}
	
	/**
	 * Returns true if the {@link Tile} is solid
	 * @return true if solid, false otherwise
	 */
	public boolean isSolid() {
		return this.isSolid;
	}
	
	/**
	 * Sets the tile solid by making isSolid true
	 * @return {@link Tile}
	 */
	public Tile setSolid() {
		this.isSolid = true;
		return this;
	}
	
}
