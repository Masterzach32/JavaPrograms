package net.masterzach32.spacefighters.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	private ServerSocket serverSocket;
	private ArrayList<CoOpLobby> games;
	
	public Server() {
		try {
			serverSocket = new ServerSocket(25565);
			games = new ArrayList<CoOpLobby>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		while(true) {
			try {
				Socket player1, player2;
				player1 = server.serverSocket.accept();
				player2 = server.serverSocket.accept();
				server.games.add(new CoOpLobby(player1, player2));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}