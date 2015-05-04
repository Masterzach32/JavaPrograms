package net.masterzach32.tilerpg.main.ui.gui;

import java.awt.Graphics;

import net.masterzach32.tilerpg.entity.player.EntityPlayer;

public abstract class Gui {
	
	public static final InventoryGui inv = new InventoryGui("Inventory", false);
	public static final PopupGui popup = new PopupGui("Alert", "", "", false);
	public static final PauseGui pause_menu = new PauseGui("Menu", false);
	
	protected String name;
	protected boolean showGui;
	
	public Gui(String name, boolean visibility) {
		this.name = name;
		this.showGui = visibility;
	}

	public abstract void render(String name, Graphics g, EntityPlayer player);
	
	public abstract void tick();
	
	public String getName() {
		return this.name;
	}
	
	public boolean getVisibility() {
		return this.showGui;
	}

	public void showGui() {
		this.showGui = true;
	}
	
	public void hideGui() {
		this.showGui = false;
	}
	
}
