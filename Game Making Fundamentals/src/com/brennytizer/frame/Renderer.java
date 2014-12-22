package com.brennytizer.frame;

import java.awt.Graphics2D;

/**
 * Initially intended to be a Tag Interface this interface forces implementation
 * of the draw method which is used by the {@link Display} class.
 * 
 * @author jarod
 */
public interface Renderer {
	/**
	 * The method which should draw items using the graphics object passed.
	 * 
	 * @param g2d
	 *        - The Graphics2D object to draw with.
	 */
	public void draw(Graphics2D g2d);
}