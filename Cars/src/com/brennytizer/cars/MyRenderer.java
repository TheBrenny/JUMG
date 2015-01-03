package com.brennytizer.cars;

import java.awt.Graphics2D;

import com.brennytizer.jumg.frame.Renderer;

public class MyRenderer implements Renderer {
	public GuiMainMenu mainMenu;
	
	public MyRenderer() {
		mainMenu = new GuiMainMenu();
	}
	
	public void draw(Graphics2D g2d) {
		mainMenu.draw(g2d);
	}
}