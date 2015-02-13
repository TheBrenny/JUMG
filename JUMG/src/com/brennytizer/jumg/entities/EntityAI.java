package com.brennytizer.jumg.entities;

public interface EntityAI {
	public Entity setEntity(Entity entity);
	public String getAiName();
	public boolean shouldBrainRun();
	public void runBrain();
}