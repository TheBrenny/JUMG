package com.brennytizer.jumg.utils;

import com.brennytizer.jumg.utils.geom.Angle;
import com.brennytizer.jumg.utils.geom.PolygonalObject;

public interface Renderable extends PolygonalObject {
	/**
	 * Returns the sprite of this entity.
	 */
	public Sprite getSprite();
	
	/**
	 * Returns the x coordinate of this entity.
	 */
	public float getX();
	
	/**
	 * Returns the y coordinate of this entity.
	 */
	public float getY();
	
	/**
	 * Returns the width of this entity.
	 */
	public float getWidth();
	
	/**
	 * Returns the height of this entity.
	 */
	public float getHeight();
	
	/**
	 * Returns any extra metadata associated with this entity.
	 */
	public String getMetadata();
	
	/**
	 * Returns the angle of this renderable.
	 */
	public Angle getAngle();
}