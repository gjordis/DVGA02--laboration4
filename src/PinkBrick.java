/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

import java.awt.Color;
import java.awt.Graphics2D;

public class PinkBrick extends ColoredBrick {
	/* Anropar super med:
	 * x, y, width, height, color, hp, hitScore, killScore, powerUp */
	public PinkBrick(int x, int y, int width, int height) {
		super(x, y, width, height, Color.PINK.darker(), 2, 2, 5, "");
		
	}

}
