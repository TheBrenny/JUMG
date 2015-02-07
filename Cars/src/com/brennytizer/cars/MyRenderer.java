package com.brennytizer.cars;

import java.awt.Graphics2D;

import com.brennytizer.cars.levels.MapsManager;
import com.brennytizer.jumg.frame.Renderer;
import com.brennytizer.jumg.gui.GuiScreen;

public class MyRenderer implements Renderer {
	public GuiScreen screen;
	
	public MyRenderer() {
		screen = new GuiMainMenu();
	}
	
	public void draw(Graphics2D g2d) {
		screen.draw(g2d);
	}
	public void tick() {
		if(screen instanceof MapsManager) {
			((MapsManager) screen).tick();
		}
	}
}