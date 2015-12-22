package net.masterzach32.spacerunner.mapobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.json.simple.JSONObject;

import net.masterzach32.lib.json.JSONHelper;
import net.masterzach32.spacerunner.mapobject.powerup.PowerUp;

/**
 * The main entity class
 * 
 * @author Zach Kozar
 */
public class MapObject {

	public String name;
	public double x, y;
	/** Image with and height */
	public int width, height;
	/** Hitbox width and height */
	public int cwidth, cheight;

	public Rectangle hitbox;
	public BufferedImage image;

	public int health, id;

	public boolean facingRight, remove = false;

	public MapObject(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;

		width = 60;
		height = 60;
		cwidth = 40;
		cheight = 40;
		
		id = 0;
		
		hitbox = new Rectangle((int) x, (int) y, cwidth, cheight);
	}
	
	public MapObject(JSONObject data) {
		this.name = JSONHelper.getString(data, "class");
		this.health = JSONHelper.getInteger(data, "h");
		this.x = JSONHelper.getDouble(data, "x");
		this.y = JSONHelper.getDouble(data, "y");
		this.id = JSONHelper.getInteger(data, "id");
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

	public MapObject tick() {
		return this;
	}

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
		mapobject.put("class", this.getClass().getSimpleName());
		mapobject.put("h", health);
		mapobject.put("x", x);
		mapobject.put("y", y);
		mapobject.put("id", id);
		return mapobject.toJSONString();
	}
	
	public boolean onScreen() {
		return (x < 700);
	}

	public boolean shouldRemove() {
		return remove;
	}
	
	public MapObject getTypeFromID(int id) {
		switch(id) {
			case 1:
				return new Player(0, 0);
			case 2:
				return new Enemy("", 0, 0);
			case 3:
				return new Lazer(this, false, false, 0, 0);
			case 4:
				return new Boss(0, 0, 0);
			case 5:
				return new PowerUp(0, 0, 0);
			default: 
				return this;
		}
	}
}