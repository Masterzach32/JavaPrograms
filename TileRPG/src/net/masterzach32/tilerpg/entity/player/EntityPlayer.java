package net.masterzach32.tilerpg.entity.player;

import java.awt.Color;
import java.awt.Graphics;

import net.masterzach32.tilerpg.entity.Entity;
import net.masterzach32.tilerpg.entity.EntityLiving;
import net.masterzach32.tilerpg.gfx.ImageManager;
import net.masterzach32.tilerpg.gfx.SpriteSheet;
import net.masterzach32.tilerpg.main.RPGGame;
import net.masterzach32.tilerpg.util.EntityInfoBars;
import net.masterzach32.tilerpg.util.LogHelper;

public abstract class EntityPlayer extends EntityLiving {

	/** The {@link SpriteSheet} for the game */
	protected SpriteSheet sheet;
	/** The {@link ImageManager} for the game */
	protected ImageManager im = RPGGame.getImageManager();
	protected RPGGame game = RPGGame.getGame();
	/** Booleans for movement */
	public boolean up = false, dn = false, lt = false, rt = false;
	/** How fast the player moves */
	protected int speed;
	/** The level of the player */
	/** The xp of the player */
	protected int xp;
	/** The max xp of the player */
	protected double maxXp = 200;
	/** The health of the player */
	//protected float health;
	//protected float maxHealth;
	protected float healthplevel;
	/** The mana of the player */
	//protected float mana;
	//protected float maxMana = 100;
	protected float manaplevel;
	
	private int i;
	
	public boolean isDead;
	
	public double deathTimer = 2;
	
	/**
	 * Creates the {@link EntityPlayer}
	 * @param name
	 * @param x
	 * @param y
	 * @param xo
	 * @param yo
	 * @param ss
	 */
	public EntityPlayer(String name, int x, int y, int xo, int yo) {
		super(x, y, 1, 0, 0, 0);
		if (name == "Fighter") {
			this.health = 175;
			this.maxHealth = 175;
			this.healthplevel = 35;
			this.maxMana = 100;
			this.manaplevel = 20;
			this.speed = 5;
			this.damage = 10;
			this.damageplevel = 5;
		} else if (name == "Assassin") {
			this.health = 125;
			this.maxHealth = 125;
			this.healthplevel = 25;
			this.manaplevel = 15;
			this.maxMana = 75;
			this.speed = 7;
			this.damage = 16;
			this.damageplevel = 8;
		} else if (name == "Tank") {
			this.health = 250;
			this.maxHealth = 250;
			this.healthplevel = 55;
			this.maxMana = 60;
			this.manaplevel = 15;
			this.speed = 3;
			this.damage = 7;
			this.damageplevel = 3;
		} else if (name == "Mage") {
			this.health = 150;
			this.maxHealth = 150;
			this.healthplevel = 20;
			this.maxMana = 125;
			this.manaplevel = 25;
			this.speed = 3;
			this.damage = 8;
			this.damageplevel = 4;
		}
		this.x = x;
		this.y = y;
		this.xo = xo;
		this.yo = yo;
		this.xs = 0;
		this.ys = 0;
		this.level = 1;
	}
	
	/**
	 * Called every tick
	 */
	public void tick(){
		// When the player isnt dead
		if(!this.isDead) {
			this.xs = 0;
			this.ys = 0;
			if(this.up) {
				this.ys -= this.speed;
			} if(this.dn) {
				this.ys += this.speed;
			} if(this.lt) {
				this.xs -= this.speed;
			} if(this.rt) {
				this.xs += this.speed;
			}
			move(this.xs, this.ys);
				if (this.xp >= this.maxXp) {
					this.level++;
					if (this.level > 95) {
						this.level = 95;
					} else {
						int max = (int) this.maxXp;
						this.xp = (int) (this.xp - this.maxXp);
						this.maxXp = max * 1.1;
						this.maxHealth += this.healthplevel;
						this.health += this.healthplevel;
						this.maxMana += this.manaplevel;
						this.mana += this.manaplevel;
						this.damage += this.damageplevel;
						this.deathTimer += 1;
					//LogHelper.logInfo("Leveled Up!");
					}
			}
		}
		// when the player is dead
		if(this.health <= 0.0) {
			this.setDead();
			this.setHealth(0);
			i++;
			if (i == 60) {
				this.deathTimer -= 1;
				i = 0;
			}
			if(this.deathTimer <= 0) {
				this.deathTimer = (int) 1 * this.level;
				this.health = 100 + (50 * this.level);
			}
		}
		// handles health regen
		if(this.health > 0) {
			this.isDead = false;
			this.health += 0.08;
			this.mana += 0.1;
		}
		//if (Game.getLevel(1).getTile(xs, ys) == Tile.grass) {}
		// Keeps the players health from going over the max
		if(this.health >= this.maxHealth) {
			this.health = this.maxHealth;
		}
		if(this.mana >= this.maxMana) {
			this.mana = this.maxMana;
		}
	}
	
