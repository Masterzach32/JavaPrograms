package net.masterzach32.spacerunner;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.masterzach32.spacerunner.util.OptionsFile;
import net.masterzach32.spacerunner.util.Utilities;

/**
 * This is the main class. Run this first or you will get errors.
 * 
 * @author Zach Kozar
 */
public class Game {
	
	private static JFrame window;
	
	public static void main(String[] args) {
		try {
			SpaceRunner.logger.logInfo("Launching SpaceRunner Build " + SpaceRunner.VERSION);
			SpaceRunner.logger.logInfo("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.version") + ")");
			SpaceRunner.logger.logInfo("OS Archetecture: " + System.getProperty("os.arch"));
			SpaceRunner.logger.logInfo("Java Version: " + System.getProperty("java.version"));
			// makes our ui for the update system look nice :)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SpaceRunner.logger.logInfo("Creating JFrame");
			window = new JFrame("SpaceFighters - " + SpaceRunner.VERSION + " Beta");
			OptionsFile.load();
			SpaceRunner.game = new SpaceRunner();
			resizeGameFrame(false);
			window.setContentPane(SpaceRunner.game.getPane());
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(false);
			window.pack();
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			Utilities.createErrorDialog("Error", "An unexpected error occured: " + e.toString(), e);
		}
	}
	
	/**
	 * Resizes the game frame and recenters it on the screen
	 * @param forceResize
	 */
	public static void resizeGameFrame(boolean forceResize) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension((int) (SpaceRunner.WIDTH * SpaceRunner.SCALE), (int) (SpaceRunner.HEIGHT * SpaceRunner.SCALE + 20));
		// calculate the top left corner of the windows position on the screen
		int x = (int) ((screenSize.width / 2) - (frameSize.width / 2));
		int y = (int) ((screenSize.height / 2) - (frameSize.height / 2));
		if (forceResize) window.setSize(frameSize);
		else window.setPreferredSize(frameSize);
		SpaceRunner.TOP = x;
		SpaceRunner.LEFT = y;
		window.setLocation(SpaceRunner.TOP, SpaceRunner.LEFT);
	}

	public static JFrame getFrame() {
		return window;
	}
}