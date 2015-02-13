package com.brennytizer.jumg.entities;

import java.util.ArrayList;

/**
 * A simple interface that allows for the use of interactable enities such as
 * NPCs, doors, levers, etc. This is different from the EntityAI interface as
 * that is aimed towards the brain of an entity, whereas this class is used
 * mainly for NPCs, such as a shop or when giving a quest.
 * 
 * Do note that only EntityInteractables can interact with other
 * EntityInteractables. If you have a pig which doesn't implement this interface
 * you won't be able to interact with it.
 * 
 * @author Jarod Brennfleck
 */
public interface EntityInteractable {
	/**
	 * Returns the name of what this entity does as an interaction. This is up
	 * to the programmer of the game, if they choose to not use this, that's
	 * okay. VERY useful when debugging.
	 */
	public String wutDoing();
	
	/**
	 * Returns whether or not the specified entity can interact with this
	 * entity.
	 * 
	 * @param entity
	 *        - The entity to check.
	 * @return True if the entity is able to interact with this entity.
	 */
	public boolean canInteract(EntityInteractable entity);
	
	/**
	 * Returns whether or not this entity is interacting with any other entity
	 * at the current moment.
	 * 
	 * @see #isInteracting(Entity)
	 */
	public boolean isInteracting();
	
	/**
	 * Returns whether or not this entity is interacting with the specified
	 * entity at the current moment.
	 * 
	 * @param entity
	 *        - The entity to check.
	 * @return True if the two entities are interacting.
	 */
	public boolean isInteracting(EntityInteractable entity);
	
	/**
	 * Start the interaction between the two parties.
	 * 
	 * @param entity
	 *        - The entity to start the interaction with.
	 */
	public void startInteraction(EntityInteractable entity);
	
	/**
	 * Stop the interaction between the two parties.
	 * 
	 * @param entity
	 *        - The entity to stop the interaction with.
	 */
	public void stopInteraction(EntityInteractable entity);
	
	/**
	 * Returns a list of all entities who have started an interaction with this
	 * entity, but have not ended their interaction.
	 */
	public ArrayList<EntityInteractable> getEntityList();
}