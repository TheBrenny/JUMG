package com.brennytizer.jumg.utils;

import java.util.Random;

import com.brennytizer.jumg.utils.geom.Collisions;
import com.brennytizer.jumg.utils.geom.Point2D;
import com.brennytizer.jumg.utils.geom.PolygonalObject;
import com.brennytizer.jumg.utils.geom.Rectangle2D;

public class Math {
	// CLAMPS
	/**
	 * @see #clampInt(int, int, int)
	 */
	public static byte clampByte(byte value, byte min, byte max) {
		return value <= min ? min : value >= max ? max : value;
	}
	
	/**
	 * @see #clampInt(int, int, int)
	 */
	public static short clampShort(short value, short min, short max) {
		return value <= min ? min : value >= max ? max : value;
	}
	
	/**
	 * Clamps the value between the minimum and maximum.
	 * 
	 * @param value
	 *        - The value to clamp.
	 * @param min
	 *        - The minimum value.
	 * @param max
	 *        - The maximum value.
	 * @return The clamped value.
	 */
	public static int clampInt(int value, int min, int max) {
		return value <= min ? min : value >= max ? max : value;
	}
	
	/**
	 * @see #clampInt(int, int, int)
	 */
	public static long clampLong(long value, long min, long max) {
		return value <= min ? min : value >= max ? max : value;
	}
	
	/**
	 * @see #clampInt(int, int, int)
	 */
	public static double clampDouble(double value, double min, double max) {
		return value <= min ? min : value >= max ? max : value;
	}
	
	/**
	 * @see #clampInt(int, int, int)
	 */
	public static float clampFloat(float value, float min, float max) {
		return value <= min ? min : value >= max ? max : value;
	}
	
	public static int absolute(int value) {
		return absolute(value, true);
	}
	
	public static int absolute(int value, boolean positive) {
		return java.lang.Math.abs(value) * (positive ? 1 : -1);
	}
	
	public static float absolute(float value) {
		return absolute(value, true);
	}
	
	public static float absolute(float value, boolean positive) {
		return java.lang.Math.abs(value) * (positive ? 1 : -1);
	}
	
	public static float getDistanceSq(Point2D a, Point2D b) {
		return absolute(a.x - b.x) * absolute(a.x - b.x) + absolute(a.y + b.y) * absolute(a.y + b.y);
	}
	
	public static float root(float value, float root) {
		return power((float) java.lang.Math.E, (float) java.lang.Math.log(value) / root);
	}
	
	public static float power(float value, float exponent) {
		return (float) java.lang.Math.pow(value, exponent);
	}
	
	public static long random(String s, int modulus) {
		String fin = "0";
		long count = Long.parseLong(s) + random(-modulus, modulus);
		for(int i = 0; i < count; i++)
			fin += "" + random(0, 9);
		return Long.parseLong(fin);
	}
	
	public static int random(int max){
		return random(0, max);
	}
	public static int random(int min, int max) {
		int tmp = 0;
		if(max < min) {
			tmp = max;
			max = min;
			min = tmp;
		}
		return new Random().nextInt(max - min) + min;
	}
	
	public static float random(float min, float max, int decimals) {
		return Float.parseFloat(random((int) min, (int) max) + "." + random("" + decimals, 0));
	}
	
	public static boolean sameSign(float a, float b) {
		return (a <= 0 && b <= 0) || (a >= 0 && b >= 0);
	}
	
	public static Point2D getMidPoint(PolygonalObject p) {
		Rectangle2D r = Collisions.buildAABB(p);
		return new Point2D(r.width / 2, r.height / 2);
	}
}