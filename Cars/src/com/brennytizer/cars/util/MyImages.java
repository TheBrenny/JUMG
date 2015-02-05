package com.brennytizer.cars.util;

import java.awt.image.BufferedImage;

import com.brennytizer.jumg.utils.Images;

public class MyImages extends Images {
	public static BufferedImage SPLASH_IMAGE;
	public static BufferedImage TILE_MAP;
	static {
		IMAGE_PACKAGE = "/com/brennytizer/cars/res/images/";
		SPLASH_IMAGE = getImage("splash_image");
		TILE_MAP = getImage("tile_map");
	}
}