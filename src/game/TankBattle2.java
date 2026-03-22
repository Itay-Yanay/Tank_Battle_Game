package game;

/*
CLASS: TankBattle2
AUTHORS: Max Dustin and Itay Yanay
DESCRIPTION: Extending Game, TankBattle2 is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

class TankBattle2 extends Game{
	static int counter = 0;
	static int MAX_TIME = 5400;

	Tank tankPlayer1;
	Tank tankPlayer2;

	Block block1;
	Block block2;
	Block block3;
	Block block4;
	Block block5;
	Block block6;
	Block block7;
	Block block8;
	
	ScoreBoard match = new ScoreBoard();
	
	/**
	 * Lambda Expression from CheckOffMap
	 */
	CheckOffMap lambda = (tank) -> {
		if(tank.position.x > 725 || tank.position.y > 550 || tank.position.y < 0 || tank.position.x < 0) {
		tank.position.x = 400;
		tank.position.y = 400;
		}
	};

	public static boolean accessBulletWallMethod;

	// static arrayList containing all of the block objects, belongs to TankBattle2
	private static ArrayList<Block> allBlocks;

	/**
	 * Constructor
	 */
	public TankBattle2() {
		super("TankBattle2", 800, 600);
		this.setFocusable(true);
		this.requestFocus();

		// just for some sembelance of efficiency I gave the crazy nested method
		// controlled access
		accessBulletWallMethod = false;

		// for the block
		Point[] ptsBlock = { new Point(0, 0), new Point(75, 0), new Point(75, 75), new Point(0, 75) };

		allBlocks = new ArrayList<Block>();

		// makes perimeter of blocks

		// makes top row of blocks
		for (int i = 0; i < 12; i++) {
			allBlocks.add(new Block(ptsBlock, new Point(i * 75, -20)));
		}

		// makes left column of blocks
		for (int i = 0; i < 10; i++) {
			allBlocks.add(new Block(ptsBlock, new Point(-10, i * 75)));
		}

		// makes bottom row of blocks
		for (int i = 0; i < 12; i++) {
			allBlocks.add(new Block(ptsBlock, new Point(i * 75, 530)));
		}

		// makes right column of blocks
		for (int i = 0; i < 10; i++) {
			allBlocks.add(new Block(ptsBlock, new Point(760, i * 75)));
		}

		block1 = new Block(ptsBlock, new Point(200, 300));
		block2 = new Block(ptsBlock, new Point(200, 375));
		block3 = new Block(ptsBlock, new Point(275, 300));

		block4 = new Block(ptsBlock, new Point(425, 125));
		block5 = new Block(ptsBlock, new Point(575, 125));
		block6 = new Block(ptsBlock, new Point(350, 125));

		block7 = new Block(ptsBlock, new Point(550, 275));
		block8 = new Block(ptsBlock, new Point(550, 375));

		allBlocks.add(block1);
		allBlocks.add(block2);
		allBlocks.add(block3);
		allBlocks.add(block4);
		allBlocks.add(block5);
		allBlocks.add(block6);
		allBlocks.add(block7);
		allBlocks.add(block8);

		Point[] pointsPlayer1 = { new Point(0, 0), new Point(50, 0), new Point(50, 50), new Point(0, 50) };
		Point[] pointsPlayer2 = { new Point(0, 0), new Point(50, 0), new Point(50, 50), new Point(0, 50) };

		tankPlayer1 = new Tank(pointsPlayer1, new Point(150, 150)) {
			public void keyPressed(KeyEvent e) { // Anonymous Class
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					forward = true;
					break;
				case KeyEvent.VK_S:
					backward = true;
					break;
				case KeyEvent.VK_A:
					left = true;
					break;
				case KeyEvent.VK_D:
					right = true;
					break;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					forward = false;
					break;
				case KeyEvent.VK_S:
					backward = false;
					break;
				case KeyEvent.VK_A:
					left = false;
					break;
				case KeyEvent.VK_D:
					right = false;
					break;
				case KeyEvent.VK_SPACE:
					fire();
				}

			}

		};

		tankPlayer2 = new Tank(pointsPlayer2, new Point(400, 400));

		this.addKeyListener(tankPlayer1);
		this.addKeyListener(tankPlayer2);

	}

	/**
	 * Creates graphics for moving parts
	 */
	@Override
	public void paint(Graphics brush) {
		Color background = new Color(210, 180, 130);
		brush.setColor(background);
		brush.fillRect(0, 0, width, height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.gray);

		// loop through all game objects
		for (Block block : allBlocks) {
			tankPlayer1.collides(block);
			tankPlayer2.collides(block);
		}

		for (Bullet bullet : tankPlayer1.bulletList) {
			// tank collision with bullet
			tankPlayer2.collides(bullet);
			// wall collision with bullet
			for (Block block : allBlocks) {
				block.collides(bullet);
			}
		}

		for (Bullet bullet : tankPlayer2.bulletList) {
			// tank collision with bullet
			tankPlayer1.collides(bullet);
			// wall collision with bullet
			for (Block block : allBlocks) {
				block.collides(bullet);
			}
		}

		removeBulletHitWall();

		resetBoard();
		
		//Accounts for if a tank was by a bullet.
		if(tankPlayer1.wasHit == true) {
			match.takeDamage(1);
			tankPlayer1.wasHit = false;
		}
		if(tankPlayer2.wasHit == true) {
			match.takeDamage(2);
			tankPlayer2.wasHit = false;
		}
		
		lambda.goToSpawnCheck(tankPlayer1);
		lambda.goToSpawnCheck(tankPlayer2);

		tankPlayer1.move();
		tankPlayer2.move();

		for (Bullet bullet : tankPlayer1.bulletList) {
			bullet.move();
		}
		for (Bullet bullet : tankPlayer2.bulletList) {
			bullet.move();
		}
		
		
		// where should the KeyPressed and KeyReleased be called?
		brush.setColor(Color.blue);
		tankPlayer1.paint(brush);
		brush.setColor(Color.red);
		tankPlayer2.paint(brush);
		for (Block block : allBlocks) {
			block.paint(brush);
		}

		brush.setColor(Color.black);
		for (Bullet bullet : tankPlayer1.bulletList) {
			bullet.paint(brush);
		}
		for (Bullet bullet : tankPlayer2.bulletList) {
			bullet.paint(brush);
		}

		// Displays time and remaining lives
		brush.drawString("Time Remaining:  " + (int)(MAX_TIME - counter) / 60, 325, 20);
		brush.drawString("Player 1 Lives Remaining:  " + match.getPlayerLives(1), 50, 20);
		brush.drawString("Player 2 Lives Remaining:  " + match.getPlayerLives(2), 550, 20);
		
		if(match.getWinner() != 0) {
			EndMessage gameOverLives;
			
			if(match.getWinner() == 3) { //In the case both players run out of lives at the same time
				gameOverLives = new EndMessage(match.getWinner(), "Both Players Ran Out of Lives");
			}
			else {
				gameOverLives = new EndMessage(match.getWinner(), "Other Player Ran Out of Lives");
			}
			
			gameOverLives.endGame();
		}
		
		if(counter >= MAX_TIME) { //If the timer runs out before any one player runs out of lives
			EndMessage gameOverTime = new EndMessage(-1, "Ran Out of Time");
			gameOverTime.endGame();
		}
	}

	/**
	 * Gets ArrayList of Block objects
	 * @return allBlocks
	 */
	public static ArrayList<Block> getAllBlocks() {
		return allBlocks;
	}

	/**
	 * Resets the board by resetting Tank object locations and removing all Bullet objects from screen
	 */
	public void resetBoard() {
		if (tankPlayer1.resetBoard || tankPlayer2.resetBoard) {
			for (int i = tankPlayer1.bulletList.size() - 1; i >= 0; i--) {
				
				//Removing a Bullet involves moving it off screen and stopping it from moving
				tankPlayer1.bulletList.get(i).setMove(0);
				tankPlayer1.bulletList.get(i).position.x = 1000;
			}

			for (int j = tankPlayer2.bulletList.size() - 1; j >= 0; j--) {
				tankPlayer2.bulletList.get(j).setMove(0);
				tankPlayer2.bulletList.get(j).position.x = 1000;
			}

			tankPlayer1.position.setX(150);
			tankPlayer1.position.setY(150);

			tankPlayer2.position.setX(400);
			tankPlayer2.position.setY(400);
		}
		tankPlayer1.resetBoard = false;
		tankPlayer2.resetBoard = false;
	}

	/**
	 * Removes from view any Bullet objects that collide with a Block object
	 */
	public void removeBulletHitWall() {
		if (accessBulletWallMethod) {
			for (int i = tankPlayer1.bulletList.size() - 1; i >= 0; i--) {
				for (Block blk : allBlocks) {
					if (tankPlayer1.bulletList.get(i) == blk.bulletHit) {
						tankPlayer1.bulletList.get(i).setMove(0);
						tankPlayer1.bulletList.get(i).position.x = 1000;
					}
				}
			}
			for (int j = tankPlayer2.bulletList.size() - 1; j >= 0 ; j--) {
				for (Block blk : allBlocks) {
					if (tankPlayer2.bulletList.get(j) == blk.bulletHit) {
						tankPlayer2.bulletList.get(j).setMove(0);
						tankPlayer2.bulletList.get(j).position.x = 1000;
					}
				}
			}
		}

	}

	/*
	 * Keeps track of scores throughout the game
	 */
	class ScoreBoard {

		private int player1Lives, player2Lives;

		private static int START_LIVES = 3;

		/**
		 * Constructor
		 */
		public ScoreBoard() {
			player1Lives = START_LIVES;
			player2Lives = START_LIVES;
		}

		/**
		 * Gets the numbers of remaining lives of the specified player
		 * 
		 * @param player
		 * @return lives
		 */
		public int getPlayerLives(int player) {
			if (player == 1) {
				return player1Lives;
			} else if (player == 2) {
				return player2Lives;
			} else {
				return -1;
			}
		}

		/**
		 * Decreases the specified player's number of lives by 1
		 * 
		 * @param lives
		 */
		public void takeDamage(int player) {
			if (player == 1) {
				player1Lives--;
			} else if (player == 2) {
				player2Lives--;
			}
		}

		/**
		 * Returns the player number of the winner
		 * 
		 * 0 = Nobody has won
		 * 1 = Player 1 wins
		 * 2 = Player 2 wins
		 * 3 = Tie from both losing all lives
		 * 
		 * @return player
		 */
		public int getWinner() {
			if(player1Lives <= 0 && player2Lives <= 0) {
				return 3;
			}
			else if (player2Lives <= 0) {
				return 1;
			} else if (player1Lives <= 0) {
				return 2;
			} else {
				return 0;
			}

		}
	}
	
	/**
	 * Displays message at end of the game
	 */
	class EndMessage{
		
		private int winner;
		private String reason;
		
		/**
		 * Constructor
		 * @param winner
		 * @param reason
		 */
		public EndMessage(int winner, String reason) {
			this.winner = winner;
			this.reason = reason;
		}
		
		/**
		 * Ends and shuts down the game
		 */
		public void endGame() {
			String message = "";
			
			//-1 implies loss due to running out of time
			if(winner == -1 || winner == 3) {
				message += "Tie, Nobody Wins\n" + reason;
			}
			else {
				message += "Winner: Player " + winner + "\n" + reason;
			}
			
			message += "\nGame Over";
			JOptionPane.showMessageDialog(TankBattle2.this, message);
			System.exit(0);
		}
	}

	/**
	 * Creates and starts the game
	 * @param args
	 */
	public static void main(String[] args) {
		TankBattle2 a = new TankBattle2();
		a.repaint();
	}

}