package fr.zimzim.frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.zimzim.render.NextItemDisplayer;
import fr.zimzim.render.RenderEngine;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7700792796798152332L;
	private JPanel panel;
	private static final String NAME = "Puyo Game";
	
	public MainFrame(RenderEngine r, NextItemDisplayer itemDisplayer) {
		super(NAME);
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		
		this.panel.add(r, BorderLayout.CENTER);
		this.panel.add(itemDisplayer, BorderLayout.EAST);
		this.setContentPane(panel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);

	}
	
	public boolean endGame() {
		int n = JOptionPane.showConfirmDialog(
			    this,
			    "Finish ! Want to play again ?",
			    "Finish !",
			    JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION) return true;
		else return false;
	}
}
