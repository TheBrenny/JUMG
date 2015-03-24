package com.brennytizer.jumg.questing;

public interface QuestGiver {
	public Quest getNextQuest();
	public boolean canGiveQuest();
	public Quest getSpecificQuest(int index);
	public Quest[] getQuests();
}
