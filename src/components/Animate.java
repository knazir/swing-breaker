package components;

public class Animate implements Runnable {

	private BrickBreakerPanel panel;
	
	public Animate(BrickBreakerPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void run() {
		while (true) {
			panel.update();
			
			try {
				Thread.sleep(Constants.DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
