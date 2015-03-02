package com.brennytizer.jumg.utils.fileio;

import java.util.HashMap;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;

public class SaveData {
	public HashMap<String, Object> cache;
	public String location;
	
	public SaveData(String location) {
		this(location, new HashMap<String, Object>());
	}
	
	public SaveData(String location, HashMap<String, Object> cache) {
		this.location = location;
		this.cache = cache;
	}
	
	public void addToCache(String key, Object value) {
		cache.put(key, value);
	}
	
	public String getJumgString(SyntaxJumg type, String ... args) {
		if(type == SyntaxJumg.INVALID) {
			Logging.log(LoggingSpice.EXTRA_HOT, "There shouldn't be any calls to this type. Fix code now...");
			return "";
		}
		String ret = type.getTrigger();
		for(String s : args)
			ret += s + type.getTrigger();
		return ret;
	}
}