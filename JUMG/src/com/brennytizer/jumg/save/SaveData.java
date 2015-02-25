package com.brennytizer.jumg.save;

import java.util.ArrayList;
import java.util.HashMap;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;
import com.brennytizer.jumg.utils.fileio.FileIO;
import com.brennytizer.jumg.utils.fileio.SyntaxJumg;

public class SaveData {
	public HashMap<String, String> mandatoryData;
	public ArrayList<ObjectSaveable> saveableObjects;
	public ArrayList<String>[] functions;
	public String location;
	private boolean writing = false;
	
	public SaveData(String location) {
		this(location, new ArrayList<ObjectSaveable>());
	}
	
	public SaveData(String location, ArrayList<ObjectSaveable> saveableStuff) {
		this(location, saveableStuff, new HashMap<String, String>());
	}
	
	@SuppressWarnings("unchecked")
	public SaveData(String location, ArrayList<ObjectSaveable> saveableStuff, HashMap<String, String> mandatoryData) {
		this(location, saveableStuff, mandatoryData, (ArrayList<String>[]) new ArrayList<?>[2]);
	}
	
	public SaveData(String location, ArrayList<ObjectSaveable> saveableStuff, HashMap<String, String> mandatoryData, ArrayList<String>[] functions) {
		this.location = location;
		this.saveableObjects = saveableStuff;
		this.mandatoryData = mandatoryData;
		this.functions = functions;
	}
	
	public void addObject(ObjectSaveable os) {
		if(writing) Logging.log(LoggingSpice.MEDIUM, "You can't add now! I'm writing data!");
		else this.saveableObjects.add(os);
	}
	
	public void addMandatoryData(String location, String name) {
		if(writing) Logging.log(LoggingSpice.MEDIUM, "You can't add now! I'm writing data!");
		else mandatoryData.put(name, location);
	}
	
	public void addPreFunction(String function) {
		if(writing) Logging.log(LoggingSpice.MEDIUM, "You can't add now! I'm writing data!");
		else this.functions[0].add(function);
	}
	
	public void addPostFunction(String function) {
		if(writing) Logging.log(LoggingSpice.MEDIUM, "You can't add now! I'm writing data!");
		else this.functions[1].add(function);
	}
	
	public void writeData() {
		writing = true;
		FileIO.FileOutput fo = FileIO.startFileWriter(location);
		for(String key : mandatoryData.keySet()) {
			fo.appendData(getJumgString(SyntaxJumg.MANDATORY_DATA, key, mandatoryData.get(key)));
		}
		fo.appendData("");
		for(String a : functions[0]) fo.appendData(getJumgString(SyntaxJumg.FUNCTION, a));
		fo.appendData("");
		for(ObjectSaveable os : saveableObjects) {
			fo.appendData(getJumgString(SyntaxJumg.OBJECT_INITIALISE, os.getObjectClass(), os.getObjectName()));
			for(String s : os.getObjectParams()) fo.appendData(getJumgString(SyntaxJumg.OBJECT_PARAMETER, s));
			fo.appendData(getJumgString(SyntaxJumg.OBJECT_END_INIT, "END"));
			for(String[] ss : os.getObjectData()) fo.appendData(getJumgString(SyntaxJumg.OBJECT_DATA, ss));
		}
		for(String a : functions[1]) fo.appendData(getJumgString(SyntaxJumg.FUNCTION, a));
		fo.writeData();
		writing = false;
	}
	
	public String getJumgString(SyntaxJumg type, String ... args) {
		if(type == SyntaxJumg.INVALID) {
			Logging.log(LoggingSpice.EXTRA_HOT, "There shouldn't be any calls to this type. Fix code now...");
			return "";
		}
		String ret = type.getTrigger();
		for(String s : args) ret += s + type.getTrigger();
		return ret;
		/*
		 * This is old code. I ended up finding it useless with the addition
		 * above...
		 * switch(type) {
		 * case MANDATORY_DATA:
		 * return type.getTrigger() + args[0] + type.getTrigger() + args[1] +
		 * type.getTrigger();
		 * case OBJECT_INITIALISE:
		 * return type.getTrigger() + args[0] + type.getTrigger() + args[1] +
		 * type.getTrigger();
		 * case OBJECT_PARAMETER:
		 * return type.getTrigger() + args[0] + type.getTrigger();
		 * case OBJECT_END_INIT:
		 * return type.getTrigger() + "END" + type.getTrigger();
		 * case OBJECT_DATA:
		 * return type.getTrigger() + args[0] + type.getTrigger() + args[1] +
		 * type.getTrigger();
		 * case FUNCTION:
		 * return type.getTrigger() + args[0] + type.getTrigger();
		 * }
		 * return "";
		 */
	}
}