package com.brennytizer.jumg.utils.engine;

import com.brennytizer.jumg.utils.Words;

/**
 * A time keeping class that can help out for when you have ageable entities,
 * dynamic day/night cycles, etc.
 * 
 * @author Jarod Brennfleck
 */
public class Time {
	public static String RATIO_E = "1667ms=10m"; // Ratio EQUATION
	public static long[] RATIO_D = {1, 1}; // Ratio DATA. Both entries should be stored as milliseconds.
	//	public static boolean INVERSE_RATIO = false; // Lets the clock know that the left side of the equation is smaller. (Slow down time)
	public static long START_TIME = 0; // The time we started at (Sys.currentTime)
	public static long LAST_UPDATED = 0; // The last time we called the update loop
	public static long[] CLOCK = {0, 0, 0, 0, 0}; // 5 data entries: ms, s, m, h, d
	public static long[] TIME_CONVERSION = {1, 1000, 60000, 3600000, 86400000}; // millisecond conversion to: ms, s, m, h, d
	
	/**
	 * Ideally should be called every time there is a game logic update. It
	 * would be wise to do this at the start of the game logic update as it
	 * allows for better accuracy when showing data to players.
	 * 
	 * Oh, and all this method does is update the in-game clock by using the
	 * ratio provided.
	 */
	public static void update() {
		long now = 0; // This way provides a perfect reading of time. because there is sometimes a couple ms wait before the next instruction is called...
		CLOCK[0] += ((now = System.currentTimeMillis()) - LAST_UPDATED) * RATIO_D[1];
		LAST_UPDATED = now;
		for(int index = 0; index < 4; index++) {
			CLOCK[index + 1] += CLOCK[index] / TIME_CONVERSION[index + 1];
			CLOCK[index] %= TIME_CONVERSION[index + 1];
		}
		//		Above me is the following 8 lines in a loop... :P
		//		CLOCK[1] += CLOCK[0] / TIME_CONVERSION[1];
		//		CLOCK[0] %= TIME_CONVERSION[1];
		//		CLOCK[2] += CLOCK[1] / TIME_CONVERSION[2];
		//		CLOCK[1] %= TIME_CONVERSION[2];
		//		CLOCK[3] += CLOCK[2] / TIME_CONVERSION[3];
		//		CLOCK[2] %= TIME_CONVERSION[3];
		//		CLOCK[4] += CLOCK[3] / TIME_CONVERSION[4];
		//		CLOCK[3] %= TIME_CONVERSION[4];
	}
	
	/**
	 * Returns the in-game time.
	 */
	public static long[] whatsTheTimeMrWolf() {
		return CLOCK;
	}
	
	/**
	 * Returns the time conversion, such as 1000 milliseconds in a second, 60000
	 * milliseconds in a minute, etc.
	 * 
	 * Returned as milliseconds in {ms, s, m, h, d}
	 */
	public static long[] getTimeConversions() {
		return TIME_CONVERSION;
	}
	
	/**
	 * Returns the ratio data which is calculated from the ratio equation
	 * provided.
	 * 
	 * @see #getRatioEquation()
	 */
	public static long[] getRatioData() {
		return RATIO_D;
	}
	
	/**
	 * Returns the human readable ratio equation provided. This eventually gets
	 * turned into the ratio data.
	 */
	public static String getRatioEquation() {
		return RATIO_E;
	}
	
	/**
	 * Returns whether or not the ratio is inverted. Ie, is the smaller number
	 * on the right?
	 */
	public static boolean areWeInverseRatio() {
		// return INVERSE_RATIO;
		return false;
	}
	
	/**
	 * Starts the clock using the default ratio (1667ms=10m).
	 */
	public static void startClock() {
		Time.startClock(Time.RATIO_E);
	}
	
	/**
	 * Starts the clock using the given ratio.
	 */
	public static void startClock(String ratio) {
		updateRatio(ratio);
		for(int i = 0; i < CLOCK.length; i++)
			CLOCK[i] = 0;
		START_TIME = System.currentTimeMillis();
		LAST_UPDATED = START_TIME;
	}
	
	/**
	 * Updates the ratio equation, and the ratio data using the ratio given.
	 * This can be called while the clock is turned on.
	 */
	public static void updateRatio(String ratio) {
		Time.RATIO_E = ratio;
		String[] splitE = ratio.split("=");
		long[] timeConvert = new long[2];
		for(int i = 0; i < 2; i++) {
			if(splitE[i].contains("ms")) {
				splitE[i] = Words.cutAt(splitE[i], splitE[i].length() - 2);
				timeConvert[i] = TIME_CONVERSION[0];
			} else if(splitE[i].contains("s")) {
				splitE[i] = Words.cutAt(splitE[i], splitE[i].length() - 1);
				timeConvert[i] = TIME_CONVERSION[1];
			} else if(splitE[i].contains("m")) {
				splitE[i] = Words.cutAt(splitE[i], splitE[i].length() - 1);
				timeConvert[i] = TIME_CONVERSION[2];
			} else if(splitE[i].contains("h")) {
				splitE[i] = Words.cutAt(splitE[i], splitE[i].length() - 1);
				timeConvert[i] = TIME_CONVERSION[3];
			} else if(splitE[i].contains("d")) {
				splitE[i] = Words.cutAt(splitE[i], splitE[i].length() - 1);
				timeConvert[i] = TIME_CONVERSION[4];
			} else {
				splitE[i] = Words.cutAt(splitE[i], splitE[i].length() - 2);
				timeConvert[i] = TIME_CONVERSION[0];
			}
		}
		long[] ratioData = {Math.abs(Long.parseLong(splitE[0])) * timeConvert[0], Math.abs(Long.parseLong(splitE[1])) * timeConvert[1]};
		// WHILE WE HAVEN'T IMPLEMENTED RATIO INVERSE:
		long a = Math.min(ratioData[0], ratioData[1]);
		long b = Math.max(ratioData[0], ratioData[1]);
		ratioData[0] = a;
		ratioData[1] = b;
		// WHILE WE HAVEN'T IMPLEMENTED RATIO INVERSE:
		Time.RATIO_D = ratioData;
	}
}