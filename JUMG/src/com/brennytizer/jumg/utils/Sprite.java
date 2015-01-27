package com.brennytizer.jumg.utils;

import java.awt.image.BufferedImage;

/**
 * This class specifically extends the BufferedImage class to allow for direct
 * access to the drawing methods.
 * 
 * @author Jarod
 */
public class Sprite extends BufferedImage {
	public BufferedImage map;
	public int posX;
	public int posY;
	public final int width;
	public final int height;
	
	/**
	 * Constructs a sprite based off the parameters given.
	 * 
	 * @param map
	 *        - The sprite map to use
	 * @param posX
	 *        - The x position (in pixels)
	 * @param posY
	 *        - The y position (in pixels)
	 * @param width
	 *        - The width (in pixels)
	 * @param height
	 *        - The height (in pixels)
	 */
	public Sprite(BufferedImage map, int posX, int posY, int width, int height) {
		super(width, height, BufferedImage.TYPE_INT_ARGB);
		this.width = width;
		this.height = height;
		updateData(map, posX, posY);
	}
	
	/**
	 * Redraws the sub-image from the map to the sprite.
	 */
	public void updateSprite() {
		this.getGraphics().drawImage(map.getSubimage(posX, posY, width, height), 0, 0, null);
	}
	
	/**
	 * Updates all the data for the sprite. If there is data you wish to
	 * unchange then you can use the get methods for this sprite.
	 * 
	 * @param map
	 *        - The sprite map to use
	 * @param posX
	 *        - The x position (in pixels)
	 * @param posY
	 *        - The y position (in pixels)
	 */
	public void updateData(BufferedImage map, int posX, int posY) {
		this.map = map;
		this.posX = posX;
		this.posY = posY;
		updateSprite();
	}
	
	/**
	 * Returns the map currently in use.
	 * @return {@link BufferedImage} - The map currently in use.
	 */
	public BufferedImage getMap() {
		return map;
	}
	
	/**
	 * Returns the x position in pixels.
	 * @return Integer - the amount of pixels from the left of the map.
	 */
	public int getPosX() {
		return posX;
	}
	
	/**
	 * Returns the y position in pixels.
	 * @return Integer - the amount of pixels from the top of the map.
	 */
	public int getPosY() {
		return posY;
	}
	
	/**
	 * Returns the width of the sprite.
	 * @return Integer - the width of the sprite.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height of the sprite.
	 * @return Integer - the height of the sprite.
	 */
	public int getHeight() {
		return height;
	}
}