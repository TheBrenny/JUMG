package com.brennytizer.jumg.utils;

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
}
