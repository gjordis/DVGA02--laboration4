import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameBoard extends JComponent {
	private final int FPS = 40;
	private Game game;
	private Keyboard keyboard;
	
	/* tillägg för bakgrundsbild */
	private Image backgroundImage;

	public GameBoard() {
		keyboard = new Keyboard();
		game = new Game(this);
		
		/* tillägg för bakgrundsbild
		 * Hämtat från: https://wallpapergod.com/synthwave */
		backgroundImage = Toolkit.getDefaultToolkit().createImage("BreakoutBackground.jpeg");
			
	}
	

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1200, 800);
	}

	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		Graphics2D graphics = (Graphics2D) arg0;
		//graphics.setColor(Color.DARK_GRAY);
		//graphics.fillRect(0, 0, getWidth(), getHeight());
		
		/* tillägg för bakgrundsbild */
		graphics.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		
		game.draw(graphics);
	}

	@Override
	protected void processKeyEvent(KeyEvent e) {
		super.processKeyEvent(e);
		if (e.getID() == KeyEvent.KEY_PRESSED)
			keyboard.processKeyEvent(e.getKeyCode(), true);
		else if (e.getID() == KeyEvent.KEY_RELEASED)
			keyboard.processKeyEvent(e.getKeyCode(), false);
	}

	public void start() {
		while (true) {
			game.update(keyboard);
			try {
				Thread.sleep(1000 / FPS); // Throttle thread
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
		}
	}
}
