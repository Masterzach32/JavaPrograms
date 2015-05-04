package net.masterzach32.tilerpg.entity;

import java.awt.Graphics;

public abstract class Entity {
	
	public int x;
	public int y;
	protected int xo;
	protected int yo;
	protected int xs;
	protected int ys;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public Entity getEntity() {
		return this;
	}
}
