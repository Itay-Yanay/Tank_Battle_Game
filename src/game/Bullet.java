package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @name Polygon
 * @author Max Dustin and Itay Yanay
 * 
 * Extends Polygon class
 * 
 * Represents a Bullet that is fired from a Tank object
 *
 */
public class Bullet extends Polygon {
	
	private double move = 5;

	/**
	 * Constructor
	 * @param points
	 * @param position
	 * @param rotation
	 */
	public Bullet(Point[] points, Point position, double rotation) {
		super(points, new Point(position.x + 10 + Math.cos(rotation * (Math.PI / 180.0)) * 30,
				position.y + 10 + Math.sin(rotation * (Math.PI / 180.0)) * 30), rotation);

	}

	/**
	 * Creates next movement frame for Bullet object in a specific direction
	 */
	public void move() {
		move = 5;
		position.y += Math.sin(rotation * (Math.PI / 180.0)) * move;
		position.x += Math.cos(rotation * (Math.PI / 180.0)) * move;
	}

	/**
	 * Overrides paint method for Bullet
	 * @param brush
	 */
	public void paint(Graphics brush) {

		Point[] points = getPoints();
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];

		for (int i = 0; i < 4; i++) {
			xPoints[i] = (int) points[i].x;
			yPoints[i] = (int) points[i].y;

		}

		brush.fillPolygon(xPoints, yPoints, xPoints.length);
		brush.setColor(Color.black);
		brush.drawPolygon(xPoints, yPoints, xPoints.length);

	}
	
	/**
	 * Changes the distance a bullet is moved each frame
	 * @param move
	 */
	protected void setMove(double move) {
		this.move = move;
	}


}
