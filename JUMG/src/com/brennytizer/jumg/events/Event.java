package com.brennytizer.jumg.events;

import java.util.ArrayList;

import com.brennytizer.jumg.save.ObjectSaveable;
import com.brennytizer.jumg.utils.JumgRunnable;
import com.brennytizer.jumg.utils.Words;
import com.brennytizer.jumg.utils.fileio.FileInstantiable;
import com.brennytizer.jumg.utils.fileio.FileInterpreter.ReflectiveType;
import com.brennytizer.jumg.utils.geom.Collisions;
import com.brennytizer.jumg.utils.geom.PolygonalObject;

public class Event implements FileInstantiable<Event>, ObjectSaveable {
	public String uid;
	public ArrayList<PolygonalObject> triggers;
	public ArrayList<PolygonalObject> activators;
	public JumgRunnable event;
	public boolean active;
	
	public Event() {
		this(null);
	}
	
	public Event(JumgRunnable event) {
		this(new ArrayList<PolygonalObject>(), new ArrayList<PolygonalObject>(), event);
	}
	
	public Event(ArrayList<PolygonalObject> triggers, ArrayList<PolygonalObject> activators, JumgRunnable event) {
		this.triggers = triggers;
		this.activators = activators;
		this.event = event;
		this.uid = "" + hashCode();
		this.active = event != null;
	}
	
	public void triggerEvent() {
		this.event.run();
	}
	
	public boolean shouldTriggerEvent() {
		for(PolygonalObject a : triggers) {
			for(PolygonalObject b : activators) {
				if(Collisions.collidedAABB(Collisions.buildAABB(a), Collisions.buildAABB(b))) return true;
			}
		}
		return false;
	}
	
	public boolean addTrigger(PolygonalObject trig) {
		return !isTrigger(trig) && !isActivator(trig) && triggers.add(trig);
	}
	
	public boolean addActivator(PolygonalObject actor) {
		return !isTrigger(actor) && !isActivator(actor) && activators.add(actor);
	}
	
	public boolean isTrigger(PolygonalObject trig) {
		for(PolygonalObject t : triggers)
			if(t == trig) return true;
		return false;
	}
	
	public boolean isActivator(PolygonalObject actor) {
		for(PolygonalObject t : activators)
			if(t == actor) return false;
		return true;
	}
	
	public String getUid() {
		return uid;
	}
	
	public ArrayList<PolygonalObject> getTriggers() {
		return triggers;
	}
	
	public ArrayList<PolygonalObject> getActivators() {
		return activators;
	}
	
	public JumgRunnable getEvent() {
		return event;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public Event setUid(String uid) {
		this.uid = uid;
		return this;
	}
	
	public Event setActive(boolean active) {
		this.active = active && this.event != null;
		return this;
	}
	
	public Event setEvent(JumgRunnable event) {
		this.event = event;
		return this;
	}
	
	public Event instantiateWithParams(String ... params) {
		//event = ;
		return this;
	}
	
	public String getObjectClass() {
		return "com.brennytizer.jumg.events.Event";
	}
	
	public String getObjectName() {
		return "event" + uid;
	}
	
	public String[] getObjectParams() {
		String[] ret = new String[3];
		ret[0] = Words.join(this.triggers, ReflectiveType.getArgSplitter());
		ret[1] = Words.join(this.activators, ReflectiveType.getArgSplitter());
		ret[2] = event.getUid();
		return ret;
	}
	
	public String[][] getObjectData() {
		return null;
	}
}