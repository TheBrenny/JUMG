package com.brennytizer.cars;

import com.brennytizer.jumg.utils.engine.Engine;

public class MyEngine extends Engine {
	public void tick() {
		Cars.INSTANCE.tick();
	}
	
	public void render() {
		Cars.INSTANCE.display.repaint();
	}
}