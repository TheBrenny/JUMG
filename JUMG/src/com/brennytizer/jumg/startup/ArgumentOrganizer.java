package com.brennytizer.jumg.startup;

import java.util.HashMap;

public abstract class ArgumentOrganizer {
	public static ArgumentOrganizer ARG_ORG_INSTANCE;
	public static String argumentSplitter = ":";
	public static HashMap<String, String> organizedArguments;
	public String[] rawArguments;
	
	public ArgumentOrganizer(String[] args) {
		ArgumentOrganizer.ARG_ORG_INSTANCE = this;
		ArgumentOrganizer.organizedArguments = new HashMap<String, String>();
		setDefaultArguments();
		this.rawArguments = args;
		collectArgumentData();
	}
	
	public abstract void setDefaultArguments();
	
	public void collectArgumentData() {
		for(String s : rawArguments)
			setArgument(s);
	}
	
	public void setArgument(String str) {
		String[] split = str.split(ArgumentOrganizer.argumentSplitter);
		setArgument(split[0], split[1]);
	}
	
	public void setArgument(String key, String value) {
		organizedArguments.put(key, value);
	}
	
	public static ArgumentOrganizer getInstance() {
		return ArgumentOrganizer.ARG_ORG_INSTANCE;
	}
	
	public String getArgument(String key) {
		return organizedArguments.get(key);
	}
}