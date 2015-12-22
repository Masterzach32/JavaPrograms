package net.masterzach32.spacerunner.mapobject;

import java.awt.Graphics2D;

import org.json.simple.JSONObject;

import net.masterzach32.lib.json.JSONHelper;
import net.masterzach32.spacerunner.assets.Assets;
import net.masterzach32.spacerunner.state.LevelState;

/**
 * pew pew!
 * 
 * @author Zach Kozar
 */
public class Lazer extends MapObject {

	public int dx = 14;
	public int timer;
	public boolean blue;
	private MapObject source;

	public Lazer(MapObject source, boolean right, boolean blue, double x, double y) {
		super("Lazer", x, y);
		id = 3;

		width = 20;
		height = 8;

		cwidth = 20;
		cheight = 8;

		timer = 45;

		this.source = source;
		this.facingRight = right;
		this.blue = blue;

		if (!blue) image = Assets.getImageAsset("lazer");
		else image = Assets.getImageAsset("lazer_blue");
		if (!right) dx = -dx;
	}

	public Lazer(JSONObject jsonObject) {
		super(jsonObject);
		
		width = 20;
		height = 8;

		cwidth = 20;
		cheight = 8;

		timer = JSONHelper.getInteger(jsonObject, "time");

		this.source = getTypeFromID(JSONHelper.getInteger(jsonObject, "source"));
		this.facingRight = JSONHelper.getBoolean(jsonObject, "right");
		this.blue = JSONHelper.getBoolean(jsonObject, "blue");

		if (!blue) image = Assets.getImageAsset("lazer");
		else image = Assets.getImageAsset("lazer_blue");
		if (!facingRight) dx = -dx;
	}

	public MapObject tick() {
		x += dx;
		if (timer > 0) timer--;
		if (timer == 0) remove = true;
		if (!(source instanceof Player) && LevelState.player != null && intersects(LevelState.player)) {
			remove = true;
			if(!LevelState.player.shield) {
				LevelState.player.hit(1, source);
				LevelState.player.flinching = true;
				LevelState.player.flinchTimer = System.nanoTime();
			}
		}
		for(int i = 0; i < LevelState.manager.getEnemyList().size(); i++) {
			MapObject enemy = LevelState.manager.getEnemyList().get(i);
			if(!(source instanceof Enemy) && intersects(enemy)) {
				remove = true;
				enemy.hit(1, source);
				if(blue) enemy.hit(1, source);
			}
		}
		return this;
	}

	public MapObject render(Graphics2D g) {
		super.render(g);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public String toString() {
		JSONObject mapobject = new JSONObject();
		mapobject.put("class", this.getClass().getSimpleName());
		mapobject.put("name", name);
		mapobject.put("h", health);
		mapobject.put("x", x);
		mapobject.put("y", y);
		mapobject.put("id", id);
		mapobject.put("right", facingRight);
		mapobject.put("blue", blue);
		mapobject.put("source", source.id);
		mapobject.put("time", timer);
		return mapobject.toJSONString();
	}
}