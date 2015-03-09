package com.brennytizer.jumg.utils.geom;

/**
 * An interface that identifies the sub-class that this object can be expressed
 * as a polygon.
 * 
 * @author Jarod Brennfleck
 */
public interface PolygonalObject {
	/**
	 * Returns all points that make up this polygon.
	 */
	public Point2D[] getPoints();
	
	/**
	 * Creates a {@link Polygon} for this object.
	 */
	public Polygon makePolygon();
}