package net.masterzach32.lib.json;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import net.masterzach32.lib.CoreLib;

/**
 * Just some helper methods for parsing json files.
 * 
 * @author Zach Kozar
 */
public class JSONHelper {
	
	public static JSONObject getTopLevelJSONObject(String json) {
		Object data = null;
		try {
			data = JSONValue.parseWithException(json);
		} catch (ParseException e) {
			CoreLib.getGame().getLogger().logInfo("Error while parsing JSON: " + json);
		}
		if (!(data instanceof JSONObject)) {
			CoreLib.getGame().getLogger().logInfo("Error while parsing JSON: " + json);
		}
		return (JSONObject) data;
	}

	/**
	 * Attempts to retrieve an integer value from obj with the given key/name.
	 * Returns null if the key is not present or has a non-numeric value.
	 * (Floating-point values will be rounded or truncated).
	 */
	public static Double getDouble(JSONObject obj, String key) {
		Object keyobj = obj.get(key);
		if (keyobj instanceof java.lang.Double) {
			return new Double(((Number) keyobj).doubleValue());
		} else return null;
	}

	/**
	 * Attempts to retrieve an integer value from obj with the given key/name.
	 * Returns null if the key is not present or has a non-numeric value.
	 * (Floating-point values will be rounded or truncated).
	 */
	public static Integer getInteger(JSONObject obj, String key) {
		Object keyobj = obj.get(key);
		if (keyobj instanceof java.lang.Number) {
			return new Integer(((Number) keyobj).intValue());
		} else return null;
	}

	/**
	 * Attempts to retrieve a boolean value from obj with the given key/name.
	 * Returns null if the key is not present or has a non-boolean value.
	 */
	public static Boolean getBoolean(JSONObject obj, String key) {
		Object keyobj = obj.get(key);
		if (keyobj instanceof java.lang.Boolean) {
			return (Boolean) keyobj;
		} else return null;
	}

	/**
	 * Attempts to retrieve a String value from obj with the given key/name.
	 * Returns null if the key is not present or has a non-string value.
	 */
	public static String getString(JSONObject obj, String key) {
		Object keyobj = obj.get(key);
		if (keyobj instanceof java.lang.String) {
			return (String) keyobj;
		} else return null;
	}

	/**
	 * Attempts to retrieve a JSONObject from obj with the given key/name.
	 * Returns null if the key is not present or it is not a JSONObject.
	 */
	public static JSONObject getJSONObject(JSONObject obj, String key) {
		Object keyobj = obj.get(key);
		if (keyobj instanceof JSONObject) {
			return (JSONObject) keyobj;
		} else return null;
	}
}