package com.brennytizer.cars;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import com.brennytizer.cars.util.MyImages;
import com.brennytizer.jumg.frame.Display;
import com.brennytizer.jumg.frame.Frame;
import com.brennytizer.jumg.gui.GuiComponentObservable;
import com.brennytizer.jumg.keybinds.Key;
import com.brennytizer.jumg.keybinds.KeyBindings;
import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.engine.Engine;

public class Cars {
	public static final String TITLE = "Cars";
	public static final int WIDTH = 600;
	public static final int HEIGHT = 550;
	public static Cars INSTANCE;
	
	public Frame frame;
	public Display display;
	public MyRenderer renderer;
	public Engine engine;
	
	public Cars() {
		if(Cars.INSTANCE == null) Cars.INSTANCE = this;
		Logging.setLogging(true);
		Dimension size = new Dimension(WIDTH, HEIGHT);
		frame = new Frame(Cars.TITLE, size);
		frame.addListener(new GuiComponentObservable());
		engine = new MyEngine();
		renderer = new MyRenderer();
		display = new Display(size, renderer, MyImages.SPLASH_IMAGE);
		display.setBackground(Color.GREEN.darker().darker());
		frame.addDisplay(display);
		frame.show(true);
		display.setSplashImage(null);
		engine.start();
		addInputs();
	}
	public void addInputs() {
		frame.addListener(new KeyBindings());
		KeyBindings.addInput(new Key("UP", KeyEvent.VK_W, false));
		KeyBindings.addInput(new Key("DOWN", KeyEvent.VK_S, false));
		KeyBindings.addInput(new Key("LEFT", KeyEvent.VK_A, false));
		KeyBindings.addInput(new Key("RIGHT", KeyEvent.VK_D, false));
	}
	
	public void tick() {
		renderer.tick();
	}
	
	public static void main(String[] args) {
		new Cars();
	}
}