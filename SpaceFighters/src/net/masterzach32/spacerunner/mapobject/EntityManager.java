package net.masterzach32.spacerunner.mapobject;

import java.awt.Graphics2D;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import net.masterzach32.lib.json.JSONHelper;
import net.masterzach32.spacerunner.SpaceFighters;
import net.masterzach32.spacerunner.mapobject.powerup.PowerUp;

/**
 * Keeps track of all entities besides the player
 * 
 * @author Zach Kozar
 */
public class EntityManager {

	private ArrayList<MapObject> entities;
	private ArrayList<MapObject> enemies;

	public EntityManager() {
		entities = new ArrayList<MapObject>();
		enemies = new ArrayList<MapObject>();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getEntitiesInJSON() {
		JSONObject objects = new JSONObject();
		
		JSONObject entities = new JSONObject();
		entities.put("size", this.entities.size());
		for(int i = 0; i < this.entities.size(); i++) 
			entities.put("" + i, this.entities.get(i));
		
		JSONObject enemies = new JSONObject();
		enemies.put("size", this.enemies.size());
		for(int i = 0; i < this.enemies.size(); i++) 
			enemies.put("" + i, this.enemies.get(i));
		
		objects.put("entities", entities);
		objects.put("enemies", enemies);
		
		return objects;
	}
	
	public void updateList(JSONObject entities, JSONObject enemies) {
		this.entities.clear();
		for(int i = 0; i < JSONHelper.getInteger(entities, "size"); i++) {
			JSONObject entity = JSONHelper.getJSONObject(entities, i + "");
			if(JSONHelper.getString(entity, "class").equals("Lazer")) this.entities.add(new Lazer(JSONHelper.getJSONObject(entities, i + "")));
			if(JSONHelper.getString(entity, "class").equals("PowerUp")) this.entities.add(new PowerUp(JSONHelper.getJSONObject(entities, i + "")));
		}
		
		this.enemies.clear();
		for(int i = 0; i < JSONHelper.getInteger(enemies, "size"); i++) {
			JSONObject entity = JSONHelper.getJSONObject(entities, i + "");
			if(JSONHelper.getString(entity, "class").equals("Enemy")) this.entities.add(new Enemy(JSONHelper.getJSONObject(entities, i + "")));
			if(JSONHelper.getString(entity, "class").equals("Boss")) this.entities.add(new Boss(JSONHelper.getJSONObject(entities, i + "")));
		}
	}

	public ArrayList<MapObject> getEntityList() {
		return entities;
	}

	public ArrayList<MapObject> getEnemyList() {
		return enemies;
	}

	public void addEntity(MapObject object) {
		entities.add(object);
	}

	public void addEnemy(MapObject enemy) {
		enemies.add(enemy);
	}

	/**
	 * Same as a tick() method.
	 */
	public void updateEntities() {
		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {
				MapObject object = entities.get(i);
				object.tick();
				if (!(object instanceof Lazer))
					object.x -= 2;
				if (object.x <= -100)
					object.remove = true;
				if (object.shouldRemove())
					entities.remove(i);
			}
		if (enemies != null)
			for (int i = 0; i < enemies.size(); i++) {
				MapObject object = enemies.get(i);
				object.tick();
				object.x -= 2;
				if (object.shouldRemove()) {
					enemies.remove(i);
					SpaceFighters.enemiesKilled++;
				}
			}
	}

	public void renderEntities(Graphics2D g) {
		for (int i = 0; i < entities.size(); i++) {
			MapObject object = entities.get(i);
			if(object != null) object.render(g);
		}
		for (int i = 0; i < enemies.size(); i++) {
			MapObject object = enemies.get(i);
			if(object != null) object.render(g);
		}
	}
}