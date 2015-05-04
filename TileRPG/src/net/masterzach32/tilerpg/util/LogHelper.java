package net.masterzach32.tilerpg.util;

public class LogHelper {

	public static void logInfo(String s) {
		long n = 0; //System.nanoTime();
		System.out.println("[" + n + "] " + "[INFO]: " + s);
	}
	
	public static void logWarning(String s) {
		System.out.println("[ERROR]: " + s);
	}
	
	public static void logError(String s) {
		System.out.println("[ERROR]: " + s);
	}
}
