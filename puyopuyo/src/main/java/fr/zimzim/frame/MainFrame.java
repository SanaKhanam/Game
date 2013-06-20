/**
 * Contains the game window.
 */
package fr.zimzim.frame;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.zimzim.render.GraphicEngine;
import fr.zimzim.render.ItemRender;
import fr.zimzim.render.MapRender;
import fr.zimzim.render.ScoreRender;

/**
 * 
 * @author Simon Jambu
 * The window that holds all graphical components.
 */

public class MainFrame extends JFrame {

		private static final long serialVersionUID = -7700792796798152332L;
	private JPanel panel, east;
	private static final String NAME = "Puyo Game";
	
	/**
	 * Frame/Window constructor
	 * @param render: 
	 * @param itemDisplayer
	 * @param scoreDisplayer
	 */
	public MainFrame(GraphicEngine engine) {
		super(NAME);
		this.panel = new JPanel();
		this.east = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.east.setLayout(new BoxLayout(this.east,BoxLayout.PAGE_AXIS));
		
		this.east.add((Component) engine.getComponent(ItemRender.NAME));
		this.east.add((Component) engine.getComponent(ScoreRender.NAME));
		this.panel.add((Component) engine.getComponent(MapRender.NAME), BorderLayout.CENTER);
		this.panel.add(this.east, BorderLayout.EAST);
		this.setContentPane(panel);
		
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);

	}
	
	/**
	 * Displays a Dialog window when the game is over.
	 * @param score: High score
	 * @return
	 */
	public boolean endGame(int score) {
		int n = JOptionPane.showConfirmDialog(
			    this,
			    "Your score: "+score+"\nWant to play again ?",
			    "Game Over !",
			    JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION) return true;
		else return false;
	}
}
