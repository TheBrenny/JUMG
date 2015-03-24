package com.brennytizer.jumg.questing;

public interface QuestReceiver {
	public boolean doesAcceptQuest(Quest quest);
	public Quest[] acceptedQuests();
	public boolean handInQuest(Quest quest);
}