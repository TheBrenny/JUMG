package com.brennytizer.jumg.headsup;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import com.brennytizer.jumg.utils.Math;

public class HudManager {
	public HashMap<String, HudElement> elements;
	
	public HudManager() {
		elements = new HashMap<String, HudElement>();
	}
	
	public void update() {
		for(HudElement he : elements.values())
			he.update();
	}
	
	public BufferedImage render(int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		for(HudElement he : elements.values()) {
			BufferedImage ren = he.render();
			int x = (width - ren.getWidth()) / 2;
			int y = (height - ren.getHeight()) / 2;
			for(String key : he.relationalLocation().keySet()) {
				String value = he.relationalLocation().get(key);
				switch(key) {
				case "bottom":
					if(value.contains("%")) {
						float percent = Float.parseFloat(value.substring(0, value.indexOf('%')));
						y = (int) ((height - ren.getHeight()) - ((percent / 100) * height - ren.getHeight()));
					} else y = (int) ((height - ren.getHeight()) - Float.parseFloat(value));
					break;
				case "top":
					if(value.contains("%")) {
						float percent = Float.parseFloat(value.substring(0, value.indexOf('%')));
						y = (int) ((percent / 100) * height - ren.getHeight());
					} else y = (int) Float.parseFloat(value);
					break;
				case "left":
					if(value.contains("%")) {
						float percent = Float.parseFloat(value.substring(0, value.indexOf('%')));
						x = (int) ((percent / 100) * width - ren.getWidth());
					} else x = (int) Float.parseFloat(value);
					break;
				case "right":
					if(value.contains("%")) {
						float percent = Float.parseFloat(value.substring(0, value.indexOf('%')));
						x = (int) ((width - ren.getWidth()) - ((percent / 100) * width - ren.getWidth()));
					} else x = (int) ((width - ren.getWidth()) - Float.parseFloat(value));
					break;
				case "random-x":
					String[] splitX = value.split(":");
					float minX = Float.parseFloat(splitX[0]);
					float maxX = Float.parseFloat(splitX[1]);
					minX = Math.clampFloat(minX, 0, width - ren.getWidth());
					maxX = Math.clampFloat(maxX, 0, width - ren.getWidth());
					x = (int) Math.random(minX, maxX, 0);
					break;
				case "random-y":
					String[] splitY = value.split(":");
					float minY = Float.parseFloat(splitY[0]);
					float maxY = Float.parseFloat(splitY[1]);
					minY = Math.clampFloat(minY, 0, height - ren.getHeight());
					maxY = Math.clampFloat(maxY, 0, height - ren.getHeight());
					y = (int) Math.random(minY, maxY, 0);
					break;
				}
			}
			g2d.drawImage(he.render(), x, y, null);
		}
		g2d.dispose();
		return bi;
	}
	
	public synchronized HashMap<String, HudElement> getElements() {
		return elements;
	}
	
	public void addHudElement(String elementName, HudElement element) {
		getElements().put(elementName, element);
	}
	
	public HudElement getHudElement(String elementName) {
		return getElements().get(elementName);
	}
	
	public boolean removeHudElement(String elementName) {
		return getElements().remove(elementName) != null;
	}
}
