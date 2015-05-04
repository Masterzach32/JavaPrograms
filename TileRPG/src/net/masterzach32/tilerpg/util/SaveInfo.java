package net.masterzach32.tilerpg.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SaveInfo {

	public static void writeToSave(String path, String[] s) {
	    try {
	        File saveFile = new File("/player.txt");
	        FileOutputStream fos = new FileOutputStream(saveFile);
	        OutputStreamWriter osw = new OutputStreamWriter(fos);    
	        Writer w = new BufferedWriter(osw);
	        //w.write(s);
	        w.close();
	    } catch (IOException e) {
	        System.err.println("Problem writing player.txt");
	    }
	}
	
	public static String[] readFromSave(String path) {
		
		Path save = Paths.get(path);
		List<String> lines;
		String[] contents = null;
		
		try {
			lines = Files.readAllLines(save, Charset.forName("UTF-8"));
			contents = lines.toArray(new String[lines.size()]);
		} catch (IOException e) {
			e.printStackTrace();
			LogHelper.logInfo("IO Exception while reading save file: " + save);
		}
		
	    return contents;
	}
}
