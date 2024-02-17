/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

package BrekoutGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

public class ColoredBrick extends Sprite {
	private Color brickColor;
	private int brickHp = 0;
	private int killScore;
	private int hitScore;
	private String powerUp;

	/*
	 * Konstruktor med referens till superklassen "Sprite". Adderar extra egenskaper
	 * till klassen: "color, hp, hitScore, killScore & powerUp".
	 */
	public ColoredBrick(int x, int y, int width, int height, Color color, int hp, int hitScore, int killScore,
			String powerUp) {
		super(x, y, width, height);
		this.brickColor = color;
		this.brickHp = hp;
		this.killScore = killScore;
		this.hitScore = hitScore;
		this.powerUp = powerUp;

	}

	@Override
	public void draw(Graphics2D graphics) {
		/* Ritar upp brick */
		graphics.setColor(brickColor);
		graphics.fillRect(getX(), getY(), getWidth(), getHeight());

		/* Rundade hörn */
		// RoundRectangle2D roundedRect = new RoundRectangle2D.Double(getX(), getY(),
		// getWidth(), getHeight(), 8, 8);
		// graphics.fill(roundedRect);

		/* Ritar upp text inne i brick med antal hp kvar */
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Arial", Font.BOLD, 20));

		int textWidth = graphics.getFontMetrics().stringWidth(getHp() + "");
		int textHeight = graphics.getFontMetrics().getHeight();
		int textX = getX() + (getWidth() - textWidth) / 2;
		int textY = getY() + (getHeight() - textHeight) / 2 + graphics.getFontMetrics().getAscent();
		
		if (brickHp != 1337) {
			graphics.drawString(getHp() + "", textX, textY);
		}
		
	}

	public void hit() {
		if (brickHp != 1337) {
			this.brickHp--;
		}
	}

	public int getHp() {
		return this.brickHp;
	}

	public void updateColor(int brickHp) {
		switch (brickHp) {
		case 0:
			break;
		case 1:
			this.brickColor = brickColor.brighter();
			break;
		case 2:
			this.brickColor = brickColor.brighter();
			break;
		case 3:
			this.brickColor = brickColor.brighter();
			break;
		}
	}

	public int gethitScore() {
		return hitScore;
	}

	public int getKillScore() {
		return killScore;
	}

	public String getPowerUp() {
		return powerUp;
	}

	@Override
	public void update(Keyboard keyboard) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBounds() {
		bounds.x = getX();
		bounds.y = getY();
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

}
