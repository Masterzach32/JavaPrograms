package net.masterzach32.spacerunner.mapobject.powerup;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.json.simple.JSONObject;

import net.masterzach32.spacerunner.assets.Assets;
import net.masterzach32.spacerunner.mapobject.MapObject;
import net.masterzach32.spacerunner.state.LevelState;

/**
 * PowerUp class. Uses one generic class for all different types.
 * 
 * @author Zach Kozar
 */
public class PowerUp extends MapObject {

	public static final int HEAL = 0, ASBUFF = 1, ATBUFF = 2, SHIELD = 3, MISSILE = 3, NUKE = 4;

	public int type;
	public boolean active;

	public PowerUp(int type, double x, double y) {
		super("PowerUp_" + type, x, y);
		this.type = type;
		this.width = 40;
		this.height = 40;
		this.cwidth = 40;
		this.cheight = 40;
		facingRight = true;
		active = false;
		id = 5;
		image = Assets.getImageAsset("powerBox_" + type);
	}

	public PowerUp(JSONObject jsonObject) {
		super(jsonObject);
		this.type = 0;
		this.width = 40;
		this.height = 40;
		this.cwidth = 40;
		this.cheight = 40;
		facingRight = true;
		active = false;
		image = Assets.getImageAsset("powerBox_" + type);
	}

	public MapObject tick() {
		if (intersects(LevelState.player) && LevelState.player.powerUp == null) {
			LevelState.player.addPowerup(this);
			this.remove = true;
		}
		return this;
	}

	public void renderBar(Graphics2D g, int ticksLeft, int total) {
		int percentage = 50 * ticksLeft / total;
		g.fillRect(10, 285, percentage, 10);
		g.drawRect(10, 285, 50, 10);
	}

	public void use() {
		if (type == HEAL)
			LevelState.player.health += 2;
		if (type == ASBUFF)
			LevelState.player.asBuff = true;
		if (type == ATBUFF)
			LevelState.player.atBuff = true;
		if (type == SHIELD)
			LevelState.player.shield = true;
	}

	public BufferedImage getIcon() {
		return Assets.getImageAsset("power_" + type);
	}
}