	/**
	 * Called every time the main render method is called
	 * @param g
	 */
	public void render(Graphics g) {
		if (this.isDead) {
		} else {
			g.drawImage(im.IMG_01, x, y, RPGGame.TILESIZE * RPGGame.SCALE, RPGGame.TILESIZE * RPGGame.SCALE, null);
			
			EntityInfoBars.renderInfoBar("normal", this.health, this.maxHealth, this.mana, this.maxMana, this.level, this.x, this.y, g);
		}
	}
	
	protected void setDead() {
		this.isDead = true;
	}

	/**
	 * Called when the player tries to move
	 * @param xs
	 * @param ys
	 */
	public void move(int xs, int ys) {
		if (!collision(xs, ys)) {
			this.xo += xs;
		}
		if (!collision(ys, ys)) {
			this.yo += ys;
		}
	}
	
	public void attackEntity(int damage, EntityLiving entity) {
		float a = entity.getHealth();
		entity.setHealth(a - damage);
	}
	
	/**
	 * Returns the x offset of the player
	 * @return xo
	 */
	public int getXo() {
		return xo;
	}
	
	/**
	 * Returns the y offset of the player
	 * @return yo
	 */
	public int getYo() {
		return yo;
	}
	
	/**
	 * Returns the level of the player
	 * @return level
	 */
	public int getLevel() {
		return this.level;
	}
	
	/**
	 * Returns the current xp of the player
	 * @return xp
	 */
	public int getXp() {
		return this.xp;
	}
	
	/**
	 * Sets the xp of the player
	 * @param xp
	 */
	public void setXp(int xp) {
		this.xp = xp;
	}
	
	/**
	 * Returns the maximum xp of the player
	 * @return maxXp
	 */
	public double getMaxXp() {
		return this.maxXp;
	}
	
	public float getHealth() {
		return this.health;
	}
	
	public float getMaxHealth() {
		return this.maxHealth;
	}
	
	public float getMana() {
		return this.mana;
	}
	
	public float getMaxMana() {
		return this.maxMana;
	}
	
	public void setHealth(float h) {
		this.health = h;
	}
	
	public float getDamage() {
		return this.damage;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	protected boolean collision(int xp, int yp) {
		if(RPGGame.getLevel(RPGGame.current_level).getTile((xo + xs + x) / (16 * RPGGame.SCALE), (yo + yp + y) / (16 * RPGGame.SCALE)).isSolid())
			return true;
		if(RPGGame.getLevel(RPGGame.current_level).getTile((xo + xs + x + RPGGame.TILESIZE * RPGGame.SCALE - 1) / (16 * RPGGame.SCALE), (yo + yp + y) / (16 * RPGGame.SCALE)).isSolid())
			return true;
		if(RPGGame.getLevel(RPGGame.current_level).getTile((xo + xs + x) / (16 * RPGGame.SCALE), (yo + yp + y + RPGGame.TILESIZE * RPGGame.SCALE - 1) / (16 * RPGGame.SCALE)).isSolid())
			return true;
		if(RPGGame.getLevel(RPGGame.current_level).getTile((xo + xs + x + RPGGame.TILESIZE * RPGGame.SCALE - 1) / (16 * RPGGame.SCALE), (yo + yp + y + RPGGame.TILESIZE * RPGGame.SCALE - 1) / (16 * RPGGame.SCALE)).isSolid())
			return true;
		return false;
	}
	
	public abstract void abilityA();
	public abstract void abilityB();
	public abstract void abilityC();
	public abstract void abilityD();
}
