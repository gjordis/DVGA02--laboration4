/* Skapad av Jonas Schymberg
 * Kurs: DVGA02
 * VT - 24
 * Uppgift: Laboration2 - Breakoutspel */

package BrekoutGame;

public class GameStateManager {
	private State currentState;
	private Game game;

	public GameStateManager(Game game) {
		this.game = game;
		this.currentState = State.RUNNING;
	}

	public void managePause(Keyboard keyboard) {

		/* hanterar paus-lägen */
		if (keyboard.isKeyDown(Key.Escape) && game.getTickCount() > 20) {
			if (currentState == State.RUNNING)
				currentState = State.PAUSED;
			else
				currentState = State.RUNNING;
			game.resetTickCount();
		}
	}

	public void manageWinLoss(Keyboard keyboard) {

		/* Hanterar omstart vid vinst/förlust */
		if (currentState != State.RUNNING) {

			if (keyboard.isKeyDown(Key.Space) && currentState == State.GAMEOVER)
				game.restartGame();
			else if (keyboard.isKeyDown(Key.Space) && currentState == State.VICTORY) {
				game.restartGame();
			}
			return;
		}

	}

	public State getState() {
		return currentState;
	}

	public void setState(State state) {
		currentState = state;
	}
}
