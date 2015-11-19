package net.masterzach32.spacerunner.mapobject;

import java.awt.Graphics2D;
import java.util.ArrayList;

import net.masterzach32.spacerunner.SpaceFighters;

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
				if(!(object instanceof Lazer)) object.x -= 2;
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
		synchronized (entities) {
			for (MapObject object : entities) {
				object.render(g);
			}
		}
		synchronized (enemies) {
			for (MapObject object : enemies) {
				object.render(g);
			}
		}
	}
}