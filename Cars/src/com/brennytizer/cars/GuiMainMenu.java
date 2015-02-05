package com.brennytizer.cars;

import java.awt.Font;
import java.awt.Graphics2D;

import com.brennytizer.cars.levels.MapsManager;
import com.brennytizer.jumg.gui.GuiButton;
import com.brennytizer.jumg.gui.GuiLabel;
import com.brennytizer.jumg.gui.GuiScreen;

public class GuiMainMenu extends GuiScreen {
	public GuiLabel title;
	
	public GuiMainMenu() {
		addComponent(title = new GuiLabel(Cars.WIDTH / 2, 25, "Cars!", new Font("Arial", Font.BOLD, 30), "center:top"));
		addComponent(new GuiButton(Cars.WIDTH / 2 - 100, 200, 200, 75, "Start Game").onAction(new Runnable() {
			public void run() {
				((MyRenderer) Cars.INSTANCE.renderer).screen = new MapsManager("grasslands");
			}
		}));
	}
	
	public void draw(Graphics2D g2d) {
		drawComponents(g2d);
	}
}