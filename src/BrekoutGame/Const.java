/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

package BrekoutGame;

public class Const {
	
	public static int DEFAULT_VALUE = 0;
	public static int GAMEAREA_WIDTH = 1200;
	public static int GAMEAREA_HEIGHT = 800;
	
	
	/* ScoreBoard */
	public static final int SCOREBOARD_LINE_START_X = 500;
	public static final int SCOREBOARD_LINE_START_Y = 450;
	public static final int SCOREBOARD_LINE_END_X = 700;
	public static final int SCOREBOARD_LINE_END_Y = 450;
	public static final int SCOREBOARD_FONTSIZE_SMALL = 30;
	public static final int SCOREBOARD_FONTSIZE_MEDIUM = 40;
	public static final int SCOREBOARD_FONTSIZE_LARGE = 80;
	public static final int SCOREBOARD_BALLS_LEFT_X = 540;
	public static final int SCOREBOARD_BALLS_LEFT_Y = 550;
	public static final int SCOREBOARD_BALLS_LEFT_DIAMETER = 20;
	
	/* Paddle */
	public static final int PADDLE_WIDTH = 150;
	public static final int PADDLE_HEIGHT = 20;
	public static final String PADDLE_NAME = "<<<< JS >>>>";
	
	/* BrickCollection */
	public static final int BRICKCOLLECTION_START_X = 10;
	public static final int BRICKCOLLECTION_START_Y = 10;
	public static final int BRICKCOLLECTION_WIDTH = 122;
	public static final int BRICKCOLLECTION_HEIGHT = 30;
	public static final int BRICKCOLLECTION_SPACING = 10;
	
	/* Ball */
	public static final int BALL_DIAMETER = 20;
	public static final int BALL_SPEED_X = 10;
	public static final int BALL_SPEED_Y = 10;
	
	/* Brick powerUps */
	public static final String POWERUP_MULTIBALL = "multiball";
}
