package client;

import javax.swing.JFrame;

import components.BrickBreakerPanel;
import components.Constants;

public class BrickBreaker {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Birck Breaker");
		
		BrickBreakerPanel panel = new BrickBreakerPanel();
		frame.getContentPane().add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(Constants.APPLICATION_WIDTH, Constants.APPLICATION_HEIGHT);
		frame.setResizable(false);
	}
}
