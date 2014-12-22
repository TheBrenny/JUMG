package com.brennytizer.cars;

import java.awt.Color;
import java.awt.Graphics2D;

import com.brennytizer.frame.Renderer;

public class MyRenderer implements Renderer {
	
	public MyRenderer() {
		
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.fillRect(10, 10, 10, 10);
	}
}