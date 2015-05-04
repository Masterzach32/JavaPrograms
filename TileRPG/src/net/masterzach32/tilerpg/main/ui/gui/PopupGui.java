package net.masterzach32.tilerpg.main.ui.gui;

import java.awt.Graphics;

import net.masterzach32.tilerpg.entity.player.EntityPlayer;
import net.masterzach32.tilerpg.gfx.ImageManager;
import net.masterzach32.tilerpg.main.RPGGame;

public class PopupGui extends Gui {
	
	private String m1, m2;

	private ImageManager im = RPGGame.getImageManager();
	
	public PopupGui(String name, String m1, String m2, boolean visibility) {
		super(name, visibility);
		this.m1 = m1;
		this.m2 = m2;
	}

	@Override
	public void render(String name, Graphics g, EntityPlayer player) {
		g.drawImage(im.GUI_01, im.GUI_01.getWidth()/2-RPGGame.WIDTH/2, im.GUI_01.getHeight()/2-RPGGame.HEIGHT/2, RPGGame.WIDTH * RPGGame.SCALE, RPGGame.HEIGHT * RPGGame.SCALE, null);
		g.drawString(this.name, 0, 0);
		g.drawString(m1, 0, 0);
		g.drawString(m2, 0, 0);
	}

	@Override
	public void tick() {}

}
