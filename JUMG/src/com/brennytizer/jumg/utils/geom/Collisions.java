package com.brennytizer.jumg.utils.geom;

import com.brennytizer.jumg.utils.Math;
import com.brennytizer.jumg.utils.Math.Vector2D;

/**
 * A broad class that contains methods that have things to do with collisions.
 * 
 * @author Jarod Brennfleck
 */
public class Collisions {
	/**
	 * Builds an 'Axis Aligned Bounding Box' using the {@link Polygon} provided.
	 * 
	 * @param p
	 *        - The polygon to make an AABB around.
	 * @return {@link Rectangle2D} -The AABB created.
	 * @see #buildAABB(PolygonalObject)
	 */
	public static Rectangle2D buildAABB(Polygon p) {
		return buildAABB((PolygonalObject) p);
	}
	
	/**
	 * Builds an 'Axis Aligned Bounding Box' using the {@link PolygonalObject}
	 * provided.
	 * 
	 * @param p
	 *        - The polygon to make an AABB around.
	 * @return {@link Rectangle2D} -The AABB created.
	 * @see #buildAABB(Polygon)
	 */
	public static Rectangle2D buildAABB(PolygonalObject po) {
		float minX = Float.MAX_VALUE;
		float maxX = Float.MIN_VALUE;
		float minY = minX;
		float maxY = maxX;
		for(Point2D p : po.getPoints()) {
			minX = p.x < minX ? p.x : minX;
			maxX = p.x < maxX ? p.x : maxX;
			minY = p.y < minY ? p.y : minY;
			maxY = p.y < maxY ? p.y : maxY;
		}
		return new Rectangle2D(minX, minY, maxX - minX, maxY - minY);
	}
	
	/**
	 * Combines zero or more PolygonalObjects to create an AABB that
	 * encompasses all objects.
	 * 
	 * @param objs
	 *        - The {@link PolygonalObject}s to build the larger AABB around.
	 * @return {@link Rectangle2D} - The AABB created.
	 * @see #combineAABB(Rectangle2D...)
	 */
	public static Rectangle2D combineAABB(PolygonalObject ... objs) {
		Rectangle2D[] aabbs = new Rectangle2D[objs.length];
		for(int i = 0; i < objs.length; i++)
			aabbs[i] = buildAABB(objs[i]);
		return combineAABB(aabbs);
	}
	
	/**
	 * Combines zero or more Rectangle2Ds to create an AABB that encompasses all
	 * objects.
	 * 
	 * @param aabbs
	 *        - {@link Rectangle2D}s to combine together to make the larger
	 *        AABB.
	 * @return {@link Rectangle2D} - The AABB created.
	 * @see #combineAABB(PolygonalObject...)
	 */
	public static Rectangle2D combineAABB(Rectangle2D ... aabbs) {
		float minX = Float.MAX_VALUE;
		float maxX = Float.MIN_VALUE;
		float minY = minX;
		float maxY = maxX;
		for(Rectangle2D rect : aabbs) {
			for(Point2D p : rect.getPoints()) {
				minX = p.x < minX ? p.x : minX;
				maxX = p.x < maxX ? p.x : maxX;
				minY = p.y < minY ? p.y : minY;
				maxY = p.y < maxY ? p.y : maxY;
			}
		}
		return new Rectangle2D(minX, minY, maxX - minX, maxY - minY);
	}
	
	/**
	 * Rotates a {@link PolygonalObject} around the origin of the
	 * PolygonalObject's AABB, at the specified angle.
	 * 
	 * @param obj
	 *        - The PolygonalObject to rotate.
	 * @param angle
	 *        - The angle to rotate for.
	 * @return {@link Polygon} - Containing all the new points for the rotated
	 *         PolygonalObject.
	 * @see #rotatePolygon(PolygonalObject, Point2D, float)
	 */
	public static Polygon rotatePolygon(PolygonalObject obj, float angle) {
		return rotatePolygon(obj, Math.getMidPoint(obj), angle);
	}
	
	/**
	 * Rotates a {@link PolygonalObject} around an origin, at the specified
	 * angle.
	 * 
	 * @param obj
	 *        - The PolygonalObject to rotate.
	 * @param origin
	 *        - The Origin to rotate the <code>obj</code> around.
	 * @param angle
	 *        - The angle to rotate for.
	 * @return {@link Polygon} - Containing all the new points for the rotated
	 *         PolygonalObject.
	 * @see #rotatePolygon(PolygonalObject, float)
	 */
	public static Polygon rotatePolygon(PolygonalObject obj, Point2D origin, float angle) {
		while(angle > 180F)
			angle -= 360F;
		Point2D[] pts = new Point2D[obj.getPoints().length];
		for(int i = 0; i < pts.length; i++) {
			pts[i] = new Point2D(obj.getPoints()[i]).rotate(origin, angle);
		}
		return new Polygon(pts);
	}
	
