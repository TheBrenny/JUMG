package com.brennytizer.jumg.networking.packets;

import java.util.HashMap;
import java.util.Map.Entry;

import com.brennytizer.jumg.networking.GameClient;
import com.brennytizer.jumg.networking.GameServer;

public abstract class Packet {
	public String packetID;
	
	public Packet(String packetID) {
		this.packetID = packetID;
	}
	
	public void writeData(GameClient client) {
		client.sendData(getData());
	}
	
	public void writeData(GameServer server) {
		server.sendDataToAll(getData());
	}
	
	public String readData() {
		return readData(getData());
	}
	
	public static String readData(byte[] data) {
		String[] message = new String(data).trim().split(", ", 2);
		return message[1];
	}
	
	public abstract byte[] getData();
	
	public byte[] makeData(Object ... obj) {
		String result = packetID + ",";
		for(int i = 0; i < obj.length; i++) {
			result += obj[i];
			if(i != obj.length - 1) result += ":";
		}
		return result.getBytes();
	}
	
	public static class PacketRegistry {
		public static HashMap<String, String> registry = new HashMap<String, String>();
		
		public static void addPacketType(String id, String name) {
			registry.put(id, name);
		}
		
		public static String getID(String name) {
			String id = "-1";
			for(Entry<String, String> e : registry.entrySet()) {
				if(e.getValue().equals(name)) {
					id = e.getKey();
					break;
				}
			}
			return id;
		}
		
		public static String getName(String id) {
			return registry.get(id);
		}
	}
	
	public String toString() {
		return getClass().getName() + "[" + new String(getData()).trim() + "]";
	}
	
	static {
		PacketRegistry.addPacketType("-1", "INVALID");
		PacketRegistry.addPacketType("00", "CONNECT");
		PacketRegistry.addPacketType("01", "DISCONNECT");
		PacketRegistry.addPacketType("1000", "REQUEST_INFO");
		PacketRegistry.addPacketType("1001", "RECEIVE_INFO");
	}
}