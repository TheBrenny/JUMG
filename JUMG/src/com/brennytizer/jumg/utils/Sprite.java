package com.brennytizer.jumg.utils;

import java.awt.image.BufferedImage;

import com.brennytizer.jumg.utils.fileio.FileInstantiable;
import com.brennytizer.jumg.utils.fileio.FileInterpreter;

/**
 * This class specifically extends the BufferedImage class to allow for direct
 * access to the drawing methods.
 * 
 * @author Jarod Brennfleck
 */
public class Sprite extends BufferedImage implements FileInstantiable {
	public BufferedImage map;
	public int posX;
	public int posY;
	public int width;
	public int height;
	
	public Sprite() {
		super(0, 0, BufferedImage.TYPE_INT_ARGB);
	}
	
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
	 * 
	 * @return {@link BufferedImage} - The map currently in use.
	 */
	public BufferedImage getMap() {
		return map;
	}
	
	/**
	 * Returns the x position in pixels.
	 * 
	 * @return Integer - the amount of pixels from the left of the map.
	 */
	public int getPosX() {
		return posX;
	}
	
	/**
	 * Returns the y position in pixels.
	 * 
	 * @return Integer - the amount of pixels from the top of the map.
	 */
	public int getPosY() {
		return posY;
	}
	
	/**
	 * Returns the width of the sprite.
	 * 
	 * @return Integer - the width of the sprite.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height of the sprite.
	 * 
	 * @return Integer - the height of the sprite.
	 */
	public int getHeight() {
		return height;
	}

	public Object instantiateWithParams(String ... params) {
		BufferedImage map = (BufferedImage) FileInterpreter.generateObject(params[0])[1];
		int x = Integer.parseInt(params[1]);
		int y = Integer.parseInt(params[2]);
		int width = Integer.parseInt(params[3]);
		int height = Integer.parseInt(params[4]);
		Sprite ret = new Sprite(map, x, y, width, height);
		return ret;
	}
}