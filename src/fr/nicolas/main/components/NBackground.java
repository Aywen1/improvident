package fr.nicolas.main.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class NBackground extends JPanel {

	private BufferedImage bg, bg2;
	private int type;

	public NBackground(int type) {
		this.type = type;
		setLayout(null);

		if (type == 0 || type == 1) {

			try {
				bg = ImageIO.read(new File("res/components/bg.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (type == 2) {

			this.setBounds(0, 0, 800, 500);
			try {
				bg2 = ImageIO.read(new File("res/components/bg2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void paintComponent(Graphics g) {
		if (type == 0) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
		} else if (type == 1) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);

			g.setColor(new Color(0, 0, 0, 120));
			g.fillRect(10, 20, 65, 30);
			g.fillRect(10, 70, 120, 30);

			g.setColor(new Color(0, 0, 0));
			g.drawRect(9, 19, 66, 31);
			g.drawRect(9, 69, 121, 31);
		} else if (type == 2) {
			g.drawImage(bg2, 0, 0, getWidth(), getHeight(), null);
		}
	}

}
