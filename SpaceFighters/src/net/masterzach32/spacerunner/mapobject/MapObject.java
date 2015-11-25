package net.masterzach32.spacerunner.mapobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.json.simple.JSONObject;

/**
 * The main entity class
 * 
 * @author Zach Kozar
 */
public abstract class MapObject {

	public String name;
	public double x, y;
	/** Image with and height */
	public int width, height;
	/** Hitbox width and height */
	public int cwidth, cheight;

	public Rectangle hitbox;
	public BufferedImage image;

	public int health;

	public boolean facingRight, remove = false;

	public MapObject(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;

		width = 60;
		height = 60;
		cwidth = 40;
		cheight = 40;
		hitbox = new Rectangle((int) x, (int) y, cwidth, cheight);
	}

	/**
	 * Creates a new hitbox
	 * 
	 * @return
	 */
	public Rectangle getHitbox() {
		return new Rectangle((int) (x + ((width - cwidth) / 2)), (int) (y + ((height - cheight) / 2)), cwidth, cheight);
	}

	public boolean intersects(MapObject object) {
		return getHitbox().intersects(object.getHitbox());
	}
	
	public boolean hit(int damage, MapObject source) {
		this.health -= damage;
		return true;
	}

	public abstract MapObject tick();

	public MapObject render(Graphics2D g) {
		if(onScreen()) {
			if(facingRight) g.drawImage(image, (int) x, (int) y, width, height, null);
			else g.drawImage(image, (int) x + width, (int) y + height, -width, -height, null);
			// g.draw(getHitbox());
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public String toString() {
		JSONObject mapobject = new JSONObject();
		mapobject.put("name", name);
		mapobject.put("health", health);
		JSONObject pos = new JSONObject();
		pos.put("x", x);
		pos.put("y", y);
		mapobject.put("position", pos);
		return mapobject.toJSONString();
	}
	
	public boolean onScreen() {
		return (x < 700);
	}

	public boolean shouldRemove() {
		return remove;
	}
}