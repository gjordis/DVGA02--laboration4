package BrekoutGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.StyledEditorKit.FontSizeAction;

public class HighScore extends JPanel {
	
	private DefaultListModel<PlayerScore> highScoreModel;
	private static final String FILE_PATH = "highscores.txt";
	private JLabel title;
	private JPanel scorePanel;
	private static final int MAX = 10;

	public HighScore() {

		setPreferredSize(new Dimension(Const.HIGHSCORE_AREA_WIDTH, Const.HIGHSCORE_AREA_HEIGHT)); // Bestäm storlek
		setOpaque(false);
		this.setLayout(new BoxLayout(this, 1));

		JPanel titlePanel = new JPanel();
		titlePanel.setOpaque(false);
		title = new JLabel("HIGHSCORE:");
		Font font = new Font("Arial", Font.BOLD, Const.HIGHSCORE_FONTSIZE_MEDIUM);
		title.setFont(font);
		title.setForeground(Color.CYAN); // Sätt textfärg
		titlePanel.add(title); // Lägg till titelpanelen i toppen av panelen
		add(titlePanel, BorderLayout.WEST);

		// lägger in startdata för highscore-listorna
		highScoreModel = new DefaultListModel<>();
		readFromFile();
		/*
		for (int i = 0; i < 10; i++) {

			int DummyScore = 0;
			PlayerScore playerScore;
			highScoreModel.addElement(playerScore = new PlayerScore("...", DummyScore));
		}
		*/
		
		
		// panel för att hålla highscore 1-10.
		scorePanel = new JPanel();
		scorePanel.setOpaque(false);
		add(scorePanel, BorderLayout.CENTER);

		// skapar modellen av rankinglistan
		DefaultListModel<String> rankingModel = new DefaultListModel<>();
		for (int i = 0; i < MAX; i++) {
			String rank = (i + 1) + ".";
			rankingModel.addElement(rank);
		}

		JList<String> ranking = new JList<>(rankingModel);
		ranking.setFont(new Font("Arial", Font.BOLD, Const.HIGHSCORE_FONTSIZE_SMALL));
		ranking.setForeground(new Color(255, 255, 255, 100));
		ranking.setBackground(new Color(0, 0, 0, 0));
		// Inaktivera interaktion med JList
		ranking.setEnabled(false);
		ranking.setFocusable(false);

		// JList som används för att visa upp highScoreModel
		JList<PlayerScore> highScoresTop10 = new JList<>(highScoreModel);
		highScoresTop10.setFont(new Font("Arial", Font.BOLD, Const.HIGHSCORE_FONTSIZE_SMALL));
		highScoresTop10.setBackground(new Color(0, 0, 0, 0));

		scorePanel.add(ranking);
		scorePanel.add(highScoresTop10);

		// Inaktivera interaktion med JList
		highScoresTop10.setEnabled(false);
		highScoresTop10.setFocusable(false);
		
		

	}

	public void pushNewScore(String initials, int score) {

		int index = checkIndex(score);
		String formattedInitials = initials + "     ";
		PlayerScore playerScore = new PlayerScore(formattedInitials, score);
		System.out.println(playerScore.toString());
		System.out.println(highScoreModel.get(score).toString());
		highScoreModel.add(index, playerScore);
		popLastScore();
		saveToFile();
	}
	
	public void popLastScore() {
		highScoreModel.remove(MAX);
	}

	public boolean isHighScore(int score) {
		if (checkIndex(score) >= 0 && checkIndex(score) <= 9)
			return true;
		else
			return false;
	}

	public int checkIndex(int score) {

		for (int i = 0; i < MAX; i++) {
			// Jämför det nya scoret med poängen i listan
			if (score > highScoreModel.getElementAt(i).getScore()) {
				return i; // Nya scoret är högre, returnerar index
			}
		}

		return MAX; // Nya scoret är inte högre än något i någon av listorna
	}

	public void saveToFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
			for (int i = 0; i < highScoreModel.size(); i++) {
				PlayerScore playerScore = highScoreModel.getElementAt(i);
				writer.write(playerScore.getInitials() + playerScore.getScore());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void readFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("     ");
                if (parts.length == 2) {
                    String initials = parts[0] + "     ";
                    int score = Integer.parseInt(parts[1]);
                    PlayerScore playerScore = new PlayerScore(initials, score);
                    highScoreModel.addElement(playerScore);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
