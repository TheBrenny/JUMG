package com.brennytizer.jumg.utils.geom;

/**
 * A class that allows for the creation and tracking of rectangles in a 2D
 * space, to float accuracy.
 * 
 * @author Jarod Brennfleck
 */
public class Rectangle2D extends java.awt.geom.Rectangle2D.Float implements PolygonalObject {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a Rectangle object.
	 * 
	 * @param x
	 *        - The x position of the rectangle.
	 * @param y
	 *        - The y position of the rectangle.
	 * @param width
	 *        - The width of the rectangle.
	 * @param height
	 *        - The height of the rectangle.
	 */
	public Rectangle2D(float x, float y, float width, float height) {
		if(width < 0) x += width;
		if(height < 0) y += height;
		width = Math.abs(width);
		height = Math.abs(height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Determines whether or not the given x and y values are in this rectangle.
	 * 
	 * @param point
	 *        - The {@link Point2D} to use.
	 * @return True if the x and y are both within the rectangle bounds.
	 */
	public boolean contains(Point2D point) {
		return contains(point.x, point.y);
	}
	
	/**
	 * Determines whether or not the given x and y values are in this rectangle.
	 * 
	 * @param x
	 *        - The x position to test.
	 * @param y
	 *        - The y position to test.
	 * @return True if the x and y are both within the rectangle bounds.
	 */
	public boolean contains(float x, float y) {
		return super.contains((double) x, (double) y);
	}
	
	/**
	 * Moves this rectangle to the specified point.
	 * 
	 * @param point
	 *        - The location to move to.
	 */
	public void move(Point2D point) {
		move(point.x, point.y);
	}
	
	/**
	 * Moves this rectangle to the specified coordinates.
	 * 
	 * @param x
	 *        - The x location to move to.
	 * @param y
	 *        - The y location to move to.
	 */
	public void move(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Shifts the x and y position by adding the point passed in.
	 * 
	 * @param point
	 *        - The point to move by.
	 */
	public void shift(Point2D point) {
		shift(point.x, point.y);
	}
	
	/**
	 * Shifts the x and y position by adding the amounts passed in.
	 * 
	 * @param xMove
	 *        - The amount of x units to move by.
	 * @param yMove
	 *        - The amount of y units to move by.
	 */
	public void shift(float xMove, float yMove) {
		this.x += xMove;
		this.y += yMove;
	}
	
	/**
	 * Resizes the rectangle by overriding the currently stored width and height
	 * value.
	 * 
	 * @param width
	 *        - The new width of the rectangle.
	 * @param height
	 *        - The new height of the rectangle.
	 */
	public void resize(float width, float height) {
		this.width = width;
		this.height = height;
	}
}