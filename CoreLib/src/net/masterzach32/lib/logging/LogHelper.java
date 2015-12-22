package net.masterzach32.lib.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.masterzach32.lib.CoreLib;
import net.masterzach32.lib.util.OSUtils;
import net.masterzach32.lib.util.Utilities;

public class LogHelper {
	
	private String name;
	private ArrayList<String> log = new ArrayList<String>();
	
	public LogHelper(String name) {
		this.name = name;
	}

	public void logInfo(String message) {
		String str = "[" + Utilities.getTime() + "] " + "[" + name + "] " + "[INFO] " + message;
		System.out.println(str);
		log.add(str);
	}
	
	public void logWarning(String message) {
		String str = "[" + Utilities.getTime() + "] " + "[" + name + "] " + "[WARNING] " + message;
		System.out.println(str);
		log.add(str);
	}
	
	public void logError(String message) {
		String str = "[" + Utilities.getTime() + "] " + "[" + name + "] " + "[ERROR] " + message;
		System.out.println(str);
		log.add(str);
	}
	
	public boolean saveLog() {
        PrintWriter writer;
        try {
    		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh-mm-ss");
    		Date date = new Date();
    		File logFolder = new File(OSUtils.getHomeDirectory("logs"));
    		if(!logFolder.exists()) {
    			logFolder.mkdir();
    		}
            writer = new PrintWriter(OSUtils.getHomeDirectory("logs/" + dateFormat.format(date) + ".log"), "UTF-8");
            for(String message : log) {
                writer.println(message);
            }
            writer.close();
            logInfo("Log saved to " + CoreLib.getGame().getPackageName() +  "/logs/" + dateFormat.format(date) + ".log");
            return true;
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            logError("Error while saving log: " + e.toString());
            return false;
        }
	}
}