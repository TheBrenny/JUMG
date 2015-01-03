package com.brennytizer.cars;

import java.awt.Color;
import java.awt.Dimension;

import com.brennytizer.cars.util.MyImages;
import com.brennytizer.jumg.frame.Display;
import com.brennytizer.jumg.frame.Frame;
import com.brennytizer.jumg.frame.Renderer;

public class Cars {
	public static final String TITLE = "Cars";
	public static final int WIDTH = 600;
	public static final int HEIGHT = 550;
	public static Cars INSTANCE;
	
	public Frame frame;
	public Display display;
	public Renderer renderer;
	
	public Cars() {
		if(Cars.INSTANCE == null) Cars.INSTANCE = this;
		Dimension size = new Dimension(WIDTH, HEIGHT);
		frame = new Frame(Cars.TITLE, size);
		renderer = new MyRenderer();
		display = new Display(size, renderer, MyImages.SPLASH_IMAGE);
		display.setBackground(Color.GREEN.darker().darker());
		frame.addDisplay(display);
		frame.show(true);
		display.setSplashImage(null);
	}
	
	public static void main(String[] args) {
		new Cars();
	}
}