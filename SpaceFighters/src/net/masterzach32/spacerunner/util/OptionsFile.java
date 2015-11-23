package net.masterzach32.spacerunner.util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import net.masterzach32.lib.json.JSONHelper;
import net.masterzach32.lib.util.OSUtils;
import net.masterzach32.lib.util.Utilities;
import net.masterzach32.spacerunner.SpaceFighters;

/** 
 * OptionsFile contains static methods that use the JSON.simple library
 * to manage writing and reading the game options to a file.
 */
public class OptionsFile {

	/**
	 * OPTIONS_VERSION identifies the version of the options file format.
	 * Increase this value whenever incompatible changes are made to the 
	 * options file format. (Just adding a new JSON field will not
	 * break compatibility).
	 */
	public static final int OPTIONS_VERSION = 1;
	public static final String OPTIONS_FILENAME = "options.json";
	
	@SuppressWarnings("unchecked")
	private static String optionsToJSON() {
		JSONObject gameOptions = new JSONObject();
		
		gameOptions.put("optionsVersion", OPTIONS_VERSION);
		
		gameOptions.put("gamesPlayed", SpaceFighters.gamesPlayed);
		gameOptions.put("enemiesKilled", SpaceFighters.enemiesKilled);
		gameOptions.put("highScore", SpaceFighters.highScore);
		gameOptions.put("consoleEnabled", SpaceFighters.isConsoleEnabled);
		
		JSONObject graphicsOptions = new JSONObject();
		graphicsOptions.put("scale", SpaceFighters.SCALE);
		graphicsOptions.put("width", SpaceFighters.WIDTH);
		graphicsOptions.put("height", SpaceFighters.HEIGHT);
		gameOptions.put("graphicsOptions", graphicsOptions);

		return gameOptions.toString();
	}
	
	private static boolean parseOptionsFromJSON(String json) {
		Object obj = null;
		JSONObject gameOptions, graphicsOptions;

		// parse the JSON string and get the top-level JSONObject
		try {
			obj = JSONValue.parseWithException(json);
		} catch (ParseException e) {
			SpaceFighters.logger.logError("Error while parsing game options file: " + e.toString());
			return false;
		}
		if (!(obj instanceof JSONObject)) {
			// give up!
			SpaceFighters.logger.logError("Options file does not begin with a JSON Object");
			return false;
		}
		gameOptions = (JSONObject)obj;

		// Check the options file version
		Integer version = JSONHelper.getInteger(gameOptions, "optionsVersion");
		if (version == null) {
			SpaceFighters.logger.logWarning("Attempting to read options file without a value for 'optionsVersion'");
		}
		else if (version > OPTIONS_VERSION) {
			// a higher version number indicates an incompatible file that
			// this version of the game does not know how to read
			SpaceFighters.logger.logWarning("Could not read options file from a newer version of the game: " + gameOptions.get("gameVersion"));
			return false;
		}

		// Check for each option and update if present
		Integer i; Boolean b;
		
		i = JSONHelper.getInteger(gameOptions, "highScore");
		if (i != null) SpaceFighters.highScore = i;
		i = JSONHelper.getInteger(gameOptions, "gamesPlayed");
		if (i != null) SpaceFighters.gamesPlayed = i;
		i = JSONHelper.getInteger(gameOptions, "enemiesKilled");
		if (i != null) SpaceFighters.enemiesKilled = i;
		b = JSONHelper.getBoolean(gameOptions, "consoleEnabled");
		if (b != null) SpaceFighters.isConsoleEnabled = b;
		
		graphicsOptions = JSONHelper.getJSONObject(gameOptions, "graphicsOptions");
		i = JSONHelper.getInteger(graphicsOptions, "scale");
		if (i != null) SpaceFighters.SCALE = i;
		i = JSONHelper.getInteger(graphicsOptions, "width");
		if (i != null) SpaceFighters.WIDTH = i;
		i = JSONHelper.getInteger(graphicsOptions, "height");
		if (i != null) SpaceFighters.HEIGHT = i;

		return true;
	}
	
	private static String getOptionsPath() {
		return OSUtils.getHomeDirectory(OPTIONS_FILENAME);
	}
	
	public static boolean save() {
		BufferedWriter fout = null;
		String path = getOptionsPath();
		
		SpaceFighters.logger.logInfo("Saving game options");
		try {
			// File optionsFile = new File(path);
			fout = new BufferedWriter(new FileWriter(path));
			fout.write(optionsToJSON());
		} catch (IOException e) {
			SpaceFighters.logger.logError("Problem writing " + path);
			e.printStackTrace();
			return false;
		} finally {
			Utilities.closeStream(fout);
		}

		return true;
	}

	/** 
	 * Thanks to StackOverflow user barjak for the example of reading an entire file to a String at
	 * <http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file>
	 */
	public static boolean load() {
		RandomAccessFile fin = null;
		String path = getOptionsPath();
		byte[] buffer;
		
		SpaceFighters.logger.logInfo("Loading game options");
		try {
			// File optionsFile = new File(path);
			fin = new RandomAccessFile(path, "r");		// "r" = open file for reading only
			buffer = new byte[(int) fin.length()];
			fin.readFully(buffer);
		} catch (FileNotFoundException e) {
			// ignore missing options file
			return false;
		} catch (IOException e) {
			SpaceFighters.logger.logError("Problem reading " + path);
			e.printStackTrace();
			return false;
		} finally {
			Utilities.closeStream(fin);
		}
		
		String json = new String(buffer);
		return parseOptionsFromJSON(json);
	}
}