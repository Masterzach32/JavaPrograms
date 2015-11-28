package net.masterzach32.spacerunner.state.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import net.masterzach32.lib.*;
import net.masterzach32.lib.assets.Background;
import net.masterzach32.lib.util.Utilities;
import net.masterzach32.spacerunner.SpaceFighters;
import net.masterzach32.spacerunner.assets.Assets;

public class InfoState extends GameState {

	public static String[] info = {
			"game created and coded",
			"by zach kozar for ap",
			"computer science programing",
			"project 3. all game assets",
			"made by zach kozar.",
			"font \"cyberspace\" created by",
			"george edward purdy.",
			"",
			"thanks to beta testers:",
			"joevanni tompson      kaleb hall",
			"     jack tortland      spencer martin",
			"    wilsean wright      anthony kozar",
			"copyright masterzach32.net"
		};
	
	protected static Color titleColor;
	protected static Font titleFont;
	
	protected static Font font;
	protected static Font subtitleFont;
	
	public InfoState() {
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
		subtitleFont = f.deriveFont(Font.PLAIN, 22);
		
		font = f.deriveFont(Font.PLAIN, 18);
	}

	public void init() {}

	protected void load() {}

	protected void unload() {}
	
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
		Utilities.drawCenteredString(g, "credits", 75);

		// draw menu options
		for(int i = 0; i < info.length; i++) {
			g.setFont(font);
			g.setColor(Color.WHITE);
			Utilities.drawCenteredString(g, info[i], 105 + i*18);
		}
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ESCAPE) GameState.setState(SpaceFighters.menu);
	}

	public void keyReleased(int k) {}

	public void mousePressed(int k) {}

	public void mouseReleased(int k) {}
}