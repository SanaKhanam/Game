package fr.zimzim.render;


import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.zimzim.model.GameEngine;

/**
 * GraphicComponent in charge of displaying the player score
 * @author Simon Jambu
 *
 */
public class ScoreRender extends JPanel implements GraphicComponent {
	
	/**
	 * Component name
	 */
	public static final String NAME = "ScoreRender";
	
	/**
	 * Auto generated serial
	 */
	private static final long serialVersionUID = 4487064539525109005L;
	
	/**
	 * Component name label
	 */
	private JLabel name;
	
	/**
	 * Current player score label
	 */
	private JLabel score;
	
	/**
	 * Game engine instance
	 */
	private GameEngine engine;
	
	/**
	 * Constructor. Initializes the component
	 * @param engine
	 */
	public ScoreRender(GameEngine engine){
		this.engine = engine;
		this.name = new JLabel("Score: ");
		this.score = new JLabel("0");
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(name);
		this.add(score);
	}
	
	/**
	 * Updates component
	 */
	public void update() {
		int scoreValue = engine.getScore();
		int currentScore = Integer.valueOf(this.score.getText());
		
		if(scoreValue != currentScore) {
			this.score.removeAll();
			this.score.setText(String.valueOf(scoreValue));
		}
		
	}
	
	/**
	 * Resets useful datas
	 */
	public void clear() {
		this.score.removeAll();
		this.score.setText("0");
	}

}
