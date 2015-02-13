package com.brennytizer.jumg.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Essentially just a {@link GuiLabel} that draws an image instead of text.
 * 
 * @author Jarod Brennfleck
 */
public class GuiImageLabel extends GuiLabel {
	public BufferedImage image;
	
	/**
	 * Construct a GuiImageLabel using the parameters provided.
	 * 
	 * @param x
	 *        - The x position of the anchor.
	 * @param y
	 *        - The y position of the anchor.
	 * @param image
	 *        - The image to display.
	 */
	public GuiImageLabel(int x, int y, BufferedImage image) {
		super(x, y, null, null, "center:center");
		this.image = image;
		boundingBox.resize(getWidth(), getHeight());
	}
	
	public float getX() {
		float xOff = anchor[0].equalsIgnoreCase("right") ? getWidth() : anchor[0].equalsIgnoreCase("center") ? getWidth() / 2 : 0;
		return boundingBox.x - xOff;
	}
	
	public float getY() {
		float yOff = anchor[1].equalsIgnoreCase("bottom") ? getHeight() : anchor[1].equalsIgnoreCase("center") ? getHeight() / 2 : 0;
		return boundingBox.y - yOff;
	}
	
	/**
	 * Returns the width of the image, unless it's null, in which case it would
	 * return 1.
	 */
	public float getWidth() {
		return image == null ? 1 : image.getWidth();
	}
	
	/**
	 * Returns the height of the image, unless it's null, in which case it would
	 * return 1.
	 */
	public float getHeight() {
		return image == null ? 1 : image.getHeight();
	}
	
	/**
	 * Returns the image.
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * Draws this object to the Graphics2d object passed.
	 * 
	 * @param g2d
	 *        - The graphics object to draw on.
	 * @param xOffset
	 *        - The xOffset to use.
	 * @param yOffset
	 *        - The yOffset to use.
	 */
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getImage(), (int) getX(), (int) getY(), null);
	}
}