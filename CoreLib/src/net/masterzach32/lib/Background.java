package net.masterzach32.lib;

import java.awt.*;
import java.awt.image.*;

/**
 * A simple class to make moving backgrounds possible
 * 
 * @author Zach Kozar
 */
public class Background {

	private BufferedImage image;

	private double x, y, dx, dy;

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
		this.x = (x * moveScale) % CoreLib.game.getWidth();
		this.y = (y * moveScale) % CoreLib.game.getHeight();
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
		g.drawImage(image, (int) x, (int) y, CoreLib.game.getWidth(), CoreLib.game.getHeight(), null);

		if (x < 0)
			g.drawImage(image, (int) x + CoreLib.game.getWidth(), (int) y, CoreLib.game.getWidth(), CoreLib.game.getHeight(), null);
		if (x > 0)
			g.drawImage(image, (int) x - CoreLib.game.getWidth(), (int) y, CoreLib.game.getWidth(), CoreLib.game.getHeight(), null);
		if (x >= 640 || x <= -640)
			x = 0;
	}
}