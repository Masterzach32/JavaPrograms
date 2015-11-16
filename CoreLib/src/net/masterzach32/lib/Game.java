package net.masterzach32.lib;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;

/**
 * Any game that uses CoreLib must implement this class.
 * 
 * @author Zach Kozar
 */
public interface Game {
	
	public int getWidth();
	
	public int getHeight();
	
	public int getScale();
	
	public boolean isUpdateEnabled();
	
	public JFrame getWindow();
	
	public int getBuildNumber();
	
	public String getRepoURL();
	
	public String getPackageName();
	
	public String getName();
	
	public boolean close();
	
	/**
	 * Checks to see if their is a newer version of the game available, and downloads it to the users home directory.<br>
	 * This method does not return if the user runs the updated build
	 * @return true if update succeded, false otherwise
	 */
	public static boolean checkForUpdates(Game game) {
		if(!game.isUpdateEnabled()) {
			LogHelper.logger.logInfo("Updates are disabled. This is probably because you are running a beta or nightly build.");
			return false;
		}
		// get server settings
		LogHelper.logger.logInfo("Checking for updates");
		Path p = Paths.get(OSUtils.getHomeDirectory("repo_settings.json"));
		Utilities.download(game.getRepoURL() + game.getPackageName() + "/settings.json", p.toString(), "", false);
		RepoSettings repo = new RepoSettings(p.toString());
		repo.load();
		
		int server = repo.updateBuild;
		int local = game.getBuildNumber();
		if(server > local) {
			LogHelper.logger.logInfo("Updating to server build " + server);
			Path path = Paths.get(OSUtils.getHomeDirectory(game.getPackageName() + ".jar"));
			boolean failed = Utilities.download(game.getRepoURL() + game.getPackageName() + "/downloads/" + game.getPackageName() + "_" + repo.updateBuild + ".jar", path.toString(), "Updating " + repo.name, true);
			if(!failed) {
				try {
					ProcessBuilder pb = new ProcessBuilder("java", "-jar", path.toString());
					pb.start();
					game.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else LogHelper.logger.logInfo("No update is available");
		return false;
	}	
}