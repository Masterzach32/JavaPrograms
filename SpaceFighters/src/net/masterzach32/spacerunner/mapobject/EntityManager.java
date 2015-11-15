package net.masterzach32.spacerunner.mapobject;

import java.awt.Graphics2D;
import java.util.ArrayList;

import net.masterzach32.spacerunner.SpaceFighters;
import net.masterzach32.spacerunner.state.LevelState;

/**
 * Keeps track of all entities besides the player
 * 
 * @author Zach Kozar
 */
public class EntityManager {

	private ArrayList<MapObject> entities;
	private ArrayList<Enemy> enemies;

	public EntityManager() {
		entities = new ArrayList<MapObject>();
		enemies = new ArrayList<Enemy>();
	}

	public ArrayList<MapObject> getEntityList() {
		return entities;
	}

	public ArrayList<Enemy> getEnemyList() {
		return enemies;
	}

	public void addEntity(MapObject object) {
		entities.add(object);
	}

	public void addEnemy(Enemy enemy) {
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
				object.x -= 2;
				if (object.x <= -100)
					object.remove = true;
				if (object.shouldRemove())
					entities.remove(i);
			}
		if (enemies != null)
			for (int i = 0; i < enemies.size(); i++) {
				Enemy object = enemies.get(i);
				object.tick();
				object.x -= 2;
				for (int j = 0; j < LevelState.player.lazers.size(); j++) {
					Lazer lazer = LevelState.player.lazers.get(j);
					if (lazer.intersects(object)) {
						lazer.remove = true;
						if (lazer.blue)
							object.health -= 2;
						else
							object.health -= 1;
					}
				}
				if (object.shouldRemove()) {
					enemies.remove(i);
					SpaceFighters.enemiesKilled++;
				}
			}
	}

	public void renderEntities(Graphics2D g) {
		for (MapObject object : entities) {
			object.render(g);
		}
		for (Enemy object : enemies) {
			object.render(g);
		}
	}
}