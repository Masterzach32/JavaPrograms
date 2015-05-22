package net.masterzach32.circlegame.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import net.masterzach32.circlegame.map.World;

public class Dots extends Circle {
	
	private int t;
	private static Dots[] dots = new Dots[1024];

	public Dots(Color color, int x, int y, int radius) {
		super(color, x, y, radius);
	}
	
	public static void tick() {
		boolean b = true;
		for(int i = 0; i < dots.length; i++) {
			if(dots[i] != null) {
				dots[i].t++;
				if(dots[i].t == 100) dots[i] = null;
			} else if(dots[i] == null) {
				if(b) {
					dots[i] = spawnDot();
					b = false;
				}
			}
		}
	}
	
	public static void render(Graphics g) {
		for(int i = 0; i < dots.length; i++) {
			if(dots[i] != null) {
				g.setColor(dots[i].c);
				dots[i].drawCenteredCircle(g);
			}
		}
	}
	
	public static Dots[] getDots() {
		return dots;
	}
	
	private static Dots spawnDot() {
		Random r1, r2;
		r1 = new Random();
		r2 = new Random();
		int x = r1.nextInt(World.WIDTH);
		int y = r2.nextInt(World.HEIGHT);
		int r = 12;
		Color c = Color.RED;
		return new Dots(c, x, y, r);
	}
}
