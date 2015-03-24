package com.brennytizer.jumg.utils.io;

public class SaveData {
	public static String DATA_TRIGGER = "..=";
	public String location;
	public ObjectSaveable objToSave;
	
	public SaveData(String location, ObjectSaveable objToSave) {
		this.location = location;
		this.objToSave = objToSave;
	}
	
	public void saveData() {
		FileIO.FileOutput fo = FileIO.startFileWriter(location);
		for(String s : objToSave.dataToSave()) fo.appendData(DATA_TRIGGER + s + DATA_TRIGGER);
		fo.writeData();
	}
}