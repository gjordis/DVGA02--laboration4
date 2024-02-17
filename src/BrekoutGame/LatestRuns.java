package BrekoutGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LatestRuns extends JPanel{
	private int score1 = 1234;
	private int score2 = 12345;
	private int score3 = 123456;
	
	public LatestRuns() {
		//setLayout(new GridLayout(4,1));
		setPreferredSize(new Dimension(380, 145)); // Bestäm storlek
		setOpaque(false);
        //setBackground(Color.DARK_GRAY); // Sätt en bakgrundsfärg
        
        
        JLabel label = new JLabel("LATEST RUNS", JLabel.CENTER);
        
        Font font = new Font("Arial", Font.BOLD, Const.SCOREBOARD_FONTSIZE_SMALL);
        setOpaque(false);
        label.setFont(font);
        label.setForeground(Color.CYAN); // Sätt textfärg
            
        add(label);

	}
	/* returnerar resultatet på plats 0-2 */
	public int getLatestRuns(int index) {
		int[] scores = {score1, score2, score3};
		return scores[index];
	}
	
	
	
	
}