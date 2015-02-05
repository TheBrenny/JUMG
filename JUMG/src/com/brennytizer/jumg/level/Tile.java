package com.brennytizer.jumg.level;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;
import com.brennytizer.jumg.utils.Sprite;

public abstract class Tile {
	public static Tile[] tiles = new Tile[256];
	public String tileName;
	public int id;
	public boolean solid;
	public boolean lightEmmitter;
	public TileEvent event;
	public Sprite tileSprite;
	
	public Tile(String name, int id, boolean solid, boolean lightEmmitter, TileEvent event, Sprite tileSprite) {
		if(tiles[id] != null) {
			Logging.log(LoggingSpice.HOT, "Oh no! There is a tile already here: " + tiles[id].tileName);
		} else {
			tiles[id] = this;
			this.tileName = name;
			this.id = id;
			this.solid = solid;
			this.lightEmmitter = lightEmmitter;
			this.event = event;
			this.tileSprite = tileSprite;
		}
	}
	public String getTileName() {
		return tileName;
	}
	public int getID() {
		return id;
	}
	public boolean isSolid() {
		return solid;
	}
	public boolean isLightEmmitter() {
		return lightEmmitter;
	}
	public TileEvent getEvent() {
		return event;
	}
	public Sprite getTileSprite() {
		return tileSprite;
	}
}