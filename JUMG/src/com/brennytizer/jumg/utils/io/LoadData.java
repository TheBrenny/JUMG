package com.brennytizer.jumg.utils.io;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadData {
	public HashMap<String, String> cache;
	public String location;
	
	public LoadData(String location) {
		this.location = location;
		this.cache = new HashMap<String, String>();
	}
	
	public HashMap<String, String> read() {
		FileIO.FileInput fi = FileIO.startFileReader(location);
		fi.readData();
		ArrayList<String> strs = fi.getData();
		for(String s : strs) {
			s = s.substring(s.indexOf("$") + 1, s.lastIndexOf("$"));
			String[] split = s.split("=", 2);
			cache.put(split[0], split[1]);
		}
		return cache;
	}
	public HashMap<String, String> getCache() {
		return cache;
	}
	public String getFromCache(String key) {
		String val = cache.get(key);
		return val == null ? "ERROR.VALUE_NULL" : val;
	}
}