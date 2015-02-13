package com.brennytizer.jumg.utils.geom;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * This class is a simplified way to handle angles. This class allows for easier
 * access to creating, adjusting, messing with angles. This class also allows
 * for the creation of rotated images.
 * 
 * @author Jarod Brennfleck
 */
public class Angle {
	public float angle;
	
	/**
	 * Creates an angle object using the angle provided.
	 * 
	 * @param ang
	 *        - The angle to make.
	 */
	public Angle(float ang) {
		while(ang >= 360)
			ang -= 360.0F;
		while(ang < 0)
			ang += 360.0F;
		angle = ang;
	}
	
	/**
	 * Creates an angle by using a previously made angle (essentially
	 * duplication).
	 * 
	 * @param angle
	 *        - The angle to duplicate.
	 */
	public Angle(Angle angle) {
		this(angle.angle);
	}
	
	/**
	 * Changes the angle by adding on the amount passed to the current angle.
	 * The angle will always be in the bounds of 0 and 360.
	 * 
	 * @param amount
	 *        - The amount to add to the angle.
	 */
	public void changeAngle(float amount) {
		angle += amount;
		while(angle < 0)
			angle += 360.0F;
		angle %= 360.0F;
	}
	
	/**
	 * Sets the angle to the specified angle. This completely overwrites the
	 * previously used angle.
	 * 
	 * @param angle
	 *        - The new angle.
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	/**
	 * Gets the {@link AngleSpeed} of which an object should be passing a plane
	 * at.
	 * 
	 * @param speed
	 *        - The speed the object moves at.
	 * @return The AngleSpeed of the object.
	 */
	public AngleSpeed getAngleSpeed(float speed) {
		float xSpeed = 0.0F;
		float ySpeed = 0.0F;
		double sin = Math.sin(Math.toRadians(angle));
		double cos = Math.cos(Math.toRadians(angle));
		xSpeed = (float) sin * speed;
		ySpeed = (float) cos * speed;
		return new AngleSpeed(xSpeed, ySpeed, angle);
	}
	
	/**
	 * Returns the rotated version of an image passed.
	 * 
	 * @param bi
	 *        - The image to rotate.
	 * @see #getRotation(BufferedImage, double)
	 */
	public BufferedImage getRotation(BufferedImage bi) {
		return Angle.getRotation(bi, (double) angle);
	}
	
	/**
	 * Returns the rotated version of an image passed by using an image and the
	 * angle to rotate with.
	 * 
	 * @param bi
	 *        - The image to rotate.
	 * @param angle
	 *        - The angle to rotate by.
	 */
	public static BufferedImage getRotation(BufferedImage bi, double angle) {
		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle), bi.getWidth() / 2, bi.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(bi, null);
	}
	
	/**
	 * Returns the angle between two points.
	 * 
	 * @param source
	 *        - The starting point.
	 * @param destination
	 *        - The ending point.
	 */
	public static float getAngle(Point2D source, Point2D destination) {
		return (float) Math.toDegrees(Math.atan2(destination.y - source.y, destination.x - source.x)) + 90.0F;
	}
	
	/**
	 * Checks if the passed object is equal to this object.
	 * 
	 * @param o
	 *        - The object to test.
	 */
	public boolean equals(Object o) {
		boolean ret = false;
		if(o instanceof Angle) {
			if(((Angle) o).angle == angle) ret = true;
		}
		return ret;
	}
	
	/**
	 * This class gives values as to how fast along the x and y axis an object should be moving.
	 * @author Jarod Brennfleck
	 */
	public class AngleSpeed {
		public float xSpeed;
		public float ySpeed;
		public float angle;
		
		/**
		 * Constructs an AngleSpeed object by using pre-computed values.
		 * @param xSpeed - The speed along the x axis.
		 * @param ySpeed - The speed along the y axis.
		 * @param angle - The directing angle.
		 */
		public AngleSpeed(float xSpeed, float ySpeed, float angle) {
			this.xSpeed = xSpeed;
			this.ySpeed = ySpeed;
			this.angle = angle;
		}
		
		/**
		 * Returns the x speed associated with this Angle Speed object.
		 */
		public float xSpeed() {
			return xSpeed;
		}
		
		/**
		 * Returns the y speed associated with this Angle Speed object.
		 */
		public float ySpeed() {
			return ySpeed;
		}
	}
}