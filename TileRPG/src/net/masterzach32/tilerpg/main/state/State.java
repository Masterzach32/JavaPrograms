package net.masterzach32.tilerpg.main.state;

import java.awt.Graphics;

public abstract class State {
	
	private static State state = null;
	
	/**
	 * Returns the current state of the game
	 * @return
	 */
	public static State getState() {
		return state;
	}

	/**
	 * Sets the current state of the game
	 * @param state
	 */
	public static void setState(State state) {
		State.state = state;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

}
