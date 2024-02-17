/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.*;

public class Game {

	private BrickCollection brickCollection;
	private GameBoard board;
	private Paddle paddle;
	// private Ball ball;
	private ArrayList<Ball> ball = new ArrayList<Ball>();
	private ScoreBoard scoreBoard;
	private GameState gameState;
	private int tickCount = 0;

	public Game(GameBoard board) {
		this.board = board;
		brickCollection = new BrickCollection(Const.BRICKCOLLECTION_START_X, Const.BRICKCOLLECTION_START_Y, Const.BRICKCOLLECTION_WIDTH, 
				Const.BRICKCOLLECTION_HEIGHT, Const.BRICKCOLLECTION_SPACING, board.getPreferredSize().width);
		paddle = new Paddle(board.getPreferredSize().width / 2 - 75, board.getPreferredSize().height - 25, Const.PADDLE_WIDTH, Const.PADDLE_HEIGHT, Const.PADDLE_NAME);
		ball.add(new Ball(Const.DEFAULT_VALUE, Const.DEFAULT_VALUE , Const.BALL_DIAMETER, Const.BALL_SPEED_X, Const.BALL_SPEED_Y, true));
		scoreBoard = new ScoreBoard(1, 1, 1, 1, brickCollection, board);
		gameState = GameState.RUNNING;
	}

	public void update(Keyboard keyboard) {
		
		tickCount++;
		
		/* hanterar paus-lägen */
		if(keyboard.isKeyDown(Key.Escape) && tickCount > 20) {
			if(gameState == GameState.RUNNING)
				gameState = GameState.PAUSED;
			else 
				gameState = GameState.RUNNING;
			tickCount = 0;
		}
		if(gameState == GameState.PAUSED) {
			return;
		}
		
		paddle.move(keyboard);
		paddle.isInsideWindow(board.getWidth());
		
		/* Hanterar omstart vid vinst/förlust */
		if(gameState != GameState.RUNNING) {
			
			if(keyboard.isKeyDown(Key.Space) && gameState == GameState.GAMEOVER) {
				restartGame();
			}else if(keyboard.isKeyDown(Key.Space) && gameState == GameState.VICTORY)
				restartGame();
			return;
			
			}else 
		
		
		if(brickCollection.checkVictory()) {
			ball.clear();
			gameState = GameState.VICTORY;
			return;
		}
		
		/* Inga bollar kvar  på planen */
		if (ball.isEmpty()) {
			System.out.println("alla bollar ute");
			scoreBoard.reduceBallsLeft();
			if(scoreBoard.getBallsLeft() <= 0) {
				gameState = GameState.GAMEOVER;
			}else {
				ball.add(new Ball(Const.DEFAULT_VALUE, Const.DEFAULT_VALUE , Const.BALL_DIAMETER, Const.BALL_SPEED_X, Const.BALL_SPEED_Y, true));
			}

		}
		
		
		/* Hanterar möjligheten till fler än en boll
		 * genom att ha en lista med alla bollar */
		List<Ball> newBalls = new ArrayList<>();
		Iterator<Ball> iterator = ball.iterator();
		while (iterator.hasNext()) {
			
		    Ball b = iterator.next();
			b.updateBounds();
			b.initiationPosition(paddle);
			b.hitWall(board.getWidth(), board.getHeight());
			
			/* boll träffar paddel */
			if (b.bounds.intersects(paddle.bounds)) {
				System.out.println("träffad");
				b.hitPaddle(board.getWidth(), board.getHeight(), paddle);
			}
			
			/* Släpper bollen från paddel */
			if (keyboard.isKeyDown(Key.Space) && b.getStartPosition()) {
				b.setBallToPaddle(false);	
			}
			
			
			/* !!Skall tas bord!! Används för test */
			if(keyboard.isKeyDown(Key.Enter)) {
				b.resetBall();
			}
			
			/* Hade gärna använt inte true/false metod för att
			 * kontrollera träff på en brick. Men kunde inte få till det.
			 * Anropar hitByball hela tiden istället. Tror inte det
			 * är optimalt. */
			ColoredBrick hitBrick = brickCollection.hitByBall(b);
			if (hitBrick != null) {
				hitBrick.hit();
				hitBrick.updateColor(hitBrick.getHp());
				if (hitBrick.getHp() <= 0) {
					powerUpExtraBalls(hitBrick, newBalls);
					brickCollection.removeBrick(hitBrick);

				}
				b.reverseY();
			}
			
			/* Rensar bort döda bollar */
			if (!b.getAliveStatus()) {
				iterator.remove();
			}

		}
		/* Lägger till objekt som adderats
		 * från powerups */
		ball.addAll(newBalls);

	}

	public void draw(Graphics2D graphics) {
		scoreBoard.draw(graphics);
		brickCollection.draw(graphics);
		paddle.draw(graphics);
		for (Ball b : ball) {
			b.draw(graphics);
		}
		
		if(gameState == GameState.PAUSED) {
			graphics.setColor(Color.CYAN.brighter());
			graphics.setFont(new Font("Arial", Font.ITALIC, Const.SCOREBOARD_FONTSIZE_LARGE));
			int textWidth = board.getWidth() /2 - graphics.getFontMetrics().stringWidth("PAUSED") /2;
			graphics.drawString("PAUSED", textWidth , board.getHeight() / 3 + 80);
		}
	}
	
	public void powerUpExtraBalls(ColoredBrick hitBrick, List<Ball> newBalls) {
		if(hitBrick.getPowerUp() == "multiBall") {
			Random random = new Random();
			for (int i = 0; i < 5; i++) {

				int speedX = random.nextInt(-10, 10);
				int speedY = 10;
				
				newBalls.add(new Ball(hitBrick.getX() + hitBrick.getWidth() / 2 * i, 
						hitBrick.getY() + hitBrick.getHeight() * i, 20, speedX, speedY, false));
			}
		}
		
	}
	
	public void restartGame() {
		scoreBoard.resetBallsLeft();
		brickCollection.resetScoreCount();
		ball.clear();
		//ball.add(new Ball(Const.DEFAULT_VALUE, Const.DEFAULT_VALUE , Const.BALL_DIAMETER, Const.BALL_SPEED_X, Const.BALL_SPEED_Y, true));
		this.brickCollection = new BrickCollection(Const.BRICKCOLLECTION_START_X, Const.BRICKCOLLECTION_START_Y, 
				Const.BRICKCOLLECTION_WIDTH, Const.BRICKCOLLECTION_HEIGHT, Const.BRICKCOLLECTION_SPACING, board.getPreferredSize().width);
		// skickar med den nya kollektionen till scoreboard för att kunna räkna poäng
		scoreBoard.countScoreOn(brickCollection);
		//ball.get(0).setBallToPaddle(true);
		gameState = GameState.RUNNING;
	}
	
	public GameState getGameState() {
		return gameState;
	}

}

