package net.masterzach32.spacerunner.state;

import java.awt.Graphics2D;

import net.masterzach32.spacerunner.SpaceRunner;
import net.masterzach32.spacerunner.assets.Background;
import net.masterzach32.spacerunner.assets.sfx.AudioPlayer;

/**
 * GameState system from SideScroller Project. Allows for
 * a state to do stuff upon being loaded and unloaded
 * 
 * @author Zach Kozar
 */
public abstract class GameState {
	
	protected static GameState state = null;
	protected static SpaceRunner game = SpaceRunner.game;
	
	protected Background bg;
	protected AudioPlayer bgMusic;
	
	public GameState() {
		init();
	}
	
	/**
	 * Called when the game loads, initializes objects such as font, backgrounds, entities, etc.
	 */
	public abstract void init();
	
	/**
	 * If you want the state to do something when it loads, do it here
	 */
	protected abstract void load();
	
	/**
	 * If you want the state to do something when it unloads, do it here 
	 */
	protected abstract void unload();
	
	public abstract void tick();
	
	public abstract void render(Graphics2D g);
	
	public abstract void keyPressed(int k);
	
	public abstract void keyReleased(int k);
	
	public abstract void mousePressed(int k);
	
	public abstract void mouseReleased(int k);
	
	private static void unloadState() {
		if(state != null) state.unload();
		state = null;
	}
	
	private static void loadState(GameState s) {
		state = s;
		state.load();
	}
	
	/**
	 * Sets the game state by unloading the current state and loading the new one
	 * @param currentState
	 * @param newState
	 */
	public static void setState(GameState newState) {
		unloadState();
		loadState(newState);
	}
	
	/**
	 * Returns the current state
	 * @return
	 */
	public static GameState getState() {
		return state;
	}
}