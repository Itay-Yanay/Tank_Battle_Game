package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * @name Tank
 * @author Max Dustin and Itay Yanay
 * 
 * Extends Polygon class, Implements KeyListener interface
 * 
 * Represents a Tank that can move around using keyboard inputs and shoot Bullet objects
 *
 */
public class Tank extends Polygon implements KeyListener {

	protected boolean forward, backward, left, right;

	private boolean allowMovement;
	
	protected boolean resetBoard;
	
	protected boolean wasHit;
	
	protected ArrayList<Bullet> bulletList;

	/**
	 * Constructor I
	 * @param points
	 * @param position
	 */
	public Tank(Point[] points, Point position) {
		super(points, new Point(position.x, position.y), 0);
		
		forward = false;
		backward = false;
		left = false;
		right = false;
		allowMovement = true;
		wasHit = false;
		bulletList = new ArrayList<Bullet>();
		resetBoard = false;
	}
	
	/**
	 * Constructor II
	 * @param points
	 * @param position
	 * @param rotation
	 */
	public Tank(Point[] points, Point position, double rotation) {
		super(points, new Point(position.x, position.y), rotation);

		forward = false;
		backward = false;
		left = false;
		right = false;
		allowMovement = true;
		wasHit = false;
		bulletList = new ArrayList<Bullet>();
	}

	/**
	 * Overrides paint method for Tank
	 * @param brush
	 */
	public void paint(Graphics brush) {

		Point[] points = getPoints();
		int[] xPoints = new int[points.length];
		int[] yPoints = new int[points.length];

		for (int i = 0; i < points.length; i++) {
			xPoints[i] = (int) points[i].x;
			yPoints[i] = (int) points[i].y;

		}

		brush.fillPolygon(xPoints, yPoints, xPoints.length);
		brush.setColor(Color.gray);
		brush.drawPolygon(xPoints, yPoints, xPoints.length);

	}

	/**
	 * From KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// leave blank
	}

	/**
	 * Creates next movement frame for Tank objects based on facing direction and input
	 */
	public void move() {
		double move = 1.5;
		if (forward == true && allowMovement) {
			position.y += Math.sin(rotation * (Math.PI / 180.0)) * move;
			position.x += Math.cos(rotation * (Math.PI / 180.0)) * move;
		}
		// allows players to get unstuck. Not ideal, but it works pretty well for now
		else if (backward == true && !allowMovement) {
			position.y -= (Math.sin(rotation * (Math.PI / 180.0)) * move)*2;
			position.x -= (Math.cos(rotation * (Math.PI / 180.0)) * move)*2;
		}

		if (left == true && allowMovement) {
			rotate(-1);
		}
		if (right == true && allowMovement) {
			rotate(1);
		}
		allowMovement = true;
	}
	
	/**
	 * Creates a Bullet object and launches it in the direction faced by the Tank who fired it
	 */
	public void fire() {
		Point[] bPoints = {new Point(0,0), new Point(0, 10), new Point(10, 10), new Point(10, 0)};
		bulletList.add(new Bullet(bPoints, new Point(position.x, position.y), rotation));
		
	}

	// make up, down, left and right booleans that control the keyTyped
	/**
	 * From KeyListener, detects the pressing of a key
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			forward = true;
			break;
		case KeyEvent.VK_DOWN:
			backward = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		}
	}

	/**
	 * From KeyListener, detects the release of a key
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			forward = false;
			break;
		case KeyEvent.VK_DOWN:
			backward = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_ENTER:
			fire();
		}

	}
	
	/**
	 * Interprets the interaction of a Tank object and a Block object or Bullet object
	 * @param other
	 */
	public void collides(Polygon other) {
		if (other instanceof Block) {
			Block blk = (Block) other;
			Point[] points = blk.getPoints();
			for (double i = points[0].x; i < points[1].x; i = i + 3) {
				if (this.contains(new Point(i, points[0].y))) {
					allowMovement = false;
				}
			}
			
			for (double i = points[1].y; i < points[2].y; i = i + 3) {
				if (this.contains(new Point(points[1].x, i))) {
					allowMovement = false;
				}
			}
			for (double i = points[3].x; i < points[2].x; i = i + 3) {
				if (this.contains(new Point(i, points[3].y))) {
					allowMovement = false;
				}
			}
			for (double i = points[0].y; i < points[3].y; i = i + 3) {
				if (this.contains(new Point(points[0].x, i))) {
					allowMovement = false;
				}
			}
		}
		
		if (other instanceof Bullet) {
			Bullet blt = (Bullet) other;
			if (this.contains(new Point(blt.position.x, blt.position.y))) {
				wasHit = true;
				resetBoard = true;
			}
		}
	}
}
