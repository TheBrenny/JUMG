package com.brennytizer.jumg.utils;

import java.util.ArrayList;

public abstract class JumgRunnable {
	public static ArrayList<JumgRunnable> allRunnables;
	public String uid;
	
	public JumgRunnable(String uid) {
		this.uid = uid;
	}
	
	public abstract void run();
	
	public String getUid() {
		return this.uid;
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
}