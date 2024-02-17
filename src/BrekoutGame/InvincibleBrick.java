/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

package BrekoutGame;
import java.awt.Color;
import java.awt.Graphics2D;

public class InvincibleBrick extends ColoredBrick {
	private boolean invincibleStatus = true;
	/* Anropar super med:
	 * x, y, width, height, color, hp, hitScore, killScore, powerUp */
	public InvincibleBrick(int x, int y, int width, int height) {
		super(x, y, width, height, Color.DARK_GRAY.darker().darker().darker(), 1337, 0, 0, "");
		
	}

	public boolean getInvincibleStatus() {
		return invincibleStatus;
	}
}
