package com.brennytizer.jumg.entities;

public interface EntityAgeable {
	public long[] getBirthTime();
	public long[] getMaxAge();
	public long[] getDeathTime();
	public long getAge(String measurement);
}