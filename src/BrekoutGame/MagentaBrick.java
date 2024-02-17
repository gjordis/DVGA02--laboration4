/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

package BrekoutGame;
import java.awt.Color;
import java.awt.Graphics2D;

public class MagentaBrick extends ColoredBrick {
	/* Anropar super med:
	 * x, y, width, height, color, hp, hitScore, killScore, powerUp */
	public MagentaBrick(int x, int y, int width, int height) {
		super(x, y, width, height, Color.MAGENTA.darker().darker(), 3, 3, 6, "");

	}

}
