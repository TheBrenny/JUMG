package com.brennytizer.cars.levels.tiles;

public class TileManager {
	public static boolean initialised = false;
	static {
		new TileGrass();
		initialised = true;
	}
}
