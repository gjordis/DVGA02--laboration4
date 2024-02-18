package BrekoutGame;

public class PlayerScore {
	private String initials;
	private int score;
	
	public PlayerScore(String ini, int sc) {
		this.initials = ini;
		this.score = sc;
	}
	
	public String getInitials() {
		return initials;
	}
	
	public int getScore() {
		return score;
	}
	
	@Override
    public String toString() {
        return initials + " " + score;
    }
	
}
