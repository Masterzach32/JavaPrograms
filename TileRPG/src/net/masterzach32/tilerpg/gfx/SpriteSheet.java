package net.masterzach32.tilerpg.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	/** The SpriteSheet */
	private BufferedImage sheet;
	
	/**
	 * Loads the {@link SpriteSheet}
	 * @param sheet
	 */
	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
	
	/**
	 * Crops an image from the {@link SpriteSheet}
	 * @param col
	 * @param row
	 * @param h
	 * @param w
	 * @return
	 */
	public BufferedImage crop(int col, int row, int h, int w) {
		return sheet.getSubimage(col * 16, row * 16, w, h);
	}
}
