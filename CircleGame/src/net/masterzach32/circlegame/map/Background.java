package net.masterzach32.circlegame.map;

import java.awt.*;
import java.awt.image.*;

import net.masterzach32.circlegame.main.CircleGame;

public class Background {
	
	private BufferedImage image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	public Background(BufferedImage i, double ms) {
		try {
			image = i;
			moveScale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the backgrounds position on the screen
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % CircleGame.WIDTH;
		this.y = (y * moveScale) % CircleGame.HEIGHT;
	}
	
	/**
	 * Sets the speed and direction that the background moves
	 * @param dx
	 * @param dy
	 */
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void tick() {
		x += dx;
		y += dy;
	}
	
	public void render(Graphics g) {
		g.drawImage(image, (int)x, (int)y, CircleGame.WIDTH, CircleGame.HEIGHT,  null);
		
		if(x < 0) {
			g.drawImage(image, (int)x + CircleGame.WIDTH, (int)y, CircleGame.WIDTH, CircleGame.HEIGHT, null);
		}
		if(x > 0) {
			g.drawImage(image, (int)x - CircleGame.WIDTH, (int)y, CircleGame.WIDTH, CircleGame.HEIGHT, null);
		}
		//if(x >= 640 || x == -640) x = 0;
	}
}