package com.brennytizer.jumg.entities;

import com.brennytizer.jumg.utils.Renderable;
import com.brennytizer.jumg.utils.Sprite;
import com.brennytizer.jumg.utils.geom.Point2D;
import com.brennytizer.jumg.utils.geom.PolygonalObject;
import com.brennytizer.jumg.utils.geom.Rectangle2D;

/**
 * An abstract class that defines the rules and values for all entities in the
 * game.
 * 
 * @author Jarod Brennfleck
 */
public abstract class Entity implements Renderable, PolygonalObject {
	public String uid;
	public Sprite sprite;
	public Point2D location;
	public Rectangle2D dimensions;
	
	/**
	 * Creates an entity object.
	 * 
	 * @param uid
	 *        - The <b>U</b>nique <b>ID</b>entifier for this entity.
	 * @param sprite
	 *        - The sprite to display this entity.
	 * @param location
	 *        - The location of the upper left corner of this entity.
	 * @param dimensions
	 *        - The dimensions of this entity.
	 */
	public Entity(String uid, Sprite sprite, Point2D location, Rectangle2D dimensions) {
		this.uid = uid;
		this.sprite = sprite;
		this.location = location;
		this.dimensions = dimensions;
	}
	
	/**
	 * Should be called everytime there is a game logic update, eg, whenever the
	 * engine ticks.
	 */
	public abstract void tick();
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public float getX() {	
		return location.x;
	}
	
	public float getY() {
		return location.y;
	}
	
	public float getWidth() {
		return dimensions.width;
	}
	
	public float getHeight() {
		return dimensions.height;
	}
	
	public String getMetadata() {
		return "";
	}
}