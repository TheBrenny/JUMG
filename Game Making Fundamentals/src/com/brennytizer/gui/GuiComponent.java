package com.brennytizer.gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class GuiComponent {
	public Point location;
	public Dimension size;
	
	public GuiComponent(int x, int y, int width, int height) {
		this(new Point(x, y), new Dimension(width, height));
	}
	public GuiComponent(Point location, Dimension size) {
		this.location = location;
		this.size = size;
	}
	
	public void onMouseMove(MouseEvent e) {};
	public void onMouseButton(MouseEvent e) {};
	
	public abstract void draw(Graphics2D g2d);
}