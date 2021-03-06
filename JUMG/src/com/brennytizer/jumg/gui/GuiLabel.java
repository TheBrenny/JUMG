package com.brennytizer.jumg.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * A GuiLabel is essentially, just a label of text that is drawn.
 * 
 * @author Jarod Brennfleck
 */
public class GuiLabel extends GuiComponent {
	public String text;
	public Font font;
	public FontMetrics fontMetrics;
	/**
	 * The anchor location to use. The anchor is defined by
	 * <code>"x-anchor:y-anchor"</code><br>
	 * <br>
	 * Valid anchor types:
	 * <p>
	 * <table>
	 * <tr>
	 * <th>X Anchors</th>
	 * <th>Y Anchors</th>
	 * </tr>
	 * <tr>
	 * <td>left</td>
	 * <td>top</td>
	 * </tr>
	 * <tr>
	 * <td>center</td>
	 * <td>center</td>
	 * </tr>
	 * <tr>
	 * <td>right</td>
	 * <td>bottom</td>
	 * </tr>
	 * </table>
	 * </p>
	 * Where you can mix and match between the three anchors for each type. But
	 * make sure you use the correct word! Here are some examples:
	 * 
	 * <pre>
	 * "left:bottom"
	 * "center:top"
	 * "center:center"
	 * </pre>
	 */
	public String[] anchor = {"left", "top"};
	public Color color = Color.BLACK;
	
	/**
	 * Constructs a new label at the x, y and anchor using the font given.
	 * 
	 * @param x
	 *        - The x position of the anchor.
	 * @param y
	 *        - The y position of the anchor.
	 * @param text
	 *        - The text to be displayed.
	 * @param font
	 *        - The font for the text to be displayed in.
	 * @param anchor
	 *        - The {@link #anchor} position.
	 */
	public GuiLabel(int x, int y, String text, Font font, String anchor) {
		super(x, y, 1, 1, false);
		this.text = text;
		this.font = font;
		if(anchor.contains(":")) this.anchor = anchor.split(":");
	}
	
	/**
	 * Draws the set text using the Graphics2D object and the color passed
	 * through.
	 * 
	 * @param g2d
	 *        - The Graphics2D object to draw with.
	 * @param color
	 *        - The color to draw with.
	 */
	public void drawColoredString(Graphics2D g2d, Color color) {
		if(font == null) return;
		if(fontMetrics == null || fontMetrics.getFont() != font) fontMetrics = g2d.getFontMetrics(font);
		this.boundingBox.resize(fontMetrics.stringWidth(text), fontMetrics.getHeight());
		g2d.setFont(font);
		g2d.setColor(color);
		g2d.drawString(text, getX(), getY());
	}
	
	/**
	 * Returns the x position, taking into account of where the anchor is positioned.
	 */
	public float getX() {
		float xOff = anchor[0].equalsIgnoreCase("right") ? fontMetrics.stringWidth(text) : anchor[0].equalsIgnoreCase("center") ? fontMetrics.stringWidth(text) / 2 : 0;
		return boundingBox.x - xOff;
	}
	
	/**
	 * Returns the y position, taking into account of where the anchor is positioned.
	 */
	public float getY() {
		float yOff = anchor[1].equalsIgnoreCase("top") ? fontMetrics.getHeight() : anchor[1].equalsIgnoreCase("center") ? fontMetrics.getHeight() / 2 : 0;
		return boundingBox.y + yOff;
	}
	
	/**
	 * Draws the set text using the Graphics2D object and a default color of
	 * white.
	 * 
	 * @param g2d
	 *        - The Graphics2D object to draw with.
	 */
	public void draw(Graphics2D g2d) {
		drawColoredString(g2d, color);
	}
	
	/**
	 * Returns the text currently being displayed.
	 * 
	 * @return {@link String} - The text being displayed.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Returns the font currently in use.
	 * 
	 * @return {@link Font} - The font in use.
	 */
	public Font getFont() {
		return this.font;
	}
	
	/**
	 * Returns the font metrics of the font in use - if not, it will be updated
	 * next frame render.
	 * 
	 * @return {@link FontMetrics} - The font metrics in use.
	 */
	public FontMetrics getFontMetrics() {
		return this.fontMetrics;
	}
	
	/**
	 * Returns the anchor positioning in the form of
	 * <code>"x-anchor:y-anchor"</code>.
	 * 
	 * @return {@link String} - The anchor in use.
	 * @see #anchor
	 */
	public String getAnchor() {
		return this.anchor[0] + ":" + this.anchor[1];
	}
	
	/**
	 * Returns the default color used to draw this label.
	 * 
	 * @return {@link String} - The color used.
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Sets the text to be displayed.
	 * 
	 * @param text
	 *        - The text to be drawn.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Sets the font to use on the text.
	 * 
	 * @param font
	 *        - The font to use.
	 */
	public void setFont(Font font) {
		this.font = font;
	}
	
	/**
	 * Sets the anchor positioning for the text.
	 * 
	 * @param anchor
	 *        - The anchor positioning.
	 * @see #anchor
	 */
	public void setAnchor(String anchor) {
		if(anchor.contains(":")) this.anchor = anchor.split(":");
	}
	
	/**
	 * Sets the default color to use when drawing this label.
	 * 
	 * @param color
	 *        - The color to use.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}