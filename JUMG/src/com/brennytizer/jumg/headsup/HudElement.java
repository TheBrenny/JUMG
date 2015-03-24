package com.brennytizer.jumg.headsup;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class HudElement {
	public abstract BufferedImage render();
	
	public abstract void update();
	
	public abstract HashMap<String, String> relationalLocation();
}