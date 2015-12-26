package components;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class BrickBreakerPanel extends JPanel implements KeyListener {

	private ArrayList<Block> blocks = new ArrayList<Block>();
	private ArrayList<Block> balls = new ArrayList<Block>();
	private ArrayList<Block> powerups = new ArrayList<Block>();
	private Block paddle;
	private Thread thread;
	private Animate animate;
	private int numBricks = Constants.NUM_BRICKS;
	private int numBalls = 1;
	private boolean lost = false;
	private boolean finished = false;

	public BrickBreakerPanel() {
		createPaddle();
		createBall();
		createBricks();
		addKeyListener(this);
		setFocusable(true);
	}

	private void createPaddle() {
		int paddleX = (Constants.APPLICATION_WIDTH - Constants.PADDLE_WIDTH)/2;
		int paddleY = Constants.APPLICATION_HEIGHT - Constants.PADDLE_HEIGHT - Constants.PADDLE_Y_OFFSET;
		this.paddle = new Block(paddleX, paddleY, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE);
	}

	private void createBall() {
		int ballX = (Constants.APPLICATION_WIDTH - Constants.BALL_WIDTH)/2;
		int ballY = paddle.y - Constants.PADDLE_HEIGHT;
		Block ballBlock = new Block(ballX, ballY, Constants.BALL_WIDTH, Constants.BALL_HEIGHT, Constants.BALL);
		this.balls.add(ballBlock);
	}

	private void createBricks() {
		for (int i = 0;  i < Constants.NUM_COLS; i++) {
			for (int j = 0; j < Constants.NUM_ROWS; j++) {
				blocks.add(new Block(i * Constants.BRICK_WIDTH + Constants.BRICK_SPACING, 
						j * Constants.BRICK_HEIGHT + Constants.BRICK_SPACING, 
						Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT, getColor(j)));
			}
		}
		Random random = new Random();
		for (int i = 0; i < Constants.NUM_POWERUPS; i++) {
			Block block = blocks.get(random.nextInt(blocks.size()));
			if (block.containsPowerup) i--;
			else block.containsPowerup = true;
		}

	}

	private String getColor(int row) {
		switch(row % Constants.NUM_COLORS) {
		case 0:
			return Constants.RED_BRICK;
		case 1:
			return Constants.GEREN_BRICK;
		case 2:
			return Constants.BLUE_BRICK;
		case 3:
			return Constants.YELLOW_BRICK;
		default:
			return Constants.BLUE_BRICK;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Block block : this.blocks)
			block.draw(g, this);

		for (Block ball : this.balls)
			ball.draw(g, this);

		for (Block powerup : this.powerups)
			powerup.draw(g, this);

		this.paddle.draw(g, this);

		if (finished) displayResult(g);
	}

	public void update() {
		runPowerupHandler();
		runBallHandler();
		if (this.numBricks == 0) finished = true;
		repaint();
	}

	private void runPowerupHandler() {
		for (Block powerup : this.powerups) {
			powerup.y++;
			if (powerup.intersects(this.paddle) && !powerup.isDestroyed) {
				powerup.isDestroyed = true;
				addBall();
			}
		}
	}

	private void addBall() {
		int ballX = (paddle.x + paddle.width) / 2;
		int ballY = paddle.y - Constants.PADDLE_HEIGHT;
		Block newBall = new Block(ballX, ballY, Constants.BALL_WIDTH, Constants.BALL_HEIGHT, Constants.BALL);
		newBall.dy *= -1;
		this.balls.add(newBall);
		this.numBalls++;
	}

	private void runBallHandler() {
		for (Block ball : this.balls) {
			moveBall(ball);
			checkBlockCollisions(ball);
		}
	}

	private void moveBall(Block ball) {
		if (ball.y >= Constants.APPLICATION_HEIGHT && !ball.isDestroyed) {
			this.numBalls--;
			ball.isDestroyed = true;
			if (this.numBalls == 0) {
				this.lost = true;
				this.finished = true;
				return;
			}
		}

		if (ball.x <= 0 || ball.x + Constants.BALL_WIDTH >= Constants.APPLICATION_WIDTH)
			ball.dx *= -1;

		if (ball.y < 0 || ball.intersects(this.paddle))
			ball.dy *= -1;

		ball.x += ball.dx;
		ball.y += ball.dy;
	}

	private void checkBlockCollisions(Block ball) {
		for (Block block : this.blocks) {
			if ((ball.left.intersects(block) || ball.right.intersects(block)) && !block.isDestroyed) {
				ball.dx *= -1;
				destroyBlock(block);
			} else if (ball.intersects(block) && !block.isDestroyed) {
				destroyBlock(block);
				ball.dy *= -1;
			}
		}
	}

	private void destroyBlock(Block block) {
		block.isDestroyed = true;
		this.numBricks--;
		System.out.println("Number of bricks: " + numBricks);
		if (block.containsPowerup)
			createPowerup(block);
	}

	private void createPowerup(Block block) {
		int powerupX = block.x + (block.width - Constants.POWERUP_WIDTH) / 2;
		int powerupY = block.y + (block.height - Constants.POWERUP_HEIGHT) / 2;
		powerups.add(new Block(powerupX, powerupY, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, Constants.POWERUP));
	}

	public int getNumBricks() {
		return this.numBricks;
	}

	private void displayResult(Graphics g) {
		Block result;
		if (lost) {
			int centerX = (Constants.APPLICATION_WIDTH - Constants.LOST_WIDTH) / 2;
			int centerY = (Constants.APPLICATION_HEIGHT - Constants.LOST_HEIGHT) / 2;
			result = new Block(centerX, centerY, Constants.LOST_WIDTH, Constants.LOST_HEIGHT, Constants.LOST);
		} else {
			int centerX = (Constants.APPLICATION_WIDTH - Constants.WON_WIDTH) / 2;
			int centerY = (Constants.APPLICATION_HEIGHT - Constants.WON_HEIGHT) / 2;
			result = new Block(centerX, centerY, Constants.WON_WIDTH, Constants.WON_HEIGHT, Constants.WON);
		}
		result.draw(g, this);
	}
	
	public boolean isFinished() {
		return this.finished;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			this.animate = new Animate(this);
			this.thread = new Thread(animate);
			thread.start();
			break;
		case KeyEvent.VK_LEFT:
			if (this.paddle.x > 0) 
				this.paddle.x -= 15;
			break;
		case KeyEvent.VK_RIGHT:
			if (this.paddle.x < Constants.APPLICATION_WIDTH - Constants.PADDLE_WIDTH)
				this.paddle.x += 15;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
