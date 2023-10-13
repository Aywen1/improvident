package fr.nicolas.main.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class NCategory extends JPanel {

	private Rectangle category;
	private BufferedImage img;
	private boolean isHovered = false;

	public NCategory(String name, int y) {
		category = new Rectangle();
		category.setBounds(10, 38 + y * 40, 35, 28);

		try {
			img = ImageIO.read(new File("res/categoriesIcons/" + name.toLowerCase() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean mouseClick(Point mouseLocation) {
		if (category.contains(mouseLocation)) {
			return true;
		} else {
			return false;
		}
	}

	public void mouseMove(Point mouseLocation) {
		if (category.contains(mouseLocation)) {
			isHovered = true;
		} else {
			isHovered = false;
		}
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, getWidth() - 7, getHeight(), null);

		if (isHovered) {
			g.setColor(new Color(255, 255, 255, 150));
			g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
		}
	}

}
