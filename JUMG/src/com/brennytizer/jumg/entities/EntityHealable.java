package com.brennytizer.jumg.entities;

public interface EntityHealable {
	public float getHealth();
	public void hurt(float amount);
	public void heal(float amount);
	public void kill();
}