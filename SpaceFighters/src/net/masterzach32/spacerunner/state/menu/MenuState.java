package net.masterzach32.spacerunner.state.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import net.masterzach32.lib.*;
import net.masterzach32.lib.assets.Background;
import net.masterzach32.lib.util.Utilities;
import net.masterzach32.spacerunner.SpaceFighters;
import net.masterzach32.spacerunner.assets.*;

public class MenuState extends GameState {

	public int currentChoice = 0;
	public static String[] options = { "play", "help", "credits", "quit" };

	protected static Color titleColor;
	protected static Font titleFont;
	protected static Font subtitleFont;

	protected static Font font;
	protected static Font selectfont;

	public void init() {
		bg = new Background(Assets.getImageAsset("space_bg"), 1);
		bg.setVector(-.2, 0);

		titleColor = new Color(160, 0, 0);
		Font f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File(Assets.getFile("cyberspace")));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		titleFont = f.deriveFont(Font.PLAIN, 30);
		subtitleFont = f.deriveFont(Font.PLAIN, 18);

		font = f.deriveFont(Font.PLAIN, 26);
		selectfont = f.deriveFont(Font.PLAIN, 28);
	}

	protected void load() {
	}

	protected void unload() {
	}

	public void tick() {
		bg.tick();
	}

	public void render(Graphics2D g) {
		// draw bg
		bg.render(g);

		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		Utilities.drawCenteredString(g, "space fighters", 45);
		g.setFont(subtitleFont);

		// draw menu options
		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setFont(selectfont);
				g.setColor(Color.BLUE);
			} else {
				g.setFont(font);
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 5, (530 + i * 55) / 2);
		}
		g.setColor(Color.WHITE);
		g.setFont(font.deriveFont(Font.PLAIN, 17));
		FontMetrics fontMetrics = g.getFontMetrics();
		String enemiesKilled = new String("enemies killed: " + SpaceFighters.enemiesKilled);
		String gamesPlayed = new String("games played: " + SpaceFighters.gamesPlayed);
		g.drawString(enemiesKilled, SpaceFighters.WIDTH - fontMetrics.stringWidth(enemiesKilled) - 8, 310);
		g.drawString(gamesPlayed, SpaceFighters.WIDTH - fontMetrics.stringWidth(gamesPlayed) - 8, 330);
	}

	private void select() {
		if (currentChoice == 0)
			GameState.setState(SpaceFighters.level);
		if (currentChoice == 1)
			GameState.setState(SpaceFighters.help);
		if (currentChoice == 2)
			GameState.setState(SpaceFighters.info);
		if (currentChoice == 3)
			SpaceFighters.game.stop();
	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			select();
		}
		if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}

	public void keyReleased(int k) {}

	public void mousePressed(int k) {}

	public void mouseReleased(int k) {}
}