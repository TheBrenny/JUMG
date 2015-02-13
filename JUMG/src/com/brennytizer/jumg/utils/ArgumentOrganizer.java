package com.brennytizer.jumg.utils;

public abstract class ArgumentOrganizer {
	public static ArgumentOrganizer organizedArguments;
	public String[] rawArguments;
	
	public ArgumentOrganizer(String[] args) {
		ArgumentOrganizer.organizedArguments = this;
		this.rawArguments = args;
	}
	
	public String getArgumentVariable(String argument) {
		for(int i = 0; i < rawArguments.length; i++) {
			if(rawArguments[i].split(":")[0].equals(argument)) {
				return rawArguments[i].split(":")[1];
			}
		}
		return null;
	}
	public abstract void setArguments(String[] args);
}
