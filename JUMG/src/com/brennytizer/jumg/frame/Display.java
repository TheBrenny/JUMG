package com.brennytizer.jumg.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;

/**
 * The display is what is used to draw the screen. It can be extended for
 * maximum efficiency for drawing rather than going through an implemented
 * {@link Renderer#draw(Graphics2D)} method.
 * 
 * @author jarod
 */
public class Display extends JPanel {
	private static final long serialVersionUID = 1L;
	public BufferedImage splashImage;
	public Renderer renderer;
	public Color backgroundColor;
	
	/**
	 * Constructs a screen to be used based off the Size to use, the Renderer to
	 * draw with, and the splash image to show unless null.
	 * 
	 * @param size
	 *        - The size of the screen.
	 * @param renderer
	 *        - The {@link Renderer} to use.
	 * @param splash
	 *        - The splash image to use.
	 */
	public Display(Dimension size, Renderer renderer, BufferedImage splash) {
		Logging.log(LoggingSpice.MILD, "Creating display.");
		this.splashImage = splash;
		this.renderer = renderer;
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		Logging.log(LoggingSpice.MILD, "Display created.");
	}
	
	/**
	 * Sets the splash image to display. Use {@code null} as the parameter to
	 * hid the splash image and show what the {@link Renderer} wants to draw.
	 * 
	 * @param splash
	 *        - The splash image to use.
	 */
	public void setSplashImage(BufferedImage splash) {
		Logging.log(LoggingSpice.MILD, splash == null ? "Removing splash image." : "Setting a splash image.");
		this.splashImage = splash;
	}
	
	/**
	 * Overrides the {@link JPanel#paint(Graphics)} method to ensure that if
	 * {@link JPanel#repaint()} is called (such as from an engine (hint,
	 * hint...)) then the display will be redrawn through this method. <br>
	 * <br>
	 * If the splashImage is not null it will draw. Thus, to ensure that the
	 * display can draw the renderer, the splashImage must be {@code null}.
	 */
	public void paint(Graphics g) {
		g.setColor(this.backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		Graphics2D g2d = (Graphics2D) g;
		if(splashImage != null) drawSplash(g2d);
		else draw(g2d);
		g2d.dispose();
	}
	
	/**
	 * Draws the splash image using the Graphics2D object provided. This draws
	 * when the splash image is not null or if the renderer is null.
	 * 
	 * @param g2d
	 *        - The graphics to draw width.
	 */
	public void drawSplash(Graphics2D g2d) {
		g2d.drawImage(splashImage, (getWidth() - splashImage.getWidth()) / 2, (getHeight() - splashImage.getHeight()) / 2, null);
	}
	
	/**
	 * Draws the screen by using the {@link Renderer}. If the renderer is
	 * {@code null} then the splash screen will be drawn. <br>
	 * <br>
	 * Override this method for better efficiency.
	 * 
	 * @param g2d
	 *        - The graphics to draw with.
	 */
	public void draw(Graphics2D g2d) {
		if(renderer != null) renderer.draw(g2d);
		else if(splashImage != null) drawSplash(g2d);
	}
	
	/**
	 * Sets the background color to be used.
	 * 
	 * @param color
	 *        - The color to use.
	 */
	public void setBackground(Color color) {
		super.setBackground(color);
		Logging.log(LoggingSpice.MILD, "Setting background color to: " + Integer.toHexString(color.getRGB()));
		this.backgroundColor = color;
	}
	
	/**
	 * Sets the renderer that will be called.
	 * 
	 * @param renderer
	 *        - The main renderer.
	 */
	public void setRenderer(Renderer renderer) {
		Logging.log(LoggingSpice.MILD, "Attaching renderer.");
		this.renderer = renderer;
	}
}