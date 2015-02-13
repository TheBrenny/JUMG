package com.brennytizer.jumg.entities;

import com.brennytizer.jumg.utils.geom.Angle;
import com.brennytizer.jumg.utils.geom.Angle.AngleSpeed;

public interface EntityMovable {
	public static final int DIR_NORTH = 0;
	public static final int DIR_EAST = 1;
	public static final int DIR_SOUTH = 2;
	public static final int DIR_WEST = 3;
	
	public void move(AngleSpeed speed);
	public void move(int direction);
	public float getSpeed();
	public Angle getAngle();
}