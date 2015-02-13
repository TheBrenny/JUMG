package com.brennytizer.jumg.utils.geom;

/**
 * A polygon is a 2D geometrical object with 3 or more sides. Thus it must have
 * at least 3 corners.
 * 
 * @author Jarod Brennfleck
 */
public class Polygon {
	public Point2D[] points;
	
	/**
	 * Constructs a new polygon using the points given.
	 * 
	 * @param points
	 *        - The points that makeup the polygon.
	 */
	public Polygon(Point2D ... points) {
		if(points.length < 3) throw new ArrayIndexOutOfBoundsException("Points must be of length 3 or more, this one only had " + points.length);
		this.points = points;
	}
	
	/**
	 * Checks whether or not the given point is inside this polygon.
	 * 
	 * @param point
	 *        - The point to test.
	 * @return True if this polygon contains the point, false otherwise.
	 */
	public boolean contains(Point2D point) {
		int i;
		int j;
		boolean result = false;
		for(i = 0, j = points.length - 1; i < points.length; j = i++) {
			if((points[i].y > point.y) != (points[j].y > point.y) && (point.x < (points[j].x - points[i].x) * (point.y - points[i].y) / (points[j].y - points[i].y) + points[i].x)) {
				result = !result;
			}
		}
		return result;
	}
	
	/**
	 * Sets a specific point in the array to the point given.
	 * 
	 * @param pointIndex
	 *        - The point to change.
	 * @param point
	 *        - The new point.
	 */
	public void setPoint(int pointIndex, Point2D point) {
		this.points[pointIndex] = point;
	}
	
	/**
	 * Returns the point at the given index.
	 * 
	 * @param pointIndex
	 *        - The index of the point to retrieve.
	 */
	public Point2D getPoint(int pointIndex) {
		return this.points[pointIndex];
	}
	
	/**
	 * Overrides all the points in the array to the point array given.
	 * 
	 * @param points
	 *        - The new set of points.
	 */
	public void setPoints(Point2D ... points) {
		this.points = points;
	}
	
	/**
	 * Returns all points that make up this polygon.
	 */
	public Point2D[] getPoints() {
		return points;
	}
}