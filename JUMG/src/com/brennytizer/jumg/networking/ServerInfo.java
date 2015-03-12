package com.brennytizer.jumg.networking;

public class ServerInfo {
	public String host;
	public String ip;
	public String port;
	
	public ServerInfo(String host, String ip, String port) {
		this.host = host;
		this.ip = ip;
		this.port = port;
	}
	public byte[] getBytes() {
		return (host + ":" + ip).getBytes();
	}
}