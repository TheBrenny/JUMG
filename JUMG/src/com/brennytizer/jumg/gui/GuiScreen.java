package com.brennytizer.jumg.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class GuiScreen {
	public ArrayList<GuiComponent> components;
	
	public GuiScreen() {
		components = new ArrayList<GuiComponent>();
	}
	
	public void addComponent(GuiComponent guiC) {
		components.add(guiC);
	}
	
	public void drawComponents(Graphics2D g2d) {
		GuiComponent[] comps = getComponents();
		for(GuiComponent c : comps) {
			c.draw(g2d);
		}
	}
	public abstract void draw(Graphics2D g2d);
	
	public synchronized GuiComponent[] getComponents() {
		synchronized(components) {
			GuiComponent[] guiCs = new GuiComponent[components.size()];
			for(int i = 0; i < components.size(); i++) {
				guiCs[i] = components.get(i);
			}
			return guiCs;
		}
	}
}