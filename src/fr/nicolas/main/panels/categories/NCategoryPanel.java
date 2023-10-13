package fr.nicolas.main.panels.categories;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public abstract class NCategoryPanel extends JPanel {

	public NCategoryPanel() {
		setLayout(null);
	}
	
	public abstract void mouseMove(Point mouseLocation);
	
	public abstract void mouseClick(Point mouseLocation, boolean middleClick);
	
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
}
