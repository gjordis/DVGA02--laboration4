/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

package BrekoutGame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Ball extends Sprite {
	private int diameter, speedX = 10, speedY = 10;
	private boolean startPosition = false;
	private boolean alive = true;
	
	public Ball(int x, int y, int diameter, int speedX, int speedY, boolean startPosition) {
		super(x, y, diameter, diameter);
		this.diameter = diameter;
		this.speedX = speedX;
		this.speedY = speedY;
		this.startPosition = startPosition;
		
		
	}

	@Override
	public void update(Keyboard keyboard) {
		updateBounds();
	}

	@Override
	public void draw(Graphics2D graphics) {

		graphics.setColor(Color.BLACK);
		graphics.fillOval(getX(), getY(), diameter, diameter);
		
		graphics.setColor(Color.WHITE);
		graphics.drawOval(getX(), getY(), diameter, diameter);
		
		graphics.setColor(Color.WHITE.darker());
		graphics.drawOval(getX()-1, getY()-1, diameter+2, diameter+2);
		
		graphics.setColor(Color.WHITE.darker().darker());
		graphics.drawOval(getX()-2, getY()-2, diameter+4, diameter+4);
		
		

	}
	
	public void initiationPosition(Paddle paddle) {
		/* Om flaggan startPosition är true,
		 * placerar bollen mitt på paddeln */
		if (startPosition) {
			setX(paddle.getX() + paddle.getWidth() / 2 - diameter / 2);
			setY(paddle.getY() - diameter);
			// förbereder bollens nästa startbana & hastighet
			Random random = new Random();
			speedX = random.nextInt(- 20, 20);
			speedY = 10;
		}else {
			setX(getX() + speedX);
			setY(getY() + speedY);
		}
	}
	
	public void hitWall(int windowWidth, int windowHeight) {
		
		/* kollision väggar */
		
		// top
		if (getY() < 0) {
			reverseY();
			setY(0); // säkertsäller att bollen ej fastnar
		}
		// höger
		if (getX() + diameter > windowWidth) {
			reverseX();
			setX(windowWidth - diameter);
		}
		// vänster
		if (getX() < 0) {
			reverseX();
			setX(0);
		}
		// botten
		if (getY() > windowHeight) {
			alive = false;
			
		}
	
	}
	
	/*
	 * kollision paddle. Vid träff av paddel, hämtar ut bollens träffpunk på paddeln
	 * & paddelns mitt. Vid träff på mitten kommer bollens träffpunkt och paddelns
	 * center vara samma värde. Vid träff på väster sidan är värdet negativt och vid
	 * höger sida positivt. Med dessa värden kan justeringar av bollens X hastighet
	 * göras, detta gör att bollen studsar åt vänster och höger beroende på sida av
	 * paddeln. Ju längre ut bollen träffar, ju mer ökar hastigheten. 
	 * Risk reward baby!!
	 */
	public void hitPaddle(int windowWidth, int windowHeight, Paddle paddle) {
		
			int ballBottom = getY() + diameter;
			int ballLeft = getX();
			int ballRight = getX() + diameter;
			int paddleLeft = paddle.getX();
			int paddleRight = paddle.getX() + paddle.getWidth();
			
			if (ballBottom > windowHeight - paddle.getHeight()) {
				if (ballRight > paddleLeft && ballLeft < paddleRight) {
					
					// ändrar bollens y-position för att förhindra att den fastnar
					setY(windowHeight - paddle.getHeight() - diameter - 1);
					reverseY();
					int ballHitPoint = getX() - paddle.getX() + diameter / 2;
					int paddleCenter = paddle.getWidth() / 2;
					speedX = (ballHitPoint - paddleCenter) / 6;
					// System.out.println("ballHit: " + ballHit);
					// System.out.println("paddleCenter: " + paddleCenter);
					// System.out.println(speedX);
				}
			}
		
		}

	
	public void resetBall() {
		startPosition = true;
		speedX = 10;
		speedY = 10;
	}

	public void reverseX() {
		speedX = -speedX;
	}

	public void reverseY() {
		speedY = -speedY;
	}

	public int getDiameter() {
		return diameter;
	}
	
	
	public boolean getAliveStatus() {
		return alive;
	}
	
	public boolean getStartPosition() {
		return startPosition;
	}
	
	public void setBallToPaddle(boolean status) {
		startPosition = status;
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
