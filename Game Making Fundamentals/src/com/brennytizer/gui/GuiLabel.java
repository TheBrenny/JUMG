package com.brennytizer.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GuiLabel extends GuiComponent {
	public String text;
	public Font font;
	public String anchor;
	
	public GuiLabel(int x, int y, String text, Font font, String anchor) {
		super(x, y, 1, 1);
	}
	
	
	public void drawColouredString(Graphics2D g2d) {
		g2d.setFont(font);
	}
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.setFont(font);
		g2d.drawString(text, location.x, location.y);
	}
}