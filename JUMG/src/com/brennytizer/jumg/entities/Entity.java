package com.brennytizer.jumg.entities;

import java.util.ArrayList;

import com.brennytizer.jumg.entities.ai.AiStateMachine;
import com.brennytizer.jumg.utils.Renderable;
import com.brennytizer.jumg.utils.Sprite;
import com.brennytizer.jumg.utils.geom.Collisions;
import com.brennytizer.jumg.utils.geom.Point2D;
import com.brennytizer.jumg.utils.geom.PolygonalObject;
import com.brennytizer.jumg.utils.geom.Rectangle2D;

/**
 * An abstract class that defines the rules and values for all entities in the
 * game.
 * 
 * @author Jarod Brennfleck
 */
public abstract class Entity implements Renderable {
	public static ArrayList<Entity> loadedEntities;
	public String uid;
	public Sprite sprite;
	public Point2D location;
	public PolygonalObject polygon;
	public AiStateMachine aiMachine;
	
	/**
	 * Creates an entity object using a {@link Rectangle2D} as the polygon.
	 * 
	 * @param uid
	 *        - The <b>U</b>nique <b>ID</b>entifier for this entity.
	 * @param sprite
	 *        - The sprite to display this entity.
	 * @param location
	 *        - The location of the upper left corner of this entity.
	 * @param polygon
	 *        - The polygon of this entity.
	 * @see #Entity(String, Sprite, Point2D, PolygonalObject)
	 */
	public Entity(String uid, Sprite sprite, Point2D location, Rectangle2D dimensions) {
		this(uid, sprite, location, (PolygonalObject) dimensions);
	}
	
	/**
	 * Creates an entity object with a {@link PolygonalObject} of any sort.
	 * 
	 * @param uid
	 *        - The <b>U</b>nique <b>ID</b>entifier for this entity.
	 * @param sprite
	 *        - The sprite to display this entity.
	 * @param location
	 *        - The location of the upper left corner of this entity.
	 * @param polygon
	 *        - The polygon of this entity.
	 * @see #Entity(String, Sprite, Point2D, Rectangle2D)
	 */
	public Entity(String uid, Sprite sprite, Point2D location, PolygonalObject polygon) {
		this.uid = uid;
		this.sprite = sprite;
		this.location = location;
		this.polygon = polygon;
	}
	
	public final void update() {
		getAiStateMachine().updateState();
		tick();
	}
	
	/**
	 * Should be called everytime there is a game logic update, eg, whenever the
	 * engine ticks.
	 */
	public abstract void tick();
	
	public void setAiStateMachine(AiStateMachine aiMachine) {
		this.aiMachine = aiMachine;
	}
	
	public AiStateMachine getAiStateMachine() {
		return this.aiMachine;
	}
	
	public String getUid() {
		return this.uid;
	}
	
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
		return Collisions.buildAABB(polygon).width;
	}
	
	public float getHeight() {
		return Collisions.buildAABB(polygon).height;
	}
	
	public Point2D[] getPoints() {
		return polygon.getPoints();
	}
	
	public static boolean addEntityToLoaded(Entity entity) {
		synchronized(Entity.loadedEntities) {
			return Entity.loadedEntities.add(entity);
		}
	}
	
	public static synchronized ArrayList<Entity> getLoadedEntitiesList() {
		synchronized(Entity.loadedEntities) {
			return Entity.loadedEntities;
		}
	}
	
	public static Entity[] getLoadedEntitiesArray() {
		Entity[] ret = null;
		synchronized(Entity.loadedEntities) {
			ArrayList<Entity> ents = getLoadedEntitiesList();
			ret = ents.toArray(new Entity[0]);
		}
		return ret;
	}
}