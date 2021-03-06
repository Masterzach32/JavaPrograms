package net.masterzach32.spacerunner;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.masterzach32.lib.*;
import net.masterzach32.lib.logging.Console;
import net.masterzach32.lib.util.Utilities;
import net.masterzach32.spacerunner.util.*;

/**
 * This is the main class. Run this first or you will get errors.
 * 
 * @author Zach Kozar
 */
public class Launcher {
	
	private static JFrame window;
	
	public static void main(String[] args) {
		try {
			// makes our ui for the update system look nice :)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			window = new JFrame("SpaceFighters - " + SpaceFighters.VERSION + " Beta");
			SpaceFighters.game = new SpaceFighters();
			if(SpaceFighters.isConsoleEnabled) new Console(SpaceFighters.game);
			SpaceFighters.lib = new CoreLib(SpaceFighters.game);
			SpaceFighters.logger.logInfo("Creating JFrame");
			OptionsFile.load();
			resizeGameFrame(false);
			window.setContentPane(SpaceFighters.game.getPane());
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(false);
			window.pack();
			SpaceFighters.game.tickAndRender.start();
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
		Dimension frameSize = new Dimension((int) (SpaceFighters.WIDTH * SpaceFighters.SCALE), (int) (SpaceFighters.HEIGHT * SpaceFighters.SCALE + 20));
		// calculate the top left corner of the windows position on the screen
		int x = (int) ((screenSize.width / 2) - (frameSize.width / 2));
		int y = (int) ((screenSize.height / 2) - (frameSize.height / 2));
		if (forceResize) window.setSize(frameSize);
		else window.setPreferredSize(frameSize);
		SpaceFighters.TOP = x;
		SpaceFighters.LEFT = y;
		window.setLocation(SpaceFighters.TOP, SpaceFighters.LEFT);
	}

	public static JFrame getFrame() {
		return window;
	}
}