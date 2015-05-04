package net.masterzach32.circlegame.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import net.masterzach32.circlegame.map.World;

public class Dots {
	
	private int x, y, t;
	private Color color;
	private static Dots[] dots = new Dots[1024];
	private Random r;

	public Dots() {
		r = new Random();
		this.x = r.nextInt(World.WIDTH);
		r = new Random();
		this.y = r.nextInt(World.HEIGHT);
		this.color = Color.BLUE;
	}
	
	public static void tick() {
		boolean b = true;
		for(int i = 0; i < dots.length; i++) {
			if(dots[i] != null) {
				dots[i].t++;
				if(dots[i].t == 100) dots[i] = null;
			} else if(dots[i] == null) {
				if(b) {
					dots[i] = new Dots();
					b = false;
				}
			}
		}
	}
	
	public static void render(Graphics g) {
		for(int i = 0; i < dots.length; i++) {
			if(dots[i] != null) {
				g.setColor(dots[i].color);
				drawCenteredCircle(g, dots[i].x, dots[i].y, 15);
			}
		}
	}
	
	public static void drawCenteredCircle(Graphics g, int x, int y, int r) {
		  x = x-(r/2);
		  y = y-(r/2);
		  g.fillOval(x, y, r, r);
	}
	
	public static Dots[] getDots() {
		return dots;
	}
}
