package com.brennytizer.jumg.questing;

public abstract class Quest {
	public abstract void startQuest();
	
	public abstract boolean isQuestComplete();
	
	public abstract void endQuest();
	
	public abstract String getQuestName();
	
	public abstract String getQuestDescription();
	
	public abstract String[] getQuestRewards();
}