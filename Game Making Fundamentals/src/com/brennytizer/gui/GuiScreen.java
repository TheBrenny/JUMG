package com.brennytizer.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class GuiScreen {
	public ArrayList<GuiComponent> components;
	
	public GuiScreen() {
		components = new ArrayList<GuiComponent>();
	}
	public void drawComponents(Graphics2D g2d) {
		GuiComponent[] comps = getComponents();
		for(GuiComponent c : comps) {
			c.draw(g2d);
		}
	}
	public abstract void draw(Graphics2D g2d);
	
	public synchronized GuiComponent[] getComponents() {
		return (GuiComponent[]) components.toArray();
	}
}