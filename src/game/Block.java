package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @name Block
 * @author Max Dustin and Itay Yanay
 * 
 *  Extends Polygon class
 * 
 * Represents a Block object that acts as the impassable walls in the game.
 *
 */
public class Block extends Polygon {

	protected Bullet bulletHit;
	
	/**
	 * Constructor
	 * @param inShape
	 * @param setPosition
	 */
	public Block(Point[] inShape, Point setPosition) {
		super(inShape, setPosition, 0);
	}

	/**
	 * Overrides paint method for Block objects
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

		brush.setColor(Color.white);
		brush.fillPolygon(xPoints, yPoints, xPoints.length);
		brush.drawPolygon(xPoints, yPoints, xPoints.length);

	}
	
	/**
	 * Detects collision from a Bullet into a Block
	 * @param other
	 */
	public void collides(Polygon other) {
		if(other instanceof Bullet) {
			Bullet blt = (Bullet) other;
			if (this.contains(new Point(blt.position.x, blt.position.y))) {
				bulletHit = blt;
				TankBattle2.accessBulletWallMethod = true;
			}
		}
	}

}
