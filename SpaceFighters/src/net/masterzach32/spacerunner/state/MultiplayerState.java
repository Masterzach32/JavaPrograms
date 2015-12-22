package net.masterzach32.spacerunner.state;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;

import net.masterzach32.lib.json.JSONHelper;
import net.masterzach32.spacerunner.Launcher;
import net.masterzach32.spacerunner.mapobject.Player;

public class MultiplayerState extends LevelState {
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private boolean connected;
	
	int playerNum = 0;

	public void init() {
		super.init();
		player2 = new Player(0, 0);
	}

	protected void load() {
		String ip = JOptionPane.showInputDialog(Launcher.getFrame(), "Server IP", "SpaceFighters Multiplayer Beta", JOptionPane.INFORMATION_MESSAGE);
		try {
			socket = new Socket(ip, 25565);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			playerNum = Integer.parseInt(in.readLine());
			connected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void unload() {
		try {
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.unload();
	}

	@SuppressWarnings("unchecked")
	public void tick() {
		if(!connected) return;
		player.tick();
		
		JSONObject playerData = new JSONObject();
		playerData.put("x", player.x);
		playerData.put("y", player.y);
		playerData.put("h", player.health);
		
		out.println(playerData.toJSONString());
	}

	public void render(Graphics2D g) {
		if(!connected) return;
		try {
			JSONObject data = JSONHelper.getTopLevelJSONObject(in.readLine());
			if(playerNum == 1) {
				JSONObject playerData = JSONHelper.getJSONObject(data, "p1");
				player.health = JSONHelper.getInteger(playerData, "h");
				player.x = JSONHelper.getDouble(playerData, "x");
				player.y = JSONHelper.getDouble(playerData, "y");
				playerData = JSONHelper.getJSONObject(data, "p2");
				player2.health = JSONHelper.getInteger(playerData, "h");
				player2.x = JSONHelper.getDouble(playerData, "x");
				player2.y = JSONHelper.getDouble(playerData, "y");
			} else if(playerNum == 2) {
				JSONObject playerData = JSONHelper.getJSONObject(data, "p1");
				player2.health = JSONHelper.getInteger(playerData, "h");
				player2.x = JSONHelper.getDouble(playerData, "x");
				player2.y = JSONHelper.getDouble(playerData, "y");
				playerData = JSONHelper.getJSONObject(data, "p2");
				player.health = JSONHelper.getInteger(playerData, "h");
				player.x = JSONHelper.getDouble(playerData, "x");
				player.y = JSONHelper.getDouble(playerData, "y");
			}
			manager.updateList(JSONHelper.getJSONObject(data, "entities"), JSONHelper.getJSONObject(data, "enemies"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		super.render(g);
		if (player2.health > 0) player2.render(g);
	}

	public void keyPressed(int k) {
		super.keyPressed(k);
	}

	public void keyReleased(int k) {
		super.keyReleased(k);
	}

	public void mousePressed(int k) {}

	public void mouseReleased(int k) {}
}