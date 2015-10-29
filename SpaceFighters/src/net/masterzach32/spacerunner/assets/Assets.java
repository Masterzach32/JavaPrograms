package net.masterzach32.spacerunner.assets;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;

import net.masterzach32.spacerunner.SpaceRunner;

public class Assets {
	
	// Anything with placeholder in front of it is a placeholder art
	
	private static HashMap<String, BufferedImage> images;
	private static HashMap<String, AudioInputStream> sounds;
	private static HashMap<String, String> files;
	
	public static void preinit() {
		images = new HashMap<String, BufferedImage>(1024);
		sounds = new HashMap<String, AudioInputStream>(1024);
		files = new HashMap<String, String>(1024);
	}
	
	public static void init() {
		// Images
		images.put("space_bg", 	SpaceRunner.al.loadImage("/backgrounds/space.png"));
		images.put("alien", 	SpaceRunner.al.loadImage("/entities/alien.png"));
		images.put("spaceship", SpaceRunner.al.loadImage("/entities/spaceship.png"));
		images.put("powerBox_0",SpaceRunner.al.loadImage("/entities/powerBox_0.png"));
		images.put("powerBox_1",SpaceRunner.al.loadImage("/entities/powerBox_1.png"));
		images.put("powerBox_2",SpaceRunner.al.loadImage("/entities/powerBox_2.png"));
		images.put("powerBox_3",SpaceRunner.al.loadImage("/entities/powerBox_3.png"));
		images.put("lazer", 	SpaceRunner.al.loadImage("/particles/lazer.png"));
		images.put("lazer_blue",SpaceRunner.al.loadImage("/particles/lazer_blue.png"));
		images.put("shield",	SpaceRunner.al.loadImage("/particles/shield.png"));
		images.put("heart", 	SpaceRunner.al.loadImage("/particles/heart.png"));
		images.put("power_0", 	SpaceRunner.al.loadImage("/particles/power_0.png"));
		images.put("power_1", 	SpaceRunner.al.loadImage("/particles/power_1.png"));
		images.put("power_2", 	SpaceRunner.al.loadImage("/particles/power_2.png"));
		images.put("power_3", 	SpaceRunner.al.loadImage("/particles/power_3.png"));

		files.put("cyberspace", "assets/fonts/cyberspace.otf");
	}
	
	/**
	 * Returns the given image asset
	 * @param s
	 * @return BufferedImage
	 */
	public static BufferedImage getImageAsset(String s) {
		return images.get(s);
	}
	
	/**
	 * Returns the given audio asset
	 * @param s
	 * @return AudioInputStream
	 */
	public static AudioInputStream getAudioAsset(String s) {
		return sounds.get(s);
	}
	
	/**
	 * Returns the given map asset
	 * @param s
	 * @return String
	 */
	public static String getFile(String s) {
		return files.get(s);
	}
}