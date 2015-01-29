package com.brennytizer.jumg.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.brennytizer.jumg.utils.Images;
import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Sprite;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;

public class GuiButton extends GuiComponent {
	public static final Runnable DEFAULT_ACTION = new Runnable() {
		public void run() {
			Logging.log(LoggingSpice.MILD, "Button action triggered!");
		}
	};
	public static final Sprite[][][] BUTTON_SPRITES;
	public Sprite image = Images.getSprite(Images.GUI_BUTTON, 0, 0, 30, 30);
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
		if(this.boundingBox.contains(e.location)) state = 1;
		else state = 0;
	}
	
	public void onMouseButton(MouseEvent e) {
		if(this.boundingBox.contains(e.location) && state == 2 && e.mouseButton == MouseEvent.LEFT_MOUSE_UP);
		if(state == -1) return;
		if(state == 1 && e.mouseButton == MouseEvent.LEFT_MOUSE_DOWN) state = 2;
	}
	
	public GuiButton lock(boolean flag) {
		state = flag ? -1 : 0;
		image.updateData(image.getMap(), 30 * (flag ? 2 : 0), 0);
		return this;
	}
	public GuiButton onAction(Runnable runnable) {
		this.action = runnable;
		return this;
	}
	static {
		BUTTON_SPRITES = new Sprite[3][3][3];
		for(int x = 0, y = 0, b = 0; b < 3; x++, y += x / 3, x%= 3, b += y / 3, y %= 3) {
			BUTTON_SPRITES[b][y][x] = new Sprite(Images.getSprite(Images.GUI_BUTTON, b * 30, 0, 30, 30), x * 10, y * 10, 10, 10);
		}
	}
}