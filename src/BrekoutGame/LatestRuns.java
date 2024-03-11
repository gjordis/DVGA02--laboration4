package BrekoutGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class LatestRuns extends JPanel{
	
	private DefaultListModel<PlayerScore> highScoreModelLatest3;
	private DefaultListModel<String> rankingModel;
	private JLabel title;
	private JPanel latestScore;
	private final int MAX = 3;
	
	public LatestRuns() {
		
		setPreferredSize(new Dimension(Const.LATESTRUNS_AREA_WIDTH, Const.LATESTRUNS_AREA_HEIGHT)); // Bestäm storlek
		setOpaque(false);

		title = new JLabel("LATEST:");
		Font font = new Font("Lucida Console", Font.BOLD, Const.HIGHSCORE_FONTSIZE_MEDIUM);
		title.setFont(font);
		title.setForeground(Color.CYAN); // Sätt textfärg
		add(title);

		// lägger in startdata för highscore-listorna
		highScoreModelLatest3 = new DefaultListModel<>();
		for (int i = 0; i < MAX; i++) {
			int DummyScore = 0;
			highScoreModelLatest3.addElement(new PlayerScore("" , DummyScore));
		}

		
		// initierar ranking-listan, lägger in värden, 1., 2. osv
		rankingModel = new DefaultListModel<>();
		for (int i = 0; i < MAX; i++) {
		    String listNr = (i + 1) + ". ";
		    rankingModel.addElement(listNr);
		}
		/* JList som används för att visa upp rankingModel */
		JList<String> ranking = new JList<>(rankingModel);
		ranking.setFont(new Font("Lucida Console", Font.BOLD, Const.HIGHSCORE_FONTSIZE_SMALL));
		ranking.setForeground(new Color(255,255,255,100));
		ranking.setBackground(new Color(0,0,0,0));
		// Inaktivera interaktion med JList
		ranking.setEnabled(false);
		ranking.setFocusable(false);
		
		// JList som används för att visa upp highScoreModelLatest3
		JList<PlayerScore> latestScores = new JList<>(highScoreModelLatest3);
		latestScores.setFont(new Font("Lucida Console", Font.BOLD, Const.HIGHSCORE_FONTSIZE_SMALL));
		latestScores.setBackground(new Color(0,0,0,0));
		// Inaktivera interaktion med JList
		latestScores.setEnabled(false);
		latestScores.setFocusable(false);
		
		// panel för att hålla listan med de tre senaste resultaten.
		latestScore = new JPanel();
		latestScore.setOpaque(false);
		add(latestScore);
		
		/* lägger in rankingen och ranking & latestScore-listan */
		latestScore.add(ranking);
		latestScore.add(latestScores);

	}

	public void pushNewScore(int score) {

		PlayerScore playerScore = null;
		playerScore = new PlayerScore( "", score);
		
		
		highScoreModelLatest3.add(0, playerScore);
		popLastScore();

	}
	
	public void popLastScore() {
		highScoreModelLatest3.remove(MAX);
	}

}