/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

package BrekoutGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game {

	private BrickCollection brickCollection;
	private GameBoard board;
	private GameStateManager gameStateManager;
	private Paddle paddle;
	private ArrayList<Ball> ball = new ArrayList<Ball>();
	private ScoreTracker scoreTracker;
	private int tickCount = 0;
	private boolean highScoreDialogShown = false;

	private HighScore highScore;
	private LatestRuns latestRuns;

	public Game(GameBoard board) {
		this.board = board;
		brickCollection = new BrickCollection(Const.BRICKCOLLECTION_START_X, Const.BRICKCOLLECTION_START_Y,
				Const.BRICKCOLLECTION_WIDTH, Const.BRICKCOLLECTION_HEIGHT, Const.BRICKCOLLECTION_SPACING,
				Const.GAMEAREA_WIDTH);
		paddle = new Paddle(Const.GAMEAREA_WIDTH / 2 - Const.PADDLE_WIDTH / 2,
				Const.GAMEAREA_HEIGHT - Const.PADDLE_HEIGHT - 2, Const.PADDLE_WIDTH, Const.PADDLE_HEIGHT,
				Const.PADDLE_NAME);
		ball.add(new Ball(Const.DEFAULT_VALUE, Const.DEFAULT_VALUE, Const.BALL_DIAMETER, Const.BALL_SPEED_X,
				Const.BALL_SPEED_Y, true));
		scoreTracker = new ScoreTracker(this);
		gameStateManager = new GameStateManager(this);

		/* Skapar en panel för score och lägger in instanser av HighScore & LatestRuns */
		JPanel HighScorePanel = new JPanel();
		JPanel filler = new JPanel(); // används som space mellan listorna
		filler.setPreferredSize(new Dimension(200, 300));
		filler.setOpaque(false);

		HighScorePanel.setLayout(new BoxLayout(HighScorePanel, BoxLayout.Y_AXIS));
		HighScorePanel.setBackground(new Color(0, 0, 0, 50));

		highScore = new HighScore();
		latestRuns = new LatestRuns();
		board.add(HighScorePanel, BorderLayout.EAST);
		HighScorePanel.add(highScore);
		HighScorePanel.add(latestRuns);
		HighScorePanel.add(filler);

	}

	public void update(Keyboard keyboard) {

		tickCount++;

		/* hanterar paus */
		gameStateManager.managePause(keyboard);
		if (gameStateManager.getState() == State.PAUSED)
			return;

		paddle.update(keyboard);
		paddle.isInsideWindow(Const.GAMEAREA_WIDTH);

		gameStateManager.manageWinLoss(keyboard);

		/* Inga brickor kvar på planen */
		if (brickCollection.checkVictory()) {
			gameStateManager.setState(State.VICTORY);
			ball.clear();
			registerHighScore();
			return;
		}

		/* Inga bollar kvar på planen */
		else if (ball.isEmpty()) {
			scoreTracker.reduceBallsLeft();
			if (scoreTracker.getBallsLeft() <= 0 && gameStateManager.getState() != State.GAMEOVER) {
				gameStateManager.setState(State.GAMEOVER);
				registerHighScore();
				return;
			} else
				ball.add(new Ball(Const.DEFAULT_VALUE, Const.DEFAULT_VALUE, Const.BALL_DIAMETER, Const.BALL_SPEED_X,
						Const.BALL_SPEED_Y, true));

		}
		manageBallBrickLogics(keyboard);
		scoreTracker.updateScoreTracker(brickCollection.getScore());
	}

	public void draw(Graphics2D graphics) {

		scoreTracker.draw(graphics);
		brickCollection.draw(graphics);
		paddle.draw(graphics);
		for (Ball b : ball) {
			b.draw(graphics);
		}

		if (gameStateManager.getState() == State.PAUSED) {
			graphics.setColor(Color.CYAN.brighter());
			graphics.setFont(new Font("Lucida Console", Font.ITALIC, Const.SCORETRACKER_FONTSIZE_LARGE * 2));
			int textWidth = Const.GAMEAREA_WIDTH / 2 - graphics.getFontMetrics().stringWidth("PAUSED") / 2;
			graphics.drawString("PAUSED", textWidth, board.getHeight() / 2 + 80);
		}
	}
	/* Skapar flera bollar på den brick som förstördes om brickan hade en powerup
	 * Pre: hitBrick.getHp() <= 0
	 * Post: Skapar extrabollar från den brick som förstörts &
	 * 		 lägger till bollarna i en temporära listan som skickades med "newBalls"*/
	public void powerUpExtraBalls(ColoredBrick hitBrick, List<Ball> newBalls) {
		if (hitBrick.getPowerUp() == "multiBall") {
			Random random = new Random();
			for (int i = 0; i < 5; i++) {

				int speedX = random.nextInt(-10, 10);
				int speedY = 10;

				newBalls.add(new Ball(hitBrick.getX() + hitBrick.getWidth() / 2 * i,
						hitBrick.getY() + hitBrick.getHeight() * i, Const.BALL_DIAMETER, speedX, speedY, false));
			}
		}
	}
	/* Startar om spelet
	 * 
	 * Pre: keyboard.isKeyDown(Key.Space) && currentState == State.GAMEOVER || 
	 * 		keyboard.isKeyDown(Key.Space) && currentState == State.VICTORY
	 * Post: Nollställer alla värden & genererar en ny spelplan */
	public void restartGame() {

		scoreTracker.resetBallsLeft();
		brickCollection.resetScoreCount();
		ball.clear();
		this.brickCollection = new BrickCollection(Const.BRICKCOLLECTION_START_X, Const.BRICKCOLLECTION_START_Y,
				Const.BRICKCOLLECTION_WIDTH, Const.BRICKCOLLECTION_HEIGHT, Const.BRICKCOLLECTION_SPACING,
				Const.GAMEAREA_WIDTH);
		gameStateManager.setState(State.RUNNING);
		highScoreDialogShown = false;
		System.out.println("restart");

	}
	
	
	/* Hanterar bollen/bollarnas kollision med paddel & bricks
	 * 
	 * Pre: Används endast inom update() metoden i klassen Game
	 * Post: Anropar metoder hos objekt boll träffar &
	 * 		 tar bort bollar som är "döda" */
	public void manageBallBrickLogics(Keyboard keyboard) {
		/*
		 * Hanterar möjligheten till fler än en boll genom att ha en lista med alla
		 * bollar
		 */
		List<Ball> newBalls = new ArrayList<>(); // temporär lista för nya bollar
		Iterator<Ball> iterator = ball.iterator();
		while (iterator.hasNext()) {

			Ball b = iterator.next();
			b.update(keyboard);
			b.initiationPosition(paddle); // är bollen fast eller inte
			b.hitWall(Const.GAMEAREA_WIDTH, board.getHeight());

			/* boll träffar paddel */
			if (b.getBounds().intersects(paddle.getBounds()))
				b.hitPaddle(Const.GAMEAREA_WIDTH, board.getHeight(), paddle);

			/* Släpper bollen från paddel */
			if (keyboard.isKeyDown(Key.Space) && b.getStartPosition())
				b.setStartPosition(false);

			/*
			 * Kontrollerar om en bricka är träffad genom att använda logiken i hitByBall
			 * metoden i brickCollection. Vid träff returneras den träffade brickan.
			 */
			ColoredBrick hitBrick = brickCollection.hitByBall(b);
			if (hitBrick != null) {
				hitBrick.hit();
				if (hitBrick.getHp() <= 0) {
					powerUpExtraBalls(hitBrick, newBalls);
					brickCollection.removeBrick(hitBrick);
				}
			}

			/* Rensar bort döda bollar */
			if (!b.getAliveStatus()) {
				iterator.remove();
			}

		}
		/* Lägger till objekt som adderats från powerups */
		ball.addAll(newBalls);
	}

	/* Metod som visar upp en dialogruta & anropar metoder för att
	 * registrera nya poäng som användaren uppnått.
	 * 
	 * pre: true
	 * post: Vid highScore visas en dialogruta där initialer skall matas in.
	 * 		 Har allt matats in anropas highScore.pushNewScore() med initialer & score.
	 * 		 latestRuns.pushNewScore() anropas alltid med score */
	public void registerHighScore() {

		if (!highScoreDialogShown && highScore.isHighScore(brickCollection.getScore())) {
	        String initials = "";
	        boolean validInitials = false;
	        while (!validInitials) {
	            initials = JOptionPane.showInputDialog(board,
	                    "Grattis! Du har uppnått en highscore. Ange dina initialer (max 3 tecken):", "Ny Highscore",
	                    JOptionPane.INFORMATION_MESSAGE);
	            if (initials != null) {
	                if (initials.length() > 3) {
	                    JOptionPane.showMessageDialog(board, "Du angav för många initialer, vänligen ange max 3 tecken.",
	                            "Felaktig inmatning", JOptionPane.ERROR_MESSAGE);
	                } else if (initials.trim().isEmpty()) {
	                    JOptionPane.showMessageDialog(board, "Du angav inga initialer, vänligen försök igen.",
	                            "Felaktig inmatning", JOptionPane.ERROR_MESSAGE);
	                } else if (!initials.matches("[a-zA-Z]+")) {
	                    JOptionPane.showMessageDialog(board, "Du angav felaktiga initialer, vänligen försök igen.",
	                            "Felaktig inmatning", JOptionPane.ERROR_MESSAGE);
	                }else {
	                    validInitials = true;
	                }
	            } else {
	                
	                return;
	            }
	        }
	        highScore.pushNewScore(initials.toUpperCase(), brickCollection.getScore());
	        highScoreDialogShown = true;
	    }
		latestRuns.pushNewScore(brickCollection.getScore());

	}

	public int getTickCount() {
		return tickCount;
	}

	public void resetTickCount() {
		tickCount = 0;
	}

	public State getState() {
		return gameStateManager.getState();
	}

}
