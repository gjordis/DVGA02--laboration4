/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

package BrekoutGame;
import java.awt.Color;
import java.awt.Graphics2D;

public class CyanBrick extends ColoredBrick {
	/* Anropar super med:
	 * x, y, width, height, color, hp, hitScore, killScore, powerUp */
	public CyanBrick(int x, int y, int width, int height) {
		super(x, y, width, height, Color.CYAN.darker(), 1, 0, 5, "");

	}

}
