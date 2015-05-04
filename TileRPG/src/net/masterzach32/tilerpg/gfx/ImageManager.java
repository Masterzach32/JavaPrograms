package net.masterzach32.tilerpg.gfx;

import java.awt.image.BufferedImage;

import net.masterzach32.tilerpg.util.LogHelper;

public class ImageManager {
	
	/** Player Image */
	public BufferedImage IMG_01;
	/** Grass Image */
	public BufferedImage IMG_02;
	/** Stone Image */
	public BufferedImage IMG_03;
	/** Tree Image */
	public BufferedImage IMG_04;
	/** Rock Image */
	public BufferedImage IMG_05;
	/** Wall Horizontal */
	public BufferedImage IMG_06;
	/** Wall Vertical */
	public BufferedImage IMG_07;
	/** Background */
	public BufferedImage IMG_08;
	public BufferedImage IMG_09;
	public BufferedImage IMG_10;
	
	
	/** Popup */
	public BufferedImage GUI_01;
	/** Inventory */
	public BufferedImage GUI_02;
	
	/**
	 * Loads all the images from the {@link SpriteSheet}
	 * @param ss
	 */
	public ImageManager(SpriteSheet ss, BufferedImage menu, BufferedImage popup, BufferedImage inv) {
		IMG_01 = ss.crop(0, 0, 16, 16);
		IMG_02 = ss.crop(1, 0, 16, 16);
		IMG_03 = ss.crop(2, 0, 16, 16);
		IMG_04 = ss.crop(3, 0, 16, 16);
		IMG_05 = ss.crop(4, 0, 16, 16);
		IMG_06 = ss.crop(5, 0, 16, 16);
		IMG_07 = ss.crop(6, 0, 16, 16);
		IMG_08 = menu;
		IMG_09 = ss.crop(0, 1, 16, 16);
		IMG_10 = ss.crop(0, 2, 16, 16);		
		GUI_01 = popup;
		GUI_02 = inv;
	}
}
