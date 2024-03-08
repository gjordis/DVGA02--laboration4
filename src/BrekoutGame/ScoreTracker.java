/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

package BrekoutGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ScoreTracker {
	private BrickCollection brickScore;
	private int ballsLeft = 3;
	private GameBoard board;
	private Game game;

	public ScoreTracker(BrickCollection score, GameBoard board, Game game) {
		
		this.brickScore = score;
		this.board = board;
		this.game = game;
	}

	public void draw(Graphics2D graphics) {
		/* Ritar upp scoreboard */
		/* Om spelaren har bollar kvar visas en live scoreboard
		 * som visar antalet poäng */
		if (ballsLeft > 0 && game.getState() != State.VICTORY) {
			graphics.setColor(Color.CYAN.darker());
			graphics.setFont(new Font("Lucida Console", Font.BOLD, 40));
			String score = String.valueOf(brickScore.getScore());
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y);
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y -10, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y -10);
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y +70, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y +70);
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y +80, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y +80);
			
			// kontrollerar antalet bollar kvar
			graphics.drawOval(Const.SCORETRACKER_BALLS_LEFT_X  * 45, Const.SCORETRACKER_BALLS_LEFT_Y, Const.SCORETRACKER_BALLS_LEFT_DIAMETER, Const.SCORETRACKER_BALLS_LEFT_DIAMETER);
			graphics.drawOval(Const.SCORETRACKER_BALLS_LEFT_X + 1 * 45, Const.SCORETRACKER_BALLS_LEFT_Y, Const.SCORETRACKER_BALLS_LEFT_DIAMETER, Const.SCORETRACKER_BALLS_LEFT_DIAMETER);
			graphics.drawOval(Const.SCORETRACKER_BALLS_LEFT_X + 2 * 45, Const.SCORETRACKER_BALLS_LEFT_Y, Const.SCORETRACKER_BALLS_LEFT_DIAMETER, Const.SCORETRACKER_BALLS_LEFT_DIAMETER);
			for(int i = 0; i < ballsLeft; i++) {
				graphics.fillOval(Const.SCORETRACKER_BALLS_LEFT_X + i * 45, Const.SCORETRACKER_BALLS_LEFT_Y, Const.SCORETRACKER_BALLS_LEFT_DIAMETER, Const.SCORETRACKER_BALLS_LEFT_DIAMETER);
			}
			
			int textWidth = graphics.getFontMetrics().stringWidth(score);
			int textHeight = graphics.getFontMetrics().getHeight();

			int textX = Const.GAMEAREA_WIDTH / 2 - textWidth /2;
			int textY = 510 - textHeight + graphics.getFontMetrics().getAscent();
			
			graphics.drawString(score, textX, textY);
		}
		/* Ritar upp Game Over om spelaren har slut på bollar */
		else if (ballsLeft <= 0) {
			graphics.setColor(Color.CYAN.darker());
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y);
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y -10, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y -10);
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y +70, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y +70);
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y +80, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y +80);
			
			graphics.setFont(new Font("Lucida Console", Font.BOLD, Const.SCORETRACKER_FONTSIZE_MEDIUM));
			
			int textWidth = Const.GAMEAREA_WIDTH /2  - graphics.getFontMetrics().stringWidth("Game Over") / 2;
			int textHeight = graphics.getFontMetrics().getHeight();

			int textY = board.getHeight() /2 + 110 - textHeight + graphics.getFontMetrics().getAscent();
			
			graphics.drawString("Game Over", textWidth, textY);
			graphics.setFont(new Font("Lucida Console", Font.ITALIC, Const.SCORETRACKER_FONTSIZE_SMALL));
			
			int textWidth2 = Const.GAMEAREA_WIDTH /2 - graphics.getFontMetrics().stringWidth("Your score: " + brickScore.getScore()) / 2;
			int textWidth3 = Const.GAMEAREA_WIDTH /2 - graphics.getFontMetrics().stringWidth("Press 'space' to play again") /2;
			
			graphics.drawString("Your score: " + brickScore.getScore(), textWidth2 , textY + 80);
			graphics.drawString("Press 'space' to play again", textWidth3, textY + 120);
			
		}
		/* Ritar upp Victory vid vinst */
		else if(game.getState() == State.VICTORY) {
			graphics.setColor(Color.CYAN.darker());
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y);
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y -10, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y -10);
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y +70, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y +70);
			graphics.drawLine(Const.SCORETRACKER_LINE_START_X, Const.SCORETRACKER_LINE_START_Y +80, Const.SCORETRACKER_LINE_END_X, Const.SCORETRACKER_LINE_END_Y +80);
			
			graphics.setFont(new Font("Lucida Console", Font.BOLD, Const.SCORETRACKER_FONTSIZE_MEDIUM));
			
			int textWidth = Const.GAMEAREA_WIDTH /2  - graphics.getFontMetrics().stringWidth("Victory") / 2;
			int textHeight = graphics.getFontMetrics().getHeight();

			int textY = board.getHeight() /2 + 110 - textHeight + graphics.getFontMetrics().getAscent();
			
			graphics.drawString("Victory", textWidth, textY);
			graphics.setFont(new Font("Lucida Console", Font.ITALIC, Const.SCORETRACKER_FONTSIZE_SMALL));
			
			int textWidth2 = Const.GAMEAREA_WIDTH /2 - graphics.getFontMetrics().stringWidth("Your score: " + brickScore.getScore()) / 2;
			int textWidth3 = Const.GAMEAREA_WIDTH /2 - graphics.getFontMetrics().stringWidth("Press 'space' to play again") /2;
			
			graphics.drawString("Your score: " + brickScore.getScore(), textWidth2 , textY + 80);
			graphics.drawString("Press 'space' to play again", textWidth3, textY + 120);
		}

	}
	
	public void countScoreOn(BrickCollection brickCollection) {
		this.brickScore = brickCollection;
	}
	
	public int getBallsLeft() {
		return ballsLeft;
	}
	
	public void reduceBallsLeft() {
		ballsLeft--;
	}
	
	public void resetBallsLeft() {
		ballsLeft = 4;
	}

}
