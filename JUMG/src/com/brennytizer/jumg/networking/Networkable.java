package com.brennytizer.jumg.networking;

import java.net.InetAddress;

public interface Networkable {
	public InetAddress getIpAddress();
	
	public int getPort();
}