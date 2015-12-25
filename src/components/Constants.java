package components;

public class Constants {

	private Constants() {
		
	}
	
	// Game constants
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
	public static final int NUM_COLORS = 4;
	public static final int BRICK_WIDTH = 97;
	public static final int BRICK_HEIGHT = 27;
	public static final int PADDLE_WIDTH = 150;
	public static final int PADDLE_HEIGHT = 25;
	public static final int PADDLE_DX = 15;
	public static final int DELAY = 10;
	
	// Graphical constants
	public static final int BRICK_SPACING = 2;
	public static final int PADDLE_Y_OFFSET = 50;
	public static final String PADDLE = "assets/paddle.png";
	public static final String RED_BRICK = "assets/red.png";
	public static final String GEREN_BRICK = "assets/green.png";
	public static final String BLUE_BRICK = "assets/blue.png";
	public static final String YELLOW_BRICK = "assets/yellow.png";
	public static final int APPLICATION_WIDTH = (NUM_ROWS + BRICK_SPACING) * BRICK_WIDTH;
	public static final int APPLICATION_HEIGHT = 700;
}
