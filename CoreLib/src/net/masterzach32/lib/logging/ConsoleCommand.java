package net.masterzach32.lib.logging;

import java.util.ArrayList;

import net.masterzach32.lib.CoreLib;
import net.masterzach32.lib.util.Utilities;

public abstract class ConsoleCommand {
	
	protected String identifier, type, helpText;
	protected String[] subIdentifiers;
	
	private static ArrayList<ConsoleCommand> commands = new ArrayList<ConsoleCommand>();

	public ConsoleCommand(String identifier, String[] subIdentifiers, String type, String helpText) {
		this.identifier = identifier;
		this.subIdentifiers = subIdentifiers;
		this.type = type;
		this.helpText = helpText;
		
		commands.add(this);
	}
	
	protected abstract void execute(String parameters);
	
	public static void reciveCommand(String command) {
		try {
			if(!command.startsWith("/")) return;
			CoreLib.getGame().getLogger().logInfo("User Input Command: " + command);

			boolean doesExist = false;
			command = command.substring(1, command.length());
			int index = command.indexOf(" ");
			String identifier;
			String parameters;
			if(index == -1) {
				identifier = command;
				parameters = "";
			} else {
				identifier = command.substring(0, index);
				parameters = command.substring(index + 1, command.length());
			}

			for(ConsoleCommand c : commands) {
				if(identifier.equals(c.getIdentifier())) {
					doesExist = true;
					c.execute(parameters);
				}
			}
			if(!doesExist) CoreLib.getGame().getLogger().logInfo("Could not find command identifier: " + identifier + ". Type /help for a list of commands.");
		} catch(Exception e) {
			Utilities.createErrorDialog("Command Input Error", "The command system could not process this command: " + command + "\nTry /help for a list of commands.", e);
		}
	}
	
	public static void enableCommands() {
		// create instances of commands here
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getType() {
		return type;
	}
	
	public String getHelpText() {
		return helpText;
	}

	public String[] getSubIdentifiers() {
		return subIdentifiers;
	}

	public static ArrayList<ConsoleCommand> getCommands() {
		return commands;
	}
}