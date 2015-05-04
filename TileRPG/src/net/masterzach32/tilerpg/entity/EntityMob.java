package net.masterzach32.tilerpg.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import net.masterzach32.tilerpg.gfx.ImageManager;
import net.masterzach32.tilerpg.gfx.SpriteSheet;
import net.masterzach32.tilerpg.main.RPGGame;
import net.masterzach32.tilerpg.util.EntityInfoBars;
import net.masterzach32.tilerpg.util.LogHelper;

public class EntityMob extends EntityLiving {

	/** Coords for the mob */
	public int x = 0, y = 0, xo, yo, xs, ys;
	/** The {@link SpriteSheet} for the game */
	private SpriteSheet sheet;
	/** The {@link ImageManager} for the game */
	private ImageManager im = RPGGame.getImageManager();
	private RPGGame game = RPGGame.getGame();
	/** Booleans for movement */
	public boolean up = false, dn = false, lt = false, rt = false;
	/** How fast the mob moves */
	private final int SPEED = 2;
	/** The health of the mob */
	private float healthplevel;
	private Random r = new Random();
	private int m;
	
	public boolean isDead;
	
	public double deathTimer = 10;
	
	public EntityMob(int xo, int yo, int l, float health, SpriteSheet ss) {
		super(0, 0, 1, 0, 0, 0);
		this.xo = xo;
		this.yo = yo;
		this.xs = 0;
		this.ys = 0;
		this.health = health;
		this.level = l;
		this.maxHealth = health;
		sheet = ss;
	}
	
	public void tick(){
		// while alive
		int i = r.nextInt(20);
			if(i == 9) {
				this.ys = 2;
				this.xs = 0;
			} else if(i == 8) {
				this.ys = -2;
				this.xs = 0;
			} else if(i == 7) {
				this.xs = 2;
				this.ys = 0;
			} else if(i == 6) {
				this.xs = -2;
				this.ys = 0;
			} else {
				//this.xs = 0;
				//this.ys = 0;
			}
		/*
		int pxo = RPGGame.getGame().getPlayer().getXo();
		int pyo = RPGGame.getGame().getPlayer().getYo();
		int dx = pxo - xo;
		int dy = pyo - xo;
		//LogHelper.logInfo("dx: " + dx + " dy: " + dy);
		if (dx == 0) {
			m = dy;
		} else {
			m = dy / dx;
		}
		int line = m * xo - m * pxo + yo;
		int ix = pxo - xo;
		int iy = pyo - yo;
		double d0 = Math.sqrt(ix*ix + iy*iy);
		double d1 = (m * m + 1) * (pxo * pxo) - (m * m + 1) * (xo * pxo) + 2 * (xo * xo - m * xo * yo + yo * yo) - (d0 * d0);
		double a = (m * m + 1);
		double b = - (m * m + 1) * (xo);
		double c = 2 * (xo * xo - m * xo * yo + yo * yo) - (d0 * d0);
		double disc = (b*b)-(4*a*c);
		LogHelper.logInfo(""+disc);
		double d2 = ((-b) - Math.sqrt(disc)) / (2 * a);
		double d3 = ((-b) + Math.sqrt(disc)) / (2 * a);
		LogHelper.logInfo("x=" + d2 + ", " + d3);
		double d4 = m * d3 - m * pxo + yo;
		*/
		//LogHelper.logInfo("" + d);
		// while dead
		if(this.isDead == true) {
			RPGGame.mob = null;
		}
		
		move(xs, ys);
	}
	
	public void move(int xs, int ys) {
		if (!collision(xs, ys)) {
			this.xo += xs;
		}
		if (!collision(ys, ys)) {
			this.yo += ys;
		}
		//LogHelper.logInfo("Mob Location: " + (xo / 16 * 2) + ", " + (yo / 16 * 2));
	}
	
	public void render(Graphics g){
		int pxo = RPGGame.getGame().getPlayer().getXo();
		int pyo = RPGGame.getGame().getPlayer().getYo();
		int dx = xo - pxo;
		int dy = yo - pyo;
		//if ((dx / RPGGame.TILESIZE * RPGGame.SCALE) >= 25 && (dy / RPGGame.TILESIZE * RPGGame.SCALE) >= 25) {
		//	return;
		//} else {
			g.drawImage(im.IMG_09, dx, dy, RPGGame.TILESIZE * RPGGame.SCALE, RPGGame.TILESIZE * RPGGame.SCALE, null);
			EntityInfoBars.renderInfoBar("normal", this.health, this.maxHealth, 0, 0, 1, dx, dy, g);
		//}
	}
	
	public boolean collision(int xp, int yp) {
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
}
