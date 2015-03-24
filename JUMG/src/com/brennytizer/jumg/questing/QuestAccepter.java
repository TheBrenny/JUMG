package com.brennytizer.jumg.questing;

public interface QuestAccepter {
	public Quest getCurrentQuest();
	public boolean canAcceptQuest();
	public boolean acceptQuest(Quest quest);
}