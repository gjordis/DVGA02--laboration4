/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class BrickCollection {
	private ArrayList<ColoredBrick> bricks;
	private int scoreCount = 0;

	public BrickCollection(int x, int y, int width, int height, int spacing, int windowWidth) {
		bricks = new ArrayList<>();
		int currentX = x;
		int currentY = y;
		int maxRows = 4;
		int invincibleBrickCount = 0;
		Random random = new Random();
		/* Skapar bricks, randomiserar vilka bricks som skapas 
		 * genom ett random-objekt och en switch-case */
		for (int rowCount = 1; rowCount <= maxRows;) {

			int brickType = random.nextInt(5);
			
			
			switch (brickType) {
			case 0:
				bricks.add(new MagentaBrick(currentX, currentY, width, height));
				break;
			case 1:
				bricks.add(new DarkGrayBrick(currentX, currentY, width, height));
				break;
			case 2:
				bricks.add(new PinkBrick(currentX, currentY, width, height));
				break;
			case 3:
				bricks.add(new CyanBrick(currentX, currentY, width, height));
				break;
			case 4:
				
				invincibleBrickCount++;
				if(invincibleBrickCount % 2 == 0) {
					bricks.add(new InvincibleBrick(currentX, currentY, width, height));
				}else
					bricks.add(new CyanBrick(currentX, currentY, width, height));
				break;
				
			}

			currentX += width + spacing;

			if (currentX + width > windowWidth) {
				currentX = x;
				currentY += height + spacing;
				rowCount++;
			}

		}

	}

	public void draw(Graphics2D graphics) {
		for (ColoredBrick brick : bricks) {
			brick.draw(graphics);
		}
	}
	
	/* Kontroll om bollen träffar en bricka, vid träff returneras denna bricka.
	 * Alla sidor på bollen kontrolleras mot brickans sidor. 
	 * inktäktar bollen på brickan uppfylls if-satsen */
	public ColoredBrick hitByBall(Ball ball) {
		for (ColoredBrick brick : bricks) {
			/* Försök att få till så att intersects fungerar istället *//*
			if (ball.bounds.intersects(brick.bounds)) {
				System.out.println("Ball: " + ball.bounds + " Brick: " + brick.bounds);
				scoreCount += brick.gethitScore();
				return brick;
				
			}
			*/
			if (ball.getX() < brick.getX() + brick.getWidth() && ball.getX() + ball.getDiameter() > brick.getX()
					&& ball.getY() < brick.getY() + brick.getHeight()
					&& ball.getY() + ball.getDiameter() > brick.getY()) {
				scoreCount += brick.gethitScore();
				/* flyttar ned bollen en aning för att förhindra
				 * konstiga studsar med brickor */
				ball.setY(ball.getY() + 5);
				return brick;
			}
			
		}
		return null;
	}
	

	public void removeBrick(ColoredBrick brick) {
		if (brick.getHp() == 0) {
			bricks.remove(brick);
			scoreCount += brick.getKillScore();
	
		}
	}

	public int getScore() {
		return scoreCount;
	}
	
	public void resetScoreCount() {
		scoreCount = 0;
	}
	
	public boolean checkVictory() {
		if(bricks.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

}
