package com.brennytizer.jumg.utils;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.brennytizer.jumg.utils.Logging.LoggingSpice;

/**
 * This class is dedicated to loading images. Extending this class will allow
 * for better intergration.
 * 
 * @author Jarod Brennfleck
 */
public class Images {
	/**
	 * The package to retrieve images from.
	 */
	public static String IMAGE_PACKAGE = "/com/brennytizer/jumg/utils/";
	
	public static BufferedImage TEST_IMAGE;
	
	static {
		Logging.log(LoggingSpice.MILD, "Loading images");
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
	 * @see #getImage(String, String)
	 */
	public static BufferedImage getImage(String image) {
		return getImage(image, ".png");
	}
	
	/**
	 * Returns an image by using a method of finding the image inside the jar
	 * files by following:<br>
	 * <br>
	 * <code>{@link #IMAGE_PACKAGE} + image + extension</code>
	 * 
	 * @param image
	 *        - The image name to use (excluding the file extension)
	 * @return {@link BufferedImage} - The image if found, null if not found.
	 * @see #getImage(String)
	 */
	public static BufferedImage getImage(String image, String extension) {
		try {
			return ImageIO.read(Images.class.getResourceAsStream(IMAGE_PACKAGE + image + extension));
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