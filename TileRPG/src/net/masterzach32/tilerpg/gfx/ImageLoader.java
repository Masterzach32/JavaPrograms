package net.masterzach32.tilerpg.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.masterzach32.tilerpg.util.LogHelper;

public class ImageLoader {

	/**
	 * Loads an image from the assets folder
	 * @param path
	 * @return {@link BufferedImage}
	 */
	public BufferedImage load(String path) {
		try {
			URL imageLocation = getClass().getResource(path);
			if(imageLocation != null) {
				return ImageIO.read(imageLocation);
			} else {
				LogHelper.logWarning("Missing image: " + path);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
