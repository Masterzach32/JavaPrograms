package net.masterzach32.tilerpg.entity.player;

import net.masterzach32.tilerpg.entity.EntityMob;
import net.masterzach32.tilerpg.gfx.SpriteSheet;
import net.masterzach32.tilerpg.main.RPGGame;
import net.masterzach32.tilerpg.util.AttackHandler;
import net.masterzach32.tilerpg.util.LogHelper;

public class EntityPlayerFighter extends EntityPlayer {

	public EntityPlayerFighter(String name, int x, int y, int xo, int yo, SpriteSheet ss) {
		super(name, x, y, xo, yo);
	}
	
	public long cd1, cd2, cd3, cd4;
	public long c1, c2, c3, c4;

	@Override
	public void abilityA() {
		int cost = this.level * 1 + 5;
		c1 = System.nanoTime() - cd1;
		if (this.mana < cost || c1 < 5000000000L) {
			LogHelper.logInfo("Cannot be casted right now");
			return;
		}
		LogHelper.logInfo("Basic ability 1 activated");
		cd1 = System.nanoTime();
		this.mana -= cost;
		// do ability
		EntityMob[] x = AttackHandler.searchForEntities(100, RPGGame.getGame().getPlayer());
		for (int m = 0; m < RPGGame.mob.length; m++) {
			x[m].attackEntity((int) (RPGGame.getGame().getPlayer().getDamage() * 1.2), RPGGame.getGame().getPlayer());
		}
	}

	@Override
	public void abilityB() {
		int cost = this.level * 2 + 5;
		c2 = System.nanoTime() - cd2;
		if (this.mana < cost || c2 < 10000000000L) {
			LogHelper.logInfo("Cannot be casted right now");
			return;
		}
		LogHelper.logInfo("Basic ability 2 activated");
		cd2 = System.nanoTime();
		this.mana -= cost;
	}

	@Override
	public void abilityC() {
		int cost = this.level * 2 + 15;
		c3 = System.nanoTime() - cd3;
		if (this.mana < cost || c3 < 8000000000L) {
			LogHelper.logInfo("Cannot be casted right now");
			return;
		}
		LogHelper.logInfo("Basic ability 3 activated");
		cd3 = System.nanoTime();
		this.mana -= cost;
	}

	@Override
	public void abilityD() {
		int cost = this.level * 3 + 30;
		c4 = System.nanoTime() - cd4;
		if (this.mana < cost || c4 < 30000000000L) {
			LogHelper.logInfo("Cannot be casted right now");
			return;
		}
		LogHelper.logInfo("Basic ability 4 activated");
		cd4 = System.nanoTime();
		this.mana -= cost;
	}

}
