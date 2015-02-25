package com.brennytizer.jumg.utils.fileio;

public enum SyntaxJumg {
	INVALID(-1, "", 1),
	MANDATORY_DATA(0, "$..$", 3),
	OBJECT_INITIALISE(1, "$$$", 3),
	OBJECT_END_INIT(2, "$$$", 2),
	OBJECT_PARAMETER(3, "$$", 2),
	OBJECT_DATA(4, "$", 3),
	FUNCTION(5, "$", 2);
	
	public int id;
	public String trigger;
	public int triggerCount;
	public boolean hasMore;
	
	SyntaxJumg(int id, String trigger, int triggerCount) {
		this(id, trigger, triggerCount, false);
	}
	SyntaxJumg(int id, String trigger, int triggerCount, boolean hasMore) {
		this.id = id;
		this.trigger = trigger;
		this.triggerCount = triggerCount;
		this.hasMore = hasMore;
	}
	
	public int getID() {
		return id;
	}
	
	public String getTrigger() {
		return trigger;
	}
	
	public int getTriggerCount() {
		return triggerCount;
	}
	
	public boolean hasMore() {
		return hasMore;
	}
	
	public static SyntaxJumg getJumgSyntax(int id) {
		for(SyntaxJumg sj : SyntaxJumg.values()) {
			if(sj.getID() == id) return sj;
		}
		return INVALID;
	}
	
	public static SyntaxJumg getJumgSyntax(String trigger) {
		return getJumgSyntax(trigger, -1);
	}
	public static SyntaxJumg getJumgSyntax(String trigger, int multiple) {
		for(SyntaxJumg sj : SyntaxJumg.values()) {
			if(trigger.startsWith(sj.getTrigger()) && (multiple == -1 || multiple == sj.getID())) return sj;
		}
		return INVALID;
	}
}