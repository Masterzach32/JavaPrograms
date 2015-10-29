package net.masterzach32.spacerunner.mapobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * The main entity class
 * 
 * @author Zach Kozar
 */
public abstract class MapObject {
	
	public String name;
	public double x,  y;
	/** Image with and height */
	public int width, height;
	/** Hitbox width and height */
	public int cwidth, cheight;
	
	public Rectangle hitbox;
	public BufferedImage image;
	
	public int health;
	
	public boolean remove = false;

	public MapObject(String name, double x, double y) {
		this.name = name;
		this.x = x; 
		this.y = y;
		
		width = 60;
		height = 60;
		cwidth = 40;
		cheight = 40;
		hitbox = new Rectangle((int) x, (int)y, cwidth, cheight);
	}
	
	/**
	 * Creates a new hitbox
	 * @return
	 */
	public Rectangle getHitbox() {
		return new Rectangle((int) (x + ((width - cwidth) / 2)), (int) (y + ((height - cheight) / 2)), cwidth, cheight);
	}
	
	public boolean intersects(MapObject object) {
		return this.getHitbox().intersects(object.getHitbox());
	}
	
	public abstract MapObject tick();
	
	public MapObject render(Graphics2D g) {
		g.drawImage(image, (int) x, (int) y, width, height, null);
		return this;
	}
	
	public String toString() {
		return "Name: " + name + " Position: ( " + x + ", " + y + ")";
	}

	public boolean shouldRemove() {
		return remove;
	}
}