package com.brennytizer.jumg.utils;

import java.util.ArrayList;

import com.brennytizer.jumg.save.ObjectSaveable;
import com.brennytizer.jumg.utils.Words.KeySpace;
import com.brennytizer.jumg.utils.fileio.FileInstantiable;

public abstract class JumgRunnable implements FileInstantiable<JumgRunnable>, ObjectSaveable {
	public static ArrayList<JumgRunnable> allRunnables;
	public String uid;
	
	public JumgRunnable() {
		this(Words.random(15, KeySpace.getKeySpace(KeySpace.ALPHA_LOWER, KeySpace.ALPHA_UPPER, KeySpace.NUMBERS)));
	}
	
	public JumgRunnable(String uid) {
		this.uid = uid;
	}
	
	public abstract void run();
	
	public String getUid() {
		return this.uid;
	}
	
	public JumgRunnable instantiateWithParams(String ... params) {
		return getRunnable(params[0]);
	}
	
	public static boolean containsRunnable(JumgRunnable runnable) {
		for(JumgRunnable r : allRunnables)
			if(r.getUid().equals(runnable.uid)) return true;
		return false;
	}
	
	public static JumgRunnable getRunnable(String uid) {
		for(JumgRunnable r : allRunnables)
			if(r.getUid().equals(uid)) return r;
		return null;
	}
	
	public String getObjectClass() {
		return "com.brennytizer.jumg.utils.JumgRunnable";
	}
	
	public String getObjectName() {
		return getUid();
	}
	
	public String[] getObjectParams() {
		return new String[] {getUid()};
	}
	
	public String[][] getObjectData() {
		return null;
	}
}