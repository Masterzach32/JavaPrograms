package net.masterzach32.circlegame.util;

public class LogHelper {

	public static void logInfo(String s) {
		System.out.println("[" + Utilities.getTime() + "] " + "[INFO] " + s);
	}
	
	public static void logWarning(String s) {
		System.out.println("[" + Utilities.getTime() + "] " + "[WARNING] " + s);
	}
	
	public static void logError(String s) {
		System.out.println("[" + Utilities.getTime() + "] " + "[ERROR] " + s);
	}
	
}
