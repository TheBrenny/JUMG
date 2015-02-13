package com.brennytizer.jumg.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.brennytizer.jumg.gui.event.MouseEvent;
import com.brennytizer.jumg.utils.Images;
import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;
import com.brennytizer.jumg.utils.Sprite;

public class GuiButton extends GuiComponent {
	public static final Runnable DEFAULT_ACTION = new Runnable() {
		public void run() {
			Logging.log(LoggingSpice.MILD, "Button action triggered!");
		}
	};
	public static final Sprite[][][] BUTTON_SPRITES;
	public GuiLabel text;
	public int state = 0;
	public Runnable action = DEFAULT_ACTION;
	
	public GuiButton(int x, int y, int width, int height, String text) {
		super(x, y, width = (width < 20 ? 20 : width), height = (height < 20 ? 20 : height), true);
		this.text = new GuiLabel(x + width / 2, y + height / 2, text, new Font("Arial", Font.BOLD, 16), "center:center");
	}
	
	public void draw(Graphics2D g2d) {
		Color textColor = state == -1 ? new Color(0xFF444444) : Color.WHITE;
		Sprite[][] button = BUTTON_SPRITES[state == -1 ? 2 : state];
		int widthSpacer = (int) (this.boundingBox.width - 20);
		int heightSpacer = (int) (this.boundingBox.height - 20);
		for(int x = 0, y = 0; y < 3; x++, y += x / 3, x %= 3) {
			int w = 10;
			int h = 10;
			if(x == 1) w = widthSpacer;
			if(y == 1) h = heightSpacer;
			int shiftX = x * 10 + (x == 2 ? widthSpacer - 10 : 0);
			int shiftY = y * 10 + (y == 2 ? heightSpacer - 10 : 0);
			g2d.drawImage(button[y][x], (int) (this.boundingBox.x + shiftX), (int) (this.boundingBox.y + shiftY), w, h, null);
		}
		this.text.drawColoredString(g2d, textColor);
	}
	
	public void onMouseMove(MouseEvent e) {
		if(state == -1) return;
		if(this.boundingBox.contains(e.location)){
			if(state != 2) state = 1;
		}
		else state = 0;
	}
	
	public void onMouseButton(MouseEvent e) {
		if(this.boundingBox.contains(e.location) && state == 2 && e.mouseButton == MouseEvent.LEFT_MOUSE_UP) {
			action.run();
			state = 1;
		}
		if(state == -1) return;
		if(state == 1 && e.mouseButton == MouseEvent.LEFT_MOUSE_DOWN) state = 2;
	}
	
	public GuiButton lock(boolean flag) {
		state = flag ? -1 : 0;
		return this;
	}
	public GuiButton onAction(Runnable runnable) {
		this.action = runnable;
		return this;
	}
	static {
		Logging.log(LoggingSpice.MILD, "Loading button sprites.");
		BUTTON_SPRITES = new Sprite[3][3][3];
		String ip = Images.IMAGE_PACKAGE;
		Images.IMAGE_PACKAGE = "/com/brennytizer/jumg/gui/images/";
		BufferedImage buttonImage = Images.getImage("gui_button");
		for(int x = 0, y = 0, b = 0; b < 3; x++, y += x / 3, x%= 3, b += y / 3, y %= 3) {
			BUTTON_SPRITES[b][y][x] = new Sprite(Images.getSprite(buttonImage, b * 30, 0, 30, 30), x * 10, y * 10, 10, 10);
		}
		Images.IMAGE_PACKAGE = ip;
	}
}