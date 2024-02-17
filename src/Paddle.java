/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Paddle extends Sprite {
	private int width, height;
	private int paddleSpeed = 15;
	//private Color paddleColor;
	private String name;

	public Paddle(int x, int y, int width, int height, String name) {
		super(x, y, width, height);
		this.width = width;
		this.height = height;
		//this.paddleColor = color;
		this.name = name;

	}

	@Override
	public void update(Keyboard keyboard) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D graphics) {
		graphics.setColor(Color.DARK_GRAY.darker().darker());
		graphics.fillRect(getX(), getY(), getWidth(), getHeight());

		graphics.setColor(Color.MAGENTA);
		graphics.drawRect(getX(), getY(), getWidth(), getHeight());

		graphics.setColor(Color.MAGENTA);
		graphics.setFont(new Font("Arial", Font.ITALIC, 20));

		int textWidth = graphics.getFontMetrics().stringWidth(name);
		int textHeight = graphics.getFontMetrics().getAscent();
		//System.out.println(textHeight);

		int textX = getX() + (width - textWidth) / 2;
		int textY = getY() + (height - textHeight) / 2 + textHeight;

		graphics.drawString(name, textX, textY);

	}

	public void move(Keyboard keyboard) {

		if (keyboard.isKeyDown(Key.Left)) {
			setX(getX() - paddleSpeed);
		}
		if (keyboard.isKeyDown(Key.Right)) {
			setX(getX() + paddleSpeed);
		}
		updateBounds();

	}

	public void isInsideWindow(int windowWidth) {
		if (getX() + width > windowWidth) {
			setX(windowWidth - width);
		}
		if (getX() < 0) {
			setX(0);
		}
	}

	@Override
	public void updateBounds() {
		bounds.x = getX();
		bounds.y = getY();
		
	}
	

}
