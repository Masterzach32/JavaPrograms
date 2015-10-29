package net.masterzach32.spacerunner.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import net.masterzach32.spacerunner.SpaceRunner;

/**
 * Class responsible for reading repository settings file and getting latest version.
 * 
 * @author Zach Kozar
 */
public class RepoSettings {
	
	/**
	 * OPTIONS_VERSION identifies the version of the options file format.
	 * Increase this value whenever incompatible changes are made to the 
	 * options file format. (Just adding a new JSON field will not
	 * break compatibility).
	 */
	public static final int REPO_VERSION = 1;
	
	public String name, version, location;
	
	public String updateVersion, updateURL;
	public String[] updateChanges;

	public RepoSettings(String location) {
		this.location = location;
	}

	public boolean parseOptionsFromJSON(String json) {
		Object obj = null;
		JSONObject repoSettings;

		// parse the JSON string and get the top-level JSONObject
		try {
			obj = JSONValue.parseWithException(json);
		} catch (ParseException e) {
			SpaceRunner.logger.logError("Error while parsing repo settings: " + e.toString());
			return false;
		}
		if (!(obj instanceof JSONObject)) {
			// give up!
			SpaceRunner.logger.logError("Repo file does not begin with a JSON Object");
			return false;
		}
		repoSettings = (JSONObject)obj;

		// Check the options file version
		Integer version = JSONHelper.getInteger(repoSettings, "repoVersion");
		if (version == null) {
			SpaceRunner.logger.logWarning("Attempting to read repo settings file without a value for 'repoVersion'");
		}
		else if (version > REPO_VERSION) {
			// a higher version number indicates an incompatible file that
			// this version of the game does not know how to read
			SpaceRunner.logger.logWarning("Could not read options file from a newer version of the game: " + repoSettings.get("gameVersion"));
			return false;
		}

		// Check for each option and update if present
		String s;
		
		s = JSONHelper.getString(repoSettings, "gameName");
		if (s != null) this.name = s;
		s = JSONHelper.getString(repoSettings, "repoVersion");
		if (s != null) this.version = s;
		
		JSONObject update = JSONHelper.getJSONObject(repoSettings, "updateInfo");
		s = JSONHelper.getString(update, "version");
		if (s != null) this.updateVersion = s;
		s = JSONHelper.getString(update, "serverFile");
		if (s != null) this.updateURL = s;
		
		return true;
	}

	/**
	 * Thanks to StackOverflow user barjak for the example of reading an entire file to a String at
	 * <http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file>
	 */
	public boolean load() {
		RandomAccessFile fin = null;
		String path = location;
		byte[] buffer;
		
		SpaceRunner.logger.logInfo("Loading repo settings file");
		try {
			// File optionsFile = new File(path);
			fin = new RandomAccessFile(path, "r");		// "r" = open file for reading only
			buffer = new byte[(int) fin.length()];
			fin.readFully(buffer);
		} catch (FileNotFoundException e) {
			// ignore missing options file
			return false;
		} catch (IOException e) {
			SpaceRunner.logger.logError("Problem reading " + path);
			e.printStackTrace();
			return false;
		} finally {
			Utilities.closeStream(fin);
		}
		
		String json = new String(buffer);
		return parseOptionsFromJSON(json);
	}
}