package net.masterzach32.lib;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A class with some constants
 * 
 * @author Zach Kozar
 */
public interface Game {
	
	public int getWidth();
	
	public int getHeight();
	
	public int getScale();
	
	public boolean isUpdateEnabled();
	
	public JFrame getWindow();
	
	public String getVersion();
	
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
		LogHelper.logger.logInfo("Checking for updates");
		Path p = Paths.get(OSUtils.getHomeDirectory("repo_settings.json"));
		Utilities.download(game.getRepoURL() + game.getPackageName() + "/settings.json", p.toString(), "", false);
		RepoSettings repo = new RepoSettings(p.toString());
		repo.load();
		if(repo.updateVersion != null) {
			LogHelper.logger.logInfo("Error while checking for updates: Could not read server update file.");
			int server = Integer.parseInt(repo.updateVersion.substring(4, repo.updateVersion.length()));
			int local = Integer.parseInt(game.getVersion().substring(4, game.getVersion().length()));
			if(server > local) {
				LogHelper.logger.logInfo("An update is available, you have build " + game.getVersion() + ", Server build is " + repo.updateVersion);
				int result = JOptionPane.showConfirmDialog(game.getWindow(), (Object) "An update is available!\nLocal Build: " + game.getVersion() + " Server Build: " + repo.updateVersion + "\nDo you want to update now?", "Update Available", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					Path path = Paths.get(OSUtils.getHomeDirectory("LogHelper.jar"));
					boolean failed = Utilities.download(game.getRepoURL() + game.getPackageName() + "/downloads/" + repo.updateURL, path.toString(), "Downloading Update", true);
					if(!failed) {
						int result2 = JOptionPane.showConfirmDialog(game.getWindow(), (Object) "Update complete! Do you want to close\nthis instance and run the new build?", "Update Complete", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
						if(result2 == JOptionPane.YES_OPTION) {
							try {
								ProcessBuilder pb = new ProcessBuilder("java", "-jar", path.toString());
								pb.start();
								game.close();
								return true;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						else if(result2 == JOptionPane.NO_OPTION) return true;
					}
				}
			}
		} else {
			LogHelper.logger.logInfo("No update is available");
		}
		return false;
	}	
}