package com.brennytizer.jumg.events;

import com.brennytizer.jumg.utils.JumgRunnable;
import com.brennytizer.jumg.utils.Words;
import com.brennytizer.jumg.utils.Words.KeySpace;
import com.brennytizer.jumg.utils.engine.Time;

public class TimeEvent implements Event {
	public long[] trigger;
	public JumgRunnable event;
	public boolean active;
	public String uid;
	
	public TimeEvent(long[] trigger, JumgRunnable event) {
		this.trigger = trigger;
		this.event = event;
	}
	public TimeEvent(String uid, long[] trigger, JumgRunnable event) {
		this.trigger = trigger;
		this.event = event;
		this.active = event != null;
		this.uid = Words.random(15, KeySpace.getKeySpace(KeySpace.ALPHA_LOWER, KeySpace.ALPHA_UPPER, KeySpace.NUMBERS));
	}
	
	public void triggerEvent() {
		this.event.run();
	}
	
	public boolean shouldTriggerEvent() {
		if(!active) return false;
		boolean ret = true;
		boolean[] compared = TimeEvent.compare(trigger);
		for(int i = 0; i < compared.length; i++) {
			if(!compared[i]) {
				ret = false;
				break;
			}
		}
		return ret;
	}
	
	public void activate(boolean active) {
		this.active = active && this.event != null;
	}
	
	public TimeEvent setTrigger(long[] trigger) {
		this.trigger = trigger;
		return this;
	}
	
	public static boolean[] compare(long[] time) {
		boolean[] result = new boolean[5];
		for(int i = 0; i < result.length; i++)
			result[i] = true;
		long[] realTime = Time.CLOCK;
		for(int i = 0; i < time.length && i < 5; i++)
			result[i] = time[i] == -1 || time[i] >= realTime[i];
		return result;
	}
	public String getUid() {
		return this.uid;
	}
	public TimeEvent setUid(String uid) {
		this.uid = uid;
		return this;
	}
	public TimeEvent setEvent(JumgRunnable event) {
		this.event = event;
		return this;
	}
	public JumgRunnable getEvent() {
		return event;
	}
	
	public boolean isActive() {
		return active;
	}
}