package com.brennytizer.jumg.entities.ai.pathing;

import com.brennytizer.jumg.utils.geom.PolygonalObject;

public interface MapChecker {
	public float getCostModifier(Mover mover);
	
	public float getCostModifier(Mover mover, float sX, float xY, float aX, float aY);
	
	public PolygonalObject getNodePolygon();
	
	public boolean canMove(Mover mover, float x, float y);
}
