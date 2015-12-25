package components;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BrickBreakerPanel extends JPanel implements KeyListener {

	ArrayList<Block> blocks = new ArrayList<Block>();
	Block paddle;
	Thread thread;
	Animate animate;

	public BrickBreakerPanel() {
		this.paddle = new Block((Constants.APPLICATION_WIDTH - Constants.PADDLE_WIDTH)/2,
								Constants.APPLICATION_HEIGHT - Constants.PADDLE_HEIGHT - Constants.PADDLE_Y_OFFSET,
								Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE);
		renderBricks();
		addKeyListener(this);
		setFocusable(true);
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

	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		for (Block block : this.blocks) {
			block.draw(graphic, this);
		}
		this.paddle.draw(graphic, this);
	}

	public void update() {
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			this.animate = new Animate(this);
			this.thread = new Thread(animate);
			thread.start();
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
