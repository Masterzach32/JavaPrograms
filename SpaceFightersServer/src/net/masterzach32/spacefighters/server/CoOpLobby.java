package net.masterzach32.spacefighters.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import org.json.simple.JSONObject;

import net.masterzach32.lib.json.JSONHelper;
import net.masterzach32.spacerunner.assets.Assets;
import net.masterzach32.spacerunner.mapobject.Enemy;
import net.masterzach32.spacerunner.mapobject.EntityManager;
import net.masterzach32.spacerunner.mapobject.Player;
import net.masterzach32.spacerunner.mapobject.powerup.PowerUp;

public class CoOpLobby extends Thread {
	
	private PrintWriter p1Out, p2Out;
	private BufferedReader p1In, p2In;
	
	private EntityManager manager;
	private Player player1, player2;
	private int wave = 0;
	private Random r;

	public CoOpLobby(Socket player1, Socket player2) {
		try {
			Assets.preinit();
			p1In = new BufferedReader(new InputStreamReader(player1.getInputStream()));
			p1Out = new PrintWriter(player1.getOutputStream(), true);
			p1Out.println("1");
			
			p2In = new BufferedReader(new InputStreamReader(player2.getInputStream()));
			p2Out = new PrintWriter(player2.getOutputStream(), true);
			p2Out.println("2");
		} catch (IOException e) {
			e.printStackTrace();
		};
		manager = new EntityManager();
		this.player1 = new Player(0, 0);
		this.player2 = new Player(0, 0);
		r = new Random();
		start();
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		while(true) {
			try {
				// start
				JSONObject p1Data = JSONHelper.getTopLevelJSONObject(p1In.readLine());
				JSONObject p2Data = JSONHelper.getTopLevelJSONObject(p2In.readLine());
				JSONObject data = new JSONObject();
				data.put("p1", p1Data);
				data.put("p2", p2Data);
				// update player references
				player1.health = JSONHelper.getInteger(JSONHelper.getJSONObject(data, "p1"), "h");
				player1.x = JSONHelper.getDouble(JSONHelper.getJSONObject(data, "p1"), "x");
				player1.y = JSONHelper.getDouble(JSONHelper.getJSONObject(data, "p1"), "y");
				player2.health = JSONHelper.getInteger(JSONHelper.getJSONObject(data, "p2"), "h");
				player2.x = JSONHelper.getDouble(JSONHelper.getJSONObject(data, "p2"), "x");
				player2.y = JSONHelper.getDouble(JSONHelper.getJSONObject(data, "p2"), "y");
				
				if(manager.getEnemyList().size() == 0) {
					wave++;
					player1.health++;
					p1Data.put("h", player1.health);
					player2.health++;
					p2Data.put("h", player2.health);
					for (int i = 0; i < 5 * wave; i++)
						manager.addEnemy(new Enemy("Enemy", r.nextInt(690 * wave) + 900, r.nextInt(230) + 50));
					for (int i = 0; i < wave / 4; i++)
						manager.addEntity(new PowerUp(r.nextInt(4), r.nextInt(690 * wave) + 900, r.nextInt(230) + 50));
				}
				manager.updateEntities();
				
				data.put("wave", wave);
				data.put("p1", p1Data);
				data.put("p2", p2Data);
				
				JSONObject temp = manager.getEntitiesInJSON();
				data.put("entities", JSONHelper.getJSONObject(temp, "entities"));
				data.put("enemies", JSONHelper.getJSONObject(temp, "enemies"));
				
				p1Out.println(data.toJSONString());
				p2Out.println(data.toJSONString());		
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}