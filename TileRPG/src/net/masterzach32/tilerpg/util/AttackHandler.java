package net.masterzach32.tilerpg.util;

import java.util.List;

import net.masterzach32.tilerpg.entity.EntityMob;
import net.masterzach32.tilerpg.entity.player.EntityPlayer;
import net.masterzach32.tilerpg.main.RPGGame;

public class AttackHandler {

	public static EntityMob[] searchForEntities(int range, EntityPlayer player) { 
		List<EntityMob> l = null;
		EntityMob[] x;
		int pxo = RPGGame.getGame().getPlayer().getXo();
		int pyo = RPGGame.getGame().getPlayer().getYo();
		int dx;
		int dy;
		for (int m = 0; m < RPGGame.mob.length; m++) {
			dx = RPGGame.mob[m].xo - pxo;
			dy = RPGGame.mob[m].yo - pyo;
			//LogHelper.logInfo("" + m + " " + dx + " " + dy);
			if (true/*dx <= range && dx >= -range && dy <= range && dy >= -range*/) {
				l.add(RPGGame.mob[m]);
				LogHelper.logInfo("Mob Found: " + m + " " + dx + " " + dy);
			}
		}
		x = l.toArray(new EntityMob[l.size()]);
		LogHelper.logInfo("" + x);
		return x;
	}
}
