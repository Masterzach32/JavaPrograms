package net.masterzach32.tilerpg.main.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import net.masterzach32.tilerpg.gfx.ImageManager;
import net.masterzach32.tilerpg.main.RPGGame;

public class LevelSelect extends State {
	
	private static ImageManager im = RPGGame.getImageManager();
	private static int space = 15;
	// 3 + (space * 1)
	
	public LevelSelect() {
		
	}

	@Override
	public void tick() {
		
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(im.IMG_08, 0, 0, RPGGame.WIDTH * RPGGame.SCALE, RPGGame.HEIGHT * RPGGame.SCALE, null);
		g.setColor(Color.white);
		g.drawString("Version: " + RPGGame.VERSION, 10, 3 + (space * 1));
		g.drawString("FPS: " + RPGGame.fps, 10, 3 + (space * 2));
		g.drawString("Session ID: 48jk5-39sfg-09j43-a4dj3-aj532 (Alpha Tester)", 10, 3 + (space * 3));
		g.setColor(Color.WHITE);
		Font f = new Font("Dialog", Font.PLAIN, 18);
		g.setFont(f);
		if (RPGGame.level_1 != null)
			g.drawString("Press 1 to load Level 1", 525, 340);
		if (RPGGame.level_2 != null)
			g.drawString("Press 2 to load Level 2", 525, 360);
		if (RPGGame.level_3 != null)
			g.drawString("Press 3 to load Level 3", 525, 380);
		if (RPGGame.level_4 != null)
			g.drawString("Press 4 to load Level 4", 525, 400);
		if (RPGGame.level_5 != null)
			g.drawString("Press 5 to load Level 5", 525, 420);
		if (RPGGame.level_6 != null)
			g.drawString("Press 6 to load Level 6", 525, 440);
		g.dispose();
	}
	
}
