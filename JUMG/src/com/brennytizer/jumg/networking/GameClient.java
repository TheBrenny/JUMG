package com.brennytizer.jumg.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.brennytizer.jumg.networking.packets.Packet;
import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;
import com.brennytizer.jumg.utils.Words;

public abstract class GameClient {
	public boolean running = false;
	public DatagramSocket socket;
	public InetAddress ipAddress;
	public int port;
	
	public GameClient(String ipAddress, int port) {
		Logging.log(LoggingSpice.MILD, Words.insert("Attempting to create a networking client [{0}:{1}]", ipAddress, port));
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
			this.port = port;
			running = true;
		} catch(SocketException | UnknownHostException e) {
			e.printStackTrace();
			Logging.log(LoggingSpice.HOT, Words.insert("Oh no! Networking client could not connect to [{0}:{1}]", ipAddress, port));
		}
		if(running) Logging.log(LoggingSpice.MILD, Words.insert("Networking client connected to [{0}:{1}]", ipAddress, port));
	}
	
	public void start() {
		new Thread(new Runnable() {
			public void run() {
				GameClient.this.run();
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
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
		socket.close();
	}
	
	public void parsePacket(byte[] data, InetAddress ipAddress, int port) {
		handlePacket(new String(data).trim(), ipAddress, port);
	}
	
	public abstract void handlePacket(String message, InetAddress ipAddress, int port);
	
	public void sendPacket(Packet packet) {
		sendData(packet.getData());
	}
	
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, GameServer.PORT_USED);
		try {
			socket.send(packet);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}