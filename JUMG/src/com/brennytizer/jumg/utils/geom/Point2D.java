package com.brennytizer.jumg.utils.geom;

/**
 * A class to track a location in 2D space, to an accurate level.
 * 
 * @author jarod
 */
public class Point2D extends java.awt.geom.Point2D.Float {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new Point2D at coordinates (0,0).
	 */
	public Point2D() {
		super(0, 0);
	}
	
	/**
	 * Constructs a new Point2D at coordinates (x,y).
	 * 
	 * @param x
	 *        - The x coordinate.
	 * @param y
	 *        - The y coordinate.
	 */
	public Point2D(float x, float y) {
		super(x, y);
	}
	
	/**
	 * Constructs a new Point2D at the specified point's location (essentially
	 * duplicating).
	 * 
	 * @param point
	 *        - The point to duplicate.
	 */
	public Point2D(Point2D point) {
		super(point.x, point.y);
	}
	
	/**
	 * Sets this point's x coordinate.
	 * 
	 * @param x
	 *        - The new x coordinate.
	 */
	public void setLocationX(float x) {
		this.x = x;
	}
	
	/**
	 * Sets this points y coordinate.
	 * 
	 * @param y
	 *        - The new y coordinate.
	 */
	public void setLocationY(float y) {
		this.y = y;
	}
	
	/**
	 * Tests whether or not the specified x and y coordinates are within the
	 * square radius of range from this point.
	 * 
	 * @param x
	 *        - The x coordinate to test.
	 * @param y
	 *        - The y coordinate to test.
	 * @param range
	 *        - How far from this point should be included.
	 * @return True if the coordinates are contained within the rectangle.
	 */
	public boolean contains(float x, float y, float range) {
		Rectangle2D rect = new Rectangle2D(this.x - range, this.y - range, range * 2, range * 2);
		return rect.contains(new Point2D(x, y));
	}
}