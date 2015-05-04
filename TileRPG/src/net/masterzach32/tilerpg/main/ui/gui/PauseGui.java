package net.masterzach32.tilerpg.main.ui.gui;

import java.awt.Color;
import java.awt.Graphics;

import net.masterzach32.tilerpg.entity.player.EntityPlayer;
import net.masterzach32.tilerpg.gfx.ImageManager;
import net.masterzach32.tilerpg.main.RPGGame;

public class PauseGui extends Gui {
	
	private ImageManager im = RPGGame.getImageManager();

	public PauseGui(String name, boolean visibility) {
		super(name, visibility);
	}

	@Override
	public void render(String name, Graphics g, EntityPlayer player) {
		g.drawImage(im.GUI_02, (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2, im.GUI_02.getWidth(), im.GUI_02.getHeight(), null);
		g.setColor(Color.GRAY);
		g.drawString(name, (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 6, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 13);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString("Options", (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 125, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 100);
		g.drawString("Main Menu", (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 125, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 130);
		g.drawString("Quit", (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 125, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 160);
		g.drawString("Resume", (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 125, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 190);
	}

	@Override
	public void tick() {
		
	}

}