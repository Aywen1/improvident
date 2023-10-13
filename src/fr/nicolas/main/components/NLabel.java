package fr.nicolas.main.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class NLabel extends JLabel {

	public NLabel(String text, int size) {
		setText(text);
		setForeground(new Color(255,255,255));
		setFont(new Font("Calibri Light", Font.PLAIN, size));
	}
	
}
