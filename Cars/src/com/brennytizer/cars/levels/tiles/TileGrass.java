package com.brennytizer.cars.levels.tiles;

import com.brennytizer.cars.util.MyImages;
import com.brennytizer.jumg.level.Tile;

public class TileGrass extends Tile {
	public TileGrass() {
		super("Grass", 1, false, false, null, MyImages.getSprite(MyImages.TILE_MAP, 0, 0, 32, 32));
	}
}