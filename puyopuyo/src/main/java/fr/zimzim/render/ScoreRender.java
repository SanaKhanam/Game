package fr.zimzim.render;


import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.zimzim.model.GameEngine;

public class ScoreRender extends JPanel implements GraphicComponent {

	public static final String NAME = "ScoreRender";
	private static final long serialVersionUID = 4487064539525109005L;
	private JLabel name;
	private JLabel score;
	private GameEngine engine;
	
	public ScoreRender(GameEngine engine){
		this.engine = engine;
		this.name = new JLabel("Score: ");
		this.score = new JLabel("0");
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(name);
		this.add(score);
	}
	
	public void update() {
		int scoreValue = engine.getScore();
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
