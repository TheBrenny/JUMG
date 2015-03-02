package com.brennytizer.jumg.events;

public interface Event {
	public boolean shouldTriggerEvent();
	public void triggerEvent();
	public String getUid();
}
