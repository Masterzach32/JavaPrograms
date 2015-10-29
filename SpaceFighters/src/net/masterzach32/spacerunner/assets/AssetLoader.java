package net.masterzach32.spacerunner.assets;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.masterzach32.spacerunner.SpaceRunner;
import net.masterzach32.spacerunner.util.*;

public class AssetLoader {

	private String s = "[ASSETS] ";
	
	/**
	 * Loads an image from the assets folder
	 * @param path
	 * @return {@link BufferedImage}
	 */
	public BufferedImage loadImage(String path) {
		BufferedImage bi;
		try {
			URL imageLocation = getClass().getResource(path);
			bi = ImageIO.read(imageLocation);
			SpaceRunner.logger.logInfo(s + "Loaded Image: " + path);
			return bi;
		} catch (Exception e) {
			SpaceRunner.logger.logWarning(s + "Missing Image: " + path + ".");
			Utilities.createErrorDialog("Missing Image Asset!", "SpaceFighters can't seem to find this asset: " + path, e);
			return null;
		}
	}
	
	/**
	 * Loads the given audio file
	 * @param path
	 * @return ais
	 */
	public AudioInputStream loadAudio(String path) {
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
			SpaceRunner.logger.logInfo(s + "Loaded Audio File: " + path);
			return ais;
		} catch (UnsupportedAudioFileException e) {
			SpaceRunner.logger.logError(s + "Unsupported Audio File: " + path);
			e.printStackTrace();
			Utilities.createErrorDialog("Unsupported Audio File!", "SpaceFighters can't use this asset: " + path, e);
			return null;
		} catch (Exception e) {
			SpaceRunner.logger.logWarning(s + "Missing Audio File: " + path);
			Utilities.createErrorDialog("Missing Audio Asset!", "SpaceFighters can't seem to find this asset: " + path, e);
			return null;
		}
	}
	
	public String loadFile(String path) {
		URL imageLocation = getClass().getResource(path);
		if(imageLocation != null) {
			SpaceRunner.logger.logInfo(s + "Loaded Map: " + path);
			return path;
		} else {
			SpaceRunner.logger.logWarning(s + "Missing Asset: " + path);
			return null;
		}
	}
}
