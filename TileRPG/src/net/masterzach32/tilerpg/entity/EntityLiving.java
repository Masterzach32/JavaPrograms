package net.masterzach32.tilerpg.entity;

public abstract class EntityLiving extends Entity {

	protected float health;
	protected float maxHealth;
	protected float mana;
	protected float maxMana;
	protected float damage;
	protected float damageplevel;
	protected int level;
	
	public EntityLiving(int x, int y,int l, float h, float m, float d) {
		super(x, y);
		this.maxHealth = h;
		this.maxMana = m;
		this.health = h;
		this.mana = m;
		this.damage = d;
		this.level = l;
	}
	
	public EntityLiving getEntityLiving() {
		return this;
	}

	public void attackEntity(int damage, Entity entity) {
		float a = this.getHealth();
		this.setHealth(a - damage);
	}
	
	public float getHealth() {
		return this.health;
	}
	
	public float getMaxHealth() {
		return this.maxHealth;
	}
	
	
	public void setHealth(float h) {
		this.health = h;
	}
	
	public void kill() {
		
	}

	public float getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(float maxMana) {
		this.maxMana = maxMana;
	}

	public float getMana() {
		return mana;
	}

	public void setMana(float mana) {
		this.mana = mana;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
