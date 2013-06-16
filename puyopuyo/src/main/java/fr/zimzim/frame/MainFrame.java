package fr.zimzim.frame;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.zimzim.render.NextItemDisplayer;
import fr.zimzim.render.RenderEngine;
import fr.zimzim.render.ScoreDisplayer;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7700792796798152332L;
	private JPanel panel, east;
	private static final String NAME = "Puyo Game";
	
	public MainFrame(RenderEngine r, NextItemDisplayer itemDisplayer, ScoreDisplayer scoreDisplayer) {
		super(NAME);
		this.panel = new JPanel();
		this.east = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.east.setLayout(new BoxLayout(this.east,BoxLayout.PAGE_AXIS));
		
		this.east.add(itemDisplayer);
		this.east.add(scoreDisplayer);
		this.panel.add(r, BorderLayout.CENTER);
		this.panel.add(this.east, BorderLayout.EAST);
		this.setContentPane(panel);
		
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);

	}
	
	public boolean endGame(int score) {
		int n = JOptionPane.showConfirmDialog(
			    this,
			    "Your score: "+score+"\n Want to play again ?",
			    "Finish !",
			    JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION) return true;
		else return false;
	}
}
