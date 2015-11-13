package net.masterzach32.spacerunner.assets;

import java.awt.*;
import java.awt.image.*;

import net.masterzach32.spacerunner.SpaceRunner;

/**
 * A simple class to make moving backgrounds possible
 * 
 * @author Zach Kozar
 */
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the backgrounds position on the screen
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % SpaceRunner.WIDTH;
		this.y = (y * moveScale) % SpaceRunner.HEIGHT;
	}

	/**
	 * Sets the speed and direction that the background moves
	 * 
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

	public void render(Graphics2D g) {
		g.drawImage(image, (int) x, (int) y, SpaceRunner.WIDTH, SpaceRunner.HEIGHT, null);

		if (x < 0) {
			g.drawImage(image, (int) x + SpaceRunner.WIDTH, (int) y, SpaceRunner.WIDTH, SpaceRunner.HEIGHT, null);
		}
		if (x > 0) {
			g.drawImage(image, (int) x - SpaceRunner.WIDTH, (int) y, SpaceRunner.WIDTH, SpaceRunner.HEIGHT, null);
		}
		if (x >= 640 || x <= -640)
			x = 0;
	}
}