	/**
	 * Determines whether or not infinite lines AB and PQ crossover or, at
	 * least, touch.
	 * 
	 * @param a
	 *        - The start of line AB.
	 * @param b
	 *        - The end of line AB.
	 * @param p
	 *        - The start of line PQ.
	 * @param q
	 *        - The end of line PQ.
	 * @return Boolean - True if point P and point Q are on opposite sides of
	 *         line AB, or if one of the points is exactly 0 units away from
	 *         line AB.
	 */
	public static boolean crossLines(Point2D a, Point2D b, Point2D p, Point2D q) {
		float crossProdP = (a.x - b.x) * (p.y - a.y) - (a.y - b.y) * (p.x - a.x);
		float crossProdQ = (a.x - b.x) * (q.y - a.y) - (a.y - b.y) * (q.x - a.x);
		float crossProdA = (p.x - q.x) * (a.y - p.y) - (p.y - q.y) * (a.x - p.x);
		float crossProdB = (p.x - q.x) * (b.y - p.y) - (p.y - q.y) * (b.x - p.x);
		if(crossProdP == 0 || crossProdQ == 0 || crossProdA == 0 || crossProdB == 0) return true;
		return !Math.sameSign(crossProdP, crossProdQ);
	}
	
	/**
	 * Checks whether two AABBs are colliding using the corner check theory and
	 * by using the positioning theory.
	 * 
	 * @param aabbA
	 *        - The first AABB to use.
	 * @param aabbB
	 *        - The second AABB to use.
	 * @return Boolean - Whether or not the AABBS are colliding.
	 */
	public static boolean collidedAABB(Rectangle2D aabbA, Rectangle2D aabbB) {
		for(Point2D p : aabbA.getPoints())
			if(aabbB.contains(p)) return true;
		for(Point2D p : aabbB.getPoints())
			if(aabbA.contains(p)) return true;
		if(aabbA.y < aabbB.y && aabbA.y + aabbA.height > aabbB.y && aabbA.x < aabbB.x && aabbA.x + aabbA.width > aabbB.x) return true;
		if(aabbB.y < aabbA.y && aabbB.y + aabbB.height > aabbA.y && aabbB.x < aabbA.x && aabbB.x + aabbB.width > aabbA.x) return true;
		return false;
	}
	
	/**
	 * Checks whether or not a circle has collided with another circle. For
	 * speed and resource efficiency, it will check the squared distance.
	 * 
	 * @param originA
	 *        - The origin of the first circle.
	 * @param radiusA
	 *        - The radius of the first circle.
	 * @param originB
	 *        - The origin of the second circle.
	 * @param radiusB
	 *        - The radius of the second circle.
	 * @return Boolean - Whether or not the circle collide.
	 */
	public static boolean collidedCircles(Point2D originA, float radiusA, Point2D originB, float radiusB) {
		float distSq = Math.getDistanceSq(originA, originB);
		return distSq <= Math.power(radiusA + radiusB, 2);
	}
	
	/**
	 * Determines whether or not a circle collides with an AABB, checking if the
	 * circle's origin is within the AABB or if the origin plus the radius
	 * combined reach the bounds of the AABB.
	 * 
	 * @param circleO
	 *        - The point of where the circle starts.
	 * @param circleR
	 *        - The radius of the circle
	 * @param aabb
	 *        - The Axis Aligned Bounding Box to check.
	 * @return Boolean - Whether the circle and AABB collide
	 */
	/*
	public static boolean collidedCircleAABB(Point2D circleO, float circleR, Rectangle2D aabb) {
		if(aabb.contains(circleO)) return true;
		float closestDistance = Float.MAX_VALUE;
		for(Point2D a : aabb.getPoints()) {
			float tmp = Math.getDistanceSq(a, circleO);
			if(tmp < closestDistance) {
				closestDistance = tmp;
			}
		}
		if(circleO.x + circleR >= aabb.x && circleO.x - circleR <= aabb.x + aabb.width && circleO.y + circleR >= aabb.y && circleO.y - circleR <= aabb.y + aabb.height) return true;
		return closestDistance - circleR <= 0;
	}
	*/
	public static boolean collidedCircleAABB(Point2D circleO, float circleR, Rectangle2D aabb) {
		Vector2D v = new Vector2D(Math.getMidPoint(aabb), circleO).clamp(aabb.width / 2, aabb.height / 2);
		return Math.absolute(v.getRealMagnitude()) - circleR < 0;
	}
	
	/**
	 * Determines whether or not the {@link Polygons} provided contain any of
	 * the other polygon's points.
	 * 
	 * @param polyA
	 *        - The first Polygon.
	 * @param polyB
	 *        - The second Polygon.
	 * @return Boolean - Whether or not the polygons collide.
	 */
	public static boolean collidedPolys(Polygon polyA, Polygon polyB) {
		for(Point2D p : polyB.getPoints())
			if(polyA.contains(p)) return true;
		for(Point2D p : polyA.getPoints())
			if(polyB.contains(p)) return true;
		return false;
	}
	
	public static boolean collidedPolys(PolygonalObject polyA, PolygonalObject polyB) {
		if(collidedPolys(new Polygon(polyA.getPoints()), new Polygon(polyB.getPoints()))) return true;
		for(int a = 0; a + 1 < polyA.getPoints().length; a++) {
			for(int b = 0; b + 1 < polyB.getPoints().length; b++) {
				if(crossLines(polyA.getPoints()[a], polyA.getPoints()[a + 1], polyB.getPoints()[b], polyB.getPoints()[b + 1])) return true;
			}
		}
		return false;
	}
	
	public static boolean collidedCircleOBB(Point2D circleOrigin, float circleRadius, Rectangle2D aabb, float aabbRotation) {
		//float distanceBetweenCorners = Math.root(Math.getDistanceSq(new Point2D(aabb.x, aabb.y), new Point2D(aabb.x + aabb.width, aabb.y + aabb.height)), 2) / 2;
		// distance to rotated point = sqrt(b*b + c*c - 2bc(cos(theta)))
		return false; // I have no idea about how to implement this...
	}
}