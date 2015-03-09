package com.brennytizer.jumg.entities.ai.communications;

import java.util.ArrayList;
import java.util.HashMap;

import com.brennytizer.jumg.entities.Entity;

public class PollingMachine {
	private static PollingMachine POLLING_MACHINE;
	public HashMap<String, ArrayList<Entity>> pollingList;
	
	private PollingMachine() {
		pollingList = new HashMap<String, ArrayList<Entity>>();
		addPollingList("NULL-LIST", new ArrayList<Entity>());
	}
	
	public void addPollingList(String listID, Entity ... entitiesToPoll) {
		addPollingList(listID, new ArrayList<Entity>());
		for(Entity e : entitiesToPoll)
			addEntityToPollList(listID, e);
	}
	
	public void addPollingList(String listID, ArrayList<Entity> entitiesToPoll) {
		synchronized(pollingList) {
			pollingList.put(listID, entitiesToPoll);
		}
	}
	
	public void addEntityToPollList(String listID, Entity e) {
		synchronized(pollingList) {
			pollingList.get(listID).add(e);
		}
	}
	
	public static PollingMachine getPollingMachine() {
		if(PollingMachine.POLLING_MACHINE == null) PollingMachine.POLLING_MACHINE = new PollingMachine();
		return PollingMachine.POLLING_MACHINE;
	}
	
	public ArrayList<Entity> getPollList(String listID) {
		synchronized(pollingList) {
			return pollingList.get(listID);
		}
	}
}