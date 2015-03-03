package com.brennytizer.jumg.updating;

import java.net.MalformedURLException;

import com.brennytizer.jumg.utils.Words;
import com.brennytizer.jumg.utils.io.WebIO;

public class Updater {
	public static String version = "1.0";
	public String url;
	
	public Updater(String url, String version) {
		Updater.version = version;
		this.url = url;
	}
	
	public boolean checkForUpdate() {
		try {
			String version = Words.findWord(Words.join("\n", WebIO.startWebReader(url).read()), "version:[\\d\\.]+").split(":")[1];
			String[] splitOne = version.split("\\.");
			String[] splitTwo = Updater.version.split("\\.");
			String[][] splits = new String[2][Math.max(splitOne.length, splitTwo.length)];
			boolean needsUpdate = false;
			int i = 0;
			for(; i < splits[0].length && !needsUpdate; i++) {
				if(splitOne.length > i) splits[0][i] = splitOne[i];
				else splits[0][i] = "0";
				if(splitTwo.length > i) splits[1][i] = splitTwo[i];
				else splits[2][i] = "0";
				if(Integer.parseInt(splits[0][i]) > Integer.parseInt(splits[1][i])) needsUpdate = true;
				else if(Integer.parseInt(splits[0][i]) < Integer.parseInt(splits[1][i])) break;
			}
			return needsUpdate;
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
		return false;
	}
}