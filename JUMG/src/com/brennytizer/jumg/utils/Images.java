package com.brennytizer.jumg.utils;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * This class is dedicated to loading images. Extending this class will allow
 * for better intergration.
 * 
 * @author jarod
 */
public class Images {
	/**
	 * The package to retrieve images from.
	 */
	public static String IMAGE_PACKAGE = "/com/brennytizer/jumg/gui/images/";
	public static BufferedImage GUI_BUTTON;
	static {
		GUI_BUTTON = getImage("gui_button");
	}
	
	/**
	 * Returns an image by using a method of finding the image inside the jar
	 * files by following:<br>
	 * <br>
	 * <code>{@link #IMAGE_PACKAGE} + image + ".png"</code>
	 * 
	 * @param image
	 *        - The image name to use (excluding the file extension)
	 * @return {@link BufferedImage} - The image if found, null if not found.
	 */
	public static BufferedImage getImage(String image) {
		try {
			return ImageIO.read(Images.class.getResourceAsStream(IMAGE_PACKAGE + image + ".png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns a new {@link Sprite} based off the parameters given.
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
	 * @return {@link Sprite} - The Sprite created.
	 */
	public static Sprite getSprite(BufferedImage map, int posX, int posY, int width, int height) {
		return new Sprite(map, posX, posY, width, height);
	}
}