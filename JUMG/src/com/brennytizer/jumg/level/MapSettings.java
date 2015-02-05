package com.brennytizer.jumg.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;

public class MapSettings {
	public String settingFile;
	public HashMap<Integer, Integer> tileColors;
	
	public MapSettings(String settingFile) {
		this.settingFile = settingFile;
		tileColors = new HashMap<Integer, Integer>();
	}
	
	public void read() {
		Logging.log(LoggingSpice.MILD, "Attempting to read tile settings file...");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(settingFile)));
			String line = "";
			String[] cut = null;
			while((line = br.readLine()) != null) {
				cut = line.split(":");
				tileColors.put(Integer.parseInt(cut[1], 16), Integer.parseInt(cut[0]));
			}
			br.close();
			Logging.log(LoggingSpice.MILD, "Success! Map settings read.");
		} catch(IOException e) {
			e.printStackTrace();
			Logging.log(LoggingSpice.HOT, "Oh oh! Couldn't read the settings file: " + settingFile);
		}
	}
	
	public int getIdOfColour(int color) {
		return tileColors.get(color);
	}
}