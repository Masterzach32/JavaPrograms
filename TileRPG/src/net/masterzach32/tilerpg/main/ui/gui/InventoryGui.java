package net.masterzach32.tilerpg.main.ui.gui;

import java.awt.Color;
import java.awt.Graphics;

import net.masterzach32.tilerpg.entity.player.EntityPlayer;
import net.masterzach32.tilerpg.gfx.ImageManager;
import net.masterzach32.tilerpg.main.RPGGame;

public class InventoryGui extends Gui {
	
	private ImageManager im = RPGGame.getImageManager();

	public InventoryGui(String name, boolean visibility) {
		super(name, visibility);
	}

	@Override
	public void render(String name, Graphics g, EntityPlayer player) {
		g.drawImage(im.GUI_02, (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2, im.GUI_02.getWidth(), im.GUI_02.getHeight(), null);
		g.setColor(Color.GRAY);
		g.drawString(name, (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 6, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 13);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString("Items", (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 130, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 32);
		g.drawString("Player Stats", (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 335, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 32);
		g.drawString("Level / XP: " + RPGGame.getGame().getPlayer().getLevel() + " (" + RPGGame.getGame().getPlayer().getXp() + "/" + (int) RPGGame.getGame().getPlayer().getMaxXp() + ")", (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 280, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 80);
		g.drawString("Health: " + (int) RPGGame.getGame().getPlayer().getHealth() + "/" + (int) RPGGame.getGame().getPlayer().getMaxHealth(), (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 280, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 60);
		g.drawString("Mana: " + (int) RPGGame.getGame().getPlayer().getMana() + "/" + (int) RPGGame.getGame().getPlayer().getMaxMana(), (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 280, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 100);
		g.drawString("Attack: " + (int) RPGGame.getGame().getPlayer().getDamage(), (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 280, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 120);
		g.drawString("Speed: " + (int) RPGGame.getGame().getPlayer().getSpeed(), (RPGGame.WIDTH * RPGGame.SCALE) / 2 - im.GUI_02.getWidth() / 2 + 280, (RPGGame.HEIGHT * RPGGame.SCALE) / 2 - im.GUI_02.getHeight() / 2 + 140);
	}

	@Override
	public void tick() {}

}
