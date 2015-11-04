package net.masterzach32.spacerunner.api;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import net.masterzach32.spacerunner.Game;
import net.masterzach32.spacerunner.SpaceRunner;
import net.masterzach32.spacerunner.util.*;

/**
 * Game Updater, v1.0
 */
public interface IUpdatable {

	public String getLocalVersion();
	
	public String getRepoURL();
	
	/**
	 * Checks to see if their is a newer version of the game available, and downloads it to the users home directory.<br>
	 * This method does not return if the user runs the updated build
	 * @return true if update succeded, false otherwise
	 */
	public static boolean checkForUpdates() {
		if(!SpaceRunner.isUpdateEnabled) {
			SpaceRunner.logger.logInfo("Updates are disabled. This is probably because you are running a beta or nightly build.");
			return false;
		}
		SpaceRunner.logger.logInfo("Checking for updates");
		Path p = Paths.get(OSUtils.getHomeDirectory("repo_settings.json"));
		Utilities.download(SpaceRunner.game.getRepoURL() + "settings.json", p.toString(), "", false);
		RepoSettings repo = new RepoSettings(p.toString());
		repo.load();
		if(repo.updateVersion != null) {
			SpaceRunner.logger.logInfo("Error while checking for updates: Could not read server update file.");
			int server = Integer.parseInt(repo.updateVersion.substring(4, repo.updateVersion.length()));
			int local = Integer.parseInt(SpaceRunner.game.getLocalVersion().substring(4, SpaceRunner.game.getLocalVersion().length()));
			if(server > local) {
				SpaceRunner.logger.logInfo("An update is available, you have build " + SpaceRunner.game.getLocalVersion() + ", Server build is " + repo.updateVersion);
				SpaceRunner.logger.logInfo("NOTE: If you are testing a beta version of the game and it prompts you to update, ignore it.");
				
				int result = JOptionPane.showConfirmDialog(Game.getFrame(), (Object) "An update is available!\nLocal Build: " + SpaceRunner.game.getLocalVersion() + " Server Build: " + repo.updateVersion + "\nDo you want to update now?", "Update Available - Build " + repo.updateVersion, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					Path path = Paths.get(OSUtils.getHomeDirectory("spacefighters.jar"));
					boolean failed = Utilities.download(SpaceRunner.game.getRepoURL() + "downloads/" + repo.updateURL, path.toString(), "Downloading Update", true);
					if(!failed) {
						int result2 = JOptionPane.showConfirmDialog(Game.getFrame(), (Object) "Update complete! Do you want to close\nthis instance and run the new build?", "Update Complete", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
						if(result2 == JOptionPane.YES_OPTION) {
							try {
								ProcessBuilder pb = new ProcessBuilder("java", "-jar", path.toString());
								pb.start();
								SpaceRunner.game.stop();
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
			SpaceRunner.logger.logInfo("No update is available");
		}
		return false;
	}	
}