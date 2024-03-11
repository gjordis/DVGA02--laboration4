/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

package BrekoutGame;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class BrickCollection {
	private ArrayList<ColoredBrick> bricks;
	private int scoreCount = 0;

	public BrickCollection(int x, int y, int width, int height, int spacing, int windowWidth) {
		bricks = new ArrayList<>();
		int currentX = x;
		int currentY = y;
		int maxRows = 5;
		Random random = new Random();
		/* Skapar bricks, randomiserar vilka bricks som skapas 
		 * genom ett random-objekt och en switch-case */
		for (int rowCount = 1; rowCount <= maxRows;) {

			int brickType = random.nextInt(4);
			
			
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
	 * inktäktar bollen på brickan uppfylls if-satsen 
	 * pre: anrop från Game.update()
	 * post: returnerar bricka vid träff, annars returnerar null*/
	public ColoredBrick hitByBall(Ball ball) {
		for (ColoredBrick brick : bricks) {
			
			if (ball.getX() < brick.getX() + brick.getWidth() && ball.getX() + ball.getDiameter() > brick.getX()
					&& ball.getY() < brick.getY() + brick.getHeight()
					&& ball.getY() + ball.getDiameter() > brick.getY()) {
				scoreCount += brick.gethitScore();
				/* stannar bollen för att förhindra konstiga studsar med brickor */
				ball.setY(ball.getY());
				ball.setX(ball.getX());
				// reverserar bollens riktning i Y-led
				ball.reverseY();
				return brick;
			}
			
		}
		return null;
	}
	
	/* Tar bort brickan som skickas med som referens
	 * pre: brick.getHp <= 0
	 * post: tar bort brickan från listan */
	public void removeBrick(ColoredBrick brick) {
			scoreCount += brick.getKillScore();
			bricks.remove(brick);	
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
