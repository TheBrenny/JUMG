package com.brennytizer.jumg.entities.ai.communications;

import com.brennytizer.jumg.entities.Entity;

public interface Poller {
	public String listToPoll();
	public void updatePoll();
	public void pollEntity(Entity e);
}