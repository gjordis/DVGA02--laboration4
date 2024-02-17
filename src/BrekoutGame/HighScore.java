package BrekoutGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.StyledEditorKit.FontSizeAction;

public class HighScore extends JPanel{
	private DefaultListModel<String> highScoreModel;
	private JLabel title;
	private JList highScores;
	
	public HighScore() {
		
		
		
		
		setPreferredSize(new Dimension(790, 145)); // Bestäm storlek
		setOpaque(false);
        //setBackground(Color.DARK_GRAY); // Sätt en bakgrundsfärg
        this.setLayout(new BorderLayout());
		
        
        title = new JLabel("HIGHSCORE");
        Font font = new Font("Arial", Font.BOLD, Const.SCOREBOARD_FONTSIZE_SMALL);
        
        title.setFont(font);
        title.setForeground(Color.CYAN); // Sätt textfärg
        
        add(title, BorderLayout.NORTH);
        
        
        highScoreModel = new DefaultListModel<>();
		highScores = new JList<>(highScoreModel);
		highScores.setFocusable(false);
		highScores.setOpaque(false);
		highScores.setBackground(Color.BLUE);
		//JScrollPane scrollPane = new JScrollPane(highScores);
		add(highScores);
        
        
	}
	
	public void addNewScore(String initials, int score) {
		
		
		highScoreModel.addElement(initials + " " + score);
        //sortHighScores();
	}
	
	private void sortHighScores() {
		
	}
	
	public boolean isHighScore(int score) {
		return true;
	}
	

}
