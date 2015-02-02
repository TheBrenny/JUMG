package com.brennytizer.cars.engine;

import com.brennytizer.cars.Cars;
import com.brennytizer.jumg.utils.engine.Engine;

public class MyEngine extends Engine {
	public void tick() {}
	
	public void render() {
		Cars.INSTANCE.display.repaint();
	}
}