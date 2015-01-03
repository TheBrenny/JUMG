package com.brennytizer.jumg.gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * This class contains the basic variables needed to create a GuiComponent. The
 * necessary values are a location value and size value. The location and size
 * are necessary for the mouse events that will be called.
 * 
 * @author jarod
 */
public abstract class GuiComponent implements GuiComponentListener {
	public Point location;
	public Dimension size;
	
	/**
	 * Constructs a new GUI component. The parameters must be of legitimate
	 * value, such that the width and height must be greater than 1. If not, you
	 * will cause an {@link IndexOutOfBoundsException} because the parameters
	 * are illegal.
	 * 
	 * @param x
	 *        - The X position of the component.
	 * @param y
	 *        - The Y position of the component.
	 * @param width
	 *        - The width of the component.
	 * @param height
	 *        - The height of the component.
	 * @param requiresInput
	 *        - Whether this component requires mouse or key interactions.
	 */
	public GuiComponent(int x, int y, int width, int height, boolean requiresInput) {
		this(new Point(x, y), new Dimension(width, height), requiresInput);
	}
	
	public GuiComponent(Point location, Dimension size, boolean requiresInput) {
		this.location = location;
		String error = "";
		if(size.width <= 0) error += "width (" + size.width + ")";
		if(size.height <= 0) error += ":height (" + size.height + ")";
		if(!error.equals("")) {
			String[] errorTmp = error.split(":");
			String built = errorTmp.length > 1 ? "Sizes " : "Size ";
			for(int i = 0; i < errorTmp.length; i++) {
				if(i == 0) {
					built += errorTmp[i];
				} else {
					built += "and " + errorTmp[i];
				}
			}
			throw new IndexOutOfBoundsException(built + " are out of range! They must be >= 1!");
		}
		this.size = size;
		if(requiresInput) GuiComponentObservable.addListener(this);
	}
	
	public void onMouseMove(MouseEvent e) {};
	public void onMouseButton(MouseEvent e) {};
	public void onMouseScroll(MouseEvent e) {};
	public void onKeyPress(KeyEvent ke) {};
	public abstract void draw(Graphics2D g2d);
}