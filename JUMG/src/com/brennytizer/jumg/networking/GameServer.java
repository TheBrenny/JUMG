package com.brennytizer.jumg.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public abstract class GameServer {
	public static GameServer INSTANCE;
	public static int PORT_USED = 58646;
	public boolean running = false;
	public ArrayList<Networkable> allPlayers = new ArrayList<Networkable>();
	public ServerInfo serverInfo;
	public DatagramSocket socket;
	
	public GameServer(String host) {
		if(GameServer.INSTANCE == null) GameServer.INSTANCE = this;
		running = true;
		try {
			String tmp = GameServer.getExternalIP();
			String serverIP = (tmp.equalsIgnoreCase("none")) ? "127.0.0.1" : tmp;
			this.serverInfo = new ServerInfo(host, serverIP, PORT_USED + "");
			this.socket = new DatagramSocket(PORT_USED);
		} catch(SocketException e) {
			e.printStackTrace();
			running = false;
		}
	}
	
	public void start() {
		new Thread(new Runnable() {
			public void run() {
				GameServer.this.run();
			}
		}).start();
	}
	
	public void run() {
		while(running) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch(IOException e) {
				e.printStackTrace();
			}
			String s = new String(packet.getData()).trim();
			if(s.equalsIgnoreCase("ping")) {
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			} else if(s.equalsIgnoreCase("info")) {
				sendData(serverInfo.getBytes(), packet.getAddress(), packet.getPort());
			}
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
		socket.close();
	}
	
	public void parsePacket(byte[] data, InetAddress address, int port) {
		handlePacket(new String(data).trim(), address, port);
	}
	
	public abstract void handlePacket(String message, InetAddress address, int port);
	
	public void sendData(byte[] data, InetAddress address, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			socket.send(packet);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendDataToAll(byte[] data) {
		for(Networkable n : allPlayers)
			sendData(data, n.getIpAddress(), n.getPort());
	}
	
	public static void destroyInstancedServer() {
		INSTANCE.running = false;
		INSTANCE.allPlayers = null;
		INSTANCE.serverInfo = null;
		INSTANCE.socket.close();
		INSTANCE = null;
	}
	
	public static void changePort(int port) {
		GameServer.PORT_USED = port;
	}
	
	public static String getExternalIP() {
		return "none";
	}
}