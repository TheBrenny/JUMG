package com.brennytizer.jumg.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;
import com.brennytizer.jumg.utils.Renderable;

public class Map {
	public int width;
	public int height;
	public Tile[][] tiles;
	public ArrayList<Renderable> renderables;
	public int tileSize;
	
	public Map(String mapFile, String mapSettings, int width, int height) throws Exception {
		Logging.log(LoggingSpice.MILD, "Attempting to make a map...");
		this.width = width;
		this.height = height;
		MapMaker mm = new MapMaker(mapFile, mapSettings, width, height);
		this.tiles = mm.read();
		this.tileSize = (tiles[0][0].tileSprite.width + tiles[0][0].tileSprite.height) / 2;
		this.renderables = new ArrayList<Renderable>();
	}
	
	public void render(Graphics2D g2d, float xOffset, float yOffset, int boardWidth, int boardHeight, float scale) {
		float tileSize = this.tileSize * scale;
		xOffset = xOffset < 0 ? 0 : xOffset + boardWidth > tiles[0].length * tileSize ? tiles[0].length * tileSize - boardWidth : xOffset;
		yOffset = yOffset < 0 ? 0 : yOffset + boardHeight > tiles.length * tileSize ? tiles.length * tileSize - boardHeight : yOffset;
		for(int x = 0, y = 0; y < tiles.length; x++, y += x / tiles[0].length, x %= tiles[0].length) {
			if(x * tileSize + tileSize >= xOffset && y * tileSize + tileSize >= yOffset && x * tileSize <= xOffset + boardWidth && y * tileSize <= yOffset + boardHeight) {
				g2d.drawImage(tiles[y][x].tileSprite, (int) (x * tileSize - xOffset), (int) (y * tileSize - yOffset), (int) tileSize, (int) tileSize, null);
			}
		}
		renderRenderables(g2d, this.renderables, xOffset, yOffset, boardWidth, boardHeight, scale);
		renderExtras(g2d, xOffset, yOffset, boardWidth, boardHeight, scale);
	}
	
	public void renderRenderables(Graphics2D g2d, ArrayList<Renderable> renderables, float xOffset, float yOffset, int boardWidth, int boardHeight, float scale) {
		for(Renderable r : renderables) {
			if((r.getX() + r.getWidth()) * scale >= xOffset && (r.getY() + r.getHeight()) * scale >= yOffset && r.getX() <= xOffset + boardWidth && r.getY() <= yOffset + boardHeight) {
				g2d.drawImage(r.getAngle().getRotation(r.getSprite()), (int) (r.getX() * scale), (int) (r.getY() * scale), (int) (r.getWidth() * scale), (int) (r.getHeight() * scale), null);
			}
		}
	}
	
	public void renderExtras(Graphics2D g2d, float xOffset, float yOffset, int boardWidth, int boardHeight, float scale) {}
	
	public ArrayList<Renderable> orderRenderables(ArrayList<Renderable> renderables) {
		return renderables;
	}
	
	public int getWidthInTiles() {
		return width;
	}
	
	public int getHeightInTiles() {
		return height;
	}
	
	public class MapMaker {
		public MapSettings mapSettings;
		public BufferedImage map;
		public Tile[][] tiles;
		
		public MapMaker(String mapFile, String mapSettings, int width, int height) throws IOException {
			this.map = ImageIO.read(MapMaker.class.getResourceAsStream(mapFile));
			this.mapSettings = new MapSettings(mapSettings);
			this.mapSettings.read();
			this.tiles = new Tile[height][width];
		}
		
		public Tile[][] read() throws Exception {
			for(int x = 0, y = 0; y < tiles.length; x++, y += x / tiles[0].length, x %= tiles[0].length) {
				Tile t = Tile.tiles[mapSettings.getIdOfColour(new Color(map.getRGB(x, y)).getRGB() & 0xFFFFFF)];
				tiles[y][x] = t;
			}
			return tiles;
		}
	}
}