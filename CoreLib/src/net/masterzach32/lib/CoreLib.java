package net.masterzach32.lib;

public class CoreLib {

	protected static Game game;
	public static final int BUILD = 0010;
	public static final String VERSION = "1.0.0." + BUILD;
	
	public CoreLib(Game gameInstance) {
		game = gameInstance;
	}
}