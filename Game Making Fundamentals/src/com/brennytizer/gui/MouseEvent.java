package com.brennytizer.gui;

import java.awt.Point;

public class MouseEvent {
	public Point location;
	public int mouseButton;
	
	public MouseEvent(Point location, int mouseButton) {
		this.location = location;
		this.mouseButton = mouseButton;
	}
	
}