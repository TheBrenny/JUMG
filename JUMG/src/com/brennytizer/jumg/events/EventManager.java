package com.brennytizer.jumg.events;

import java.util.ArrayList;

public class EventManager {
	public ArrayList<Event> events;
	
	public EventManager() {
		this(new ArrayList<Event>());
	}
	
	public EventManager(ArrayList<Event> events) {
		this.events = events;
	}
	
	public void update() {
		for(Event e : events)
			if(e.shouldTriggerEvent()) e.triggerEvent();
	}
	public boolean removeEvent(String uid) {
		Event e = getEvent(uid);
		return e != null && events.remove(e);
	}
	public boolean addEvent(Event e) {
		return !hasEvent(e.uid) && events.add(e);
	}
	
	public boolean hasEvent(String uid) {
		return getEvent(uid) != null;
	}
	public Event getEvent(String uid) {
		for(Event e : events)
			if(e.uid.equals(uid)) return e;
		return null;
	}
}