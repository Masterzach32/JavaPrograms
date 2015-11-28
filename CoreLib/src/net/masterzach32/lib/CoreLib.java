package net.masterzach32.lib;

public class CoreLib {

	private static Game game;
	public static final int BUILD = 13;
	public static final String VERSION = "1.0.0." + BUILD;
	
	public CoreLib(Game gameInstance) {
		game = gameInstance;
		game.getLogger().logInfo("Launching " + game.getName() + " Build " + game.getBuildNumber());
		game.getLogger().logInfo("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.version") + ")");
		game.getLogger().logInfo("OS Archetecture: " + System.getProperty("os.arch"));
		game.getLogger().logInfo("Java Version: " + System.getProperty("java.version") + " " + System.getProperty("java.vendor"));
	}
	
	public static Game getGame() {
		return game;
	}
}