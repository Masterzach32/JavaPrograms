package net.masterzach32.circlegame.entity;

import java.awt.*;

public class Circle {
	
	protected Color c;
	protected int x, y, r;

	public Circle(Color color, int x, int y, int radius) {
		this.c = color;
		this.x = x;
		this.y = y;
		this.r = radius;
	}
	
	public void getCircle() {
		
	}
	
	public void setRadius(int radius) {
		this.r = radius;
	}
	
	public int getRadius() {
		return this.r;
	}
	
	public void drawCenteredCircle(Graphics g) {
		int px, py;
		px = this.x-(this.r/2);
		py = this.y-(this.r/2);
		g.fillOval(px, py, this.r, this.r);
	}
}
