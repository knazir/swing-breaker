package components;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Block extends Rectangle {
	
	private Image image;
	public int dx = Constants.BALL_X_SPEED;
	public int dy = Constants.BALL_Y_SPEED;
	
	public Block(int x, int y, int width, int height, String image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = Toolkit.getDefaultToolkit().getImage(image);
	}
	
	public void draw(Graphics graphic, Component component) {
		graphic.drawImage(this.image, this.x, this.y, component);
	}

}
