package fr.zimzim.render;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.zimzim.model.GameEngine;

public class ScoreDisplayer extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4487064539525109005L;
	private JLabel name;
	private JLabel score;
	
	public ScoreDisplayer(){
		this.name = new JLabel("Score: ");
		this.score = new JLabel("0");
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(name);
		this.add(score);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		int scoreValue = ((GameEngine) arg1).getScore();
		int currentScore = Integer.valueOf(this.score.getText());
		
		if(scoreValue != currentScore) {
			this.score.removeAll();
			this.score.setText(String.valueOf(scoreValue));
		}
		
	}
	
	public void clear() {
		this.score.removeAll();
		this.score.setText("0");
	}

}
