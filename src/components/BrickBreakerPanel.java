package components;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BrickBreakerPanel extends JPanel implements KeyListener {

	private ArrayList<Block> blocks;
	private ArrayList<Block> balls;
	private Block paddle;
	private Thread thread;
	private Animate animate;

	public BrickBreakerPanel() {
		this.blocks = new ArrayList<Block>();
		this.balls = new ArrayList<Block>();
		
		createPaddle();
		createBall();
		renderBricks();
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

	private void renderBricks() {
		for (int i = 0;  i < Constants.NUM_COLS; i++) {
			for (int j = 0; j < Constants.NUM_ROWS; j++) {
				blocks.add(new Block(i * Constants.BRICK_WIDTH + Constants.BRICK_SPACING, 
						j * Constants.BRICK_HEIGHT + Constants.BRICK_SPACING, 
						Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT, getColor(j)));
			}
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
		
		this.paddle.draw(g, this);
	}

	public void update() {
		for (Block ball : this.balls) {
			if (ball.x <= 0 || ball.x + Constants.BALL_WIDTH >= Constants.APPLICATION_WIDTH)
				ball.dx *= -1;
			ball.x += ball.dx;
			
			if (ball.y < 0 || ball.intersects(this.paddle))
				ball.dy *= -1;
			ball.y += ball.dy;
		}
		repaint();
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
