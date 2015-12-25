package components;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BrickBreakerPanel extends JPanel {

	ArrayList<Block> blocks = new ArrayList<Block>();

	public BrickBreakerPanel() {
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
		for (Block block : blocks) {
			block.draw(graphic, this);
		}
	}
}
