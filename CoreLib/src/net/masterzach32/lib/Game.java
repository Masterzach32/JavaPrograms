package net.masterzach32.lib;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;

import net.masterzach32.lib.json.RepoSettings;
import net.masterzach32.lib.logging.LogHelper;
import net.masterzach32.lib.util.OSUtils;
import net.masterzach32.lib.util.Utilities;

/**
 * Any game that uses CoreLib must implement this class.
 * 
 * @author Zach Kozar
 */
public interface Game {

	public int getWidth();

	public int getHeight();

	public int getScale();

	public int getBuildNumber();

	public boolean close();

	public boolean isUpdateEnabled();

	public String getRepoURL();

	public String getPackageName();

	public String getName();

	public JFrame getWindow();

	public LogHelper getLogger();

	/**
	 * Checks to see if their is a newer version of the game available, and
	 * downloads it to the game install location.<br>
	 * This method does not return if the update succeeded.
	 * 
	 * @return true if update succeded, false otherwise
	 */
	public static boolean checkForUpdates(Game game) {
		if (!game.isUpdateEnabled()) {
			CoreLib.getGame().getLogger().logInfo("Updates are disabled. This is probably because you are running a beta or nightly build.");
			return false;
		}
		// get server settings
		CoreLib.getGame().getLogger().logInfo("Checking for updates");
		Path p = Paths.get(OSUtils.getHomeDirectory("repo_settings.json"));
		//game.getRepoURL() + game.getPackageName() + "/settings.json"
		Utilities.download(game.getRepoURL() + game.getPackageName() + "/settings.json", p.toString(), "Downloading Server Settings", false);
		RepoSettings repo = new RepoSettings(p.toString());
		repo.load();
		
		if (repo.updateBuild > game.getBuildNumber()) {
			CoreLib.getGame().getLogger().logInfo("Updating to server build " + repo.updateBuild);
			Path path = Paths.get(OSUtils.getHomeDirectory(game.getPackageName() + ".jar"));
			if (!Utilities.download(game.getRepoURL() + game.getPackageName() + "/downloads/" + game.getPackageName() + "_" + repo.updateBuild + ".jar", path.toString(), "Updating " + repo.name, false)) {
				try {
					ProcessBuilder pb = new ProcessBuilder("java", "-jar", path.toString());
					pb.start();
					game.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else
			CoreLib.getGame().getLogger().logInfo("No update is available");
		return false;
	}
}