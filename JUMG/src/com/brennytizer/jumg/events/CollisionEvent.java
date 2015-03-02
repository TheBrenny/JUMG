package com.brennytizer.jumg.events;

import java.util.ArrayList;

import com.brennytizer.jumg.utils.JumgRunnable;
import com.brennytizer.jumg.utils.Words;
import com.brennytizer.jumg.utils.Words.KeySpace;
import com.brennytizer.jumg.utils.geom.Collisions;
import com.brennytizer.jumg.utils.geom.PolygonalObject;

public class CollisionEvent implements Event {
	public String uid;
	public ArrayList<PolygonalObject> triggers;
	public ArrayList<PolygonalObject> activators;
	public JumgRunnable event;
	public boolean active;
	
	public CollisionEvent() {
		this(null);
	}
	
	public CollisionEvent(JumgRunnable event) {
		this(new ArrayList<PolygonalObject>(), new ArrayList<PolygonalObject>(), event);
	}
	
	public CollisionEvent(ArrayList<PolygonalObject> triggers, ArrayList<PolygonalObject> activators, JumgRunnable event) {
		this.triggers = triggers;
		this.activators = activators;
		this.event = event;
		this.active = event != null;
		this.uid = Words.random(15, KeySpace.getKeySpace(KeySpace.ALPHA_LOWER, KeySpace.ALPHA_UPPER, KeySpace.NUMBERS));
	}
	
	public void triggerEvent() {
		this.event.run();
	}
	
	public boolean shouldTriggerEvent() {
		if(!active) return false;
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
	
	public CollisionEvent setUid(String uid) {
		this.uid = uid;
		return this;
	}
	
	public CollisionEvent activate(boolean active) {
		this.active = active && this.event != null;
		return this;
	}
	
	public CollisionEvent setEvent(JumgRunnable event) {
		this.event = event;
		return this;
	}
}